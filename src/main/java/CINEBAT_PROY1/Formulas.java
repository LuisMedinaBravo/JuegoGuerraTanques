
package CINEBAT_PROY1                                                                                                                                                                                                                                                                                                                                                                                               ;


/*Clase con las formulas para altura y distancia maximas del proyectil*/
//formulas obtenidas de http://recursostic.educacion.es/descartes/web/materiales_didacticos/comp_movimientos/parabolico.htm
//http://recursostic.educacion.es/descartes/web/materiales_didacticos/comp_movimientos/parabolico.htm

import java.util.ArrayList;

public class Formulas {
	private int velocidad; 
	private int angulo;
	private int posicion_inicial_x;
	private int posicion_inicial_y;
	private int gravedadCoeficiente; 
	private int altura_maxima_proyectil;
	private int alcance_maximo_proyectil;
	private int ancho_pantalla;
	private int pantalla_alto_juego;

	
	//se utiliza para guardar los pares ordenados (x,y) generado por la funcion de la trayectoria parabolica
	private ArrayList<int[]> lista_posiciones_proyectil; 
	private String direccionLanzar;
	private int viento;
	String direccionViento;

	/**
	 * Constructor para la clase Formulas, crea las fórmulas para los lanzamientos de los proyectiles.
	 * 
	 * @param velocidad, int determina la velocidad del lanzamiento.
	 * @param angulo, int determina el ángulo del lanzamiento.
	 * @param posicion_inicial_x, int que corresponde al valor inicial en la coordenada x para el lanzamiento.
	 * @param posicion_inicial_y, int que corresponde al valor inicial en la coordenada y para el lanzamiento.
	 * @param jugador, int que corresponde al número del jugador.
	 * @param ancho_pantalla, int que corresponde al valor del ancho de la pantalla del juego
	 * @param pantalla_alto_juego, int que corresponde al valor del alto de la pantalla del juego (Panel_Batalla)
	 */
	public Formulas(int velocidad, int angulo, int posicion_inicial_x, int posicion_inicial_y, int ancho_pantalla,int pantalla_alto_juego,
			String direccion, int viento, String direccionViento, int gravedad) {
		this.velocidad=velocidad;
		this.angulo=angulo;
		this.posicion_inicial_x=posicion_inicial_x;
		this.posicion_inicial_y=posicion_inicial_y;
		this.ancho_pantalla=ancho_pantalla;
		this.pantalla_alto_juego=pantalla_alto_juego;
		this.direccionLanzar=direccion;
		this.gravedadCoeficiente=gravedad; //aproximacion del coeficiente_gravedad
		this.viento=viento;
		this.direccionViento=direccionViento;
		altura_maxima_proyectil=0;
		alcance_maximo_proyectil=0;

		//metedos para las formulas
		altura_max();
		distancia_max();
		funcion_trayectoria_parabolica();
	}
	

	/**
	 * Método para determinar la altura máxima que puede alcanzar el proyectil.
	 */
	public void altura_max() {
		double altura_maxima=0;
		/*en 90 grados, la altura solo es calculada como una recta vertical 
		 * hasta llegar al punto más alto (lanzamiento vertical hacia arriba).
		 */
		if (angulo==90) {
			altura_maxima=(Math.pow(velocidad,2)/(2*gravedadCoeficiente));
			altura_maxima_proyectil=(int)altura_maxima;
		}
		else {
			//se agrega altura de donde se encuentra el tanque desde el origen
			altura_maxima=((Math.pow(velocidad,2)*Math.pow(Math.sin(Math.toRadians(angulo)),2))/(2*gravedadCoeficiente))+posicion_inicial_y;
			altura_maxima_proyectil=(int)altura_maxima; //conversion debido al calculo del seno, utiliza el tipo double
		}
	}
	
	/**
	 * Método para determinar la distancia máxima que puede alcanzar el proyectil.
	 */
	public void distancia_max() {
		double distancia_maxima=0;
		//velocidad cero, no hay alcance
		if (velocidad==0) {
			alcance_maximo_proyectil=0;
		}
		else {
			//se agrega distancia de donde se encuentra el tanque desde el origen
			distancia_maxima=Math.abs(((Math.pow(velocidad,2)*Math.sin(Math.toRadians(2*angulo)))/gravedadCoeficiente));
			alcance_maximo_proyectil=(int)distancia_maxima;//conversion debido al calculo del seno, utiliza el tipo double
		}
	}
	
