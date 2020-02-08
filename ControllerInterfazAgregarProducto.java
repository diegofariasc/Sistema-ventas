import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ControllerInterfazAgregarProducto implements Controller, FocusListener {

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


    
    @Override
    public void focusGained(FocusEvent e) {

    } //End focusGained

    @Override
    public void focusLost(FocusEvent e) {

    } //End focusLost

} //End ControllerInterfazAgregarProducto