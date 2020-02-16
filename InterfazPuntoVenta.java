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
    JLabel              labelDescripcionAbortarVenta;
    JLabel              labelDescripcionQuitarProducto;
    JLabel              labelDescripcionConsultarListaPrecios;
    JLabel              labelDescripcionCampoCodigo;
    JLabel              labelDescripcionCampoCantidad;
    JLabel              labelTotalCompra;
    JLabel              labelCantidadArticulos;
    JLabel              labelSignoMultiplicacion;
    JLabel              labelMontoRecibido;
    JLabel              labelError;
    JButton             botonAgregar;
    JButton             botonFinalizar;
    JButton             botonAbortarVenta;
    JButton             botonQuitarProducto;
    JButton             botonConsultarListaPrecios;
    JTextField          campoAgregar;
    JTextField          campoRecibido;
    JTextField          campoCantidadAgregar;
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
        setLocation(300,100);
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
        imagenVentana = new JLabel(new ImageIcon("Iconos/Icono_puntoVenta.png"));
        imagenVentana.setLocation(5,7);
        imagenVentana.setSize(40,40);
        panelTitulo.add(imagenVentana);

        //Campo para especificar el codigo de barras del producto a agregar
        campoAgregar = new JTextField();
        campoAgregar.setLocation(13,110);
        campoAgregar.setSize(230,22);
        add(campoAgregar);

        //Campo para indicar la cantidad de producto a agregar
        campoCantidadAgregar = new JTextField("1");
        campoCantidadAgregar.setLocation(258,110);
        campoCantidadAgregar.setSize(60,22);
        add(campoCantidadAgregar);

        //Campo para indicar el monto recibido por el cliente
        campoRecibido = new JTextField("0.00");
        campoRecibido.setLocation(303,483);
        campoRecibido.setSize(105,26);
        campoRecibido.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(campoRecibido);

        //Boton para agregar un producto a la venta actual
        botonAgregar = new JButton(new ImageIcon("Iconos/Icono_agregarVenta.png"));
        botonAgregar.setText("Agregar");
        botonAgregar.setLocation(325,110);
        botonAgregar.setSize(95,21);
        botonAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonAgregar.setFocusPainted(false);
        botonAgregar.setBackground(colorEstilo);
        botonAgregar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        botonAgregar.setForeground(Color.WHITE);
        botonAgregar.setToolTipText("Agregar el producto con el codigo de barras dado a la venta actual");
        add(botonAgregar);

        //Boton para finalizar exitosamente la venta en curso
        botonFinalizar = new JButton(new ImageIcon("Iconos/Icono_finalizarVenta.png"));
        botonFinalizar.setText("Finalizar");
        botonFinalizar.setLocation(LARGO_VENTANA-138,ALTO_VENTANA-73);
        botonFinalizar.setSize(115,30);
        botonFinalizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonFinalizar.setFocusPainted(false);
        botonFinalizar.setBackground(colorEstilo);
        botonFinalizar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        botonFinalizar.setForeground(Color.WHITE);
        botonFinalizar.setEnabled(false);
        botonFinalizar.setToolTipText("Terminar exitosamente con la venta actual");
        add(botonFinalizar);
        
        //Boton para cambiar a la ventana de consultar lista de precios
        botonConsultarListaPrecios = new JButton(new ImageIcon("Iconos/Icono_consultarPrecios.png"));
        botonConsultarListaPrecios.setLocation(LARGO_VENTANA-200,5);
        botonConsultarListaPrecios.setSize(35,35);
        botonConsultarListaPrecios.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonConsultarListaPrecios.setBorderPainted(false);
        botonConsultarListaPrecios.setContentAreaFilled(false);
        botonConsultarListaPrecios.setFocusPainted(false);
        botonConsultarListaPrecios.setToolTipText("Acceder a la lista de precios para agregar, eliminar o editar productos");
        panelTitulo.add(botonConsultarListaPrecios);

        //Boton para abortar la venta en curso
        botonAbortarVenta = new JButton(new ImageIcon("Iconos/Icono_cancelarVenta.png"));
        botonAbortarVenta.setLocation(LARGO_VENTANA-120,5);
        botonAbortarVenta.setSize(35,35);
        botonAbortarVenta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonAbortarVenta.setBorderPainted(false);
        botonAbortarVenta.setContentAreaFilled(false);
        botonAbortarVenta.setToolTipText("Abortar la venta actual reiniciando el punto de venta");
        panelTitulo.add(botonAbortarVenta);

        //Boton para quitar el producto seleccionado de la venta actual
        botonQuitarProducto = new JButton(new ImageIcon("Iconos/Icono_quitarProductoVenta.png"));
        botonQuitarProducto.setLocation(LARGO_VENTANA-65,6);
        botonQuitarProducto.setSize(35,35);
        botonQuitarProducto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonQuitarProducto.setBorderPainted(false);
        botonQuitarProducto.setContentAreaFilled(false);
        botonQuitarProducto.setToolTipText("Quitar el producto seleccionado de la venta actual");
        panelTitulo.add(botonQuitarProducto);

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

        //Etiqueta para indicar el proceso para agregar un articulo a la venta
        labelDescripcionCampoCodigo = new JLabel("Ingresar codigo de articulo");
        labelDescripcionCampoCodigo.setLocation(13,92);
        labelDescripcionCampoCodigo.setSize(150,20);
        labelDescripcionCampoCodigo.setForeground(Color.DARK_GRAY);
        labelDescripcionCampoCodigo.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        add(labelDescripcionCampoCodigo);

        //Etiqueta para describir el campo cantidad
        labelDescripcionCampoCantidad = new JLabel("Cantidad");
        labelDescripcionCampoCantidad.setLocation(258,92);
        labelDescripcionCampoCantidad.setSize(150,20);
        labelDescripcionCampoCantidad.setForeground(Color.DARK_GRAY);
        labelDescripcionCampoCantidad.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        add(labelDescripcionCampoCantidad);

        //Etiqueta para indicar el total de la compra
        labelTotalCompra = new JLabel("Total de compra: $0.00");
        labelTotalCompra.setLocation(13,440);
        labelTotalCompra.setSize(400,40);
        labelTotalCompra.setForeground(new Color(51,51,51));
        labelTotalCompra.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        add(labelTotalCompra);

        //Etiqueta para indicar lo que se espera en el campo recibido
        labelMontoRecibido = new JLabel("Recibido: $");
        labelMontoRecibido.setLocation(225,485);
        labelMontoRecibido.setSize(400,20);
        labelMontoRecibido.setForeground(new Color(51,51,51));
        labelMontoRecibido.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        add(labelMontoRecibido);

        //Etiqueta para indicar la cantidad de articulos en la venta actual
        labelCantidadArticulos = new JLabel("Cantidad de artículos: 0.00");
        labelCantidadArticulos.setLocation(13,475);
        labelCantidadArticulos.setSize(400,40);
        labelCantidadArticulos.setForeground(Color.DARK_GRAY);
        labelCantidadArticulos.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        add(labelCantidadArticulos);

        //Etiqueta para agregar un signo de multiplicacion entre los campos codigo y cantidad
        labelSignoMultiplicacion = new JLabel("×");
        labelSignoMultiplicacion.setLocation(243,108);
        labelSignoMultiplicacion.setSize(20,20);
        labelSignoMultiplicacion.setForeground(new Color(163,10,5));
        labelSignoMultiplicacion.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
        add(labelSignoMultiplicacion);

        //Etiqueta informar un error al agregar el producto
        labelError = new JLabel(new ImageIcon("Iconos/Icono_incorrecto.png"));
        labelError.setLocation(14,ALTO_VENTANA-134);
        labelError.setSize(300,20);
        labelError.setOpaque(true);
        labelError.setVisible(false);
        labelError.setBackground(new Color(255,166,166));
        labelError.setForeground(new Color(163,10,5));
        labelError.setHorizontalAlignment(JLabel.LEFT);
        labelError.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
        add(labelError);

        //Etiqueta para indicar el funcionamiento del boton quitar producto
        labelDescripcionQuitarProducto = new JLabel("Quitar");
        labelDescripcionQuitarProducto.setLocation(LARGO_VENTANA-73,37);
        labelDescripcionQuitarProducto.setSize(51,20);
        labelDescripcionQuitarProducto.setHorizontalAlignment(JLabel.CENTER);
        labelDescripcionQuitarProducto.setForeground(Color.WHITE);
        labelDescripcionQuitarProducto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panelTitulo.add(labelDescripcionQuitarProducto);

        //Etiqueta para indicar el funcionamiento del boton de abortar venta
        labelDescripcionAbortarVenta = new JLabel("Abortar");
        labelDescripcionAbortarVenta.setLocation(LARGO_VENTANA-128,37);
        labelDescripcionAbortarVenta.setSize(51,20);
        labelDescripcionAbortarVenta.setHorizontalAlignment(JLabel.CENTER);
        labelDescripcionAbortarVenta.setForeground(Color.WHITE);
        labelDescripcionAbortarVenta.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panelTitulo.add(labelDescripcionAbortarVenta);

        //Etiqueta para indicar el funcionamiento del boton de consultar en la lista de precios
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
        tablaProductos.getColumnModel().getColumn(1).setPreferredWidth((LARGO_VENTANA-60)/7);
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
        botonAgregar.addActionListener(theController);
        botonFinalizar.addActionListener(theController);
        botonAbortarVenta.addActionListener(theController);
        botonQuitarProducto.addActionListener(theController);
        botonConsultarListaPrecios.addActionListener(theController);
        
    } //End setActionListener

    public void setFocusListener(ControllerInterfazPuntoVenta theController) {
        campoRecibido.addFocusListener(theController);
    } //End setFocusListener

    public void setKeyListener(ControllerInterfazPuntoVenta theController) {
        campoRecibido.addKeyListener(theController);
        campoAgregar.addKeyListener(theController);
        campoCantidadAgregar.addKeyListener(theController);
    } //End setFocusListener

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