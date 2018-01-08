package es.unex.cum.jrevello.si.damas.tad;

import java.util.LinkedList;
import java.util.List;

/**
 * Cada nodo de un árbol multihijo
 * @author Jaime Revello
 *
 */
public class NodoMH<T> {

//ATRIBUTOS
	
	/**
	 * Valor almacenado
	 */
	private T valor;
	
	/**
	 * Lista de hijos
	 */
	private List<NodoMH<T>> hijos;
	
//CONSTRUCTORES
	
	/**
	 * Constructor básico con un valor
	 * @param in_valor Valor del nodo
	 */
	public NodoMH(T in_valor){
		this.valor = in_valor;
		this.hijos = new LinkedList<NodoMH<T>>();
	}

//MÉTODOS
	
	/**
	 * Método para añadir un nodo hijo
	 * @param in_hijo Nodo hijo.
	 */
	public void addHijo(NodoMH<T> in_hijo){
		this.hijos.add(in_hijo);
	}

//GETTERS & SETTERS
	
	/**
	 * Método para obtener el valor almacenado en el nodo
	 * @return El valor almacenado en el nodo
	 */
	public T getValor(){
		return this.valor;
	}
	
	/**
	 * Método para obtener la lista de hijos.
	 * @return La lista de hijos
	 */
	public List<NodoMH<T>> getHijos(){
		return this.hijos;
	}
}
