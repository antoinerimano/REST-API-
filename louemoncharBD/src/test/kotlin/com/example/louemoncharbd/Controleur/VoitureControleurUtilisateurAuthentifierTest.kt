package com.example.louemoncharbd.Controleur

import com.example.louemoncharbd.Exeptions.RessourceInexistanteException

import com.example.louemoncharbd.Modèle.Utilisateur
import com.example.louemoncharbd.Modèle.Voiture
import com.example.louemoncharbd.Service.LocationService
import com.example.louemoncharbd.Service.UtilisateurService
import com.example.louemoncharbd.Service.VoitureService
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.mockito.Mockito
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf

@SpringBootTest
@AutoConfigureMockMvc
class VoitureControleurUtilisateurAuthentifierTest {

    @Autowired
    private lateinit var mapper: ObjectMapper

    @MockBean
    lateinit var locationService: LocationService

    @MockBean
    private lateinit var utilisateurService: UtilisateurService

    @MockBean
    private lateinit var voitureService: VoitureService

    @Autowired
    private lateinit var mockMvc: MockMvc

    //@PostMapping("/voitures")
    @Test
    fun `Étant donné un utilisateur authentifié qui tente d'inscire une voiture et qui n'est pas inscrit au service lorsque l'utilisateur effectue une requête POST pour l'ajouter alors il obtient un JSON qui contient une voiture dont le code est 6, un code de retour 201 et l'uri de la ressource ajoutée` (){
        val voiture =Voiture("6", 2, "honda", "Automatique", "civice", 2015, "honda.png", "Disponible", "400")
        Mockito.`when`(voitureService.ajouter(voiture)).thenReturn(voiture)
        mockMvc.perform(
            post("/voitures").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(voiture)))
            .andExpect(status().isCreated)
            .andExpect(
                header().string("Location", CoreMatchers.containsString("/voitures")))
            .andExpect(jsonPath("$.code").value("6"))
        Mockito.verify(voitureService).ajouter(voiture)
    }

    //@PutMapping("/restaurants/{code}")
    @Test
    fun `Étant donné un utilisateur non authentifier et non autorisé et la voiture inscrit au service dont le code est 8 la marque est Mazda lorsque l'utilisateur effectue une requête PUT pour modifier la marque pour « Mazda v2 » alors il obtient un JSON qui contient une erreur ainsi qu'un code de retour 401` (){
        val voiture =Voiture("8", 2, "Mazda", "Automatique", "civice", 2015, "honda.png", "Disponible", "400")
        val voiture2 =Voiture("8", 2, "Mazda v2", "Automatique", "civice", 2015, "honda.png", "Disponible", "400")
        val utilisateur= Utilisateur("1",2,"test","testU","testU@hotmail.com",1,"5141231234","213123asdsad","auth0|656fe9c419c6480cbd88e491")

        Mockito.`when`(voitureService.chercherParCode("8")).thenReturn(voiture)
        Mockito.`when`(voitureService.modifier("8", voiture,utilisateur.id_auth0)).thenReturn(voiture2)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/voitureModifier/8").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(voiture)))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
            .andExpect(MockMvcResultMatchers.jsonPath("$.marque").value("Mazda v2"))
        Mockito.verify(voitureService).modifier("8", voiture, utilisateur.id_auth0)
    }

    // @DeleteMapping("/voiture/{code}")
    @Test
    fun `Étant donné l'utilisateur authentifié qui est autorisé et la voiture dont le code est 3 et qui est inscrit au service lorsque l'utilisateur effectue une requête DELETE alors il obtient un code de retour 204` (){
        val voiture =Voiture("3", 2, "Mazda v2", "Automatique", "civice", 2015, "honda.png", "Disponible", "400")
        val utilisateur= Utilisateur("1",2,"test","testU","testU@hotmail.com",1,"5141231234","213123asdsad","auth0|656fe9c419c6480cbd88e491")

        Mockito.doNothing().`when`(voitureService).effacer("3", utilisateur.id_auth0)

        mockMvc.perform(MockMvcRequestBuilders.delete("/voiture/3").with(csrf()))
            .andExpect(MockMvcResultMatchers.status().isNoContent)

        Mockito.verify(voitureService).effacer("3", utilisateur.id_auth0)
    }

    // @DeleteMapping("/voiture/{code}")
    @Test
    fun `Étant donné l'utilisateur n'est pas authentifié qui n'est pas autorisé et la voiture dont le code est 3 et qui est inscrit au service lorsque l'utilisateur effectue une requête DELETE alors il obtient un code de retour 403` (){
        val voiture =Voiture("3", 2, "Mazda v2", "Automatique", "civice", 2015, "honda.png", "Disponible", "400")
        val utilisateur= Utilisateur("1",2,"test","testU","testU@hotmail.com",1,"5141231234","213123asdsad","12312asd")

        Mockito.doNothing().`when`(voitureService).effacer("3", utilisateur.id_auth0)

        mockMvc.perform(MockMvcRequestBuilders.delete("/voiture/3").with(csrf()))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
            .andExpect { résultat ->
                Assertions.assertTrue(résultat.resolvedException is RessourceInexistanteException)
                Assertions.assertEquals("La voiture 3 n'est pas inscrit au service.",
                    résultat.resolvedException?.message)

 }



    }
}