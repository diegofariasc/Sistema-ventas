import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 * La clase representa el controller para la clase (View) InterfazAgregarProducto
 * Implementa Controller (requerida) asi como KeyListener (para cambiar los estados
 * de validacion de los campos mientras el usuario escribe) y FocusListener (Para dar
 * formato a dos decimales en los campos numericos que lo ameriten)
 * @author Diego Farias Castro
 */
public class ControllerInterfazAgregarProducto extends ControllerAbstracto implements KeyListener, FocusListener {

    private InterfazAgregarProducto viewAgregarProducto;

    // Variables para almacenar si lo ingresado en cada campo es valido
    private boolean campoCodigoEsValido;
    private boolean campoDescripcionEsValido;
    private boolean campoDepartamentoEsValido;
    private boolean camposPrecioSonValidos;
    private boolean campoDisponibilidadEsValido;


    /************************************************
     * Constructor de la clase
     *************************************************/
    public ControllerInterfazAgregarProducto(BaseDatosProductos model, InterfazAgregarProducto view) {
        modelProductos = model;
        viewAgregarProducto = view;
    }// End constructor


    /************************************************
     * Implementacion de la interfaz controller
     *************************************************/

    /**
     * El metodo empaqueta el contenido del view en un objeto
     * de la clase producto
     * @return Producto
     */
    @Override
    public Producto obtieneDatoDelView() {

        // Crear un nuevo producto
        Producto producto = new Producto(

                // Recibiendo como argumentos el contenido del view
                Integer.parseInt(viewAgregarProducto.campoCodigo.getText()),
                viewAgregarProducto.campoDescripcion.getText(), viewAgregarProducto.campoDepartamento.getText(),
                viewAgregarProducto.campoUnidadVenta.getSelectedIndex(),
                Double.parseDouble(viewAgregarProducto.campoPrecioCompra.getText()),
                Double.parseDouble(viewAgregarProducto.campoPrecioVenta.getText()),
                Double.parseDouble(viewAgregarProducto.campoDisponibilidad.getText())

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

        //Icono referente al campo codigo
        if (campoCodigoEsValido)
            viewAgregarProducto.imagenValidezCampoCodigo.setIcon(new ImageIcon("Iconos/Icono_correcto.png"));
        else
            viewAgregarProducto.imagenValidezCampoCodigo.setIcon(new ImageIcon("Iconos/Icono_incorrecto.png"));


        //Icono referente al campo descripcion
        if (campoDescripcionEsValido)
            viewAgregarProducto.imagenValidezCampoDescripcion.setIcon(new ImageIcon("Iconos/Icono_correcto.png"));
        else
            viewAgregarProducto.imagenValidezCampoDescripcion.setIcon(new ImageIcon("Iconos/Icono_incorrecto.png"));

        //Icono referente a los campos de precio
        if (camposPrecioSonValidos)
            viewAgregarProducto.imagenValidezCampoPrecios.setIcon(new ImageIcon("Iconos/Icono_correcto.png"));
        else
            viewAgregarProducto.imagenValidezCampoPrecios.setIcon(new ImageIcon("Iconos/Icono_incorrecto.png"));

        //Icono referente al campo departamento
        if (campoDepartamentoEsValido)
            viewAgregarProducto.imagenValidezCampoDepartamento.setIcon(new ImageIcon("Iconos/Icono_correcto.png"));
        else
            viewAgregarProducto.imagenValidezCampoDepartamento.setIcon(new ImageIcon("Iconos/Icono_incorrecto.png"));

        //Icono referente al campo de disponibilidad
        if (campoDisponibilidadEsValido)
            viewAgregarProducto.imagenValidezCampoDisponibilidad.setIcon(new ImageIcon("Iconos/Icono_correcto.png"));
        else
            viewAgregarProducto.imagenValidezCampoDisponibilidad.setIcon(new ImageIcon("Iconos/Icono_incorrecto.png"));
            
        //Activar el boton aceptar dependiendo de si todos los campos son validos o no
        viewAgregarProducto.botonAceptar.setEnabled(campoCodigoEsValido && campoDescripcionEsValido && 
                                                    camposPrecioSonValidos && campoDepartamentoEsValido && 
                                                    campoDisponibilidadEsValido);


    } // End actualizaElView


    /**
     * El metodo solicita actualizar (agregar) un dato al model
     * @param accion A realizar en el model
     */
    @Override
    public void solicitaActualizacionDelModel(String accion) {
        
        //Agregar datos al model
        if (accion.equals("Agregar")){
            modelProductos.agregaDatosALaEstructura(modelProductos.size(),obtieneDatoDelView());
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
        if (evento.getSource()== viewAgregarProducto.campoUnidadVenta){

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
            if (boton == viewAgregarProducto.botonCancelar) 
                viewAgregarProducto.ocultarInterfaz();

            //Si fue el boton aceptar
            else{

                //Agregar al model y cerrar la ventana
                solicitaActualizacionDelModel("Agregar");
                Dialogo dialogo = new Dialogo("Registro de un nuevo articulo",
                                              "Se ha agregado exitosamente el articulo",
                                              Dialogo.MENSAJE_INFORMATIVO);
                dialogo.iniciarDialogo();
                viewAgregarProducto.ocultarInterfaz();
            } //End else 

        } //End else
    } // End actionPerformed


    /************************************************
     * Implementacion de la interfaz FocusListener
     *************************************************/
    @Override
    public void focusGained(FocusEvent evento) {

        // Seleccionar todo el contenido del campo que fue enfocado
        JTextField campoEnfocado = (JTextField) evento.getSource();
        campoEnfocado.selectAll();

    } // End focusGained

    //Evento no necesario pero agregado para cumplir con la interface
    @Override
    public void focusLost(FocusEvent evento) {

        //Obtener el campo que fue desenfocado
        JTextField campoDesenfocado = (JTextField) evento.getSource();
        
        //Ver si el campo desenfocado es el precio de compra o venta
        if (campoDesenfocado == viewAgregarProducto.campoPrecioCompra || 
            campoDesenfocado == viewAgregarProducto.campoPrecioVenta)
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

        //Fue el campo codigo
        if (campoEnQueSeEscribio == viewAgregarProducto.campoCodigo) {
            validarCampoCodigo();
        } // End if

        //Fue el campo descripcion
        else if (campoEnQueSeEscribio == viewAgregarProducto.campoDescripcion) {

            // Validar el campo descripcion si la descripcion no esta en blanco
            campoDescripcionEsValido = !viewAgregarProducto.campoDescripcion.getText().equals("");

        } // End if

        //Fue alguno de los campos de precios
        else if (campoEnQueSeEscribio == viewAgregarProducto.campoPrecioCompra ||
                 campoEnQueSeEscribio == viewAgregarProducto.campoPrecioVenta) {

            validarCamposPrecio();

        } // End if

        //Fue el campo de departamento
        else if (campoEnQueSeEscribio == viewAgregarProducto.campoDepartamento) {

            // Validar el campo departamento si no esta en blanco
            campoDepartamentoEsValido = !viewAgregarProducto.campoDepartamento.getText().equals("");

        } // End if

        //Fue el campo de disponibilidad 
        else if (campoEnQueSeEscribio == viewAgregarProducto.campoDisponibilidad) {

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
    * Metodos de la clase para validar campos
    *************************************************/

   /**
     * El metodo valida el contenido de los campos de codigo de barras  
     * de compra y de venta, verificando que no sea negativo y que 
     * en efecto sea numerico
     */
    private void validarCampoCodigo(){
        //Iniciar indicando que el contenido del campo es valido
        campoCodigoEsValido = true;
            
        try{

            int codigoBarras = Integer.parseInt(viewAgregarProducto.campoCodigo.getText());

            //Verificar si el codigo de barras es mayor que 0
            if (codigoBarras<0)
                campoCodigoEsValido = false;

            // Si no: 
            else{
                
                //Revisar si en la base de datos ya hay un articulo con el mismo codigo
                int producto = buscarIndiceEnModel(codigoBarras);
                    
                /*Indicar que lo escrito en el campo de codigo es valido solo si el indice del producto es -1
                (no existia previamente)*/
                campoCodigoEsValido = producto==-1;
            
            } //End else

        } //End try
        catch(NumberFormatException excepcion){
            
            //En caso que el usuario no escribiera un numero en el campo
            campoCodigoEsValido = false;

        } //End catch
    } //End validarCampoCodigo


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
            
            double precioCompra = Double.parseDouble(viewAgregarProducto.campoPrecioCompra.getText());
            double precioVenta  = Double.parseDouble(viewAgregarProducto.campoPrecioVenta.getText());

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
            if (viewAgregarProducto.campoUnidadVenta.getSelectedIndex()== Producto.KILO ||
                viewAgregarProducto.campoUnidadVenta.getSelectedIndex()== Producto.LITRO ){

                //Extraer el valor de campo disponibilidad y parsearlo a double. Checar si >0
                double disponibilidadDecimal = Double.parseDouble(viewAgregarProducto.campoDisponibilidad.getText());
                campoDisponibilidadEsValido = disponibilidadDecimal>0;
            } //End if

            else{

                //Extraer el valor de campo disponibilidad y parsearlo a int. Checar si >0
                int disponibilidadEntera = Integer.parseInt(viewAgregarProducto.campoDisponibilidad.getText());
                campoDisponibilidadEsValido = disponibilidadEntera>0;
            } //End else

        } //End try

        catch(NumberFormatException excepcion){
            
            //En caso que el usuario no escribiera un valor valido en el campo de disponibilidad
            campoDisponibilidadEsValido = false;

        } //End catch

    } //End validarCamposPrecio

   

} //End ControllerInterfazAgregarProducto