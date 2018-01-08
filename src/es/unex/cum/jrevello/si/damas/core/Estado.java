package es.unex.cum.jrevello.si.damas.core;

import java.util.ArrayList;
import java.util.Iterator;

import es.unex.cum.jrevello.si.damas.excepciones.ExcepcionMovimiento;
import es.unex.cum.jrevello.si.damas.interfaz.MainFrame;
import es.unex.cum.jrevello.si.damas.tad.NodoMH;

/**
 * Representación de un estado.
 * Contiene la matriz de casillas (Con la posición de las fichas)
 * 
 * @author Jaime Revello
 *
 */
public class Estado {

	// ATRIBUTOS
	/**
	 * Representación del tablero. Por defecto tiene 8x8 casillas, se numeran de
	 * abajo a arriba y de izquierda a derecha tiendo la fichan blanca
	 * <b>abajo</b>.
	 * 
	 * <b>Recordar que esta numeración comienza en 0</b>
	 */
	private Casilla[][] tablero;

	/**
	 * Puntero al MainPrincipal. Lo usamos para comprobar la situación de la
	 * partida.
	 */
	private MainFrame mainf;

	// CONSTRUCTORES

	/**
	 * Contructor por defecto.
	 * Reserva la memoria para la matriz.
	 */
	public Estado() {
		generaTablero();

	}
	/**
	 * Similar al contructor por defecto, pero con este contructor
	 * podemos especificarle el frame en el que se proyectará el estado.
	 * @param in_main El frame al que se va a asignar al estado.
	 * @see MainFrame
	 */
	public Estado(MainFrame in_main) {
		generaTablero();
		this.mainf = in_main;
	}

