package com.kysp.banque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kysp.banque.models.Compte;
import com.kysp.banque.repository.CompteRepository;

import java.util.List;

@Service
public class CompteService {

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean verifierMotDePasse(String plainTextPassword, String encodedPassword) {
        return passwordEncoder.matches(plainTextPassword, encodedPassword);
    }
    
    public Compte ouvrirUnCompte(Compte compte) {
        compte.setPassword(passwordEncoder.encode(compte.getPassword()));
        return compteRepository.save(compte);
    }

    public Compte afficherUnCompte(String accno) {
        return compteRepository.findByAccno(accno);
    }

    public List<Compte> afficherTousLesComptes() {
        return compteRepository.findAll();
    }

    public Compte depot(String accno, long montant) {
        Compte compte = compteRepository.findByAccno(accno);
        if (compte != null) {
            compte.setSolde(compte.getSolde() + montant);
            compteRepository.save(compte);
        }
        return compte;
    }

    public Compte retrait(String accno, long montant) {
        Compte compte = compteRepository.findByAccno(accno);
        if (compte != null && compte.getSolde() >= montant) {
            compte.setSolde(compte.getSolde() - montant);
            compteRepository.save(compte);
        }
        return compte;
    }

    public void supprimerUnCompte(String accno) {
        Compte compte = compteRepository.findByAccno(accno);
        if (compte != null) {
            compteRepository.delete(compte);
        }
    }

    public Compte modifierCompte(Compte updatedCompte) {
        Compte compte = compteRepository.findById(updatedCompte.getId()).orElse(null);
        if (compte != null) {
            compte.setEmail(updatedCompte.getEmail());
            compte.setAccno(updatedCompte.getAccno());
            compte.setAcType(updatedCompte.getAcType());
            compte.setUsername(updatedCompte.getUsername());
            compte.setSolde(updatedCompte.getSolde());
    
            if (!compte.getPassword().equals(updatedCompte.getPassword())) {
                compte.setPassword(passwordEncoder.encode(updatedCompte.getPassword()));
            }
    
            compte.setRole(updatedCompte.getRole());
            compteRepository.save(compte);
        }
        return compte;
    }
    

    public Compte transfert(String accnoSource, String accnoDestination, long montant) {
        Compte compteSource = compteRepository.findByAccno(accnoSource);
        Compte compteDestination = compteRepository.findByAccno(accnoDestination);
        if (compteSource != null && compteDestination != null && compteSource.getSolde() >= montant) {
            compteSource.setSolde(compteSource.getSolde() - montant);
            compteDestination.setSolde(compteDestination.getSolde() + montant);
            compteRepository.save(compteSource);
            compteRepository.save(compteDestination);
        }
        return compteSource;
    }
}

