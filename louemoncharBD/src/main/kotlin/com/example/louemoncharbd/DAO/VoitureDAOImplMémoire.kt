package com.example.louemoncharbd.DAO

import com.example.louemoncharbd.Exeptions.ConflitAvecUneRessourceExistanteException
import com.example.louemoncharbd.Exeptions.RessourceInexistanteException
import com.example.louemoncharbd.GestionAccès.DroitAccèsInsuffisantException
import com.example.louemoncharbd.Modèle.Utilisateur
import com.example.louemoncharbd.Modèle.Voiture
import org.springframework.stereotype.Repository
import org.springframework.jdbc.core.JdbcTemplate


@Repository
class VoitureDAOImplMémoire(val db: JdbcTemplate, private val jdbcTemplate: JdbcTemplate) : VoitureDAO {
    override fun chercherTous(): MutableList<Voiture> = db.query("select * from voiture") { résultat, _ ->
        Voiture(
                résultat.getString("code"),
                résultat.getInt("code_propriétaire"),
                résultat.getString("marque"),
                résultat.getString("transmission"),
                résultat.getString("modèle"),
                résultat.getInt("année"),
                résultat.getString("image"),
                résultat.getString("etat"),
                résultat.getString("prix"),



        )
    }


    override fun chercherParCode(id: String): Voiture? {
        val parameters = arrayOf(id)
        return db.queryForObject(
                "select * from voiture where code = ?",
                parameters
        ) { résultat, _ ->
            Voiture(
                    résultat.getString("code"),
                    résultat.getInt("code_propriétaire"),
                    résultat.getString("marque"),
                    résultat.getString("transmission"),
                    résultat.getString("modèle"),
                    résultat.getInt("année"),
                    résultat.getString("image"),
                    résultat.getString("etat"),
                    résultat.getString("prix"),
            )
        }
    }





    override fun chercherIdAuth_a_partireIdVoiture(id: String): Utilisateur? {
        val parameters = arrayOf(id)
        return db.queryForObject(
            "SELECT u.* FROM voiture v " +
                    "JOIN utilisateur u ON v.code_propriétaire = u.code " +
                    "WHERE v.code = ?",
            parameters
        ) { résultat, _ ->
            Utilisateur(
                résultat.getString("code"),
                résultat.getInt("type_id"),
                résultat.getString("nom"),
                résultat.getString("prénom"),
                résultat.getString("courriel"),
                résultat.getInt("adresse_id"),
                résultat.getString("téléphone"),
                résultat.getString("NumPermis"),
                résultat.getString("id_auth0")
            )
        }
    }




    override fun chercherParPrix(prix: String): List<Voiture> {
        val parameters = arrayOf(prix)
        return db.query(
                "select * from voiture where prix = ?",
                parameters
        ) { résultat, _ ->
            Voiture(
                    résultat.getString("code"),
                    résultat.getInt("code_propriétaire"),
                    résultat.getString("marque"),
                    résultat.getString("transmission"),
                    résultat.getString("modèle"),
                    résultat.getInt("année"),
                    résultat.getString("image"),
                    résultat.getString("etat"),
                    résultat.getString("prix"),
            )
        }
    }

    override fun chercherParAnnee(annee: Int): List<Voiture> {
        val parameters = arrayOf(annee)
        return db.query(
                "select * from voiture where année = ?",
                parameters
        ) { résultat, _ ->
            Voiture(
                    résultat.getString("code"),
                    résultat.getInt("code_propriétaire"),
                    résultat.getString("marque"),
                    résultat.getString("transmission"),
                    résultat.getString("modèle"),
                    résultat.getInt("année"),
                    résultat.getString("image"),
                    résultat.getString("etat"),
                    résultat.getString("prix"),
            )
        }
    }

    override fun chercherParMarque(marque: String): List<Voiture> {
        val parameters = arrayOf(marque)
        return db.query(
                "select * from voiture where marque = ?",
                parameters
        ) { résultat, _ ->
            Voiture(
                    résultat.getString("code"),
                    résultat.getInt("code_propriétaire"),
                    résultat.getString("marque"),
                    résultat.getString("transmission"),
                    résultat.getString("modèle"),
                    résultat.getInt("année"),
                    résultat.getString("image"),
                    résultat.getString("etat"),
                    résultat.getString("prix"),
            )
        }
    }


    override fun chercherParTransmission(transmission: String): List<Voiture> {
        val parameters = arrayOf(transmission)
        return db.query(
                "select * from voiture where transmission = ?",
                parameters
        ) { résultat, _ ->
            Voiture(
                    résultat.getString("code"),
                    résultat.getInt("code_propriétaire"),
                    résultat.getString("marque"),
                    résultat.getString("transmission"),
                    résultat.getString("modèle"),
                    résultat.getInt("année"),
                    résultat.getString("image"),
                    résultat.getString("etat"),
                    résultat.getString("prix"),
            )
        }
    }


