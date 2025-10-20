package view;

import controller.Controlador;
import model.Articulo;
import model.Cliente;
import model.Pedido;

import java.util.List;
import java.util.Scanner;
/*
 * Clase destinada a ser el menú principal
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
            System.out.println("\n---- MENÚ DE OPCIONES: ----");
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


    //METODOS PREGUNTA AL CONTROLADOR
    /**
     * Función para gestión de clientes, permite añadir nuevo cliente solicitando los datos, Se pasa un booleano con 1
     * true 0 false si es premium, así se puede añadir la cuota y el descuento. Además se muestra el listado de todos
     * los clientes, listado clientes estándar, listado clientes prémium.
     */
    public void gestionarClientes(){
        System.out.println("\n ¿Qué acción deseas realizar?: ");
        System.out.println("1. Añadir nuevo cliente");
        System.out.println("2. Mostrar todos los clientes");
        System.out.println("3. Mostrar los clientes estándar");
        System.out.println("4. Mostrar los clientes prémium");
        int opcion = askNumero(4);
        switch (opcion){
            case 1:
                System.out.println("---- Añadir nuevo cliente ----");
                System.out.print("Introduce el email >> ");
                String email = teclado.nextLine();
                System.out.print("Introduce el nombre >> ");
                String nombre = teclado.nextLine();
                System.out.print("Introduce el domicilio >> ");
                String domicilio = teclado.nextLine();
                System.out.print("Introduce el NIF >> ");
                String nif = teclado.nextLine();
                System.out.print("Si es cliente prémium pulsa 1 >> ");  //Se pasa un booleano con true si es premium, así se puede añadir la cuota y el descuento
                String entrada = teclado.nextLine();
                boolean premium = entrada.equals("1");

                controlador.añadirCliente(email, nombre, domicilio, nif,premium);
                break;
            case 2:
                controlador.mostrarClientes();
                break;
            case 3:
                controlador.mostrarClientesEstandar();
                break;
            case 4:
                controlador.mostrarClientesPremium();
                break;
        }
    }

    /**
     * En esta función se solicita al usuário si desea añadir nuevo artículo, mostrar todos los artículos o buscar un artículo por código.
     * el controlador recibe una función con los campos necesarios para realizarla.
     */
    public void gestionarArticulos(){
        System.out.println("\n ¿Qué acción deseas realizar?: ");
        System.out.println("1. Añadir nuevo artículo");
        System.out.println("2. Mostrar artículos");
        System.out.println("3. Buscar artículo");
        int opcion = askNumero(3);

        switch (opcion){
            case 1:
                System.out.println("---- Añadir nuevo artículo ----");
                System.out.print("Introduce el código >> ");
                String codigo = teclado.nextLine();
                System.out.print("Introduce la descripción >> ");
                String descripcion = teclado.nextLine();
                System.out.print("Introduce el precio de venta >> ");
                double precioVenta = Double.parseDouble(teclado.nextLine());
                System.out.print("Introduce el precio de envío >> ");
                double gastosEnvio = Double.parseDouble(teclado.nextLine());
                System.out.print("Introduce el tiempo de preparación >> ");
                int tiempoPreparacion = Integer.parseInt(teclado.nextLine());

                controlador.añadirArticulo(codigo,descripcion,precioVenta,gastosEnvio,tiempoPreparacion);
                break;

            case 2:
                controlador.mostrarArticulos();
                break;

            case 3:
                System.out.print("Introduce el código del artículo a buscar >> ");
                String codigoBuscar = teclado.nextLine();

                controlador.buscarArticulo(codigoBuscar);
                break;
        }
    }

    /**
     * Esta función gestiona los pedidos, añade un nuevo pedido (sin solicitar fecha ni estado), elimina pedidos con el
     * nuemro de pedido, muestra los pedidos pendientes y los pedidos enviados.
     */
    public void gestionarPedidos(){
        System.out.println("\n ¿Qué acción deseas realizar?: ");
        System.out.println("1. Añadir nuevo pedido");
        System.out.println("2. Eliminar pedido");
        System.out.println("3. Mostrar pedidos pendientes");
        System.out.println("4. Mostrar pedidos enviados");
        int opcion = askNumero(4);

        switch (opcion){
            case 1:
                System.out.println("---- Añadir nuevo pedido ----");
                System.out.print("Introduce el número de pedido >> ");
                String numeroPedido = teclado.nextLine();
                System.out.print("Introduce el email del cliente >> ");
                String cliente = teclado.nextLine();
                System.out.print("Introduce el código del artículo >> ");
                String articulo = teclado.nextLine();
                System.out.print("Introduce la cantidad >> ");
                int cantidad = Integer.parseInt(teclado.nextLine());

                controlador.añadirPedido(numeroPedido,cliente,articulo,cantidad);
                break;
            case 2:
                System.out.print("Introduce el número de pedido >> ");
                String numeroPedidoBorrar = teclado.nextLine();

                controlador.eliminarPedido(numeroPedidoBorrar);
                break;
            case 3:
                controlador.mostarPedidosPendientes();
                break;
            case 4:
                controlador.mostrarPedidosEnviados();
                break;

        }
    }



    //METODOS RESPUESTA DEL CONTROLADOR

    //Gestión clientes
    /**
     * muestra mensaje cliente añadido correctamente
     */
    public void clienteAñadido(){
        System.out.println("Cliente añadido correctamente");
    }

    /**
     * muestra mensaje si no hay clientes registrados y lista clientes si hay.
     * @param lista
     */
    public void mostrarListaClientes(List<Cliente> lista){
        if (lista.isEmpty()){
            System.out.println("No hay clientes registrados.");
        }else{
            System.out.println("Listado de clientes: ");
            for ( Cliente cliente : lista ){
                System.out.println(cliente.toString());
            }
        }
    }

    /**
     * muestra mensaje si no hay clientes registrados y lista clientes estandar si hay.
     * @param listaStandar
     */
    public void mostarListaClientesEstandar(List<Cliente> listaStandar){
        if (listaStandar.isEmpty()){
            System.out.println("No hay clientes estándar registrados.");
        }else{
            System.out.println("Listado de clientes estándar: ");
            for ( Cliente cliente : listaStandar){
                System.out.println(cliente.toString());
            }
        }
    }

    /**
     * muestra mensaje si no hay clientes registrados y lista clientes estandar si hay.
     * @param listaPremium
     */
    public void mostrarListaClientesPremium(List<Cliente> listaPremium){
        if (listaPremium.isEmpty()){
            System.out.println("No hay clientes premium registrados.");
        }else{
            System.out.println("Listado de clientes premium: ");
            for ( Cliente cliente : listaPremium){
                System.out.println(cliente.toString());
            }
        }
    }


    //Gestión Artículos

    /**
     * muestra mensaje de cliente añadido correctamente
     */
    public void articuloAñadido(){
        System.out.println("Artículo añadido correctamente");
    }

    /**
     * mostrar lista de articulos si hay registrados.
     * @param lista
     */
    public void mostrarListaArticulos(List<Articulo> lista){
        if (lista.isEmpty()){
            System.out.println("No hay artículos registrados.");
        }else{
            System.out.println("Listado de artículos: ");

            for (Articulo articulo : lista){
                System.out.println(articulo.toString());
            }
        }
    }

    /**
     * devuelve el artículo buscado por el código o un mensaje de artículo no encontrado.
     * @param articulo
     */
    public void articuloBuscado(Articulo articulo){
        if (articulo == null){
            System.out.println("Artículo no encontrado, revisa el código introducido.");
        }else{
            System.out.println(articulo.toString());
        }
    }

    //Gestión Pedidos

    /**
     * mensaje de pedido añadido correctamente
     */
    public void pedidoAñadido(){
        System.out.println("Pedido añadido correctamente.");
    }

    /**
     * mensaje de pedido eliminado correctamente
     */
    public void pedidoEliminado(){
        System.out.println("Pedido eliminado correctamente.");
    }

    /**
     * muestra los pedidos pendientes si hay.
     * @param pedidosPendientes
     */
    public void mostrarListaPedidosPendientes(List<Pedido> pedidosPendientes){
        if (pedidosPendientes.isEmpty()){
            System.out.println("No hay pedidos pendientes.");
        }else{
            System.out.println("Listado de pedidos pendientes: ");

            for (Pedido pedido : pedidosPendientes){
                System.out.println(pedido.toString());
            }
        }
    }

    /**
     * muestra los pedidos enviados si hay.
     * @param pedidosEnviados
     */
    public void mostrarListaPedidosEnviados(List<Pedido> pedidosEnviados){
        if (pedidosEnviados.isEmpty()){
            System.out.println("No hay pedidos pendientes.");
        }else{
            System.out.println("Listado de pedidos pendientes: ");

            for (Pedido pedido : pedidosEnviados){
                System.out.println(pedido.toString());
            }
        }
    }


    //OTROS METODOS

    /**
     * muestra el mensaje de error genérico al usuario.
     * El controller llamará a este método cuando ocurrar una excepción.
     * @param message
     */
    public void mostrarError(String message){
        System.out.println("\nERROR:");
        System.out.println(message);
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
            System.out.print("\nIntroduce el índice >> ");
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
