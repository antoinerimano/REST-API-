package com.example.louemoncharbd.DAO

import com.example.louemoncharbd.DAO.UtilisateurDAO
import com.example.louemoncharbd.Modèle.Utilisateur
import com.example.louemoncharbd.Modèle.Voiture

interface UtilisateurDAO : DAO<Utilisateur> {
    override fun chercherTous(): List<Utilisateur>
    override fun chercherParCode(code: String): Utilisateur?

    fun chercherParNom(nom: String): List<Utilisateur>
    fun chercherParPrénom(prénom: String): List<Utilisateur>
    fun chercherParTéléphone(téléphone: Int): List<Utilisateur>
    fun chercherParNumPermis(numPermis: String): List<Utilisateur>

    fun chercherParCodeAuth0(id: String): Utilisateur?

    fun valider(code: String): Boolean

    override fun ajouter(utilisateur: Utilisateur): Utilisateur?

    override fun modifier(id: String, element: Utilisateur): Utilisateur?
    override fun effacer(id: String)




}
