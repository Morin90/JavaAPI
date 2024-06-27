package com.mycompany.formationspring.demo.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;


@RestController

public class HelloController {

    // fonction pour envoyer un objet en json
    /**
    @GetMapping("/envoyer-objet1")
    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "OK")
    public MonObjet envoyerObjet(@RequestParam("prenom") String prenom,
                                 @RequestParam("nom") String nom,
                                 @RequestParam("age")int age ){

        MonObjet objet = new MonObjet();

        objet.setNom(nom);
        objet.setPrenom(prenom);
        objet.setAge(age);

          return objet;

    }

    //fonction pour changer le status de la reponse de ok  a forbidden
    @GetMapping("envoyer-objet/method-level")
    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "OK")
    public String ok(MonObjet objet) {
        return "Class Level HTTP Status Overriden. The HTTP Status will be OK (CODE 403)\n";
    }**/
/**
    //fonction lire un param dans une requete http
    @GetMapping("/envoyer-param")
    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "OK")
    public String envoyerParam(@RequestParam(name = "monParam") String monParam) {

        return "Le paramètre reçu est : " + monParam;

    }**/


/**
@GetMapping("/envoyer-objet")
public ResponseEntity <MonObjet> envoyerObjetP(@RequestParam("prenom") String prenom,
                                              @RequestParam(value = "nom",defaultValue = "nanard",
                                                      required = false) String nom,
                                              @RequestParam("age") int age) {
    if (age < 18) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    MonObjet objet = new MonObjet();
    objet.setNom(nom);
    objet.setPrenom(prenom);
    objet.setAge(age);

    return ResponseEntity.ok(objet);
}
    @GetMapping("/envoyer-objet2")
    public  MonObjet envoyerObjetP2(HttpServletResponse response,  @RequestParam("prenom") String prenom,
                                    @RequestParam(value = "nom",defaultValue = "nanard", required = false) String nom,
                                    @RequestParam("age") int age) {
        if (age < 18) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return null;
        }

        MonObjet objet = new MonObjet();
        objet.setNom(nom);
        objet.setPrenom(prenom);
        objet.setAge(age);

        return objet;
    }


    // Les valeurs attendues après décodage de la chaîne Base64
    private static final String EXPECTED_USERNAME = "julien";
    private static final String EXPECTED_PASSWORD = "password";

    @GetMapping("/ouvrir")
    public ResponseEntity<String> authenticate(@RequestHeader("Authorization") String authorizationHeader) {
        // Vérifie si l'en-tête Authorization est présent et commence par "Basic "
        if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
            // Extrait la partie encodée en Base64 de l'en-tête
            String base64Credentials = authorizationHeader.substring("Basic ".length()).trim();
            // Décode la chaîne Base64
            byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
            String decodedString = new String(decodedBytes);

            // Sépare la chaîne décodée en parties utilisateur et mot de passe
            String[] credentials = decodedString.split("&");
            if (credentials.length == 2) {
                String[] userPart = credentials[0].split("=");
                String[] passwordPart = credentials[1].split("=");
                if (userPart.length == 2 && passwordPart.length == 2) {
                    String username = userPart[1];
                    String password = passwordPart[1];

                    // Vérifie les valeurs décodées
                    if (EXPECTED_USERNAME.equals(username) && EXPECTED_PASSWORD.equals(password)) {
                        return new ResponseEntity<>("Authentication successful", HttpStatus.OK);
                    }
                }
            }
        }
        // Retourne une erreur si l'authentification échoue
        return new ResponseEntity<>("Authentication failed", HttpStatus.UNAUTHORIZED);
    }**/
}
