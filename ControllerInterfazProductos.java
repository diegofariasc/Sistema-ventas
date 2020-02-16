import java.awt.event.ActionEvent;
import javax.swing.JButton;


/**
 * La clase representa el controller para la clase (View) InterfazProductos
 * @author Diego Farias Castro
 */
public class ControllerInterfazProductos extends ControllerAbstracto {

    private InterfazProductos viewProductos;
    private String filtro;  

    /************************************************
    * Constructor de la clase
    *************************************************/    
    public ControllerInterfazProductos(BaseDatosProductos model, InterfazProductos view)
    {
        modelProductos  = model;
        viewProductos   = view;
    }//End constructor


    /************************************************
    * Implementacion de la interfaz controller
    *************************************************/    

    /**
     * El metodo obtiene el dato numero "indice" del model
     * @param indice Con el numero de dato que se requiere del model
     */
    @Override
    public Producto obtieneDatoDelModel(int indice) {
        return modelProductos.get(indice);
    } // End obtieneDatoDelModel


    /**
     * El metodo lee la columna seleccionada en el view y regresa 
     * todos sus datos empaquetados en un objeto de la clase Producto
     * @return Producto
     */
    @Override
    public Producto obtieneDatoDelView() {

        //Obtener indice seleccionado en la table
        int indice = viewProductos.tablaProductos.getSelectedRow();

        //Si no hay nada seleccionado (indice -1) devolver nulo
        if (indice < 0) return null;

        //Si no, recuperar la informacion del producto desde la tabla
        int     codigo       = (int)    viewProductos.tablaProductos.getValueAt(indice, 0);
        String  descripcion  = (String) viewProductos.tablaProductos.getValueAt(indice, 1);
        String  departamento = (String) viewProductos.tablaProductos.getValueAt(indice, 2);
        String  unidadVenta  = (String) viewProductos.tablaProductos.getValueAt(indice, 3);

        //Recuperar valores numericos, quitar signo de pesos y convertir a double **
        String  precioCompraStr = (String) viewProductos.tablaProductos.getValueAt(indice, 4);
        String  precioVentaStr  = (String) viewProductos.tablaProductos.getValueAt(indice, 5);
        String  disponibleStr   = (String) viewProductos.tablaProductos.getValueAt(indice, 6);

        precioCompraStr = precioCompraStr.replace("$", "");
        precioVentaStr  = precioVentaStr.replace("$", "");

        double  precioCompra = Double.parseDouble(precioCompraStr);
        double  precioVenta  = Double.parseDouble(precioVentaStr);
        double  disponible   = Double.parseDouble(disponibleStr);

        //Regresar un objeto Producto
        return new Producto(codigo, descripcion, departamento, unidadVenta, 
                            precioCompra, precioVenta, disponible);

    } // End obtieneDatoDelView


    /**
     * El metodo lee todos los productos almacenados en el model y los despliega
     * en la tabla del view. 
     * Nota: un producto solo es agregado al view si no hay un filtro establecido
     * o si la descripcion del producto contiene lo especificado en el filtro
     */
    @Override
    public void actualizaElView() {
        
        viewProductos.modeloTabla.setRowCount(0);   // Vaciar la tabla del view

        //Por cada producto en el modelProductos, agregar a la tabla del view
        for (int i =0; i<modelProductos.size();i++){

            Producto producto = obtieneDatoDelModel(i);

            /* Agregar unicamente a la tabla si no hay filtro establecido
            o si la descripcion del producto contiene lo especificado en el filtro*/
            if (filtro == null || producto.getDescripcion().toLowerCase().contains(filtro.toLowerCase()))
            
                viewProductos.modeloTabla.addRow(new Object[]{
                    producto.getCodigo(),
                    producto.getDescripcion(),
                    producto.getDepartamento(),
                    producto.getUnidadVentaStr(),
                    String.format("$%.2f",producto.getPrecioCompra()),
                    String.format("$%.2f",producto.getPrecioVenta()),
                    String.format("%.3f",producto.getCantidadDisponible()),
                    String.format("%.2f",modelProductos.procesa(i))+"%"
                }); //End addRow

                

        } //End for

    } // End actualizaElView


