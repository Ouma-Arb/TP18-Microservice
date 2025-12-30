# Quick Start Guide - TP_18 gRPC Service

## 🚀 Quick Setup (3 Steps)

### Step 1: Build the Project
```bash
cd C:\Users\Dark\IdeaProjects\TP_18
mvn clean install
```

This will:
- Download all dependencies
- Generate gRPC stubs from `CompteService.proto`
- Compile the Java code
- Run tests (if any)

### Step 2: Run the Server
```bash
mvn spring-boot:run
```

Expected output:
```
✓ Database initialized with sample data
✓ Total comptes: 3
...
gRPC Server started, listening on port 9090
...
```

### Step 3: Test the Service

#### Option A: Using REST API (Browser/Postman)

Open browser: http://localhost:8080/api/comptes

**Available endpoints:**
- `GET http://localhost:8080/api/comptes` - Get all accounts
- `GET http://localhost:8080/api/comptes/{id}` - Get account by ID
- `POST http://localhost:8080/api/comptes` - Create new account
- `GET http://localhost:8080/api/comptes/stats` - Get statistics
- `DELETE http://localhost:8080/api/comptes/{id}` - Delete account

**Example POST request body:**
```json
{
    "solde": 1000.50,
    "dateCreation": "2025-12-18",
    "type": "COURANT"
}
```

#### Option B: Using gRPC Client

Run the test client:
```bash
mvn exec:java -Dexec.mainClass="com.example.tp_18.client.CompteGrpcClient"
```

#### Option C: Using grpcurl (Command Line)

Install grpcurl first, then:

```bash
# List all services
grpcurl -plaintext localhost:9090 list

# Get all comptes
grpcurl -plaintext localhost:9090 CompteService/AllComptes

# Save a new compte
grpcurl -plaintext -d '{
  "compte": {
    "solde": 1500.0,
    "dateCreation": "2025-12-18",
    "type": "EPARGNE"
  }
}' localhost:9090 CompteService/SaveCompte

# Get total solde stats
grpcurl -plaintext localhost:9090 CompteService/TotalSolde
```

#### Option D: Using BloomRPC (GUI)

1. Download BloomRPC
2. Import `src/main/proto/CompteService.proto`
3. Connect to `localhost:9090`
4. Select a method and send requests

## 📊 H2 Database Console

Access: http://localhost:8080/h2-console

**Connection details:**
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave empty)

## 🛠️ Common Commands

### Rebuild project
```bash
mvn clean compile
```

### Run without building
```bash
mvn spring-boot:run
```

### Build JAR and run
```bash
mvn clean package
java -jar target/TP_18-0.0.1-SNAPSHOT.jar
```

### Run tests
```bash
mvn test
```

## 📝 Project Components

✅ **Protocol Buffer Definition** - `src/main/proto/CompteService.proto`
✅ **gRPC Service Implementation** - `CompteServiceImpl.java`
✅ **JPA Entity** - `Compte.java`
✅ **Repository** - `CompteRepository.java`
✅ **REST Controller** - `CompteRestController.java` (for easy testing)
✅ **gRPC Client Example** - `CompteGrpcClient.java`
✅ **Data Initializer** - `DataInitializer.java` (creates sample data)

## 🔧 Configuration

**gRPC Port:** 9090 (configured in `application.properties`)  
**HTTP Port:** 8080 (default Spring Boot)  
**Database:** H2 in-memory

## 📚 Next Steps

1. **Modify the proto file** - Edit `src/main/proto/CompteService.proto`
2. **Regenerate stubs** - Run `mvn clean compile`
3. **Update service implementation** - Edit `CompteServiceImpl.java`
4. **Test with client** - Use REST API or gRPC client

## ⚠️ Troubleshooting

**Problem:** Cannot resolve CompteServiceGrpc
**Solution:** Run `mvn clean compile` first to generate the gRPC stubs

**Problem:** Port 9090 already in use
**Solution:** Change in `application.properties`:
```properties
grpc.server.port=9091
```

**Problem:** Dependencies not downloading
**Solution:** Run `mvn clean install -U` to force update

## 📧 Support

For questions or issues, refer to:
- README.md (detailed documentation)
- Spring gRPC Starter: https://github.com/yidongnan/grpc-spring-boot-starter
- gRPC Java: https://grpc.io/docs/languages/java/

---
Happy coding! 🎉

