@echo off
setlocal
cd /d "%~dp0"
echo.
echo === PotionsQ Build ===
echo.
if not exist "gradlew.bat" (
  echo Gradle Wrapper fehlt.
  echo Bitte zuerst "setup-wrapper.bat" ausfuehren.
  pause
  exit /b 1
)
call gradlew.bat build
if errorlevel 1 (
  echo.
  echo BUILD FEHLGESCHLAGEN.
  pause
  exit /b 1
)
echo.
echo BUILD ERFOLGREICH!
echo Die JAR liegt jetzt unter:
echo build\libs\
explorer "build\libs"
pause
