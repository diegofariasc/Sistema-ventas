/**
 * La clase representa el punto de entrada de la aplicacion
 * @author Diego Farias Castro
 */
public class Main{
    public static void main (String[] args){

        // Declarar model view y controller para el punto de venta 
        BaseDatosProductos              modelProductos;
        InterfazPuntoVenta              viewVenta;
        ControllerInterfazPuntoVenta    controllerVenta;

        // Crear view y controller para la ventana de productos 
        modelProductos  = new BaseDatosProductos();
        viewVenta       = new InterfazPuntoVenta();
        controllerVenta = new ControllerInterfazPuntoVenta(modelProductos, viewVenta);

        //Asociar view y controller
        viewVenta.setActionListener(controllerVenta);
        viewVenta.setFocusListener(controllerVenta);
        viewVenta.setKeyListener(controllerVenta);

        //Lanzar la ventana principal
        viewVenta.iniciarInterfaz();

    } //End main

} //End class