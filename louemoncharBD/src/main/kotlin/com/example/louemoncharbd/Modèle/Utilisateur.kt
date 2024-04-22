package com.example.louemoncharbd.Modèle

data class Utilisateur(var code:String?, val type_id :Int, val nom:String,val prénom : String, val courriel :String, val adresse_id: Int, val téléphone : String, val numPermis :String, val id_auth0: String) {
}

