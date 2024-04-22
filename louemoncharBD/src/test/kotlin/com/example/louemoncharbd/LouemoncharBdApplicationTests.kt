import com.example.louemoncharbd.Controleur.LocationControleur
import com.example.louemoncharbd.Controleur.UtilisateurControleur
import com.example.louemoncharbd.Controleur.VoitureControleur

import com.example.louemoncharbd.Exeptions.RessourceInexistanteException
import com.example.louemoncharbd.LouemoncharBdApplication
import com.example.louemoncharbd.Modèle.Utilisateur
import com.example.louemoncharbd.Modèle.Voiture
import com.example.louemoncharbd.Service.LocationService
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@SpringBootTest(classes = arrayOf(LouemoncharBdApplication::class))
@AutoConfigureMockMvc

class LouemoncharBdApplicationTests {

	@MockBean
	lateinit var locationService: LocationService

	@MockBean
	private lateinit var utilisateurService: UtilisateurService

	@MockBean
	private lateinit var voitureService: VoitureService

	@Autowired
	private lateinit var mockMvc: MockMvc

	@Test
	fun contextLoads() {
		// Ensure the Spring application context loads successfully
	}


	// on veut tester la recherche d'une transaction (historique) spécifique
	//on veut tester l'afficahge de toute les voiture disponible avec un GET
	//on veut tester l'affichage de toute les voiture par prix
	//on veut tester l'affichage de toute les voiture par marque
	//on veut tester l'affichage de toute les voiture par année
	//on veut tester l'affichage de toute les voiture par distance
	//on veut tester une recherche d'utilisateur inexistant
	//on veut tester une recherche de voiture inexistante

	//@GetMapping("/voiture/{code_voiture}")

	@Test
	// @GetMapping("/location/{code_location}")
	fun `Étant donné une transaction entre un propriétaire et un utilisateur dont le code est 1111 lorsqu'on effectue une requête GET de recherche par code alors on obtient un JSON qui contient une transaction dont le code est 1111 et un code de retour 200`() {

	}


	// @GetMapping("/voiture/{code_voiture}/{prix}")
	@Test
	fun `Étant donné une demande pour afficher toutes les voitures disponibles lorsqu'on effectue une requête GET pour accéder à la liste de voiture on obtient un JSON contenant toutes les voitures disponibles et un code de retour 200`() {

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


	// @GetMapping("/voiture/{code_voiture}/{prix}")
	@Test
	fun `Étant donné une demande pour afficher toutes les voitures par prix lorsqu'on effectue une requête GET pour accéder à la liste de voiture par prix on obtient un JSON contenant toutes les voitures triées par prix et un code de retour 200`() {
		val voiture: List<Voiture> = listOf(
				Voiture("1", 2, "honda", "Automatique", "civice", 2015, "honda.png", "Disponible", "400"),
		)

		Mockito.`when`(voitureService.chercherParPrix("400")).thenReturn(voiture)

		mockMvc.perform(MockMvcRequestBuilders.get("/voiturePrix/400"))
				.andExpect(MockMvcResultMatchers.status().isOk)
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].prix").value("400"))
	}




