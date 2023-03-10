
package CINEBAT_PROY1                                                                                                                                                                                                                                                                                                                                                                                               ;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

@SuppressWarnings("serial")
public class PanelBatalla extends JPanel implements MouseListener, MouseMotionListener {
	
    private int corregir_pos_x_tanque1;
    private int corregir_pos_y_tanque1;
    private int corregir_pos_x_tanque2;
    private int corregir_pos_y_tanque2;
    private int pantalla_ancho_juego, pantalla_alto_juego;
    private int pantalla_alto_juego_batalla;
    private int ancho_bloque_terreno,alto_bloque_terreno;
    
    private boolean hay_colision_terreno;
    
    private Set<int[]>terreno_actual;
    private int[] par_ordenado_colision;

    private Mapa territorio;
    private int dano_proyectil;
    private boolean juego_terminado;
    private int jugador_ganador=0;

    private ArrayList<Tanque> tanquesEnJuego;
    private Tanque tanqueSeleccionado;
	private int cantidadTanquesJ2;
	private int cantidadTanquesJ1;  
	private int jugadorTurno;
	private String viento;
	private String gravedad;
	
	private String direccionViento;
	private int valorViento;
	private int gravedadCoeficiente;
	private boolean estaSeleccionado;
	
    /**
     * Contructor de Panel_Batalla, corresponde a mostrar la animacion de la batalla de los tanques, muestra los tanques
     * posicionados, el terreno y la trayectoria del proyectil.
     * 
     * @param pantalla_ancho_juego valor de ancho para la pantalla del panel de animacion,
     * @param pantalla_alto_juego valor de alto para la pantalla del panel de animacion.
     * @param tamano_controles valor del tamano de los controles inferiores
     */
	public PanelBatalla(int pantalla_ancho_juego,int pantalla_alto_juego,
			int tamano_controles, int cantidadTanquesJ1, int cantidadTanquesJ2,
			int misilPerforanteCant, int misil105Cant ,int misil60Cant,String viento, String gravedad){
		setVisible(true);	
		
		this.pantalla_ancho_juego=pantalla_ancho_juego;
		this.pantalla_alto_juego=pantalla_alto_juego;
		this.viento=viento;
		this.gravedad=gravedad;
		pantalla_alto_juego_batalla=pantalla_alto_juego;

		this.cantidadTanquesJ1=cantidadTanquesJ1;
		this.cantidadTanquesJ2=cantidadTanquesJ2;
		territorio=new Mapa(pantalla_ancho_juego, pantalla_alto_juego, cantidadTanquesJ1, cantidadTanquesJ2);
		hay_colision_terreno=false;
	
		corregir_pos_x_tanque1=50;
		corregir_pos_y_tanque1=50;
		corregir_pos_x_tanque2=60;
		corregir_pos_y_tanque2=49;
		
		ancho_bloque_terreno=10;
		alto_bloque_terreno=10;
	
		terreno_actual=new HashSet<int[]>();
		par_ordenado_colision=new int[2];
		juego_terminado=false;
		tanquesEnJuego = new ArrayList<Tanque>();
		dano_proyectil=0;
		territorio.generar_mapa();
		jugadorTurno=1;
		estaSeleccionado=false;
		crearTanques(cantidadTanquesJ1,cantidadTanquesJ2,misilPerforanteCant, misil105Cant ,misil60Cant);
		variarViento();
		variarGravedad();
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	/**
	 * Redimensiona la pantalla para el panel, usando los parametros del constructor
	 */
	public Dimension getPreferredSize() {
        return new Dimension(pantalla_ancho_juego, pantalla_alto_juego_batalla);
    }
	
	/**
	 * Metodo para dibujar.
	 * Contiene lo necesario para dibujar el terreno obtenido de la Clase Mapa y Tanque.
	 * Utiliza los metodos existe_colision_terreno, colision_bloque_afectado, valor_dano_proyectil en caso de que 
	 * exista una colisi??n en el terreno para actualizar el mapa.
	 * Para dibujar el terreno se recorre un conjunto con los puntos (x,y) del terreno.
	 * Se dibujan los tanques, se muestran los estados de los tanques y los proyectiles.
	 * Se muestra (dibuja) un mensaje cuando un jugador gana. 
	 *
	 */
	public void paint(Graphics g) {
		super.paintComponent(g);
		existe_colision_terreno(hay_colision_terreno);
		colision_bloque_afectado(par_ordenado_colision);
		valor_dano_proyectil(dano_proyectil);
		territorio.crear_terreno(hay_colision_terreno,par_ordenado_colision,alto_bloque_terreno,dano_proyectil,tanquesEnJuego,jugadorTurno);
		terreno_actual=territorio.getTerreno();
		tanquesEnJuego=territorio.getTanques();
		
		
		g.setColor(new Color(9, 132, 184));//color de fondo
		g.fillRect(0, 0, pantalla_ancho_juego, pantalla_alto_juego_batalla);		
		for (int[] par_ordenado: terreno_actual) {
			g.setColor(Color.BLACK); //color para el terreno, montes y valles
			g.fillRect(par_ordenado[0],par_ordenado[1], 15,15);
		}
	
		for (Tanque tanques : tanquesEnJuego) {
			g.drawImage(tanques.getImagen_tanque(),tanques.getPosTanqueCorrectaX(), tanques.getPosTanqueCorrectaY(),76,78,null);			
		}
		
		/*for (Tanque pos: tanquesEnJuego) {
			g.setColor(Color.RED); //color para el terreno, montes y valles
			g.fillRect(pos.getPos_x_tanque()-50,pos.getPos_y_tanque()-28,53,30);
		}*/
		
		variarGravedadInfo(g, gravedadCoeficiente);
		variarVientoInfo(g,direccionViento,valorViento);
		
	
	   	if(juego_terminado) {
	   		String ganador="";
	   		FontMetrics fm2=g.getFontMetrics();
			g.setFont(new Font("Serif",Font.BOLD,30));
			String game_over= "GAME OVER";
			int ancho1_2=fm2.stringWidth(game_over);
		 	g.setColor(Color.WHITE);
			g.drawString(game_over,((pantalla_ancho_juego-ancho1_2)/2)-30,60);
			
			if(jugador_ganador==-1) {
				g.setFont(new Font("Serif",Font.BOLD,30));
			 	g.setColor(Color.WHITE);
				ganador= "EMPATE";
				int ancho2_2=fm2.stringWidth(ganador);
				g.drawString(ganador,((pantalla_ancho_juego-ancho2_2)/2)-20,120);
			}
			else {
				ganador= "Ganador: JUGADOR "+ jugador_ganador;
				g.setFont(new Font("Serif",Font.BOLD,30));
			 	g.setColor(Color.WHITE);
				int ancho2_2=fm2.stringWidth(ganador);
				g.drawString(ganador,((pantalla_ancho_juego-ancho2_2)/2)-45,120);
				
			}
	   	}  	
	}
	
	/**
	 * Metodo para crear la clase Tanque para los respectivos jugadores.
	 * Se utilizan los puntos calculados por la clase Mapa, se hace un reajuste en ellos para mostrar correctamente los tanques.
	 * Se cargan las imagenes de los tanques.
	 * Se crean los rectangulos delimitadores para los tanques, ??tiles para verificar una colisi??n en ellos.
	 */
	public void crearTanques(int cantidadTanquesJ1, int cantidadTanquesJ2,int misilPerforanteCant, int misil105Cant ,int misil60Cant) {
		Queue <int[]>posicionesParaTanques=new LinkedList<>(territorio.getListaPosicionesTanques());
	    int contTanquesCreadosJ1=0;
	    int contTanquesCreadosJ2=0;
	    
		Image tanqueJ1Imagen = new ImageIcon (("primer_tanque.png")).getImage();
		Image tanqueJ2Imagen = new ImageIcon (("segundo_tanque.png")).getImage();
		
		while (contTanquesCreadosJ1<cantidadTanquesJ1) {
			contTanquesCreadosJ1++;
			Tanque tanqueJugador1= new Tanque(1,contTanquesCreadosJ1,posicionesParaTanques.peek()[0],
											posicionesParaTanques.peek()[1],
											tanqueJ1Imagen,100,
											corregir_pos_x_tanque1,corregir_pos_y_tanque1,
											misilPerforanteCant, misil105Cant,misil60Cant);
			posicionesParaTanques.remove();
			tanquesEnJuego.add(tanqueJugador1);			
		}
		
		while (contTanquesCreadosJ2<cantidadTanquesJ2) {
			contTanquesCreadosJ2++;
			Tanque tanqueJugador2= new Tanque(2,contTanquesCreadosJ2,posicionesParaTanques.peek()[0],
											posicionesParaTanques.peek()[1],
											tanqueJ2Imagen,100,
											corregir_pos_x_tanque2,corregir_pos_y_tanque2,
											misilPerforanteCant, misil105Cant,misil60Cant);
			posicionesParaTanques.remove();
			tanquesEnJuego.add(tanqueJugador2);			
		}    
	}
	
		
	public void tanqueSeleccionado(Rectangle puntero) {
		for (Tanque tanque: tanquesEnJuego ) {
			if (puntero.intersects(tanque.getRadioUbicacionTanque()) && 
				tanque.getTanquePerteneceJugador()==jugadorTurno) {
				tanqueSeleccionado=tanque;
				estaSeleccionado=true;
				avisarSeleccion();
				mostrarInfoTanques(this.getGraphics(),tanque);
			}
		}
	}
	public void avisarSeleccion() {
		Graphics g=this.getGraphics();
		String seleccion= "Tanque Seleccionado";
		FontMetrics fm=g.getFontMetrics();
		g.setFont(new Font("Serif",Font.BOLD,20));
		g.setColor(Color.WHITE);
	   	int ancho1=fm.stringWidth(seleccion);
		g.drawString(seleccion,((pantalla_ancho_juego-ancho1)/2)-30,pantalla_alto_juego_batalla-140);
	}
	
	
	public void avisarTurno() {
		Graphics g=this.getGraphics();
		String turno="";
		String colorTanques="";
		if(jugadorTurno==1) {
			turno="Turno JUGADOR 1";
			colorTanques="Tanques Beiges";
		}
		if(jugadorTurno==2) {
			turno="Turno JUGADOR 2";
			colorTanques="Tanques Verdes";
		}
		
		FontMetrics fm=g.getFontMetrics();
		g.setFont(new Font("Serif",Font.BOLD,20));
		g.setColor(Color.WHITE);
	   	int ancho1=fm.stringWidth(turno);
	   	int ancho2=fm.stringWidth(turno);
		g.drawString(turno,((pantalla_ancho_juego-ancho1)/2)-20,20);
		g.drawString(colorTanques,((pantalla_ancho_juego-ancho2)/2)-2,40);
		
	}
	
	public void verTanques(Rectangle puntero) {
		for (Tanque tanque: tanquesEnJuego ) {
			if (puntero.intersects(tanque.getRadioUbicacionTanque())&& 
				tanque.getTanquePerteneceJugador()!=jugadorTurno) {
				try {
					Thread.sleep(2000);
					mostrarInfoTanques(this.getGraphics(),tanque);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.repaint();
			}
		}
		
	}
	
	public void mostrarInfoTanques(Graphics g,Tanque tanque) {
			
        g.setFont(new Font("Serif",Font.BOLD,20));
	   	g.setColor(Color.WHITE);
	
	    FontMetrics fm=g.getFontMetrics();
	    
	    String jugador="Jugador: "+tanque.getTanquePerteneceJugador();
	    String tanqueNumero="Tanque: n??"+tanque.getNumeroDeTanque();
	   	String vida="VIDA: "+tanque.getVida_tanque()+"%";
	   	String proyectil1=tanque.getLista_proyectiles().get(1).getTipo_proyectil()+ ": "+
				tanque.getLista_proyectiles().get(1).getUnidades_disponibles();
	   	String proyectil2=tanque.getLista_proyectiles().get(0).getTipo_proyectil()+ ": "+
				tanque.getLista_proyectiles().get(0).getUnidades_disponibles();
	   	String proyectil3=tanque.getLista_proyectiles().get(2).getTipo_proyectil()+ ": "+
				tanque.getLista_proyectiles().get(2).getUnidades_disponibles();
	   	
	    int ancho1=fm.stringWidth(vida);
	    int ancho2=fm.stringWidth(proyectil1);
	    int ancho3=fm.stringWidth(proyectil2);
	    int ancho4=fm.stringWidth(proyectil3);
	    int ancho5=fm.stringWidth(jugador);
	    int ancho6=fm.stringWidth(tanqueNumero);
	    
	    g.drawString(jugador,((pantalla_ancho_juego-ancho5)/2)-5,pantalla_alto_juego_batalla-110);
	    g.drawString(tanqueNumero,(pantalla_ancho_juego-ancho6)/2-5,pantalla_alto_juego_batalla-90);
	    g.drawString(vida,((pantalla_ancho_juego-ancho1)/2)-5,pantalla_alto_juego_batalla-70);
	   	g.drawString(proyectil1,((pantalla_ancho_juego-ancho2)/2)-5,pantalla_alto_juego_batalla-10);
	   	g.drawString(proyectil2,((pantalla_ancho_juego-ancho3)/2)-5,pantalla_alto_juego_batalla-30);
	   	g.drawString(proyectil3,((pantalla_ancho_juego-ancho4)/2)-5,pantalla_alto_juego_batalla-50);
	}
	
	public void variarViento() {
		
		direccionViento="";
		valorViento=0;
		
		if(viento.equals("Si")) {
			Random random = new Random();
			int randomIndice= 0;
			randomIndice=random.nextInt(2);
			String[] direcciones= {"izquierda", "derecha"};
			valorViento=new Random().nextInt(5)+1;
			direccionViento=direcciones[randomIndice];
			
		}
	
		if(viento.equals("No")) {
			valorViento=0;
			direccionViento="";
		}
	}
	public void variarVientoInfo(Graphics g,String direccionViento,int valorViento) {
			
			String vientoString="";
					
			if(direccionViento.equals("izquierda")) {
				vientoString="Viento: "+valorViento +"m/s"+" /"+"izq";
			}
			if(direccionViento.equals("derecha")) {
				vientoString="Viento: "+valorViento +"m/s"+" /"+"der";
			}
				
			g.setFont(new Font("Serif",Font.BOLD,20));
			g.setColor(Color.WHITE);
			FontMetrics fm=g.getFontMetrics();
			int ancho=fm.stringWidth(vientoString);
		    g.drawString(vientoString,(pantalla_ancho_juego-ancho)-5,20);	
		}

	public void variarGravedad() {
		gravedadCoeficiente=0;	
		
		if(gravedad.equals("Si")) {
			gravedadCoeficiente=new Random().nextInt(11)+5;
		}
		
		if(gravedad.equals("No")) {
			gravedadCoeficiente=10;
		}
		
	}
	public void variarGravedadInfo(Graphics g, int gravedadCoeficiente) {
			String gravedadString="Coef. Gravedad: "+gravedadCoeficiente;
		   	g.setFont(new Font("Serif",Font.BOLD,20));
		   	g.setColor(Color.WHITE);
	    	g.drawString(gravedadString,5,20);
	}
	
	/**
	 * Metodo para retornar el mapa o territorio desde la clase Mapa
	 * @return territorio, instancia de tipo Mapa
	 */
	public Mapa getTerritorio() {
		return territorio;
	}
	
	public int getPantalla_alto_juego() {
		return pantalla_alto_juego;
	}
	
	public int getPantalla_ancho_juego() {
		return pantalla_ancho_juego;
	}
	
	public int getAncho_bloque_terreno() {
		return ancho_bloque_terreno;
	}
	public int getAlto_bloque_terreno() {
		return alto_bloque_terreno;
	}
	
	/**
	 * Metodo para retornar el terreno 
	 * @return terreno_actual, conjunto con puntos (x,y) guardados en un arreglo int[] de tama??o 2
	 */
	public Set<int[]> getTerreno_actual() {
		return terreno_actual;
	}
	
	/**
	 * Metodo set para cambiar el valor de la variable booleana cuando existe una colisi??n en el terreno
	 * @param colision_terreno, booleano para cambiar el valor de hay_colision_terreno, puede ser true o false.
	 */
	public void existe_colision_terreno(boolean colision_terreno) {
		hay_colision_terreno=colision_terreno;
	}
	
	/**
	 * Metodo set para ingresar la posici??n de la colisi??n 
	 * @param bloque_afectado, arreglo que contiene un los valores x, y del punto de la colisi??n 
	 */
	public void colision_bloque_afectado(int[] bloque_afectado) {
		par_ordenado_colision=bloque_afectado;
	}

	/**
	 * Metodo para ingresar el da??o provocado por el proyectil (la potencia que tiene para da??ar)
	 * @param dano_proyectil, int que contiene el valor del da??o que produce el proyectil
	 */
	public void valor_dano_proyectil(int dano_proyectil) {
		this.dano_proyectil = dano_proyectil;
	}
	
	/**
	 * Metodo set para el juego terminado, se cambia dependiendo del valor de la variable booleana
	 * @param juego_terminado, booleano que puede ser true cuando el juego termine, es decir, cuando un tanque tenga vida 0.
	 */
	public void setJuego_terminado(boolean juego_terminado) {
		this.juego_terminado = juego_terminado;
	}
	
	/**
	 * Metodo set para ingresar el jugador que gan??. Se muestra en el mensaje cuando el juego termina.
	 * @param jugador_ganador, int que puede tener valor 1 (jugador 1) o 2 (jugador 2)
	 */
	public void setJugador_ganador(int jugador_ganador) {
		this.jugador_ganador = jugador_ganador;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Rectangle puntero =new Rectangle (e.getX(), e.getY(),1,1);
		tanqueSeleccionado(puntero);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.repaint();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	public Tanque getTanqueSeleccionado() {
		return tanqueSeleccionado;
	}

	public int getJugadorTurno() {
		return jugadorTurno;
	}

	public void setJugadorTurno(int jugadorTurno) {
		this.jugadorTurno = jugadorTurno;
		variarViento();
		variarGravedad();
		estaSeleccionado=false;
		
	}

	public int getCantidadTanquesJ2() {
		return cantidadTanquesJ2;
	}

	public void setCantidadTanquesJ2(int cantidadTanquesJ2) {
		this.cantidadTanquesJ2 = cantidadTanquesJ2;
	}

	public int getCantidadTanquesJ1() {
		return cantidadTanquesJ1;
	}

	public void setCantidadTanquesJ1(int cantidadTanquesJ1) {
		this.cantidadTanquesJ1 = cantidadTanquesJ1;
	}
	
	public ArrayList<Tanque> getTanquesEnJuego(){
		return tanquesEnJuego;
	}
	public void eliminarTanquesEnJuego(Tanque tanqueEliminado){
		ArrayList<Tanque> TanquesCopia= new ArrayList<Tanque>(tanquesEnJuego);
		TanquesCopia.remove(tanqueEliminado);
		tanquesEnJuego.clear();
		tanquesEnJuego.addAll(TanquesCopia);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!estaSeleccionado) {
			avisarTurno();
		}
		Rectangle puntero =new Rectangle (e.getX(), e.getY(),1,1);
		verTanques(puntero);
	}
	
	public int getGravedadCoeficiente() {
		return gravedadCoeficiente;
	}
	public int getValorViento() {
		return valorViento;
	}
	public String getDireccionViento() {
		return direccionViento;
	}

	public boolean getEstaSeleccionado() {
		return estaSeleccionado;
	}

}