    /**
     * El metodo recibe una accion en formato string y la ejecuta
     * sobre el model
     * @param accion
     */
    @Override
    public void solicitaActualizacionDelModel(String accion) {

        //Si es necesario eliminar
        if (accion.startsWith("Eliminar")) {

            //Buscar el indice del producto basado en su codigo y proceder a eliminar
            int indice = buscarIndiceEnModel(obtieneDatoDelView().getCodigo());
            modelProductos.eliminaDatosDeLaEstructura( indice );
        } //End if

        //Si se solicita ordenar, llamar al metodo del model
        else if (accion.startsWith("Ordenar")){
            modelProductos.ordenaLaEstructura();
        } //End else if

        //Si se solicita cambiar la disponibilidad (por abastecimiento)
        else if (accion.startsWith("CambiarCantidad")) {
            
            /*Nota: la accion viene en dos partes ej. : "CambiarCantidad 4"
            la primera indica la accion y la segunda es un parametro que indica en cuanto
            debe incrementarse la cantidad*/

            //Obtener la magnitud en que se aumentara la cantidad
            double cantidad = Double.parseDouble(accion.split(" ")[1]);
    
            //Recuperar el producto seleccionado en el view y ajustar la cantidad
            Producto seleccionView = obtieneDatoDelView();
            seleccionView.setCantidadDisponible(seleccionView.getCantidadDisponible() + cantidad );

            //Modificar el model
            int indice = buscarIndiceEnModel(seleccionView.getCodigo());     //Obtener el indice
            modelProductos.modificaDatosEnLaEstructura(indice,seleccionView);        //Modificar model

        } //End if

        //Guardar automaticamente tras actualizar el model
        modelProductos.salvaDatosDeLaEstructuraAlRepositorio();

    } // End solicitaActualizacionDelModel


    /**
     * El metodo es llamado automaticamente cuando ocurre algun evento 
     * tipo Action en el view. Este, responde dependiendo el control que 
     * causo su invocacion
     * @param evento Con los datos del evento del cual deriva su invocacion
     */
    @Override
    public void actionPerformed(ActionEvent evento) {

        //Obtener referencia al causante del evento
        JButton boton = (JButton) evento.getSource();


        //Si es el boton de nuevo producto ****************************
        if (boton == viewProductos.botonNuevoProducto){

            //Guardar la cantidad de registros que hay actualmente en el model
            int datosEnModel = modelProductos.size();

            //Desplegar la interfaz de agregar producto
            mostrarInterfazAgregarProducto();

            //Ver si se registraron cambios en el model y de ser asi, actualizar el view
            if (modelProductos.obtenerTamano() > datosEnModel)
                actualizaElView();

        } //End if 

        //Si es el boton de editar producto ****************************
        else if (boton == viewProductos.botonEditarProducto){

            //Obtener el producto seleccionado en el view
            Producto seleccionEnView = obtieneDatoDelView();

            //Ver si el dato leido del view no es nulo y si hay datos en el model
            if (modelProductos.hayDatos() && obtieneDatoDelView() != null){

                //Lanzar la interfaz de editar producto y actualizar el view al concluir
                mostrarInterfazEditarProducto(seleccionEnView);
                actualizaElView();

            } //End if 

        } //End if

        //Si el boton eliminar fue el accionado ****************************
        else if (boton == viewProductos.botonEliminarProducto)
        {
            //Revisar si hay datos en el model y si hay una fila seleccionada en la tabla
            if (modelProductos.hayDatos() && obtieneDatoDelView() != null){

                Dialogo dialogo = new Dialogo(  "Eliminacion de un articulo",
                                                "¿Confirma que desea eliminar el articulo seleccionado?",
                                                Dialogo.MENSAJE_ADVERTENCIA);
                dialogo.iniciarDialogo();

                if (dialogo.seAceptaLaAccion()){
                    //Proceder a la eliminacion
                    solicitaActualizacionDelModel("Eliminar");
                    actualizaElView();
                } //End if 
            } //End if
        }//End if

        //Si es el boton de abastecer ****************************
        else if (boton == viewProductos.botonRegistrarAbastecimientoProducto){

            //Obtener el producto seleccionado del view
            Producto productoSeleccionado = obtieneDatoDelView();

            //Ver si hay datos en el model y la seleccion en el view no es nula
            if (modelProductos.hayDatos() && productoSeleccionado != null){

                /*Crear el dialogo de abastecer y pasarle los datos de unidadVenta y descripcion
                solo con el fin de que el dialogo pueda informar a que producto va a agregarle cantidad*/
                DialogoAbastecer dialogo = new DialogoAbastecer(productoSeleccionado.getUnidadVenta(), 
                                                                productoSeleccionado.getDescripcion() );

                //Lanzar el dialogo
                dialogo.iniciarDialogo();

                //Si se acepto el registro de entrada al almacen, notificarlo al usuario 
                if (dialogo.seAceptaLaAccion()){

                    Dialogo informativo = new Dialogo(  "Registrar abastecimiento",
                                                    "Se ha registrado exitosamente la entrada del producto",
                                                    Dialogo.MENSAJE_INFORMATIVO);
                    informativo.iniciarDialogo();

                    //Actualizar el model y el view
                    solicitaActualizacionDelModel("CambiarCantidad "+ dialogo.getCantidadAgregar() );
                    actualizaElView();

                } //End if 

            } //End if

        } //End else if 

         //Si es el boton de buscar ****************************
        else if(boton == viewProductos.botonBuscar){

            //Establecer el filtro, mostrar el boton para quitarlo y actualizar el view
            filtro = viewProductos.campoFiltrar.getText();
            viewProductos.botonQuitarFiltro.setVisible(true);
            actualizaElView();

        } //End if 

         //Si es el boton de quitar filtro ****************************
        else if (boton == viewProductos.botonQuitarFiltro){

            //Anular el filtro, ocultar el boton de quitar filtro y actualizar el view
            filtro = null;
            viewProductos.botonQuitarFiltro.setVisible(false);
            actualizaElView();

        } //End if

        //Si es el boton de regresar****************************
        else if(boton == viewProductos.botonRegresar){
            viewProductos.ocultarInterfaz();
        } //End elseif 

        //Si es el boton de ordenar productos ****************************
        else if(boton == viewProductos.botonOrdenarProductos){

            //Actualizar el model (dejarlo en orden) y actualizar el view
            solicitaActualizacionDelModel("Ordenar");
            actualizaElView();

        } //End elseif 

    } //End actionPerformed


