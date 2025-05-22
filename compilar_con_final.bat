
@echo off
echo Compilando modelo...
javac -d . modelo\*.java

echo Compilando gestor...
javac -d . gestor\gestorCierreOrdenInspeccion.java

echo Compilando boundary UI...
javac -d . boundary\PantallaCierreOrdenInspeccion_Final.java
javac -d . boundary\InterfazNotificacion.java

echo Compilando main UI launcher...
javac -d . main\mainUI.java

echo Ejecutando UI...
java -cp . main.mainUI

pause
