package com.example.louemoncharbd.Controleur

import com.example.louemoncharbd.GestionAccès.DroitAccèsInsuffisantException
import com.example.louemoncharbd.Modèle.Voiture
import com.example.louemoncharbd.Service.UtilisateurService
import com.example.louemoncharbd.Service.VoitureService
import com.example.louemoncharbd.Modèle.Utilisateur
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.security.Principal

@RestController
class VoitureControleur (val service: VoitureService) {

    @GetMapping("/voiture")
    fun obtenirVoiture() = service.chercherTous()

    @GetMapping("/voiture/{code_voiture}")
    fun obtenirVoiture(@PathVariable code_voiture: String) = service.chercherParCode(code_voiture)

    //Recherche par prix
    @GetMapping("/voiturePrix/{prix}")
    fun obtenirVoitureParPrix(@PathVariable prix: String) = service.chercherParPrix(prix)

    //Recherche par annee
    @GetMapping("/voitureAnnee/{annee}")
    fun obtenirVoitureParAnne(@PathVariable annee: Int) = service.chercherParAnnee(annee)

    //Recherche par marque
    @GetMapping("/voitureMarque/{marque}")
    fun obtenirVoitureParMarque(@PathVariable marque: String) = service.chercherParMarque(marque)

    //Rechercher par transmission
    @GetMapping("/voitureTransmission/{transmission}")
    fun obtenirListVoitureParTransmission(@PathVariable transmission: String) =
        service.chercherParTransmission(transmission)

    //Recherche par distance
//    @GetMapping("/voitureDistance/{distance}")
//    fun obtenirVoitureParDistance(@PathVariable distance: Int) = service.chercherParDistance(distance)


    @GetMapping("/voiture/{code_voiture}/location/")
    fun obtenirLocationVoiture(@PathVariable code_voiture: String): ResponseEntity<Voiture> = ResponseEntity(
        HttpStatus.NOT_IMPLEMENTED
    )

    //Ajouter une voiture
    @PostMapping("/voitures")
    fun ajouterVoiture(@RequestBody voiture: Voiture): ResponseEntity<Voiture> {
        val nouvelVoiture = service.ajouter(voiture)

        if (nouvelVoiture != null) {
            val uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nouvelVoiture.code)
                .toUri()

            return ResponseEntity.created(uri).body(nouvelVoiture)
        }
        return ResponseEntity.internalServerError().build()
    }


    @PutMapping("/voitureModifier/{id}")
    fun modifierVoiture(@PathVariable id: String, @RequestBody auto: Voiture, principal: Principal): ResponseEntity<Voiture> {
        val nouvelleVoiture = service.modifier(id, auto, principal.name)

        if (nouvelleVoiture != null) {
            val uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nouvelleVoiture.code)
                .toUri()

            return ResponseEntity.created(uri).body(nouvelleVoiture)
        }

        return ResponseEntity.notFound().build()
    }




    @DeleteMapping("/voiture/{code}")
    fun effacerVoiture(@PathVariable code: String, principal: Principal): ResponseEntity<Voiture> {
        println(principal.name)
        println(code)

        service.effacer(code, principal.name)

        return ResponseEntity.noContent().build()
    }


}