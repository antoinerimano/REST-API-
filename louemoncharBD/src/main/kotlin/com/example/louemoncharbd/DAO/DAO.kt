package com.example.louemoncharbd.DAO

import com.example.louemoncharbd.Modèle.*
import com.example.louemoncharbd.DAO.DAO

interface DAO<T> {
    fun chercherTous(): List<T>
    fun chercherParCode(code: String): T?
    fun ajouter(element: T): T?

    fun modifier(id: String, element: T): T?
    fun effacer(id: String)


}
