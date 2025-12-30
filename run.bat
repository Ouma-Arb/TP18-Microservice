@echo off
REM Quick run script for TP_18

echo Starting TP_18 gRPC Service...
echo.
echo Server will be available at:
echo   - gRPC: localhost:9090
echo   - REST: localhost:8080
echo   - H2 Console: http://localhost:8080/h2-console
echo.
echo Press Ctrl+C to stop the server
echo.

call mvn spring-boot:run

