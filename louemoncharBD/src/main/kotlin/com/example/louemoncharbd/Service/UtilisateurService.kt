package com.example.louemoncharbd.Service

import com.example.louemoncharbd.DAO.UtilisateurDAO
import com.example.louemoncharbd.GestionAccès.DroitAccèsInsuffisantException
import com.example.louemoncharbd.Modèle.Utilisateur
import org.springframework.stereotype.Service

@Service
class UtilisateurService (val dao: UtilisateurDAO) {
    fun chercherTous(): List<Utilisateur> = dao.chercherTous()
    fun chercherParCode(code: String): Utilisateur? = dao.chercherParCode(code)

    fun chercherParNom(nom: String): List<Utilisateur> = dao.chercherParNom(nom)

    fun chercherParPrénom(prenom: String): List<Utilisateur> = dao.chercherParPrénom(prenom)

    fun chercherParTelephone(telephone: Int): List<Utilisateur> = dao.chercherParTéléphone(telephone)

    fun chercherParNumPermis(numpermis: String): List<Utilisateur> = dao.chercherParNumPermis(numpermis)
    fun retirerUtilisateur(code: String) {

    }
    fun ajouter (user:Utilisateur):Utilisateur? = dao.ajouter(user)
    fun modifier(code: String, utilisateur: Utilisateur): Utilisateur? {
        if (dao.valider(code)) {
            dao.modifier(code, utilisateur)
        } else {
            throw DroitAccèsInsuffisantException("Vous n'avez pas les privilèges suffisants")
        }
        return utilisateur
    }








        fun ajouterUtilisateur(utilisateur: Utilisateur): Utilisateur? = dao.ajouter(utilisateur)

        fun effacer(code: String, code_util: String) {
            if (dao.valider(code)) {
                dao.effacer(code)
            } else {
                throw DroitAccèsInsuffisantException("Vous n'avez pas les privilèges suffisants")
            }

        }


}
