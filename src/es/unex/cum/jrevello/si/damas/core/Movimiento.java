package es.unex.cum.jrevello.si.damas.core;

import java.util.ArrayList;
import java.util.Iterator;

import es.unex.cum.jrevello.si.damas.excepciones.ExcepcionMovimiento;
import es.unex.cum.jrevello.si.damas.interfaz.CasillaGrafica;

/**
 * Un posible movimiento.
 * @author Jaime Revello
 *
 */
public class Movimiento {

//ATRIBUTOS
	/**
	 * La ficha que se mueve
	 */
	private Ficha ficha;
	
	/**
	 * Casilla de origen
	 */
	private Casilla origen;
	
	/**
	 * Casilla destino
	 */
	private Casilla destino;
	
	/**
	 * Color del que es el turno.
	 */
	private Colores color;
	
	/**
	 * Estado del que se parte
	 */
	private Estado estadoInicial;
	
	/**
	 * Puntero a la partida actual. Lo usamos para comprobar de quién es el turno.
	 */
	private Partida partida;
	
	/**
	 * Indica si en el movimiento se incluye una "comida" de ficha.
	 */
	private boolean come;

//CONSTRUCTORES

	/**
	 * Constructor por defecto.
	 *
	 */
	public Movimiento() {
		
	}
	
	/**
	 * Constructor parametrizado
	 * @param in_ficha La ficha a mover
	 * @param in_origen El origen de la ficha
	 * @param in_destino El destino de la ficha
	 * @param in_color El color de la ficha
	 */
	public Movimiento(Ficha in_ficha, Casilla in_origen, Casilla in_destino, Colores in_color) {
		
		this.ficha = in_ficha;
		this.origen = in_origen;
		this.destino = in_destino;
		this.color = in_color;
		
	}
	
	/**
	 * Constructor parametrizado sin el color, que puede sacarse de la ficha.
	 * @param in_ficha La ficha a mover
	 * @param in_origen El origen de la ficha
	 * @param in_destino El destino de la ficha
	 */
	public Movimiento(Ficha in_ficha, Casilla in_origen, Casilla in_destino){
		this.ficha = in_ficha;
		this.origen = in_origen;
		this.destino = in_destino;
		this.color = in_ficha.getColor();
	}
	
	/**
	 * Constructor "Cómodo" pide los mínimos parámetros y genera todos los datos a través de estos.
	 * @param origen Casilla origen
	 * @param destino Casilla destino
	 * @param estado Estado Inicial
	 */
	public Movimiento(Casilla origen, Casilla destino, Estado estado){
		this.ficha = origen.getFicha();
		this.origen = origen;
		this.destino = destino;
		this.estadoInicial = estado;
		this.partida = origen.getcGrafica().getMainf().getPartida();
		this.color = ficha.getColor();
	}
	
	public Movimiento(Casilla origen, Casilla destino, Estado estado, boolean come) {
		this.ficha = origen.getFicha();
		this.origen = origen;
		this.destino = destino;
		this.estadoInicial = estado;
		this.partida = origen.getcGrafica().getMainf().getPartida();
		this.color = ficha.getColor();
		this.come = come;
	}
	

//MÉTODOS
	


