public class Pasajero {
    private String nombre;
    private String identificacion;
    private String email;

    public Pasajero(String nombre, String identificacion, String email) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getEmail() {
        return email;
    }
}
