// package com.kysp.banque.controllers;

// import java.security.Principal;

// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// public class LoginController {

//     @GetMapping("/user")
//     public String getUser() {
//         return "Bienvenue, user";
//     }

//     @GetMapping("/admin")
//     public String getAdmin() {
//         return "Bienvenue, admin";
//     }

//     @GetMapping("/info")
//     public String getUserInfo(Principal compte) {
//         if (compte instanceof UsernamePasswordAuthenticationToken) {
//             return "Authenticated as: " + compte.getName();
//         }
//         return "NA";
//     }
// }
