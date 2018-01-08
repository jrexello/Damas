package es.unex.cum.jrevello.si.damas.interfaz;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import es.unex.cum.jrevello.si.damas.core.Casilla;
import es.unex.cum.jrevello.si.damas.core.Colores;
import es.unex.cum.jrevello.si.damas.core.Estado;
import es.unex.cum.jrevello.si.damas.core.Ficha;
import es.unex.cum.jrevello.si.damas.core.Movimiento;
import es.unex.cum.jrevello.si.damas.core.Partida;

/**
 * Clase encargada de generar el entorno gráfico
 * 
 * @author Jaime Revello
 *
 */
public class MainFrame {

	// ATRIBUTOS
	/**
	 * El frame más externo.
	 */
	private JFrame frame;

	/**
	 * La barra del menú.
	 */
	private JMenuBar menuBar;

	/**
	 * ContentPane principal.
	 */
	private JPanel contentPane;

	/**
	 * Array que guarda todas las casillas. Lo usamos para repintar.
	 */
	private ArrayList<CasillaGrafica> arrayCasillas;
	
	/**
	 * Estado actual.
	 */
	private Estado estadoActual;
	
	/**
	 * Booleano para controlar si ya "tenemos una ficha en la mano".
	 */
	private boolean clicked;
	
	/**
	 * Movimiento que estamos ejecutando
	 */
	private Movimiento mov_actual;
	
	/**
	 * Partida actual
	 */
	private Partida partida;
	
	/**
	 * La ficha "en la mano"
	 */	
	private Ficha fichaEnMano;


	// CONSTRUCTORES

	/**
	 * Constructor básico. Es el que genera todo el interfaz.
	 */
	public MainFrame() {
		this.partida = new Partida();
		partida.iniciaIA();
		this.startGame();
	}
	
	public MainFrame(Partida p){
		this.partida = p;
		partida.iniciaIA();
		this.startGame();
	}

	// MÉTODOS

	/**
	 * Método encargado de generar (y devolver) el panel del juego. Para ello
	 * crea una matriz (gráfica) de casillas.
	 * 
	 * @return El JPanel que representará el tablero de juego.
	 */
	public JPanel dameContentPane() {
		// Usamos el GridLayout para distribuir las casillas de forma uniforme.
		JPanel panelAux = new JPanel(new GridLayout(8, 8));
		CasillaGrafica labelAux;

		// Generamos las 64 casilla. Para ello usamos CasillaGrafica, que es un
		// hijo de JLabel.
		for (int i = 0; i < 64; i++) {
			labelAux = new CasillaGrafica(this, i);

			/*
			 * Para distribuir los colores de las casilla con forma de tablero
			 * usamos este algoritmo Si la línea es par (Empezando de 0) y la
			 * casilla también es par o si la línea es impar y la casilla impar,
			 * lo ponemos rojo
			 */
			if ((i % 16 < 8 && i % 2 == 0) || (i % 16 >= 8 && i % 2 == 1)) {
				labelAux.setBackground(new Color(153, 0, 0));
				/*if ((i / 8) < 3){
					labelAux.addFichaBlanca(null);
				}*/
				labelAux.setCasilla(estadoActual.getTablero()[(i % 8)][(7 - (i / 8))]);
				labelAux.addMouseListener(new RatonHandler(this, labelAux));
			}
			/*
			 * Ahora coloreamos y preparamos las casillas grises
			 */
			else if ((i % 16 < 8 && i % 2 == 1) || (i % 16 >= 8 && i % 2 == 0)) {
				labelAux.setBackground(Color.GRAY);
				labelAux.setCasilla(estadoActual.getTablero()[(i % 8)][(7 - (i / 8))]);
				/*if ((i / 8) > 4)
					labelAux.addFichaRoja(null);*/
				labelAux.addMouseListener(new RatonHandler(this, labelAux));
			}
			// Dibujamos líneas entre las casillas para ayudar a diferenciarlas
			labelAux.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			this.arrayCasillas.add(labelAux);
			// Hacemos visible el color y las pintamos.
			labelAux.setOpaque(true);
			labelAux.repaint();
			panelAux.add(labelAux);
			this.pintaEstado(estadoActual);
		}

		return panelAux;
	}

	/**
	 * Método que genera (Y devuelve) la barra menú
	 * 
	 * @return La barra menú.
	 */
	public JMenuBar dameMenuBar() {
		JMenuBar menuAux = new JMenuBar();
		JMenu m_archivo = new JMenu("Archivo");
		
		JMenuItem mi_new = new JMenuItem("Nueva partida");
		mi_new.addActionListener(new MenuHandler(this));
		m_archivo.add(mi_new);
		
		JMenuItem mi_contar = new JMenuItem("Contar Fichas");
		mi_contar.addActionListener(new MenuHandler(this));
		m_archivo.add(mi_contar);
		
		JMenuItem mi_listar = new JMenuItem("Listar Movimientos");
		mi_listar.addActionListener(new MenuHandler(this));
		m_archivo.add(mi_listar);
		
		menuAux.add(m_archivo);

		return menuAux;
	}

