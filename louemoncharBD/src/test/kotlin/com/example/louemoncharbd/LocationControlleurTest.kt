package com.example.louemoncharbd

import com.example.louemoncharbd.Modèle.Location
import com.example.louemoncharbd.Modèle.Utilisateur
import com.example.louemoncharbd.Modèle.Voiture
import com.example.louemoncharbd.Service.LocationService
import com.example.louemoncharbd.Exeptions.RessourceInexistanteException
import com.example.louemoncharbd.Exeptions.ConflitAvecUneRessourceExistanteException


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

import org.mockito.Mockito

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.CoreMatchers.containsString
import java.sql.Date
import java.time.LocalDate


@SpringBootTest
@AutoConfigureMockMvc

class LocationControlleurTest {


    @Autowired
    private lateinit var mapper: ObjectMapper

    @MockBean
    lateinit var service: LocationService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    //@GetMapping("/location/{code}")
    fun `Étant donné la location dont le code est 1 lorsqu'on effectue une requête GET de recherche par code alors on obtient un JSON qui contient un restaurant dont le code est 1 et un code de retour 200`() {
        val location = Location("1",3,4,Date(3029,5,6))

        Mockito.`when`(service.chercherParCode("1")).thenReturn(location)

        mockMvc.perform(get("/location/1"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("1"))

    }


    @Test
    //@PostMapping("/location")
    fun `Étant donnée la location dont le code est 67 et qui n'est pas inscrit au service lorsqu'on effectue une requête POST pour l'ajouter alors on obtient un JSON qui contient un restaurant dont le code est 2, un code de retour 201 et l'uri de la ressource ajoutée`() {
        val location = Location("68",1,2, Date(2022,4,6))
        Mockito.`when`(service.ajouterLocation(location)).thenReturn(location)

        mockMvc.perform(post("/location")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(location)))
                .andExpect(status().isCreated)
                .andExpect(header().string("Location", containsString("/location/68")))

    }

    @Test
    //@PostMapping("/location")
    fun `Étant donnée la location dont le code est 1 qui existe déjà lorsqu'on effectue une requête POST pour l'ajouter alors on obtient un code de retour 409 et le message d'erreur « La location 1 est déjà inscrit au service » `() {
        val location = Location("1",4,3,Date(2022,4,6))
        Mockito.`when`(service.ajouterLocation(location)).thenThrow(ConflitAvecUneRessourceExistanteException("La location 1 est déjà inscrit au service."))

        mockMvc.perform(post("/location")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(location)))
                .andExpect(status().isCreated)

    }

    @Test
    //@PostMapping("/location")
    fun `Étant donnée la location 3 dont le code est 3 et qui n'est pas inscrit au service lorsqu'on effectue une requête POST pour l'ajouter et que le champ nom est manquant dans le JSON envoyé alors on obtient un code de retour 400`() {
        val locationStr = ""

        mockMvc.perform(post("/location")
                .contentType(MediaType.APPLICATION_JSON)
                .content(locationStr))
                .andExpect(status().isBadRequest)
    }

    @Test
    //@PutMapping("/location/{code}")
    fun `Étant donnée la location dont le code est 1 et qui est inscrit au service et dont la date est 20230909 lorsqu'on effectue une requête PUT pour modifier la date pour « 20230318 » alors on obtient un JSON qui contient une location dont le code est 3 et le nom est « 20230318 » ainsi qu'un code de retour 200`() {
        val location = Location("1",4,3,Date(2022,4,6))

        Mockito.`when`(service.modifier("1", location)).thenReturn(null)

        mockMvc.perform(put("/location/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(location)))
                .andExpect(status().isOk)

    }

    @Test
    //@PutMapping("/location/{code}")
    fun `Étant donnée la location 70 dont le code est 70 et qui n'est pas inscrit au service lorsqu'on effectue une requête PUT alors on obtient un JSON qui contient une location dont le code est 70 et un code de retour 201`() {
        val location = Location("4",4,3, Date(2022,4,6))

        mockMvc.perform(put("/location/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(location)))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.code").value("4"))
    }

    @Test
    //@DeleteMapping("/location/{code}")
    fun `Étant donnée la location dont le code est 1 et qui est inscrit au service lorsqu'on effectue une requête DELETE alors on obtient un code de retour 204`() {
        Mockito.doNothing().`when`(service).effacer("1")

        mockMvc.perform(delete("/location/1"))
                .andExpect(status().isNoContent)
    }

    @Test
    //@DeleteMapping("/location/{code}")
    fun `Étant donné la location dont le code est 78 et qui n'est pas inscrit au service lorsqu'on effectue une requête DELETE alors on obtient un code de retour 404`() {
        Mockito.`when`(service.effacer("78")).thenThrow(RessourceInexistanteException("Le restaurant HS123 n'est pas inscrit au service."))

        mockMvc.perform(delete("/location/78"))
                .andExpect(status().isNotFound)



    }

}






