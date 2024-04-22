package com.example.louemoncharbd.DAO

import com.example.louemoncharbd.DAO.DAO
import com.example.louemoncharbd.Modèle.Location
import com.example.louemoncharbd.Modèle.Utilisateur


interface LocationDAO : DAO<Location> {
    override fun chercherTous(): List<Location>
    override fun chercherParCode(id: String): Location?
    fun chercherParVoitureId(voitureId: Int): List<Location>

    fun chercherParUtilisateurId(uid: String): List<Location>
    override fun modifier(id: String, location: Location): Location?
    override fun effacer(code: String)

}
