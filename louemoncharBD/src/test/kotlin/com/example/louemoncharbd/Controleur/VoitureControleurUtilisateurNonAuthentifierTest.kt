package com.example.louemoncharbd.Controleur

import com.example.louemoncharbd.Exeptions.RessourceInexistanteException
import com.example.louemoncharbd.Modèle.Voiture
import com.example.louemoncharbd.Service.UtilisateurService
import com.example.louemoncharbd.Service.VoitureService


import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.mockito.Mockito

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*



@SpringBootTest
@AutoConfigureMockMvc
class VoitureControleurUtilisateurNonAuthentifierTest{




    @MockBean
    private lateinit var utilisateurService: UtilisateurService

    @MockBean
    private lateinit var voitureService: VoitureService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    // @GetMapping("/voiture")
    fun `Étant donné un utilisateur non-authentifié et des voitures inscrits au service lorsque l'utilisateur effectue un requête GET pour obtenir la liste de voitures inscrits alors il obtient la liste de voitures et un code de retour 200` (){
        val voiture: List<Voiture> = listOf(
            Voiture("1", 2, "honda", "Automatique", "civice", 2015, "honda.png", "Disponible", "400"),
        )

        Mockito.`when`(voitureService.chercherTous()).thenReturn(voiture)

        mockMvc.perform(MockMvcRequestBuilders.get("/voiture"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0]['code']").value("1"))
    }

    @Test
    // /voiture/{code_voiture}
    fun `Étant donné une recherche pour afficher une voitures par code lorsqu'on effectue une requête GET pour accéder à une voiture par code on obtient un JSON contenant une voiture triées par code et un code de retour 200`() {
        val voiture: List<Voiture> = listOf(
            Voiture("1", 2, "honda", "Automatique", "civice", 2015, "honda.png", "Disponible", "400"),
        )
        Mockito.`when`(voitureService.chercherParCode("1")).thenReturn(voiture[0])
        mockMvc.perform(MockMvcRequestBuilders.get("/voiture/1"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("1"))
    }

    @Test
    // /voiture/{code_voiture}
    fun `Étant donné une recherche pour afficher une voitures par code qui n'existe pas lorsqu'on effectue une requête GET pour accéder à une voiture par code on obtient un JSON contenant un code de retour 404	`() {
        Mockito.`when`(voitureService.chercherParCode("1012")).thenReturn(null)
        mockMvc.perform(get("/voiture/1012")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect { résultat ->
                Assertions.assertTrue(résultat.resolvedException is RessourceInexistanteException)
                Assertions.assertEquals(
                    "La voiture	 nest pas inscrit au service.",
                    résultat.resolvedException?.message
                )
            }
    }



}