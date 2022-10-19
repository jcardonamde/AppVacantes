package co.edu.upb.android.modelo;

public class Vacantes {
    private String nombre;
    private String detalle;
    private String profesiones_asociadas;
    private String salario;

    /*
    public Vacante(String nombre, String detalle, String profesiones_asociadas, int salario) {
        this.nombre = nombre;
        this.detalle = detalle;
        this.profesiones_asociadas = profesiones_asociadas;
        //this.salario = salario;
    }
    */

    public Vacantes() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getProfesiones_asociadas() {
        return profesiones_asociadas;
    }

    public void setProfesiones_asociadas(String profesiones_asociadas) {
        this.profesiones_asociadas = profesiones_asociadas;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }
}
