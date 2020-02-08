import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class ControllerInterfazProductos implements Controller {

    private BaseDatosProductos modelProductos;
    private InterfazProductos  viewProductos;

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
    public Object obtieneDatoDelView() {

        return null;
    } // End obtieneDatoDelView

    @Override
    public void actualizaElView() {

    } // End actualizaElView

    @Override
    public void solicitaActualizacionDelModel(String accion) {
        if (accion.equals("Eliminar")) {
            modelProductos.eliminaDatosDeLaEstructura(viewProductos.tablaProductos.getSelectedRow());
        } //End if
    } // End solicitaActualizacionDelModel

    @Override
    public void actionPerformed(ActionEvent evento) {

        JButton boton = (JButton) evento.getSource();

        //Si el boton eliminar fue el accionado
        if(boton == viewProductos.botonEliminarProducto)
        {
            //Revisar si hay datos en el model y si hay una fila seleccionada en la tabla
            if (modelProductos.hayDatos() && viewProductos.tablaProductos.getSelectedRow() != -1){

                //Proceder a la eliminacion
                solicitaActualizacionDelModel("Eliminar");
                actualizaElView();

            } //End if
        }//End if

    } //End actionPerformed

} //End class