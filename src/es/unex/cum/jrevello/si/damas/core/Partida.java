package es.unex.cum.jrevello.si.damas.core;

/**
 * Este objeto llevará los aspectos más básicos de la partida.
 * Deberá ser invocada por el MainFrame.
 * 
 * @author Jaime Revello
 *
 */
public class Partida {

//ATRIBUTOS
	
	/**
	 * Contador de turnos
	 */
	private int n_turno;
	
	/**
	 * Modo seleccionado para la partida
	 * @see Modos
	 */
	private Modos modo;
	
	/**
	 * A quién pertenece el turno actual
	 */
	private Colores turno_actual;
	
	/**
	 * IA que mueve las fichas negras.
	 */
	private IA iAN;
	
	/**
	 * IA que mueve las fichas rojas.
	 */
	private IA iAR;
	
	/**
	 * Profundidad máxima a la que van a buscar los algoritmos.
	 */
	private int limite;
	
	

//CONSTRUCTORES
	
	/**
	 * Constructor por defecto
	 */
	public Partida() {
		this.n_turno =0;
		this.modo = Modos.HvH;
		this.turno_actual = Colores.ROJA;
		this.limite = 5;
		
	}
	/**
	 * Constructor con modo
	 * @param in_modo El modo de juego
	 */
	public Partida(Modos in_modo){
		this.n_turno =0;
		this.modo=in_modo;
		this.turno_actual = Colores.ROJA;
		this.limite = 5;
		
	}
	
	public Partida(Modos in_modo, int prof){
		this.n_turno =0;
		this.modo=in_modo;
		this.turno_actual = Colores.ROJA;
		this.limite = prof;
		
	}


//MÉTODOS
	
	/**
	 * Método para avanzar de turno
	 */
	public void cambiaTurno() {
		this.n_turno++;
		if(this.turno_actual == Colores.ROJA) this.turno_actual = Colores.NEGRA;
		else this.turno_actual = Colores.ROJA;
		
	}
	
	/**
	 * Inicializa la IA
	 */
	public void iniciaIA(){
		if(this.modo == Modos.HvM) iAN = new IA(Colores.NEGRA, limite);
		if(this.modo == Modos.MvM){
			iAN = new IA(Colores.NEGRA, limite);
			iAR = new IA(Colores.ROJA, limite);
		}
	}
	
	/**
	 * Comprueba si el turno es de la IA
	 * @return <b>True</b> si el turno es de la IA.
	 */
	public boolean turnoIA() {
		boolean res = false;
		if((modo == Modos.MvM)||((modo == Modos.HvM) && (turno_actual == Colores.NEGRA))) res = true;
		return res;
	}
	

//GETTERS & SETTERS

	/**
	 * @return La n_turno del objeto.
	 */
	public int getN_turno() {
		return n_turno;
	}


	/**
	 * @param n_turno La n_turno a guardar.
	 */
	public void setN_turno(int n_turno) {
		this.n_turno = n_turno;
	}


	/**
	 * @return La modo del objeto.
	 */
	public Modos getModo() {
		return modo;
	}


	/**
	 * @param modo La modo a guardar.
	 */
	public void setModo(Modos modo) {
		this.modo = modo;
	}


	/**
	 * @return La turno_actual del objeto.
	 */
	public Colores getTurno_actual() {
		return turno_actual;
	}


	/**
	 * @param turno_actual La turno_actual a guardar.
	 */
	public void setTurno_actual(Colores turno_actual) {
		this.turno_actual = turno_actual;
	}
	/**
	 * @return La iAN del objeto.
	 */
	public IA getiAN() {
		return iAN;
	}
	/**
	 * @param iAN La iAN a guardar.
	 */
	public void setiAN(IA iAN) {
		this.iAN = iAN;
	}
	/**
	 * @return La profundidad del objeto.
	 */
	public int getProfundidad() {
		return limite;
	}
	/**
	 * @param profundidad La profundidad a guardar.
	 */
	public void setProfundidad(int profundidad) {
		this.limite = profundidad;
	}
	/**
	 * @return La iAR del objeto.
	 */
	public IA getiAR() {
		return iAR;
	}
	/**
	 * @param iAR La iAR a guardar.
	 */
	public void setiAR(IA iAR) {
		this.iAR = iAR;
	}

	

}
