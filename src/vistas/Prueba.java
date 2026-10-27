package vistas;

import persistencia.SocioData;
import modelo.Socio;

public class Prueba {

    public static void main(String[] args) {
        System.out.println("=== INTEGRANTES DEL GRUPO ===");
        System.out.println("- Genaro Farías");
        System.out.println("=============================\n");
        System.out.println("Probando conexión a la base de datos 'aborigym'...");

        System.out.println("¡Conexión exitosa a la Base de Datos!\n");
        System.out.println("Probando inserción de un Socio de prueba...");
        SocioData socioData = new SocioData();
        Socio socioPrueba = new Socio("4012358", "Carlos", "Gómez", 28, "carllos@mail.com", "2664998877", true);
        socioData.guardarSocio(socioPrueba);
        System.out.println("¡Revisa tu base de datos en la tabla 'socio'! Debería aparecer Carlos Gómez.");
    }
}
