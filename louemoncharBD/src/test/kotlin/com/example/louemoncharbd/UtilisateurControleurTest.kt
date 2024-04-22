package com.example.louemoncharbd


import com.example.louemoncharbd.Exeptions.ConflitAvecUneRessourceExistanteException
import com.example.louemoncharbd.Modèle.Utilisateur
import com.example.louemoncharbd.Service.UtilisateurService
import com.example.louemoncharbd.Exeptions.RessourceInexistanteException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.hasSize


@SpringBootTest
	@AutoConfigureMockMvc
	class UtilisateurControleurTest {

		@Autowired
		private lateinit var mockMvc: MockMvc

		@MockBean
		lateinit var utilisateurService: UtilisateurService

		@Autowired
		private lateinit var mapper: ObjectMapper




		// Test pour obtenir tous les utilisateurs
		@Test
		fun `Étant donné une demande pour afficher tous les utilisateurs lorsqu'on effectue une requête GET alors on obtient un JSON contenant tous les utilisateurs et un code de retour 200`() {
			val utilisateurs: List<Utilisateur> = listOf(
				Utilisateur("1", 1, "Dupont", "Jean", "jean.dupont@mail.com", 2, "1234567890", "NP1234","asd"),
				Utilisateur("2", 2, "Durand", "Alice", "alice.durand@mail.com", 1, "0987654321", "NP2345","asd")
			)

			Mockito.`when`(utilisateurService.chercherTous()).thenReturn(utilisateurs)

			mockMvc.perform(get("/utilisateur"))
				.andExpect(status().isOk)
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize<Any>(2)))
				.andExpect(jsonPath("$[0].nom").value("Dupont"))
				.andExpect(jsonPath("$[1].nom").value("Durand"))
		}


	// Test pour obtenir un utilisateur par code
	@Test
	fun `Étant donné un utilisateur dont le code est 1 lorsqu'on effectue une requête GET de recherche par code alors on obtient un JSON qui contient un utilisateur dont le code est 1 et un code de retour 200`() {
		val utilisateur = Utilisateur("1", 1, "Dupont", "Jean", "jean.dupont@mail.com", 1, "1234567890", "NP1234","asd")

		Mockito.`when`(utilisateurService.chercherParCode("1")).thenReturn(utilisateur)

		mockMvc.perform(get("/utilisateur/1"))
			.andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.code").value("1"))
			.andExpect(jsonPath("$.nom").value("Dupont"))
	}


	// Test pour ajouter un utilisateur
	@Test
	fun `Étant donné un nouvel utilisateur lorsqu'on effectue une requête POST pour l'ajouter alors on obtient un code de retour 201 et l'uri de la ressource ajoutée`() {
		val utilisateur = Utilisateur("3", 3, "Leroy", "Marie", "marie.leroy@mail.com", 3, "1122334455", "NP3456","asd")

		Mockito.`when`(utilisateurService.ajouterUtilisateur(utilisateur)).thenReturn(utilisateur)

		mockMvc.perform(post("/utilisateur")
			.contentType(MediaType.APPLICATION_JSON)
			.content(mapper.writeValueAsString(utilisateur)))
			.andExpect(status().isCreated)
			.andExpect(header().string("Location", containsString("/utilisateur/3")))
	}


	// Test pour mettre à jour un utilisateur
	@Test
	fun `Étant donné un utilisateur existant lorsqu'on effectue une requête PUT pour le mettre à jour alors on obtient un JSON qui contient l'utilisateur mis à jour et un code de retour 200`() {
		val utilisateur = Utilisateur("1", 1, "Dupont", "Jean", "jean.dupont@mail.com", 1, "1234567890", "NP1234","asd")

		Mockito.`when`(utilisateurService.modifier("1", utilisateur)).thenReturn(utilisateur)

		mockMvc.perform(put("/utilisateur/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(mapper.writeValueAsString(utilisateur)))
			.andExpect(status().isOk)
			.andExpect(jsonPath("$.code").value("1"))
			.andExpect(jsonPath("$.nom").value("Dupont"))
	}

	// Test pour supprimer un utilisateur
	@Test
	fun `Étant donné un utilisateur existant lorsqu'on effectue une requête DELETE alors on obtient un code de retour 204`() {
		Mockito.doNothing().`when`(utilisateurService).effacer("1","2")

		mockMvc.perform(delete("/utilisateur/1"))
			.andExpect(status().isNoContent)
	}


	// Test pour obtenir un utilisateur par nom
	@Test
	fun `Étant donné un utilisateur dont le nom est Dupont lorsqu'on effectue une requête GET de recherche par nom alors on obtient un JSON qui contient un utilisateur dont le nom est Dupont et un code de retour 200`() {
		val utilisateur = Utilisateur("1", 1, "Dupont", "Jean", "jean.dupont@mail.com", 1, "1234567890", "NP1234","asd")

		Mockito.`when`(utilisateurService.chercherParNom("Dupont")).thenReturn(listOf(utilisateur))

		mockMvc.perform(get("/utilisateurNom/Dupont"))
			.andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize<Any>(1)))
			.andExpect(jsonPath("$[0].nom").value("Dupont"))
	}

	// Test pour obtenir un utilisateur par prénom
	@Test
	fun `Étant donné un utilisateur dont le prénom est Jean lorsqu'on effectue une requête GET de recherche par prénom alors on obtient un JSON qui contient un utilisateur dont le prénom est Jean et un code de retour 200`() {
		val utilisateur = Utilisateur("1", 1, "Dupont", "Jean", "jean.dupont@mail.com", 1, "1234567890", "NP1234","asd")

		Mockito.`when`(utilisateurService.chercherParPrénom("Jean")).thenReturn(listOf(utilisateur))

		mockMvc.perform(get("/utilisateurPrenom/Jean"))
			.andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize<Any>(1)))
			.andExpect(jsonPath("$[0].prénom").value("Jean"))
	}

	// Test pour obtenir un utilisateur par numéro de permis
	@Test
	fun `Étant donné un utilisateur dont le numéro de permis est NP1234 lorsqu'on effectue une requête GET de recherche par numéro de permis alors on obtient un JSON qui contient un utilisateur dont le numéro de permis est NP1234 et un code de retour 200`() {
		val utilisateur = Utilisateur("1", 1, "Dupont", "Jean", "jean.dupont@mail.com", 1, "1234567890", "NP1234","asd")

		Mockito.`when`(utilisateurService.chercherParNumPermis("NP1234")).thenReturn(listOf(utilisateur))

		mockMvc.perform(get("/utilisateurNumPermis/NP1234"))
			.andExpect(status().isOk)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.numPermis").value("NP1234"))
	}




}
