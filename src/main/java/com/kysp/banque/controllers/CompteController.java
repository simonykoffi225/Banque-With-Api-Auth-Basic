package com.kysp.banque.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kysp.banque.models.Compte;
import com.kysp.banque.models.TransferRequest;
import com.kysp.banque.service.CompteService;

import lombok.AllArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/banque")
@AllArgsConstructor
public class CompteController {

   
    private CompteService compteService;

    @PostMapping("/ouvrir")
    public ResponseEntity<Compte> ouvrirUnCompte(@RequestBody Compte compte) {
        Compte nouveauCompte = compteService.ouvrirUnCompte(compte);
        return ResponseEntity.ok(nouveauCompte);
    }

    // @GetMapping("/comptes")
    // public ResponseEntity<List<Compte>> afficherTousLesComptes() {
    //     return ResponseEntity.ok(compteService.afficherTousLesComptes());
    // }

    @GetMapping("/comptes")
    public ResponseEntity<List<Compte>> afficherTousLesComptes() {
        List<Compte> comptes = compteService.afficherTousLesComptes();
        System.out.println("Comptes: " + comptes); // Log for debugging
        return ResponseEntity.ok(comptes);
    }


    @GetMapping("/compte/{accno}")
    public ResponseEntity<Compte> afficherUnCompte(@PathVariable String accno) {
        Compte compte = compteService.afficherUnCompte(accno);
        return compte != null ? ResponseEntity.ok(compte) : ResponseEntity.notFound().build();
    }

    @PostMapping("/depot/{accno}")
    public ResponseEntity<Compte> depot(@PathVariable String accno, @RequestParam long montant) {
        Compte compte = compteService.depot(accno, montant);
        return compte != null ? ResponseEntity.ok(compte) : ResponseEntity.notFound().build();
    }

    @PostMapping("/retrait/{accno}")
    public ResponseEntity<Compte> retrait(@PathVariable String accno, @RequestParam long montant) {
        Compte compte = compteService.retrait(accno, montant);
        return compte != null ? ResponseEntity.ok(compte) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/supprimer/{accno}")
    public ResponseEntity<Void> supprimerUnCompte(@PathVariable String accno) {
        compteService.supprimerUnCompte(accno);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/modifier")
    public ResponseEntity<Compte> modifierCompte(@RequestBody Compte compte) {
        Compte compteModifie = compteService.modifierCompte(compte);
        return ResponseEntity.ok(compteModifie);
    }


    @PostMapping("/transfert")
    public ResponseEntity<Compte> transfert(@RequestBody TransferRequest transferRequest) {
        Compte compte = compteService.transfert(
            transferRequest.getAccnoSource(),
            transferRequest.getAccnoDestination(),
            transferRequest.getMontant()
        );
        return compte != null ? ResponseEntity.ok(compte) : ResponseEntity.notFound().build();
    }
}

