# Testing Guide - TP_18 gRPC Service

## 🧪 Complete Testing Guide

This guide provides comprehensive testing instructions for the TP_18 gRPC service.

---

## Prerequisites

1. **Build the project first:**
   ```bash
   mvn clean install
   ```

2. **Start the server:**
   ```bash
   mvn spring-boot:run
   ```
   
   Or use the convenience script:
   ```bash
   run.bat
   ```

---

## Test Method 1: REST API (Easiest)

### Using PowerShell

```powershell
# Get all accounts
Invoke-RestMethod -Uri "http://localhost:8080/api/comptes" -Method Get

# Create a new account
$body = @{
    solde = 1500.75
    dateCreation = "2025-12-18"
    type = "COURANT"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/comptes" -Method Post -Body $body -ContentType "application/json"

# Get statistics
Invoke-RestMethod -Uri "http://localhost:8080/api/comptes/stats" -Method Get
```

### Using Browser

Simply open your browser and visit:

- **Get all accounts:** http://localhost:8080/api/comptes
- **Get statistics:** http://localhost:8080/api/comptes/stats

### Using curl

```bash
# Get all accounts
curl http://localhost:8080/api/comptes

# Create account (Windows PowerShell)
curl -X POST http://localhost:8080/api/comptes -H "Content-Type: application/json" -d "{\"solde\":1000.0,\"dateCreation\":\"2025-12-18\",\"type\":\"COURANT\"}"

# Get statistics
curl http://localhost:8080/api/comptes/stats
```

---

## Test Method 2: Java gRPC Client

Run the included test client:

```bash
mvn exec:java -Dexec.mainClass="com.example.tp_18.client.CompteGrpcClient"
```

**Expected Output:**
```
=== Test 1: Get All Comptes ===
Number of comptes: 3
  - [UUID]: 1500.5 (COURANT)
  - [UUID]: 5000.0 (EPARGNE)
  - [UUID]: 3200.75 (COURANT)

=== Test 2: Save New Compte ===
Saved compte: [UUID]

=== Test 3: Get Compte By ID ===
Found compte: [UUID] with solde: 2500.0

=== Test 4: Get Total Solde Stats ===
Total count: 4
Total sum: 12201.25
Average: 3050.3125
```

---

## Test Method 3: grpcurl (Command Line)

### Install grpcurl

**Windows (using Chocolatey):**
```bash
choco install grpcurl
```

**Or download from:** https://github.com/fullstorydev/grpcurl/releases

### Test Commands

```bash
# List all available services
grpcurl -plaintext localhost:9090 list

# List all methods in CompteService
grpcurl -plaintext localhost:9090 list CompteService

# Describe the CompteService
grpcurl -plaintext localhost:9090 describe CompteService

# 1. Get all accounts
grpcurl -plaintext localhost:9090 CompteService/AllComptes

# 2. Save a new account (COURANT)
grpcurl -plaintext -d "{\"compte\":{\"solde\":1000.0,\"dateCreation\":\"2025-12-18\",\"type\":\"COURANT\"}}" localhost:9090 CompteService/SaveCompte

# 3. Save a new account (EPARGNE)
grpcurl -plaintext -d "{\"compte\":{\"solde\":5000.0,\"dateCreation\":\"2025-12-18\",\"type\":\"EPARGNE\"}}" localhost:9090 CompteService/SaveCompte

# 4. Get account by ID (replace YOUR_ID with actual ID from previous response)
grpcurl -plaintext -d "{\"id\":\"YOUR_ID\"}" localhost:9090 CompteService/CompteById

# 5. Get statistics
grpcurl -plaintext localhost:9090 CompteService/TotalSolde
```

---

## Test Method 4: BloomRPC (GUI Tool)

### Setup

1. Download BloomRPC: https://github.com/bloomrpc/bloomrpc/releases
2. Install and open BloomRPC
3. Click "Import Paths" and add: `C:\Users\Dark\IdeaProjects\TP_18\src\main\proto`
4. Import `CompteService.proto`
5. Set server address: `localhost:9090`
6. Disable TLS/SSL

### Test Scenarios

#### Scenario 1: Get All Comptes
- Select: `CompteService.AllComptes`
- Request body: `{}`
- Click "Play" button

#### Scenario 2: Save Compte
- Select: `CompteService.SaveCompte`
- Request body:
  ```json
  {
    "compte": {
      "solde": 2500.5,
      "dateCreation": "2025-12-18",
      "type": "EPARGNE"
    }
  }
  ```
- Click "Play" button

#### Scenario 3: Get Compte By ID
- Select: `CompteService.CompteById`
- Request body:
  ```json
  {
    "id": "paste-id-from-previous-response"
  }
  ```
- Click "Play" button

#### Scenario 4: Get Total Solde
- Select: `CompteService.TotalSolde`
- Request body: `{}`
- Click "Play" button

---

## Test Method 5: H2 Database Console

### Access H2 Console

1. Open browser: http://localhost:8080/h2-console
2. Use these settings:
   - **JDBC URL:** `jdbc:h2:mem:testdb`
   - **Username:** `sa`
   - **Password:** (leave empty)
3. Click "Connect"

### SQL Queries

```sql
-- View all accounts
SELECT * FROM COMPTE;

-- Count accounts by type
SELECT type, COUNT(*) as count, SUM(solde) as total_solde
FROM COMPTE
GROUP BY type;

-- Get total statistics
SELECT 
    COUNT(*) as total_count,
    SUM(solde) as total_sum,
    AVG(solde) as average_solde
FROM COMPTE;

-- Insert new account
INSERT INTO COMPTE (id, solde, date_creation, type) 
VALUES ('test-123', 1000.0, '2025-12-18', 'COURANT');
```

