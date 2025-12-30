package com.example.tp_18.controllers;

import com.example.tp_18.entities.TypeCompte;
import com.example.tp_18.repositories.CompteRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import ma.projet.grpc.stubs.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@GrpcService
public class CompteServiceImpl extends CompteServiceGrpc.CompteServiceImplBase {

    @Autowired
    private CompteRepository compteRepository;

    // Helper method to convert JPA entity to Protobuf message
    private ma.projet.grpc.stubs.Compte entityToProto(com.example.tp_18.entities.Compte entity) {
        return ma.projet.grpc.stubs.Compte.newBuilder()
                .setId(entity.getId())
                .setSolde(entity.getSolde())
                .setDateCreation(entity.getDateCreation())
                .setType(entity.getType() == TypeCompte.COURANT ?
                    ma.projet.grpc.stubs.TypeCompte.COURANT :
                    ma.projet.grpc.stubs.TypeCompte.EPARGNE)
                .build();
    }

    // Helper method to convert Protobuf message to JPA entity
    private com.example.tp_18.entities.Compte protoToEntity(ma.projet.grpc.stubs.CompteRequest proto, String id) {
        return new com.example.tp_18.entities.Compte(
                id,
                proto.getSolde(),
                proto.getDateCreation(),
                proto.getType() == ma.projet.grpc.stubs.TypeCompte.COURANT ?
                    TypeCompte.COURANT :
                    TypeCompte.EPARGNE
        );
    }

    @Override
    public void allComptes(GetAllComptesRequest request, StreamObserver<GetAllComptesResponse> responseObserver) {
        List<com.example.tp_18.entities.Compte> entities = compteRepository.findAll();
        List<ma.projet.grpc.stubs.Compte> protoComptes = entities.stream()
                .map(this::entityToProto)
                .collect(Collectors.toList());

        GetAllComptesResponse response = GetAllComptesResponse.newBuilder()
                .addAllComptes(protoComptes)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void compteById(GetCompteByIdRequest request, StreamObserver<GetCompteByIdResponse> responseObserver) {
        Optional<com.example.tp_18.entities.Compte> entityOpt = compteRepository.findById(request.getId());

        if (entityOpt.isPresent()) {
            ma.projet.grpc.stubs.Compte protoCompte = entityToProto(entityOpt.get());
            responseObserver.onNext(GetCompteByIdResponse.newBuilder()
                    .setCompte(protoCompte).build());
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new Throwable("Compte non trouvé"));
        }
    }

    @Override
    public void totalSolde(GetTotalSoldeRequest request, StreamObserver<GetTotalSoldeResponse> responseObserver) {
        List<com.example.tp_18.entities.Compte> entities = compteRepository.findAll();

        int count = entities.size();
        float sum = 0;
        for (com.example.tp_18.entities.Compte entity : entities) {
            sum += entity.getSolde();
        }
        float average = count > 0 ? sum / count : 0;

        SoldeStats stats = SoldeStats.newBuilder()
                .setCount(count)
                .setSum(sum)
                .setAverage(average)
                .build();

        responseObserver.onNext(GetTotalSoldeResponse.newBuilder().setStats(stats).build());
        responseObserver.onCompleted();
    }

    @Override
    public void saveCompte(SaveCompteRequest request, StreamObserver<SaveCompteResponse> responseObserver) {
        CompteRequest compteReq = request.getCompte();
        String id = UUID.randomUUID().toString();

        com.example.tp_18.entities.Compte entity = protoToEntity(compteReq, id);
        com.example.tp_18.entities.Compte savedEntity = compteRepository.save(entity);

        ma.projet.grpc.stubs.Compte protoCompte = entityToProto(savedEntity);

        responseObserver.onNext(SaveCompteResponse.newBuilder().setCompte(protoCompte).build());
        responseObserver.onCompleted();
    }
}