	/**
	 * Constructor de copia. Crea una copia del estado que se le pasa por
	 * parámetro.
	 * 
	 * @param estadoFinal
	 *            Estado a copiar
	 */
	public Estado(Estado estadoFinal) {
		generaTablero();
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				this.tablero[i][j] = new Casilla(estadoFinal.getTablero()[i][j]);
			}
		}
		this.mainf = estadoFinal.getMainf();
	}

	// MÉTODOS

	/**
	 * Método auxiliar para recuperar una casilla
	 * PRECONDICIÓN: Se introducen unas coordenadas válidas.
	 * @param x Coordenada X de la casilla buscada
	 * @param y Coordenada Y de la casilla buscada
	 * @return La casilla en dicha posición
	 */
	public Casilla dameCasilla(int x, int y) {
		return tablero[x][y];
	}

	/**
	 * Método para obtener todos los posibles movimientos de una ficha en una
	 * casilla
	 * 
	 * @param in_cas
	 *            Casilla donde se encuentra la ficha a mover.
	 * @return Un Array con todas las casilla a las que se puede mover con orden
	 *         de prioridad.
	 * @throws ExcepcionMovimiento En caso de error al calcular el movimiento.
	 */
	public ArrayList<Movimiento> dameMovimientos(Casilla in_cas) throws ExcepcionMovimiento {
		ArrayList<Movimiento> res = new ArrayList<Movimiento>();
		Ficha fichaActual;
		boolean esDama;
		Casilla cAux = null;

		// Primero comprobamos si hay una ficha en la casilla dada.
		if (in_cas.estaVacio())
			throw new ExcepcionMovimiento("La casilla indicada está vacia");

		// Cogemos la información.
		fichaActual = in_cas.getFicha();
		esDama = fichaActual.isDama();

		// Si la ficha NO ES dama, limitamos los movimientos.
		if (!esDama) {
			/*
			 * 
			 * SI ES BLANCA
			 * 
			 */
			if (fichaActual.getColor() == Colores.ROJA) {// Si es blanca,
															// movemos en
															// sentido POSITIVO.
				// INICIO DIAGONAL IZQUIERDA
				if (in_cas.getCoorX() > 0 && in_cas.getCoorY() < 7) { // Con
																		// esto
																		// evitamos
																		// un
																		// ArrayIndexOutOfBounds
					cAux = this.dameCasilla(in_cas.getCoorX() - 1, in_cas.getCoorY() + 1); // Diagonal
																							// izquierda

					// Si está vacía, la añadimos a la lista
					if (cAux != null && cAux.estaVacio())
						res.add(new Movimiento(in_cas, cAux, this));
					else {
						/*
						 * Si no está vacía, se abren dos nuevas opciones: Que
						 * haya una ficha amiga o una ficha enemiga. Si la ficha
						 * es amiga, hemos acabado por aquí. Si es enemiga,
						 * comprobamos si se puede comer.
						 */
						if (cAux != null && cAux.getFicha().getColor() == Colores.NEGRA) {
							if (!cAux.estaVacio() && (in_cas.getCoorX() > 1) && (in_cas.getCoorY() < 6)
									&& tablero[in_cas.getCoorX() - 2][in_cas.getCoorY() + 2].estaVacio()) {
								// Si entramos aquí es que puede hacer un
								// movimiento
								// de comer.
								Movimiento mAux = new Movimiento(in_cas, cAux, this);
								res.add(mAux);
							} // END IF PUEDE COMER
						} // END IF ES NEGRA
					} // END ELSE NO ESTA VACIA
				} // END DE EVITAR EXCEPCION
					// Primero evitamos la posible excepcion
				if (in_cas.getCoorX() < 7 && in_cas.getCoorY() < 7) {
					cAux = this.dameCasilla(in_cas.getCoorX() + 1, in_cas.getCoorY() + 1);
					if (cAux.estaVacio())
						res.add(new Movimiento(in_cas, cAux, this));
					else {
						/*
						 * De nuevo tenemos dos opciones
						 */
						if (cAux != null && cAux.getFicha().getColor() == Colores.NEGRA) {
							if (!cAux.estaVacio() && (in_cas.getCoorX() < 6) && (in_cas.getCoorY() < 6)
									&& tablero[in_cas.getCoorX() + 2][in_cas.getCoorY() + 2].estaVacio()) {
								// Si entramos aquí es que puede hacer un
								// movimiento
								// de comer.
								Movimiento mAux = new Movimiento(in_cas, cAux, this);
								res.add(mAux);
							} // END IF PUEDE COMER
						} // END IF ES NEGRA
					} // END ELSE ESTA VACIO
				} // END EVITAR EXCEPCION
			} // END IF BLANCA
			else {
				/*
				 * 
				 * Si es negra
				 * 
				 */
				// INICIO DIAGONAL INFERIOR IZQUIERDA
				if (in_cas.getCoorX() > 0 && in_cas.getCoorY() > 0) { // Con
																		// esto
																		// evitamos
																		// un
																		// ArrayIndexOutOfBounds
					cAux = this.dameCasilla(in_cas.getCoorX() - 1, in_cas.getCoorY() - 1); // Diagonal
																							// izquierda

					// Si está vacía, la añadimos a la lista
					if (cAux != null && cAux.estaVacio())
						res.add(new Movimiento(in_cas, cAux, this));
					else {
						/*
						 * Si no está vacía, se abren dos nuevas opciones: Que
						 * haya una ficha amiga o una ficha enemiga. Si la ficha
						 * es amiga, hemos acabado por aquí. Si es enemiga,
						 * comprobamos si se puede comer.
						 */
						if (cAux != null && cAux.getFicha().getColor() == Colores.ROJA) {
							if (!cAux.estaVacio() && (in_cas.getCoorX() > 1) && (in_cas.getCoorY() > 1)
									&& tablero[in_cas.getCoorX() - 2][in_cas.getCoorY() - 2].estaVacio()) {
								// Si entramos aquí es que puede hacer un
								// movimiento
								// de comer.
								Movimiento mAux = new Movimiento(in_cas, cAux, this);
								res.add(mAux);
							} // END IF PUEDE COMER
						} // END IF ES NEGRA
					} // END ELSE NO ESTA VACIA
				} // END DE EVITAR EXCEPCION
					// FINAL DIAGONAL IZQUIERDA
					// INICIO DIAGONAL DERECHA
					// Primero evitamos la posible excepción
				if (in_cas.getCoorX() < 7 && in_cas.getCoorY() > 0) {
					cAux = this.dameCasilla(in_cas.getCoorX() + 1, in_cas.getCoorY() - 1);
					if (cAux.estaVacio())
						res.add(new Movimiento(in_cas, cAux, this));
					else {
						/*
						 * De nuevo tenemos dos opciones
						 */
						if (cAux != null && cAux.getFicha().getColor() == Colores.ROJA) {
							if (!cAux.estaVacio() && (in_cas.getCoorX() < 6) && (in_cas.getCoorY() > 2)
									&& tablero[in_cas.getCoorX() + 2][in_cas.getCoorY() - 2].estaVacio()) {
								// Si entramos aquí es que puede hacer un
								// movimiento
								// de comer.
								Movimiento mAux = new Movimiento(in_cas, cAux, this);
								res.add(mAux);
							} // END IF PUEDE COMER
						} // END IF ES NEGRA
					} // END ELSE ESTA VACIO
				} // END EVITAR EXCEPCION
			} // END IF NEGRA
		} // END IF !DAMA

		return res;

	}
	
	
	/**
	 * Convierte este estado en un estado inicial.
	 */
	public void hazloInicial() {
		Casilla cAux;

		// Fichas Rojas
		cAux = this.tablero[0][0];
		cAux.setFicha(new Ficha(Colores.ROJA, cAux, false));

		cAux = this.tablero[2][0];
		cAux.setFicha(new Ficha(Colores.ROJA, cAux, false));

		cAux = this.tablero[4][0];
		cAux.setFicha(new Ficha(Colores.ROJA, cAux, false));

		cAux = this.tablero[6][0];
		cAux.setFicha(new Ficha(Colores.ROJA, cAux, false));

		cAux = this.tablero[1][1];
		cAux.setFicha(new Ficha(Colores.ROJA, cAux, false));

		cAux = this.tablero[3][1];
		cAux.setFicha(new Ficha(Colores.ROJA, cAux, false));

		cAux = this.tablero[5][1];
		cAux.setFicha(new Ficha(Colores.ROJA, cAux, false));

		cAux = this.tablero[7][1];
		cAux.setFicha(new Ficha(Colores.ROJA, cAux, false));

		cAux = this.tablero[0][2];
		cAux.setFicha(new Ficha(Colores.ROJA, cAux, false));

		cAux = this.tablero[2][2];
		cAux.setFicha(new Ficha(Colores.ROJA, cAux, false));

		cAux = this.tablero[4][2];
		cAux.setFicha(new Ficha(Colores.ROJA, cAux, false));

		cAux = this.tablero[6][2];
		cAux.setFicha(new Ficha(Colores.ROJA, cAux, false));

		// Fichas Negras
		cAux = this.tablero[1][7];
		cAux.setFicha(new Ficha(Colores.NEGRA, cAux, false));

		cAux = this.tablero[3][7];
		cAux.setFicha(new Ficha(Colores.NEGRA, cAux, false));

		cAux = this.tablero[5][7];
		cAux.setFicha(new Ficha(Colores.NEGRA, cAux, false));

		cAux = this.tablero[7][7];
		cAux.setFicha(new Ficha(Colores.NEGRA, cAux, false));

		cAux = this.tablero[0][6];
		cAux.setFicha(new Ficha(Colores.NEGRA, cAux, false));

		cAux = this.tablero[2][6];
		cAux.setFicha(new Ficha(Colores.NEGRA, cAux, false));

		cAux = this.tablero[4][6];
		cAux.setFicha(new Ficha(Colores.NEGRA, cAux, false));

		cAux = this.tablero[6][6];
		cAux.setFicha(new Ficha(Colores.NEGRA, cAux, false));

		cAux = this.tablero[1][5];
		cAux.setFicha(new Ficha(Colores.NEGRA, cAux, false));

		cAux = this.tablero[3][5];
		cAux.setFicha(new Ficha(Colores.NEGRA, cAux, false));

		cAux = this.tablero[5][5];
		cAux.setFicha(new Ficha(Colores.NEGRA, cAux, false));

		cAux = this.tablero[7][5];
		cAux.setFicha(new Ficha(Colores.NEGRA, cAux, false));

	}

	/**
	 * Genera el tablero en memoria.
	 */
	public void generaTablero() {
		this.tablero = new Casilla[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				this.tablero[i][j] = new Casilla(i, j);
			}
		}
	}

	/**
	 * Comprueba si una ficha puede comer (Sin ser Dama). Primero comprobamos la
	 * casilla directa, y luego la siguiente; para que tenga un hueco donde
	 * moverse.
	 * 
	 * @param in_ficha
	 *            La Ficha a comprobar
	 * @return <b>True</b> si hay alguna ficha enemiga en una de los posibles
	 *         movimientos.
	 */
	public boolean puedeComer(Ficha in_ficha) {
		boolean res = false;
		int x = in_ficha.getCasilla().getCoorX();
		int y = in_ficha.getCasilla().getCoorY();
		Colores color = in_ficha.getColor();
		Casilla cAux;

		// Comprobamos la esquina superior derecha
		if ((x < 7) && (y < 7) && color == Colores.ROJA) {
			cAux = tablero[x + 1][y + 1];
			if (!cAux.estaVacio() && (x < 6) && (y < 6)
					&& cAux.getFicha().getColor() != mainf.getPartida().getTurno_actual()
					&& tablero[x + 2][y + 2].estaVacio())
				res = (res || true);
		}
		// Comprobamos la esquina inferior derecha
		if ((x < 7) && (y > 0) && color == Colores.NEGRA) {
			cAux = tablero[x + 1][y - 1];
			if (!cAux.estaVacio() && (x < 6) && (y > 1)
					&& cAux.getFicha().getColor() != mainf.getPartida().getTurno_actual()
					&& tablero[x + 2][y - 2].estaVacio())
				res = (res || true);
		}
		// Comprobamos la esquina superior izquierda
		if ((x > 0) && (y < 7) && color == Colores.ROJA) {
			cAux = tablero[x - 1][y + 1];
			if (!cAux.estaVacio() && (x > 1) && (y < 6)
					&& cAux.getFicha().getColor() != mainf.getPartida().getTurno_actual()
					&& tablero[x - 2][y + 2].estaVacio())
				res = (res || true);
		}
		// Comprobamos la esquina inferior izquierda
		if ((x > 0) && (y > 0) && color == Colores.NEGRA) {
			cAux = tablero[x - 1][y - 1];
			if (!cAux.estaVacio() && (x > 1) && (y > 1)
					&& cAux.getFicha().getColor() != mainf.getPartida().getTurno_actual()
					&& tablero[x - 2][y - 2].estaVacio())
				res = (res || true);
		}
		return res;

	}

	/**
	 * Comprueba todas las fichas para ver si alguna puede convertirse en Dama
	 */
	public void checkDamas() {
		// Fila 0
		for (int i = 0; i < 8; i++) {
			Casilla cAux = tablero[i][0];
			// Usamos evaluación perezosa
			if (!cAux.estaVacio() && cAux.getFicha().getColor() == Colores.NEGRA)
				cAux.getFicha().setEsDama(true);
		}

		// Fila 7
		for (int i = 0; i < 8; i++) {
			Casilla cAux = tablero[i][7];
			// Usamos evaluación perezosa
			if (!cAux.estaVacio() && cAux.getFicha().getColor() == Colores.ROJA)
				cAux.getFicha().setEsDama(true);
		}
	}

	/**
	 * Cuenta el número de fichan negras sobre la mesa en este estado.
	 * Las damas cuentan por 2.
	 * 
	 * @return El número de fichas negras
	 */
	public int cuentaNegras() {
		int res = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (!tablero[i][j].estaVacio() && tablero[i][j].getFicha().getColor() == Colores.NEGRA)
					if(tablero[i][j].getFicha().isEsDama()) res = res+2;
					else res++;
			}
		}

		return res;

	}

	/**
	 * Cuenta el número de fichan blancas sobre la mesa en este estado
	 * 
	 * @return El número de fichas blancas
	 */
	public int cuentaBlancas() {
		int res = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (!tablero[i][j].estaVacio() && tablero[i][j].getFicha().getColor() == Colores.ROJA)
					if(tablero[i][j].getFicha().isEsDama()) res = res+2;
					else res++;
			}
		}

		return res;

	}

	/**
	 * Método auxiliar que comprueba si una ficha dada puede o no hacer <b>algún</b> movimiento.
	 * @param in_ficha La ficha a analizar
	 * @return <b>True</b> si dicha ficha tiene algún movimiento disponible.
	 */
	public boolean puedeMover(Ficha in_ficha) {
		boolean res = false;
		int x = in_ficha.getCasilla().getCoorX();
		int y = in_ficha.getCasilla().getCoorY();
		Colores color = in_ficha.getColor();
		Casilla cAux;
		// SI NO ES DAMA
		if (in_ficha.isDama() == false) {
			// Comprobamos la esquina superior derecha
			if ((x < 7) && (y < 7) && (color == Colores.ROJA)) {
				cAux = tablero[x + 1][y + 1];
				if (cAux.estaVacio())
					res = (res || true);
			}
			// Comprobamos la esquina inferior derecha
			if ((x < 7) && (y > 0) && (color == Colores.NEGRA)) {
				cAux = tablero[x + 1][y - 1];
				if (cAux.estaVacio())
					res = (res || true);
			}
			// Comprobamos la esquina superior izquierda
			if ((x > 0) && (y < 7) && (color == Colores.ROJA)) {
				cAux = tablero[x - 1][y + 1];
				if (cAux.estaVacio())
					res = (res || true);
			}
			// Comprobamos la esquina inferior izquierda
			if ((x > 0) && (y > 0) && (color == Colores.NEGRA)) {
				cAux = tablero[x - 1][y - 1];
				if (cAux.estaVacio())
					res = (res || true);
			}
		} // END IF !DAMA
		else {
			// SI ES DAMA
			// Comprobamos la esquina superior derecha
			if ((x < 7) && (y < 7)) {
				cAux = tablero[x + 1][y + 1];
				if (cAux.estaVacio())
					res = (res || true);
			}
			// Comprobamos la esquina inferior derecha
			if ((x < 7) && (y > 0)) {
				cAux = tablero[x + 1][y - 1];
				if (cAux.estaVacio())
					res = (res || true);
			}
			// Comprobamos la esquina superior izquierda
			if ((x > 0) && (y < 7)) {
				cAux = tablero[x - 1][y + 1];
				if (cAux.estaVacio())
					res = (res || true);
			}
			// Comprobamos la esquina inferior izquierda
			if ((x > 0) && (y > 0)) {
				cAux = tablero[x - 1][y - 1];
				if (cAux.estaVacio())
					res = (res || true);
			}
		}
		return res;
	}
	
	/**
	 * Este método es una "expansión" del puedeMover. Aquí te devuelve los posibles movimientos realizables.
	 * PRECONDICIÓN: Puede mover.
	 * @param f La ficha a comprobar
	 * @return Una Array con los posibles movimientos.
	 * @see ArrayList
	 */
	public ArrayList<Movimiento> posiblesMovimientos(Ficha f){
		Estado eAux = new Estado(this);
		ArrayList<Movimiento> res = new ArrayList<Movimiento>();
		/*
		 * Usamos este boolean para poder recorrer todos los
		 * movimientos en una diagonal de una dama.
		 */
		boolean viaLibre = true;
		/*
		 * Boolean auxiliar. Lo usamos para comprobar que no 
		 * se saltan 2 fichas enemigas seguidas.
		 */
		boolean saltoDoble = false;
		int x = f.getCasilla().getCoorX();
		int y = f.getCasilla().getCoorY();
		/*
		 * Contador auxiliar
		 */
		int i=0; 
		Colores color = f.getColor();
		Casilla cAux;
		// SI NO ES DAMA
		if (f.isDama() == false) {
			// Comprobamos la esquina superior derecha
			if ((x < 7) && (y < 7) && (color == Colores.ROJA)) {
				cAux = tablero[x + 1][y + 1];
				if (cAux.estaVacio())
					res.add(new Movimiento(f.getCasilla(), cAux, eAux));
				else{
					if(cAux.getFicha().getColor() == Colores.NEGRA && ((x+2)<=7) && ((y+2)<=7)){
						Casilla cAux2 = tablero[x+2][y+2];
						if(cAux2.estaVacio()){
							/*Si entramos aquí, es que puede comer*/
							res.add(new Movimiento(f.getCasilla(), cAux2, eAux));
						}
					}
				}
			}
			// Comprobamos la esquina inferior derecha
			if ((x < 7) && (y > 0) && (color == Colores.NEGRA)) {
				cAux = tablero[x + 1][y - 1];
				if (cAux.estaVacio())
					res.add(new Movimiento(f.getCasilla(), cAux, eAux));
				else{
					if(cAux.getFicha().getColor() == Colores.ROJA && ((x+2)<=7) && ((y-2)>=0)){
						Casilla cAux2 = tablero[x+2][y-2];
						if(cAux2.estaVacio()){
							/*Si entramos aquí, es que puede comer*/
							res.add(new Movimiento(f.getCasilla(), cAux2, eAux));
						}
					}
				}
			}
			// Comprobamos la esquina superior izquierda
			if ((x > 0) && (y < 7) && (color == Colores.ROJA)) {
				cAux = tablero[x - 1][y + 1];
				if (cAux.estaVacio())
					res.add(new Movimiento(f.getCasilla(), cAux, eAux));
				else{
					if(cAux.getFicha().getColor() == Colores.NEGRA && ((x-2)>=0) && ((y+2)<=7)){
						Casilla cAux2 = tablero[x-2][y+2];
						if(cAux2.estaVacio()){
							/*Si entramos aquí, es que puede comer*/
							res.add(new Movimiento(f.getCasilla(), cAux2, eAux));
						}
					}
				}
			}
			// Comprobamos la esquina inferior izquierda
			if ((x > 0) && (y > 0) && (color == Colores.NEGRA)) {
				cAux = tablero[x - 1][y - 1];
				if (cAux.estaVacio())
					res.add(new Movimiento(f.getCasilla(), cAux, eAux));
				else{
					if(cAux.getFicha().getColor() == Colores.ROJA && ((x-2)>=0) && ((y-2)>=0)){
						Casilla cAux2 = tablero[x-2][y-2];
						if(cAux2.estaVacio()){
							/*Si entramos aquí, es que puede comer*/
							res.add(new Movimiento(f.getCasilla(), cAux2, eAux));
						}
					}
				}
			}						
		} // END IF !DAMA
		else {
			// SI ES DAMA
			// Comprobamos la esquina superior derecha
			viaLibre = true;
			i = 0;
			saltoDoble = false;
			while(viaLibre && i<8){
				i++;
				if ((x+i < 8) && (y+i < 8)) {
				cAux = tablero[x + i][y + i];
				if (cAux.estaVacio()){
					saltoDoble = false;
					res.add(new Movimiento(f.getCasilla(), cAux, eAux));
				}
				else{
					/*
					 * Si nos encontramos una ficha propia, se acaba el movimiento.
					 */
					if(cAux.getFicha().getColor() == f.getColor()) viaLibre = false;
					/*
					 * Si encontramos una ficha enemiga
					 */
					else{
						/*
						 * Si ya hemos saltado, no comprobamos el siguiente
						 */
						if(saltoDoble) viaLibre = false;
						else saltoDoble = true;
					}
				}
				}else viaLibre=false;
			}
			// Comprobamos la esquina inferior derecha
			viaLibre = true;
			i = 0;
			saltoDoble = false;
			while(viaLibre && i<8){
				i++;
				if ((x+i < 8) && (y-i >= 0)) {
				cAux = tablero[x + i][y - i];
				if (cAux.estaVacio()){
					saltoDoble = false;
					res.add(new Movimiento(f.getCasilla(), cAux, eAux));
				}
				else{
					/*
					 * Si nos encontramos una ficha propia, se acaba el movimiento.
					 */
					if(cAux.getFicha().getColor() == f.getColor()) viaLibre = false;
					/*
					 * Si encontramos una ficha enemiga
					 */
					else{
						/*
						 * Si ya hemos saltado, no comprobamos el siguiente
						 */
						if(saltoDoble) viaLibre = false;
						else saltoDoble = true;
					}
				}
				}else viaLibre=false;
			}
			// Comprobamos la esquina superior izquierda
			viaLibre = true;
			i = 0;
			saltoDoble = false;
			while(viaLibre && i<8){
				i++;
				if ((x-i >= 0) && (y+i < 8)) {
				cAux = tablero[x - i][y + i];
				if (cAux.estaVacio()){
					saltoDoble = false;
					res.add(new Movimiento(f.getCasilla(), cAux, eAux));
				}
				else{
					/*
					 * Si nos encontramos una ficha propia, se acaba el movimiento.
					 */
					if(cAux.getFicha().getColor() == f.getColor()) viaLibre = false;
					/*
					 * Si encontramos una ficha enemiga
					 */
					else{
						/*
						 * Si ya hemos saltado, no comprobamos el siguiente
						 */
						if(saltoDoble) viaLibre = false;
						else saltoDoble = true;
					}
				}
				}else viaLibre=false;
			}
			// Comprobamos la esquina inferior izquierda
			viaLibre = true;
			i = 0;
			saltoDoble = false;
			while(viaLibre && i<8){
				i++;
				if ((x-i >= 0) && (y-i >= 0)) {				
				cAux = tablero[x - i][y - i];
				if (cAux.estaVacio()){
					saltoDoble = false;
					res.add(new Movimiento(f.getCasilla(), cAux, eAux));
				}
				else{
					/*
					 * Si nos encontramos una ficha propia, se acaba el movimiento.
					 */
					if(cAux.getFicha().getColor() == f.getColor()) viaLibre = false;
					/*
					 * Si encontramos una ficha enemiga
					 */
					else{
						/*
						 * Si ya hemos saltado, no comprobamos el siguiente
						 */
						if(saltoDoble) viaLibre = false;
						else saltoDoble = true;
					}
				}
				}
			}
		}
		return res;
		
		
	}

	/**
	 * Método para comprobar si se cumple alguna de las condiciones de victoria
	 * 
	 * @return <b>True</b> si hay algún ganador.
	 */
	public boolean checkWin() {
		
		 boolean res = false;
		 boolean algunMovimientoNegro = false;
		 boolean algunMovimientoRojo = false;
		 Casilla cAux;
		 //Primero comprobamos si alguno de los jugadores se ha quedado sin fichas.
		 if(cuentaBlancas() == 0 || cuentaNegras() == 0) res = (res || true);
		 //Ahora comprobamos si algún jugador se ha quedado sin jugadas.
		 for(int i=0; i<8; i++){
			 for(int j=0; j<8; j++){
				 cAux = tablero[i][j];
				 /*Si no es null (No debería) y tiene una ficha encima (No está vacío)
				  * comprobamos la ficha en cuestión. Si tiene ALGÚN movimiento, no hay ganador. 
				  */
				 if(cAux != null && !cAux.estaVacio()){
					 Ficha fAux = cAux.getFicha();
					 if(fAux.getColor() == Colores.NEGRA){
						 if(puedeMover(fAux) || puedeComer(fAux)) algunMovimientoNegro = (algunMovimientoNegro || true);
					 }
					 else if(puedeMover(fAux) || puedeComer(fAux)) algunMovimientoRojo = (algunMovimientoRojo || true);
					 
				 }
			 }
		 }//END FOR i
		 
		 if(algunMovimientoNegro == false || algunMovimientoRojo == false) res = true;
		 
		 return res;		 
	}
	
	/**
	 * Método para calcular el valor del estado actual para un jugador dado.
	 * Para calcular esto, simplemente vamos a comparar la diferencia entre las fichas
	 * propia y las fichas enemigas.
	 * @param c El color para el cual se va a calcular el estado
	 * @return El valor del estado. Suma por cada ficha del mismo color y resta por 
	 * cada ficha del equipo contrario. Las damas cuentan el doble.
	 */
	public int valorEstado(Colores c){
		int res = 0;
		if(c == Colores.NEGRA) res = (cuentaNegras()-cuentaBlancas());
		else res = (cuentaBlancas()-cuentaNegras());
		return res;
	}
	
	/**
	 * Método para generar todos los posibles estados hijos de un movimiento para 
	 * un jugador dado.
	 * 
	 * Si le damos el color rojo, por ejemplo, generará todos los posibles estados derivados
	 * de cada posible movimiento de ficha roja.
	 * 
	 *  
	 * @param c Color que mueve 
	 * @return Un ArrayList con todos los posibles movimientos. A cada movimiento
	 * se le puede ejecutar el "EjecutaMovimiento" para obtener el estado generado.
	 * @see ArrayList
	 * @see Movimiento
	 */
	public ArrayList<Movimiento> dameHijos(Colores c){
		ArrayList<Movimiento> res = new ArrayList<Movimiento>();
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				Casilla cAux = tablero[i][j];
				/*
				 * Si hay una ficha, y dicha ficha es del color dado
				 * [EVALUACIÓN PEREZOSA]
				 */
				if(!cAux.estaVacio() && cAux.getFicha().getColor() == c){
					/*
					 * De ser así, añadimos todos los posibles movimientos
					 */
					cAux.getFicha().setCasilla(cAux);
					res.addAll(posiblesMovimientos(cAux.getFicha()));
				}
			}
		}
		return res;
	}
	
	/**
	 * Método auxiliar que genera todos los posibles estados de todos los posibles movimientos.
	 * @param padre Árbol cuya raíz es el movimiento que ha llevado a este estado.
	 * @param c Color que mueve
	 */
	public void dameHijosArbol(NodoMH<Movimiento> padre, Colores c){
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				Casilla cAux = tablero[i][j];
				/*
				 * Si hay una ficha, y dicha ficha es del color dado
				 * [EVALUACIÓN PEREZOSA]
				 */
				if(!cAux.estaVacio() && cAux.getFicha().getColor() == c){
					/*
					 * De ser así, añadimos todos los posibles movimientos
					 */
					ArrayList<Movimiento> al = posiblesMovimientos(cAux.getFicha());
					Iterator<Movimiento> it = al.iterator();
					while(it.hasNext()){
						Movimiento movAux = it.next();
						padre.addHijo(new NodoMH<Movimiento>(movAux));
					}
					
				}
			}
		}
	}
	
	/**
	 * Método usado durante el desarrollo para comprobar el funcionamiento
	 * de alguno de los métodos.
	 * 
	 * Saca por consola todos los posibles movimientos de una ficha.
	 * 
	 * PRECONDICIÓN: La ficha no es null.
	 * 
	 * @param f La ficha a comprobar
	 * @return Un String con los posibles movimientos.
	 */
	public String pintaMovimientos(Ficha f){
		String s ="";
		Iterator<Movimiento> it = posiblesMovimientos(f).iterator();
		while(it.hasNext()){
			Movimiento mAux = it.next();
			s = s.concat(mAux.toString()+"\n");
		}
		return s;
		
	}
	
	/**
	 * Método auxiliar que muestra por consola todos los posibles movimientos.
	 * @return Un String con todos los posibles movimientos de todas la fichas.
	 */
	public String pintaTodoMovimiento(){
		String s="";
		
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				Casilla c = tablero[i][j];
				if(!c.estaVacio()){
					s = s.concat("Ficha en ["+c.getCoorX()+", "+c.getCoorY()+"]:\n"
				+pintaMovimientos(c.getFicha()));
				}
			}
		}
		
		return s;
	}

	// GETTERS AND SETTERS

	/**
	 * @return La tablero del objeto.
	 */
	public Casilla[][] getTablero() {
		return tablero;
	}

	/**
	 * @param tablero
	 *            La tablero a guardar.
	 */
	public void setTablero(Casilla[][] tablero) {
		this.tablero = tablero;
	}

	/**
	 * @return La mainf del objeto.
	 */
	public MainFrame getMainf() {
		return mainf;
	}

	/**
	 * @param mainf
	 *            La mainf a guardar.
	 */
	public void setMainf(MainFrame mainf) {
		this.mainf = mainf;
	}

}
