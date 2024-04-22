package com.example.louemoncharbd.Controleur

import com.example.louemoncharbd.Service.UtilisateurService
import com.example.louemoncharbd.Modèle.Utilisateur
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.security.Principal

@RestController
class UtilisateurControleur (val service: UtilisateurService){

    @GetMapping("/utilisateur")
    fun obtenirUtilisateur() = service.chercherTous()

    @GetMapping("/utilisateur/{code_utilisateur}")
    fun obtenirUtilisateurParCode(@PathVariable code_utilisateur: String) = service.chercherParCode(code_utilisateur)

    @GetMapping("/utilisateurNom/{nom}")
    fun obtenirUtilisateurParNom(@PathVariable nom: String) = service.chercherParNom(nom)

    //Recherche par annee
    @GetMapping("/utilisateurPrenom/{prenom}")
    fun obtenirUtilisateurParPrénom(@PathVariable prenom: String) = service.chercherParPrénom(prenom)

    //Recherche par marque
    @GetMapping("/utilisateurNumPermis/{numPermis}")
    fun obtenirUtilisateurParNumPermis(@PathVariable numPermis: String) = service.chercherParNumPermis(numPermis)

    //Rechercher par transmission
    @GetMapping("/utilisateurNumTéléphone/{telephone}")
    fun obtenirListUtilisateurParTéléphone(@PathVariable telephone : Int) = service.chercherParTelephone(telephone)

    //Recherche par distance
//    @GetMapping("/voitureDistance/{distance}")
//    fun obtenirVoitureParDistance(@PathVariable distance: Int) = service.chercherParDistance(distance)


    @PostMapping("/utilisateur")
    fun ajouterUtilisateur(@RequestBody utilisateur: Utilisateur) : ResponseEntity<Utilisateur> {
        val nouvelleUtilisateur = service.ajouterUtilisateur(utilisateur)

        val uri: URI = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{code}")
            .buildAndExpand(utilisateur.code) // Remplacez 'code' par le nom de la propriété appropriée dans votre classe Location
            .toUri()

        return ResponseEntity.created(uri).body(nouvelleUtilisateur)
    }

    @PutMapping("/utilisateur/{code_utilisateur}")
    fun majUtilisateur(@PathVariable code_utilisateur: String, @RequestBody utilisateur: Utilisateur): ResponseEntity<Utilisateur> {
        val nouveauResto = service.modifier(code_utilisateur, utilisateur)

        if (nouveauResto != null) {
            val uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{code}")
                    .buildAndExpand(nouveauResto)
                    .toUri()

            return ResponseEntity.created(uri).body(nouveauResto)
        }
        return ResponseEntity.ok(utilisateur)


    }
    @DeleteMapping("/utilisateur/{code_utilisateur}")
    fun retirerUtilisateur(@PathVariable code_utilisateur: String,  utilisateur: Principal): ResponseEntity<Utilisateur> {

        service.effacer(code_utilisateur, utilisateur.name)
        return ResponseEntity.noContent().build()
        }

}




