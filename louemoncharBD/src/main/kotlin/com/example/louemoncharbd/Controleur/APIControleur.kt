package com.example.louemoncharbd.Controleur

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class APIControleur {
    @GetMapping("/")
    fun index() = "Service web du service de livraison"

}