	/**
	 * Método para la trayectoria parabólica. 
	 * Se obtiene a partir de las ecuaciones paramétricas de la trayectoria haciendo una sustitución 
	 * con el tiempo t a partir de la ecuacion de x (revisar links al principio de esta clase).
	 * Se agrega la posición inicial del tanque, es decir, donde partirá la bala.
	 * En caso de que el angulo sea 90, los valores obtenidos generarán una recta vertical, que irá en sentido hacia arriba
	 * mientras que otra recta vertical irá en sentido hacia abajo, para que se simule el efecto de subida y bajada del proyectil
	 * en el mismo lugar, esto se obtiene con ciclos cuyo límite es la altura máxima que tendrá el proyectil al lanzarlo.
	 * Sino, se determinará con la fórmula cuadrática para la trayectoria, usando solo funciones trigonómetricas. En caso de que sea el 
	 * jugador 1 que lanzé el proyectil, los valores dibujarán la trayectoria de izquierda a derecha, en caso contrario, es decir, si el 
	 * lanzador es el jugador 2, los valores podrán dibujar la trayectoria de derecha a izquierda.
	 * 
	 */
	public void funcion_trayectoria_parabolica(){
		int y=0;
		double tiempo_t=0;
		lista_posiciones_proyectil = new ArrayList<int[]>();
		
		if (angulo==90) {
			for (int valores_y=0; valores_y<altura_maxima_proyectil; valores_y++) {
				int[] par_ordenado_proyectil=new int[2]; //para guardar los puntos generados por la funcion
				par_ordenado_proyectil[0]=posicion_inicial_x-1; //se traslada respecto a la posicion del tanque
				par_ordenado_proyectil[1]=pantalla_alto_juego-(posicion_inicial_y+valores_y);
				lista_posiciones_proyectil.add(par_ordenado_proyectil);
			}
			for (int valores_y=altura_maxima_proyectil; valores_y>=-10; valores_y--) {
				int[] par_ordenado_proyectil=new int[2]; //para guardar los puntos generados por la funcion
				par_ordenado_proyectil[0]=posicion_inicial_x+1; //se traslada respecto a la posicion del tanque
				par_ordenado_proyectil[1]=pantalla_alto_juego-(posicion_inicial_y+valores_y);
				lista_posiciones_proyectil.add(par_ordenado_proyectil);
			}
		}
		else {
			if (viento>0) {
				int u=viento;
				double b=0.12;
				int anguloViento=0;
				for (double valores_t=0.0; valores_t<=100.0; valores_t+=0.1){ 
					if (direccionLanzar.equals("derecha")) {
						if(direccionViento.equals("izquierda")) {
							anguloViento=0;
						}
						if(direccionViento.equals("derecha")) {
							anguloViento=180;
						}
					}
					if(direccionLanzar.equals("izquierda")) {
						if(direccionViento.equals("izquierda")) {
							anguloViento=180;
						}
						if(direccionViento.equals("derecha")) {
							anguloViento=0;
						}
					}
					
					int[] par_ordenado_proyectil=new int[2]; //para guardar los puntos generados por la funcion
					
					int xViento=(int)(((u*Math.cos(Math.toRadians(anguloViento)))*valores_t)+
							((1/b)*(((velocidad*Math.cos(Math.toRadians(angulo))
									-u*Math.cos(Math.toRadians(anguloViento))))*(1-Math.exp(-b*valores_t)))));
					
					int yViento=(int)((
							(((gravedadCoeficiente/b)+
							velocidad*Math.sin(Math.toRadians(angulo))-u*Math.sin(Math.toRadians(anguloViento)))
							*((1-Math.exp(-b*valores_t))*(1/b))))
							-((((gravedadCoeficiente/b)-u*Math.sin(Math.toRadians(anguloViento)))*valores_t)));
					
					if (direccionLanzar.equals("derecha")) {
						par_ordenado_proyectil[0]=posicion_inicial_x+xViento;
					}
					if(direccionLanzar.equals("izquierda")) {
						par_ordenado_proyectil[0]=posicion_inicial_x-xViento;
					}
					
			
					par_ordenado_proyectil[1]=pantalla_alto_juego-(yViento+posicion_inicial_y); //se resta debido a la ubicacion del plano cartesiano en java	
					lista_posiciones_proyectil.add(par_ordenado_proyectil);
				}
			}
			else {
				for (int valores_x=0; valores_x<=ancho_pantalla; valores_x++){ 
					int[] par_ordenado_proyectil=new int[2]; //para guardar los puntos generados por la funcion
					tiempo_t=valores_x/(velocidad*Math.cos(Math.toRadians(angulo)));
					y=(int)(posicion_inicial_y + (velocidad*Math.sin(Math.toRadians(angulo))*tiempo_t) - ((gravedadCoeficiente*Math.pow(tiempo_t,2))/2));
					
					if (direccionLanzar.equals("derecha")) {
						par_ordenado_proyectil[0]=(posicion_inicial_x+valores_x); //se traslada respecto a la posicion del tanque
					}
					if(direccionLanzar.equals("izquierda")) {
						par_ordenado_proyectil[0]=(posicion_inicial_x-valores_x); //se traslada y se resta respecto a la posicion del tanque 
					}
				
					par_ordenado_proyectil[1]=pantalla_alto_juego-y; //se resta debido a la ubicacion del plano cartesiano en java	
	
					lista_posiciones_proyectil.add(par_ordenado_proyectil);
				}
			}
			
		}
	}
		
	public int getAltura_maxima_proyectil() {
		return altura_maxima_proyectil;
	}
	
	public int getAlcance_maximo_proyectil() {
		return alcance_maximo_proyectil;
	}
	
	public ArrayList<int[]> getLista_posiciones_proyectil() {
		return lista_posiciones_proyectil;
	}
	
	public int getAngulo(){
		return angulo;
	}
	
	public int getVelocidad(){
		return velocidad;
	}
	
}
