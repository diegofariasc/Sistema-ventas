import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 * La clase representa el controller para la clase (View) InterfazEditarProducto
 * Implementa Controller (requerida) asi como KeyListener (para cambiar los estados
 * de validacion de los campos mientras el usuario escribe) y FocusListener (Para dar
 * formato a dos decimales en los campos numericos que lo ameriten)
 * @author Diego Farias Castro
 */
public class ControllerInterfazEditarProducto extends ControllerAbstracto implements KeyListener, FocusListener {

    private InterfazEditarProducto viewEditarProducto;

    // Variables para almacenar si lo ingresado en cada campo es valido
    private boolean campoDescripcionEsValido;
    private boolean campoDepartamentoEsValido;
    private boolean camposPrecioSonValidos;
    private boolean campoDisponibilidadEsValido;

    /************************************************
     * Constructor de la clase
     *************************************************/
    public ControllerInterfazEditarProducto(BaseDatosProductos model, InterfazEditarProducto view ,
                                            Producto datosParaView) {
        modelProductos = model;
        viewEditarProducto = view;

        //Inicializar campos del view 
        viewEditarProducto.campoCodigo.setText( String.valueOf(datosParaView.getCodigo() ));
        viewEditarProducto.campoDescripcion.setText( datosParaView.getDescripcion() ) ;
        viewEditarProducto.campoDepartamento.setText( datosParaView.getDepartamento() );
        viewEditarProducto.campoUnidadVenta.setSelectedIndex( datosParaView.getUnidadVenta() );
        viewEditarProducto.campoPrecioCompra.setText( String.valueOf(datosParaView.getPrecioCompra() ));
        viewEditarProducto.campoPrecioVenta.setText( String.valueOf(datosParaView.getPrecioVenta() ));
        viewEditarProducto.campoDisponibilidad.setText( String.valueOf(datosParaView.getCantidadDisponible() ));

        //Inicializar la validacion de los campos en true (los datos guardados en el model son validos)
        campoDescripcionEsValido = true;
        campoDepartamentoEsValido = true;
        camposPrecioSonValidos = true;
        campoDisponibilidadEsValido = true; 

    }// End constructor

    /************************************************
     * Implementacion de la interfaz controller
     *************************************************/

    /**
     * El metodo empaqueta el contenido de todos los campos del view en
     * un objeto de la clase Producto
     * @return Producto
     */
    @Override
    public Producto obtieneDatoDelView() {

        // Crear un objeto producto
        Producto producto = new Producto(

                // Recibiendo como argumentos el contenido del view
                Integer.parseInt(viewEditarProducto.campoCodigo.getText()),
                viewEditarProducto.campoDescripcion.getText(), viewEditarProducto.campoDepartamento.getText(),
                viewEditarProducto.campoUnidadVenta.getSelectedIndex(),
                Double.parseDouble(viewEditarProducto.campoPrecioCompra.getText()),
                Double.parseDouble(viewEditarProducto.campoPrecioVenta.getText()),
                Double.parseDouble(viewEditarProducto.campoDisponibilidad.getText())

        ); // End producto

        return producto;

    } // End obtieneDatoDelView


