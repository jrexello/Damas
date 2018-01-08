package es.unex.cum.jrevello.si.damas.interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import es.unex.cum.jrevello.si.damas.core.Modos;
import es.unex.cum.jrevello.si.damas.core.Partida;

public class BotonesHandler implements ActionListener {

	// ATRIBUTOS

	private JFrame frame;
	private ButtonGroup grupoBotones;
	private JComboBox<String> combo;
	private Partida partida;

	// CONSTRUCTORES

	public BotonesHandler() {

	}

	public BotonesHandler(JFrame jf, ButtonGroup bg) {
		this.frame = jf;
		this.grupoBotones = bg;
	}

	public BotonesHandler(JFrame jf, JComboBox<String> cb, Partida p) {
		this.frame = jf;
		this.combo = cb;
		this.partida = p;
	}

	// MÉTODOS

	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {
		frame.setVisible(false);
		frame.dispose();
		if (grupoBotones != null) {
			String comm = grupoBotones.getSelection().getActionCommand();
			grupoBotones = null;

			if (comm == "HvH") {
				Partida p = new Partida(Modos.HvH);
				MainFrame mainf = new MainFrame(p);
			}

			else if (comm == "HvM") {
				partida = new Partida(Modos.HvM);
				((DialogoInicio) frame).preguntaDificultad(this);
			} else if (comm == "MvM") {
				partida = new Partida(Modos.MvM);
				((DialogoInicio) frame).preguntaDificultad(this);
			} else
				System.out.println("Error al seleccionar un modo");
		}else if(combo != null && partida != null){
			frame.setVisible(false);
			frame.dispose();
			int iAux = combo.getSelectedIndex();
			partida.setProfundidad(iAux+3);
			MainFrame mainf = new MainFrame(partida);
			
		}else System.out.println("Error al crear la partida");

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
	 * @return La grupoBotones del objeto.
	 */
	public ButtonGroup getGrupoBotones() {
		return grupoBotones;
	}

	/**
	 * @param grupoBotones La grupoBotones a guardar.
	 */
	public void setGrupoBotones(ButtonGroup grupoBotones) {
		this.grupoBotones = grupoBotones;
	}

	/**
	 * @return La combo del objeto.
	 */
	public JComboBox<String> getCombo() {
		return combo;
	}

	/**
	 * @param combo La combo a guardar.
	 */
	public void setCombo(JComboBox<String> combo) {
		this.combo = combo;
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
}
