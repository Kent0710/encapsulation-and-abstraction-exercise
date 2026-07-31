@echo off
setlocal

pushd "%~dp0"
javac -d out src\com\rental\*.java
if errorlevel 1 goto :end
java -cp out com.rental.Main

:end
popd
endlocal