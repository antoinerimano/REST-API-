package com.example.louemoncharbd.Modèle

import com.example.louemoncharbd.Modèle.Utilisateur
import com.example.louemoncharbd.Modèle.Voiture
import java.sql.Date

data class Location(val code : String?, val voiture_id: Int, val utilisateur_id: Int, val date: Date) {
}
