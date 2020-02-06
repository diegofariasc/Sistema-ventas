import java.io.Serializable;

/**
 * La clase representa un producto que ofrece el establecimiento 
 * propietario del sistema
 * 
 * Proyecto 1. Patrones de diseño de Software
 * @author Diego Farias Castro
 */

@SuppressWarnings("serial")
public class Producto implements Serializable, Comparable<Producto> {

    private int     codigo;
    private String  descripcion;
    private String  departamento;
    private int     unidadVenta;
    private float   precioCompra;
    private float   precioVenta;
    private float   cantidadDisponible;

    // Constantes para estandarizar las unidades de venta 
    public static final int PIEZA   = 0;
    public static final int KILO    = 1;
    public static final int PAQUETE = 2;
    public static final int CAJA    = 3;
    public static final int LITRO   = 4;

    /************************************************
     * Coleccion de getters
    *************************************************/  
    public int    getCodigo()               { return codigo; }
    public String getDescripcion()          { return descripcion; }
    public String getDepartamento()         { return departamento; }
    public int    getUnidadVenta()          { return unidadVenta; }
    public float  getPrecioCompra()         { return precioCompra; }
    public float  getPrecioVenta()          { return precioVenta; }
    public float  getCantidadDisponible()   { return cantidadDisponible; }


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

    public void setPrecioCompra(float nuevoPrecioCompra) {
        precioCompra = nuevoPrecioCompra;
    } // End setPrecioCompra

    public void setPrecioVenta(float nuevoPrecioVenta) {
        precioVenta = nuevoPrecioVenta;
    } // End setPrecioVenta

    public void setCantidadDisponible(float nuevaCantidadDisponible) {
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