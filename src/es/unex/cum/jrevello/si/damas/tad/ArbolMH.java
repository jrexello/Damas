package es.unex.cum.jrevello.si.damas.tad;

/**
 * Estructura en forma de �rbol con n�mero variable de hijos
 * por nodo
 * 
 * @author Jaime Revello
 *
 */
public class ArbolMH<T> {

//ATRIBUTOS
	/**
	 * Ra�z del �rbol
	 */
	private NodoMH<T> raiz;

//CONSTRUCTORES
	
	/**
	 * Constructor b�sico.
	 * 
	 * Se crea un nuevo nodo con el valor insertado y se hace ra�z del �rbol.
	 *  
	 * @param in_valorRaiz El valor de tipo T que estar� en el nodo ra�z.
	 */
	public ArbolMH(T in_valorRaiz){
		this.raiz = new NodoMH<T>(in_valorRaiz);
	}

	//M�TODOS

//GETTERS & SETTERS
	
	/**
	 * M�todo para obtener la ra�z del �rbol.
	 * @return El nodo ra�z.
	 * @see NodoMH
	 */
	public NodoMH<T> getRaiz(){
		return this.raiz;
	}
}
