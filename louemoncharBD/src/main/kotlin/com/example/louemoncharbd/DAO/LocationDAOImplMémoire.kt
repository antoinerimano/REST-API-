package com.example.louemoncharbd.DAO

import com.example.louemoncharbd.Exeptions.ConflitAvecUneRessourceExistanteException
import com.example.louemoncharbd.Modèle.Location
import com.example.louemoncharbd.Modèle.Utilisateur
import com.example.louemoncharbd.Exeptions.RessourceInexistanteException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.lang.Exception

@Repository
class LocationDAOImplMémoire(val db: JdbcTemplate, private val jdbcTemplate: JdbcTemplate) : LocationDAO {
    override fun chercherTous(): MutableList<Location> = db.query("select * from reservation") { résultat, _ ->
        Location(
                résultat.getString("code"),
                résultat.getInt("utilisateur_id"),
                résultat.getInt("voiture_id"),
                résultat.getDate("date")

        )
    }


    override fun chercherParCode(id: String): Location? {
        val parameters = arrayOf(id)
        return db.queryForObject(
                "select * from reservation where code = ?",
                parameters
        ) { résultat, _ ->
            Location(
                    résultat.getString("code"),
                    résultat.getInt("utilisateur_id"),
                    résultat.getInt("voiture_id"),
                    résultat.getDate("date"))

        }
    }



    override fun chercherParVoitureId(voitureId: Int): List<Location> {
        val parameters = arrayOf(voitureId)
        return db.query(
                "select * from reservation where voiture_id = ?",
                parameters
        ) { résultat, _ ->
            Location(
                    résultat.getString("code"),
                    résultat.getInt("utilisateur_id"),
                    résultat.getInt("voiture_id"),
                    résultat.getDate("date"))

        }
    }

    override fun chercherParUtilisateurId(uid: String): List<Location> {
        val parameters = arrayOf(uid)
        return db.query(
                "select * from reservation where utilisateur_id = ?",
                parameters
        ) { résultat, _ ->
            Location(
                    résultat.getString("code"),
                    résultat.getInt("utilisateur_id"),
                    résultat.getInt("voiture_id"),
                    résultat.getDate("date"))

        }
    }


    override fun ajouter(location: Location): Location? {
        /*

    Voici le JSON a mettre dans Postman

        {
          "code":"7"
          "utilisateur_id":"6"
          "voiture_id":"1"
          "date": 83120312

        }

*/

        val parameters = arrayOf(


              location.code,
              location.utilisateur_id,
              location.voiture_id,
              location.date


        )


        val insertQuery = """
       INSERT INTO reservation(code,utilisateur_id, voiture_id, date)
       VALUES (? ,?, ?, ?)


    """

        val numberOfRowsAffected = db.update(insertQuery, *parameters)

        return if (numberOfRowsAffected > 0) {
            location.copy(code = null)  // Assuming code is nullable in Location data class
        } else {
            throw ConflitAvecUneRessourceExistanteException("La Location ${location.code} est déjà inscrit au service.")

        }
    }






    override fun effacer(id: String) {
        val deleteSql = "DELETE FROM reservation WHERE code = ?"

        val deletedRows = jdbcTemplate.update(deleteSql, id)

        if (deletedRows == 0) {
            throw RessourceInexistanteException("La location dont le ID est $id n'est pas inscrit au service.")
        }
    }


    override fun modifier(id: String, location: Location): Location? {
        val updateSql = """
        UPDATE reservation
        SET utilisateur_id = ?, 
            voiture_id = ?, 
            date = ?
        WHERE code = ?
    """

        val updatedRows = jdbcTemplate.update(
                updateSql,


                location.utilisateur_id,
                location.voiture_id,
                location.date,


                id
        )

        return location

    }
}

