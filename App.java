import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class App {
    private static final int asientos_totales = 100;
    private static int asientosDisponibles = asientos_totales;
    private static final double multa_cancelacion = 0.20;
    private static final int limite_equipaje_cantidad = 2;
    private static final double limite_equipaje_peso = 23.0; // En kg
    private static final int hora_limite_cancelacion = 24; // Horas para cancelar sin multa

    // Arreglos para almacenar datos de reservas
    private static Pasajero[] pasajeros = new Pasajero[asientos_totales];
    private static Boleto[] boletos = new Boleto[asientos_totales];
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            switch (opcion) {
                case 1 -> reservarVuelo();
                case 2 -> cancelarReserva();
                case 3 -> listarReservas();
                case 4 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 4);
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Sistema de Reservas de Vuelos ---");
        System.out.println("1. Reservar vuelo");
        System.out.println("2. Cancelar reserva");
        System.out.println("3. Listar reservas");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void reservarVuelo() {
        if (asientosDisponibles <= 0) {
            System.out.println("No hay asientos disponibles.");
            return;
        }

        System.out.println("\n--- Reservar Vuelo ---");
        System.out.print("Nombre del pasajero: ");
        String nombre = scanner.nextLine();
        System.out.print("Número de identificación: ");
        String identificacion = scanner.nextLine();
        System.out.print("Correo electrónico: ");
        String email = scanner.nextLine();

        System.out.println("Seleccione la ruta: ");
        System.out.println("1. Quito - Guayaquil");
        System.out.println("2. Guayaquil - Cuenca");
        System.out.println("3. Cuenca - Quito");
        int rutaOpcion = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        String ruta;
        switch (rutaOpcion) {
            case 1 -> ruta = "Quito - Guayaquil";
            case 2 -> ruta = "Guayaquil - Cuenca";
            case 3 -> ruta = "Cuenca - Quito";
            default -> {
                System.out.println("Ruta inválida. Reservación cancelada.");
                return;
            }
        }

        System.out.print("Precio del boleto: $");
        double precio = scanner.nextDouble();
        System.out.print("Cantidad de equipaje (máx. " + limite_equipaje_cantidad + "): ");
        int equipajeCantidad = scanner.nextInt();
        if (equipajeCantidad > limite_equipaje_cantidad) {
            System.out.println("Cantidad de equipaje excede el límite. Reservación cancelada.");
            return;
        }

        System.out.print("Peso total del equipaje (kg, máx. " + limite_equipaje_peso + "): ");
        double equipajePeso = scanner.nextDouble();
        if (equipajePeso > limite_equipaje_peso) {
            System.out.println("Peso del equipaje excede el límite. Reservación cancelada.");
            return;
        }

        // Registrar la fecha y hora de la reserva
        LocalDateTime fechaHoraReserva = LocalDateTime.now();

        // Crear objetos Pasajero y Boleto
        Pasajero pasajero = new Pasajero(nombre, identificacion, email);
        Boleto boleto = new Boleto(pasajero, ruta, precio, equipajeCantidad, equipajePeso, fechaHoraReserva);

        // Almacenar en el arreglo
        pasajeros[asientos_totales - asientosDisponibles] = pasajero;
        boletos[asientos_totales - asientosDisponibles] = boleto;

        asientosDisponibles--;
        System.out.println("Reservación realizada con éxito.");
        System.out.println("Fecha y hora de la reserva: " +
                fechaHoraReserva.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    private static void cancelarReserva() {
        System.out.println("\n--- Cancelar Reserva ---");
        System.out.print("Ingrese el número de identificación del pasajero: ");
        String identificacion = scanner.nextLine();

        for (int i = 0; i < asientos_totales - asientosDisponibles; i++) {
            if (pasajeros[i] != null && pasajeros[i].getIdentificacion().equals(identificacion)) {
                LocalDateTime ahora = LocalDateTime.now();
                LocalDateTime fechaReserva = boletos[i].getFechaHoraReserva();
                long horasTranscurridas = java.time.Duration.between(fechaReserva, ahora).toHours();

                if (horasTranscurridas > hora_limite_cancelacion) {
                    double multa = boletos[i].getPrecio() * multa_cancelacion;
                    System.out.println("Se aplicará una multa del 20%: $" + multa);
                } else {
                    System.out.println("Cancelación dentro del tiempo permitido. No se aplica multa.");
                }

                // Eliminar reserva
                pasajeros[i] = null;
                boletos[i] = null;
                asientosDisponibles++;
                System.out.println("Reserva cancelada con éxito.");
                return;
            }
        }
        System.out.println("No se encontró una reserva con esa identificación.");
    }

    private static void listarReservas() {
        System.out.println("\n--- Listar Reservas ---");
        boolean hayReservas = false;
        for (int i = 0; i < asientos_totales - asientosDisponibles; i++) {
            if (pasajeros[i] != null) {
                hayReservas = true;
                System.out.println("Pasajero: " + pasajeros[i].getNombre() +
                        " | Ruta: " + boletos[i].getRuta() +
                        " | Precio: $" + boletos[i].getPrecio() +
                        " | Equipaje: " + boletos[i].getEquipajeCantidad() + 
                        " piezas, " + boletos[i].getEquipajePeso() + " kg" +
                        " | Fecha y hora: " + boletos[i].getFechaHoraReserva().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        }
        if (!hayReservas) {
            System.out.println("No hay reservas realizadas.");
        }
    }
}
