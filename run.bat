@echo off
REM KEY FIX: Use SET "VAR=value" style (quotes around assignment, NOT value)
REM KEY FIX: Use CALL before every mvn.cmd invocation so execution returns

SET "MVN=E:\Computer Engineering\apache-maven-3.9.15-bin\apache-maven-3.9.15\bin\mvn.cmd"

echo.
echo  =======================================
echo    AutomationExercise - Test Runner
echo  =======================================
echo.

REM ---- Verify Maven exists ----
IF NOT EXIST "%MVN%" (
    echo [ERROR] Maven not found at:
    echo   %MVN%
    echo.
    echo Please check the path and update this file.
    echo.
    pause
    exit /b 1
)

echo  [OK] Maven found.
echo  [OK] Java detected.
echo.

REM ---- Parse argument ----
SET "MODE=%~1"

IF "%MODE%"==""        GOTO show_usage
IF /I "%MODE%"=="ui"      GOTO run_ui
IF /I "%MODE%"=="api"     GOTO run_api
IF /I "%MODE%"=="all"     GOTO run_all
IF /I "%MODE%"=="report"  GOTO open_report
IF /I "%MODE%"=="compile" GOTO compile_only
GOTO show_usage

:show_usage
echo  How to use - open a CMD window here and type one of:
echo.
echo    run.bat compile   ^<-- Compile check (no tests, just verify build)
echo    run.bat ui        ^<-- Run all 26 UI tests (opens Chrome)
echo    run.bat api       ^<-- Run all 14 API tests (no browser)
echo    run.bat all       ^<-- Run all 40 tests
echo    run.bat report    ^<-- Open Allure HTML report in browser
echo.
echo  TIP: Open a CMD here by clicking the address bar and typing: cmd
echo.
pause
GOTO end

:compile_only
echo  [RUNNING] Compiling all sources...
echo.
CALL "%MVN%" clean test-compile
IF ERRORLEVEL 1 (
    echo.
    echo  [FAILED] Compilation errors found. See output above.
) ELSE (
    echo.
    echo  [SUCCESS] All sources compiled successfully - no errors!
)
echo.
pause
GOTO end

:run_ui
echo  [RUNNING] All 26 UI Tests...
echo  Chrome browser will open automatically.
echo.
CALL "%MVN%" clean test -Dsuite=testng.xml
IF ERRORLEVEL 1 (
    echo.
    echo  [DONE] Some tests FAILED. Run 'run.bat report' to see the Allure report.
) ELSE (
    echo.
    echo  [SUCCESS] All UI tests PASSED!
)
echo.
pause
GOTO end

:run_api
echo  [RUNNING] All 14 API Tests...
echo  No browser needed - pure HTTP API calls.
echo.
CALL "%MVN%" clean test -Dsuite=testng-api.xml
IF ERRORLEVEL 1 (
    echo.
    echo  [DONE] Some tests FAILED. Run 'run.bat report' to see details.
) ELSE (
    echo.
    echo  [SUCCESS] All API tests PASSED!
)
echo.
pause
GOTO end

:run_all
echo  [RUNNING] All 40 Tests (26 UI + 14 API)...
echo.
echo  --- Phase 1: UI Tests (26) ---
CALL "%MVN%" clean test -Dsuite=testng.xml
echo.
echo  --- Phase 2: API Tests (14) ---
CALL "%MVN%" test -Dsuite=testng-api.xml
echo.
echo  [DONE] All tests finished. Run 'run.bat report' to see Allure report.
echo.
pause
GOTO end

:open_report
echo  [OPENING] Generating Allure Report...
echo  A browser tab will open automatically.
echo.
CALL "%MVN%" allure:serve
echo.
pause
GOTO end

:end
