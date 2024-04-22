package com.example.louemoncharbd.Service

import com.example.louemoncharbd.DAO.LocationDAO
import com.example.louemoncharbd.Modèle.Location
import org.springframework.stereotype.Service

@Service
class LocationService (val dao: LocationDAO){
    fun chercherTous(): List<Location> = dao.chercherTous()
    fun chercherParCode(code: String): Location? = dao.chercherParCode(code)


    fun retirerLocation(code: String) {

    }

    fun ajouterLocation(location: Location): Location? {
        dao.ajouter(location)
        return location
    }
    fun modifier(id: String, location: Location): Location? = dao.modifier(id, location)

    fun effacer(code: String) = dao.effacer(code)

}