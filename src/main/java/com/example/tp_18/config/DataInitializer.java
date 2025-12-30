package com.example.tp_18.config;

import com.example.tp_18.entities.Compte;
import com.example.tp_18.entities.TypeCompte;
import com.example.tp_18.repositories.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.UUID;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(CompteRepository repository) {
        return args -> {
            // Add sample data
            repository.save(new Compte(
                    UUID.randomUUID().toString(),
                    1500.50f,
                    LocalDate.now().toString(),
                    TypeCompte.COURANT
            ));

            repository.save(new Compte(
                    UUID.randomUUID().toString(),
                    5000.00f,
                    LocalDate.now().toString(),
                    TypeCompte.EPARGNE
            ));

            repository.save(new Compte(
                    UUID.randomUUID().toString(),
                    3200.75f,
                    LocalDate.now().toString(),
                    TypeCompte.COURANT
            ));

            System.out.println("✓ Database initialized with sample data");
            System.out.println("✓ Total comptes: " + repository.count());
        };
    }
}

