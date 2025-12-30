# 🏦 TP_18 - Gestion des Comptes Bancaires avec gRPC et Spring Boot

![Java](https://img.shields.io/badge/Java-20-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0.5-brightgreen.svg)
![gRPC](https://img.shields.io/badge/gRPC-1.53.0-blue.svg)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)

Une application Spring Boot complète qui implémente un service gRPC pour la gestion des comptes bancaires, avec une interface web pour les tests et une API REST.

## 📋 Table des Matières

- [🏗️ Architecture du Projet](#️-architecture-du-projet)
- [🔧 Technologies Utilisées](#-technologies-utilisées)
- [⚡ Démarrage Rapide](#-démarrage-rapide)
- [🚀 Installation et Exécution](#-installation-et-exécution)
- [📡 APIs Disponibles](#-apis-disponibles)
- [🧪 Tests avec BloomRPC](#-tests-avec-bloomrpc)
- [🌐 Interface Web](#-interface-web)
- [📊 Base de Données](#-base-de-données)
- [🔍 Configuration](#-configuration)
- [🛠️ Résolution des Problèmes](#️-résolution-des-problèmes)

## 🏗️ Architecture du Projet

```
TP_18/
├── src/
│   ├── main/
│   │   ├── java/com/example/tp_18/
│   │   │   ├── controllers/
│   │   │   │   ├── CompteServiceImpl.java      # Service gRPC
│   │   │   │   ├── CompteRestController.java   # API REST
│   │   │   │   └── GrpcTestController.java     # Tests gRPC via REST
│   │   │   ├── entities/
│   │   │   │   ├── Compte.java                 # Entité JPA
│   │   │   │   └── TypeCompte.java             # Énumération
│   │   │   ├── repositories/
│   │   │   │   └── CompteRepository.java       # Repository JPA
│   │   │   ├── config/
│   │   │   │   └── DataInitializer.java        # Données initiales
│   │   │   ├── client/
│   │   │   │   └── CompteGrpcClient.java       # Client gRPC
│   │   │   └── Tp18Application.java            # Application principale
│   │   ├── proto/
│   │   │   └── CompteService.proto             # Définition Protocol Buffers
│   │   └── resources/
│   │       ├── application.properties          # Configuration
│   │       └── static/
│   │           └── index.html                  # Interface web
│   └── test/
├── image/
│   └── gett all comptes rpc.png               # Capture d'écran BloomRPC
├── pom.xml                                     # Configuration Maven
└── README.md
```

## 🔧 Technologies Utilisées

| Technologie | Version | Description |
|-------------|---------|-------------|
| **Java** | 20 | Langage de programmation |
| **Spring Boot** | 3.0.5 | Framework d'application |
| **gRPC** | 1.53.0 | Communication RPC |
| **Protocol Buffers** | 3.21.12 | Sérialisation des données |
| **Spring Data JPA** | 3.0.5 | Accès aux données |
| **H2 Database** | 2.1.214 | Base de données en mémoire |
| **Lombok** | 1.18.30 | Réduction du code boilerplate |
| **Maven** | 3.x | Gestionnaire de dépendances |

## ⚡ Démarrage Rapide

```bash
# Cloner le projet
git clone <votre-repo>
cd TP_18

# Compiler et démarrer
mvn clean spring-boot:run
```

**URLs importantes :**
- 🌐 **Interface Web :** http://localhost:8080
- 🔧 **API REST :** http://localhost:8080/api/comptes
- 📊 **Console H2 :** http://localhost:8080/h2-console
- 🚀 **Serveur gRPC :** localhost:9090

## 🚀 Installation et Exécution

### Prérequis
- JDK 20 ou supérieur
- Maven 3.6+
- Git

### Étapes d'installation

1. **Compiler le projet :**
```bash
mvn clean compile
```

2. **Générer les stubs gRPC :**
```bash
mvn protobuf:generate
```

3. **Construire l'application :**
```bash
mvn clean install -DskipTests
```

4. **Exécuter l'application :**
```bash
mvn spring-boot:run
```

### Exécution alternative
```bash
java -jar target/TP_18-0.0.1-SNAPSHOT.jar
```

## 📡 APIs Disponibles

### 🎯 Service gRPC - CompteService

Le service gRPC expose les méthodes suivantes sur le port **9090** :

#### 1. AllComptes
- **Description :** Récupère tous les comptes
- **Requête :** `GetAllComptesRequest` (vide)
- **Réponse :** `GetAllComptesResponse` (liste de comptes)

#### 2. CompteById
- **Description :** Récupère un compte par son ID
- **Requête :** `GetCompteByIdRequest { id: string }`
- **Réponse :** `GetCompteByIdResponse { compte: Compte }`

#### 3. TotalSolde
- **Description :** Statistiques des soldes (total, moyenne, nombre)
- **Requête :** `GetTotalSoldeRequest` (vide)
- **Réponse :** `GetTotalSoldeResponse { stats: SoldeStats }`

#### 4. SaveCompte
- **Description :** Crée un nouveau compte
- **Requête :** `SaveCompteRequest { compte: CompteRequest }`
- **Réponse :** `SaveCompteResponse { compte: Compte }`

### 🌐 API REST - /api/comptes

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| `GET` | `/api/comptes` | Tous les comptes |
| `GET` | `/api/comptes/{id}` | Compte par ID |
| `POST` | `/api/comptes` | Créer un compte |
| `DELETE` | `/api/comptes/{id}` | Supprimer un compte |
| `GET` | `/api/comptes/stats` | Statistiques |

### 🧪 API Test gRPC - /api/grpc-test

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| `GET` | `/api/grpc-test/all-comptes` | Test AllComptes via gRPC |
| `GET` | `/api/grpc-test/compte/{id}` | Test CompteById via gRPC |
| `GET` | `/api/grpc-test/stats` | Test TotalSolde via gRPC |
| `POST` | `/api/grpc-test/save-compte` | Test SaveCompte via gRPC |

## 🧪 Tests avec BloomRPC

Pour tester le service gRPC avec BloomRPC :

1. **Configuration de connexion :**
   - **Adresse :** `localhost:9090`
   - **Type :** `Plaintext` (non-sécurisé)

2. **Importer le fichier proto :**
   - Fichier : `src/main/proto/CompteService.proto`

3. **Exemple de test AllComptes :**

![Test BloomRPC](image/gett%20all%20comptes%20rpc.png)

### Exemples d'appels avec grpcurl

```bash
# Lister tous les services
grpcurl -plaintext localhost:9090 list

# Récupérer tous les comptes
grpcurl -plaintext localhost:9090 CompteService/AllComptes

# Créer un nouveau compte
grpcurl -plaintext -d '{
  "compte": {
    "solde": 1500.50,
    "dateCreation": "2025-12-18T10:30:00Z",
    "type": "COURANT"
  }
}' localhost:9090 CompteService/SaveCompte

# Obtenir les statistiques
grpcurl -plaintext localhost:9090 CompteService/TotalSolde
```

## 🌐 Interface Web

L'application inclut une interface web moderne accessible à http://localhost:8080

### Fonctionnalités de l'interface :
- ✅ **Gestion des comptes :** Ajouter, consulter, supprimer
- 📊 **Statistiques :** Visualisation des données
- 🔍 **Recherche :** Par ID de compte
- 🚀 **Tests gRPC :** Interface pour tester le serveur gRPC
- 📱 **Design responsive :** Compatible mobile

### Sections disponibles :
1. **Ajouter un Compte** - Formulaire de création
2. **Liste des Comptes** - Tableau avec actions
3. **Recherche par ID** - Fonction de recherche
4. **Tests gRPC** - Interface de test du serveur gRPC

## 📊 Base de Données

### Configuration H2
- **URL :** `jdbc:h2:mem:testdb`
- **Username :** `sa`
- **Password :** (vide)
- **Console :** http://localhost:8080/h2-console

### Modèle de données

```sql
CREATE TABLE compte (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    solde FLOAT NOT NULL,
    date_creation VARCHAR(255),
    type VARCHAR(255) CHECK (type IN ('COURANT', 'EPARGNE'))
);
```

### Données initiales
L'application initialise automatiquement 3 comptes de test :
- 1 compte courant : 1500.50€
- 1 compte épargne : 5000.00€
- 1 compte courant : 3200.75€

## 🔍 Configuration

### application.properties
```properties
# Configuration gRPC
grpc.server.port=9090
grpc.server.address=0.0.0.0

# Base de données H2
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.show-sql=true
```

### Personnalisation
- **Port gRPC :** Modifier `grpc.server.port`
- **Port Web :** Ajouter `server.port=8081`
- **Base de données :** Remplacer H2 par PostgreSQL/MySQL

## 🛠️ Résolution des Problèmes

### ❌ Problème : "DNS resolution failed" dans BloomRPC
**✅ Solution :**
- Utilisez `localhost:9090` au lieu d'un nom de domaine
- Vérifiez que l'application est démarrée
- Utilisez le type de connexion "Plaintext"

### ❌ Problème : "Port 8080 already in use"
**✅ Solution :**
```bash
# Tuer le processus utilisant le port
Get-Process -Id (Get-NetTCPConnection -LocalPort 8080).OwningProcess | Stop-Process -Force
```

### ❌ Problème : Classes gRPC non trouvées
**✅ Solution :**
```bash
mvn clean compile
# Ou régénérer les stubs
mvn protobuf:generate
```

### ❌ Problème : "Unexpected HTTP/1.x request" 
**✅ Explication :**
Cette erreur est **NORMALE** quand vous accédez à `localhost:9090` via un navigateur. 
gRPC n'est pas accessible via HTTP - utilisez BloomRPC ou l'interface web sur port 8080.

### ❌ Problème : Compilation Java 20
**✅ Solution :**
Vérifiez la variable `JAVA_HOME` :
```bash
echo $env:JAVA_HOME
java -version
```

## 📝 Modèle de Données Protocol Buffers

```protobuf
// Types de compte
enum TypeCompte {
    COURANT = 0;
    EPARGNE = 1;
}

// Entité principale
message Compte {
    string id = 1;
    float solde = 2;
    string dateCreation = 3;
    TypeCompte type = 4;
}

// Requête de création
message CompteRequest {
    float solde = 1;
    string dateCreation = 2;
    TypeCompte type = 3;
}

// Statistiques
message SoldeStats {
    int32 count = 1;
    float sum = 2;
    float average = 3;
}
```

## 🤝 Contribution

1. Fork le projet
2. Créer une branche feature (`git checkout -b feature/AmazingFeature`)
3. Commit les changements (`git commit -m 'Add AmazingFeature'`)
4. Push sur la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

## 📜 Licence

Distribué sous la licence MIT. Voir `LICENSE` pour plus d'informations.

## 👤 Auteur

**TP_18 Project**
- 📧 Email: [votre-email@example.com]
- 🔗 LinkedIn: [Votre LinkedIn]
- 🐙 GitHub: [Votre GitHub]

## 🙏 Remerciements

- [Spring Boot](https://spring.io/projects/spring-boot)
- [gRPC](https://grpc.io/)
- [Protocol Buffers](https://developers.google.com/protocol-buffers)
- [BloomRPC](https://github.com/bloomrpc/bloomrpc)

---

⭐ **N'hésitez pas à laisser une étoile si ce projet vous a aidé !**

