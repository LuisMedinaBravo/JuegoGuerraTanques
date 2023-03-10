
package CINEBAT_PROY1                                                                                                                                                                                                                                                                                                                                                                                               ;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Tanque {
	private int pos_x_tanque;
	private int pos_y_tanque;

	private Rectangle tanque_delimitador_arriba;
	private Rectangle tanque_delimitador_abajo;
	private Image imagen_tanque;
	private ArrayList<Proyectil> lista_proyectiles;
	private int vida;
	
	private int tanquePerteneceJugador;
	private int numeroDeTanque;
	private Rectangle radioUbicacionTanque;
	private int coorDisparoX;
	private int coorDisparoY;
	private Rectangle tanqueDelimitadorTerreno;
	private int posTanqueCorrectaX;
	private int posTanqueCorrectaY;
	private int correccionX, correccionY;
	
	private ArrayList<int[]>puntosInterTanqueTerreno;

	/**
	 * Constructor clase Tanque.
	 * Se crean los proyectiles con método crear_proyectiles().
	 * 
	 * @param pos_x_tanque, posición de la coordenada x del tanque
	 * @param pos_y_tanque, posición de la coordenada y del tanque
	 * @param imagen_tanque, imagen que se mostrará en la pantalla
	 * @param tanque_delimitador_arriba, delimitador rectángulo superior del tanque
	 * @param tanque_delimitador_abajo, delimitador rectángulo inferior del tanque
	 * @param vida
	 */
	public Tanque(int tanquePerteneceJugador,int numeroDeTanque, int pos_x_tanque,int pos_y_tanque, 
			Image imagen_tanque, int vida, int correccionX, int correccionY,int misilPerforanteCant, int misil105Cant ,int misil60Cant) {
		this.pos_x_tanque= pos_x_tanque;
		this.pos_y_tanque= pos_y_tanque;
		this.imagen_tanque= imagen_tanque;
		this.vida=vida;
		this.tanquePerteneceJugador=tanquePerteneceJugador;
		this.numeroDeTanque=numeroDeTanque;
		this.correccionX=correccionX;
		this.correccionY=correccionY;
		puntosInterTanqueTerreno= new ArrayList<int[]>();
		lista_proyectiles= new ArrayList<Proyectil>(); //arraylist para guardar los proyectiles
		radioUbicacionTanque= null;
		tanque_delimitador_arriba=null;
		tanque_delimitador_abajo= null;
		tanqueDelimitadorTerreno=null;
		
		crearOtrasPosiciones(pos_y_tanque);
		crearDelimitadores(pos_y_tanque);
		crear_proyectiles(misilPerforanteCant, misil105Cant ,misil60Cant);
	}
	
	
	/**
	 * Método para crear los proyectiles, se crean con la clase Proyectil
	 * usando los parámetros para el nombre el proyectil, las cantidades, el daño y el tamaño
	 * Se agregan al arraylist lista_proyectiles
	 */
	public void crearOtrasPosiciones(int postanqueY) {
		coorDisparoX=pos_x_tanque-25;
		coorDisparoY=postanqueY-33;
		posTanqueCorrectaX=pos_x_tanque-correccionX;
		posTanqueCorrectaY=postanqueY-correccionY;
	}
	
	
	public void crear_proyectiles(int misilPerforanteCant, int misil105Cant ,int misil60Cant) {
		Proyectil CientoCinco_mm = new Proyectil("105mm",misil105Cant,50,7);
		Proyectil Perforante = new Proyectil("perforante",misilPerforanteCant,40,6);
		Proyectil Sesenta_mm= new Proyectil("60mm",misil60Cant,30,5);
		lista_proyectiles.add(CientoCinco_mm);
		lista_proyectiles.add(Perforante);
		lista_proyectiles.add(Sesenta_mm);
	
	}
	
	public void crearDelimitadores(int postanqueY) {
		radioUbicacionTanque= new Rectangle (pos_x_tanque-50,postanqueY-28,53, 30);
		tanque_delimitador_arriba= new Rectangle(pos_x_tanque-37,postanqueY-24,32,8);
		tanque_delimitador_abajo= new Rectangle(pos_x_tanque-46, postanqueY-16,52,16);
		tanqueDelimitadorTerreno= new Rectangle(pos_x_tanque-48, postanqueY-5,60,10);
	}
	
	public int getPos_x_tanque() {
		return pos_x_tanque;
	}

	public int getPos_y_tanque() {
		return pos_y_tanque;
	}
	
	public void cambioUbicacionTanque(int nuevoPosY) {
		this.pos_y_tanque=nuevoPosY;
		crearOtrasPosiciones(nuevoPosY);
		crearDelimitadores(nuevoPosY);
	}

	public Rectangle getTanque_delimitador_arriba() {
		return tanque_delimitador_arriba;
	}

	public Rectangle getTanque_delimitador_abajo() {
		return tanque_delimitador_abajo;
	}

	public int getVida_tanque() {
		return vida;
	}
	
	/**
	 * Método set para cambiar la vida del tanque
	 * Si la vida del tanque disminuye pero el valor es positivo, se asigna
	 * ese valor de vida, en caso de que al disminuir la vida llegué a un número
	 * negativo, el valor siempre será 0.
	 * 
	 * @param vida_tanque, int que recibe el valor (ya disminuido) de la vida del tanque.
	 */
	public void setVida_tanque(int vida_tanque) {
		if (vida_tanque>0) {
			this.vida =vida_tanque;
		}
		else {
			vida = 0;
		}	
	}
	
	public Image getImagen_tanque() {
		return imagen_tanque;
	}

	public ArrayList<Proyectil> getLista_proyectiles() {
		return lista_proyectiles;

	}
		
	public int getTanquePerteneceJugador() {
		return tanquePerteneceJugador;
	}

	public int getNumeroDeTanque() {
		return numeroDeTanque;
	}

	public Rectangle getRadioUbicacionTanque() {
		return radioUbicacionTanque;
	}

	public int getCoorDisparoX() {
		return coorDisparoX;
	}

	public int getCoorDisparoY() {
		return coorDisparoY;
	}

	public Rectangle getTanqueDelimitadorTerreno() {
		return tanqueDelimitadorTerreno;
	}

	public int getPosTanqueCorrectaX() {
		return posTanqueCorrectaX;
	}

	public int getPosTanqueCorrectaY() {
		return posTanqueCorrectaY;
	}

	public ArrayList<int[]> getPuntosInterTanqueTerreno() {
		return puntosInterTanqueTerreno;
	}
}
