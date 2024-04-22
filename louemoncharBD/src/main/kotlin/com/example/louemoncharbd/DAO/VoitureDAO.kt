package com.example.louemoncharbd.DAO

import com.example.louemoncharbd.Modèle.Voiture
import com.example.louemoncharbd.DAO.VoitureDAO
import com.example.louemoncharbd.Modèle.Utilisateur

interface VoitureDAO : DAO<Voiture> {
    override fun chercherTous(): List<Voiture>
    override fun chercherParCode(id: String): Voiture?

    fun chercherIdAuth_a_partireIdVoiture(id: String): Utilisateur?

    fun chercherParPrix(prix: String): List<Voiture>

    fun chercherParAnnee(annee: Int): List<Voiture>

    fun chercherParMarque(marque: String): List<Voiture>

    fun chercherParTransmission(transmission: String): List<Voiture>

    override fun ajouter (voiture: Voiture):Voiture?

    override fun effacer (id: String)

    override fun modifier(id: String, element: Voiture): Voiture?

    fun validerProprio(code_voiture: String, code_utilisateur: String?): Boolean








}