	// @GetMapping("/voiture/{code_voiture}/{marque}")
	@Test
	fun `Étant donné une demande pour afficher toutes les voitures par marque lorsqu'on effectue une requête GET pour accéder à la liste de voiture par marque on obtient un JSON contenant toutes les voitures triées par marque et un code de retour 200`() {
		val voiture: List<Voiture> = listOf(
				Voiture("1", 2, "honda", "Automatique", "civice", 2015, "honda.png", "Disponible", "400"),
		)

		Mockito.`when`(voitureService.chercherParMarque("honda")).thenReturn(voiture)

		mockMvc.perform(MockMvcRequestBuilders.get("/voitureMarque/honda"))
				.andExpect(MockMvcResultMatchers.status().isOk)
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].marque").value("honda"))
	}





	// @GetMapping("/voiture/{code_voiture}/{annee}")
	@Test
	fun `Étant donné une demande pour afficher toutes les voitures par année lorsqu'on effectue une requête GET pour accéder à la liste de voiture par année on obtient un JSON contenant toutes les voitures triées par année et un code de retour 200`() {
		val voiture: List<Voiture> = listOf(
				Voiture("1", 2, "honda", "Automatique", "civice", 2015, "honda.png", "Disponible", "400"),
		)

		Mockito.`when`(voitureService.chercherParAnnee(2015)).thenReturn(voiture)

		mockMvc.perform(MockMvcRequestBuilders.get("/voitureAnnee/2015"))
				.andExpect(MockMvcResultMatchers.status().isOk)
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].année").value("2015"))
	}

	@Test
	fun `Étant donné une demande pour afficher toutes les voitures par année qui n'existe pas lorsqu'on effectue une requête GET pour accéder à la liste de voiture par année on obtient un JSON contenant un code de retour 404`() {
		val voiture : List<Voiture> = listOf(
			Voiture("1", 2, "honda", "Automatique", "civice", 2015, "honda.png", "Disponible", "400"),
		)
		Mockito.`when`(voitureService.chercherParAnnee(1000)).thenReturn(voiture)
		mockMvc.perform(get("/voitureAnnee/1000"))
			.andExpect(MockMvcResultMatchers.status().isOk)
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].année").value("2015"))
	}


	@Test
	fun `Étant donné une demande pour afficher toutes les voitures par transmission lorsqu'on effectue une requête GET pour accéder à la liste de voiture par transmission Automatique on obtient un JSON contenant toutes les voitures triées par transmission manuel et un code de retour 200`() {
		val voiture: List<Voiture> = listOf(
				Voiture("1", 2, "honda", "Automatique", "civice", 2015, "honda.png", "Disponible", "400"),
				Voiture("2", 1, "mazda", "Automatique", "2013", 2013, "mazda.png", "Disponible", "300"),

				)

		Mockito.`when`(voitureService.chercherParTransmission("Automatique")).thenReturn(voiture)

		mockMvc.perform(MockMvcRequestBuilders.get("/voitureTransmission/Automatique"))
				.andExpect(MockMvcResultMatchers.status().isOk)
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].transmission").value("Automatique"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].transmission").value("Automatique"))
	}



	// @GetMapping("/voiture/{code_voiture}/{distance}")
	@Test
	fun `Étant donné une demande pour afficher toutes les voitures par distance lorsqu'on effectue une requête GET pour accéder à la liste de voiture par distance on obtient un JSON contenant toutes les voitures triées par distance et un code de retour 200`() {

	}


	// @GetMapping("/utilisateur/{code_utilisateur}/")
	@Test
	fun `Étant donné l'utilisateur dont le code est 1111 et qui n'est pas inscrit au service lorsqu'on effectue une requête GET de recherche par code alors on obtient un code de retour 404 et le message d'erreur « L'utilisateur 1111 n'est pas inscrit au service »`() {

	}



	// @GetMapping("/voiture/{id_voiture}/")
	@Test
	fun `Étant donné une voiture dont le ID est 20 et qui n'est pas inscrite au service lorsqu'on effectue une requête GET de recherche par ID alors on obtient un code de retour 404 et le message d'erreur « La voiture dont le ID est 20 n'est pas inscrite au service »`() {

		Mockito.`when`(voitureService.chercherParCode("20")).thenThrow(RessourceInexistanteException("La voiture dont le ID est 20 n'est pas inscrite au service."))



		mockMvc.perform(get("/voiture/20")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound)
				.andExpect { résultat ->
					Assertions.assertTrue(résultat.resolvedException is RessourceInexistanteException)
					Assertions.assertEquals("La voiture dont le ID est 20 n'est pas inscrite au service.", résultat.resolvedException?.message)
				}
	}


}














