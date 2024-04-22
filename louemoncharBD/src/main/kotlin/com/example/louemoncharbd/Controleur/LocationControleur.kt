package com.example.louemoncharbd.Controleur

import com.example.louemoncharbd.Modèle.Location
import com.example.louemoncharbd.Service.LocationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
class LocationControleur (val service: LocationService){

    @GetMapping("/location")
    fun obtenirLocation() = service.chercherTous()

    @GetMapping("/location/{code_location}")
    fun obtenirLocationParCode(@PathVariable code_location: String) = service.chercherParCode(code_location)

    @PostMapping("/location")
    fun inscrireLocation(@RequestBody location: Location): ResponseEntity<Location> {
        val nouvelleLocation = service.ajouterLocation(location)

        val uri: URI = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{code}")
            .buildAndExpand(location.code) // Remplacez 'code' par le nom de la propriété appropriée dans votre classe Location
            .toUri()

        return ResponseEntity.created(uri).body(nouvelleLocation)

        return ResponseEntity.internalServerError().build()
    }





    @PutMapping("/location/{code_location}")
    fun majLocation(@PathVariable code_location: String, @RequestBody location: Location): ResponseEntity<Location>
    {
        val nouvelleLocation = service.modifier(code_location, location)

        if (nouvelleLocation != null) {
            val uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{code}")
                    .buildAndExpand(nouvelleLocation)
                    .toUri()

            return ResponseEntity.created(uri).body(nouvelleLocation)
        }
        return ResponseEntity.ok(location)
    }

    @DeleteMapping("/location/{code_location}")
    fun retirerLocation(@PathVariable code_location: String): ResponseEntity<Location>{
        service.effacer(code_location)
        return ResponseEntity.noContent().build()
    }




}