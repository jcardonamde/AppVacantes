package co.edu.upb.android.modelo;

public class Estudiante {
    private Long id;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String correo;
    private String clave;
    private int edad;

    public Estudiante() {
        this.id = Long.valueOf(0);
        this.nombre = "";
        this.apellido = "";
        this.direccion = "";
        this.telefono = "";
        this.correo = "";
        this.clave = "";
        this.edad = 0;
    }

    public Estudiante(Long id, String nombre, String apellido, String direccion, String telefono, String correo, String clave, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.clave = clave;
        this.edad = edad;
    }

    public Estudiante(String nombre, String apellido, String direccion, String telefono, String correo, String clave, int edad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.clave = clave;
        this.edad = edad;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}