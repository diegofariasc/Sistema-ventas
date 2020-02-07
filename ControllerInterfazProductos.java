import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ControllerInterfazProductos implements Controller, FocusListener {

    @Override
    public Object obtieneDatoDelModel(int indice) {

        return null;
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

    }

    @Override
    public void actionPerformed(ActionEvent evento) {

    } //End actionPerformed

    @Override
    public void focusGained(FocusEvent evento) {

    } //End focusGained

    @Override
    public void focusLost(FocusEvent evento) {

    } //End focusLost

} //End class