    /**
     * El metodo cambia las imagenes de los estados de validez 
     * de cada campo (View) dependiendo de si su contenido es apropiado
     */
    @Override
    public void actualizaElView() {

        /*Cada que un campo sea valido, cambiar el icono de validez al de correcto.
        de lo contrario, cambiarlo a incorrecto*/

        //Icono referente al campo descripcion
        if (campoDescripcionEsValido)
            viewEditarProducto.imagenValidezCampoDescripcion.setIcon(new ImageIcon("Iconos/Icono_correcto.png"));
        else
            viewEditarProducto.imagenValidezCampoDescripcion.setIcon(new ImageIcon("Iconos/Icono_incorrecto.png"));

        //Icono referente a los campos de precio
        if (camposPrecioSonValidos)
            viewEditarProducto.imagenValidezCampoPrecios.setIcon(new ImageIcon("Iconos/Icono_correcto.png"));
        else
            viewEditarProducto.imagenValidezCampoPrecios.setIcon(new ImageIcon("Iconos/Icono_incorrecto.png"));

        //Icono referente al campo departamento
        if (campoDepartamentoEsValido)
            viewEditarProducto.imagenValidezCampoDepartamento.setIcon(new ImageIcon("Iconos/Icono_correcto.png"));
        else
            viewEditarProducto.imagenValidezCampoDepartamento.setIcon(new ImageIcon("Iconos/Icono_incorrecto.png"));

        //Icono referente al campo de disponibilidad
        if (campoDisponibilidadEsValido)
            viewEditarProducto.imagenValidezCampoDisponibilidad.setIcon(new ImageIcon("Iconos/Icono_correcto.png"));
        else
            viewEditarProducto.imagenValidezCampoDisponibilidad.setIcon(new ImageIcon("Iconos/Icono_incorrecto.png"));
            
        //Activar el boton aceptar dependiendo de si todos los campos son validos o no
        viewEditarProducto.botonAceptar.setEnabled( campoDescripcionEsValido && 
                                                    camposPrecioSonValidos && campoDepartamentoEsValido && 
                                                    campoDisponibilidadEsValido);
    } // End actualizaElView

    
    /**
     * El metodo lleva a cabo la accion de actualizacion del model 
     */
    @Override
    public void solicitaActualizacionDelModel(String accion) {
        
        //Modificar datos del model
        if (accion.equals("Editar")){

            //Recuperar producto del view
            Producto producto = obtieneDatoDelView();   

            //Obtener su indice en el model
            int indice = buscarIndiceEnModel(producto.getCodigo());

            //Realizar la actualizacion en el model
            modelProductos.modificaDatosEnLaEstructura(indice,producto);
            modelProductos.salvaDatosDeLaEstructuraAlRepositorio();

        } //End if 
    
    } // End solicitaActualizacionDelModel


    /**
     * El metodo es llamado automaticamente cuando ocurre algun evento 
     * tipo Action en el view. Este, responde dependiendo el control que 
     * causo su invocacion
     * @param evento Con los datos del evento del cual deriva su invocacion
     */
    @Override
    public void actionPerformed(ActionEvent evento) {

        //Si el que acciono el evento es el campo de unidad de venta
        if (evento.getSource()== viewEditarProducto.campoUnidadVenta){

            /*Validar el campo de disponibilidad.
            Esto evita que si se cambia el valor del JComboBox unidadVenta despues de ingresar
            la disponibilidad se considere valido*/
            validarCampoDisponibilidad();
            actualizaElView();
            
        } //End if

        //De lo contrario, el evento que lo acciono fue uno de los botones
        else{
            
            JButton boton = (JButton) evento.getSource();

            //Si fue el boton cancelar
            if (boton == viewEditarProducto.botonCancelar) 
                viewEditarProducto.ocultarInterfaz();

            //Si fue el boton aceptar
            else{

                //Editar en el model, notificar al usuario y cerrar la ventana
                solicitaActualizacionDelModel("Editar");
                Dialogo dialogo = new Dialogo("Modificacion de un articulo",
                                              "Se ha modificado exitosamente el articulo",
                                              Dialogo.MENSAJE_INFORMATIVO);
                dialogo.iniciarDialogo();
                viewEditarProducto.ocultarInterfaz();
            } //End else 

        } //End else
    } // End actionPerformed



    /************************************************
     * Implementacion de la interfaz FocusListener
     *************************************************/

    /**
     * El metodo es llamado automaticamente cuando el usuario se enfoca 
     * (o activa) un control determinado
     * @param evento Con los datos del evento del cual deriva su invocacion
     */
    @Override
    public void focusGained(FocusEvent evento) {

        // Seleccionar todo el contenido del campo que fue enfocado
        JTextField campoEnfocado = (JTextField) evento.getSource();
        campoEnfocado.selectAll();

    } // End focusGained


    /**
     * El metodo es llamado automaticamente cuando el usuario desenfoca 
     * (o activa otro) control determinado
     * @param evento Con los datos del evento del cual deriva su invocacion
     */
    @Override
    public void focusLost(FocusEvent evento) {

        //Obtener el campo que fue desenfocado
        JTextField campoDesenfocado = (JTextField) evento.getSource();
        
        //Ver si el campo desenfocado es el precio de compra o venta
        if (campoDesenfocado == viewEditarProducto.campoPrecioCompra || 
            campoDesenfocado == viewEditarProducto.campoPrecioVenta)
        try{
            
            //Intentar aplicar formato a dos decimales
            campoDesenfocado.setText(String.format("%.2f",Double.parseDouble(campoDesenfocado.getText())));

        } //End try
        catch(NumberFormatException excepcion){} //En caso que no sea numero

    } // End focusLost

