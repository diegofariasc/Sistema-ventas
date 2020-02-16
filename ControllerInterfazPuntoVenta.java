import java.awt.event.ActionEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 * La clase representa el controller para la clase (View) InterfazPuntoVenta
 * Implementa Controller (requerida) asi como KeyListener (para cambiar la activacion
 * del boton finalizar mientras el usuario escribe) y FocusListener (Para dar
 * formato a dos decimales al campo de recibido)
 * @author Diego Farias Castro
 */
public class ControllerInterfazPuntoVenta extends ControllerAbstracto implements KeyListener, FocusListener {

    private InterfazPuntoVenta viewPuntoVenta;

    //Variables para almacenar el total de la compra y de productos
    private double totalCompra;
    private double totalProductos;

    /************************************************
    * Constructor de la clase
    *************************************************/
    public ControllerInterfazPuntoVenta(BaseDatosProductos model, InterfazPuntoVenta view) {
        modelProductos = model;
        viewPuntoVenta = view;
    }// End constructor


    /************************************************
    * Implementacion de la interfaz controller
    *************************************************/    

    /**
     * El metodo obtiene el dato numero "indice" del model
     * @param indice Con el numero de dato que se requiere del model
     */
	@Override
	public Producto obtieneDatoDelModel(int indice) {
		return modelProductos.get(indice);
	} //End obtieneDatoDelModel


   /**
     * El metodo lee el contenido de los campos de agregar y 
     * de cantidad y los empaqueta en un arreglo de Strings
     * @return String[]
     */
	@Override
	public String[] obtieneDatoDelView() {

        //Devlover el codigo de barras y la cantidad a agregar
        return new String[]{ viewPuntoVenta.campoAgregar.getText(),
                             viewPuntoVenta.campoCantidadAgregar.getText() };
	} //End obtieneDatoDelView


    /**
     * El metodo actualiza lo que se muestra en el View en los 
     * campos de total de compra y cantidad de articulos 
     */
	@Override
	public void actualizaElView() {
        
        //Actualizar totales de compra y de articulos en el view 
        viewPuntoVenta.labelTotalCompra.setText(String.format("Total de compra: $%.2f", totalCompra));
        viewPuntoVenta.labelCantidadArticulos.setText(String.format("Cantidad de articulos: %.2f", totalProductos));
        viewPuntoVenta.campoAgregar.setText("");
        viewPuntoVenta.campoCantidadAgregar.setText("1");
        viewPuntoVenta.campoRecibido.setText("0.00");
        viewPuntoVenta.botonFinalizar.setEnabled(false);
        viewPuntoVenta.campoAgregar.requestFocus();

	} //End actualizaElView


    /**
     * El metodo recibe una accion en formato String que se compone:
     * "Accion indice cantidad"
     * (accion a realizar: incrementar o decrementar disponibilidad, 
     * indice del objeto en que se realizara el cambio y cantidad a agregar
     * o disminuir)
     * El cambio solicitado se ejecuta en el model
     * @param accion Con la accion que se realizara
     */
	@Override
	public void solicitaActualizacionDelModel(String accion) {

        //Desempaquetar el contenido de la accion
        String[] acciones = accion.split(" ");  
        int      indice   = Integer.parseInt(acciones[1]);
        double   cantidad = Double.parseDouble(acciones[2]);
        Producto producto = obtieneDatoDelModel(indice);

        //Si hay que decrementar la disponibilidad
		if (accion.startsWith("DecrementarDisponibilidad")){
            producto.setCantidadDisponible(producto.getCantidadDisponible()-cantidad);
        } //End if
        
        //Si hay que incrementar la disponibilidad
        else if (accion.startsWith("IncrementarDisponibilidad")){

            producto.setCantidadDisponible(producto.getCantidadDisponible()+cantidad);

        } //End elseif

        //Actualizar el model
        modelProductos.salvaDatosDeLaEstructuraAlRepositorio();

	} //End solicitaActualizacionDelModel


