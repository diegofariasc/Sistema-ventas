
/**
 * La clase abstracta implementa la interfaz Controller, obligando
 * a todas sus subclases (Todos los controllers) a implementarla. 
 * Ademas, incluye un atributo tipo BaseDatosProductos (que es comun en todos los
 * controllers)
 * Finalmente, incluye el metodo obtieneDatoDelModel, cuya implementacion es 
 * identica en todos los controllers asi como BuscarIndiceEnModel 
 * (que, se requiere en todos los controllers bajo una misma implementacion)
 * Con esto, se previene la replicacion de codigo en los controllers. 
 * En vez de ello, se heredan de esta clase abstracta
 */

public abstract class ControllerAbstracto implements Controller {

    BaseDatosProductos modelProductos;

    /**
     * El metodo obtiene el dato numero "indice" del model
     * @param indice Con el numero de dato que se requiere del model
    */
    @Override
    public Producto obtieneDatoDelModel(int indice) {
        return modelProductos.get(indice);
    } // End obtieneDatoDelModel

    /**
     * El metodo realiza una busqueda lineal para localizar
     * el codigo de barras dado. 
     * Si se encuentra, devuelve el indice del producto en la 
     * estructura. De lo contrario, se devuelve -1 
     * @param codigo Codigo de barras a buscar
     * @return Indice
     */
    public int buscarIndiceEnModel(int codigo){
        
        //Busqueda lineal
        for (int i=0; i< modelProductos.obtenerTamano();i++){

            //Obtener objeto en la posicion i
            Producto producto = obtieneDatoDelModel(i);

            //Comparar codigo
            if (producto.getCodigo() == codigo)
                return i;
        } //End for
    
        //Si no se encontro el producto
        return -1;

    } //End buscarIndiceEnModel
}