import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 * La clase representa el view para visualizar la lista de productos
 * @author Diego Farias Castro
 */
@SuppressWarnings("serial")
public class InterfazProductos extends JDialog implements View {

    //Constantes para construir la ventana
    private static final int LARGO_VENTANA = 1000;
    private static final int ALTO_VENTANA  = 600;

    JPanel              panelTitulo;
    JLabel              labelNombreAplicacion;
    JLabel              labelTitulo;
    JLabel              labelInstrucciones;
    JLabel              imagenVentana;
    JLabel              labelDescripcionRegresar;
    JLabel              labelDescripcionAgregarProducto;
    JLabel              labelDescripcionEditarProducto;
    JLabel              labelDescripcionEliminarProducto;
    JLabel              labelDescripcionRegistrarAbastecimientoProducto;
    JLabel              labelDescripcionOrdenarProductos;
    JLabel              labelDescripcionBuscar;
    JButton             botonRegresar;
    JButton             botonOrdenarProductos;;
    JButton             botonNuevoProducto;
    JButton             botonEditarProducto;
    JButton             botonEliminarProducto;
    JButton             botonRegistrarAbastecimientoProducto;
    JButton             botonBuscar;
    JButton             botonQuitarFiltro;
    JTextField          campoFiltrar;
    JTable              tablaProductos;
    DefaultTableModel   modeloTabla;
    TableColumn         columnaCodigo;
    TableColumn         columnaDescripcion;
    TableColumn         columnaDepartamento;
    TableColumn         columnaPrecioCompra;
    TableColumn         columnaPrecioVenta;
    TableColumn         columnaGanancia;
    TableColumn         columnaDisponibilidad;
    TableColumn         columnaUnidadVenta;
    JScrollPane         panelConScroll;
    Color               colorEstilo;