    /**
     * El metodo es llamado automaticamente cuando ocurre algun evento 
     * tipo Action en el view. Este, responde dependiendo el control que 
     * causo su invocacion
     * @param evento Con los datos del evento del cual deriva su invocacion
     */    
	@Override
	public void actionPerformed(ActionEvent evento) {
        
        //Obtener el objeto que causo el evento
        JButton boton = (JButton) evento.getSource();
        
        //Si fue el boton agregar
        if (boton == viewPuntoVenta.botonAgregar){
            agregarProductoVenta( obtieneDatoDelView() );
            actualizaElView();
        } //End if

        //Si fue el boton abortar
        else if (boton == viewPuntoVenta.botonAbortarVenta){

            if (viewPuntoVenta.modeloTabla.getRowCount()>0){
                Dialogo dialogo = new Dialogo(  "Abortar una venta", 
                                                "¿Confirma que desea abortar la venta actual?",
                                                Dialogo.MENSAJE_ADVERTENCIA);
                dialogo.iniciarDialogo();



                //Reiniciar el view si el usuario confirma el abortaje de la venta 
                if (dialogo.seAceptaLaAccion()){
                    
                    while(viewPuntoVenta.modeloTabla.getRowCount()>0){
                        viewPuntoVenta.tablaProductos.setRowSelectionInterval(0, 0);
                        quitarArticuloDeColumnaSeleccionada();
                    } //End while
                    viewPuntoVenta.labelError.setVisible(false);
                    actualizaElView();
                } //End if

            } //End if 

        } //End elseif

        //Si fue el boton quitar producto
        else if (boton == viewPuntoVenta.botonQuitarProducto){

            // Si la fila seleccionada es mayor o igual a 0
            if (viewPuntoVenta.tablaProductos.getSelectedRow()>=0) {

                //Lanzar dialogo de advertencia
                Dialogo dialogo = new Dialogo(  "Quitar un producto de la venta", 
                                                "¿Confirma que desea quitar el producto seleccionado?",
                                                Dialogo.MENSAJE_ADVERTENCIA);
                dialogo.iniciarDialogo();

                //Ver si se acepta la eliminacion
                if (dialogo.seAceptaLaAccion()){

                    //Si se acepta, quitar la columna seleccionada
                    quitarArticuloDeColumnaSeleccionada();

                } //End if 
            } //End if 

            actualizaElView();

        } //End elseif 

        //Si fue el boton de finalizar
        else if (boton == viewPuntoVenta.botonFinalizar){

            //Informar que la venta fue exitosa y reiniciar el view
            Dialogo dialogo = new Dialogo(  "Venta finalizada", 
                                            "Se ha concluido exitosamente la venta actual",
                                            Dialogo.MENSAJE_INFORMATIVO);
            dialogo.iniciarDialogo();
            reiniciar();
            
        } //End elseif 

        //Si fue el boton de consultar la lista de precios
        else if (boton == viewPuntoVenta.botonConsultarListaPrecios){
            mostrarListaPrecios();
        } //End if 

    } //End actionPerformed


    /************************************************
    * Implementacion de la interfaz FocusListener
    *************************************************/   

    /**
     * El metodo es llamado automaticamente cuando el usuario desenfoca 
     * (o activa otro) control determinado
     * @param evento Con los datos del evento del cual deriva su invocacion
     */
    @Override
    public void focusLost(FocusEvent evento) {

        JTextField campoDesenfocado = (JTextField) evento.getSource();
        
        //Ver si el campo de recibido fue el que perdio el foco
        if (campoDesenfocado == viewPuntoVenta.campoRecibido){

            try{
                //Formatear a dos decimales el campo
                double recibido = Double.parseDouble(viewPuntoVenta.campoRecibido.getText());
                viewPuntoVenta.campoRecibido.setText(String.format("%.2f",recibido));
            } //End try
            catch (NumberFormatException exception) {}

        } //End if

    } //End focusLost

    //Evento no necesario pero agregado para cumplir con la interfaz
    @Override
    public void focusGained(FocusEvent e) {}


