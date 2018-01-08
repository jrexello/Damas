package es.unex.cum.jrevello.si.damas.tad;

/**
 * Estructura en forma de árbol con número variable de hijos
 * por nodo
 * 
 * @author Jaime Revello
 *
 */
public class ArbolMH<T> {

//ATRIBUTOS
	/**
	 * Raíz del árbol
	 */
	private NodoMH<T> raiz;

//CONSTRUCTORES
	
	/**
	 * Constructor básico.
	 * 
	 * Se crea un nuevo nodo con el valor insertado y se hace raíz del árbol.
	 *  
	 * @param in_valorRaiz El valor de tipo T que estará en el nodo raíz.
	 */
	public ArbolMH(T in_valorRaiz){
		this.raiz = new NodoMH<T>(in_valorRaiz);
	}

	//MÉTODOS

//GETTERS & SETTERS
	
	/**
	 * Método para obtener la raíz del árbol.
	 * @return El nodo raíz.
	 * @see NodoMH
	 */
	public NodoMH<T> getRaiz(){
		return this.raiz;
	}
}
