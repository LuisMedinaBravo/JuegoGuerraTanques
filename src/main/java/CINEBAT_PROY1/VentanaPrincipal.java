

package CINEBAT_PROY1                                                                                                                                                                                                                                                                                                                                                                                               ;


import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class VentanaPrincipal extends JFrame {
	private JPanel panel;
    private VentanaConfi juego;
    private JLabel fondo_menu;
    
    public VentanaPrincipal(){
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
        colocarBotones(); 
        colocarEtiqueta();
    }
    
    private void colocarLaminas(){
    	 panel=new JPanel();
    	 panel.setBackground(Color.WHITE);
         panel.setLayout(null);
         this.getContentPane().add(panel);
    }

    private void colocarEtiqueta(){     
         
        
        JLabel logo = new JLabel();
         logo.setBounds(0,430,450,150);
         logo.setVisible(true);
         logo.setBackground(Color.WHITE);
         Image Foto2 = new ImageIcon (("logo-juego.png")).getImage();
         logo.setIcon(new ImageIcon(Foto2.getScaledInstance(logo.getWidth(), logo.getHeight(),Image.SCALE_SMOOTH)));
         panel.add(logo);
         
        
         fondo_menu = new JLabel();
         fondo_menu.setBounds(0,0,800,600);
         fondo_menu.setVisible(true);
         fondo_menu.setBackground(Color.WHITE);
         Image Foto = new ImageIcon (("fondo-principal.jpg")).getImage();
         fondo_menu.setIcon(new ImageIcon(Foto.getScaledInstance(fondo_menu.getWidth(), fondo_menu.getHeight(),Image.SCALE_SMOOTH)));
         panel.add(fondo_menu);
         
     
     }
    
     private void colocarBotones(){
         
    	 JButton jugar = new JButton("Dos Jugadores");
         jugar.setBounds(100,100,200,80);
         jugar.setFont(new Font("Serif",Font.BOLD,25));
         jugar.setBackground(Color.GREEN);
         jugar.setForeground(new Color(255,255,255));
         jugar.setVisible(true);
         panel.add(jugar);
           
         JButton jugarIA = new JButton("Tu v/s IA");
         jugarIA.setBounds(100,200,200,80);
         jugarIA.setFont(new Font("Serif",Font.BOLD,25));
         jugarIA.setBackground(Color.GRAY);
         jugarIA.setForeground(new Color(255,255,255));
         jugarIA.setVisible(true);
         panel.add(jugarIA);
          
         JButton salir = new JButton("Salir");
         salir.setBounds(100,300,200,80);
         salir.setFont(new Font("Serif",Font.BOLD,25));
         salir.setBackground(Color.RED);
         salir.setForeground(new Color(255,255,255));
         salir.setVisible(true);
         panel.add(salir);
         
         ActionListener oyentedeaccion1 = new ActionListener(){
         	
             @Override
             public void actionPerformed(ActionEvent ae) {
                 juego = new VentanaConfi(0);
                 dispose();
            }
         };
         jugar.addActionListener(oyentedeaccion1);
    
         ActionListener oyentedeaccion2 = new ActionListener(){
        	
         @Override
         public void actionPerformed(ActionEvent ae) {
        	 
             juego = new VentanaConfi(1);
             dispose();
        }
     };
     jugarIA.addActionListener(oyentedeaccion2);
     
     ActionListener oyentedeaccion3 = new ActionListener(){   	
         @Override
         public void actionPerformed(ActionEvent ae) {
            dispose();
        }
     };
     salir.addActionListener(oyentedeaccion3);
     }
}
