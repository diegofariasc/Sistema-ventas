import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

/**
 * La clase representa un cuadro de dialogo siguiendo 
 * la misma arquitectura del que se muestra en el ejemplo distribuido 
 * en el portafolio del curso con la diferencia de que este dialogo
 * solicita un valor numerico con la cantidad de producto recibida
 * en el abastecimiento
 * @author Diego Farias Castro
 */
@SuppressWarnings("serial")
public class DialogoAbastecer extends JDialog implements ActionListener, KeyListener {
    
    //Constantes para construir la ventana
    private static final int LARGO_VENTANA = 400;
    private static final int ALTO_VENTANA  = 250;

    JPanel      panelTitulo;
    JLabel      labelNombreAplicacion;
    JLabel      labelTitulo;
    JLabel      labelInstrucciones;
    JLabel      imagenVentana;
    JLabel      imagenValidezCantidad;
    JLabel      labelInformacionProducto;
    JTextField  campoCantidadAgregar;
    JButton     botonAceptar;
    JButton     botonCancelar;
    Color       colorEstilo;

    boolean     seAceptaLaAccion;       // Guardar si el usuario acepta
    double      cantidadAgregar;        // Valor dado en el dialogo
    String      nombreProducto;         // Datos a mostrar
    int         unidadVenta;

    /************************************************
     * Constructor de la clase
     *************************************************/
    public DialogoAbastecer(int unidadVenta, String nombreProducto){
        
        colorEstilo =  new Color(13,62,145);
        setTitle("Sistema de ventas [Ingreso a almacen]");
        setSize(LARGO_VENTANA,ALTO_VENTANA);
        setLocation(450,260);
        setLayout(null);
        setResizable(false);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.nombreProducto = nombreProducto;
        this.unidadVenta    = unidadVenta;

        //Construir los componentes de la ventana
        constuyeComponentes();

    } //End Constructor

    
    /************************************************
     * Implementacion de la interfaz View
     *************************************************/
    /**
     * El metodo construye cada componente de la ventana
     */
    public void constuyeComponentes() {

        //Panel superior de la ventana
        panelTitulo = new JPanel();
        panelTitulo.setSize(LARGO_VENTANA,60);
        panelTitulo.setBackground(colorEstilo);
        panelTitulo.setLayout(null);
        add(panelTitulo);

        //Imagen en la esquina superior izquierda
        imagenVentana = new JLabel(new ImageIcon("Iconos/Icono_entradaAlmacen.png")); 
        imagenVentana.setLocation(5,7);
        imagenVentana.setSize(40,40);
        panelTitulo.add(imagenVentana);

        //Boton para cancelar 
        botonCancelar = new JButton("Cancelar");
        botonCancelar.setLocation(LARGO_VENTANA-225,ALTO_VENTANA-63);
        botonCancelar.setSize(100,25);
        botonCancelar.setForeground(Color.WHITE);
        botonCancelar.setBackground(new Color(84,84,84));
        botonCancelar.setFocusPainted(false);
        botonCancelar.setOpaque(true);
        botonCancelar.setBorder(null);
        botonCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        botonCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(botonCancelar);

        //Boton para aceptar 
        botonAceptar = new JButton("Aceptar");
        botonAceptar.setLocation(LARGO_VENTANA-118,ALTO_VENTANA-63);
        botonAceptar.setSize(100,25);
        botonAceptar.setForeground(Color.WHITE);
        botonAceptar.setBackground(new Color(84,84,84));
        botonAceptar.setOpaque(true);
        botonAceptar.setFocusPainted(false);
        botonAceptar.setBorder(null);
        botonAceptar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
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
        labelTitulo = new JLabel("Registrar la entrada de producto al almacén");
        labelTitulo.setLocation(55,27);
        labelTitulo.setSize(400,20);
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        panelTitulo.add(labelTitulo);

        //Etiqueta con instrucciones sobre el funcionamiento de la ventana
        labelInstrucciones = new JLabel("Indique la cantidad que ingresará al almacen:");
        labelInstrucciones.setLocation(13,70);
        labelInstrucciones.setSize(700,20);
        labelInstrucciones.setForeground(Color.DARK_GRAY);
        labelInstrucciones.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(labelInstrucciones);

        //Etiqueta para indicar el producto al que se agregara 
        labelInformacionProducto = new JLabel("Producto: "+nombreProducto);
        labelInformacionProducto.setLocation(13,90);
        labelInformacionProducto.setSize(700,20);
        labelInformacionProducto.setForeground(Color.DARK_GRAY);
        labelInformacionProducto.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        add(labelInformacionProducto);

        //Campo para indicar la cantidad de producto que entrara al almacen
        campoCantidadAgregar = new JTextField("1");
        campoCantidadAgregar.setSize(60,20);
        campoCantidadAgregar.setLocation(13,120); 
        campoCantidadAgregar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        add(campoCantidadAgregar);

        //Imagen en la esquina superior izquierda
        imagenValidezCantidad = new JLabel(new ImageIcon("Iconos/Icono_correcto.png")); 
        imagenValidezCantidad.setLocation(80,70);
        imagenValidezCantidad.setSize(20,120);
        add(imagenValidezCantidad);

        //Agregar listeners
        campoCantidadAgregar.addKeyListener(this);
        botonAceptar.addActionListener(this);
        botonCancelar.addActionListener(this);

    } //End constuyeComponentes


