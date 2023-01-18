
package CINEBAT_PROY1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class VentanaConfi extends JFrame {
	private JPanel panel;
    private int pantalla_ancho_juego, pantalla_alto_juego,tamanno_controles;
    @SuppressWarnings("rawtypes")
   	private JComboBox listaDesplegableTanquesJ1;
    @SuppressWarnings("rawtypes")
   	private JComboBox listaDesplegableTanquesJ2;
    private JComboBox listaDesplegablePerforante;
    private JComboBox listaDesplegable60;
    private JComboBox listaDesplegable105;
    private JComboBox listaDesplegableGravedad;
    private JComboBox listaDesplegableViento;
    private JLabel etiquetaListaTanqueJ1;
    private JLabel etiquetaListaTanqueJ2;
    private JTextField ancho;
    private JTextField alto;
    private JLabel fondo_menu;
    private JTextField viento;
    private JTextField gravedad;
    private JLabel etiquetaPerforante;
    private JLabel etiqueta60;
    private JLabel etiqueta105;
    private JLabel etiquetaResolucion;
    private JLabel etiquetaTanques;
    private JLabel etiquetaTanquesJ2;
    private JLabel etiquetaClima;
    private JLabel etiquetaGravedad;
    private int tamanno_menuBar;
    VentanaJuego juego;
    private int usarIA;
    public VentanaConfi(int usarIA){  
    	pantalla_ancho_juego=0; //min=600 ,max=1024
    	pantalla_alto_juego=0; //min=400, max=600
    	tamanno_controles=40;
    	tamanno_menuBar=30;
    	this.usarIA=usarIA;
    	setSize(800,600);
        setVisible(true);
        setTitle("Guerra de Tanques");
        setResizable(false);
        setLocationRelativeTo(null);
        getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        iniciarComponentes();
        
    }
    private void iniciarComponentes(){
        colocarLaminas();
        colocarContenido(); 
        colocarEtiqueta();
    }
    private void colocarLaminas(){
    	panel=new JPanel();
   	 	panel.setBackground(Color.WHITE);
   	 	panel.setLayout(null);
        this.getContentPane().add(panel);
    }
    
    private void colocarEtiqueta(){     
        fondo_menu = new JLabel();
        fondo_menu.setBounds(0,0,800,600);
        fondo_menu.setVisible(true);
        fondo_menu.setBackground(Color.WHITE);
        Image Foto = new ImageIcon (("fondo-principal.jpg")).getImage();
        fondo_menu.setIcon(new ImageIcon(Foto.getScaledInstance(fondo_menu.getWidth(), fondo_menu.getHeight(),Image.SCALE_SMOOTH)));
        panel.add(fondo_menu);     
    }
    
    private void colocarContenido(){
    	 etiquetaTanques = new JLabel("Numero de Tanques JUGADOR 1:");
         etiquetaTanques.setFont(new Font("Serif",Font.BOLD,14));
         etiquetaTanques.setBounds(50, 30, 300, 20);
         etiquetaTanques.setForeground(Color.WHITE);
         
         if(usarIA==0) {
        	 etiquetaTanquesJ2 = new JLabel("Numero de Tanques JUGADOR 2:"); 
         }
         if(usarIA==1) {
        	 etiquetaTanquesJ2 = new JLabel("Numero de Tanques CONTRA IA:");
         }
         
         etiquetaTanquesJ2.setFont(new Font("Serif",Font.BOLD,14));
         etiquetaTanquesJ2.setBounds(405, 30, 300, 20);
         etiquetaTanquesJ2.setForeground(Color.WHITE);
         
         String [] numeroTanques = {"1","2","3","4"};
         listaDesplegableTanquesJ1 = new JComboBox(numeroTanques);
         listaDesplegableTanquesJ1.setBounds(265,30,100,20);
		 panel.add(listaDesplegableTanquesJ1);
		 listaDesplegableTanquesJ1.setEnabled(true);
		 listaDesplegableTanquesJ1.setVisible(true);
		
		 listaDesplegableTanquesJ2 = new JComboBox(numeroTanques);
		 listaDesplegableTanquesJ2.setBounds(620,30,100,20);
    	 panel.add(listaDesplegableTanquesJ2);
    	 listaDesplegableTanquesJ2.setEnabled(true);
    	 listaDesplegableTanquesJ2.setVisible(true);
    	 panel.add(etiquetaTanques);
    	 panel.add(etiquetaTanquesJ2);
    	 
         etiquetaResolucion = new JLabel("Resolucion de Pantalla (ancho x alto):");
         etiquetaResolucion.setFont(new Font("Serif",Font.BOLD,14));
         etiquetaResolucion.setBounds(50, 95, 300, 20);
         etiquetaResolucion.setForeground(Color.WHITE);
         panel.add(etiquetaResolucion);
      
         ancho = new JTextField();
         ancho.setBounds(290,95,60,20);
         panel.add(ancho);
         panel.add(etiquetaResolucion);
         
         alto = new JTextField();
         alto.setBounds(355,95,60,20);
         panel.add(alto);
         
		 /*
          * TIPOS DE PROYECTILES
          */
         etiqueta60 = new JLabel("Cantidad de Proyectiles de 60 mm:");
         etiqueta60.setFont(new Font("Serif",Font.BOLD,14));
         etiqueta60.setBounds(50, 150, 300, 20);
         etiqueta60.setForeground(Color.WHITE);
         panel.add(etiqueta60);
         String [] numero60 = {"10","15","20","25","30"};
         listaDesplegable60 = new JComboBox(numero60);
         listaDesplegable60.setBounds(270,150,100,20);
  		 panel.add(listaDesplegable60);
  		 listaDesplegable60.setEnabled(true);
  		 listaDesplegable60.setVisible(true);
  		 
  		 etiqueta105= new JLabel("Cantidad de Proyectiles de 105 mm:");
         etiqueta105.setFont(new Font("Serif",Font.BOLD,14));
         etiqueta105.setBounds(50, 205, 300, 20);
         etiqueta105.setForeground(Color.WHITE);
         panel.add(etiqueta105);
         String [] numero105 = {"10","15","20","25","30"};
         listaDesplegable105 = new JComboBox(numero105);
         listaDesplegable105.setBounds(274,205,100,20);
 		 panel.add(listaDesplegable105);
 	 	 listaDesplegable105.setEnabled(true);
 		 listaDesplegable105.setVisible(true);
 		
 		 etiquetaPerforante= new JLabel("Cantidad de Proyectiles Perforantes:");
         etiquetaPerforante.setFont(new Font("Serif",Font.BOLD,14));
         etiquetaPerforante.setBounds(50, 260, 300, 20);
         etiquetaPerforante.setForeground(Color.WHITE);
         panel.add(etiquetaPerforante);
         String [] numeroPerforante = {"10","25","50","75","100"};
         listaDesplegablePerforante = new JComboBox(numeroPerforante);
         listaDesplegablePerforante.setBounds(279,260,100,20);
		 panel.add(listaDesplegablePerforante);
	 	 listaDesplegablePerforante.setEnabled(true);
		 listaDesplegablePerforante.setVisible(true);
 		
		 /*
		  * Modos especiales
		  */
         etiquetaClima = new JLabel("Modo Clima(El viento cambiará cada turno):");
         etiquetaClima.setFont(new Font("Serif",Font.BOLD,14));
         etiquetaClima.setBounds(50, 315, 300, 20);
         etiquetaClima.setForeground(Color.WHITE);
         panel.add(etiquetaClima);
         String [] opcionesViento = {"No","Si"};
         listaDesplegableViento = new JComboBox(opcionesViento);
         listaDesplegableViento.setBounds(325,315,100,20);
         listaDesplegableViento.setEnabled(true);
		 listaDesplegableViento.setVisible(true);
         panel.add(listaDesplegableViento);
       
         etiquetaGravedad = new JLabel("Modo Gravedad(Cambio en la gravedad):");
         etiquetaGravedad.setFont(new Font("Serif",Font.BOLD,14));
         etiquetaGravedad.setBounds(50, 370, 300, 20);
         etiquetaGravedad.setForeground(Color.WHITE);
         panel.add(etiquetaGravedad);
         String [] opcionesGravedad = {"No","Si"};
         listaDesplegableGravedad = new JComboBox(opcionesGravedad);
         listaDesplegableGravedad.setBounds(305,370,100,20);
         listaDesplegableGravedad.setEnabled(true);
		 listaDesplegableGravedad.setVisible(true);
         panel.add(listaDesplegableGravedad);
                 
         JButton inicio = new JButton("Iniciar Batalla");
         inicio.setBounds(50,450,200,40);
         inicio.setFont(new Font("Serif",Font.BOLD,25));
         inicio.setBackground(Color.GREEN);
         inicio.setForeground(new Color(255,255,255));
         inicio.setVisible(true);
         panel.add(inicio);
         
    
    ActionListener oyentedeaccion1 = new ActionListener(){
    /*
     * ***ACTUALIZAR LISTENERS***
     */
        @Override
        public void actionPerformed(ActionEvent ae) {
       	 
            try{
            
       	 	String cantidadTanquesJ1String = listaDesplegableTanquesJ1.getSelectedItem().toString();
            String cantidadTanquesJ2String = listaDesplegableTanquesJ2.getSelectedItem().toString();
            String anchoString=ancho.getText();
            String altoString=alto.getText();
            
            if(altoString.equals(700)){
                
                altoString=anchoString;
            }
            
            
            String vientoString=listaDesplegableViento.getSelectedItem().toString();
            String gravedadString=listaDesplegableGravedad.getSelectedItem().toString();
            String misilPerforanteString=listaDesplegablePerforante.getSelectedItem().toString();
            String misil105String=listaDesplegable105.getSelectedItem().toString();
            String misil60String=listaDesplegable60.getSelectedItem().toString();
            pantalla_ancho_juego=Integer.parseInt(anchoString); 
            pantalla_alto_juego=Integer.parseInt(altoString);
            int misilPerforanteInt= Integer.parseInt(misilPerforanteString);
            int misil105Int=Integer.parseInt(misil105String);
            int misil60Int=Integer.parseInt(misil60String);
            int cantidadTanquesJ1Int = Integer.parseInt(cantidadTanquesJ1String);
            int cantidadTanquesJ2Int = Integer.parseInt(cantidadTanquesJ2String);
     
           juego = new VentanaJuego(pantalla_ancho_juego,pantalla_alto_juego,tamanno_controles,tamanno_menuBar, 
           		 cantidadTanquesJ1Int, cantidadTanquesJ2Int,misilPerforanteInt,misil105Int 
           		 ,misil60Int, vientoString, gravedadString,usarIA);
            dispose();
            
            }catch(Exception e){
                
            }
       }
    };
    inicio.addActionListener(oyentedeaccion1);
   
    
    }
   
}

