package es.unex.cum.jrevello.si.damas.interfaz;

import javax.swing.Icon;
import javax.swing.ImageIcon;
/**
 * Esta clase enlaza el JLabel con la clase casilla.
 */
import javax.swing.JLabel;

import es.unex.cum.jrevello.si.damas.core.Casilla;
import es.unex.cum.jrevello.si.damas.core.Ficha;

public class CasillaGrafica extends JLabel {

	//ATRIBUTOS
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1135014907484787477L;
	/**
	 * La casilla enlazada.
	 */
	private Casilla casilla;
	
	/**
	 * El MainFrame que lo ha creado
	 */
	private MainFrame mainf;
	
	/**
	 * La posicion en la que se encuentra (de 0 a 63)
	 */
	private int posicion;

	//CONSTRUCTORES
	
	public CasillaGrafica() {
	
	}

	public CasillaGrafica(String arg0) {
		super(arg0);
	
	}

	public CasillaGrafica(Icon arg0) {
		super(arg0);
	
	}

	public CasillaGrafica(String arg0, int arg1) {
		super(arg0, arg1);

	}

	public CasillaGrafica(Icon arg0, int arg1) {
		super(arg0, arg1);

	}

	public CasillaGrafica(String arg0, Icon arg1, int arg2) {
		super(arg0, arg1, arg2);

	}
	
	
	public CasillaGrafica(MainFrame mainFrame, int i) {
		this.mainf = mainFrame;
		this.posicion = i;
	}

	public CasillaGrafica(CasillaGrafica in_cg, Casilla in_c) {
		this.casilla = in_c;
		this.mainf = in_cg.getMainf();
		this.posicion = in_cg.getPosicion();
		this.addMouseListener(in_cg.getMouseListeners()[0]);
	}

	//MÉTODOS


	public void addFichaNegra(Ficha f){
		this.setIcon(new ImageIcon(this.getClass().getResource("fichaN.png")));
		if(f != null) this.casilla.setFicha(f);
	}
	
	public void addFichaRoja(Ficha f){
		this.setIcon(new ImageIcon(this.getClass().getResource("fichaR.png")));
		
		if(f != null) this.casilla.setFicha(f);
	}
	
	public void addDamaNegra(Ficha f){
		this.setIcon(new ImageIcon(this.getClass().getResource("fichaRN.png")));
		if(f != null) this.casilla.setFicha(f);
	}
	
	public void addDamaRoja(Ficha f){
		this.setIcon(new ImageIcon(this.getClass().getResource("fichaRR.png")));
		if(f != null) this.casilla.setFicha(f);
	}
	
	@Override
	public String toString(){
		return("Posición: "+this.getPosicion()+"\nCasilla asignada: "+this.getCasilla().toString());
	}

	//GETTERS & SETTERS
	

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
		casilla.setcGrafica(this);
	}

	/**
	 * @return La posicion del objeto.
	 */
	public int getPosicion() {
		return posicion;
	}

	/**
	 * @param posicion La posicion a guardar.
	 */
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	/**
	 * @return La mainf del objeto.
	 */
	public MainFrame getMainf() {
		return mainf;
	}

	/**
	 * @param mainf La mainf a guardar.
	 */
	public void setMainf(MainFrame mainf) {
		this.mainf = mainf;
	}
	
}
