package vistas;

import persistencia.MiConexion;
import persistencia.SocioData;
import modelo.Socio;
import java.sql.Connection;

public class Prueba {

    public static void main(String[] args) {
        System.out.println("=== INTEGRANTES DEL GRUPO ===");
        System.out.println("- Genaro Farías");
        System.out.println("=============================\n");
        System.out.println("Probando conexión a la base de datos 'aborigym'...");
        MiConexion miconexion = new MiConexion("jdbc:mysql://localhost:3306/aborigym?useSSL=false&serverTimezone=UTC", "root", "");
        Connection con = miconexion.buscarConexion();
        if (con != null) {
            System.out.println("¡Conexión exitosa a la Base de Datos!\n");
            System.out.println("Probando inserción de un Socio de prueba...");
            SocioData socioData = new SocioData();
            Socio socioPrueba = new Socio("40123zñ58", "Carlos", "Gómez", 28, "carllozs@mail.com", "2664998877", true);
            socioData.guardarSocio(socioPrueba);
            System.out.println("¡Revisa tu base de datos en la tabla 'socio'! Debería aparecer Carlos Gómez.");
        } else {
            System.out.println("No se pudo establecer la conexión.\n");
        }
    }
}
