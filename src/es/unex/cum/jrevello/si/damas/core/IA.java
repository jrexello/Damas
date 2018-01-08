package es.unex.cum.jrevello.si.damas.core;

import java.util.Iterator;
import java.util.List;

import es.unex.cum.jrevello.si.damas.excepciones.ExcepcionMovimiento;
import es.unex.cum.jrevello.si.damas.tad.ArbolMH;
import es.unex.cum.jrevello.si.damas.tad.NodoMH;

/**
 * Inteligencia artificial que se encargar� de mover la fichas cuando se juegue contra la
 * m�quina.
 * 
 * @author Jaime Revello
 *
 */
public class IA {

	//ATRIBUTOS
	/**
	 * Color que tiene asignado.
	 */
	private Colores color;
	
	/**
	 * L�mite en la profundidad.
	 */
	private int limite;

	
	//CONSTRUCTORES
	
	/**
	 * Constructor por defecto.
	 * @deprecated
	 */
	public IA() {
		
	}
	
	/**
	 * Constructor con color.
	 * Asigna el color de la IA.
	 * @param in_color Color que va a manejar la IA.
	 * La profundidad m�xima de B�squeda se asgina a 5
	 * @see Colores
	 */
	public IA(Colores in_color){
		this.color = in_color;
		this.limite = 5;
	}
	
	/**
	 * Contructor con color y l�mite de b�squeda
	 * @param in_color Color que controlar� la IA
	 * @param in_lim Profundidad m�xima a la que va a buscar.
	 */
	public IA(Colores in_color, int in_lim) {
		this.color = in_color;
		this.limite = in_lim;
	}

	//M�TODOS
	

	/**
	 * Subm�todo del algoritmo MiniMax.
	 * Se introducen por par�metros el estado inicial y la profundidad actual.
	 * Tras cierta recursi�n, devuelve el valor m�nimo de todos los posibles movimientos 
	 * a partir del estado dado.
	 * 
	 * @param e Estado a analizar. Se generar�n los hijos de este estado.
	 * @param prof Profundidad actual
	 * @return EL valor m�nimo de todos los hijos
	 * @throws ExcepcionMovimiento En caso de error al generar un movimiento.
	 * @deprecated
	 */
	public int valorMin(Estado e, int prof) throws ExcepcionMovimiento{
		//Iniciamos a 0 por inicializar el valor.
		int vmin=0;
		prof++;
		/*Si es un estado terminal (Nos pasamos de profundidad
		 * o termine el juego), devolvemos el valor directamente.
		 */
		if((prof>=limite) || (e.checkWin())) vmin=e.valorEstado(color);
		else{
			//Esto har� las veces de infinito.
			vmin = Integer.MAX_VALUE;
			/*
			 *  Comprobamos cada movimiento.
			 */			
			Iterator<Movimiento> it = e.dameHijos(color).iterator();
			while(it.hasNext()){
				Estado estAux = it.next().preMovimiento();
				vmin = Math.min(vmin, valorMax(estAux, prof));
				
			}					
		}
		return vmin;
		
	}
	
	/**
	 * Algoritmo de b�squeda de mejor movimiento mediante el uso del algoritmo minimax
	 * @param e Estado desde el que se parte
	 * @param prof Profundidad inicial
	 * @return El mejor movimiento encontrado
	 * @throws ExcepcionMovimiento
	 * @Deprecated
	 */
	public Movimiento miniMax(Estado e, int prof) throws ExcepcionMovimiento{
		int max = Integer.MIN_VALUE;
		int cmax;
		Movimiento res = null;
		
		Iterator<Movimiento> it = e.dameHijos(color).iterator();
		while(it.hasNext()){
			Movimiento mAux = it.next();
			Estado estAux = mAux.preMovimiento();
			cmax = valorMin(estAux, prof);
			if(cmax > max){
				max = cmax;
				res = mAux;
			}
		}
		
		return res;
		
	}
	
