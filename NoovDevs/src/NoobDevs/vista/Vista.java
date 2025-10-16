package NoobDevs.vista;

import NoobDevs.controlador.Controlador;
import java.util.Scanner;
/*
 * Clase destinada a ser el menú
 */
public class Vista {
    private Controlador controlador;
    private Scanner teclado;

    public Vista(){
        this.teclado = new Scanner(System.in);
    }

    //Método para que el Main pase el controlador a Vista
    public void setControlador(Controlador controlador){
        this.controlador = controlador;
    }

    public void menu(){
        boolean salir = false;

        int opcion;

        do{
            System.out.println("---- MENÚ DE OPCIONES: ----");
            System.out.println("1.  Gestionar clientes");  // añadir cliente, mostrar clientes, mostrar clientes estandar, mostrar clientes premium
            System.out.println("2.  Gestionar Artículos"); //añadir artículo, mostrar artículo
            System.out.println("3.  Gestionar Pedidos"); // añadir pedido, eliminar pedido, mostrar pedidos pendientes, mostrar pedidos enviados.
            System.out.println("0.  Salir de la aplicación");
            opcion = askNumero(4);

            switch (opcion){
                case 1:
                    gestionarClientes();
                    break;
                case 2:
                    gestionarArticulos();
                    break;
                case 3:
                    gestionarPedidos();
                    break;
                case 0:
                    salir = true;
                    break;
            }

        }while(!salir);
    }

    public void gestionarClientes(){
        System.out.println(" ¿Qué acción deseas realizar?: ");
        System.out.println("1. Añadir nuevo cliente");
        System.out.println("2. Mostrar todos los clientes");
        System.out.println("3. Mostrar los clientes estándar");
        System.out.println("4. Mostrar los clientes prémium");
        int opcion = askNumero(4);
        switch (opcion){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;

        }

    }

    /**
     * En esta función se solicita al usuário si desea añadir nuevo artículo, mostrar todos los artículos o buscar un artículo por código.
     * el controlador recibe una función con los campos necesarios para realizarla.
     */
    public void gestionarArticulos(){
        System.out.println(" ¿Qué acción deseas realizar?: ");
        System.out.println("1. Añadir nuevo artículo");
        System.out.println("2. Mostrar artículos");
        System.out.println("3. Buscar artículo");
        int opcion = askNumero(3);

        switch (opcion){
            case 1:

                System.out.println("Introduce el código >> ");
                String codigo = teclado.nextLine();
                System.out.println("Introduce la descripción >> ");
                String descripcion = teclado.nextLine();
                System.out.println("Introduce el precio de venta >> ");
                double precioVenta = teclado.nextDouble();
                teclado.nextLine();
                System.out.println("Introduce el precio de envío >> ");
                double gastosEnvio = teclado.nextDouble();
                teclado.nextLine();
                System.out.println("Introduce el tiempo de preparación >> ");
                int tiempoPreparacion = teclado.nextInt();
                teclado.nextLine();

                //controlador.añadirArticulo(codigo,descripcion,precioVenta,gastosEnvio,tiempoPreparacion);
                break;

            case 2:
                System.out.println("Listado de artículos: ");

                //controlador.mostrarArticulos();
                break;

            case 3:
                System.out.println("Introduce el código del artículo a buscar >> ");
                String codigoBuscar = teclado.nextLine();

               // controlador.buscarArticulo(codigoBuscar);
                break;
        }
    }

    public void gestionarPedidos(){
        System.out.println(" ¿Qué acción deseas realizar?: ");
        System.out.println("1. Añadir nuevo pedido");
        System.out.println("2. Eliminar pedido");
        System.out.println("3. Mostrar pedidos pendientes");
        System.out.println("4. Mostrar pedidos enviados");
        int opcion = askNumero(4);

        switch (opcion){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;

        }
    }

    /**
     * Pide el íncide a buscar, comprueba que este sea un número válido.
     * @param max tamaño máximo de la lista que se quiere recorrer.
     * @return devuelve el índice de la lista introducido.
     */
    private int askNumero(int max){
        boolean valido = false;
        int indice = 0;

        while(!valido){
            System.out.println("Introduce el índice >> ");
            if (teclado.hasNextInt()){
                indice = teclado.nextInt();
                teclado.nextLine();
                valido = (indice >= 0 && indice <= max);

                if (!valido) {
                    System.out.println("El índice debe estar entre 0 y "+ max);
                }
            }else{
                System.out.println("Por favor introduce un índice válido");
                teclado.next();
            }
        }
        return indice;
    }
}
