import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class CodigoCompleto {
    private static final int MAX_VISTAS = 255;
    private static String[] vistasAdmin = new String[256];
    private static int contadorVistas = 0;
    private static String[][] vistasComprador = new String[255][3];
    private static int contadorCompras = 0;
    private static final String ARCHIVO_ADMIN = "vistas_admin.txt";
    private static final String ARCHIVO_COMPRADOR = "vistas_comprador.txt";
    private static Scanner scanner;

    public CodigoCompleto() {
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 255; ++i) {
            vistasAdmin[i] = null;
        }

        cargarVistasAdmin();
        cargarVistasComprador();
        mostrarMenuPrincipal();
    }

    private static void mostrarMenuPrincipal() {
        int opcion = 0;

        while (opcion != 3) {
            System.out.println("-------------------------");
            System.out.println("****Menu Principal****");
            System.out.println("1. Administrador");
            System.out.println("2. Comprador");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            System.out.println("-------------------------");
            switch (opcion) {
                case 1:
                    mostrarMenuAdministrador();
                    break;
                case 2:
                    mostrarMenuComprador();
                    break;
                case 3:
                    System.out.println("Saliendo del sistema. ¡Bye!");
                    break;
                default:
                    System.out.println("Opción no valida. Intente de nuevo.");
            }
        }
    }

    private static void mostrarMenuAdministrador() {
        int opcion = 0;

        while (opcion != 3) {
            System.out.println("-------------------------");
            System.out.println("***Menu Administrador***");
            System.out.println("1. Insertar Nueva Vista");
            System.out.println("2. Imprimir Vistas");
            System.out.println("3. Menu Principal");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            System.out.println("-------------------------");
            switch (opcion) {
                case 1:
                    insertarNuevaVista();
                    break;
                case 2:
                    imprimirVistasAdmin();
                    break;
                case 3:
                    System.out.println("Volviendo al Menu Principal...");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }
        }
    }

    private static void mostrarMenuComprador() {
        int opcion = 0;

        while (opcion != 3) {
            System.out.println("-------------------------");
            System.out.println("****Menu Comprador****");
            System.out.println("1. Comprar Vista");
            System.out.println("2. Ver Vistas Compradas");
            System.out.println("3. Menu Principal");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            System.out.println("-------------------------");
            switch (opcion) {
                case 1:
                    comprarVista();
                    break;
                case 2:
                    imprimirVistasComprador();
                    break;
                case 3:
                    System.out.println("Volviendo al Menu Principal...");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }
        }
    }

    private static void insertarNuevaVista() {
        if (contadorVistas >= 255) {
            System.out.println("**¡Almacen Lleno!** Ya se agotaron las 255 vistas disponibles.");
        } else {
            System.out.print("Ingrese el nombre de la nueva vista: ");
            String nombreVista = scanner.next();
            if (nombreVista.isEmpty()) {
                System.out.println("El nombre de la vista no puede estar vacío.");
            } else {
                ++contadorVistas;
                vistasAdmin[contadorVistas] = nombreVista;
                System.out.println("Vista " + nombreVista + " insertada correctamente.");
                System.out.println("   - Numero de Vista asignado: " + contadorVistas);
                guardarVistasAdmin();
            }
        }
    }

    private static void imprimirVistasAdmin() {
        if (contadorVistas == 0) {
            System.out.println("No hay vistas registradas");
        } else {
            System.out.println("--- Listado de Vistas del Administrador ---");
            System.out.println("No. Vista | Nombre de la Vista");
            System.out.println("----------|-------------------");

            for (int i = 1; i <= contadorVistas; ++i) {
                System.out.printf("%9d | %s%n", i, vistasAdmin[i]);
            }

            System.out.println("-------------------------------------");
            System.out.println("Total de Vistas: " + contadorVistas);
        }
    }

    private static void comprarVista() {
        if (contadorVistas == 0) {
            System.out.println("El administrador aun no ha registrado ninguna vista para comprar.");
        } else {
            System.out.println("Generando 3 compras aleatorias");
            Random random = new Random();
            int comprasRealizadas = 0;

            for (int k = 0; k < 3; ++k) {
                int numVistaAleatorio = random.nextInt(contadorVistas) + 1;
                String nombreVista = vistasAdmin[numVistaAleatorio];
                boolean encontrada = false;

                for (int i = 0; i < contadorCompras; ++i) {
                    if (vistasComprador[i][0].equals(String.valueOf(numVistaAleatorio))) {
                        int cantidadActual = Integer.parseInt(vistasComprador[i][2]);
                        vistasComprador[i][2] = String.valueOf(cantidadActual + 1);
                        encontrada = true;
                        break;
                    }
                }

                if (!encontrada) {
                    if (contadorCompras >= vistasComprador.length) {
                        System.out.println("¡Límite de filas en el arreglo de compras alcanzado! No se pudo guardar mas.");
                        break;
                    }

                    vistasComprador[contadorCompras][0] = String.valueOf(numVistaAleatorio);
                    vistasComprador[contadorCompras][1] = nombreVista;
                    vistasComprador[contadorCompras][2] = "1";
                    ++contadorCompras;
                }

                ++comprasRealizadas;
                System.out.println("   - Compra " + (k + 1) + ": Vista No. " + numVistaAleatorio + " (" + nombreVista + ")");
            }

            if (comprasRealizadas > 0) {
                System.out.println("" + comprasRealizadas + " compras procesadas correctamente.");
                guardarVistasComprador(); // 👈 Guardar al final de las compras
            } else {
                System.out.println("No se pudo realizar ninguna compra.");
            }
        }
    }

    private static void imprimirVistasComprador() {
        if (contadorCompras == 0) {
            System.out.println("No se han realizado compras todavía.");
        } else {
            System.out.println("--- Reporte de Compras Realizadas ---");
            System.out.println("Codigo | Nombre de la Vista | Cantidad");
            System.out.println("-------|--------------------|---------");

            for (int i = 0; i < contadorCompras; ++i) {
                String codigo = vistasComprador[i][0];
                String nombre = vistasComprador[i][1];
                String cantidad = vistasComprador[i][2];
                System.out.printf("%6s | %18s | %8s%n", codigo, nombre, cantidad);
            }

            System.out.println("--------------------------------------------");
        }
    }
    private static void guardarVistasComprador() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_COMPRADOR))) {
            for (int i = 0; i < contadorCompras; i++) {
                pw.println(vistasComprador[i][0] + ";" +
                        vistasComprador[i][1] + ";" +
                        vistasComprador[i][2]);
            }
            System.out.println("Compras guardadas en **vistas_comprador.txt**.");
        } catch (IOException e) {
            System.out.println("Error al guardar las compras: " + e.getMessage());
        }
    }

    private static void guardarVistasAdmin() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_ADMIN))) {
            for (int i = 1; i <= contadorVistas; i++) {
                pw.println(i + ";" + vistasAdmin[i]);
            }
            System.out.println("Vistas de administrador guardadas en **vistas_admin.txt**.");
        } catch (IOException e) {
            System.out.println("Error al guardar las vistas del administrador: " + e.getMessage());
        }
    }

    private static void cargarVistasAdmin() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_ADMIN))) {
            int maxVistasLeidas = 0;
            String linea;
            while ((linea = br.readLine()) != null && maxVistasLeidas < 255) {
                String[] partes = linea.split(";");
                if (partes.length == 2) {
                    try {
                        int numVista = Integer.parseInt(partes[0].trim());
                        String nombreVista = partes[1].trim();
                        if (numVista >= 1 && numVista <= 255) {
                            vistasAdmin[numVista] = nombreVista;
                            if (numVista > maxVistasLeidas) {
                                maxVistasLeidas = numVista;
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Formato de número de vista inválido en el archivo Admin.");
                    }
                }
            }
            contadorVistas = maxVistasLeidas;
        } catch (FileNotFoundException e) {
            System.out.println("ℹArchivo de administrador **vistas_admin.txt** no encontrado. Iniciando vacío.");
        } catch (IOException e) {
            System.out.println("Error al cargar las vistas del administrador: " + e.getMessage());
        }
    }

    private static void cargarVistasComprador() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_COMPRADOR))) {
            int fila = 0;
            String linea;
            while ((linea = br.readLine()) != null && fila < vistasComprador.length) {
                String[] partes = linea.split(";");
                if (partes.length == 3) {
                    vistasComprador[fila][0] = partes[0].trim();
                    vistasComprador[fila][1] = partes[1].trim();
                    vistasComprador[fila][2] = partes[2].trim();
                    fila++;
                }
            }
            contadorCompras = fila;
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de compras **vistas_comprador.txt** no encontrado. Iniciando vacío.");
        } catch (IOException e) {
            System.out.println("Error al cargar las compras del comprador: " + e.getMessage());
        }
    }

    static {
        scanner = new Scanner(System.in);
    }
}