	/**
	 * Método para pintar el fondo (Sin las fichas).
	 * Lo usamos para seleccionar y deseleccionar casillas.
	 */
	public void pintaFondos() {
		Iterator<CasillaGrafica> it = arrayCasillas.iterator();
		CasillaGrafica aux;
		while (it.hasNext()) {
			aux = it.next();
			int i = aux.getPosicion();
			if ((i % 16 < 8 && i % 2 == 0) || (i % 16 >= 8 && i % 2 == 1)) {
				aux.setBackground(new Color(153, 0, 0));
			} else
				aux.setBackground(Color.GRAY);
		}
	}
	
	public void generaCGraficas(Estado in_e){
		Iterator<CasillaGrafica> it = arrayCasillas.iterator();
		CasillaGrafica aux;
		while (it.hasNext()) {
			aux = it.next();
			int aux_x = aux.getCasilla().getCoorX();
			int aux_y = aux.getCasilla().getCoorY();
			aux.setCasilla(in_e.getTablero()[aux_x][aux_y]);
		}
	}
	
	/**
	 * Método para actualizar el atributo "estado actual". No pinta el estado.
	 */
	public void actualizaEstado(){
		Iterator<CasillaGrafica> it = arrayCasillas.iterator();
		CasillaGrafica aux;
		while(it.hasNext()){
			aux = it.next();
			estadoActual.getTablero()[aux.getCasilla().getCoorX()][aux.getCasilla().getCoorY()] = aux.getCasilla();
		}
		
	}
	
	/**
	 * Reestructura el tablero para representar un estado
	 * @param in_estado Estado a representar
	 */
	public void pintaEstado(Estado in_estado){
		Iterator<CasillaGrafica> it = arrayCasillas.iterator();
		CasillaGrafica aux;
		while(it.hasNext()){
			aux = it.next();
			Casilla cAux = in_estado.getTablero()[aux.getCasilla().getCoorX()][aux.getCasilla().getCoorY()];
			if(cAux.getFicha() != null){
				Ficha fAux = cAux.getFicha();
				if(fAux.getColor()==Colores.ROJA){
					if(!fAux.isDama())
					aux.addFichaRoja(cAux.getFicha());
					else aux.addDamaRoja(cAux.getFicha());
				}
				else if(fAux.getColor()==Colores.NEGRA){
					if(!fAux.isDama())
					aux.addFichaNegra(cAux.getFicha());
					else aux.addDamaNegra(cAux.getFicha());
				}
					
				}else{
					//Si la ficha estaba antes y ahora no, la borramos.
					aux.setIcon(null);
				}
			}
	}
	
	/**
	 * Inicia todo el entorno gráfico del juego.
	 */
	public void startGame(){
		frame = new JFrame("Damas");
		frame.setSize(600, 600);
		frame.setLocationRelativeTo(null); // Con esto lo colocamos en el centro
											// de la pantalla
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		arrayCasillas = new ArrayList<CasillaGrafica>();
		estadoActual = new Estado(this);
		estadoActual.hazloInicial();
		clicked = false;

		menuBar = dameMenuBar();
		contentPane = dameContentPane();

		frame.setJMenuBar(menuBar);
		frame.add(contentPane);

		frame.setVisible(true);
		
		//System.out.println(estadoActual.pintaMovimientos(estadoActual.getTablero()[4][2].getFicha()));
		
	}
	// GETTERS & SETTERS
	

	/**
	 * @return La frame del objeto.
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * @param frame La frame a guardar.
	 */
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	/**
	 * @return La menuBar del objeto.
	 */
	public JMenuBar getMenuBar() {
		return menuBar;
	}

	/**
	 * @param menuBar La menuBar a guardar.
	 */
	public void setMenuBar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}

	/**
	 * @return La contentPane del objeto.
	 */
	public JPanel getContentPane() {
		return contentPane;
	}

	/**
	 * @param contentPane La contentPane a guardar.
	 */
	public void setContentPane(JPanel contentPane) {
		this.contentPane = contentPane;
	}

	/**
	 * @return La arrayCasillas del objeto.
	 */
	public ArrayList<CasillaGrafica> getArrayCasillas() {
		return arrayCasillas;
	}

	/**
	 * @param arrayCasillas La arrayCasillas a guardar.
	 */
	public void setArrayCasillas(ArrayList<CasillaGrafica> arrayCasillas) {
		this.arrayCasillas = arrayCasillas;
	}

	/**
	 * @return La estadoActual del objeto.
	 */
	public Estado getEstadoActual() {
		return estadoActual;
	}

	/**
	 * @param estadoActual La estadoActual a guardar.
	 */
	public void setEstadoActual(Estado estadoActual) {
		this.estadoActual = estadoActual;
	}

	/**
	 * @return La clicked del objeto.
	 */
	public boolean isClicked() {
		return clicked;
	}

	/**
	 * @param clicked La clicked a guardar.
	 */
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	/**
	 * @return La mov_actual del objeto.
	 */
	public Movimiento getMov_actual() {
		return mov_actual;
	}

	/**
	 * @param mov_actual La mov_actual a guardar.
	 */
	public void setMov_actual(Movimiento mov_actual) {
		this.mov_actual = mov_actual;
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
	 * @return La fichaEnMano del objeto.
	 */
	public Ficha getFichaEnMano() {
		return fichaEnMano;
	}

	/**
	 * @param fichaEnMano La fichaEnMano a guardar.
	 */
	public void setFichaEnMano(Ficha fichaEnMano) {
		this.fichaEnMano = fichaEnMano;
	}
}
