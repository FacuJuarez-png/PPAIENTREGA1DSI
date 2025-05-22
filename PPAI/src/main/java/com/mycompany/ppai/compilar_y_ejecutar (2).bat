
@echo off
echo Compilando todos los archivos .java...

:: Borrar archivo de fuentes anterior (si existe)
if exist sources.txt del sources.txt

:: Generar lista de archivos .java
for /R %%i in (*.java) do echo %%i >> sources.txt

:: Compilar con gson incluido
javac -cp ".;gson-2.10.1.jar" -d ../bin @sources.txt

if %errorlevel% neq 0 (
    echo.
    echo ❌ Error al compilar. Revisa los mensajes de error arriba.
    pause
    exit /b
)

echo.
echo ✅ Compilación exitosa.

:: Ejecutar el programa principal con gson incluido en el classpath
echo Ejecutando la aplicación...
cd ../bin
java -cp ".;../src/gson-2.10.1.jar" com.mycompany.ppai.PPAI

pause
