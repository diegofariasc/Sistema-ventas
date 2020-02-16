import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class ControllerInterfazProductos implements Controller {

    private BaseDatosProductos      modelProductos;
    private InterfazProductos       viewProductos;

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
    @Override
    public Producto obtieneDatoDelModel(int indice) {
        return modelProductos.get(indice);
    } // End obtieneDatoDelModel

    @Override
    public Integer obtieneDatoDelView() {

        int columnaSeleccionada = viewProductos.tablaProductos.getSelectedRow();

        if (columnaSeleccionada < 0) return null;
        
        for (int i=0; i<modelProductos.size(); i++){
            if (obtieneDatoDelModel(i).getCodigo() == (int) viewProductos.modeloTabla.getValueAt(columnaSeleccionada,0))
                return i;
        } //End for

        return null;

    } // End obtieneDatoDelView

    @Override
    public void actualizaElView() {
        
        viewProductos.modeloTabla.setRowCount(0);   // Vaciar la tabla del view

        //Por cada producto en el modelProductos, agregar a la tabla del view
        for (int i =0; i<modelProductos.size();i++){

            Producto producto = obtieneDatoDelModel(i);

            if (filtro == null || producto.getDescripcion().toLowerCase().contains(filtro.toLowerCase()))
            
                viewProductos.modeloTabla.addRow(new Object[]{
                    producto.getCodigo(),
                    producto.getDescripcion(),
                    producto.getDepartamento(),
                    producto.getUnidadVentaString(),
                    String.format("$%.2f",producto.getPrecioCompra()),
                    String.format("$%.2f",producto.getPrecioVenta()),
                    String.format("%.3f",producto.getCantidadDisponible()),
                }); //End addRow

        } //End for

    } // End actualizaElView

    @Override
    public void solicitaActualizacionDelModel(String accion) {

        if (accion.startsWith("Eliminar")) {
            modelProductos.eliminaDatosDeLaEstructura(obtieneDatoDelView());
        } //End if

        else if (accion.startsWith("Ordenar")){
            modelProductos.ordenaLaEstructura();
        } //End else if

        else if (accion.startsWith("CambiarCantidad")) {
            
            double cantidad = Double.parseDouble(accion.split(" ")[1]);
    
            //Recuperar un producto actualizarlo
            Producto producto =  obtieneDatoDelModel(obtieneDatoDelView());
            producto.setCantidadDisponible(producto.getCantidadDisponible() + cantidad );

            //Modificar el model
            modelProductos.modificaDatosEnLaEstructura(obtieneDatoDelView(),producto);

        } //End if

        //Guardar automaticamente tras actualizar el model
        modelProductos.salvaDatosDeLaEstructuraAlRepositorio();

    } // End solicitaActualizacionDelModel

    @Override
    public void actionPerformed(ActionEvent evento) {

        JButton boton = (JButton) evento.getSource();

        if (boton == viewProductos.botonNuevoProducto){

            //Guardar la cantidad de informacion en el model
            int datosEnModel = modelProductos.size();

            mostrarInterfazAgregarProducto();

            //Ver si se registraron cambios en el model y de ser asi, actualizar el view
            if (modelProductos.size() > datosEnModel)
                actualizaElView();

        } //End if


        else if (boton == viewProductos.botonEditarProducto){

            if (modelProductos.hayDatos() && obtieneDatoDelView() != null){
                mostrarInterfazEditarProducto();
                actualizaElView();
            } //End if 
        } //End if


        //Si el boton eliminar fue el accionado
        else if (boton == viewProductos.botonEliminarProducto)
        {
            //Revisar si hay datos en el model y si hay una fila seleccionada en la tabla
            if (modelProductos.hayDatos() && obtieneDatoDelView() != null){

                Dialogo dialogo = new Dialogo(  "Eliminacion de un articulo",
                                                "¿Confirma que desea eliminar el articulo seleccionado?",
                                                Dialogo.MENSAJE_ADVERTENCIA);
                dialogo.iniciarInterfaz();

                if (dialogo.seAceptaLaAccion()){
                    //Proceder a la eliminacion
                    solicitaActualizacionDelModel("Eliminar");
                    actualizaElView();
                } //End if 
            } //End if
        }//End if

        else if (boton == viewProductos.botonRegistrarAbastecimientoProducto){

            if (modelProductos.hayDatos() && obtieneDatoDelView() != null){

                Producto producto = obtieneDatoDelModel( obtieneDatoDelView() );

                DialogoAbastecer dialogo = new DialogoAbastecer(producto.getUnidadVenta(), producto.getDescripcion() );
                dialogo.iniciarInterfaz();

                if (dialogo.seAceptaLaAccion()){

                    Dialogo informativo = new Dialogo(  "Registrar abastecimiento",
                                                    "Se ha registrado exitosamente la entrada del producto",
                                                    Dialogo.MENSAJE_INFORMATIVO);
                    informativo.iniciarInterfaz();

                    //Proceder a la eliminacion
                    solicitaActualizacionDelModel("CambiarCantidad "+ dialogo.getCantidadAgregar() );
                    actualizaElView();



                } //End if 
            } //End if

        } //End else if 

        else if(boton == viewProductos.botonBuscar){
            filtro = viewProductos.campoFiltrar.getText();
            viewProductos.botonQuitarFiltro.setVisible(true);
            actualizaElView();
        } //End if 

        else if (boton == viewProductos.botonQuitarFiltro){
            filtro = null;
            viewProductos.botonQuitarFiltro.setVisible(false);
            actualizaElView();
        } //End if

        else if(boton == viewProductos.botonRegresar){
            viewProductos.ocultarInterfaz();
        } //End elseif 

        else if(boton == viewProductos.botonOrdenarProductos){
            solicitaActualizacionDelModel("Ordenar");
            actualizaElView();
        } //End elseif 

    } //End actionPerformed


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


    private void mostrarInterfazEditarProducto(){

        //Crear la interfaz y su controller
        InterfazEditarProducto interfazEditarProducto = new InterfazEditarProducto( obtenerFi );
        ControllerInterfazEditarProducto controllerEditarProducto = new ControllerInterfazEditarProducto(
            modelProductos, interfazEditarProducto);

        //Asociar dialogo y controller
        interfazEditarProducto.setActionListener(controllerEditarProducto);
        interfazEditarProducto.setFocusListener(controllerEditarProducto);
        interfazEditarProducto.setKeyListener(controllerEditarProducto);

        //Lanzar la interfaz de agregar producto
        interfazEditarProducto.iniciarInterfaz();

    } //End mostrarInterfazAgregarProducto

} //End class