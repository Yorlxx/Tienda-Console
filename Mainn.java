import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

class Producto {
    String categoria;
    String nombre;
    double precio;
    int stock;


    public Producto(String categoria, String nombre, double precio, int stock) {
        this.categoria = categoria;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }


    public void reducirStock() {
        // CONDICIONAL SIMPLE
        if (this.stock > 0) {
            this.stock--;
        }
    }
}


// CLASE PRINCIPAL

public class Main {

    static Scanner Fsociety = new Scanner(System.in);
    static String usuarios = "";
    static String contras = "";


    static ArrayList<Producto> inventario = new ArrayList<>();
    static ArrayList<Producto> carrito = new ArrayList<>();

    static double saldoUsuario = 0.0; // Billetera digital

    // Motort
    static void main() {
        productosiniciales();
        panelInicio();

    }


    public static void panelInicio() {
        int opcion;

        do {
            System.out.println("\n===== Bienvenido a FsocietyStore =====");
            System.out.println("1. Registrarse (Cliente)");
            System.out.println("2. Iniciar Sesión (Cliente)");
            System.out.println("3. Iniciar Sesión (Administrador)");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = Fsociety.nextInt();
            Fsociety.nextLine();


            switch (opcion) {
                case 1:
                    System.out.print("Cree un usuario: ");
                    usuarios = Fsociety.nextLine();
                    System.out.print("Cree una contraseña: ");
                    contras = Fsociety.nextLine();
                    System.out.println("Usuario registrado correctamente");
                    break;

                case 2:
                    System.out.print("Usuario: ");
                    String userIn = Fsociety.nextLine();
                    System.out.print("Contraseña: ");
                    String passIn = Fsociety.nextLine();


                    if (userIn.equals(usuarios) && passIn.equals(contras)) {
                        menuCliente(userIn);
                    } else {
                        System.out.println("Usuario o contraseña incorrecta.");
                    }
                    break;

                case 3:
                    System.out.print("Admin usuario: ");
                    String adminUser = Fsociety.nextLine();
                    System.out.print("Admin contra: ");
                    String adminPass = Fsociety.nextLine();


                    if (adminUser.equals("admin") && adminPass.equals("admin123")) {
                        menuAdmin();
                    } else {
                        System.out.println(" Credenciales inválidas.");
                    }
                    break;

                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println(" Opción no válida.");
                    break;
            }
        } while (opcion != 4);
    }


    public static void menuCliente(String nombreUser) {
        int opc;
        do {
            System.out.println("\n--- Panel de Cliente: " + nombreUser + " ---");
            System.out.println("1. Ver catálogo y comprar");
            System.out.println("2. Ver mi Billetera");
            System.out.println("3. Ver Carrito y Pagar");
            System.out.println("4. Cerrar sesión");
            System.out.print("Opción: ");
            opc = Fsociety.nextInt();
            Fsociety.nextLine();

            switch (opc) {
                case 1: comprarProducto(); break;
                case 2: gestionarBilletera(); break;
                case 3: pagarCarrito(nombreUser); break;
                case 4: System.out.println("Cerrando sesión..."); break;
                default: System.out.println("Opción incorrecta."); break;
            }
        } while (opc != 4);
    }


    public static void comprarProducto() {
        System.out.println("\n--- Catálogo Organizado por Categorías ---");


        String[] categorias = {"Laptops", "Celulares", "Perifericos", "Hardware"};


        for (String cat : categorias) {
            System.out.println("\n>> " + cat.toUpperCase() + " <<");


            for (int i = 0; i < inventario.size(); i++) {
                Producto p = inventario.get(i);


                if (p.categoria.equalsIgnoreCase(cat)) {
                    System.out.printf("[%d] %-25s | Precio: S/.%-7.2f | Stock: %d%n",
                            i, p.nombre, p.precio, p.stock);
                }
            }
        }

        System.out.print("\nIngrese el ID (número) del producto a agregar (o -1 para salir): ");
        int id = Fsociety.nextInt();
        Fsociety.nextLine();


        if (id >= 0 && id < inventario.size()) {
            Producto prodSeleccionado = inventario.get(id);

            if (prodSeleccionado.stock > 0) {
                carrito.add(prodSeleccionado);
                prodSeleccionado.reducirStock();
                System.out.println("[!] " + prodSeleccionado.nombre + " agregado al carrito.");
            } else {
                System.out.println("[X] Lo sentimos, no hay stock disponible de este producto.");
            }
        }
    }


