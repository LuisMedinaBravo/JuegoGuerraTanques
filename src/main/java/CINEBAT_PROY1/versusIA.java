
package CINEBAT_PROY1;


import java.util.ArrayList;
import java.util.Random;

public class versusIA {
    
	String[] direccion;
	int numeroTanques;
	int jugador;
	ArrayList<Tanque> tanquesUsarIA;
	
	public versusIA(int jugador, ArrayList<Tanque> tanques) {
            
		this.jugador=jugador;
		direccion= new String[2];
		cantidadTanquesUsar(tanques);
	}
	
	public void cantidadTanquesUsar(ArrayList<Tanque> tanques) {
		tanquesUsarIA = new ArrayList<Tanque>();
		numeroTanques=0;
		for (Tanque tanque: tanques) {
			if(tanque.getTanquePerteneceJugador()==2) {
				tanquesUsarIA.add(tanque);
				numeroTanques++;
			}
		}
	}
	
	public Tanque escogerTanque() {
		Tanque tanqueIA=null;
		Random random = new Random();
		int randomIndice= 0;
		randomIndice=random.nextInt(numeroTanques);
		tanqueIA=tanquesUsarIA.get(randomIndice);
		return tanqueIA;
	}
	
	public String calcularVelocidad() {
		int velocidad= new Random().nextInt(101);
		String velocidadString= Integer.toString(velocidad);
		return velocidadString;
	}
	public String calcularAngulo() {
		int angulo=new Random().nextInt(71)+20;
		String anguloString=Integer.toString(angulo);
		return anguloString;
	}
	public String retornarProyectil() {
		Random random = new Random();
		int randomIndice= 0;
		randomIndice=random.nextInt(3);
		String[] proyectiles= {"105mm", "perforante","60mm"};
		String proyectil=proyectiles[randomIndice];
		return proyectil;
	}
	public String retornarDireccion() {
		Random random = new Random();
		int randomIndice= 0;
		randomIndice=random.nextInt(2);
		String[] direcciones= {"izquierda", "derecha"};
		String direccion=direcciones[randomIndice];
		return direccion;
	}
	public void actualizarTanques(ArrayList<Tanque> tanques) {
		cantidadTanquesUsar(tanques);
	}
}
