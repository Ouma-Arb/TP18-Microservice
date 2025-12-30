package com.example.tp_18.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ma.projet.grpc.stubs.*;

/**
 * Example gRPC client for testing the CompteService
 *
 * Note: This client will work after compiling the proto files.
 * Run: mvn clean compile
 *
 * To use this client:
 * 1. Start the server (mvn spring-boot:run)
 * 2. Run this client class
 */
public class CompteGrpcClient {

    public static void main(String[] args) {
        // Create a channel to connect to the server
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        // Create a blocking stub
        CompteServiceGrpc.CompteServiceBlockingStub stub =
                CompteServiceGrpc.newBlockingStub(channel);

        try {
            // Test 1: Get all comptes
            System.out.println("=== Test 1: Get All Comptes ===");
            GetAllComptesResponse allComptes = stub.allComptes(
                    GetAllComptesRequest.newBuilder().build()
            );
            System.out.println("Number of comptes: " + allComptes.getComptesCount());
            allComptes.getComptesList().forEach(compte ->
                    System.out.println("  - " + compte.getId() + ": " +
                            compte.getSolde() + " (" + compte.getType() + ")")
            );

            // Test 2: Save a new compte
            System.out.println("\n=== Test 2: Save New Compte ===");
            SaveCompteResponse saveResponse = stub.saveCompte(
                    SaveCompteRequest.newBuilder()
                            .setCompte(CompteRequest.newBuilder()
                                    .setSolde(2500.0f)
                                    .setDateCreation("2025-12-18")
                                    .setType(TypeCompte.EPARGNE)
                                    .build())
                            .build()
            );
            System.out.println("Saved compte: " + saveResponse.getCompte().getId());

            // Test 3: Get compte by ID
            System.out.println("\n=== Test 3: Get Compte By ID ===");
            String compteId = saveResponse.getCompte().getId();
            GetCompteByIdResponse compteById = stub.compteById(
                    GetCompteByIdRequest.newBuilder()
                            .setId(compteId)
                            .build()
            );
            System.out.println("Found compte: " + compteById.getCompte().getId() +
                    " with solde: " + compteById.getCompte().getSolde());

            // Test 4: Get total solde stats
            System.out.println("\n=== Test 4: Get Total Solde Stats ===");
            GetTotalSoldeResponse statsResponse = stub.totalSolde(
                    GetTotalSoldeRequest.newBuilder().build()
            );
            SoldeStats stats = statsResponse.getStats();
            System.out.println("Total count: " + stats.getCount());
            System.out.println("Total sum: " + stats.getSum());
            System.out.println("Average: " + stats.getAverage());

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Shutdown the channel
            channel.shutdown();
        }
    }
}