    public static void menuAdmin() {
        int opc;
        do {
            System.out.println("\n--- Panel de Administrador ---");
            System.out.println("1. Añadir nuevo producto");
            System.out.println("2. Ver inventario general");
            System.out.println("3. Regresar");
            System.out.print("Opción: ");
            opc = Fsociety.nextInt();
            Fsociety.nextLine();


            if (opc == 1) {
                System.out.print("Categoría (Laptops/Celulares/Perifericos/Hardware): ");
                String cat = Fsociety.nextLine();
                System.out.print("Nombre del producto: ");
                String nom = Fsociety.nextLine();
                System.out.print("Precio unitario (S/.): ");
                double pre = Fsociety.nextDouble();
                System.out.print("Cantidad en stock: ");
                int stk = Fsociety.nextInt();
                Fsociety.nextLine();


                inventario.add(new Producto(cat, nom, pre, stk));
                System.out.println("Producto registrado con exito");

            } else if (opc == 2) {
                System.out.println("\n----- Inventario Completo -----");

                for (Producto p : inventario) {
                    System.out.println("Cat: " + p.categoria + " | Prod: " + p.nombre + " | Stock: " + p.stock);
                }
            }
        } while (opc != 3);
    }


    public static void gestionarBilletera() {
        System.out.println("\n--- Billetera Digital ---");
        System.out.println("Saldo actual: S/." + saldoUsuario);
        System.out.println("1. Depositar");
        System.out.println("2. Regresar");
        System.out.print("Opción: ");
        int opc = Fsociety.nextInt();
        Fsociety.nextLine();

        if (opc == 1) {
            System.out.print("Monto a depositar: ");
            double deposito = Fsociety.nextDouble();
            Fsociety.nextLine();


            saldoUsuario = operarSaldo(saldoUsuario, deposito, true);
            System.out.println("[!] Saldo actualizado: S/." + saldoUsuario);
        }
    }


    public static double operarSaldo(double actual, double monto, boolean esSuma) {
        if (esSuma) {
            return actual + monto;
        }
        return actual - monto;
    }


    static double calcularTotalRecursivo(ArrayList<Producto> lista, int indice) {
        if (indice == lista.size()) {
            return 0;
        }

        return lista.get(indice).precio + calcularTotalRecursivo(lista, indice + 1);
    }


    public static void pagarCarrito(String usuario) {
        if (carrito.isEmpty()) {
            System.out.println("[X] Tu carrito está vacío.");
            return;
        }

        double totalPagar = calcularTotalRecursivo(carrito, 0);
        System.out.println("\nTotal a pagar: S/." + totalPagar);


        if (saldoUsuario >= totalPagar) {


            System.out.print("¿Desea generar un voucher de pago? (S/N): ");
            String respuestaVoucher = Fsociety.nextLine();

            String dniUsuario = "";

            if (respuestaVoucher.equalsIgnoreCase("S")) {
                System.out.print("Ingrese su DNI (o presione Enter para omitir): ");
                dniUsuario = Fsociety.nextLine();
            }


            saldoUsuario = operarSaldo(saldoUsuario, totalPagar, false);


            if (respuestaVoucher.equalsIgnoreCase("S")) {
                generarTicket(usuario, dniUsuario, totalPagar);
            } else {
                System.out.println("Compra realizada con éxito. (Sin comprobante impreso)");
            }

            carrito.clear();

        } else {
            System.out.println(" Saldo insuficiente. Ve a tu billetera.");


            for (Producto p : carrito) {
                p.stock++;
            }
            carrito.clear();
            System.out.println("Carrito vaciado y stock restaurado en la tienda.");
        }
    }


    public static void generarTicket(String cliente, String dni, double total) {
        String nombreArchivo = "Voucher_" + cliente + ".txt";
        File archivo = new File(nombreArchivo);


        try {
            if (!archivo.exists()) {
                archivo.createNewFile();
            }

            PrintWriter escritor = new PrintWriter(archivo);


            escritor.println("================================");
            escritor.println("         FSOCIETY STORE         ");
            escritor.println("================================");
            escritor.println("Cliente: " + cliente);


            if (!dni.trim().isEmpty()) {
                escritor.println("DNI: " + dni);
            }

            escritor.println("--------------------------------");
            escritor.println("DESCRIPCION             PRECIO");
            escritor.println("--------------------------------");

            for (Producto p : carrito) {
                String nombreProd = p.nombre;
                if(nombreProd.length() > 20) {
                    nombreProd = nombreProd.substring(0, 20);
                }
                escritor.printf("%-22s S/.%.2f%n", nombreProd, p.precio);
            }

            escritor.println("--------------------------------");
            escritor.printf("TOTAL A PAGAR:         S/.%.2f%n", total);
            escritor.println("================================");
            escritor.println("    GRACIAS POR SU COMPRA!      ");

            escritor.close();
            System.out.println("Comprobante impreso con éxito: " + archivo.getName());

        } catch (IOException e) {
            System.out.println(" Error al generar el comprobante: " + e.getMessage());
        }
    }


    public static void productosiniciales() {
        inventario.add(new Producto("Laptops", "Asus ROG RTX 4060", 4800.00, 5));
        inventario.add(new Producto("Laptops", "Lenovo Legion Pro 5", 5200.00, 3));
        inventario.add(new Producto("Celulares", "Samsung Galaxy S24", 3500.00, 10));
        inventario.add(new Producto("Perifericos", "Mouse Logitech G Pro", 350.00, 15));
        inventario.add(new Producto("Hardware", "Procesador Intel i7", 1200.00, 8));
    }
}
