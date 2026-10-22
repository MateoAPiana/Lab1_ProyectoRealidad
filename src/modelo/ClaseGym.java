package modelo;

public class ClaseGym {
    private int id;
    private String diciplina;
    private Entrenador entrenador;
    private String horario;
    private int capacidad;
    private boolean estado;

    public ClaseGym() {
    }

    public ClaseGym(String diciplina, Entrenador entrenador, String horario, int capacidad, boolean estado) {
        this.id = -1;
        this.diciplina = diciplina;
        this.entrenador = entrenador;
        this.horario = horario;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public String getDiciplina() {
        return diciplina;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public String getHorario() {
        return horario;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDiciplina(String diciplina) {
        this.diciplina = diciplina;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    
}
