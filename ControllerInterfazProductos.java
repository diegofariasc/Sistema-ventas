import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class ControllerInterfazProductos implements Controller {

    private BaseDatosProductos      modelProductos;
    private InterfazProductos       viewProductos;


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

        //Ver si no hay ninguna columna seleccionada (el indice es -1), entonces
        //devolver null. De lo contrario regresar el indice 
        return  columnaSeleccionada < 0 ? null : columnaSeleccionada ;

    } // End obtieneDatoDelView

    @Override
    public void actualizaElView() {
        
        viewProductos.modeloTabla.setRowCount(0);   // Vaciar la tabla del view

        //Por cada producto en el modelProductos, agregar a la tabla del view
        for (int i =0; i<modelProductos.size();i++){
            Producto producto = obtieneDatoDelModel(i);
             viewProductos.modeloTabla.addRow(new Object[]{
                producto.getCodigo(),
                producto.getDescripcion(),
                producto.getDepartamento(),
                convertirUnidadVentaAString(producto.getUnidadVenta()),
                String.format("$%.2f",producto.getPrecioCompra()),
                String.format("$%.2f",producto.getPrecioVenta()),
                String.format("%.3f",producto.getCantidadDisponible()),
             }); //End addRow
        } //End for

    } // End actualizaElView

    @Override
    public void solicitaActualizacionDelModel(String accion) {
        if (accion.equals("Eliminar")) {
            modelProductos.eliminaDatosDeLaEstructura(obtieneDatoDelView());
        } //End if

        //Guardar automaticamente tras actualizar el model
        modelProductos.salvaDatosDeLaEstructuraAlRepositorio();

    } // End solicitaActualizacionDelModel

    @Override
    public void actionPerformed(ActionEvent evento) {

        JButton boton = (JButton) evento.getSource();

        if (boton == viewProductos.botonNuevoProducto){

            //Crear el dialogo y su controller
            InterfazAgregarProducto dialogoAgregarProducto = new InterfazAgregarProducto();
            ControllerInterfazAgregarProducto controllerAgregarProducto = new ControllerInterfazAgregarProducto(
                modelProductos, dialogoAgregarProducto);

            //Asociar dialogo y controller
            dialogoAgregarProducto.setActionListener(controllerAgregarProducto);
            dialogoAgregarProducto.setFocusListener(controllerAgregarProducto);
            dialogoAgregarProducto.setKeyListener(controllerAgregarProducto);

            //Guardar la cantidad de informacion en el model
            int datosEnModel = modelProductos.size();

            //Lanzar la interfaz de agregar producto
            dialogoAgregarProducto.iniciarInterfaz();

            //Ver si se registraron cambios en el model y de ser asi, actualizar el view
            if (modelProductos.size() > datosEnModel)
                actualizaElView();

        } //End if

        //Si el boton eliminar fue el accionado
        if(boton == viewProductos.botonEliminarProducto)
        {
            //Revisar si hay datos en el model y si hay una fila seleccionada en la tabla
            if (modelProductos.hayDatos() && obtieneDatoDelView() != null){

                //Proceder a la eliminacion
                solicitaActualizacionDelModel("Eliminar");
                actualizaElView();

            } //End if
        }//End if

    } //End actionPerformed

    private String convertirUnidadVentaAString(int unidadVentaInt){
        switch (unidadVentaInt){
            case 0:  return "Pieza";
            case 1:  return "Kilo";
            case 2:  return "Paquete";
            case 3:  return "Caja";
            case 4:  return "Litro";
            default: return "Desconocido";
        } //End switch
    } // End convertirUnidadVentaAString

} //End class