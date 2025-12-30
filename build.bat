@echo off
REM TP_18 Project Build and Run Script
REM This script builds the project and runs it

echo ========================================
echo TP_18 - gRPC Service Build Script
echo ========================================
echo.

echo [Step 1/3] Cleaning and building project...
echo Running: mvn clean install
call mvn clean install -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: Build failed!
    echo Please check the error messages above.
    pause
    exit /b 1
)

echo.
echo ========================================
echo Build completed successfully!
echo ========================================
echo.
echo Generated files are in:
echo   - target/generated-sources/protobuf/java/
echo   - target/generated-sources/protobuf/grpc-java/
echo.
echo [Step 2/3] Build Summary
echo   - gRPC stubs generated from CompteService.proto
echo   - All Java files compiled
echo   - JAR file created: target/TP_18-0.0.1-SNAPSHOT.jar
echo.

echo [Step 3/3] Ready to run!
echo.
echo To start the server, run:
echo   mvn spring-boot:run
echo.
echo OR run the JAR directly:
echo   java -jar target/TP_18-0.0.1-SNAPSHOT.jar
echo.
echo The server will start on:
echo   - gRPC Server: localhost:9090
echo   - HTTP Server: localhost:8080
echo   - H2 Console: http://localhost:8080/h2-console
echo.

pause

