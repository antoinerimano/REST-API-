package com.example.louemoncharbd.Modèle

data class Voiture(var code: String?, val code_propriétaire: Int, val marque: String, val transmission: String, val modèle: String?, val année: Int, val image: String?, val etat: String, val prix: String, val proprio: List<Utilisateur> = mutableListOf()) {
}
