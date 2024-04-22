package com.example.louemoncharbd.Service

import com.example.louemoncharbd.DAO.LocationDAO
import com.example.louemoncharbd.DAO.VoitureDAO
import com.example.louemoncharbd.GestionAccès.DroitAccèsInsuffisantException
import com.example.louemoncharbd.Modèle.Location
import com.example.louemoncharbd.Modèle.Voiture
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class VoitureService (val dao: VoitureDAO){
    fun chercherTous(): List<Voiture> = dao.chercherTous()
    fun chercherParCode(code: String): Voiture? = dao.chercherParCode(code)

    fun chercherParPrix(prix: String): List<Voiture> = dao.chercherParPrix(prix)

    fun chercherParMarque(marque: String): List<Voiture> = dao.chercherParMarque(marque)
    fun chercherParAnnee(annee: Int): List<Voiture> = dao.chercherParAnnee(annee)

    //fun chercherParDistance(distance: Int): List<Voiture> = dao.chercherParPrix(distance)

    fun chercherParTransmission(transmission: String): List<Voiture> = dao.chercherParTransmission(transmission)

    fun ajouter(voiture: Voiture): Voiture?=dao.ajouter(voiture)

  //  fun effacer(id: String) = dao.effacer(id)

    fun effacer(code_voiture: String, code_util: String) {
        if (dao.validerProprio(code_voiture, code_util)) {
            dao.effacer(code_voiture)

        } else {
            throw DroitAccèsInsuffisantException("Seuls le proprietaire de la voiture " + code_voiture + " peuvent le désinscrire. L'utilisateur " + code_util + " n'est pas inscrit comme proprietair de la voiture.")
        }
    }



    fun modifier(id: String, auto: Voiture, code_util: String): Voiture? {
        if (dao.validerProprio(id, code_util)) {
            return dao.modifier(id, auto)
        } else {
            throw DroitAccèsInsuffisantException("Seul le propriétaire de la voiture $id peut la modifier. L'utilisateur $code_util n'est pas inscrit comme propriétaire de la voiture.")
        }
    }


}