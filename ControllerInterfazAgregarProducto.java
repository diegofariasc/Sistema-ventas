import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ControllerInterfazAgregarProducto implements Controller, FocusListener {

    private BaseDatosProductos      modelProductos;
    private InterfazAgregarProducto viewProductos;

    /************************************************
    * Constructor de la clase
    *************************************************/    
    public ControllerInterfazAgregarProducto(BaseDatosProductos model, InterfazAgregarProducto view)
    {
        modelProductos  = model;
        viewProductos   = view;
    }//End constructor


    /************************************************
    * Implementacion de la interfaz controller
    *************************************************/        
    @Override
    public Object obtieneDatoDelModel(int indice) {
        return null;
    } //End obtieneDatoDelModel

    @Override
    public Object obtieneDatoDelView() {
        return null;
    } //End obtieneDatoDelView

    @Override
    public void actualizaElView() {

    } //End actualizaElView

    @Override
    public void solicitaActualizacionDelModel(String accion) {

    } //End solicitaActualizacionDelModel

    @Override
    public void actionPerformed(ActionEvent evento) {

    } //End actionPerformed


    /************************************************
    * Implementacion de la interfaz FocusListener
    *************************************************/    
    @Override
    public void focusGained(FocusEvent e) {

    } //End focusGained

    @Override
    public void focusLost(FocusEvent e) {

    } //End focusLost

} //End ControllerInterfazAgregarProducto