---

## Test Scenarios

### Scenario 1: Basic CRUD Operations

1. **List all accounts** (should show 3 initial accounts)
   ```bash
   curl http://localhost:8080/api/comptes
   ```

2. **Create new account**
   ```powershell
   $body = @{solde=1000.0; dateCreation="2025-12-18"; type="COURANT"} | ConvertTo-Json
   Invoke-RestMethod -Uri "http://localhost:8080/api/comptes" -Method Post -Body $body -ContentType "application/json"
   ```

3. **Verify account was created** (should now show 4 accounts)
   ```bash
   curl http://localhost:8080/api/comptes
   ```

4. **Get account by ID** (replace {id} with actual ID)
   ```bash
   curl http://localhost:8080/api/comptes/{id}
   ```

### Scenario 2: Statistics Verification

1. **Get current statistics**
   ```bash
   curl http://localhost:8080/api/comptes/stats
   ```
   
   Expected output format:
   ```json
   {
     "count": 3,
     "sum": 9701.25,
     "average": 3233.75
   }
   ```

2. **Add new account and verify stats change**

3. **Use gRPC to get same stats**
   ```bash
   grpcurl -plaintext localhost:9090 CompteService/TotalSolde
   ```

### Scenario 3: Type-based Testing

1. **Create COURANT account**
   ```json
   {"solde": 500.0, "dateCreation": "2025-12-18", "type": "COURANT"}
   ```

2. **Create EPARGNE account**
   ```json
   {"solde": 10000.0, "dateCreation": "2025-12-18", "type": "EPARGNE"}
   ```

3. **Verify in H2 Console**
   ```sql
   SELECT type, COUNT(*), AVG(solde) FROM COMPTE GROUP BY type;
   ```

---

## Performance Testing

### Load Test with PowerShell

```powershell
# Create 100 accounts
1..100 | ForEach-Object {
    $body = @{
        solde = Get-Random -Minimum 100 -Maximum 10000
        dateCreation = "2025-12-18"
        type = if($_ % 2 -eq 0) {"COURANT"} else {"EPARGNE"}
    } | ConvertTo-Json
    
    Invoke-RestMethod -Uri "http://localhost:8080/api/comptes" -Method Post -Body $body -ContentType "application/json"
    Write-Host "Created account $_"
}

# Get statistics
Invoke-RestMethod -Uri "http://localhost:8080/api/comptes/stats" -Method Get
```

---

## Troubleshooting Tests

### Issue: Connection Refused

**Symptoms:** Cannot connect to localhost:9090 or localhost:8080

**Solutions:**
1. Check if server is running: `netstat -an | findstr "9090"`
2. Check server logs for errors
3. Restart the server: `mvn spring-boot:run`

### Issue: Empty Response from AllComptes

**Symptoms:** GetAllComptes returns empty list

**Explanation:** 
- gRPC service uses in-memory ConcurrentHashMap
- Data is NOT persisted to database
- Initial data is in H2 database only

**Solution:** Add accounts through gRPC SaveCompte first

### Issue: Account Not Found

**Symptoms:** CompteById returns "Compte non trouvé"

**Cause:** ID doesn't exist in the in-memory map

**Solution:** 
1. Use SaveCompte to create account first
2. Copy the returned ID
3. Use that ID in CompteById request

---

## Expected Test Results

### Initial State (3 accounts)
- **Count:** 3
- **Total Sum:** ~9701.25
- **Average:** ~3233.75

### After Adding 1 Account (1000.0 COURANT)
- **Count:** 4
- **Total Sum:** ~10701.25
- **Average:** ~2675.31

---

## Automated Test Script

Create a file `test-all.ps1`:

```powershell
Write-Host "=== TP_18 Automated Test Suite ===" -ForegroundColor Green

# Test 1: REST API
Write-Host "`nTest 1: REST API - Get All Comptes" -ForegroundColor Yellow
$comptes = Invoke-RestMethod -Uri "http://localhost:8080/api/comptes"
Write-Host "Found $($comptes.Count) comptes" -ForegroundColor Cyan

# Test 2: Create Account
Write-Host "`nTest 2: REST API - Create Account" -ForegroundColor Yellow
$newCompte = @{solde=1500; dateCreation="2025-12-18"; type="COURANT"} | ConvertTo-Json
$created = Invoke-RestMethod -Uri "http://localhost:8080/api/comptes" -Method Post -Body $newCompte -ContentType "application/json"
Write-Host "Created compte with ID: $($created.id)" -ForegroundColor Cyan

# Test 3: Get Statistics
Write-Host "`nTest 3: REST API - Get Statistics" -ForegroundColor Yellow
$stats = Invoke-RestMethod -Uri "http://localhost:8080/api/comptes/stats"
Write-Host "Stats - Count: $($stats.count), Sum: $($stats.sum), Avg: $($stats.average)" -ForegroundColor Cyan

Write-Host "`n=== All Tests Completed ===" -ForegroundColor Green
```

Run with: `.\test-all.ps1`

---

## Summary

✅ **5 Testing Methods Available:**
1. REST API (easiest for quick tests)
2. Java gRPC Client (included in project)
3. grpcurl (command-line gRPC testing)
4. BloomRPC (GUI gRPC testing)
5. H2 Console (database verification)

✅ **All test scenarios covered:**
- CRUD operations
- Statistics calculation
- Type-based filtering
- Performance testing
- Error handling

**Happy Testing! 🎉**

