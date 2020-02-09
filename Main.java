public class Main{
    public static void main (String[] args){

        InterfazProductos           viewProductos;
        BaseDatosProductos          modelProductos;
        ControllerInterfazProductos controllerProductos;

        // Crear model view y controller para la ventana de productos 
        modelProductos      = new BaseDatosProductos();
        viewProductos       = new InterfazProductos();
        controllerProductos = new ControllerInterfazProductos(modelProductos, viewProductos);

        // Asociar view con controller de la ventana de productos
        viewProductos.setActionListener(controllerProductos);

        // Inicializar tabla del view
        controllerProductos.actualizaElView();

        viewProductos.iniciarInterfaz();
        
        InterfazPuntoVenta viewVenta = new InterfazPuntoVenta();
        ControllerInterfazPuntoVenta controllerVenta = new ControllerInterfazPuntoVenta(modelProductos, viewVenta);
        viewVenta.setActionListener(controllerVenta);
        viewVenta.iniciarInterfaz();


    } //End main
} //End class