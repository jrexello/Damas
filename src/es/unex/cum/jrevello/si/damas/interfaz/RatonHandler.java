package es.unex.cum.jrevello.si.damas.interfaz;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import es.unex.cum.jrevello.si.damas.core.Casilla;
import es.unex.cum.jrevello.si.damas.core.Colores;
import es.unex.cum.jrevello.si.damas.core.Estado;
import es.unex.cum.jrevello.si.damas.core.IA;
import es.unex.cum.jrevello.si.damas.core.Modos;
import es.unex.cum.jrevello.si.damas.core.Movimiento;
import es.unex.cum.jrevello.si.damas.core.Partida;
import es.unex.cum.jrevello.si.damas.excepciones.ExcepcionMovimiento;

/**
 * Clase especializada para los eventos del ratón. Esencialmente es igual que la
 * MouseAdapter, pero se añade un puntero a la casilla que representa.
 * 
 * @author Jaime Revello
 * @see CasillaGrafica
 * @see Casilla
 *
 */
public class RatonHandler extends MouseAdapter {

	// ATRIBUTOS

	/**
	 * Casilla Gráfica enlazada
	 */
	private CasillaGrafica casillaG;

	/**
	 * Frame principal
	 */
	private MainFrame mainf;

	/**
	 * Partida actual
	 */
	private Partida partida;

	// CONSTRUCTORES

	public RatonHandler() {
		
	}

	public RatonHandler(CasillaGrafica in_cg) {
		this.casillaG = in_cg;
		this.mainf = in_cg.getMainf();
		this.partida = in_cg.getMainf().getPartida();
	}

	public RatonHandler(MainFrame mainFrame, CasillaGrafica labelAux) {
		this.casillaG = labelAux;
		this.mainf = mainFrame;
		this.partida = mainFrame.getPartida();
	}

//MÉTODOS

	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			Partida partida = mainf.getPartida();
			mainf.generaCGraficas(mainf.getEstadoActual());
			// Si hay algún ganador, terminamos la partida
			if (mainf.getEstadoActual().checkWin()) {
				JOptionPane.showMessageDialog(null, "FIN DE PARTIDA!");
			} else {
				if((partida.getModo() == Modos.HvH) ||
						(partida.getModo() == Modos.HvM && 
								partida.getTurno_actual() == Colores.ROJA)){
				// Primero comprobamos si se ha seleccionado el origen o el
				// destino.
				if (!mainf.isClicked()) {
					// Entramos aquí si estamos seleccionando la ficha (Casilla
					// origen)
					// Comprobamos que exista una ficha
					if (!casillaG.getCasilla().estaVacio()) {
						// Si NO está vacío. Creamos un nuevo movimiento con
						// esta casilla como origen.
						// Primero comprobamos si se está tomando una ficha
						// propia.
						if (this.casillaG.getCasilla().getFicha().getColor() != this.partida.getTurno_actual())
							throw new ExcepcionMovimiento("Solo puedes mover tus " + "propias fichas ("
									+ this.partida.getTurno_actual().toString() + ")");

						Movimiento mAux = new Movimiento();
						mAux.setOrigen(casillaG.getCasilla());
						mAux.setFicha(casillaG.getCasilla().getFicha());
						mAux.setColor(casillaG.getCasilla().getFicha().getColor());
						mAux.setEstadoInicial(mainf.getEstadoActual());
						mainf.setMov_actual(mAux);
						// Indicamos que ya hemos cogido una ficha.
						mainf.setClicked(true);
						if (!casillaG.getCasilla().estaVacio())
							mainf.setFichaEnMano(casillaG.getCasilla().getFicha());

						// Ahora coloreamos el fondo como amarillo.
						casillaG.getMainf().pintaFondos();
						casillaG.setBackground(Color.YELLOW);
					} else {
						// Si entramos aquí, es que la casilla estaba vacía.
						JOptionPane.showMessageDialog(null, "Debes seleccionar una de tus fichas!");
					}
				} else {
					// Aquí entramos si ya tenemos una ficha en la mano
					// Comprobamos que clickamos en una casilla vacía.
					// Primero comprobamos si seleccionamos la misma casilla
					if (casillaG.getCasilla().mismaCasilla(mainf.getFichaEnMano().getCasilla())) {
						mainf.pintaFondos();
						mainf.setClicked(false);
						mainf.setFichaEnMano(null);

					} else {
						if (casillaG.getCasilla().estaVacio()) {
							// Está vacío podemos mover la ficha.
							Movimiento mAux = mainf.getMov_actual();
							mAux.setDestino(casillaG.getCasilla());
							mAux.setPartida(mainf.getPartida());
							// Comprobamos el movimiento
							if (mAux.esValido()) {
								mainf.setEstadoActual(mAux.preMovimiento());
								mAux.ejecutaMovimiento(casillaG);
								mainf.getEstadoActual().checkDamas();
								mainf.pintaFondos();
								mainf.pintaEstado(mainf.getEstadoActual());
								mainf.setClicked(false);
								if (!mainf.getEstadoActual().puedeComer(mAux.getFicha()))
									partida.cambiaTurno();
								else {
									if (!mAux.getFicha().haComido()) {
										partida.cambiaTurno();
									}
								}

							} else
								JOptionPane.showMessageDialog(null, "Movimiento invalido");
						}
					}
					}
				}
				else{
					/*
					 * Entramos aquí si es el turno de la máquina
					 */
					IA ia;
					if(partida.getTurno_actual() == Colores.NEGRA)
						ia = partida.getiAN();
					 else ia = partida.getiAR();
												
						Estado eInicial = new Estado(mainf.getEstadoActual());
						Movimiento nextMov = ia.alfaBetaArbol(mainf.getEstadoActual());
						nextMov.setPartida(partida);
						nextMov.setEstadoInicial(eInicial);
						mainf.setEstadoActual(nextMov.preMovimiento());
						nextMov.ejecutaMovimiento(nextMov.getDestino().getcGrafica());
						mainf.getEstadoActual().checkDamas();
						mainf.pintaFondos();
						mainf.pintaEstado(mainf.getEstadoActual());
						if (!mainf.getEstadoActual().puedeComer(nextMov.getFicha()))
							partida.cambiaTurno();
						else {
							if (!nextMov.getFicha().haComido()) {
								partida.cambiaTurno();
							}
						}
				}
			}
		} catch (ExcepcionMovimiento em) {
			JOptionPane.showMessageDialog(null, em.getMessage());
			em.printStackTrace();
		}

		/*
		 * int ejeX,ejeY; ejeX = this.getCasillaG().getCasilla().getCoorX();
		 * ejeY = this.getCasillaG().getCasilla().getCoorY(); String s_color =
		 * " Rojo"; if((ejeX+ejeY)%2 == 0) s_color=" Blanco";
		 * 
		 * casillaG.getMainf().pintaFondos();
		 * casillaG.setBackground(Color.YELLOW);
		 * 
		 * System.out.println(ejeX+ "," +ejeY + s_color);
		 */
	}

// GETTERS & SETTERS

	/**
	 * @return La casillaG del objeto.
	 */
	public CasillaGrafica getCasillaG() {
		return casillaG;
	}

	/**
	 * @param casillaG
	 *            La casillaG a guardar.
	 */
	public void setCasillaG(CasillaGrafica casillaG) {
		this.casillaG = casillaG;
	}
}
