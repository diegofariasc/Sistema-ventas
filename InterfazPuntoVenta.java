import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

@SuppressWarnings("serial")
public class InterfazPuntoVenta extends JFrame implements View {

    private static final int LARGO_VENTANA = 750;
    private static final int ALTO_VENTANA  = 550;

    JPanel              panelTitulo;
    JLabel              labelNombreAplicacion;
    JLabel              labelTitulo;
    JLabel              labelInstrucciones;
    JLabel              imagenVentana;
    JLabel              labelDescripcionFinalizarVenta;
    JLabel              labelDescripcionEliminarProducto;
    JLabel              labelDescripcionConsultarListaPrecios;
    JLabel              labelDescripcionBuscar;
    JButton             botonFinalizarVenta;
    JButton             botonEliminarProducto;
    JButton             botonConsultarListaPrecios;
    JButton             botonBuscar;
    JTextField          campoBuscar;
    JTextField          campoAgregar;
    JTextField          campoRecibido;
    JTable              tablaProductos;
    DefaultTableModel   modeloTabla;
    TableColumn         columnaCantidad;
    TableColumn         columnaUnidadVenta;
    TableColumn         columnaCodigo;
    TableColumn         columnaDescripcion;
    TableColumn         columnaPrecioUnitario;
    TableColumn         columnaTotal;
    JScrollPane         panelConScroll;
    Color               colorEstilo;

