
@echo off
echo Compilando modelo...
javac modelo\Empleado.java
javac modelo\Rol.java
javac modelo\Sesion.java
javac modelo\Estado.java
javac modelo\MotivoFueraDeServicio.java
javac modelo\CambioEstado.java
javac modelo\Sismografo.java
javac modelo\EstacionSismologica.java
javac modelo\OrdenDeInspeccion.java

echo Compilando gestor...
javac gestor\gestorCierreOrdenInspeccion.java

echo Compilando main...
javac main\main.java

echo Ejecutando programa...
java -cp . main.main

pause
