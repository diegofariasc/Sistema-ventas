import java.io.Serializable;

/**
 * La clase representa un producto que ofrece el establecimiento 
 * propietario del sistema
 * 
 * @author Diego Farias Castro
 */

@SuppressWarnings("serial")
public class Producto implements Serializable, Comparable<Producto> {

    private int     codigo;
    private String  descripcion;
    private String  departamento;
    private int     unidadVenta;
    private double  precioCompra;
    private double  precioVenta;
    private double  cantidadDisponible;

    // Constantes para estandarizar las unidades de venta 
    public static final int PIEZA   = 0;
    public static final int KILO    = 1;
    public static final int PAQUETE = 2;
    public static final int CAJA    = 3;
    public static final int LITRO   = 4;

    /************************************************
     * Coleccion de getters
    *************************************************/  
    public int     getCodigo()              { return codigo; }
    public String  getDescripcion()         { return descripcion; }
    public String  getDepartamento()        { return departamento; }
    public int     getUnidadVenta()         { return unidadVenta; }
    public double  getPrecioCompra()        { return precioCompra; }
    public double  getPrecioVenta()         { return precioVenta; }
    public double  getCantidadDisponible()  { return cantidadDisponible; }
    public String  getUnidadVentaStr()      {
        switch (unidadVenta){
            case 0:  return "Pieza";
            case 1:  return "Kilo";
            case 2:  return "Paquete";
            case 3:  return "Caja";
            case 4:  return "Litro";
        } //End switch
        return null;
    } // End getUnidadVentaString


    /************************************************
     * Constructores
    *************************************************/  

    //Constructor prefefinido por la superclase Object
    public Producto(){}

    //Constructor que acepta todos los parametros
    public Producto(int codigo, String descripcion, String departamento,
                    int unidadVenta, double precioCompra, double precioVenta,  
                    double cantidadDisponible){
        this.codigo             = codigo;
        this.descripcion        = descripcion;
        this.departamento       = departamento;
        this.unidadVenta        = unidadVenta;
        this.precioCompra       = precioCompra;
        this.precioVenta        = precioVenta;
        this.cantidadDisponible = cantidadDisponible;
    } //End constructor

    //Constructor que acepta todos los parametros, pero con unidad de venta como string
    public Producto(int codigo, String descripcion, String departamento,
                    String unidadVenta, double precioCompra, double precioVenta,  
                    double cantidadDisponible){
        
        this(codigo,descripcion,departamento,0,precioCompra,precioVenta,cantidadDisponible);
        this.setUnidadVenta(unidadVenta);

    } //End constructor

    /************************************************
     * Coleccion de setters
    *************************************************/  
    public void setCodigo(int nuevoCodigo) {
        codigo = nuevoCodigo;
    } // End setCodigo

    public void setDescripcion(String nuevaDescripcion) {
        descripcion = nuevaDescripcion;
    } // End setDescripcion

    public void setDepartamento(String nuevoDepartamento) {
        departamento = nuevoDepartamento;
    } // End setDepartamento

    public void setUnidadVenta(int nuevaUnidadVenta) {
        unidadVenta = nuevaUnidadVenta;
    } // End setUnidadVenta

    public void setUnidadVenta(String nuevaUnidadVenta)   {
        switch (nuevaUnidadVenta){
            case "Pieza"    :  unidadVenta = 0; break;
            case "Kilo"     :  unidadVenta = 1; break;
            case "Paquete"  :  unidadVenta = 2; break;
            case "Caja"     :  unidadVenta = 3; break;
            case "Litro"    :  unidadVenta = 4; break;
        } //End switch
    } // End getUnidadVentaString

    public void setPrecioCompra(double nuevoPrecioCompra) {
        precioCompra = nuevoPrecioCompra;
    } // End setPrecioCompra

    public void setPrecioVenta(double nuevoPrecioVenta) {
        precioVenta = nuevoPrecioVenta;
    } // End setPrecioVenta

    public void setCantidadDisponible(double nuevaCantidadDisponible) {
        cantidadDisponible = nuevaCantidadDisponible;
    } // End setCantidadDisponible

    /************************************************
     * Implementacion de Comparable
     *************************************************/
    @Override
    public int compareTo(Producto otroProducto) {
        return descripcion.compareTo(otroProducto.getDescripcion());
    } //End compareTo

} //End class