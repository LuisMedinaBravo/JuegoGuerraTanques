package CINEBAT_PROY1;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class PanelControl extends JPanel implements Runnable{
	private JTextField angulo;
    private JTextField velocidad;
    private JLabel ang; 
    private JLabel vel;
    private JLabel lista;
    private JLabel direccion;
    @SuppressWarnings("rawtypes")
   	private JComboBox ListaDesplegable1;
    @SuppressWarnings("rawtypes")
   	private JComboBox ListaDesplegable2;
     
    private JButton aceptar1;
    
    private String ang_1,vel_1;
   
    private Colisiones colisiones;
    private PanelBatalla batalla; //se utiliza para comunicarse con el Panel_Batalla
    
    private int jugador;
    private int ventana_ancho,pantalla_alto_juego, tamano_controles;
    int ajuste_x, ajuste_y;
    
    private boolean colisionTerreno, colisionTanques, tocaBorde;//boolean para verificar las colisiones con los elementos
    private boolean no_jugada;
    private boolean no_proyectiles;
    private Formulas formulasJugador;

    private Proyectil proyectilDisparar;
    private Tanque tanqueEnJuego;
    private Tanque tanqueAtacado;
    public int decidioIA;
    private versusIA jugadorIA;
  
    int ventana_alto;
    
    /**
     * Constructor de Panel_Control, esta clase implementa el funcionamiento del juego, como son los controles, el lanzamiento de 
     * proyectiles, mostrar la información acerca del juego, determinar el estado de los tanques etc.
     * 
     * @param batalla variable proveniente de la clase Panel_Batalla, útil para realizar los cambios en el panel y el mapa
     * @param ventana_ancho valor de anchura de la pantalla
     * @param ventana_alto valor de la altura de la pantalla
     * @param tamano_controles valor de la altura de la sección para los controles
     */
	public PanelControl(PanelBatalla batalla,int ventana_ancho,int ventana_alto,int tamano_controles, int tamanno_menuBar, 
			 int decidioIA){
		this.batalla=batalla;
		this.ventana_ancho=ventana_ancho;
		this.tamano_controles=tamano_controles;
		this.pantalla_alto_juego=ventana_alto; //se calcula el valor real que se usará para el juego

		this.decidioIA=decidioIA;
		this.ventana_alto=ventana_alto;
		
		jugador=0; //se utiliza para iniciar y reiniciar a los jugadores (no existe el jugador 0)
		colisionTerreno=false;
		colisionTanques=false;
		tocaBorde=false;
		no_jugada=false;
		no_proyectiles=false;
		tanqueEnJuego=null;
		tanqueAtacado=null;
		colisiones=new Colisiones(batalla, ventana_ancho);
		
		iniciarComponentes(); //se inician los componentes para los controles
		crearIA(decidioIA);
		
	}
	
	public void crearIA(int decidioIA) {
		if (decidioIA==1) {
			jugadorIA= new versusIA(2, batalla.getTanquesEnJuego());
		}
	}
	
	
	public int variarGravedadeEfecto(int gravedad) {
		int valorEfecto=0;
		if(gravedad==10) {
			valorEfecto=1;
		}
		if(gravedad>=5 && gravedad<=9) {
			valorEfecto=0;
		}
		if(gravedad>=11 && gravedad<=13) {
			valorEfecto=2;
		}
		if(gravedad>=14) {
			valorEfecto=3;
		}
		return valorEfecto;
	}

	/**
	 *Redimensiona la pantalla para el panel, usando los parametros del constructor.
	 */
	public Dimension getPreferredSize() {
		return new Dimension(ventana_ancho, tamano_controles);
	}
	
	/**
	 *Inicia los componentes para los controles, que son: el fondo, las etiquetas para los elementos,
	 *las cajas de texto, barras, y botones, además de los listener para recibir las acciones de los 
	 *elementos de control.
	 */
	private void iniciarComponentes(){
        colocarLaminas();
        colocarEtiquetas();
        colocarCajadeTexto();
        colocarBotones(); 
    }
	
	/**
	 *Pone el fondo en la sección de los controles.
	 */
	private void colocarLaminas(){
        //creacion de la lamina para el Panel_Control
        setBackground(Color.WHITE);
        setLayout(null);
        setVisible(true);
    }
	
	/**
	 *Agrega las etiquetas a los elementos de control.
	 */
	private void colocarEtiquetas(){ 
		       
        ang = new JLabel("Angulo (°)");
        ang.setBounds((ventana_ancho/2)-140,5,180,30);
        add(ang);
        
        vel = new JLabel("Velocidad (m/s)");
        vel.setBounds((ventana_ancho/2)-20,5,180,30);
        add(vel);
        
        lista = new JLabel("Misil");
        lista.setBounds((ventana_ancho/2)+210,2,180,20);
        add(lista);
        
        direccion = new JLabel("Direccion");
        direccion.setBounds((ventana_ancho/2)+210,22,180,20);
        add(direccion);
        
    }
   
	/**
	 *Agrega la caja de texto para ingresar los números para la velocidad y el angulo para el 
	 *lanzamiento de los proyectiles.
	 */
    private void colocarCajadeTexto(){ 
        angulo = new JTextField();
        angulo.setBounds((ventana_ancho/2)-180,5,30,30);
        add(angulo);
        
        velocidad = new JTextField();
        velocidad.setBounds((ventana_ancho/2)-60,5,30,30);
        add(velocidad);
          
    }
    
    /**
     *Método para agregar los botones para accionar los proyectiles y las listas desplegables para
     *seleccionarlos. Se implementan los listenerS para recibir las acciones de los 
     *elementos del control (botones y listas). Las acciones son recibidas como strings, luego se llama a otro 
     *método para iniciar la verificación, crear las fórmulas y dibujar (accionar) los proyectiles. 
     *
     *
     *También se llama un método para hacer los cambios de turno entre los jugadores. Por defecto comienza el 
     *jugador 1
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void colocarBotones(){ 
        
        aceptar1 = new JButton("Disparar J1");
        aceptar1.setBounds(0,0,115,40);
        aceptar1.setBackground(Color.YELLOW);
       
      
        add(aceptar1);
        
        String [] misiles = {"perforante","105mm","60mm"};
		ListaDesplegable1 = new JComboBox(misiles);
		ListaDesplegable1.setBounds((ventana_ancho/2)+100,1,100,20);
		add(ListaDesplegable1);
		ListaDesplegable1.setEnabled(true);
		
		String [] direccion = {"izquierda","derecha"};
		ListaDesplegable2 = new JComboBox(direccion);
		ListaDesplegable2.setBounds((ventana_ancho/2)+100,21,100,20);
		add(ListaDesplegable2);
		ListaDesplegable2.setEnabled(true);
		
        if(decidioIA==0){
        	ActionListener oyentedeaccion1 = new ActionListener(){
	        	public void actionPerformed(ActionEvent ae) {
                            
                            try{
                            
	        		jugador=batalla.getJugadorTurno();
	        		ang_1=angulo.getText(); 
	        		vel_1=velocidad.getText();
	        		tanqueEnJuego=batalla.getTanqueSeleccionado();
	        		String proyectilSeleccionado = ListaDesplegable1.getSelectedItem().toString();
	        		String direccionSeleccionado = ListaDesplegable2.getSelectedItem().toString();
	        		if(batalla.getEstaSeleccionado()) {
	        			verificar_formular_y_combatir(vel_1, ang_1, proyectilSeleccionado,direccionSeleccionado);
		        		turno_jugadores();
	        		}
                                
                            }catch(Exception e){
                                
                            }
	        		
	            }	
	        };
	        aceptar1.addActionListener(oyentedeaccion1);
	        
        	
        }
        
        else if(decidioIA==1){ 
        	if (batalla.getJugadorTurno()==1) {
        		ActionListener oyentedeaccion = new ActionListener(){
                	public void actionPerformed(ActionEvent ae) {
                            
                           try{
                            
                		jugador=batalla.getJugadorTurno();
                		ang_1=angulo.getText(); 
                		vel_1=velocidad.getText();
                		tanqueEnJuego=batalla.getTanqueSeleccionado();
                		String proyectilSeleccionado = ListaDesplegable1.getSelectedItem().toString();
                		String direccionSeleccionado = ListaDesplegable2.getSelectedItem().toString();
                		verificar_formular_y_combatir(vel_1, ang_1, proyectilSeleccionado,direccionSeleccionado);
                		jugador=0;
                		activarJugadorIA();
                		batalla.setJugadorTurno(1);
                                
                           }catch(Exception e){
                               
                           }
                	}
                	
        		};
        		aceptar1.addActionListener(oyentedeaccion);
        	}
        }
    }
    
    public void activarJugadorIA() {
        	jugador=2;
        	
                try{
                    
                    Thread.sleep(4*1000);
                    
                }catch(Exception e){
                    
                }
                
                
                
                tanqueEnJuego=jugadorIA.escogerTanque();
        	verificar_formular_y_combatir(jugadorIA.calcularVelocidad(), 
        	jugadorIA.calcularAngulo(), jugadorIA.retornarProyectil(),jugadorIA.retornarDireccion());
                angulo.setText(null);
                velocidad.setText(null);
    }
    
    /**
     * Método que verifica que el parámetro ingresado es número o carácter, se realiza con un matcheo 
     * con una expresión regular, el cual solo permite combinación de números del 0 al 9.
     * 
     * @param numero, String proveniente de la caja de texto del listener en colocarBotones() 
     * @return esNumeroValido, corresponde a un boolean, que cuando se comprueba que es un número aceptado por 
     * la expresión regular, retorna true, en caso contrario, false.
     */
    
    
    //El método esNumeroValidar es para verificar que se están ingresando datos correctos
    
    
    private static boolean esNumeroValidar(String numero){
    	boolean esNumeroValido;
        if(numero.matches("[0-9]*")){
        	esNumeroValido=true;
        }
        else {
        	esNumeroValido=false;
        }
        return esNumeroValido;
    }
    
    /**
     * paint para dibujar en el panel 
     */
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.drawLine(0,0,ventana_ancho,0);   
    }
    
    /**
     *Método run() para utilizar hilos, en el momento de mostrar el lanzamiento del proyectil y los 
     *mensajes de información. Debido a que el dibujado de los elementos se realiza de manera casi 
     *instantáneo, es necesario dormir el hilo (usar sleep) unos milisegundos o segundos para mostrar
     *apropiadamente el dibujo del proyectil o los ver los mensajes.
     *
     *Es utilizado por los métodos accion_batalla() y movimiento_proyectil que realizan las acciones
     *anteriormente mencionadas
     */
	public void run() {
		try {
			accion_batalla();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 *Método que se utiliza cuando un jugador gana la partida. Inhabilita los botones para disparar  
	 *además de la barra desplegable.
	 */
	public void ganar() {
		 aceptar1.setEnabled(false);
	}
	/**
	 *Método que realiza distintas acciones según corresponda:
	 *Caso jugador=1; se llama al método movimiento_proyectil para lanzar un proyectil usando 
	 *las formulas y el tipo de proyectil que lanzará según el tanque 1.
	 *
	 *Caso jugador=2; se llama al método movimiento_proyectil para lanzar un proyectil usando 
	 *las formulas y el tipo de proyectil que lanzará según el tanque 2.
	 *
	 *Caso no_jugada=true; este caso se activa cuando los datos ingresados a las cajas de texto son erróneos,
	 *se mostrará un mensaje para que verifique los datos, se mostrará hasta que ingrese los datos correctos.
	 *
	 *Caso no_proyectiles=true; en caso de que se acaben los proyectiles, aparecerá el mensaje de "no hay munición",
	 *se mostrará este mensaje, hasta que cambie a un tipo de proyectil que tenga unidades disponibles 
	 */
	public void accion_batalla() throws InterruptedException {
		if (jugador!=0) {
			movimiento_proyectil(formulasJugador,proyectilDisparar);
		}

		if (no_jugada){
			Graphics g=batalla.getGraphics();
			String verifique= "Verifique los datos Ingresados";
			FontMetrics fm=g.getFontMetrics();
		   	g.setFont(new Font("Serif",Font.BOLD,20));
		   	g.setColor(Color.WHITE);
		   	int ancho1=fm.stringWidth(verifique);
		   	g.drawString(verifique,((ventana_ancho-ancho1)/2)-20,20);
		   	Thread.sleep(1000);
	    	batalla.repaint();
	    	no_jugada=false;
		}
		if (no_proyectiles) {			
			Graphics g=batalla.getGraphics();
			String noProyectiles= "Sin Munición";
			FontMetrics fm=g.getFontMetrics();
		   	g.setFont(new Font("Serif",Font.BOLD,20));
		   	g.setColor(Color.WHITE);
		   	int ancho1=fm.stringWidth(noProyectiles);
		   	g.drawString(noProyectiles,((ventana_ancho-ancho1)/2)-20,20);
		   	Thread.sleep(1000);
	    	batalla.repaint();
	    	no_proyectiles=false;
		}
	}
	/**
	 *Metodo para generar y dibujar el desplazamiento del proyectil. 
	 *Primero se verifica si la velocidad de mayor que cero, después se piden un conjunto de puntos (x,y) calculados 
	 *por la clase formulas, usando un método para obtener los puntos de la trayectoria de un proyectil, después, 
	 *se obtienen los gráficos desde la clase Panel_Batalla y se comienza a dibujar su desplazamiento, esto 
	 *se hace iterando con el conjunto de puntos. Luego se verifica si hubo alguna clase de colisión 
	 *(auto-colisión, colisión con otro tanque o colisión con el terreno), si se cumple, se rompe la iteración,
	 *se asignan los valores del punto de colisión para mostrar la información, en caso de que es una colisión al terreno
	 *se utilizarán métodos set provenientes de la clase Panel_Batalla para cambiar los valores y actualizar el mapa.
	 *Se cambia el valor de las unidades del proyectil usado.
	 * 
	 * @param formulas_jugador, parámetro de la clase formulas, donde es calculado la altura máxima, alcance máximo y la función para 
	 * la trayectoria del proyectil
	 * @param proyectil_disparar, parámetro 
	 * @throws InterruptedException, se utiliza un hilo para disminuir el proceso de dibujado de la trayectoria
	 */
	public void movimiento_proyectil(Formulas formulas_jugador, Proyectil proyectil_disparar) throws InterruptedException {
		boolean alcanzo_altura_maxima=false;
		int coordenada_x_colision=0;
		int coordenada_y_colision=0;
		
		if (formulas_jugador.getVelocidad()>0) {
			//se devuelve lista con los puntos para lanzar el proyectil
			for (int[] par_ordenado: formulas_jugador.getLista_posiciones_proyectil()) {
		   	//se dibujan los puntos en el Panel_Batalla
				Graphics g=batalla.getGraphics(); //se obtienen los gráficos de Panel_Batalla
				
				proyectil_disparar.dibujar_trayectoria_proyectil(par_ordenado[0],par_ordenado[1], g); //se dibuja trayectoria
				Thread.sleep(variarGravedadeEfecto(batalla.getGravedadCoeficiente()));//el hilo hace disminuir el proceso de dibujado 
				//se comprueba si se alcanzo altura maxima
				if (pantalla_alto_juego-formulas_jugador.getAltura_maxima_proyectil()==par_ordenado[1]) {
					alcanzo_altura_maxima=true;
				}
			
				colisiones.verificarColisionesTerreno(par_ordenado[0],par_ordenado[1],
				proyectil_disparar.getTamano_proyectil(),proyectil_disparar.getTamano_proyectil());
				
				tanqueAtacado=colisiones.verificarColisionesTanques(par_ordenado[0],par_ordenado[1],
				proyectil_disparar.getTamano_proyectil(),proyectil_disparar.getTamano_proyectil());
				
				tocaBorde=colisiones.verificarColisionesPantalla(par_ordenado[0],par_ordenado[1]);
				colisionTerreno= colisiones.isColision_terreno();
				colisionTanques=colisiones.isColision_tanque();
					
				if (colisionTerreno||colisionTanques||tocaBorde) {
					coordenada_x_colision=colisiones.getDistancia_x();
					coordenada_y_colision=colisiones.getAltura_y();		
					break;
				}
			}
			if (colisionTerreno) {
				batalla.existe_colision_terreno(colisionTerreno);
				batalla.colision_bloque_afectado(colisiones.getBloque_terreno_afectado());		
				batalla.valor_dano_proyectil(proyectil_disparar.getDano_producido());
			}
			proyectil_disparar.setUnidades_disponibles(proyectil_disparar.getUnidades_disponibles()-1);
		}
			mostrar_info_lanzamiento(alcanzo_altura_maxima,coordenada_x_colision,coordenada_y_colision,formulas_jugador);	
	}
	
	/**
	 * Método para mostrar la información del lanzamiento
	 * Dependiendo del jugador saldrá el nombre de JUGADOR 1 o JUGADOR 2.
	 * Se piden los gráficos de la clase Panel_Batalla para dibujar los mensajes.
	 * Se muestra el nombre del jugador, la altura y distancia alcanzada.
	 * 
	 * @param alcanzo_altura_maxima, boolean para determinar si se alcanzó la altura máxima u otra altura.
	 * @param coordenada_x_colision, int para mostrar la distancia recorrida del proyectil
	 * @param coordenada_y_colision, int para mostrar la altura alcanzada
	 * @param formulas_jugador, formulas para verificar con la distancias y alturas máximas
	 * @throws InterruptedException, se utiliza hilo para mostrar el mensaje por milisegundos
	 */
	public void mostrar_info_lanzamiento(boolean alcanzo_altura_maxima,int coordenada_x_colision,
		int coordenada_y_colision, Formulas formulas_jugador) throws InterruptedException {
		String jugador_string="";
		int getPos_x_tanque=0;
		int distancia_alcanzada=0;
		int altura_alcanzada=0;
	
		if (jugador==1) {
			getPos_x_tanque=tanqueEnJuego.getPos_x_tanque();
		}
		if (jugador==2) {
			getPos_x_tanque=tanqueEnJuego.getPos_x_tanque();
		}
		if(jugador==2 && decidioIA==1) {
			getPos_x_tanque=tanqueEnJuego.getPos_x_tanque();
		}
		
		Graphics g=batalla.getGraphics(); //se obtienen los gráficos de Panel_Batalla
        //se agrega texto con la altura y la distancia
		FontMetrics fm=g.getFontMetrics();
	   	g.setFont(new Font("Serif",Font.BOLD,20));
	   	g.setColor(Color.WHITE);
	   	
	   	
	   	if (formulas_jugador.getVelocidad()==0) {
    		distancia_alcanzada=0;
    		altura_alcanzada=0;
    	}
    	else {
    		if (formulas_jugador.getAngulo()==90) {
    			altura_alcanzada=formulas_jugador.getAltura_maxima_proyectil();
    			distancia_alcanzada=0;
    		}
    		else {
    			distancia_alcanzada=coordenada_x_colision-getPos_x_tanque;
        		altura_alcanzada=comprobarAlturaAlcanzada(coordenada_y_colision,
            	formulas_jugador.getAltura_maxima_proyectil(),alcanzo_altura_maxima);
    		}	
    	}
	   	
	   	if(decidioIA==1) {

		   	if (jugador==1) {
		   		jugador_string="JUGADOR 1";
			   	g.drawString(jugador_string,5,ventana_alto-50);
				
			   	String altura="Altura: "+altura_alcanzada;
		    	g.drawString(altura,5,ventana_alto-30);	
		    	
		    	String distancia="Distancia: "+Math.abs(distancia_alcanzada);
		    	g.drawString(distancia,5,ventana_alto-10);
		    	Thread.sleep(1000);
			}
	
			if(jugador==2) {
				jugador_string="JUGADOR IA";
			   	int ancho=fm.stringWidth(jugador_string);
			   	g.drawString(jugador_string,(ventana_ancho-ancho)-58,ventana_alto-50);
				
				String altura="Altura: "+altura_alcanzada;
			   	int ancho2=fm.stringWidth(altura);
		    	g.drawString(altura,(ventana_ancho-ancho2)-50,ventana_alto-30);	
		    	
		    	//distancia alcanzada
		    	String distancia="Distancia: "+Math.abs(distancia_alcanzada);
		    	int ancho3=fm.stringWidth(distancia);
		    	g.drawString(distancia,(ventana_ancho-ancho3)-50,ventana_alto-10);
		    	Thread.sleep(2000);
			}
		   
	   	}
	   	if(decidioIA==0) {
	   		if (jugador==1) {
				jugador_string="JUGADOR 1";
				int ancho1=fm.stringWidth(jugador_string);
			   	g.drawString(jugador_string,(ventana_ancho-ancho1)/2-10,20);
			}
			if (jugador==2) {
				jugador_string="JUGADOR 2";
				int ancho1=fm.stringWidth(jugador_string);
			   	g.drawString(jugador_string,(ventana_ancho-ancho1)/2-10,20);
			}
	   	//altura alcanzada
		   	String altura="Altura Alcanzada: "+altura_alcanzada;
		   	int ancho2=fm.stringWidth(altura);
	    	g.drawString(altura,(ventana_ancho-ancho2)/2-20,50);	
	    	
	    	//distancia alcanzada
	    	String distancia="Distancia Alcanzada: "+Math.abs(distancia_alcanzada);
	    	int ancho3=fm.stringWidth(distancia);
	    	g.drawString(distancia,(ventana_ancho-ancho3)/2-20,70);
	    	Thread.sleep(2000);
	   	}
	   	batalla.repaint();
	 
	}
	
	
	/**
	 * Método para comprobar si la altura que alcanzó el proyectil fue la máxima o no, debido a que puede chocar antes,
	 * en ese caso se queda con el valor de la altura donde ocurrió la colisión, sino, se queda con el valor
	 * de la altura máxima.
	 * 
	 * @param punto_y_alcanzado, valor de la coordenada y que alcanzó el proyectil
	 * @param altura_maxima, valor calculado con la clase formulas para determinar la altura máxima del proyectil si realiza 
	 * el recorrido completo
	 * @param altura_alcance, valor booleano que se determina antes de llamar a este método, se comprueba con la condición 
	 * de que en algún momento, la coordenada y es igual a la altura máxima calculada. En caso que sea cierto
	 * el valor es true, en caso contrario el valor es false.
	 * @return altura_alcanzada, retorna el valor de la altura 
	 */
	public int comprobarAlturaAlcanzada(int punto_y_alcanzado, int altura_maxima, boolean altura_alcance) {
		int altura_alcanzada=0;
		
		if (altura_alcance){
			altura_alcanzada=altura_maxima;
		}
		else {
			altura_alcanzada=pantalla_alto_juego-punto_y_alcanzado;		
		}
		return altura_alcanzada;
	}
	
	/**
	 *Método para bloquear los controles dependiendo del turno del jugador.
	 *En caso de que sea jugador=1, se activa el control del jugador 1 y bloquea
	 *el jugador 2.
	 *En caso de que sea jugador=2, se activa el control del jugador 2 y bloquea
	 *el jugador 1.
	 */
	public void turno_jugadores() {
		if (jugador==1) {
 	   		jugador=0;
 	   		batalla.setJugadorTurno(2);
                        aceptar1.setBackground(Color.GREEN);
                        aceptar1.setText("Disparar J2");
                        angulo.setText(null);
                        velocidad.setText(null);
 	   	}
		
		if (jugador==2) {
 			jugador=0;
 			batalla.setJugadorTurno(1);
                        aceptar1.setBackground(Color.YELLOW);
                        aceptar1.setText("Disparar J1");
                        angulo.setText(null);
                        velocidad.setText(null);
                }	
	}
        
	/**
	 * Método para verificar si los datos ingresados son correctos, pedir los datos de los tanques y de las formulas para después 
	 * utilizar run() y realizar las acciones. 
	 * Se revisa si algún tanque recibió alguna colisión para disminuir la vida del tanque, esto es dependiendo del jugador,
	 * si jugador 1 recibe ataque o autocolisión, el tanque 1 disminuye de vida, si llega a cero, gana el jugador 2.
	 * Se revisa si algún tanque ganó, esto es informado y llevado a un método set que pertenece a Panel_Batalla, para mostrar el 
	 * jugador vencedor.
	 * 
	 * @param vel, string para la velocidad, generado por la caja de texto.
	 * @param ang, string para el ángulo, generado por la caja de texto.
	 * @param tipo_proyectil, string para el tipo de proyectil, generado por la lista desplegable.
	 */
	public void verificar_formular_y_combatir(String vel, String ang, String tipo_proyectil, String direccion) {
		if(esNumeroValidar(ang) && esNumeroValidar(vel) && ang != "" && vel != "" 
				&& Integer.parseInt(ang)>=0 && Integer.parseInt(ang)<=90 && Integer.parseInt(vel)<=200){		
			int posicion_disparo_x=0;
			int posicion_disparo_y=0;
        	int velocidad=Integer.parseInt(vel);
        	int angulo=Integer.parseInt(ang);

        	if (jugador==tanqueEnJuego.getTanquePerteneceJugador()) {
        		posicion_disparo_x=tanqueEnJuego.getCoorDisparoX();
            	posicion_disparo_y=pantalla_alto_juego-tanqueEnJuego.getCoorDisparoY();
            	
            	formulasJugador = new Formulas(velocidad,angulo,posicion_disparo_x,posicion_disparo_y,
            			ventana_ancho,pantalla_alto_juego,direccion, batalla.getValorViento(), batalla.getDireccionViento()
            			,batalla.getGravedadCoeficiente());
            	
            	for (Proyectil proyectil : tanqueEnJuego.getLista_proyectiles()) {
    				if (proyectil.getTipo_proyectil().equals(tipo_proyectil)) {
    					proyectilDisparar=proyectil;
    				}
    			}
           
            	if (!proyectilDisparar.tiene_proyectiles()) {
            		no_proyectiles=true;
            		jugador=0;
            	}
            	
            	run();
            	
            	if (colisionTanques) {
            		ArrayList<Tanque> tanquesCopia = new ArrayList<Tanque>(batalla.getTanquesEnJuego());
            		tanqueAtacado.setVida_tanque(tanqueAtacado.getVida_tanque()-proyectilDisparar.getDano_producido());
            		for (Tanque tanqueEnJuego : tanquesCopia) {
            			if (tanqueEnJuego.getVida_tanque()==0) {
            				if(tanqueEnJuego.getTanquePerteneceJugador()==1) {
            					batalla.setCantidadTanquesJ1(batalla.getCantidadTanquesJ1()-1);
            				}
            				if(tanqueEnJuego.getTanquePerteneceJugador()==2) {
            					batalla.setCantidadTanquesJ2(batalla.getCantidadTanquesJ2()-1);
            				}
            				batalla.eliminarTanquesEnJuego(tanqueEnJuego);
            				
            				if(decidioIA==1) {
            					jugadorIA.actualizarTanques(batalla.getTanquesEnJuego());
            				}
            			}
            		}
            	}
            	
            	if (batalla.getCantidadTanquesJ1()==0) {
            		ganar();
            		batalla.setJugador_ganador(2);
            		batalla.setJuego_terminado(true);
            	}
            	if (batalla.getCantidadTanquesJ2()==0) {
            		ganar();
            		batalla.setJugador_ganador(1);
            		batalla.setJuego_terminado(true);
            	}
            	
            	int sinMunicion=0;
            	for (Tanque tanque : batalla.getTanquesEnJuego()) {     			
        			for (Proyectil proyectil : tanque.getLista_proyectiles()) {
        				if(!proyectil.tiene_proyectiles()) {
        					sinMunicion++;
        				}		
        			}
        		}
            	
				if (sinMunicion==(batalla.getCantidadTanquesJ1()+batalla.getCantidadTanquesJ2())*3) {
            		System.out.println("entra");
					if(batalla.getCantidadTanquesJ1()>batalla.getCantidadTanquesJ2()) {
            			ganar();
                		batalla.setJugador_ganador(1);
                		batalla.setJuego_terminado(true);
            		}
            		if(batalla.getCantidadTanquesJ2()>batalla.getCantidadTanquesJ1()) {
            			ganar();
                		batalla.setJugador_ganador(2);
                		batalla.setJuego_terminado(true);
            		}
            		if(batalla.getCantidadTanquesJ2()==batalla.getCantidadTanquesJ1()) {
            			ganar();
                		batalla.setJugador_ganador(-1);
                		batalla.setJuego_terminado(true);
            		}
            	}
       
        	}
     	}
        else {
 	   		no_jugada=true;
 	   		run(); 
        }
	}

}