    /**
     * El metodo despliega la interfaz de agregar producto, creando
     * su view, controller y asociandolos
     */
    private void mostrarInterfazAgregarProducto(){

        //Crear la interfaz y su controller
        InterfazAgregarProducto interfazAgregarProducto = new InterfazAgregarProducto();
        ControllerInterfazAgregarProducto controllerAgregarProducto = new ControllerInterfazAgregarProducto(
            modelProductos, interfazAgregarProducto);

        //Asociar dialogo y controller
        interfazAgregarProducto.setActionListener(controllerAgregarProducto);
        interfazAgregarProducto.setFocusListener(controllerAgregarProducto);
        interfazAgregarProducto.setKeyListener(controllerAgregarProducto);

        //Lanzar la interfaz de agregar producto
        interfazAgregarProducto.iniciarInterfaz();

    } //End mostrarInterfazAgregarProducto


    /**
     * El metodo despliega la interfaz de editar producto, creando
     * su view, controller y asociandolos.
     * Requiere como argumento el producto seleccionado en el view InterfazProductos
     * para saber que producto se estara editando en el dialogo
     * @param productoSeleccionado Producto seleccionado en el view que se desea editar
     */
    private void mostrarInterfazEditarProducto(Producto productoSeleccionado){

        //Crear la interfaz y su controller
        InterfazEditarProducto interfazEditarProducto = new InterfazEditarProducto();
        ControllerInterfazEditarProducto controllerEditarProducto = new ControllerInterfazEditarProducto(
            modelProductos, interfazEditarProducto, productoSeleccionado);

        //Asociar dialogo y controller
        interfazEditarProducto.setActionListener(controllerEditarProducto);
        interfazEditarProducto.setFocusListener(controllerEditarProducto);
        interfazEditarProducto.setKeyListener(controllerEditarProducto);

        //Lanzar la interfaz de editar producto
        interfazEditarProducto.iniciarInterfaz();

    } //End mostrarInterfazEditarProducto

} //End class