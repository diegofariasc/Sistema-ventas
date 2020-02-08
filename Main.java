public class Main{
    public static void main (String[] args){

        InterfazPuntoVenta viewPuntoVenta = new InterfazPuntoVenta();
        viewPuntoVenta.iniciarInterfaz();

        BaseDatosProductos modelProductos = new BaseDatosProductos();
        modelProductos.cargaDatosDelRepositorioALaEstructura();

        Producto producto = new Producto(1,"Leche","Abarrotes",0,13.50,14.50,100);
        modelProductos.agregaDatosALaEstructura(0, producto);

    } //End main
} //End class