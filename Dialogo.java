import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

@SuppressWarnings("serial")
public class Dialogo extends JDialog implements ActionListener {
    
    private static final int LARGO_VENTANA = 400;
    private static final int ALTO_VENTANA  = 180;

    JPanel  panelTitulo;
    JLabel  labelNombreAplicacion;
    JLabel  labelTitulo;
    JLabel  labelInstrucciones;
    JLabel  imagenVentana;
    JButton botonAceptar;
    JButton botonCancelar;
    Color   colorEstilo;
    String  titulo;
    String  mensaje;
    int     tipoMensaje;
    boolean seAceptaLaAccion;

    public static int MENSAJE_ADVERTENCIA = 0;
    public static int MENSAJE_INFORMATIVO = 1;


    /************************************************
     * Constructor de la clase
     *************************************************/
    public Dialogo(String titulo,String mensaje, int tipoMensaje){
        
        //Parámetros de la ventana
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.tipoMensaje = tipoMensaje;
        
        
        colorEstilo = tipoMensaje == MENSAJE_ADVERTENCIA ? new Color(207,86,0) : new Color(10,128,16);
        setTitle("Sistema de ventas [Dialogo]");
        setSize(LARGO_VENTANA,ALTO_VENTANA);
        setLocation(450,260);
        setLayout(null);
        setResizable(false);
        setModal(true);
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
    public void constuyeComponentes() {

        //Panel superior de la ventana
        panelTitulo = new JPanel();
        panelTitulo.setSize(LARGO_VENTANA,60);
        panelTitulo.setBackground(colorEstilo);
        panelTitulo.setLayout(null);
        add(panelTitulo);

        //Imagen en la esquina superior izquierda
        imagenVentana = new JLabel();
        imagenVentana.setIcon(tipoMensaje == MENSAJE_ADVERTENCIA ?  new ImageIcon("Iconos/Icono_advertencia.png") :
                                                                    new ImageIcon("Iconos/Icono_confirmado.png"));
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
        botonCancelar.setVisible(tipoMensaje==MENSAJE_ADVERTENCIA);
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
        botonAceptar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(botonAceptar);

        //Etiqueta con el nombre de la aplicacion
        labelNombreAplicacion = new JLabel(tipoMensaje == MENSAJE_ADVERTENCIA ? "Espera de confirmacion" : 
                                                                                "Informacion");
        labelNombreAplicacion.setLocation(55,5);
        labelNombreAplicacion.setSize(400,20);
        labelNombreAplicacion.setForeground(Color.WHITE);
        labelNombreAplicacion.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        panelTitulo.add(labelNombreAplicacion);

        //Etiqueta para describir la tarea de la ventana
        labelTitulo = new JLabel(titulo);
        labelTitulo.setLocation(55,27);
        labelTitulo.setSize(400,20);
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        panelTitulo.add(labelTitulo);

        //Etiqueta con instrucciones sobre el funcionamiento de la ventana
        labelInstrucciones = new JLabel(mensaje);
        labelInstrucciones.setLocation(13,70);
        labelInstrucciones.setSize(700,20);
        labelInstrucciones.setForeground(Color.DARK_GRAY);
        labelInstrucciones.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(labelInstrucciones);

        botonAceptar.addActionListener(this);
        botonCancelar.addActionListener(this);

    } //End constuyeComponentes


     /************************************************
     * Metodos de la clase
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
    
    public boolean seAceptaLaAccion(){
        return seAceptaLaAccion;
    } //End seAceptaLaAccion

    
    public void actionPerformed(ActionEvent evento)
    {
        JButton botonAccionado;

        botonAccionado = (JButton) evento.getSource();

        if(botonAccionado == botonAceptar)
        {
            setVisible(false);
            seAceptaLaAccion = true;
        }//End if

        if(botonAccionado == botonCancelar)
        {
            setVisible(false);
            seAceptaLaAccion = false;
        }//End if

    }//end actionPerformed

} //End class