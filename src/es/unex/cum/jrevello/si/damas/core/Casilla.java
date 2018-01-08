package es.unex.cum.jrevello.si.damas.core;

import es.unex.cum.jrevello.si.damas.interfaz.CasillaGrafica;

/**
 * Representación de una casilla.
 * @author Jaime Revello
 *
 */
public class Casilla {

//ATRIBUTOS
	
	/**
	 * Posición en las coordenadas X.
	 */
	private int coorX;
	
	/**
	 * Posición en las coordenadas Y.
	 */
	private int coorY;
	
	/**
	 * Ficha colocada en la casilla (si la hay).
	 */
	private Ficha ficha;
	
	/**
	 * Puntero a la representación gráfica de la casilla.
	 */
	private CasillaGrafica cGrafica;
	
//CONSTRUCTORES
	/**
	 * Constructor por defecto
	 */
	public Casilla() {
		this.coorX=0;
		this.coorY=0;
		this.ficha=null;
	}
	
	/**
	 * Constructor con las coordenadas
	 * @param x Coordenada X (0-7)
	 * @param y Coordenada Y (0-7)
	 */
	public Casilla(int x, int y){
		this.coorX=x;
		this.coorY=y;
		this.ficha=null;
	}
	
	/**
	 * Constructor con ficha
	 * @param x Coordenada X (0-7)
	 * @param y Coordenada Y (0-7)
	 * @param f Ficha colocada en la casilla.
	 */
	public Casilla(int x, int y, Ficha f){
		this.coorX=x;
		this.coorY=y;
		this.ficha=f;
		f.setCasilla(this);
	}
	
	/**
	 * Constructor con casilla gráfica
	 * @param x Coordenada X (0-7)
	 * @param y Coordenada Y (0-7)
	 * @param in_cg Casila gráfica asignada a esta casilla
	 */
	public Casilla(int x, int y, CasillaGrafica in_cg) {
		this.coorX=x;
		this.coorY=y;
		this.ficha=null;
		this.cGrafica = in_cg;
		in_cg.setCasilla(this);
	}
	
	/**
	 * Constructor por copia
	 * @param in_c Casilla a copiar
	 */
	public Casilla(Casilla in_c){
		this.coorX = in_c.getCoorX();
		this.coorY = in_c.getCoorY();
		this.cGrafica = in_c.getcGrafica();
		if(!in_c.estaVacio()) this.ficha = new Ficha(in_c.getFicha(), this);
		else this.ficha = null;


	}
	
//MÉTODOS
	

	/**
	 * Comprueba si hay alguna ficha en esta casilla
	 * @return <b>True</b> si existe alguna ficha.
	 */
	public boolean estaVacio(){
		boolean res=false;		
		if(this.ficha == null) res=true;		
		return res;
	}
	
	/**
	 * Comprueba si la casilla dada está en la misma posición que esta casilla.
	 * Para ello, simplemente compara las coordenadas.
	 * @param casilla Casilla a comprobar
	 * @return <b>True</b> si los dos casillas están en ls mismas coordenadas.
	 */
	public boolean mismaCasilla(Casilla casilla) {
		boolean res = false;
		if(this.coorX == casilla.getCoorX() && this.coorY == casilla.getCoorY()) res = true;
		return res;
	}
	
	/**
	 * Método sobrecargado para generar una representación textual de la casilla.
	 * [Posición X, Posición Y, (Y/N) según tenga una ficha o no]
	 */
	@Override
	public String toString(){
		String s_ficha = "N";
		if(!this.estaVacio()) s_ficha ="Y";
		return("["+this.coorX+", "+this.coorY+","+s_ficha+"]");
	}
	
	
//GETTERS AND SETTERS

	/**
	 * @return the coorX
	 */
	public int getCoorX() {
		return coorX;
	}

	/**
	 * @param coorX the coorX to set
	 */
	public void setCoorX(int coorX) {
		this.coorX = coorX;
	}

	/**
	 * @return the coorY
	 */
	public int getCoorY() {
		return coorY;
	}

	/**
	 * @param coorY the coorY to set
	 */
	public void setCoorY(int coorY) {
		this.coorY = coorY;
	}

	/**
	 * @return the ficha
	 */
	public Ficha getFicha() {
		return ficha;
	}

	/**
	 * @param ficha the ficha to set
	 */
	public void setFicha(Ficha ficha) {
		this.ficha = ficha;
		if(ficha != null)ficha.setCasilla(this);
	}

	/**
	 * @return La cGrafica del objeto.
	 */
	public CasillaGrafica getcGrafica() {
		return cGrafica;
	}

	/**
	 * @param cGrafica La cGrafica a guardar.
	 */
	public void setcGrafica(CasillaGrafica cGrafica) {
		this.cGrafica = cGrafica;
	}

	

}