    /************************************************
    * Implementacion de la interfazKeyListener
    *************************************************/   


   /**
     * El metodo es llamado automaticamente cuando el despues 
     * de que el usuario presionara una tecla en un control determinado
     * @param evento Con los datos del evento del cual deriva su invocacion
     */
    @Override
    public void keyReleased(KeyEvent evento) {

        JTextField campoAccionado = (JTextField) evento.getSource();
        
        if (campoAccionado == viewPuntoVenta.campoRecibido)
            validarActivacionBotonFinalizar();

        if (campoAccionado == viewPuntoVenta.campoAgregar || campoAccionado == viewPuntoVenta.campoCantidadAgregar )
            if (evento.getKeyCode() == KeyEvent.VK_ENTER){
                agregarProductoVenta(obtieneDatoDelView());
                actualizaElView();
            } //End if

    } //End keyReleased

    //Eventos no necesarios pero agregados para cumplir con la interfaz
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent evento) {}



    /************************************************
    * Metodos
    *************************************************/    

    /**
     * El metodo genera un nuevo view y controller correspondiente
     * a la InterfazProductos, los asocia y muestra la nueva ventana
     */
    private void mostrarListaPrecios(){

        //Proceso para llamar a la interfaz de productos 
        InterfazProductos           viewProductos;
        ControllerInterfazProductos controllerProductos;

        // Crear view y controller para la ventana de productos 
        viewProductos       = new InterfazProductos();
        controllerProductos = new ControllerInterfazProductos(modelProductos, viewProductos);

        // Asociar view con controller de la ventana de productos
        viewProductos.setActionListener(controllerProductos);

        // Inicializar tabla del view
        controllerProductos.actualizaElView();

        //Mostrar la lista de precios
        viewProductos.iniciarInterfaz();

    } //End mostrarListaPrecios
    

    /**
     * El metodo reinicia el estado de la ventana 
     */
    private void reiniciar(){

        //Reiniciar filas (0)
        viewPuntoVenta.modeloTabla.setRowCount(0);

        //Quitar cualquier mensaje de advertencia
        viewPuntoVenta.labelError.setVisible(false);

        //Reiniciar totales
        totalCompra = 0;
        totalProductos = 0;

        actualizaElView();
    } //End reiniciar


    /**
     * El metodo agrega un producto a la venta actual.
     * Recibe como parametro un arreglo de strings 
     * @param informacionProductoAgregar
     */
    private void agregarProductoVenta(String[] informacionProductoAgregar ){
        
        boolean  seAgregoElProducto = false;

        try{

            //Obtener el codigo del view
            int codigo = Integer.parseInt(informacionProductoAgregar[0]);

            //Basado en el codigo de barras, obtener el indice del producto en el model
            int indice = buscarIndiceEnModel(codigo);
            
            //Ver si hubo coincidencias
            if (indice!=-1 ){

                //Recuperar el producto
                Producto producto = obtieneDatoDelModel(indice);

                double cantidad;
                
                /*Ver si la unidad de venta es fraccionable y si es asi sumar 
                la cantidad con decimales al total de productos*/
                
                if (producto.getUnidadVenta()== Producto.KILO || 
                    producto.getUnidadVenta()== Producto.LITRO )
                    cantidad = Double.parseDouble(informacionProductoAgregar[1]);
                
                //Si no, sumar un entero
                else
                    cantidad = Integer.parseInt(informacionProductoAgregar[1]);
                
                //Verificar si hay suficiente stock para realizar la operacion
                if (cantidad>producto.getCantidadDisponible())
                    throw new UnsupportedOperationException();

                //Reducir la disponibilidad del articulo en el model
                solicitaActualizacionDelModel("DecrementarDisponibilidad " + indice + " " + cantidad);
                
                //Calcular el total a agregar a la venta
                double subtotalProducto = producto.getPrecioVenta()*cantidad;

                //Actualizar el contenido de la tabla
                viewPuntoVenta.modeloTabla.addRow(new Object[]{
                    cantidad,
                    producto.getCodigo(),
                    producto.getDescripcion(),     
                    producto.getUnidadVentaStr(),
                    String.format("$%.2f",producto.getPrecioVenta()),
                    String.format("$%.2f",subtotalProducto)
                }); //End addRow

                //Modificar los totales de la compra
                totalCompra += subtotalProducto;
                totalProductos += cantidad;

                //Indicar que se agrego el producto
                seAgregoElProducto = true;

            } //End if

            /*Si el producto se agrego, quitar cualquier aviso de error
            de lo contrario, indicar que el producto no esta presente en la base de datos*/
            if (seAgregoElProducto)
                viewPuntoVenta.labelError.setVisible(false);
            else{
                viewPuntoVenta.labelError.setText("El producto no se encontro en la base de datos");
                viewPuntoVenta.labelError.setVisible(true);
            } //End if 

        } //End try

        //La excepcion es lanzada manualmente en caso que no haya stock suficiente
        catch (UnsupportedOperationException excepcion){
            
            viewPuntoVenta.labelError.setText("No hay suficiente stock para agregar a la venta");
            viewPuntoVenta.labelError.setVisible(true);

        } //End catch

        //La excepcion se lanza si no es posible convertir algun valor a numero
        catch (NumberFormatException excepcion){
            
            viewPuntoVenta.labelError.setText("La informacion proporcionada es invalida");
            viewPuntoVenta.labelError.setVisible(true);

        } //End catch
    } //End agregarProductoVenta


    /**
     * Activar o desactivar el boton de finalizar 
     * basado en el contenido del campo de monto recibido
     */
    private void validarActivacionBotonFinalizar(){

        try{

            //Obtener el monto recibido
            double recibido = Double.parseDouble(viewPuntoVenta.campoRecibido.getText());

            //Ver si cubre el total de la venta y si hay mas de un producto en la venta 
            if (recibido>=totalCompra && totalProductos > 0)

                //Activar el boton finalizar de ser asi
                viewPuntoVenta.botonFinalizar.setEnabled(true);
            else
                viewPuntoVenta.botonFinalizar.setEnabled(false);


        } //End try

        //En caso que el campo de recibido no tenga un valor numerico
        catch (NumberFormatException excepcion){
            viewPuntoVenta.botonFinalizar.setEnabled(false);
        } //End catch

    } //End validarActivacionBotonFinalizar


    /**
     * El metodo obtiene la columna seleccionada en el view, la remueve 
     * y actualiza los totales de venta
     */
    private void quitarArticuloDeColumnaSeleccionada(){

        // Extraer la cantidad y codigo del producto a quitar de la tabla en el view
        int      seleccion = obtenerFilaSeleccionadaView(); 
        double   cantidad  = (double) viewPuntoVenta.modeloTabla.getValueAt(seleccion, 0);
        int      codigo    = (int)    viewPuntoVenta.modeloTabla.getValueAt(seleccion, 1);
        
        // Llamar a la actualizacion del model
        solicitaActualizacionDelModel("IncrementarDisponibilidad "+ buscarIndiceEnModel(codigo)+" "+cantidad);
                
        // Actualizacion de los totales**

        // Obtener el subtotal a quitar del view y quitarle el signo de pesos
        String subtotalString = (String) viewPuntoVenta.modeloTabla.getValueAt(seleccion, 5);
        subtotalString = subtotalString.replace("$", "");

        // Convertir el string a double para poder restarlo
        double subtotal = Double.parseDouble(subtotalString);

        // Actualizar totales
        totalCompra    -= subtotal;
        totalProductos -= cantidad;

        // Modificar el view
        viewPuntoVenta.modeloTabla.removeRow(seleccion);

    } // End quitarArticuloDeColumnaSeleccionada

    /**
     * El metodo obtiene el indice de la fila seleccionada en el view
     * @return FilaSeleccionada 
     */
    private int obtenerFilaSeleccionadaView(){
        return viewPuntoVenta.tablaProductos.getSelectedRow();
    } //End obtenerFilaSeleccionadaView

} //End class