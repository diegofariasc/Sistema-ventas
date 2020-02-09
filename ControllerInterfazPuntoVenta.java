import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class ControllerInterfazPuntoVenta implements Controller {

    private BaseDatosProductos modelProductos;
    private InterfazPuntoVenta viewPuntoVenta;

    /************************************************
    * Constructor de la clase
    *************************************************/
    public ControllerInterfazPuntoVenta(BaseDatosProductos model, InterfazPuntoVenta view) {
        modelProductos = model;
        viewPuntoVenta = view;
    }// End constructor

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

} //End class