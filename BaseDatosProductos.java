import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * La clase representa la coleccion de productos almacenados
 * por el sistema
 */

public class BaseDatosProductos implements Model {

    private ArrayList<Producto> productos;

    /************************************************
    * Constructor de la clase
    *************************************************/
    public BaseDatosProductos(){
        productos = new ArrayList<Producto>();
    } //End constructor


    /************************************************
    * Implementacion de la interfaz Model
    *************************************************/
    @Override
    @SuppressWarnings("unchecked")
    public void cargaDatosDelRepositorioALaEstructura() {

        try {
            // Deserializar archivo de datos 
            FileInputStream fileInputStream = new FileInputStream("baseDatosProductos.sysventas");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            
            //Leer base de datos de productos y guardarlo en el arraylist
            productos = (ArrayList<Producto>) objectInputStream.readObject();

            //Cerrar los flujos de entrada
            objectInputStream.close();
            fileInputStream  .close();
        
        } // End try

        catch (FileNotFoundException excepcion) {

            /* Si no se pudo cargar la estructura porque el archivo 
            no se encontro (posiblemente no se ha creado), guardarlo*/
            salvaDatosDeLaEstructuraAlRepositorio();
        } //End catch

        catch (ClassNotFoundException | IOException excepcion) {}
        
    } //End cargaDatosDelRepositorioALaEstructura

    @Override
    public void salvaDatosDeLaEstructuraAlRepositorio() {

        try {
            // Serializar archivo de datos 
            FileOutputStream fileOutputStream = new FileOutputStream("baseDatosProductos.sysventas");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            
            //Guardar arraylist
            objectOutputStream.writeObject(productos);

            //Cerrar los flujos de entrada
            objectOutputStream.close();
            fileOutputStream  .close();
            
        } catch (IOException e) {}

    } //End salvaDatosDeLaEstructuraAlRepositorio

    @Override
    public void agregaDatosALaEstructura(int indice, Object dato) {
        productos.add(indice,(Producto)dato);
    } //End agregaDatosALaEstructura

    @Override
    public void modificaDatosEnLaEstructura(int indice, Object dato) {
        productos.set(indice,(Producto)dato);
    } //End modificaDatosEnLaEstructura

    @Override
    public void eliminaDatosDeLaEstructura(int indice) {
        productos.remove(indice);
    } //End eliminaDatosDeLaEstructura

    @Override
    public void ordenaLaEstructura() {
        Collections.sort(productos);
    } //End ordenaLaEstructura

    @Override
    public double procesa(int indice) {
        return 0;
    } //End procesa

    @Override
    public boolean hayDatos() {
        return !productos.isEmpty();
    } //End hayDatos


    /************************************************
    * Coleccion de getters
    *************************************************/ 
    public Producto getProducto(int indice) { 
        return productos.get(indice); 
    } //End getProducto
    
    public int getCantidadProductos(){ 
        return productos.size();
    } // End getCantidadProductos

} //End class