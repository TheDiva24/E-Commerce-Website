# AutomationExercise - PowerShell Test Runner
# Usage: .\run.ps1 ui | api | all | report | compile

$MVN = "E:\Computer Engineering\apache-maven-3.9.15-bin\apache-maven-3.9.15\bin\mvn.cmd"
$mode = $args[0]

Write-Host "=======================================" -ForegroundColor Cyan
Write-Host "  AutomationExercise - Test Runner" -ForegroundColor Cyan
Write-Host "=======================================" -ForegroundColor Cyan

# Verify Maven exists
if (-not (Test-Path $MVN)) {
    Write-Host "[ERROR] Maven not found at: $MVN" -ForegroundColor Red
    exit 1
}

switch ($mode) {

    "compile" {
        Write-Host "[RUNNING] Compiling project..." -ForegroundColor Yellow
        & $MVN clean test-compile
    }

    "ui" {
        Write-Host "[RUNNING] All 26 UI Tests (testng.xml)..." -ForegroundColor Yellow
        # Pass -Dsuite as a single quoted string to avoid PowerShell parsing it
        & $MVN clean test '-Dsuite=testng.xml'
    }

    "api" {
        Write-Host "[RUNNING] All 14 API Tests (testng-api.xml)..." -ForegroundColor Yellow
        & $MVN clean test '-Dsuite=testng-api.xml'
    }

    "all" {
        Write-Host "[RUNNING] UI Tests (26)..." -ForegroundColor Yellow
        & $MVN clean test '-Dsuite=testng.xml'
        Write-Host ""
        Write-Host "[RUNNING] API Tests (14)..." -ForegroundColor Yellow
        & $MVN test '-Dsuite=testng-api.xml'
    }

    "report" {
        Write-Host "[OPENING] Allure Report in browser..." -ForegroundColor Yellow
        & $MVN allure:serve
    }

    default {
        Write-Host ""
        Write-Host "Usage:" -ForegroundColor White
        Write-Host "  .\run.ps1 compile  - Compile all sources (quick check)" -ForegroundColor Gray
        Write-Host "  .\run.ps1 ui       - Run all 26 UI tests" -ForegroundColor Gray
        Write-Host "  .\run.ps1 api      - Run all 14 API tests" -ForegroundColor Gray
        Write-Host "  .\run.ps1 all      - Run all 40 tests" -ForegroundColor Gray
        Write-Host "  .\run.ps1 report   - Open Allure HTML report in browser" -ForegroundColor Gray
        Write-Host ""
        Write-Host "Direct Maven (in PowerShell, always quote the -D flag):" -ForegroundColor White
        Write-Host "  & `"$MVN`" clean test '-Dsuite=testng.xml'" -ForegroundColor Gray
        Write-Host "  & `"$MVN`" clean test '-Dsuite=testng-api.xml'" -ForegroundColor Gray
    }
}

Write-Host ""
Write-Host "Done." -ForegroundColor Green
