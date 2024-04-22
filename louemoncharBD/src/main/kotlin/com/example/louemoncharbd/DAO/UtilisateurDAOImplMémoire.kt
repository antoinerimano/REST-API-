package com.example.louemoncharbd.DAO

import com.example.louemoncharbd.Exeptions.ConflitAvecUneRessourceExistanteException
import com.example.louemoncharbd.Modèle.Location
import com.example.louemoncharbd.Modèle.Utilisateur
import com.example.louemoncharbd.Exeptions.RessourceInexistanteException
import com.example.louemoncharbd.GestionAccès.DroitAccèsInsuffisantException
import com.example.louemoncharbd.Modèle.Voiture
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.lang.Exception

@Repository
class UtilisateurDAOImplMémoire(val db: JdbcTemplate, private val jdbcTemplate: JdbcTemplate) : UtilisateurDAO {
    override fun chercherTous(): MutableList<Utilisateur> = db.query("select * from utilisateur") { résultat, _ ->
        Utilisateur(
            résultat.getString("code"),
            résultat.getInt("type_id"),
            résultat.getString("nom"),
            résultat.getString("prénom"),
            résultat.getString("courriel"),
            résultat.getInt("adresse_id"),
            résultat.getString("téléphone"),
            résultat.getString("numPermis"),
            résultat.getString("id_auth0")
        )
    }
    override fun chercherParCode(id: String): Utilisateur? {
        val parameters = arrayOf(id)
        return db.queryForObject(
            "select * from utilisateur where code = ?",
            parameters
        ){ résultat, _ ->
            Utilisateur(
                résultat.getString("code"),
                résultat.getInt("type_id"),
                résultat.getString("nom"),
                résultat.getString("prénom"),
                résultat.getString("courriel"),
                résultat.getInt("adresse_id"),
                résultat.getString("téléphone"),
                résultat.getString("numPermis"),
                résultat.getString("id_auth0")
            )
        }
    }

    override fun chercherParCodeAuth0(id: String): Utilisateur? {
        val parameters = arrayOf(id)
        return db.queryForObject(
            "select * from utilisateur where id_auth0 = ?",
            parameters
        ){ résultat, _ ->
            Utilisateur(
                résultat.getString("code"),
                résultat.getInt("type_id"),
                résultat.getString("nom"),
                résultat.getString("prénom"),
                résultat.getString("courriel"),
                résultat.getInt("adresse_id"),
                résultat.getString("téléphone"),
                résultat.getString("numPermis"),
                résultat.getString("id_auth0")
            )
        }
    }



    override fun chercherParNom(nom: String): List<Utilisateur> {
        val parameters = arrayOf(nom)
        return db.query(
                "select * from utilisateur where nom = ?",
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
                résultat.getString("numPermis"),
                résultat.getString("id_auth0")
            )
        }
    }

    override fun chercherParPrénom(prénom: String): List<Utilisateur> {
        val parameters = arrayOf(prénom)
        return db.query(
                "select * from utilisateur where prénom = ?",
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
                résultat.getString("numPermis"),
                résultat.getString("id_auth0")
            )
        }
    }

    override fun chercherParTéléphone(téléphone: Int): List<Utilisateur> {
        val parameters = arrayOf(téléphone)
        return db.query(
                "select * from utilisateur where téléphone = ?",
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
                résultat.getString("numPermis"),
                résultat.getString("id_auth0")
            )
        }
    }


    override fun chercherParNumPermis(numPermis: String): List<Utilisateur> {
        val parameters = arrayOf(numPermis)
        return db.query(
                "select * from utilisateur where numPermis = ?",
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
                résultat.getString("numPermis"),
                résultat.getString("id_auth0")
            )
        }
    }


    override fun ajouter(utilisateur: Utilisateur): Utilisateur? {
        /*

    Voici le JSON a mettre dans Postman

        {
            "code": 5,
            "type_id": 1,
            "nom": "Frank",
            "prénom": "Legros",
            "courriel": "frank@pop.net",
            "adresse_id": 2,
            "téléphone": "5146732322",
            "numPermis": "RIMA912032-3121"

        }

*/

        val parameters = arrayOf(


                utilisateur.code,
                utilisateur.type_id,
                utilisateur.nom,
                utilisateur.prénom,
                utilisateur.courriel,
                utilisateur.adresse_id,
                utilisateur.téléphone,
                utilisateur.numPermis,
                utilisateur.id_auth0



        )


        val insertQuery = """
       INSERT INTO utilisateur (code, type_id, nom, prénom, courriel, adresse_id, téléphone, numPermis, id_auth0)
       VALUES (?,?, ?, ?, ?, ?, ?, ?, ?)
    """

        val numberOfRowsAffected = db.update(insertQuery, *parameters)

        return if (numberOfRowsAffected > 0) {
            utilisateur.copy(code = null)  // Assuming code is nullable in Utilisateur data class
        } else {
            throw ConflitAvecUneRessourceExistanteException("Utilisateur ${utilisateur.code} est déjà inscrit au service.")

        }
    }






    override fun effacer(id: String) {
        val deleteSql = "DELETE FROM utilisateur WHERE code = ?"

        val deletedRows = jdbcTemplate.update(deleteSql, id)

        if (deletedRows == 0) {
            throw RessourceInexistanteException("L'Utilisateur dont le ID est $id n'est pas inscrit au service.")
        }
    }


    override fun modifier(id: String, utilisateur: Utilisateur): Utilisateur? {
        val updateSql = """
        UPDATE utilisateur
        SET 
            code = ?,
            type_id = ?,
            nom = ?, 
            prénom = ?, 
            courriel = ?, 
            téléphone = ?, 
            numPermis = ?,
            id_auth0 = ?
        WHERE code = ?
    """

        val updatedRows = jdbcTemplate.update(
                updateSql,


            utilisateur.code,
            utilisateur.type_id,
            utilisateur.nom,
            utilisateur.prénom,
            utilisateur.courriel,
            utilisateur.adresse_id,
            utilisateur.téléphone,
            utilisateur.numPermis,
            utilisateur.id_auth0
        )

        return utilisateur

    }

    override fun valider(code: String): Boolean{
        val personne = chercherParCode(id = code)

        if(personne != null) {

                return true

        } else {
            throw RessourceInexistanteException("L'utilisateur $code n'est pas inscrit au service.")
        }


    }









}