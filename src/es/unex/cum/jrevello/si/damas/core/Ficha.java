package es.unex.cum.jrevello.si.damas.core;

/**
 * Objeto que representa a un ficha en juego.
 * @author Jaime Revello
 *
 *
 */
public class Ficha {
//ATRIBUTOS
	
	/**
	 * Determina si la ficha es una Dama
	 */
	private boolean esDama;
	
	/**
	 * La casilla en la que se encuentra
	 */
	private Casilla casilla;
	
	/**
	 * El "Bando" de la ficha
	 */
	private Colores color;
	
	/**
	 * <b>True</b> si la ficha ha comido
	 */
	private boolean haComido;
	
//CONSTRUCTORES
	
	/**
	 * Constructor por defecto. No debería de usarse
	 */
	public Ficha() {
		this.color=Colores.ROJA;
		this.casilla = new Casilla();
		this.esDama=false;
	}
	
	/**
	 * Constructor parametrizado
	 * @param in_color El color de la ficha
	 * @param in_casilla La casilla en la que se encuentra
	 * @param in_esDama <b>True</b> si es Dama
	 * 
	 * @see Casilla
	 */
	public Ficha(Colores in_color, Casilla in_casilla, boolean in_esDama){
		this.casilla = in_casilla;
		this.color = in_color;
		this.esDama = in_esDama;
		in_casilla.setFicha(this);
	}
	
	/**
	 * Constructor por copia auxiliar. Se le pasan or parámetros la ficha a copiar 
	 * y la nueva casilla.
	 * El color, estado de dama, estado de comida, etc lo copia todo de la ficha pasada por
	 * parámetro.
	 * 
	 * @param in_f Ficha a copiar
	 * @param c Nueva casilla
	 */
	public Ficha(Ficha in_f, Casilla c) {
		this.casilla = c;
		this.color = in_f.getColor();
		this.esDama = in_f.isDama();
		this.haComido = in_f.haComido();
	}
	
//MÉTODOS


	//GETTERS & SETTERS
	/**
	 * @return the esDama
	 */
	public boolean isDama() {
		return esDama;
	}

	/**
	 * @param esDama the esDama to set
	 */
	public void setEsDama(boolean esDama) {
		this.esDama = esDama;
	}

	/**
	 * @return the casilla
	 */
	public Casilla getCasilla() {
		return casilla;
	}

	/**
	 * @param casilla the casilla to set
	 */
	public void setCasilla(Casilla casilla) {
		this.casilla = casilla;

	}

	/**
	 * @return the color
	 */
	public Colores getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Colores color) {
		this.color = color;
	}

	/**
	 * @return La haComido del objeto.
	 */
	public boolean haComido() {
		return haComido;
	}

	/**
	 * @param haComido La haComido a guardar.
	 */
	public void setHaComido(boolean haComido) {
		this.haComido = haComido;
	}

	/**
	 * @return La esDama del objeto.
	 */
	public boolean isEsDama() {
		return esDama;
	}
	
	

}