	/**
	 * Comprueba si el movimiento se puede ejecutar.
	 * @return <b>True</b> si las reglas permiten el movimiento.
	 * @throws ExcepcionMovimiento En caso de error al calcular el movimiento
	 */
	public boolean esValido() throws ExcepcionMovimiento{
		boolean res = true;
		
		//Comprobamos que haya una ficha en juego.
		if(this.ficha == null){
			res = false;
			//Si no la hay, la intentamos buscar la ficha en la casilla origen
			if(this.origen.getFicha() != null){
				this.ficha = this.origen.getFicha();
				res = true;
			}
			else throw new ExcepcionMovimiento("No existe una ficha en que mover.");
			
			
		}//END IF !FICHA
		 //Comprobamos que la ficha esté en la casilla de origen dada.
		else if((this.ficha.getCasilla().getCoorX() != this.origen.getCoorX()) ||(this.ficha.getCasilla().getCoorY() != this.origen.getCoorY())) {
			/*Como es un error "extraño" (No podemos que ficha es la correcta si la que tenemos en el 
			 * puntero o la de la casilla origen), enviamos directamente la excepción.*/
			res = false;
			throw new ExcepcionMovimiento("La ficha en juego no es la misma que la ficha en la casilla");			
			
		}
		
		else if(!estadoInicial.puedeMover(ficha) && !estadoInicial.puedeComer(ficha)){
			res = false;
			throw new ExcepcionMovimiento("La ficha no puede hacer nada");
		}
		//Comprobamos que si la ficha NO es dama, solo mueve una posición.
		else if((!this.ficha.isDama()) && ((Math.abs(this.destino.getCoorX()-this.origen.getCoorX())!=1) || 
				(Math.abs(this.destino.getCoorY()-this.origen.getCoorY())!=1))){
			//Esta regla tiene una excepción: Si va a comer:
			if(this.estadoInicial.puedeComer(ficha)){
				if((!this.ficha.isDama()) && ((Math.abs(this.destino.getCoorX()-this.origen.getCoorX())!=2) || 
						(Math.abs(this.destino.getCoorY()-this.origen.getCoorY())!=2))){
					res = false;
					throw new ExcepcionMovimiento("Las fichas normales solo pueden moverse una posición en diagonal");
				}
			}else {
				res = false;
				throw new ExcepcionMovimiento("Las fichas normales solo pueden moverse una posición en diagonal");
			
			}
		}
		//Comprobamos que el destino NO esté ocupado.
		else if(!this.destino.estaVacio()){
			res = false;
			throw new ExcepcionMovimiento("La casilla de destino");
		}
		//Comprobamos que están en la misma diagonal. 
		else if(!mismaDiagonal(origen, destino)){
			res = false;
			throw new ExcepcionMovimiento("No están en la misma diagonal");
		}
		//Comprobamos que no retrocede
		else{
			if(ficha.getColor() == Colores.ROJA && ficha.isDama() == false){		
			if(origen.getCoorY() >= destino.getCoorY()) throw new ExcepcionMovimiento("Las fichas no puede retroceder");
		} else if (ficha.getColor() == Colores.NEGRA && ficha.isDama() == false){
			if(origen.getCoorY() <= destino.getCoorY()) throw new ExcepcionMovimiento("Las fichas no puede retroceder");
		}
		}
		
		return res;		
	}
	
	/**
	 * Método auxiliar para determinar si dos casillas se encuentran en la misma diagonal.
	 * @param casilla1 Una de las casillas.
	 * @param casilla2 La segunda casilla.
	 * @return <b>True</b> si las dos casillas están en la misma diagonal.
	 */
	public boolean mismaDiagonal(Casilla casilla1, Casilla casilla2){
		
		int x1,x2,y1,y2;
		
		x1= casilla1.getCoorX();
		x2= casilla2.getCoorX();
		y1= casilla1.getCoorY();
		y2= casilla2.getCoorY();
		
		return(Math.abs((x1-x2)/(y1-y2)) == 1);
		
	}
	
	/**
	 * Método que hace efectivo el movimiento a nivel lógico y gráfico.
	 * PRECONDICIÓN: El movimiento es válido
	 * @return El nuevo estado con el movimiento aplicado
	 * @throws ExcepcionMovimiento En caso de error al calcular el movimiento
	 */
	public Estado preMovimiento() throws ExcepcionMovimiento{
		//Hacemos una copia del estado inicial
		Estado estadoFinal = new Estado(this.estadoInicial);
		Estado copiaEInicial = new Estado(this.estadoInicial);
		this.estadoInicial = estadoFinal;
		
		

			ArrayList<Ficha> comidas = listaComidas();			
			if(!comidas.isEmpty()){
				//Si hemos comido alguna ficha
				Iterator<Ficha> it = comidas.iterator();
				while(it.hasNext()){
					Ficha f = it.next();
					f.getCasilla().setFicha(null);
				}
			}
			Casilla sinFicha = estadoFinal.getTablero()[origen.getCoorX()][origen.getCoorY()];
			Casilla conFicha = estadoFinal.getTablero()[destino.getCoorX()][destino.getCoorY()];
			sinFicha.setFicha(null);			
			conFicha.setFicha(ficha);
			ficha.setCasilla(conFicha);
			
			this.estadoInicial = copiaEInicial;

		
		return estadoFinal;		
	}
	