     /************************************************
     * Metodos de la clase
     *************************************************/
    /**
     * El metodo lanza la interfaz tras su construccion
     */
    public void iniciarDialogo(){
        setVisible(true);
    } //End iniciar interfaz

    /**
     * El metodo oculta la interfaz
     */
    public void ocultarDialogo(){
        setVisible(false);
    } //End ocultarInterfaz    

    public void actionPerformed(ActionEvent evento){

        //Obtener el boton que causo el evento
        JButton botonAccionado;
        botonAccionado = (JButton) evento.getSource();

        //En caso que sea el boton aceptar
        if(botonAccionado == botonAceptar)
        {
            ocultarDialogo();
            seAceptaLaAccion = true;
        }//End if

        //En caso que sea el boton cancelar
        if(botonAccionado == botonCancelar)
        {
            ocultarDialogo();
            seAceptaLaAccion = false;
        }//End if

    }//end actionPerformed


    /************************************************
     * Implementacion de la interfaz KeyListener
     *************************************************/  
    
   /**
     * El metodo es llamado automaticamente cuando el despues 
     * de que el usuario presionara una tecla en un control determinado
     * @param evento Con los datos del evento del cual deriva su invocacion
     */     
    @Override
    public void keyReleased(KeyEvent evento) {
        try {
            
            //Asignar cantidadAgregar dependiendo la unidad de venta 
            if (unidadVenta == Producto.KILO || unidadVenta == Producto.LITRO )
                cantidadAgregar = Double.parseDouble(campoCantidadAgregar.getText());
            else
                cantidadAgregar = Integer.parseInt(campoCantidadAgregar.getText());

            //Lanzar excepcion si la cantidad a agregar es menor a cero
            if (cantidadAgregar<0) throw new NumberFormatException();

            //Validar como correcto lo ingresado en el campoCantidadAgregar
            imagenValidezCantidad.setIcon(new ImageIcon("Iconos/Icono_correcto.png"));

            //Habilitar el boton para aceptar
            botonAceptar.setEnabled(true);


        } //End try
        catch (NumberFormatException excepcion){

            //Indicar que hay un error en los datos
            imagenValidezCantidad.setIcon(new ImageIcon("Iconos/Icono_incorrecto.png"));

            //Deshabilitar el boton aceptar
            botonAceptar.setEnabled(false);

        } //End try

    } //End keyReleased

    //Eventos no necesarios pero agregados para cumplir con la interfaz
    @Override
    public void keyTyped(KeyEvent e) {} 

    @Override
    public void keyPressed(KeyEvent e) {}


    /************************************************
     * Metodos
     *************************************************/

     /**
      * El metodo devuelve si el usuario acepto o no el abastecimiento
      */
    public boolean seAceptaLaAccion(){
        return seAceptaLaAccion;
    } //End seAceptaLaAccion

    /**
     * El metodo devuelve el valor que el usuario indico en el campo de 
     * cantidad a agregar
     */
    public double getCantidadAgregar(){
        return cantidadAgregar;
    } //End getCantidadAgregar


} //End class