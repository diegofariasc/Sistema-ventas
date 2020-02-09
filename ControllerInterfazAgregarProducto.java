import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JButton;

public class ControllerInterfazAgregarProducto implements Controller, FocusListener {

    private BaseDatosProductos      modelProductos;
    private InterfazAgregarProducto viewAgregarProducto;

    /************************************************
    * Constructor de la clase
    *************************************************/    
    public ControllerInterfazAgregarProducto(BaseDatosProductos model, InterfazAgregarProducto view)
    {
        modelProductos      = model;
        viewAgregarProducto = view;
    }//End constructor


    /************************************************
    * Implementacion de la interfaz controller
    *************************************************/        
    @Override
    public Object obtieneDatoDelModel(int indice) {
        return modelProductos.get(indice);
    } //End obtieneDatoDelModel

    @Override
    public Producto obtieneDatoDelView() {
        
        //Crear un nuevo producto
        Producto producto = new Producto(

            //Recibiendo como argumentos el contenido del view 
            Integer.parseInt(viewAgregarProducto.campoCodigo.getText()),
            viewAgregarProducto.campoDescripcion.getText(),
            viewAgregarProducto.campoDepartamento.getText(),
            viewAgregarProducto.campoUnidadVenta.getSelectedIndex(),
            Double.parseDouble(viewAgregarProducto.campoPrecioCompra.getText()),
            Double.parseDouble(viewAgregarProducto.campoPrecioVenta.getText()),
            Double.parseDouble(viewAgregarProducto.campoDisponibilidad.getText())

        ); //End producto 

        return producto;
    
    } //End obtieneDatoDelView

    @Override
    public void actualizaElView() {

    } //End actualizaElView

    @Override
    public void solicitaActualizacionDelModel(String accion) {

    } //End solicitaActualizacionDelModel

    @Override
    public void actionPerformed(ActionEvent evento) {

        JButton boton = (JButton) evento.getSource();

        if (boton == viewAgregarProducto.botonCancelar){
            
            viewAgregarProducto.ocultarInterfaz();
        } //End if        
        
    } //End actionPerformed


    /************************************************
    * Implementacion de la interfaz FocusListener
    *************************************************/    
    @Override
    public void focusGained(FocusEvent e) {
        System.out.println("Focus");
    } //End focusGained

    @Override
    public void focusLost(FocusEvent e) {

    } //End focusLost

} //End ControllerInterfazAgregarProducto