	public void ejecutaMovimiento(CasillaGrafica in_cg){
		origen.setFicha(null);
		in_cg.setCasilla(destino);
		destino.setcGrafica(in_cg);
		
	}
	
	/**
	 * Este método comprueba si se ha comido alguna ficha en el movimiento de una dama.
	 * PRECONDICIÓN: El movimiento es válido.
	 * @return Un array con la lista de fichas comida.
	 * @throws ExcepcionMovimiento En caso de error al calcular el movimiento
	 */
	public ArrayList<Ficha> listaComidas() throws ExcepcionMovimiento{
		ArrayList<Ficha> a = new ArrayList<Ficha>();
		//Usaremos esto para ir comprobamos las casillas.
		Casilla cAux;
		int oX, oY, dX, dY;
		oX = origen.getCoorX();
		oY = origen.getCoorY();
		dX = destino.getCoorX();
		dY = destino.getCoorY();
		
		//Contador por si el juego entra en un bucle infinito por cualquier fallo.
		int contador = 1;
		
		//Primero averiguamos el tipo de movimiento.
		if(dX > oX){
			//En este caso es un movimiento hacia la derecha.
			if(dY > oY){
				//Derecha hacia arriba
				//Tomamos la primera casilla en la diagonal
				cAux = this.estadoInicial.getTablero()[oX+contador][oY+contador];
				while((cAux.getCoorX() != destino.getCoorX() || cAux.getCoorY() != destino.getCoorY()) && contador < 10){
					cAux = this.estadoInicial.getTablero()[oX+contador][oY+contador];
					if(!cAux.estaVacio()){
						//Si hay una ficha, hay dos posibilidades:
						//La ficha es propia:
						if(cAux.getFicha().getColor() == this.partida.getTurno_actual()){
							//throw new ExcepcionMovimiento("No puedes saltar sobre tus propias fichas");
						}
						else{
							//Si la ficha es del enemigo
							a.add(cAux.getFicha());
						}
					}
					//Avanzamos a la siguiente casilla
					contador++;
				}
				if(contador >= 10) throw new ExcepcionMovimiento("Se han ejecutado más de 10 movimiento horizontales. Ha habido un fallo.");
			}//END DERECHA ARRIBA
			
			else{
				//Si el movimiento es derecha abajo
				//Tomamos la primera casilla en la diagonal
				cAux = this.estadoInicial.getTablero()[oX+contador][oY-contador];
				while((cAux.getCoorX() != destino.getCoorX() || cAux.getCoorY() != destino.getCoorY()) && contador < 10){
					cAux = this.estadoInicial.getTablero()[oX+contador][oY-contador];
					if(!cAux.estaVacio()){
						//Si hay una ficha, hay dos posibilidades:
						//La ficha es propia:
						if(cAux.getFicha().getColor() == this.partida.getTurno_actual()){
							if(!partida.turnoIA()) throw new ExcepcionMovimiento("No puedes saltar sobre tus propias fichas, "
									+this.color+", "+this.toString());
						}
						else{
							//Si la ficha es del enemigo
							a.add(cAux.getFicha());
						}
					}
					//Avanzamos a la siguiente casilla
					contador++;
				}
				if(contador >= 10) throw new ExcepcionMovimiento("Se han ejecutado más de 10 movimiento horizontales. Ha habido un fallo.");
			}//END DERECHA ABAJO
			
		}//END DERECHA
		
		else{
			//En este caso es un movimiento hacia la izquierda.
			if(dY > oY){
				//Izquierda hacia arriba
				//Tomamos la primera casilla en la diagonal
				cAux = this.estadoInicial.getTablero()[oX-contador][oY+contador];
				while((cAux.getCoorX() != destino.getCoorX() || cAux.getCoorY() != destino.getCoorY()) && contador < 10){
					cAux = this.estadoInicial.getTablero()[oX-contador][oY+contador];
					if(!cAux.estaVacio()){
						//Si hay una ficha, hay dos posibilidades:
						//La ficha es propia:
						if(cAux.getFicha().getColor() == this.partida.getTurno_actual()){
							//throw new ExcepcionMovimiento("No puedes saltar sobre tus propias fichas");
						}
						else{
							//Si la ficha es del enemigo
							a.add(cAux.getFicha());
						}
					}
					//Avanzamos a la siguiente casilla
					contador++;
				}
				if(contador >= 10) throw new ExcepcionMovimiento("Se han ejecutado más de 10 movimiento horizontales. Ha habido un fallo.");
			}//END IZQUIERDA ARRIBA
			
			else{
				//Si el movimiento es izquierda abajo
				//Tomamos la primera casilla en la diagonal
				cAux = this.estadoInicial.getTablero()[oX-contador][oY-contador];
				while((cAux.getCoorX() != destino.getCoorX() || cAux.getCoorY() != destino.getCoorY()) && contador < 10){
					cAux = this.estadoInicial.getTablero()[oX-contador][oY-contador];
					if(!cAux.estaVacio()){
						//Si hay una ficha, hay dos posibilidades:
						//La ficha es propia:
						if(cAux.getFicha().getColor() == this.partida.getTurno_actual()){
							if(!this.partida.turnoIA()) throw new ExcepcionMovimiento("No puedes saltar sobre tus propias fichas "+this.color.toString()+", "+this.toString());
						}
						else{
							//Si la ficha es del enemigo
							a.add(cAux.getFicha());
						}
					}
					//Avanzamos a la siguiente casilla
					contador++;
				}
				if(contador >= 10) throw new ExcepcionMovimiento("Se han ejecutado más de 10 movimiento horizontales. Ha habido un fallo.");
			}//END IZQUIERDA ABAJO
			
		}//END IZQUIERDA
		
		if(!a.isEmpty()) this.ficha.setHaComido(true);
		return a;
	}
	
