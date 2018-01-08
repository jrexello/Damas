package es.unex.cum.jrevello.si.damas.interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public class MenuHandler implements ActionListener {

	private MainFrame mainf;
	
	public MenuHandler(MainFrame m) {
		this.mainf = m;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) e.getSource();
		
		if(source.getText().equals("Contar Fichas")){
			System.out.println("Turno "+mainf.getPartida().getN_turno()+"\nFichas Blancas: "+mainf.getEstadoActual().cuentaBlancas()+"\nFichas Negras: "
					+mainf.getEstadoActual().cuentaNegras()+"\n");
		}
		else if(source.getText().equals("Listar Movimientos")){
			System.out.println(mainf.getEstadoActual().pintaTodoMovimiento()+"\n");
		}

	}
}
