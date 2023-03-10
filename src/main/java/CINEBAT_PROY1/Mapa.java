

package CINEBAT_PROY1                                                                                                                                                                                                                                                                                                                                                                                               ;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Mapa {
	private Set<int[]> paresOrdenadosTanques;
	private Set<int[]> terreno_completo;
	private Set<int[]> terreno_copia;
	
	private int terreno_arreglo[];
	
    private int tamano_pantalla_ancho;
    private int pantalla_alto_juego_batalla;
    private int tamano_terreno_arreglo;
    private int mitad_alto_pantalla_batalla;
    
    private int cantidadTanqueJugador1;
    private int cantidadTanqueJugador2;
    private List<int[]> listaPosicionesTanques;
    private boolean mapa_correcto;
    private ArrayList<Tanque> nuevasPosicionesTanques;
    
  
    /**
     * Contructor Mapa, utiliza los parámetros:
     * @param tamano_pantalla_ancho ancho de la ventana
     * @param pantalla_alto_juego_batalla el alto que se utiliza para la animacion.
     * Ejemplo: se utiliza 500px para la animacion y se dejan 100px para los controles, lo que da un alto total (ventana) de 600
     */
	public Mapa(int tamano_pantalla_ancho, int pantalla_alto_juego_batalla, int cantidadTanqueJugador1,
			int cantidadTanqueJugador2) {
		this.tamano_pantalla_ancho=tamano_pantalla_ancho;
		this.pantalla_alto_juego_batalla=pantalla_alto_juego_batalla;
		this.cantidadTanqueJugador1=cantidadTanqueJugador1;
		this.cantidadTanqueJugador2=cantidadTanqueJugador2;
		
		mitad_alto_pantalla_batalla=(int)Math.floorDiv(pantalla_alto_juego_batalla, 2);
		tamano_terreno_arreglo=tamano_pantalla_ancho+1;
		mapa_correcto=false;
		
		
	}
	
	/**
	 * Método que permite generar el mapa y las ubicaciones de los tanques, primero se obtienen los parámetros para usarlos en el 
	 * algoritmo DPM, tras obtener el arreglo obtenido por el algoritmo, se determinan los puntos para ubicar a los tanques, por 
	 * último un ciclo While con una condición booleana, que llamará a un método para comprobar si el mapa es correcto para comenzar a jugar.
	 * En caso que el mapa este incorrecto, se generará otro mapa (terreno y ubicaciones del tanque), en caso de que esté correcto, 
	 * se romperá el ciclo y podrá dibujar en el panel Panel_Batalla.  
	 */
	public void generar_mapa() {

			terreno_arreglo=new int [tamano_pantalla_ancho+1];
			paresOrdenadosTanques=new HashSet<int[]>();
			listaPosicionesTanques= new ArrayList<int[]>();
	
			for (double[]datos_algoritmo:calcular_datos_paraAlgoritmo()) {
				algoritmo_desplazamiento_punto_medio(datos_algoritmo[0], datos_algoritmo[1],datos_algoritmo[2],datos_algoritmo[3]);
			}	

			for (int i=0; i<terreno_arreglo.length-1; i++) {
				puntos_ubicar_tanques(i,mitad_alto_pantalla_batalla-(terreno_arreglo[i]));	
			}
		
			listarUbicacionesTanques(paresOrdenadosTanques);
		
		
			while(!mapa_correcto) {
				comprobar_mapa();
			}
	}

	/**
	 * Algoritmo DPM: Método que genera un arreglo con números que será utilizado para la dibujar el terreno. Se utilizan índices
	 * que corresponden a un valor al lado izquierdo y otro al derecho, luego se calcula un valor que se encuentra al medio de los dos 
	 * (índice medio), que de forma random se determinará si es positivo (que representaría un monte) o negativo (que representaría un valle),
	 * esto se determina mediante un valor llamado cambio que como se describió se realiza con un random, multiplicado por el desplazamiento
	 * que corresponde al valor de la altura en la pantalla (en este caso está en la mitad de la pantalla). La aspereza coorresponde a un factor 
	 * que se multiplica al desplazamiento, el cual puede aumentar el número de montes o valles, en otras palabras, afecta a 
	 * la rugosidad del terreno.
	 * 
	 * Esto se realiza recursivamente hasta que el indice izquierdo + 1 sea igual al indice derecho o cuando se supere indice el tamaño.  
	 * 
	 * @param indiceIzquierdo valor de un punto x que se encuentra al lado izquierdo 
	 * @param indiceDerecho valor de un punto x que se encuentra al lado derecho 
	 * @param desplazamiento valor que corresponde a la altura que se serán ubicados los puntos (eje y)
	 * @param aspereza valor que corresponde al factor de rugosidad del terreno
	 */
	public void algoritmo_desplazamiento_punto_medio(double indiceIzquierdo, double indiceDerecho, double desplazamiento, double aspereza) {
		if((indiceIzquierdo + 1) == indiceDerecho) {
			return;
		}
		double indiceMedio = Math.floor((indiceIzquierdo + indiceDerecho) / 2);
		if (indiceMedio>tamano_terreno_arreglo) {
			return;
		}
		double cambio = (Math.random() * 2 - 1) * desplazamiento; //random para generar numeros de [0,2[ restando 1, resultando numeros positivos y negativos (subidas y bajadas)
		terreno_arreglo[(int) indiceMedio] = (int)Math.floor((terreno_arreglo[(int)indiceIzquierdo] + terreno_arreglo[(int)indiceDerecho]) / 2 + cambio);
		desplazamiento = desplazamiento * aspereza;
		algoritmo_desplazamiento_punto_medio(indiceIzquierdo, indiceMedio, desplazamiento, aspereza);
		algoritmo_desplazamiento_punto_medio(indiceMedio, indiceDerecho,desplazamiento, aspereza);
	}
	
	/**
	 * Método que guarda posiciones en un conjunto para ubicar los tanques a traves de los valles generados, en un rango en que los tanques
	 * puedan aparecer en la pantalla
	 * 
	 * @param x posición con coordenada x
 	 * @param y posición con coordenada y
	 */
	public void puntos_ubicar_tanques(int x,int y) {
		int[] par_ordenado=new int[2];

		if (x>=45 && x<tamano_pantalla_ancho-35) {
			par_ordenado[0]=x;
			par_ordenado[1]=y;
			paresOrdenadosTanques.add(par_ordenado);
		}
	}
	
	public void listarUbicacionesTanques(Set<int[]>conjunto_ubicar_tanques) {
		int contarTanques=0;
		List<int[]> listaParaUbicarTanques = new ArrayList<int[]>(conjunto_ubicar_tanques);
		Random random = new Random();
		int randomIndice= 0;
		int posicionTanque[]=new int[2];
		while (contarTanques < (cantidadTanqueJugador1 + cantidadTanqueJugador2)) {
			randomIndice=random.nextInt(listaParaUbicarTanques.size());
			posicionTanque=listaParaUbicarTanques.get(randomIndice);
			listaPosicionesTanques.add(posicionTanque);
			contarTanques++;
		}
	}	
	
	/**
	 *Genera una lista con los parámetros para el algoritmo DPM, primero se divide en cuatro secciones, con un tamaño que varía
	 *segun el ancho de la pantalla, luego se realiza un ciclo en que se asigna los parametros del ancho (indices izq y der) 
	 *de las secciones, la mitad de la pantalla y los valores de la rugosidad del terreno (forma la variedad de cumbres y acantilados 
	 *cuando de dibuja), por ultimo se guardan en arreglos que son almacenados en una lista.
	 *
	 * @return datos_algoritmo, lista de arreglos con los parametros para el algoritmo DPM.
	 */
	public ArrayList<double[]> calcular_datos_paraAlgoritmo(){
		ArrayList<double[]> datos_algoritmo = new ArrayList<double[]>();
		int divisionPantalla=0;
		divisionPantalla=new Random().nextInt(6)+5;
		
		int seccion_pantalla=(int)Math.floorDiv(tamano_pantalla_ancho, divisionPantalla);
		double coeficienteAspereza=0;
		while(!(coeficienteAspereza>=0.25 && coeficienteAspereza<=0.4)) {
			coeficienteAspereza=Math.random();
		}
		
		for (int i=0; i<tamano_pantalla_ancho; i=seccion_pantalla+i) {
			double indices_desplazamiento[]=new double [4];
			indices_desplazamiento[0]=i;
			indices_desplazamiento[1]=i+seccion_pantalla;
			indices_desplazamiento[2]=(int)Math.floorDiv(pantalla_alto_juego_batalla, 3);
			indices_desplazamiento[3]=coeficienteAspereza;
			datos_algoritmo.add(indices_desplazamiento);
		}
		return datos_algoritmo;
	}
	
	/**
	 * Se aplica la formula clasica para calcular la distancia entre dos puntos vista en cursos de geometria.
	 * 
	 * @param y2 coordenada (y) del segundo punto.
	 * @param y1 coordenada (y) del primer punto.
	 * @param x2 coordenada (x) del segundo punto.
	 * @param x1 coordenada (x) del primer punto.
	 * 
	 * @return valor del resultado de la formula. 
	 */
	public int calcularDistanciaEntrePuntos(int y2, int y1, int x2, int x1) {
		double resultado=0;
		resultado=Math.sqrt(((y2-y1)*(y2-y1))+((x2-x1)*(x2-x1)));
		return (int)resultado;
	}
	
	public Set<int[]> getTerreno() {
		return terreno_completo;
	}
	
	/**
	 * Metodo para crear el terreno, se realiza mediante puntos (x,y), a partir del arreglo del algoritmo de 
	 * desplazamiento de punto medio, que genera un arreglo con numeros positivos y negativos que son usados para formar la coordenada (y)
	 * sumandolos con el número cuyo valor es la mitad de la pantalla de animacion de la batalla, mientras que la coordenada en (x) es
	 * generado por un arreglo 0 al tamano que tiene el ancho de la pantalla, esto solo formará una una linea con altos y bajos a la mitad de 
	 * la pantalla, por lo tanto, para formar todo el terreno hacia abajo, se utiliza un ciclo for que por cada 10 espacios genere otra linea o 
	 * de puntos hasta llegar al limite de la pantalla de animacion. Es de cada 10 en 10 porque el tamaño en que se dibuja el bloque 
	 * (cuadrado) en  pantalla es de 10x10, ademas es mucho mejor, puesto que se verifica la informacion exacta a 
	 * la hora de verificar las colisiones. Luego se guardan en un conjunto set llamado terreno_completo y genera un copia (terreno_copia).
	 * 
	 * En caso de que exista una colision en el terreno, se busca si el punto de colision existe en el terreno, luego se copia una variable 
	 * auxiliar, despues punto funcionara como punto medio y se buscaran si puntos a la izquierda, derecha, arriba y abajo de el para simular
	 * la expansion al explotar el proyectil en el terreno, luego estos puntos seran borrados de la copia del terreno (no se puede eliminar
	 * directamente del terreno original). Finalmente, se limpia el conjunto con los datos del terreno original y
	 * los datos de la copia son agregadas al terreno original.
	 * 
	 * @param colision booleano para verificar si existe una colision en el terreno.
	 * @param coordenadas_colision arreglo que contiene el punto (x,y) del impacto.
	 * @param alto_bloque_terreno int que contiene el valor del alto del cuadrado del terreno (bloque).
	 */
	public void crear_terreno(boolean colision, int[] coordenadas_colision, 
			int alto_bloque_terreno, int dano_proyectil, ArrayList<Tanque> tanques, int jugador) {
		if (colision) {
			int[] par_ordenado_aux=new int[2];
			
			for(int[] par_ordenado: terreno_completo) {
				if(par_ordenado[0]==coordenadas_colision[0] && par_ordenado[1]==coordenadas_colision[1]) {
					par_ordenado_aux=par_ordenado;					
					break;
				}
			}
			int expansion_atras_x=0;
			int expansion_adelante_x=0;
			int profundidad_y=0;
			int altura_y=0;
				
			for (int i=0; i<30;i++) {
				expansion_atras_x=par_ordenado_aux[0]-i;
				expansion_adelante_x=par_ordenado_aux[0]+i;
				
				for (int j=0; j<dano_proyectil;j++) {
					profundidad_y=par_ordenado_aux[1]+j;
					altura_y=par_ordenado_aux[1]-j;
					for(int[] par_ordenado: terreno_completo) {
						if((par_ordenado[0]==expansion_atras_x || par_ordenado[0]==expansion_adelante_x) && 
								(par_ordenado[1]==profundidad_y || par_ordenado[1]==altura_y)) {
							cambiarPosicionTanque(jugador, par_ordenado, tanques,dano_proyectil);
							terreno_copia.remove(par_ordenado);
						}
					}
				}
			}
			terreno_completo.clear();
			terreno_completo.addAll(terreno_copia);
		}	
		else {
			terreno_completo=new HashSet<int[]>();
			ArrayList<int[]>borde_terreno=new ArrayList<int[]>();
			for (int i=0; i<terreno_arreglo.length-1; i++) {
				int[] par_borde_terreno=new int[2];
				par_borde_terreno[0]=i;
				par_borde_terreno[1]=mitad_alto_pantalla_batalla-terreno_arreglo[i];
				borde_terreno.add(par_borde_terreno);
			}
			for (int i=0; i<pantalla_alto_juego_batalla; i+=10) {
				for (int j=0; j<borde_terreno.size();j++) {
					int[] par_terreno_completo=new int[2];
					par_terreno_completo[0]=borde_terreno.get(j)[0];
					par_terreno_completo[1]=borde_terreno.get(j)[1]+i;
					terreno_completo.add(par_terreno_completo);
				}
			}
			terreno_copia=new HashSet<int[]>(terreno_completo);
			nuevasPosicionesTanques=tanques;
		}
	}
	public ArrayList<int[]> terrenoPrueba() {
		ArrayList<int[]>terrenoPrueba=new ArrayList<int[]>();
		ArrayList<int[]>borde_terreno=new ArrayList<int[]>();
		for (int i=0; i<terreno_arreglo.length-1; i++) {
			int[] par_borde_terreno=new int[2];
			par_borde_terreno[0]=i;
			par_borde_terreno[1]=mitad_alto_pantalla_batalla-terreno_arreglo[i];
			borde_terreno.add(par_borde_terreno);
		}
		for (int i=0; i<pantalla_alto_juego_batalla; i+=10) {
			for (int j=0; j<borde_terreno.size();j++) {
				int[] par_terreno_completo=new int[2];
				par_terreno_completo[0]=borde_terreno.get(j)[0];
				par_terreno_completo[1]=borde_terreno.get(j)[1]+i;
				terrenoPrueba.add(par_terreno_completo);
			}
		}
		return terrenoPrueba;
		
		
	}
	
	
	public void cambiarPosicionTanque(int jugador, int[] puntoColision, ArrayList<Tanque> tanques, int dano_proyectil) {
		Rectangle puntoDelimitador = new Rectangle(puntoColision[0],puntoColision[1],10,10);
		nuevasPosicionesTanques = new ArrayList<Tanque>();
		boolean cambiarPosicion=false;
		ArrayList<Tanque> TanquesCopia= new ArrayList<Tanque>(tanques);
		Tanque tanqueNuevaUbicacion= null;
		if(jugador==1) {
			for (Tanque tanque: tanques) {
				if(tanque.getTanquePerteneceJugador()==2 && 
				tanque.getTanqueDelimitadorTerreno().intersects(puntoDelimitador)) {
					cambiarPosicion=true;
					tanqueNuevaUbicacion=tanque;
					tanqueNuevaUbicacion.cambioUbicacionTanque(tanque.getPos_y_tanque()+dano_proyectil);
					TanquesCopia.remove(tanque);
					TanquesCopia.add(tanqueNuevaUbicacion);
					nuevasPosicionesTanques.addAll(TanquesCopia);
					break;
				}
			}
		}
		if(jugador==2) {
			for (Tanque tanque: tanques) {
				if(tanque.getTanquePerteneceJugador()==1 && 
				tanque.getTanqueDelimitadorTerreno().intersects(puntoDelimitador)) {
					cambiarPosicion=true;
					tanqueNuevaUbicacion=tanque;
					tanqueNuevaUbicacion.cambioUbicacionTanque(tanque.getPos_y_tanque()+dano_proyectil);
					TanquesCopia.remove(tanque);
					TanquesCopia.add(tanqueNuevaUbicacion);
					nuevasPosicionesTanques.addAll(TanquesCopia);
					break;
				}
			}
		}
		if (!cambiarPosicion) {
			nuevasPosicionesTanques.addAll(TanquesCopia);
		}
	}
	
	public boolean tanquesSeparados(List<int[]> listaPosicionesTanquesProbar) {
		boolean tanquesCorrectos=true;
		for (int[] posicionUnTanque : listaPosicionesTanquesProbar){
			Rectangle pruebaTanque =new Rectangle(posicionUnTanque[0]-63,posicionUnTanque[1]-20,63, 20);
				 for(int[] posicionOtroTanque : listaPosicionesTanquesProbar) {
					 Rectangle pruebaOtroTanque =new Rectangle(posicionOtroTanque[0]-63,posicionOtroTanque[1]-20,63, 20);
					 if( (pruebaTanque.intersects(pruebaOtroTanque) && posicionUnTanque[0]!=posicionOtroTanque[0]
							 && posicionUnTanque[1]!=posicionOtroTanque[1])) {
						 tanquesCorrectos=false;
						 break;
					 }
				 }
				 if (!tanquesCorrectos) {
						break;
				 }
			}
		return tanquesCorrectos;
	}
	
	public boolean tanqueEnterrado(int[] posicion){
		boolean tanqueCorrecto=true;
		Rectangle pruebaTanque =new Rectangle(posicion[0]-50,posicion[1]-28,53, 30);
		for(int[] bloque: terrenoPrueba()) {
			Rectangle pruebaBloque =new Rectangle(bloque[0],bloque[1],10, 10);
			if(pruebaTanque.intersects(pruebaBloque)){
				tanqueCorrecto=false;
				break;
			}
		}
		return tanqueCorrecto;
	}
	
	public boolean tanquesValidos() {
		boolean tanquesCorrectos=false;
		if(tanquesSeparados(listaPosicionesTanques)) {
			ArrayList<int[]> listaPosicionesTanquesOK = new ArrayList<int[]>();
			ArrayList<int[]> listaPosicionesTanquesACorregir = new ArrayList<int[]>();
	
			for(int[] posicionTanque : listaPosicionesTanques){
				if(!tanqueEnterrado(posicionTanque)) {
					listaPosicionesTanquesACorregir.add(posicionTanque);
				}
				else {
					listaPosicionesTanquesOK.add(posicionTanque);
				}
			}
			for(int[] posicionTanque : listaPosicionesTanquesACorregir) {
				int[] nuevaPosicion=new int[2];
				int nuevaPosX=posicionTanque[0];
				int nuevaPosY=posicionTanque[1];
				int[] posicionActual=posicionTanque;
				while(!tanqueEnterrado(posicionActual)) {
					 nuevaPosY-=1;
					 nuevaPosicion[0]=nuevaPosX;
					 nuevaPosicion[1]=nuevaPosY;
					 posicionActual=nuevaPosicion;
				}
				
				posicionActual[1]=posicionActual[1]+18;
				listaPosicionesTanquesOK.add(posicionActual);
			}
			
			if(tanquesSeparados(listaPosicionesTanquesOK)) {
				listaPosicionesTanques.clear();
				listaPosicionesTanques.addAll(listaPosicionesTanquesOK);
				tanquesCorrectos=true;
			}
			else {
				tanquesCorrectos=false;
			}
			
		}
		else {
			tanquesCorrectos=false;
		}
		return tanquesCorrectos;
	}
	
	public void comprobar_mapa() {
		try {
			if ( (!tanquesValidos()) && (!mapa_correcto)) {
				generar_mapa();
			}
			else {
				mapa_correcto=true;
			
			}
		}
		catch(StackOverflowError ex) {
			generar_mapa();
		}
		
	}
	public List<int[]> getListaPosicionesTanques() {
		return listaPosicionesTanques;
	}
	
	public Set<int[]> getParesOrdenadosTanques() {
		return paresOrdenadosTanques;
	}

	public ArrayList<Tanque> getTanques() {
		return nuevasPosicionesTanques;
	}

}
