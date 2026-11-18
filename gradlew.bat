@echo off
setlocal
set "APP_HOME=%~dp0"
set "GRADLE_VERSION=8.9"
set "GRADLE_HOME_BASE=%USERPROFILE%\.gradle\mess-hisab-gradle"
set "GRADLE_HOME=%GRADLE_HOME_BASE%\gradle-%GRADLE_VERSION%"
if not exist "%GRADLE_HOME%\bin\gradle.bat" (
  if not exist "%GRADLE_HOME_BASE%" mkdir "%GRADLE_HOME_BASE%"
  echo Gradle %GRADLE_VERSION% download required (first build only)...
  powershell -NoProfile -ExecutionPolicy Bypass -Command "$u='https://services.gradle.org/distributions/gradle-%GRADLE_VERSION%-bin.zip'; $z='%GRADLE_HOME_BASE%\gradle-%GRADLE_VERSION%-bin.zip'; Invoke-WebRequest -Uri $u -OutFile $z; Expand-Archive -Path $z -DestinationPath '%GRADLE_HOME_BASE%' -Force"
  if errorlevel 1 exit /b 1
)
call "%GRADLE_HOME%\bin\gradle.bat" -p "%APP_HOME%" %*
endlocal
