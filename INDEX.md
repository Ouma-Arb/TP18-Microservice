# 📖 TP_18 Documentation Index

Welcome to TP_18 - A Spring Boot gRPC Service for Bank Account Management!

---

## 🚀 Quick Navigation

### 🏃 Getting Started (Start Here!)
1. **[QUICKSTART.md](QUICKSTART.md)** - Get up and running in 3 steps
   - Build instructions
   - Run commands
   - Quick testing examples

### 📚 Core Documentation
2. **[README.md](README.md)** - Complete project documentation
   - Full project overview
   - Architecture details
   - Technology stack
   - API reference
   - Configuration guide

3. **[PROJECT_SUMMARY.md](PROJECT_SUMMARY.md)** - Project summary and status
   - What's implemented
   - Architecture diagrams
   - Key features
   - Next steps

### 🧪 Testing
4. **[TESTING_GUIDE.md](TESTING_GUIDE.md)** - Comprehensive testing guide
   - 5 different testing methods
   - Test scenarios
   - Example commands
   - Automated test scripts

### ✅ Verification
5. **[CHECKLIST.md](CHECKLIST.md)** - Complete project checklist
   - All implemented components
   - Build verification
   - Testing checklist
   - Deployment status

---

## 📂 Project Structure

```
TP_18/
├── 📄 Documentation
│   ├── README.md              # Main documentation
│   ├── QUICKSTART.md          # Quick start guide
│   ├── TESTING_GUIDE.md       # Testing instructions
│   ├── PROJECT_SUMMARY.md     # Project overview
│   ├── CHECKLIST.md           # Completion checklist
│   ├── INDEX.md               # This file
│   └── HELP.md                # Spring Boot help
│
├── 🔧 Build & Run Scripts
│   ├── build.bat              # Build script (Windows)
│   ├── run.bat                # Run script (Windows)
│   ├── mvnw                   # Maven wrapper (Unix)
│   └── mvnw.cmd               # Maven wrapper (Windows)
│
├── 📝 Configuration
│   └── pom.xml                # Maven configuration
│
└── 📁 src/
    ├── main/
    │   ├── proto/
    │   │   └── CompteService.proto       # gRPC service definition
    │   ├── java/com/example/tp_18/
    │   │   ├── Tp18Application.java      # Main application
    │   │   ├── controllers/
    │   │   │   ├── CompteServiceImpl.java    # gRPC service
    │   │   │   └── CompteRestController.java # REST API
    │   │   ├── entities/
    │   │   │   ├── Compte.java               # JPA entity
    │   │   │   └── TypeCompte.java           # Enum
    │   │   ├── repositories/
    │   │   │   └── CompteRepository.java     # JPA repository
    │   │   ├── config/
    │   │   │   └── DataInitializer.java      # Sample data
    │   │   └── client/
    │   │       └── CompteGrpcClient.java     # Test client
    │   └── resources/
    │       └── application.properties        # App config
    └── test/
        └── ...
```

---

## 🎯 Where to Start?

### I'm completely new to this project
👉 Start with **[QUICKSTART.md](QUICKSTART.md)**

### I want to understand the architecture
👉 Read **[README.md](README.md)** and **[PROJECT_SUMMARY.md](PROJECT_SUMMARY.md)**

### I want to test the application
👉 Follow **[TESTING_GUIDE.md](TESTING_GUIDE.md)**

### I want to verify everything is complete
👉 Check **[CHECKLIST.md](CHECKLIST.md)**

### I need help with Spring Boot
👉 See **[HELP.md](HELP.md)**

---

## ⚡ Quick Commands

### Build the Project
```bash
mvn clean install
```
Or use: `build.bat`

### Run the Application
```bash
mvn spring-boot:run
```
Or use: `run.bat`

### Test REST API
```bash
curl http://localhost:8080/api/comptes
```

### Test gRPC Client
```bash
mvn exec:java -Dexec.mainClass="com.example.tp_18.client.CompteGrpcClient"
```

---

## 🔗 Important URLs

Once the application is running:

| Service | URL | Description |
|---------|-----|-------------|
| **REST API** | http://localhost:8080/api/comptes | RESTful endpoints |
| **H2 Console** | http://localhost:8080/h2-console | Database console |
| **gRPC Server** | localhost:9090 | gRPC service |

---

## 📊 Technology Stack

- **Java:** 20
- **Spring Boot:** 3.0.5
- **gRPC:** 1.53.0
- **Protocol Buffers:** 3.21.12
- **Database:** H2 (in-memory)
- **Build Tool:** Maven

---

## 🎓 Learning Resources

### Protocol Buffers
- Official Guide: https://developers.google.com/protocol-buffers
- Language Guide: https://developers.google.com/protocol-buffers/docs/proto3

### gRPC
- Official Documentation: https://grpc.io/docs/
- Java Tutorial: https://grpc.io/docs/languages/java/

### Spring Boot with gRPC
- GitHub Repository: https://github.com/yidongnan/grpc-spring-boot-starter
- Documentation: https://yidongnan.github.io/grpc-spring-boot-starter/

---

## 🆘 Common Issues

### Problem: Cannot resolve CompteServiceGrpc
**Solution:** Run `mvn clean compile` to generate gRPC stubs

### Problem: Port 9090 already in use
**Solution:** Change port in `application.properties`:
```properties
grpc.server.port=9091
```

### Problem: Dependencies not downloading
**Solution:** Run `mvn clean install -U`

For more troubleshooting, see **[TESTING_GUIDE.md](TESTING_GUIDE.md#troubleshooting-tests)**

---

## 📞 Support

For questions or issues:
1. Check the documentation files listed above
2. Review the TESTING_GUIDE.md troubleshooting section
3. Check Spring Boot logs for error messages

---

## 🎉 Project Status

**STATUS: ✅ COMPLETE AND READY TO USE**

All components implemented, documented, and tested!

---

## 📝 Document Change Log

| Date | Version | Changes |
|------|---------|---------|
| 2025-12-18 | 1.0 | Initial project completion |

---

**Happy Coding! 🚀**

For detailed information, please refer to the specific documentation files linked above.

