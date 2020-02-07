import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class InterfazAgregarProducto extends JDialog implements View {

    private static final int LARGO_VENTANA = 400;
    private static final int ALTO_VENTANA  = 410;

    JPanel              panelTitulo;
    JLabel              labelNombreAplicacion;
    JLabel              labelTitulo;
    JLabel              labelInstrucciones;
    JLabel              imagenVentana;
    JLabel              labelDescripcionCampoCodigo;
    JLabel              labelDescripcionCampoDescripcion;
    JLabel              labelDescripcionCampoPrecioCompra;
    JLabel              labelSignoPesosPrecioCompra;
    JLabel              labelDescripcionCampoPrecioVenta;
    JLabel              labelSignoPesosPrecioVenta;
    JLabel              labelDescripcionCampoUnidadVenta;
    JLabel              labelDescripcionCampoDepartamento;
    JLabel              labelDescripcionCampoDisponibilidad;
    JLabel              imagenValidezCampoCodigo;
    JLabel              imagenValidezCampoDescripcion;
    JLabel              imagenValidezCampoPrecios;
    JLabel              imagenValidezCampoDepartamento;
    JLabel              imagenValidezCampoDisponibilidad;
    JButton             botonAceptar;
    JButton             botonCancelar;
    JTextField          campoCodigo;
    JTextField          campoDescripcion;
    JTextField          campoPrecioCompra;
    JTextField          campoPrecioVenta;
    JTextField          campoDepartamento;
    JTextField          campoDisponibilidad;
    JComboBox<String>   campoUnidadVenta;
    Color               colorEstilo;

    /************************************************
     * Constructor de la clase
     *************************************************/
    public InterfazAgregarProducto(){
        
        //Parámetros de la ventana
        colorEstilo = new Color(13,62,145);
        setTitle("Registrar nuevo producto");
        setSize(LARGO_VENTANA,ALTO_VENTANA);
        setLocation(200,80);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

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
        imagenVentana = new JLabel(new ImageIcon("Iconos/Icono_ventanaNuevoProducto.png"));
        imagenVentana.setLocation(5,7);
        imagenVentana.setSize(40,40);
        panelTitulo.add(imagenVentana);

        //Campo para el codigo de barras
        campoCodigo = new JTextField();
        campoCodigo.setLocation(13,113);
        campoCodigo.setSize(250,22);
        add(campoCodigo);

        //Campo para la descripcion del producto
        campoDescripcion = new JTextField();
        campoDescripcion.setLocation(13,158);
        campoDescripcion.setSize(250,22);
        add(campoDescripcion);

        //Campo para el precio de compra del producto
        campoPrecioCompra = new JTextField("0.00");
        campoPrecioCompra.setLocation(23,203);
        campoPrecioCompra.setSize(110,22);
        add(campoPrecioCompra);

        //Campo para el precio de venta del producto
        campoPrecioVenta = new JTextField("0.00");
        campoPrecioVenta.setLocation(153,203);
        campoPrecioVenta.setSize(110,22);
        add(campoPrecioVenta);

        //Campo para el departamento
        campoDepartamento = new JTextField();
        campoDepartamento.setLocation(13,248);
        campoDepartamento.setSize(250,22);
        add(campoDepartamento);

        //Campo para la disponibilidad del producto
        campoDisponibilidad = new JTextField();
        campoDisponibilidad.setLocation(143,293);
        campoDisponibilidad.setSize(120,22);
        add(campoDisponibilidad);

        //Selector de la unidad de venta del producto
        campoUnidadVenta = new JComboBox<String>();
        campoUnidadVenta.setLocation(13,293);
        campoUnidadVenta.setSize(110,22);
        campoUnidadVenta.addItem("Pieza");
        campoUnidadVenta.addItem("Kilo");
        campoUnidadVenta.addItem("Paquete");
        campoUnidadVenta.addItem("Caja");
        campoUnidadVenta.addItem("Litro");
        campoUnidadVenta.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campoUnidadVenta.setBackground(new Color(235,235,235));
        add(campoUnidadVenta);

        //Imagen para indicar si lo introducido el campo codigo es valido o no
        imagenValidezCampoCodigo = new JLabel(new ImageIcon("Iconos/Icono_incorrecto.png"));
        imagenValidezCampoCodigo.setLocation(283,113);
        imagenValidezCampoCodigo.setSize(21,21);
        add(imagenValidezCampoCodigo);

        //Imagen para indicar si lo introducido el campo descripcion es valido o no
        imagenValidezCampoDescripcion = new JLabel(new ImageIcon("Iconos/Icono_incorrecto.png"));
        imagenValidezCampoDescripcion.setLocation(283,158);
        imagenValidezCampoDescripcion.setSize(21,21);
        add(imagenValidezCampoDescripcion);

        //Imagen para indicar si lo introducido el campo de precio compra y venta es valido o no
        imagenValidezCampoPrecios = new JLabel(new ImageIcon("Iconos/Icono_incorrecto.png"));
        imagenValidezCampoPrecios.setLocation(283,203);
        imagenValidezCampoPrecios.setSize(21,21);
        add(imagenValidezCampoPrecios);

        //Imagen para indicar si lo introducido el campo departamento es valido o no
        imagenValidezCampoDepartamento = new JLabel(new ImageIcon("Iconos/Icono_incorrecto.png"));
        imagenValidezCampoDepartamento.setLocation(283,248);
        imagenValidezCampoDepartamento.setSize(21,21);
        add(imagenValidezCampoDepartamento);

        //Imagen para indicar si lo introducido el campo disponibilidad es valido o no
        imagenValidezCampoDisponibilidad = new JLabel(new ImageIcon("Iconos/Icono_incorrecto.png"));
        imagenValidezCampoDisponibilidad.setLocation(283,293);
        imagenValidezCampoDisponibilidad.setSize(21,21);
        add(imagenValidezCampoDisponibilidad);

        //Boton para cancelar 
        botonCancelar = new JButton("Cancelar");
        botonCancelar.setLocation(LARGO_VENTANA-225,ALTO_VENTANA-63);
        botonCancelar.setSize(100,25);
        botonCancelar.setForeground(Color.WHITE);
        botonCancelar.setBackground(new Color(84,84,84));
        botonCancelar.setFocusPainted(false);
        botonCancelar.setBorder(null);
        botonCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        botonCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(botonCancelar);

        //Boton para agregar el producto
        botonAceptar = new JButton("Aceptar");
        botonAceptar.setLocation(LARGO_VENTANA-118,ALTO_VENTANA-63);
        botonAceptar.setSize(100,25);
        botonAceptar.setForeground(Color.WHITE);
        botonAceptar.setBackground(new Color(84,84,84));
        botonAceptar.setFocusPainted(false);
        botonAceptar.setBorder(null);
        botonAceptar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        botonAceptar.setEnabled(false);
        botonAceptar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(botonAceptar);

        //Etiqueta con el nombre de la aplicacion
        labelNombreAplicacion = new JLabel("Sistema de ventas");
        labelNombreAplicacion.setLocation(55,5);
        labelNombreAplicacion.setSize(400,20);
        labelNombreAplicacion.setForeground(Color.WHITE);
        labelNombreAplicacion.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        panelTitulo.add(labelNombreAplicacion);

        //Etiqueta para describir la tarea de la ventana
        labelTitulo = new JLabel("Registrar nuevo producto");
        labelTitulo.setLocation(55,27);
        labelTitulo.setSize(400,20);
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        panelTitulo.add(labelTitulo);

        //Etiqueta con instrucciones sobre el funcionamiento de la ventana
        labelInstrucciones = new JLabel("Introduzca la informacion del producto");
        labelInstrucciones.setLocation(13,70);
        labelInstrucciones.setSize(700,20);
        labelInstrucciones.setForeground(Color.DARK_GRAY);
        labelInstrucciones.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(labelInstrucciones);

        //Etiqueta para describir el campo codigo
        labelDescripcionCampoCodigo = new JLabel("Codigo de barras");
        labelDescripcionCampoCodigo.setLocation(13,95);
        labelDescripcionCampoCodigo.setSize(150,20);
        labelDescripcionCampoCodigo.setForeground(Color.DARK_GRAY);
        labelDescripcionCampoCodigo.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        add(labelDescripcionCampoCodigo);

        //Etiqueta para describir el campo descripcion
        labelDescripcionCampoDescripcion = new JLabel("Descripcion del producto");
        labelDescripcionCampoDescripcion.setLocation(13,140);
        labelDescripcionCampoDescripcion.setSize(150,20);
        labelDescripcionCampoDescripcion.setForeground(Color.DARK_GRAY);
        labelDescripcionCampoDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        add(labelDescripcionCampoDescripcion);

        //Etiqueta para describir el campo precio de compra
        labelDescripcionCampoPrecioCompra = new JLabel("Precio de compra");
        labelDescripcionCampoPrecioCompra.setLocation(13,185);
        labelDescripcionCampoPrecioCompra.setSize(150,20);
        labelDescripcionCampoPrecioCompra.setForeground(Color.DARK_GRAY);
        labelDescripcionCampoPrecioCompra.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        add(labelDescripcionCampoPrecioCompra);

        //Etiqueta para agregar el signo de pesos al campo de precio de compra
        labelSignoPesosPrecioCompra = new JLabel("$");
        labelSignoPesosPrecioCompra.setLocation(13,202);
        labelSignoPesosPrecioCompra.setSize(150,20);
        labelSignoPesosPrecioCompra.setForeground(Color.DARK_GRAY);
        labelSignoPesosPrecioCompra.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
        add(labelSignoPesosPrecioCompra);

        //Etiqueta para describir el campo precio de venta
        labelDescripcionCampoPrecioVenta = new JLabel("Precio de venta");
        labelDescripcionCampoPrecioVenta.setLocation(143,185);
        labelDescripcionCampoPrecioVenta.setSize(150,20);
        labelDescripcionCampoPrecioVenta.setForeground(Color.DARK_GRAY);
        labelDescripcionCampoPrecioVenta.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        add(labelDescripcionCampoPrecioVenta);
        
        //Etiqueta para agregar el signo de pesos al campo precio de venta
        labelSignoPesosPrecioVenta= new JLabel("$");
        labelSignoPesosPrecioVenta.setLocation(143,202);
        labelSignoPesosPrecioVenta.setSize(150,20);
        labelSignoPesosPrecioVenta.setForeground(Color.DARK_GRAY);
        labelSignoPesosPrecioVenta.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
        add(labelSignoPesosPrecioVenta);

        //Etiqueta para describir el campo departamento
        labelDescripcionCampoDepartamento = new JLabel("Departamento");
        labelDescripcionCampoDepartamento.setLocation(13,230);
        labelDescripcionCampoDepartamento.setSize(150,20);
        labelDescripcionCampoDepartamento.setForeground(Color.DARK_GRAY);
        labelDescripcionCampoDepartamento.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        add(labelDescripcionCampoDepartamento);

        //Etiqueta para describir el campo unidad de venta 
        labelDescripcionCampoUnidadVenta = new JLabel("Unidad de venta");
        labelDescripcionCampoUnidadVenta.setLocation(13,275);
        labelDescripcionCampoUnidadVenta.setSize(150,20);
        labelDescripcionCampoUnidadVenta.setForeground(Color.DARK_GRAY);
        labelDescripcionCampoUnidadVenta.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        add(labelDescripcionCampoUnidadVenta);

        //Etiqueta para describir el campo cantidad disponible
        labelDescripcionCampoDisponibilidad = new JLabel("Cantidad disponible");
        labelDescripcionCampoDisponibilidad.setLocation(143,275);
        labelDescripcionCampoDisponibilidad.setSize(150,20);
        labelDescripcionCampoDisponibilidad.setForeground(Color.DARK_GRAY);
        labelDescripcionCampoDisponibilidad.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        add(labelDescripcionCampoDisponibilidad);

    } //End constuyeComponentes


    /**
     * El metodo designa el control del view al Controller dado
     * @param theController Controlador de la interfaz
     */
    @Override
    public void setActionListener(Controller theController) {
        botonAceptar.addActionListener(theController);
        botonCancelar.addActionListener(theController);
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