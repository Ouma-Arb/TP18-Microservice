package com.example.tp_18.controllers;

import com.example.tp_18.entities.Compte;
import com.example.tp_18.entities.TypeCompte;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ma.projet.grpc.stubs.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/grpc-test")
public class GrpcTestController {

    private CompteServiceGrpc.CompteServiceBlockingStub getStub() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        return CompteServiceGrpc.newBlockingStub(channel);
    }

    @GetMapping("/all-comptes")
    public Map<String, Object> getAllComptes() {
        try {
            CompteServiceGrpc.CompteServiceBlockingStub stub = getStub();
            GetAllComptesRequest request = GetAllComptesRequest.newBuilder().build();
            GetAllComptesResponse response = stub.allComptes(request);

            List<Map<String, Object>> comptes = new ArrayList<>();
            for (ma.projet.grpc.stubs.Compte compte : response.getComptesList()) {
                Map<String, Object> compteMap = new HashMap<>();
                compteMap.put("id", compte.getId());
                compteMap.put("solde", compte.getSolde());
                compteMap.put("dateCreation", compte.getDateCreation());
                compteMap.put("type", compte.getType().name());
                comptes.add(compteMap);
            }

            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("comptes", comptes);
            return result;
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("error", e.getMessage());
            return result;
        }
    }

    @GetMapping("/compte/{id}")
    public Map<String, Object> getCompteById(@PathVariable String id) {
        try {
            CompteServiceGrpc.CompteServiceBlockingStub stub = getStub();
            GetCompteByIdRequest request = GetCompteByIdRequest.newBuilder()
                    .setId(id)
                    .build();
            GetCompteByIdResponse response = stub.compteById(request);

            ma.projet.grpc.stubs.Compte compte = response.getCompte();
            Map<String, Object> compteMap = new HashMap<>();
            compteMap.put("id", compte.getId());
            compteMap.put("solde", compte.getSolde());
            compteMap.put("dateCreation", compte.getDateCreation());
            compteMap.put("type", compte.getType().name());

            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("compte", compteMap);
            return result;
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("error", e.getMessage());
            return result;
        }
    }

    @GetMapping("/stats")
    public Map<String, Object> getTotalSolde() {
        try {
            CompteServiceGrpc.CompteServiceBlockingStub stub = getStub();
            GetTotalSoldeRequest request = GetTotalSoldeRequest.newBuilder().build();
            GetTotalSoldeResponse response = stub.totalSolde(request);

            SoldeStats stats = response.getStats();
            Map<String, Object> statsMap = new HashMap<>();
            statsMap.put("count", stats.getCount());
            statsMap.put("sum", stats.getSum());
            statsMap.put("average", stats.getAverage());

            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("stats", statsMap);
            return result;
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("error", e.getMessage());
            return result;
        }
    }

    @PostMapping("/save-compte")
    public Map<String, Object> saveCompte(@RequestBody Map<String, Object> compteData) {
        try {
            CompteServiceGrpc.CompteServiceBlockingStub stub = getStub();

            float solde = ((Number) compteData.get("solde")).floatValue();
            String dateCreation = (String) compteData.get("dateCreation");
            String typeStr = (String) compteData.get("type");

            ma.projet.grpc.stubs.TypeCompte type = typeStr.equals("COURANT") ?
                ma.projet.grpc.stubs.TypeCompte.COURANT :
                ma.projet.grpc.stubs.TypeCompte.EPARGNE;

            CompteRequest compteRequest = CompteRequest.newBuilder()
                    .setSolde(solde)
                    .setDateCreation(dateCreation)
                    .setType(type)
                    .build();

            SaveCompteRequest request = SaveCompteRequest.newBuilder()
                    .setCompte(compteRequest)
                    .build();

            SaveCompteResponse response = stub.saveCompte(request);

            ma.projet.grpc.stubs.Compte compte = response.getCompte();
            Map<String, Object> compteMap = new HashMap<>();
            compteMap.put("id", compte.getId());
            compteMap.put("solde", compte.getSolde());
            compteMap.put("dateCreation", compte.getDateCreation());
            compteMap.put("type", compte.getType().name());

            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("compte", compteMap);
            return result;
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("error", e.getMessage());
            return result;
        }
    }
}

