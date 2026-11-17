package vistas;

import java.time.LocalDate;
import java.util.List;
import modelo.ClaseGym;
import modelo.Entrenador;
import persistencia.SocioData;
import modelo.Socio;
import modelo.Pase;
import modelo.Asistencia;
import persistencia.AsistenciaData;
import persistencia.ClaseGymData;
import persistencia.EntrenadorData;
import persistencia.PaseData;

public class Prueba {

    public static void main(String[] args) {
        ClaseGymData cd = new ClaseGymData();
        EntrenadorData ed = new EntrenadorData();
        AsistenciaData ad = new AsistenciaData();
        SocioData sd = new SocioData();
        PaseData pd = new PaseData();

        List<Entrenador> el = ed.listarEntrenador(true);
        List<Socio> sl = sd.listarSocios(true);
        List<Pase> pl = pd.listarPases(true);
        List<ClaseGym> cl = cd.listarClases(true);
        List<Asistencia> al = ad.listarAsistencias();

        for (Asistencia i : al) {
            ad.borrarAsistencia(i.getIdAsistencia());
        }

        for (ClaseGym i : cl) {
            System.out.println(i.getId());
            cd.borrarClase(i.getId());
        }

        for (Pase i : pl) {
            pd.borrarPase(i.getId());
        }

        for (Entrenador i : el) {
            ed.eliminarEntrenador(i.getIdEntrenador());
        }

        for (Socio i : sl) {
            sd.eliminarSocio(i.getIdSocio());
        }

        Entrenador e1 = new Entrenador("57789735", "Mateo", "Piana", "pilates", true);
        Entrenador e2 = new Entrenador("57788734", "Donato", "Santagata", "aereobox", true);
        Entrenador e3 = new Entrenador("57789736", "Luciano", "Perez", "spinning", true);
        Entrenador e4 = new Entrenador("57789737", "Camila", "Rodriguez", "crossfit", true);
        Entrenador e5 = new Entrenador("57789738", "Santiago", "Lopez", "musculacion", true);

        ed.guardarEntrenador(e1);
        ed.guardarEntrenador(e2);
        ed.guardarEntrenador(e3);
        ed.guardarEntrenador(e4);
        ed.guardarEntrenador(e5);

        Socio s1 = new Socio("78654321", "Genaro", "Farias", 19, "genaeo@gmail.com", "2665098743", true);
        Socio s2 = new Socio("78654561", "Veronica", "Gonzalez", 22, "vero@gmail.com", "2665035543", true);
        Socio s3 = new Socio("78654322", "Agustina", "Martinez", 25, "agus@gmail.com", "2665012345", true);
        Socio s4 = new Socio("78654323", "Franco", "Diaz", 30, "franco@gmail.com", "2665076543", true);
        Socio s5 = new Socio("78654324", "Lucia", "Fernandez", 21, "lucia@gmail.com", "2665098765", true);
        Socio s6 = new Socio("78654325", "Tomas", "Romero", 27, "tomas@gmail.com", "2665032145", true);

        sd.guardarSocio(s1);
        sd.guardarSocio(s2);
        sd.guardarSocio(s3);
        sd.guardarSocio(s4);
        sd.guardarSocio(s5);
        sd.guardarSocio(s6);

        Pase p1 = new Pase(s1, 8, LocalDate.now(), LocalDate.now().plusMonths(1), 7000, true);
        Pase p2 = new Pase(s1, 30, LocalDate.now(), LocalDate.now().plusMonths(1), 28000, true);
        Pase p3 = new Pase(s2, 12, LocalDate.now(), LocalDate.now().plusDays(30), 1000, true);
        Pase p4 = new Pase(s3, 20, LocalDate.now(), LocalDate.now().plusDays(30), 3500, true);
        Pase p5 = new Pase(s4, 8, LocalDate.now(), LocalDate.now().plusDays(30), 3500, true);
        Pase p6 = new Pase(s5, 8, LocalDate.now().minusDays(40), LocalDate.now().minusDays(10), 28000, true);
        Pase p7 = new Pase(s1, 12, LocalDate.now().plusDays(30), LocalDate.now().plusDays(60), 7000, true);

        pd.guardarPase(p1);
        pd.guardarPase(p2);
        pd.guardarPase(p3);
        pd.guardarPase(p4);
        pd.guardarPase(p5);
        pd.guardarPase(p6);
        pd.guardarPase(p7);

        ClaseGym c1 = new ClaseGym("pilates", e1, "13:00", 12, true);
        ClaseGym c2 = new ClaseGym("pilates", e2, "08:00", 10, true);
        ClaseGym c3 = new ClaseGym("pilates", e1, "15:00", 8, true);
        ClaseGym c4 = new ClaseGym("aereobox", e2, "18:45", 6, true);
        ClaseGym c5 = new ClaseGym("aereobox", e1, "20:00", 4, true);
        ClaseGym c6 = new ClaseGym("aereobox", e2, "12:30", 12, true);
        ClaseGym c7 = new ClaseGym("spinning", e3, "09:00", 4, true);
        ClaseGym c8 = new ClaseGym("spinning", e3, "16:00", 15, true);
        ClaseGym c9 = new ClaseGym("crossfit", e4, "10:00", 8, true);
        ClaseGym c10 = new ClaseGym("crossfit", e4, "19:00", 12, true);
        ClaseGym c11 = new ClaseGym("musculacion", e5, "11:00", 20, true);
        ClaseGym c12 = new ClaseGym("musculacion", e5, "17:00", 10, true);

        cd.guardarClase(c1);
        cd.guardarClase(c2);
        cd.guardarClase(c3);
        cd.guardarClase(c4);
        cd.guardarClase(c5);
        cd.guardarClase(c6);

        Asistencia a1 = new Asistencia(s1, c1, LocalDate.now().plusDays(1));
        Asistencia a2 = new Asistencia(s2, c2, LocalDate.now().plusDays(2));
        Asistencia a3 = new Asistencia(s2, c2, LocalDate.now().plusDays(3));
        Asistencia a4 = new Asistencia(s1, c2, LocalDate.now().plusDays(20));
        Asistencia a5 = new Asistencia(s2, c3, LocalDate.now().plusDays(30));
        Asistencia a6 = new Asistencia(s1, c4, LocalDate.now().plusDays(40));
        Asistencia a7 = new Asistencia(s1, c5, LocalDate.now().plusDays(3));
        Asistencia a8 = new Asistencia(s1, c4, LocalDate.now().plusDays(2));
        Asistencia a9 = new Asistencia(s1, c3, LocalDate.now().plusDays(-4));
        Asistencia a10 = new Asistencia(s1, c4, LocalDate.now().plusDays(10));
        Asistencia a11 = new Asistencia(s1, c6, LocalDate.now().plusDays(14));
        Asistencia a12 = new Asistencia(s1, c6, LocalDate.now().plusDays(16));
        Asistencia a13 = new Asistencia(s1, c3, LocalDate.now().plusDays(14));
        Asistencia a14 = new Asistencia(s1, c1, LocalDate.now().plusDays(4));
        Asistencia a15 = new Asistencia(s1, c6, LocalDate.now().plusDays(2));
        Asistencia a16 = new Asistencia(s2, c7, LocalDate.now().plusDays(1));
        Asistencia a17 = new Asistencia(s3, c7, LocalDate.now().plusDays(1));
        Asistencia a18 = new Asistencia(s4, c7, LocalDate.now().plusDays(1));
        Asistencia a19 = new Asistencia(s5, c7, LocalDate.now().plusDays(1));
        Asistencia a20 = new Asistencia(s3, c8, LocalDate.now().plusDays(2));
        Asistencia a21 = new Asistencia(s4, c8, LocalDate.now().plusDays(2));
        Asistencia a22 = new Asistencia(s2, c9, LocalDate.now().plusDays(3));
        Asistencia a23 = new Asistencia(s3, c9, LocalDate.now().plusDays(3));
        Asistencia a24 = new Asistencia(s6, c9, LocalDate.now().plusDays(3));
        Asistencia a25 = new Asistencia(s4, c10, LocalDate.now().plusDays(5));
        Asistencia a26 = new Asistencia(s6, c10, LocalDate.now().plusDays(5));
        Asistencia a27 = new Asistencia(s2, c11, LocalDate.now().minusDays(2));
        Asistencia a28 = new Asistencia(s3, c11, LocalDate.now().minusDays(1));
        Asistencia a29 = new Asistencia(s4, c12, LocalDate.now().plusDays(7));
        Asistencia a30 = new Asistencia(s6, c12, LocalDate.now().plusDays(8));
        Asistencia a31 = new Asistencia(s2, c8, LocalDate.now().minusDays(5));
        Asistencia a32 = new Asistencia(s3, c9, LocalDate.now().minusDays(10));
        Asistencia a33 = new Asistencia(s4, c10, LocalDate.now().minusDays(15));

        ad.registrarAsistencia(a1);
        ad.registrarAsistencia(a2);
        ad.registrarAsistencia(a3);
        ad.registrarAsistencia(a4);
        ad.registrarAsistencia(a5);
        ad.registrarAsistencia(a6);
        ad.registrarAsistencia(a7);
        ad.registrarAsistencia(a8);
        ad.registrarAsistencia(a9);
        ad.registrarAsistencia(a10);
        ad.registrarAsistencia(a11);
        ad.registrarAsistencia(a12);
        ad.registrarAsistencia(a13);
        ad.registrarAsistencia(a14);
        ad.registrarAsistencia(a15);
        ad.registrarAsistencia(a16);
        ad.registrarAsistencia(a17);
        ad.registrarAsistencia(a18);
        ad.registrarAsistencia(a19);
        ad.registrarAsistencia(a20);
        ad.registrarAsistencia(a21);
        ad.registrarAsistencia(a22);
        ad.registrarAsistencia(a23);
        ad.registrarAsistencia(a24);
        ad.registrarAsistencia(a25);
        ad.registrarAsistencia(a26);
        ad.registrarAsistencia(a27);
        ad.registrarAsistencia(a28);
        ad.registrarAsistencia(a29);
        ad.registrarAsistencia(a30);
        ad.registrarAsistencia(a31);
        ad.registrarAsistencia(a32);
        ad.registrarAsistencia(a33);
    }
}
