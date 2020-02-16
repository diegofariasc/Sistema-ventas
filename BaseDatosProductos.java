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

 @SuppressWarnings("serial")
public class BaseDatosProductos extends ArrayList<Producto> implements Model {


    /************************************************
    * Constructor
    *************************************************/
    public BaseDatosProductos(){
        cargaDatosDelRepositorioALaEstructura();
    } //End BaseDatosProductos


    /************************************************
    * Implementacion de la interfaz Model
    *************************************************/

    /**
     * El metodo intenta deserializar el archivo con la base de datos del
     * sistema, usando la metodologia ya contruida por default en Java.
     * Si falla, se crea el archivo 
     */
    @Override
    @SuppressWarnings("unchecked")
    public void cargaDatosDelRepositorioALaEstructura() {

        try {
            // Deserializar archivo de datos 
            FileInputStream fileInputStream = new FileInputStream("baseDatosProductos.sysventas");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            
            //Leer base de datos de productos y guardarlo en el arraylist
            addAll((ArrayList<Producto>) objectInputStream.readObject());

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


    /**
     * El metodo guarda el contenido del model usando la metodologia
     * de serializacion construida en Java
     */
    @Override
    public void salvaDatosDeLaEstructuraAlRepositorio() {

        try {
            // Serializar archivo de datos 
            FileOutputStream fileOutputStream = new FileOutputStream("baseDatosProductos.sysventas");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            
            //Guardar arraylist
            objectOutputStream.writeObject(this);

            //Cerrar los flujos de entrada
            objectOutputStream.close();
            fileOutputStream  .close();
            
        } catch (IOException e) {}

    } //End salvaDatosDeLaEstructuraAlRepositorio


    /**
     * El metodo recibe un objeto, lo convierte 
     * al tipo Producto y lo agrega a la estructura en el indice dado
     * @param indice Indice en que se insertara el objeto
     * @param dato Objeto que se insertara en la estructura
     */
    @Override
    public void agregaDatosALaEstructura(int indice, Object dato) {
        add(indice,(Producto)dato);
    } //End agregaDatosALaEstructura


    /**
     * El metodo recibe un objeto y un indice. Se reemplaza
     * el elemento en la posicion dada por el objeto dado en la 
     * estructura
     * @param indice Indice del objeto que sera reemplazado
     * @param dato Objeto que reemplazara al elemento de la estructura
     */
    @Override
    public void modificaDatosEnLaEstructura(int indice, Object dato) {
        set(indice,(Producto)dato);
    } //End modificaDatosEnLaEstructura


    /**
     * El metodo elimina el elemento en la posicion dada
     * de la estructura
     * @param indice Indice del elemento a remover
     */
    @Override
    public void eliminaDatosDeLaEstructura(int indice) {
        remove(indice);
    } //End eliminaDatosDeLaEstructura


    /**
     * El metodo ordena la estructura usando el mecanismo
     * implementado en la clase Collections
     */
    @Override
    public void ordenaLaEstructura() {
        Collections.sort(this);
    } //End ordenaLaEstructura


    /**
     * El metodo calcula la ganancia del producto 
     * en la posicion dada 
     * @param indice Indice del producto del que se desea calcular su ganancia
     */
    @Override
    public double procesa(int indice) {

        //Recuperar producto
        double   ganancia;
        Producto producto = get(indice); 

        //Calcular ganancia
        ganancia = ((producto.getPrecioVenta() / producto.getPrecioCompra()) -1 ) * 100;
        return ganancia;

    } //End procesa


    /**
     * El metodo indica si el model se encuentra vacio
     * @return Si la estructura no esta vacia
     */
    @Override
    public boolean hayDatos() {
        return !isEmpty();
    } //End hayDatos



    /************************************************
    * Metodos 
    *************************************************/
    /**
     * El metodo recupera el tamano de la estructura
     * @return Tamano de la estructura
     */
    public int obtenerTamano(){
        return size();
    } //End obtenerTamano

} //End class