    override fun ajouter(voiture: Voiture): Voiture? {
        /*

    Voici le JSON a mettre dans Postman

        {
            "code": 5,  Oublier pas de changer le numero du code puisque c'est un auto-
            "code_propriétaire": 1,
            "marque": "Toyota",
            "transmission": "Manuel",
            "modèle": "Camry",
            "année": 2022,
            "image": "camry.jpg",
            "etat": "Disponible",
            "prix": "25000"
        }

*/

        val parameters = arrayOf(
                voiture.code,
                voiture.code_propriétaire,
                voiture.marque,
                voiture.transmission,
                voiture.modèle,
                voiture.année,
                voiture.image,
                voiture.etat,
                voiture.prix
        )


        val insertQuery = """
       INSERT INTO voiture (code, code_propriétaire, marque, transmission, modèle, année, image, etat, prix)
       VALUES (?,?, ?, ?, ?, ?, ?, ?, ?)
    """

        val numberOfRowsAffected = db.update(insertQuery, *parameters)

        return if (numberOfRowsAffected > 0) {
            voiture.copy(code = null)  // Assuming code is nullable in Voiture data class
        } else {
            throw ConflitAvecUneRessourceExistanteException("La voiture ${voiture.code} est déjà inscrit au service.")

        }
    }






    override fun effacer(id: String) {
        val deleteSql = "DELETE FROM voiture WHERE code = ?"

        val deletedRows = jdbcTemplate.update(deleteSql, id)

        if (deletedRows == 0) {
            throw RessourceInexistanteException("L'auto dont le ID est $id n'est pas inscrit au service.")
        }
    }


    override fun modifier(id: String, voiture: Voiture): Voiture? {
        val updateSql = """
        UPDATE voiture
        SET code_propriétaire = ?, 
            marque = ?, 
            transmission = ?, 
            modèle = ?, 
            année = ?, 
            image = ?, 
            etat = ?, 
            prix = ?
        WHERE code = ?
    """

        val updatedRows = jdbcTemplate.update(
                updateSql,
                voiture.code_propriétaire,
                voiture.marque,
                voiture.transmission,
                voiture.modèle,
                voiture.année,
                voiture.image,
                voiture.etat,
                voiture.prix,
                id
        )

        return voiture

    }




    override fun validerProprio(code_voiture: String, code_util: String?): Boolean {
        // Trouver qui est le propriétaire
        val voiture = chercherIdAuth_a_partireIdVoiture(code_voiture)

        if (voiture != null) {
            // Vérifier que le code utilisateur est le même que celui de la personne qui essaie d'effacer
            if (voiture.id_auth0 == code_util) {
                return true
            } else {
                throw DroitAccèsInsuffisantException("Vous n'avez pas la permission d'effectuer cette action.")
            }
        } else {
            throw RessourceInexistanteException("La voiture $code_voiture n'est pas inscrite au service.")
        }

        return false
    }







    /*

    override fun chercherParCode(id: String): Voiture? = SourceDonnées.auto.find{it.id.equals(id)}


    override fun ajouter(voiture: Voiture): Voiture? {
       val index = SourceDonnées.auto.indexOfFirst { it.id== voiture.id }
        if(index != -1){
            throw ConflitAvecUneRessourceExistanteException("Le restaurant ${voiture.id} est déjà inscrit au service.")
        }
        SourceDonnées.auto.add(voiture)
        return voiture

    }

    override fun modifier(id: String, auto: Voiture): Voiture? {
        val index = SourceDonnées.auto.indexOfFirst{it.id.equals(id)}

        if (index != -1) {
            SourceDonnées.auto.set(index, auto)
            return null
        } else {
            return ajouter(auto)
        }
    }

    override fun effacer(id: String) {
        val auto = SourceDonnées.auto.find{it.id.equals(id)}
        if(auto != null) {
            SourceDonnées.auto.remove(auto)
        } else {
            throw RessourceInexistanteException("L'auto dont le ID est $id n'est pas inscrit au service.")
        }
    }


    override fun chercherParPrix(prix: String): List<Voiture> {
        return SourceDonnées.auto.filter { it.prix.equals(prix, ignoreCase = true) }
    }

    override fun chercherParAnnee(annee: Int): List<Voiture> {
        return SourceDonnées.auto.filter { it.année == annee }
    }

    override fun chercherParMarque(marque: String): List<Voiture> {
        return SourceDonnées.auto.filter { it.marque.equals(marque, ignoreCase = true) }
    }


    override fun chercherParTransmission(transmission: String): List<Voiture> {
        return SourceDonnées.auto.filter { it.transmission?.equals(transmission) ?: true }

    }




*/

}