package CINEBAT_PROY1;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;



@SuppressWarnings("serial")
public class VentanaJuego extends JFrame {

	private PanelControl control; // instancia para el panel con los botones y las acciones
	private PanelBatalla batalla; // instancia para el panel con el mapa, los tanques, los proyectiles y las animaciones
	
	private int VERTSPLIT;
	private JSplitPane splitPane1;
	private JSplitPane splitPane2;
	public int pantalla_ancho_juego, pantalla_alto_juego,tamanno_controles_alto;
	
	public int tamanno_menuBar;
	private JPanel barra;

	/**
	 * Constructor para la ventana del juego, se crean dos paneles, uno para las animaciones del juego, barra de menú y
	 * otra para los controles, se separan por JSplitPane.
	 * 
	 * Se agregan botones para reiniciar y cerrar el juego, junto con los listeners
	 * 
	 * @param pantalla_ancho_juego, int que corresponde al ancho de la pantalla.
	 * @param pantalla_alto_juego, int que corresponde al alto de la pantalla.
	 * @param tamanno_controles, int que corresponde al tamaño del panel de los controles.
	 * @param tamanno_menuBar, int que corresponde al tamaño de la barra de menú.
	 */
	public VentanaJuego(int pantalla_ancho_juego, int pantalla_alto_juego,int tamanno_controles, int tamanno_menuBar,
			int cantidadTanquesJ1, int cantidadTanquesJ2,int misilPerforanteInt, int misil105Int ,int misil60Int,
			String viento, String gravedad, int usarIA){
		
		VERTSPLIT = JSplitPane.VERTICAL_SPLIT; //se utiliza para dividir la pantalla (VENTANA) en dos 
		this.pantalla_ancho_juego=pantalla_ancho_juego;
		this.pantalla_alto_juego=pantalla_alto_juego;
		this.tamanno_controles_alto=tamanno_controles;
		this.tamanno_menuBar= tamanno_menuBar;
	
		setSize(pantalla_ancho_juego,pantalla_alto_juego+tamanno_menuBar+tamanno_controles);
		setTitle("Guerra de Tanques");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);    
		getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
      
		batalla=new PanelBatalla(pantalla_ancho_juego, pantalla_alto_juego,tamanno_controles, cantidadTanquesJ1, cantidadTanquesJ2,
				misilPerforanteInt,misil105Int,misil60Int,viento, gravedad);  
		control=new PanelControl(batalla,pantalla_ancho_juego,pantalla_alto_juego,tamanno_controles,tamanno_menuBar,usarIA);
		barra=new JPanel();
		barra.setPreferredSize(new Dimension(pantalla_ancho_juego, tamanno_menuBar));
		
		splitPane1 = new JSplitPane(VERTSPLIT, true,barra, batalla); //separación de los paneles barra de menú y Panel_Batalla
		
		splitPane1.setDividerSize(2);
		splitPane1.setDividerLocation(0.5);
		splitPane1.setEnabled(false);
		
		splitPane2 = new JSplitPane(VERTSPLIT, true, splitPane1, control); //separación de los paneles barra de menú-Panel_Batalla y Panel_Control
		splitPane2.setDividerSize(2);
		splitPane2.setDividerLocation(0.5);
		splitPane2.setEnabled(false);
		setResizable(false);
		        
        JButton reiniciar = new JButton();
        JButton menu = new JButton();
        JButton finalizar = new JButton();
        
        JLabel etiquetaReiniciar= new JLabel("Reiniciar");
        JLabel etiquetaMenu= new JLabel("Menú");
        JLabel etiquetaFinalizar= new JLabel("Finalizar");
        
        
        barra.setBackground(Color.WHITE);
        
        reiniciar.setBounds(0,0,30,30);
        etiquetaReiniciar.setBounds(35, 0, 100, 30);
        reiniciar.setBorder(null);
        reiniciar.setBackground(Color.WHITE);
        Image Foto = new ImageIcon (("boton-reiniciar.png")).getImage();
        reiniciar.setIcon(new ImageIcon(Foto.getScaledInstance(reiniciar.getWidth(), reiniciar.getHeight(),Image.SCALE_SMOOTH)));
        barra.add(reiniciar);
        barra.add(etiquetaReiniciar);
        
        menu.setBounds(this.pantalla_ancho_juego/2,0,30,30);
        etiquetaMenu.setBounds((this.pantalla_ancho_juego/2)-40, 0, 100, 30);
        menu.setBorder(null);
        menu.setBackground(Color.WHITE);
        Image Foto1 = new ImageIcon (("boton-menu.png")).getImage();
        menu.setIcon(new ImageIcon(Foto1.getScaledInstance(menu.getWidth(), menu.getHeight(),Image.SCALE_SMOOTH)));
        barra.add(menu);
        barra.add(etiquetaMenu);
        
        
        finalizar.setBounds(pantalla_ancho_juego-95,0,30,30);
        etiquetaFinalizar.setBounds(pantalla_ancho_juego-60,0, 100, 30);

        finalizar.setBorder(null);
        finalizar.setBackground(Color.WHITE);
        Image Foto2 = new ImageIcon (("boton-finalizar.png")).getImage();
        finalizar.setIcon(new ImageIcon(Foto2.getScaledInstance(finalizar.getWidth(), finalizar.getHeight(),Image.SCALE_SMOOTH)));
        barra.add(finalizar);
        barra.add(etiquetaFinalizar);
        barra.setLayout(null);
 
        ActionListener oyentedeaccion_reiniciar = new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                @SuppressWarnings("unused")
                VentanaJuego obj = new VentanaJuego(pantalla_ancho_juego,pantalla_alto_juego,
				tamanno_controles,tamanno_menuBar,cantidadTanquesJ1, cantidadTanquesJ2,misilPerforanteInt,misil105Int 
         		 ,misil60Int, viento, gravedad, usarIA);
                setVisible(false);
            }
        
        };
        reiniciar.addActionListener(oyentedeaccion_reiniciar);
               
        ActionListener oyentedeaccion_finalizar = new ActionListener(){     
            @Override
            public void actionPerformed(ActionEvent ae) {        
                System.exit(0);
            }
        
        };
        finalizar.addActionListener(oyentedeaccion_finalizar);
        
        ActionListener oyentedeaccion_menu = new ActionListener(){     
            @Override
            public void actionPerformed(ActionEvent ae) {        
                @SuppressWarnings("unused")
				VentanaPrincipal objeto = new VentanaPrincipal();
                setVisible(false);
            }
        
        };
        menu.addActionListener(oyentedeaccion_menu);

		add(splitPane2);   
		pack();		
	}
}    