	/**
	 * Subm�todo del algoritmo minimax para encontrar el mejor valor de entre varios posibles
	 * movimientos
	 * @param e Estado inicia
	 * @param prof profundidad actual
	 * @return El valor m�nimo de calcular el valor de cada estado para el enemigo
	 * @throws ExcepcionMovimiento
	 * @Deprecated
	 */
	public int valorMax(Estado e, int prof) throws ExcepcionMovimiento{
		//Iniciamos a 0; por inicializar el valor.
		int vmax=0;
		prof++;
		/* Si es un estado terminal (Nos pasamos de profundidad
		 * o termine el juego), devolvemos el valor directamente.
		 */
		if((prof>=limite) || (e.checkWin())) vmax=e.valorEstado(color);
		else{
			//Esto har� las veces de menos infinito.
			vmax = Integer.MIN_VALUE;
			/*
			 *  Comprobamos cada movimiento.
			 */			
			Iterator<Movimiento> it = e.dameHijos(color).iterator();
			while(it.hasNext()){
				Estado estAux = it.next().preMovimiento();
				vmax = Math.max(vmax, valorMin(estAux, prof));
				
			}					
		}
		return vmax;
		
	}
	/**
	 * Algoritmo de ValorM�ximo aplicando la poda alfa/beta.
	 * Con esta poda, evitamos buscar en estados innecesarios.
	 * @param e Estado de entrada
	 * @param prof Profundidad actual
	 * @param alfa Cota inferior de un nodo Max (Alfa)
	 * @param beta Cota superior de un nodo Min (Beta)
	 * @param in_color Color que est� moviendo
	 * @return el valor m�ximo que se puede obtener.
	 * @throws ExcepcionMovimiento En caso de error al generar el estado resultante de un movimiento.
	 * @deprecated
	 */
	public int valorMax_ab(Estado e, int prof, int alfa, int beta, Colores in_color) throws ExcepcionMovimiento{
		int res=Integer.MIN_VALUE;
		boolean continua = true;
		Movimiento mAux = null;
		prof++;
		if((prof>=limite) || (e.checkWin())) res=e.valorEstado(otroColor(in_color));
		else{
			Iterator<Movimiento> it = e.dameHijos(in_color).iterator();
			while(it.hasNext() && continua){
				mAux = it.next();
				Estado estAux = mAux.preMovimiento();
				res = Math.max(res, valorMin_ab(estAux, prof, alfa, beta, otroColor(in_color)));
				alfa = Math.max(alfa, res);
				if(alfa >= beta){
					continua = false;
				}				
			}
		}
		return res;
	}
	
	/**
	 * Algoritmo de ValorM�ximo aplicando la poda alfa/beta.
	 * Con esta poda, evitamos buscar en estados innecesarios.
	 * En esta versi�n se usa el Tipo abstracto de datos (TAD) de �rbol multihijos.
	 * @param e Estado de entrada
	 * @param prof Profundidad actual
	 * @param alfa Cota inferior de un nodo Max (Alfa)
	 * @param beta Cota superior de un nodo Min (Beta)
	 * @param in_color Color que est� moviendo
	 * @return el valor m�ximo que se puede obtener.
	 * @throws ExcepcionMovimiento En caso de error al generar el estado resultante de un movimiento.
	 * @see ArbolMH
	 * @see NodoMH
	 */
	public int valorMax_ab_arbol(NodoMH<Movimiento> in_nodo, int prof, int alfa, int beta, Colores in_color) throws ExcepcionMovimiento{
		int res=Integer.MIN_VALUE;
		boolean continua = true;
		/*
		 * Estado que generado por el movimiento.
		 */
		Estado eAux = in_nodo.getValor().preMovimiento();
		prof++;
		if((prof>=limite) || (eAux.checkWin())) res=eAux.valorEstado(otroColor(in_color));
		else{
			/*
			 * Generamos los hijos de este movimiento (Ser�a el turno enemigo)
			 */
			eAux.dameHijosArbol(in_nodo, in_color);
			List<NodoMH<Movimiento>> lista = in_nodo.getHijos();
			Iterator<NodoMH<Movimiento>> it = lista.iterator();
			while(it.hasNext() && continua){
				NodoMH<Movimiento> next_nodo = it.next();
				next_nodo.getValor().setEstadoInicial(eAux);
				res = Math.max(res, valorMin_ab_arbol(next_nodo, prof, alfa, beta, otroColor(in_color)));
				alfa = Math.max(alfa, res);
				if(alfa >= beta){
					continua = false;
				}				
			}
		}
		return res;
	}
	
	/**
	 * Algoritmo de ValorM�nimo aplicando la poda alfa/beta.
	 * Con esta poda, evitamos buscar en estados innecesarios. 
	 * @param e Estado de entrada
	 * @param prof Profundidad actual
	 * @param alfa Cota inferior de un nodo Max (Alfa)
	 * @param beta Cota superior de un nodo Min (Beta)
	 * @param in_color Color para el que se calcula el valor.
	 * @return el valor m�nimo que se puede obtener.
	 * @throws ExcepcionMovimiento En caso de error al generar el estado resultante de un movimiento.
	 * @deprecated
	 */
	public int valorMin_ab(Estado e, int prof, int alfa, int beta, Colores in_color) throws ExcepcionMovimiento{
		int res=Integer.MAX_VALUE;
		boolean continua = true;
		prof++;
		if((prof>=limite) || (e.checkWin())) res=e.valorEstado(otroColor(in_color));
		else{
			Iterator<Movimiento> it = e.dameHijos(in_color).iterator();
			while(it.hasNext() && continua){
				Movimiento nextMov = it.next();
				Estado estAux = nextMov.preMovimiento();
				res = Math.min(res, valorMax_ab(estAux, prof, alfa, beta, otroColor(in_color)));
				beta = Integer.min(beta, res);
				if(alfa >= beta){
					continua = false;
				}
			}				
		}
		return res;
	}
	