    /************************************************
     * Constructor de la clase
     *************************************************/
    public InterfazProductos(){
        
        //Parámetros de la ventana
        colorEstilo = new Color(13,62,145);
        setTitle("Sistema de ventas [Gestionar productos]");
        setSize(LARGO_VENTANA,ALTO_VENTANA);
        setLocation(200,80);
        setBackground(colorEstilo);
        setLayout(null);
        setResizable(false);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //Construir los componentes de la ventana
        constuyeComponentes();

    } //End Constructor

    
    /************************************************
     * Implementacion de la interfaz View
     *************************************************/
    /**
     * El metodo construye cada componente de la ventana
     */
    @Override
    public void constuyeComponentes() {

        //Panel superior de la ventana
        panelTitulo = new JPanel();
        panelTitulo.setSize(LARGO_VENTANA,60);
        panelTitulo.setBackground(colorEstilo);
        panelTitulo.setLayout(null);
        add(panelTitulo);

        //Imagen en la esquina superior izquierda
        imagenVentana = new JLabel(new ImageIcon("Iconos/Icono_productos.png"));
        imagenVentana.setLocation(5,7);
        imagenVentana.setSize(40,40);
        panelTitulo.add(imagenVentana);

        //Campo para filtrado por nombre
        campoFiltrar = new JTextField();
        campoFiltrar.setLocation(LARGO_VENTANA-245,79);
        campoFiltrar.setSize(200,22);
        add(campoFiltrar);

        //Boton para buscar
        botonBuscar = new JButton(new ImageIcon("Iconos/Icono_buscar.png"));
        botonBuscar.setLocation(LARGO_VENTANA-44,79);
        botonBuscar.setSize(21,21);
        botonBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonBuscar.setBorderPainted(false);
        botonBuscar.setOpaque(true);
        botonBuscar.setFocusPainted(false);
        botonBuscar.setBackground(colorEstilo);
        botonBuscar.setToolTipText("Buscar productos que contengan el nombre dado");
        add(botonBuscar);

        //Boton para quitar filtro
        botonQuitarFiltro = new JButton("Quitar filtro");
        botonQuitarFiltro.setLocation(LARGO_VENTANA-130,61);
        botonQuitarFiltro.setSize(100,20);
        botonQuitarFiltro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonQuitarFiltro.setForeground(new Color(84,14,0));
        botonQuitarFiltro.setBorderPainted(false);
        botonQuitarFiltro.setContentAreaFilled(false);
        botonQuitarFiltro.setFocusPainted(false);
        botonQuitarFiltro.setVisible(false);
        botonQuitarFiltro.setFont(new Font("Segoe UI", Font.BOLD, 10));
        botonQuitarFiltro.setToolTipText("Eliminar el filtrado de datos y volver a la vista completa");
        add(botonQuitarFiltro);

        //Boton para registrar la entrada al almacen del producto
        botonRegistrarAbastecimientoProducto = new JButton(new ImageIcon("Iconos/Icono_registrarAbastecimientoProducto.png"));
        botonRegistrarAbastecimientoProducto.setLocation(LARGO_VENTANA-365,5);
        botonRegistrarAbastecimientoProducto.setSize(35,35);
        botonRegistrarAbastecimientoProducto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonRegistrarAbastecimientoProducto.setBorderPainted(false);
        botonRegistrarAbastecimientoProducto.setContentAreaFilled(false);
        botonRegistrarAbastecimientoProducto.setToolTipText("Registrar la entrada de unidades del producto seleccionado al almacen");
        panelTitulo.add(botonRegistrarAbastecimientoProducto);

        //Boton para ordenar producto
        botonOrdenarProductos = new JButton(new ImageIcon("Iconos/Icono_ordenarProductos.png"));
        botonOrdenarProductos.setLocation(LARGO_VENTANA-285,5);
        botonOrdenarProductos.setSize(35,35);
        botonOrdenarProductos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonOrdenarProductos.setBorderPainted(false);
        botonOrdenarProductos.setContentAreaFilled(false);
        botonOrdenarProductos.setFocusPainted(false);
        botonOrdenarProductos.setToolTipText("Registrar un nuevo producto");
        botonOrdenarProductos.setToolTipText("Ordenar los productos de manera ascendente segun su descripcion");
        panelTitulo.add(botonOrdenarProductos);

        //Boton para agregar producto
        botonNuevoProducto = new JButton(new ImageIcon("Iconos/Icono_agregarProducto.png"));
        botonNuevoProducto.setLocation(LARGO_VENTANA-230,5);
        botonNuevoProducto.setSize(35,35);
        botonNuevoProducto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonNuevoProducto.setBorderPainted(false);
        botonNuevoProducto.setContentAreaFilled(false);
        botonNuevoProducto.setToolTipText("Registrar un nuevo producto");
        panelTitulo.add(botonNuevoProducto);

        //Boton para editar producto
        botonEditarProducto = new JButton(new ImageIcon("Iconos/Icono_editarProducto.png"));
        botonEditarProducto.setLocation(LARGO_VENTANA-175,5);
        botonEditarProducto.setSize(35,35);
        botonEditarProducto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonEditarProducto.setBorderPainted(false);
        botonEditarProducto.setContentAreaFilled(false);
        botonEditarProducto.setToolTipText("Editar la informacion del producto seleccionado");
        panelTitulo.add(botonEditarProducto);

        //Boton para quitar producto 
        botonEliminarProducto = new JButton(new ImageIcon("Iconos/Icono_quitarProducto.png"));
        botonEliminarProducto.setLocation(LARGO_VENTANA-120,5);
        botonEliminarProducto.setSize(35,35);
        botonEliminarProducto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonEliminarProducto.setBorderPainted(false);
        botonEliminarProducto.setContentAreaFilled(false);
        botonEliminarProducto.setToolTipText("Eliminar el producto seleccionado");
        panelTitulo.add(botonEliminarProducto);

        //Boton para volver al punto de venta
        botonRegresar = new JButton(new ImageIcon("Iconos/Icono_regresar.png"));
        botonRegresar.setLocation(LARGO_VENTANA-65,5);
        botonRegresar.setSize(35,35);
        botonRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonRegresar.setBorderPainted(false);
        botonRegresar.setContentAreaFilled(false);
        botonRegresar.setToolTipText("Regresar al punto de venta");
        panelTitulo.add(botonRegresar);

        //Etiqueta con el nombre de la aplicacion
        labelNombreAplicacion = new JLabel("Sistema de ventas");
        labelNombreAplicacion.setLocation(55,5);
        labelNombreAplicacion.setSize(400,20);
        labelNombreAplicacion.setForeground(Color.WHITE);
        labelNombreAplicacion.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        panelTitulo.add(labelNombreAplicacion);

        //Etiqueta para describir la tarea de la ventana
        labelTitulo = new JLabel("Administrar productos registrados");
        labelTitulo.setLocation(55,27);
        labelTitulo.setSize(400,20);
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        panelTitulo.add(labelTitulo);

        //Etiqueta para describir el funcionamiento de la busqueda
        labelDescripcionBuscar = new JLabel("Busqueda por nombre");
        labelDescripcionBuscar.setLocation(LARGO_VENTANA-245,61);
        labelDescripcionBuscar.setSize(150,20);
        labelDescripcionBuscar.setForeground(Color.DARK_GRAY);
        labelDescripcionBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        add(labelDescripcionBuscar);

        //Etiqueta para indicar el funcionamiento del boton regresar
        labelDescripcionRegresar = new JLabel("Regresar");
        labelDescripcionRegresar.setLocation(LARGO_VENTANA-76,37);
        labelDescripcionRegresar.setSize(57,20);
        labelDescripcionRegresar.setHorizontalAlignment(JLabel.CENTER);
        labelDescripcionRegresar.setForeground(Color.WHITE);
        labelDescripcionRegresar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panelTitulo.add(labelDescripcionRegresar);

        //Etiqueta para indicar el funcionamiento del boton eliminar
        labelDescripcionEliminarProducto = new JLabel("Eliminar");
        labelDescripcionEliminarProducto.setLocation(LARGO_VENTANA-128,37);
        labelDescripcionEliminarProducto.setSize(51,20);
        labelDescripcionEliminarProducto.setHorizontalAlignment(JLabel.CENTER);
        labelDescripcionEliminarProducto.setForeground(Color.WHITE);
        labelDescripcionEliminarProducto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panelTitulo.add(labelDescripcionEliminarProducto);

        //Etiqueta para indicar el funcionamiento del boton editar
        labelDescripcionEditarProducto = new JLabel("Editar");
        labelDescripcionEditarProducto.setLocation(LARGO_VENTANA-183,37);
        labelDescripcionEditarProducto.setSize(51,20);
        labelDescripcionEditarProducto.setHorizontalAlignment(JLabel.CENTER);
        labelDescripcionEditarProducto.setForeground(Color.WHITE);
        labelDescripcionEditarProducto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panelTitulo.add(labelDescripcionEditarProducto);

        //Etiqueta para indicar el funcionamiento del boton nuevo
        labelDescripcionAgregarProducto = new JLabel("Nuevo");
        labelDescripcionAgregarProducto.setLocation(LARGO_VENTANA-238,37);
        labelDescripcionAgregarProducto.setSize(51,20);
        labelDescripcionAgregarProducto.setHorizontalAlignment(JLabel.CENTER);
        labelDescripcionAgregarProducto.setForeground(Color.WHITE);
        labelDescripcionAgregarProducto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panelTitulo.add(labelDescripcionAgregarProducto);

        //Etiqueta para indicar el funcionamiento del boton ordenar
        labelDescripcionOrdenarProductos = new JLabel("Ordenar");
        labelDescripcionOrdenarProductos.setLocation(LARGO_VENTANA-293,37);
        labelDescripcionOrdenarProductos.setSize(51,20);
        labelDescripcionOrdenarProductos.setHorizontalAlignment(JLabel.CENTER);
        labelDescripcionOrdenarProductos.setForeground(Color.WHITE);
        labelDescripcionOrdenarProductos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panelTitulo.add(labelDescripcionOrdenarProductos);

        //Etiqueta para indicar el funcionamiento del boton de registrar entrada al almacen
        labelDescripcionRegistrarAbastecimientoProducto = new JLabel("Abastecer");
        labelDescripcionRegistrarAbastecimientoProducto.setLocation(LARGO_VENTANA-380,37);
        labelDescripcionRegistrarAbastecimientoProducto.setSize(65,20);
        labelDescripcionRegistrarAbastecimientoProducto.setHorizontalAlignment(JLabel.CENTER);
        labelDescripcionRegistrarAbastecimientoProducto.setForeground(Color.WHITE);
        labelDescripcionRegistrarAbastecimientoProducto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panelTitulo.add(labelDescripcionRegistrarAbastecimientoProducto);
        
        //Etiqueta con instrucciones sobre el funcionamiento de la ventana
        labelInstrucciones = new JLabel("Use las opciones del panel superior para agregar, "+
        "editar o quitar un producto");
        labelInstrucciones.setLocation(13,70);
        labelInstrucciones.setSize(700,20);
        labelInstrucciones.setForeground(Color.DARK_GRAY);
        labelInstrucciones.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(labelInstrucciones);

        //Columnas para la tabla
        columnaCodigo = new TableColumn();
        columnaDescripcion = new TableColumn();
        columnaDepartamento = new TableColumn();
        columnaUnidadVenta = new TableColumn();
        columnaPrecioCompra = new TableColumn();
        columnaPrecioVenta = new TableColumn();
        columnaDisponibilidad = new TableColumn();
        columnaGanancia = new TableColumn();

        //Modelo para la tabla
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn(columnaCodigo);
        modeloTabla.addColumn(columnaDescripcion);
        modeloTabla.addColumn(columnaDepartamento);
        modeloTabla.addColumn(columnaUnidadVenta);
        modeloTabla.addColumn(columnaPrecioCompra);
        modeloTabla.addColumn(columnaPrecioVenta);
        modeloTabla.addColumn(columnaDisponibilidad);
        modeloTabla.addColumn(columnaGanancia);

        //Tabla de datos 
        tablaProductos = new JTable(modeloTabla);
        tablaProductos.getColumnModel().getColumn(0).setPreferredWidth((LARGO_VENTANA-60)/8);
        tablaProductos.getColumnModel().getColumn(1).setPreferredWidth((LARGO_VENTANA-60)/3);
        tablaProductos.getColumnModel().getColumn(2).setPreferredWidth((LARGO_VENTANA-60)/4);
        tablaProductos.getColumnModel().getColumn(3).setPreferredWidth((LARGO_VENTANA-60)/8);
        tablaProductos.getColumnModel().getColumn(4).setPreferredWidth((LARGO_VENTANA-60)/8);
        tablaProductos.getColumnModel().getColumn(5).setPreferredWidth((LARGO_VENTANA-60)/8);
        tablaProductos.getColumnModel().getColumn(6).setPreferredWidth((LARGO_VENTANA-60)/8);
        tablaProductos.getColumnModel().getColumn(7).setPreferredWidth((LARGO_VENTANA-60)/8);
        tablaProductos.getColumnModel().getColumn(0).setHeaderValue("Codigo");
        tablaProductos.getColumnModel().getColumn(1).setHeaderValue("Descripcion");
        tablaProductos.getColumnModel().getColumn(2).setHeaderValue("Departamento");
        tablaProductos.getColumnModel().getColumn(3).setHeaderValue("Unidad venta");
        tablaProductos.getColumnModel().getColumn(4).setHeaderValue("Precio compra");
        tablaProductos.getColumnModel().getColumn(5).setHeaderValue("Precio venta");
        tablaProductos.getColumnModel().getColumn(6).setHeaderValue("Disponible");
        tablaProductos.getColumnModel().getColumn(7).setHeaderValue("Ganancia");
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Recuadro con opcion de scrolling para la tabla
        panelConScroll = new JScrollPane(tablaProductos);
        panelConScroll.setLocation(13,105);
        panelConScroll.setSize(LARGO_VENTANA-35,ALTO_VENTANA-150);
        panelConScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); 
        add(panelConScroll);

    } //End constuyeComponentes


    /**
     * El metodo designa el control del view al Controller dado
     * @param theController Controlador de la interfaz
     */
    @Override
    public void setActionListener(Controller theController) {
        botonRegistrarAbastecimientoProducto.addActionListener(theController);
        botonNuevoProducto.addActionListener(theController);
        botonEditarProducto.addActionListener(theController);
        botonEliminarProducto.addActionListener(theController);
        botonRegresar.addActionListener(theController);
        botonBuscar.addActionListener(theController);
        botonQuitarFiltro.addActionListener(theController);
        botonOrdenarProductos.addActionListener(theController);
    } //End setActionListener


     /************************************************
     * Metodos
     *************************************************/
    /**
     * El metodo lanza la interfaz tras su construccion
     */
    public void iniciarInterfaz(){
        setVisible(true);
    } //End iniciar interfaz

    /**
     * El metodo oculta la interfaz
     */
    public void ocultarInterfaz(){
        setVisible(false);
    } //End ocultarInterfaz

} //End class