import java.time.LocalDateTime;

public class Boleto {
    private Pasajero pasajero;
    private String ruta;
    private double precio;
    private int equipajeCantidad;
    private double equipajePeso;
    private LocalDateTime fechaHoraReserva;

    public Boleto(Pasajero pasajero, String ruta, double precio, int equipajeCantidad, double equipajePeso, LocalDateTime fechaHoraReserva) {
        this.pasajero = pasajero;
        this.ruta = ruta;
        this.precio = precio;
        this.equipajeCantidad = equipajeCantidad;
        this.equipajePeso = equipajePeso;
        this.fechaHoraReserva = fechaHoraReserva;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public String getRuta() {
        return ruta;
    }

    public double getPrecio() {
        return precio;
    }

    public int getEquipajeCantidad() {
        return equipajeCantidad;
    }

    public double getEquipajePeso() {
        return equipajePeso;
    }

    public LocalDateTime getFechaHoraReserva() {
        return fechaHoraReserva;
    }
}