	/**
	 * Algoritmo de ValorM�nimo aplicando la poda alfa/beta.
	 * Con esta poda, evitamos buscar en estados innecesarios.
	 * Esta versi�n usa el TAD de �rbol multihijos
	 * @param e Estado de entrada
	 * @param prof Profundidad actual
	 * @param alfa Cota inferior de un nodo Max (Alfa)
	 * @param beta Cota superior de un nodo Min (Beta)
	 * @param in_color Color para el que se calcula el valor.
	 * @return el valor m�nimo que se puede obtener.
	 * @throws ExcepcionMovimiento En caso de error al generar el estado resultante de un movimiento.
	 */
	public int valorMin_ab_arbol(NodoMH<Movimiento> in_nodo, int prof, int alfa, int beta, Colores in_color) throws ExcepcionMovimiento{
		int res=Integer.MAX_VALUE;
		boolean continua = true;
		/*
		 * Estado que generado por el movimiento.
		 */
		Estado eAux = in_nodo.getValor().preMovimiento();
		prof++;
		if((prof>=limite) || (eAux.checkWin())) res=eAux.valorEstado(otroColor(in_color));
		else{
			/*
			 * Generamos los hijos de este movimiento (Ser�a el turno enemigo)
			 */
			eAux.dameHijosArbol(in_nodo, in_color);
			List<NodoMH<Movimiento>> lista = in_nodo.getHijos();
			Iterator<NodoMH<Movimiento>> it = lista.iterator();
			while(it.hasNext() && continua){
				NodoMH<Movimiento> next_nodo = it.next();
				next_nodo.getValor().setEstadoInicial(eAux);
				res = Math.min(res, valorMax_ab_arbol(next_nodo, prof, alfa, beta, otroColor(in_color)));
				beta = Integer.min(beta, res);
				if(alfa >= beta){
					continua = false;
				}
			}				
		}
		return res;
	}
	
	/**
	 * Algoritmo de b�squeda de mejor movimiento a partir de un algoritmo de poda
	 * alfa-beta usando array como tipo abstracto de datos.
	 * @param e Estado inicial
	 * @return El mejor movimiento
	 * @throws ExcepcionMovimiento
	 * @Deprecated
	 */
	public Movimiento alfaBeta(Estado e) throws ExcepcionMovimiento{
		int alfa = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		int tMax;
		Movimiento res = null;
		/*
		 * Primero obtenemos un array con todos los posible movimientos (Sin profundidad)
		 */
		Iterator<Movimiento> it = e.dameHijos(color).iterator();
		while(it.hasNext()){
			Movimiento mAux = it.next();
			Estado estAux = mAux.preMovimiento();
			/*
			 * Ahora para cada estado, generamos los hijos
			 */
			tMax = (estAux.valorEstado(color)+valorMin_ab(estAux, 0, alfa, beta, otroColor(color)));
			if(tMax > max){
				max = tMax;
				res = mAux;
			}
		}
		
		return res;
		
		
	}
	
	/**
	 * Algoritmo de b�squeda de movimiento basa en el algoritmo de
	 * poda alfa/beta.
	 * 
	 * Es una versi�n optimizada del minimax. Busca de entre todos los posibles hijos el 
	 * mejor movimiento dado un estado inicial
	 * @param e Estado inicial
	 * @return El mejor movimiento posible
	 * @throws ExcepcionMovimiento
	 * @see ArbolMH
	 * @see NodoMH
	 */
	public Movimiento alfaBetaArbol(Estado e) throws ExcepcionMovimiento{
		int alfa = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		Movimiento res = null;
		/*
		 * Primero obtenemos un array con todos los posible movimientos (Sin profundidad)
		 */
		Iterator<Movimiento> it = e.dameHijos(color).iterator();
		while(it.hasNext()){
			Movimiento mAux = it.next();
			ArbolMH<Movimiento> arbol = new ArbolMH<Movimiento>(mAux);
			int temp = valorMax_ab_arbol(arbol.getRaiz(), 0, alfa, beta, otroColor(this.color));
			if(temp>max){
				res = mAux;
				max = temp;
			}
		}
		
		return res;
		
		
	}
	/*	
	public Movimiento alfaBeta(Estado e) throws ExcepcionMovimiento{
		int alfa = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		int cmax;
		Movimiento res = null;
		
		Iterator<Movimiento> it = e.dameHijos(color).iterator();
		while(it.hasNext()){
			Movimiento mAux = it.next();
			Estado estAux = mAux.preMovimiento();
			cmax = valorMax_ab(estAux, 0, alfa, beta, color);
			if(cmax > max){
				max = cmax;
				res = mAux;
			}
		}
		
		return res;
		
		
	}*/
	
	/**
	 * Devuelve el color contrario al dado.
	 * @param c Color de entrada
	 * @return Color contrario al de entrada
	 */
	public Colores otroColor(Colores c){
		if (c == Colores.NEGRA) return Colores.ROJA;
		else return Colores.NEGRA;
	}
	
	

	//GETTERS & SETTERS

	/**
	 * @return La color del objeto.
	 */
	public Colores getColor() {
		return color;
	}

	/**
	 * @param color La color a guardar.
	 */
	public void setColor(Colores color) {
		this.color = color;
	}
}
