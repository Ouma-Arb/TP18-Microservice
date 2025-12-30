package com.example.tp_18.controllers;

import com.example.tp_18.entities.Compte;
import com.example.tp_18.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comptes")
public class CompteRestController {

    @Autowired
    private CompteRepository compteRepository;

    @GetMapping
    public List<Compte> getAllComptes() {
        return compteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Compte getCompteById(@PathVariable String id) {
        return compteRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Compte saveCompte(@RequestBody Compte compte) {
        if (compte.getId() == null || compte.getId().isEmpty()) {
            compte.setId(UUID.randomUUID().toString());
        }
        return compteRepository.save(compte);
    }

    @DeleteMapping("/{id}")
    public void deleteCompte(@PathVariable String id) {
        compteRepository.deleteById(id);
    }

    @GetMapping("/stats")
    public CompteStats getStats() {
        List<Compte> comptes = compteRepository.findAll();
        int count = comptes.size();
        float sum = (float) comptes.stream().mapToDouble(Compte::getSolde).sum();
        float average = count > 0 ? sum / count : 0;
        return new CompteStats(count, sum, average);
    }

    // Inner class for stats
    public static class CompteStats {
        public int count;
        public float sum;
        public float average;

        public CompteStats(int count, float sum, float average) {
            this.count = count;
            this.sum = sum;
            this.average = average;
        }
    }
}

