@echo off
setlocal
cd /d "%~dp0"
echo === PotionsQ Gradle Wrapper Setup ===
echo.
where gradle >nul 2>nul
if %errorlevel%==0 (
  echo Gradle gefunden. Erzeuge Wrapper...
  gradle wrapper --gradle-version 9.1.0
  if not errorlevel 1 (
    echo Fertig. Du kannst jetzt "build.bat" starten.
    pause
    exit /b 0
  )
)
echo.
echo Gradle ist auf diesem PC nicht installiert.
echo.
echo Installiere/oeffne Gradle zuerst oder nutze die offizielle Gradle-Installation.
echo Danach in diesem Ordner ausfuehren:
echo     gradle wrapper --gradle-version 9.1.0
echo.
pause
