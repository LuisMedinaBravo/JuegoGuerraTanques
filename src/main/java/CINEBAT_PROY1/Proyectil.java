

package CINEBAT_PROY1                                                                                                                                                                                                                                                                                                                                                                                               ;

import java.awt.Color;
import java.awt.Graphics;

public class Proyectil {
	private String tipo_proyectil;
	private int unidades_disponibles;
	private int dano_producido;
	private int tamano_proyectil;
	private boolean proyectiles_disponibles;
	
	/**
	 * Constructor clase Proyectil
	 * 
	 * @param tipo_proyectil, string para recibir el nombre del proyectil
	 * @param unidades_disponibles, int para determinar las unidades disponibles de los proyectiles
	 * @param dano_producido, int para recibir el daño que produce los proyectiles
	 * @param tamano_proyectil, int para determinar el tamaño del proyectil, para ser dibujado en el terreno (Panel_Batalla)
	 */
	public Proyectil(String tipo_proyectil, int unidades_disponibles, int dano_producido, int tamano_proyectil){
		this.tipo_proyectil= tipo_proyectil;
		this.unidades_disponibles= unidades_disponibles;
		this.dano_producido= dano_producido;
		this.tamano_proyectil=tamano_proyectil;
	}
	
	/**
	 * Método para consultar si hay proyectiles. 
	 * En caso de que no hay proyectiles, retornará false, sino
	 * retornará true.
	 * 
	 * @return proyectiles_disponibles, booleano que retorna true o false según corresponda.
	 */
	public boolean tiene_proyectiles(){
		
		if(unidades_disponibles==0) {
			proyectiles_disponibles=false;
		}
		else {
			proyectiles_disponibles=true;
		}
		return proyectiles_disponibles;
	}
	
	/**
	 * Método para dibujar la trayectoria del proyectil.
	 * Utiliza las coordenadas de los puntos calculados por la clase Formulas, en la funcion para la 
	 * trayectoria del proyetil. Para el dibujo, se ingresa los gráficos de la clase Panel_Batalla.
	 * En caso de que es un proyectil de 105mm, el color es rojo.
	 * En caso de que es un proyectil perforante, el color es gris.
	 * En caso de que es un proyectil 60mm, el color es azul.
	 * 
	 * @param coordenada_x, int para ingresar la coordenada x de un punto del proyectil.
	 * @param coordenada_y, int para ingresar la coordenada y de un punto del proyectil.
	 * @param g, graphics, para dibujar la trayectoria del proyectil en la pantalla.
	 */
	public void dibujar_trayectoria_proyectil(int coordenada_x, int coordenada_y, Graphics g) {
		if(tipo_proyectil.equals("105mm")) {
			g.setColor(new Color(89,75,64));
			g.fillOval(coordenada_x, coordenada_y, tamano_proyectil, tamano_proyectil);
		}
		
		if(tipo_proyectil.equals("perforante")) {
			g.setColor(new Color(60,55,89));
			g.fillOval(coordenada_x, coordenada_y, tamano_proyectil, tamano_proyectil);
		}
		
		if(tipo_proyectil.equals("60mm")) {
			g.setColor(new Color(48,89,46));
			g.fillOval(coordenada_x, coordenada_y, tamano_proyectil, tamano_proyectil);
		}
	}

	public String getTipo_proyectil() {
		return tipo_proyectil;
	}
	
	public int getUnidades_disponibles() {
		return unidades_disponibles;
	}
	
	/**
	 * Método set para cambiar la cantidad de unidades de los proyectiles.
	 * @param unidades_disponibles, int para ingresar el nuevo valor de unidades de proyectiles.
	 */
	public void setUnidades_disponibles(int unidades_disponibles) {
		this.unidades_disponibles = unidades_disponibles;
	}
	
	public int getDano_producido() {
		return dano_producido;
	}

	public int getTamano_proyectil() {
		return tamano_proyectil;
	}
}
