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

        // Iniciar ventana de productos
        viewProductos.iniciarInterfaz();
        
    } //End main
} //End class