    /************************************************
    * Implementacion de la interfaz KeyListener
    *************************************************/

    /**
     * El metodo es llamado automaticamente cuando el despues 
     * de que el usuario presionara una tecla en un control determinado
     * @param evento Con los datos del evento del cual deriva su invocacion
     */
    @Override
    public void keyReleased(KeyEvent evento) {

        //Obtener el campo en que se escribio
        JTextField campoEnQueSeEscribio = (JTextField) evento.getSource();

        if (campoEnQueSeEscribio == viewEditarProducto.campoDescripcion) {

            // Validar el campo descripcion si la descripcion no esta en blanco
            campoDescripcionEsValido = !viewEditarProducto.campoDescripcion.getText().equals("");

        } // End if

        else if (campoEnQueSeEscribio == viewEditarProducto.campoPrecioCompra ||
                 campoEnQueSeEscribio == viewEditarProducto.campoPrecioVenta) {

            validarCamposPrecio();

        } // End if

        else if (campoEnQueSeEscribio == viewEditarProducto.campoDepartamento) {

            // Validar el campo departamento si no esta en blanco
            campoDepartamentoEsValido = !viewEditarProducto.campoDepartamento.getText().equals("");

        } // End if

        else if (campoEnQueSeEscribio == viewEditarProducto.campoDisponibilidad) {

            validarCampoDisponibilidad();

        } // End if

        actualizaElView();
    } //End keyReleased

    //Eventos no necesarios pero agregados para cumplir con la interface
    @Override
    public void keyPressed(KeyEvent evento) {} //End keyPressed

    @Override
    public void keyTyped(KeyEvent evento) {} // End keyTyped


    /************************************************
    * Metodos para validar campos
    *************************************************/

    /**
     * El metodo valida el contenido de los campos de precio 
     * de compra y de venta, verificando que ninguno sea negativo, que 
     * en efecto sean numeros (no letras) y que el precio de compra 
     * sea menor que el de venta
     */
    private void validarCamposPrecio(){
        try{

            /*Verificar si el precio de compra y venta son ambos mayores a cero
            y el precio de venta es mayor que el de compra*/
            
            double precioCompra = Double.parseDouble(viewEditarProducto.campoPrecioCompra.getText());
            double precioVenta  = Double.parseDouble(viewEditarProducto.campoPrecioVenta.getText());

            camposPrecioSonValidos = precioCompra > 0 && precioVenta > 0 && precioVenta > precioCompra;

        } //End try

        catch(NumberFormatException excepcion){
            
            //En caso que el usuario no escribiera un numero en alguno de los campos
            camposPrecioSonValidos = false;

        } //End catch

    } //End validarCamposPrecio


    /**
     * El metodo valida el contenido del campo de disponibilidad,
     * verificando que no sea negativo, y que su contenido sea numerico
     */
    private void validarCampoDisponibilidad(){
        try{

            /*Verificar si la disponibilidad es mayor que cero y coincide con la unidad
            de venta*/
            
            //Ver si la unidad de venta es fragmentable (puede incluir decimales)
            if (viewEditarProducto.campoUnidadVenta.getSelectedIndex()== Producto.KILO ||
                viewEditarProducto.campoUnidadVenta.getSelectedIndex()== Producto.LITRO ){

                //Extraer el valor de campo disponibilidad y parsearlo a double. Checar si >0
                double disponibilidadDecimal = Double.parseDouble(viewEditarProducto.campoDisponibilidad.getText());
                campoDisponibilidadEsValido = disponibilidadDecimal>0;
            } //End if

            else{

                //Extraer el valor de campo disponibilidad y parsearlo a int. Checar si >0
                int disponibilidadEntera = Integer.parseInt(viewEditarProducto.campoDisponibilidad.getText());
                campoDisponibilidadEsValido = disponibilidadEntera>0;
            } //End else

        } //End try

        catch(NumberFormatException excepcion){
            
            //En caso que el usuario no escribiera un valor valido en el campo de disponibilidad
            campoDisponibilidadEsValido = false;

        } //End catch

    } //End validarCamposPrecio

} //End ControllerInterfazEditarProducto