    /************************************************
     * Constructor de la clase
     *************************************************/
    public InterfazPuntoVenta(){
        
        //Parámetros de la ventana
        colorEstilo = new Color(13,62,145);
        setTitle("Sistema de ventas [Punto de venta]");
        setSize(LARGO_VENTANA,ALTO_VENTANA);
        setLocation(200,80);
        setBackground(colorEstilo);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

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
        campoBuscar = new JTextField();
        campoBuscar.setLocation(LARGO_VENTANA-245,110);
        campoBuscar.setSize(200,22);
        add(campoBuscar);

        //Campo para filtrado por nombre
        campoAgregar = new JTextField();
        campoAgregar.setLocation(13,110);
        campoAgregar.setSize(230,22);
        add(campoAgregar);

        //Campo para filtrado por nombre
        campoRecibido = new JTextField("0.00");
        campoRecibido.setLocation(303,483);
        campoRecibido.setSize(105,26);
        campoRecibido.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(campoRecibido);

        //Campo para filtrado por nombre
        campoAgregar = new JTextField("1");
        campoAgregar.setLocation(258,110);
        campoAgregar.setSize(60,22);
        add(campoAgregar);

        //Boton para buscar
        botonBuscar = new JButton(new ImageIcon("Iconos/Icono_agregarVenta.png"));
        botonBuscar.setText("Agregar");
        botonBuscar.setLocation(325,110);
        botonBuscar.setSize(95,21);
        botonBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonBuscar.setFocusPainted(false);
        botonBuscar.setBackground(colorEstilo);
        botonBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        botonBuscar.setForeground(Color.WHITE);
        botonBuscar.setToolTipText("Agregar el");
        add(botonBuscar);

        //Boton para buscar
        botonBuscar = new JButton(new ImageIcon("Iconos/Icono_agregarVenta.png"));
        botonBuscar.setText(" Finalizar");
        botonBuscar.setLocation(LARGO_VENTANA-138,ALTO_VENTANA-73);
        botonBuscar.setSize(115,30);
        botonBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonBuscar.setFocusPainted(false);
        botonBuscar.setBackground(colorEstilo);
        botonBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        botonBuscar.setForeground(Color.WHITE);
        botonBuscar.setToolTipText("Buscar productos que contengan el nombre dado");
        add(botonBuscar);

        //Boton para buscar
        botonBuscar = new JButton(new ImageIcon("Iconos/Icono_buscar.png"));
        botonBuscar.setLocation(LARGO_VENTANA-44,110);
        botonBuscar.setSize(21,21);
        botonBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonBuscar.setBorderPainted(false);
        botonBuscar.setFocusPainted(false);
        botonBuscar.setBackground(colorEstilo);
        botonBuscar.setToolTipText("Buscar productos que contengan el nombre dado");
        add(botonBuscar);

        
        //Boton para registrar la entrada al almacen del producto
        botonConsultarListaPrecios = new JButton(new ImageIcon("Iconos/Icono_consultarPrecios.png"));
        botonConsultarListaPrecios.setLocation(LARGO_VENTANA-200,5);
        botonConsultarListaPrecios.setSize(35,35);
        botonConsultarListaPrecios.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonConsultarListaPrecios.setBorderPainted(false);
        botonConsultarListaPrecios.setContentAreaFilled(false);
        botonConsultarListaPrecios.setFocusPainted(false);
        botonConsultarListaPrecios.setToolTipText("Acceder a la lista de precios para agregar, eliminar o editar productos");
        panelTitulo.add(botonConsultarListaPrecios);

        //Boton para editar producto
        botonFinalizarVenta = new JButton(new ImageIcon("Iconos/Icono_cancelarVenta.png"));
        botonFinalizarVenta.setLocation(LARGO_VENTANA-120,5);
        botonFinalizarVenta.setSize(35,35);
        botonFinalizarVenta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonFinalizarVenta.setBorderPainted(false);
        botonFinalizarVenta.setContentAreaFilled(false);
        botonFinalizarVenta.setToolTipText("Abortar la venta actual reiniciando el punto de venta");
        panelTitulo.add(botonFinalizarVenta);

        //Boton para quitar producto 
        botonEliminarProducto = new JButton(new ImageIcon("Iconos/Icono_quitarProductoVenta.png"));
        botonEliminarProducto.setLocation(LARGO_VENTANA-65,6);
        botonEliminarProducto.setSize(35,35);
        botonEliminarProducto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonEliminarProducto.setBorderPainted(false);
        botonEliminarProducto.setContentAreaFilled(false);
        botonEliminarProducto.setToolTipText("Quitar el producto seleccionado de la venta actual");
        panelTitulo.add(botonEliminarProducto);

        //Etiqueta con el nombre de la aplicacion
        labelNombreAplicacion = new JLabel("Sistema de ventas");
        labelNombreAplicacion.setLocation(55,5);
        labelNombreAplicacion.setSize(400,20);
        labelNombreAplicacion.setForeground(Color.WHITE);
        labelNombreAplicacion.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        panelTitulo.add(labelNombreAplicacion);

        //Etiqueta para describir la tarea de la ventana
        labelTitulo = new JLabel("Realizar una venta");
        labelTitulo.setLocation(55,27);
        labelTitulo.setSize(400,20);
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        panelTitulo.add(labelTitulo);

        //Etiqueta para describir el funcionamiento de la busqueda
        labelDescripcionBuscar = new JLabel("Busqueda por nombre");
        labelDescripcionBuscar.setLocation(LARGO_VENTANA-245,92);
        labelDescripcionBuscar.setSize(150,20);
        labelDescripcionBuscar.setForeground(Color.DARK_GRAY);
        labelDescripcionBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        add(labelDescripcionBuscar);

        //Etiqueta para describir el funcionamiento de la busqueda
        labelDescripcionBuscar = new JLabel("Ingresar codigo de articulo");
        labelDescripcionBuscar.setLocation(13,92);
        labelDescripcionBuscar.setSize(150,20);
        labelDescripcionBuscar.setForeground(Color.DARK_GRAY);
        labelDescripcionBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        add(labelDescripcionBuscar);

        //Etiqueta para describir el funcionamiento de la busqueda
        labelDescripcionBuscar = new JLabel("Cantidad");
        labelDescripcionBuscar.setLocation(258,92);
        labelDescripcionBuscar.setSize(150,20);
        labelDescripcionBuscar.setForeground(Color.DARK_GRAY);
        labelDescripcionBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        add(labelDescripcionBuscar);

        //Etiqueta para describir el funcionamiento de la busqueda
        labelDescripcionBuscar = new JLabel("Total de compra: $0.00");
        labelDescripcionBuscar.setLocation(13,440);
        labelDescripcionBuscar.setSize(400,40);
        labelDescripcionBuscar.setForeground(new Color(51,51,51));
        labelDescripcionBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        add(labelDescripcionBuscar);


        //Etiqueta para describir el funcionamiento de la busqueda
        labelDescripcionBuscar = new JLabel("Recibido: $");
        labelDescripcionBuscar.setLocation(225,485);
        labelDescripcionBuscar.setSize(400,20);
        labelDescripcionBuscar.setForeground(new Color(51,51,51));
        labelDescripcionBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        add(labelDescripcionBuscar);

        //Etiqueta para describir el funcionamiento de la busqueda
        labelDescripcionBuscar = new JLabel("Cantidad de artículos: 0");
        labelDescripcionBuscar.setLocation(13,475);
        labelDescripcionBuscar.setSize(400,40);
        labelDescripcionBuscar.setForeground(Color.DARK_GRAY);
        labelDescripcionBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        add(labelDescripcionBuscar);


        //Etiqueta para describir el funcionamiento de la busqueda
        labelDescripcionBuscar = new JLabel("×");
        labelDescripcionBuscar.setLocation(243,108);
        labelDescripcionBuscar.setSize(20,20);
        labelDescripcionBuscar.setForeground(new Color(163,10,5));
        labelDescripcionBuscar.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
        add(labelDescripcionBuscar);

        //Etiqueta para indicar el funcionamiento del boton eliminar
        labelDescripcionEliminarProducto = new JLabel("Quitar");
        labelDescripcionEliminarProducto.setLocation(LARGO_VENTANA-73,37);
        labelDescripcionEliminarProducto.setSize(51,20);
        labelDescripcionEliminarProducto.setHorizontalAlignment(JLabel.CENTER);
        labelDescripcionEliminarProducto.setForeground(Color.WHITE);
        labelDescripcionEliminarProducto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panelTitulo.add(labelDescripcionEliminarProducto);

        //Etiqueta para indicar el funcionamiento del boton editar
        labelDescripcionFinalizarVenta = new JLabel("Abortar");
        labelDescripcionFinalizarVenta.setLocation(LARGO_VENTANA-128,37);
        labelDescripcionFinalizarVenta.setSize(51,20);
        labelDescripcionFinalizarVenta.setHorizontalAlignment(JLabel.CENTER);
        labelDescripcionFinalizarVenta.setForeground(Color.WHITE);
        labelDescripcionFinalizarVenta.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panelTitulo.add(labelDescripcionFinalizarVenta);

        //Etiqueta para indicar el funcionamiento del boton de registrar entrada al almacen
        labelDescripcionConsultarListaPrecios = new JLabel("Consultar");
        labelDescripcionConsultarListaPrecios.setLocation(LARGO_VENTANA-215,37);
        labelDescripcionConsultarListaPrecios.setSize(65,20);
        labelDescripcionConsultarListaPrecios.setHorizontalAlignment(JLabel.CENTER);
        labelDescripcionConsultarListaPrecios.setForeground(Color.WHITE);
        labelDescripcionConsultarListaPrecios.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panelTitulo.add(labelDescripcionConsultarListaPrecios);
        
        //Etiqueta con instrucciones sobre el funcionamiento de la ventana
        labelInstrucciones = new JLabel("Incluya nuevos productos"+
        " o use el panel superior para quitar elementos de la venta");
        labelInstrucciones.setLocation(13,70);
        labelInstrucciones.setSize(700,20);
        labelInstrucciones.setForeground(Color.DARK_GRAY);
        labelInstrucciones.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(labelInstrucciones);

        //Columnas para la tabla
        columnaCantidad = new TableColumn();
        columnaUnidadVenta = new TableColumn();
        columnaCodigo = new TableColumn();
        columnaDescripcion = new TableColumn();
        columnaPrecioUnitario = new TableColumn();
        columnaTotal = new TableColumn();

        //Modelo para la tabla
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn(columnaCantidad);
        modeloTabla.addColumn(columnaUnidadVenta);
        modeloTabla.addColumn(columnaCodigo);
        modeloTabla.addColumn(columnaDescripcion);
        modeloTabla.addColumn(columnaPrecioUnitario);
        modeloTabla.addColumn(columnaTotal);

        //Tabla de datos 
        tablaProductos = new JTable(modeloTabla);
        tablaProductos.getColumnModel().getColumn(0).setPreferredWidth((LARGO_VENTANA-60)/8);
        tablaProductos.getColumnModel().getColumn(1).setPreferredWidth((LARGO_VENTANA-60)/6);
        tablaProductos.getColumnModel().getColumn(2).setPreferredWidth((LARGO_VENTANA-60)/3);
        tablaProductos.getColumnModel().getColumn(3).setPreferredWidth((LARGO_VENTANA-60)/6);
        tablaProductos.getColumnModel().getColumn(4).setPreferredWidth((LARGO_VENTANA-60)/6);
        tablaProductos.getColumnModel().getColumn(5).setPreferredWidth((LARGO_VENTANA-60)/6);
        tablaProductos.getColumnModel().getColumn(0).setHeaderValue("Cantidad");
        tablaProductos.getColumnModel().getColumn(1).setHeaderValue("Codigo");
        tablaProductos.getColumnModel().getColumn(2).setHeaderValue("Descripcion");
        tablaProductos.getColumnModel().getColumn(3).setHeaderValue("Unidad venta");
        tablaProductos.getColumnModel().getColumn(4).setHeaderValue("Precio unitario");
        tablaProductos.getColumnModel().getColumn(5).setHeaderValue("Subtotal");
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Recuadro con opcion de scrolling para la tabla
        panelConScroll = new JScrollPane(tablaProductos);
        panelConScroll.setLocation(13,138);
        panelConScroll.setSize(LARGO_VENTANA-35,ALTO_VENTANA-250);
        panelConScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); 
        add(panelConScroll);

    } //End constuyeComponentes


    /**
     * El metodo designa el control del view al Controller dado
     * @param theController Controlador de la interfaz
     */
    @Override
    public void setActionListener(Controller theController) {
        botonConsultarListaPrecios.addActionListener(theController);
        botonFinalizarVenta.addActionListener(theController);
        botonEliminarProducto.addActionListener(theController);
        botonBuscar.addActionListener(theController);
    } //End setActionListener


     /************************************************
     * Metodos de la clase
     *************************************************/
    /**
     * El metodo lanza la interfaz tras su construccion
     */
    public void iniciarInterfaz(){
        setVisible(true);
    } //End iniciar interfaz

    
} //End class