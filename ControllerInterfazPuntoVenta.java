import java.awt.event.ActionEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 * La clase representa el controller para la clase (View) InterfazPuntoVenta
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

    
	@Override
	public void actionPerformed(ActionEvent evento) {
		JButton boton = (JButton) evento.getSource();
        
        if (boton == viewPuntoVenta.botonAgregar)
            agregarProductoVenta( obtieneDatoDelView() );

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
                    }
                    
                } //End if
            } //End if 
        } //End elseif

        else if (boton == viewPuntoVenta.botonQuitarProducto){

            if (viewPuntoVenta.modeloTabla.getRowCount()>0){

                Dialogo dialogo = new Dialogo(  "Quitar un producto de la venta", 
                                                "¿Confirma que desea quitar el producto seleccionado?",
                                                Dialogo.MENSAJE_ADVERTENCIA);
                dialogo.iniciarDialogo();

                
                if (dialogo.seAceptaLaAccion()){

                    
                    quitarArticuloDeColumnaSeleccionada();

                } //End if 

            } //End if 

        } //End elseif 

        else if (boton == viewPuntoVenta.botonFinalizar){
            Dialogo dialogo = new Dialogo(  "Venta finalizada", 
                                            "Se ha concluido exitosamente la venta actual",
                                            Dialogo.MENSAJE_INFORMATIVO);
            dialogo.iniciarDialogo();
            reiniciarView();
        } //End elseif 

        else if (boton == viewPuntoVenta.botonConsultarListaPrecios){
            mostrarListaPrecios();
            viewPuntoVenta.iniciarInterfaz();
        } //End if 

        actualizaElView();
        
    } //End actionPerformed

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
    
    private void reiniciarView(){
        viewPuntoVenta.modeloTabla.setRowCount(0);
        totalCompra = 0;
        totalProductos = 0;
    } //End reiniciarView

    private void agregarProductoVenta(String[] informacionProductoAgregar ){
        
        Producto producto;
        boolean  seAgregoElProducto = false;

        try{
            for (int i=0;i<modelProductos.size();i++){
                
                //Obtener el producto i del model
                producto = obtieneDatoDelModel(i);

                /*De la informacion del view extraer el codigo de barras y ver si 
                coincide con el codigo del producto i en el model*/
                if (producto.getCodigo()== Integer.parseInt(informacionProductoAgregar[0])){

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
                    solicitaActualizacionDelModel("DecrementarDisponibilidad " + i + " " + cantidad);
                    
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
                    });

                    //Modificar los totales de la compra
                    totalCompra += subtotalProducto;
                    totalProductos += cantidad;

                    //Indicar que se agrego el producto
                    seAgregoElProducto = true;
                    break;

                } //End if

            } //End for

            /*Si el producto se agrego, quitar cualquier aviso de error
            de lo contrario, indicar que el producto no esta presente en la base de datos*/
            if (seAgregoElProducto)
                viewPuntoVenta.labelError.setVisible(false);
            else{
                viewPuntoVenta.labelError.setText("El producto no se encontro en la base de datos");
                viewPuntoVenta.labelError.setVisible(true);
            } //End if 

        } //End try

        catch (UnsupportedOperationException excepcion){
            
            viewPuntoVenta.labelError.setText("No hay suficiente stock para agregar a la venta");
            viewPuntoVenta.labelError.setVisible(true);

        } //End catch

        catch (NumberFormatException excepcion){
            
            viewPuntoVenta.labelError.setText("La informacion proporcionada es invalida");
            viewPuntoVenta.labelError.setVisible(true);

        } //End catch
    } //End agregarProductoVenta

    @Override
    public void focusGained(FocusEvent e) {}

    @Override
    public void focusLost(FocusEvent evento) {

        JTextField campoDesenfocado = (JTextField) evento.getSource();
        
        if (campoDesenfocado == viewPuntoVenta.campoRecibido){

            validarActivacionBotonFinalizar();
            double recibido = Double.parseDouble(viewPuntoVenta.campoRecibido.getText());
            viewPuntoVenta.campoRecibido.setText(String.format("%.2f",recibido));

        } //End if

    } //End focusLost

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent evento) {} //End keyPress

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

    private void validarActivacionBotonFinalizar(){
        try{

            double recibido = Double.parseDouble(viewPuntoVenta.campoRecibido.getText());
            if (recibido>=totalCompra && totalProductos > 0)
                viewPuntoVenta.botonFinalizar.setEnabled(true);
            else
                viewPuntoVenta.botonFinalizar.setEnabled(false);

        } //End try
        catch (NumberFormatException excepcion){} //End catch
    } //End try

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

    private int obtenerFilaSeleccionadaView(){
        return viewPuntoVenta.tablaProductos.getSelectedRow();
    } //End obtenerFilaSeleccionadaView

} //End class