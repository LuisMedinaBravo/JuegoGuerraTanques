
package CINEBAT_PROY1;

import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Set;

public class Colisiones {
	PanelBatalla colisiones_batalla; //se obtienen datos del Panel_Batalla
	
	Set<int[]> terreno_coordenadas;
	private int[] bloque_terreno_afectado;
	
	private int distancia_x;
	private int altura_y;
	private int ancho_bloque;
	private int alto_bloque;
	private int jugador_atacado;
	
	private boolean colision_terreno;
	private boolean colision_tanque;
	private boolean auto_colision;
	private int ancho_pantalla;
	
	/**
	 * Constructor para la clase Colisiones
	 * @param batalla, instancia de tipo Panel_Batalla para obtener los datos de esa clase (principalmente el terreno y los tanques)
	 */
	public Colisiones(PanelBatalla batalla, int ancho_pantalla) {
		this.ancho_pantalla=ancho_pantalla;
		this.colisiones_batalla=batalla;
		distancia_x=0;
		altura_y=0;
		bloque_terreno_afectado=new int[2];
		terreno_coordenadas=new HashSet<int[]>();
		ancho_bloque=batalla.getAncho_bloque_terreno();
		alto_bloque=batalla.getAlto_bloque_terreno();
	}
	
	/**
	 * Método para verificar las colisiones en el terreno.
	 * Primero se crean delimitadores para los puntos del proyectil, luego en un ciclo, iterando los bloques del terreno,
	 * se crean delimitadores para los bloques, se verifica si se intersecta, eso da como resultado un valor booleano true,
	 * entonces se asignan a variables la altura y distancia donde ocurrió la colisión, asi como las coordenadas del bloque
	 * afectado y valor booleano de la colisión. Finalmente se rompe el ciclo.
	 * 
	 * @param proyectil_x, int con la coordenada en x del proyectil.
	 * @param proyectil_y, int con la coordenada en y del proyectil.
	 * @param ancho, int con el tamaño del ancho del proyectil*
	 * @param alto, int con el tamaño del alto del proyectil*
	 * (*) al ser un cuadrado los valores son iguales, esto representa el tamaño del proyectil
	 */
	public void verificarColisionesTerreno(int proyectil_x, int proyectil_y,int ancho, int alto) {
		terreno_coordenadas=colisiones_batalla.getTerreno_actual();
		colision_terreno=false;
		Rectangle limite_proyectil=new Rectangle(proyectil_x,proyectil_y,ancho, alto);
		for (int[] datos : terreno_coordenadas) {
			Rectangle limites_bloque=new Rectangle(datos[0], datos[1], ancho_bloque, alto_bloque);
			colision_terreno=limite_proyectil.intersects(limites_bloque);
			if (colision_terreno) {
				distancia_x=proyectil_x;
				altura_y =proyectil_y;
				bloque_terreno_afectado[0]=datos[0];
				bloque_terreno_afectado[1]=datos[1];
				break;
			}
		}
	}
	
	/**
	 * Método para verificar si el proyectil traspasa el ancho de la pantalla, en caso de que se cumpla retorna true
	 * sino false.
	 * 
	 * @param proyectil_x, int con la coordenada en x del proyectil.
	 * @param proyectil_y, int con la coordenada en y del proyectil.
	 * @return colision, valor booleano, true o false.
	 */
	public boolean verificarColisionesPantalla(int proyectil_x,int proyectil_y) {
		boolean colision=false;
		if (proyectil_x>=ancho_pantalla-10 || proyectil_x<=10) {
			distancia_x=proyectil_x;
			altura_y =proyectil_y;
			colision=true;
		}
		return colision;
	}

	/**
	 * Método para verificar las colisiones con los tanques.
	 * Primero se ingresan los delimitadores de los tanques, luego se crea el delimitador para el punto donde golpeó 
	 * el tanque, entonces:
	 * Si es el jugador=1, la colisión del tanque (inferior o superior), es true, se asigan los valores de la altura y
	 * el distancia que recorrió el proyectil hasta el punto de choque, se asigna que el jugador 2 recibió un ataque. En caso
	 * de autocolisión=true, se asigna al mismo jugador. Es análogo para el jugador=2. Sino todas las colisiones quedan en false.
	 * 
	 * @param proyectil_x, int con la coordenada en x del proyectil.
	 * @param proyectil_y, int con la coordenada en y del proyectil.
	 * @param ancho, int con el tamaño del ancho del proyectil*.
	 * @param alto, int con el tamaño del alto del proyectil*.
	 * (*) Al ser un cuadrado los valores son iguales, esto representa el tamaño del proyectil.
	 * @param tanque1_arriba, Rectangle delimitador superior del tanque del jugador 1.
	 * @param tanque2_arriba, Rectangle delimitador superior del tanque del jugador 2.
	 * @param tanque1_abajo, Rectangle delimitador inferior del tanque del jugador 1.
	 * @param tanque2_abajo, Rectangle delimitador inferior del tanque del jugador 2.
	 * @param jugador, int para asignar el jugador que tiene turno para atacar.
	 */
	public Tanque verificarColisionesTanques(int proyectil_x, int 
		proyectil_y,int ancho, int alto){
		Tanque tanqueAtacado = null;
		colision_tanque=false;
		Rectangle limite_proyectil=new Rectangle(proyectil_x, proyectil_y, ancho, alto);
		for (Tanque tanqueEnCombate: colisiones_batalla.getTanquesEnJuego()) {
			if (limite_proyectil.intersects(tanqueEnCombate.getTanque_delimitador_abajo())||
					limite_proyectil.intersects(tanqueEnCombate.getTanque_delimitador_arriba())) {
				colision_tanque=true;
				tanqueAtacado=tanqueEnCombate;
				break;
			}
		}
		return tanqueAtacado;
	}

	public int getDistancia_x() {
		return distancia_x;
	}
	
	public int getAltura_y() {
		return altura_y;
	}
	
	/**
	 * Método para retornar un arreglo con las coordenadas x e y de un bloque del terreno
	 * @return arrreglo de int, contiene un punto (x,y).
	 */
	public int[] getBloque_terreno_afectado() {
		return bloque_terreno_afectado;
	}
	
	/**
	 * Método para retornar al jugador que fue atacado.
	 * @return int, retornar un valor que corresponde a un jugador.
	 */
	public int getJugador_atacado() {
		return jugador_atacado;
	}
	
	/**
	 * Método para retornar un booleano en el caso que hubo una colision en el terreno.
	 * @return colision_terreno, booleano que retorna true si hubo una colisión en el terreno, caso contrario, falso.
	 */
	public boolean isColision_terreno() {
		return colision_terreno;
	}
	
	/**
	* Método para retornar un booleano en el caso que hubo una colision con algún tanque.
	 * @return colision_tanque, booleano que retorna true si hubo una colisión con un tanque, caso contrario, falso.
	 */
	public boolean isColision_tanque() {
		return colision_tanque;
	}
	
	/**
	 * Método para retornar un booleano en el caso que hubo una auto-colision (tanque que se lanza a si mismo un proyectil).
	 * @return auto_colision, booleano que retorna true si hubo una auto-colisión, caso contrario, falso.
	 */
	public boolean isAuto_colision() {
		return auto_colision;
	}
	
}