	@Override
	public String toString(){
		return("["+this.origen.getCoorX()+", "+this.origen.getCoorY()+"] -> ["+this.destino.getCoorX()+", "
				+ this.destino.getCoorY()+"]");
	}

//GETTERS & SETTERS
	

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
	}

	/**
	 * @return the origen
	 */
	public Casilla getOrigen() {
		return origen;
	}

	/**
	 * @param origen the origen to set
	 */
	public void setOrigen(Casilla origen) {
		this.origen = origen;
	}

	/**
	 * @return the destino
	 */
	public Casilla getDestino() {
		return destino;
	}

	/**
	 * @param destino the destino to set
	 */
	public void setDestino(Casilla destino) {
		this.destino = destino;
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
	 * @return La estadoInicial del objeto.
	 */
	public Estado getEstadoInicial() {
		return estadoInicial;
	}

	/**
	 * @param estadoInicial La estadoInicial a guardar.
	 */
	public void setEstadoInicial(Estado estadoInicial) {
		this.estadoInicial = estadoInicial;
	}

	/**
	 * @return La partida del objeto.
	 */
	public Partida getPartida() {
		return partida;
	}

	/**
	 * @param partida La partida a guardar.
	 */
	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	/**
	 * @return La come del objeto.
	 */
	public boolean isCome() {
		return come;
	}

	/**
	 * @param come La come a guardar.
	 */
	public void setCome(boolean come) {
		this.come = come;
	}
	
	
}
