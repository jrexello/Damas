package es.unex.cum.jrevello.si.damas.interfaz;

import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class DialogoInicio extends JFrame {

	//ATRIBUTOS
	

	//CONSTRUCTORES
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4812411496320606735L;



	public DialogoInicio() {
	
		this.setTitle("Damas");
		this.setSize(200, 200);
		this.setLocationRelativeTo(null); // Con esto lo colocamos en el centro
											// de la pantalla
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setContentPane(crearDialogo());
		this.setVisible(true);
		
	}



	//MÉTODOS
	
	private JPanel crearDialogo(){
		JPanel res = new JPanel();
		/**
		 * Es el número de seleccionables que habrá.
		 * (HVH, HVM, MVM)
		 */
		final int n_botones = 3;
		JRadioButton[] radioButtons = new JRadioButton[n_botones];
		final ButtonGroup grupoBotones = new ButtonGroup();
		
		JLabel label = new JLabel("Seleccione un modo de juego:");
		res.add(label);
		
		radioButtons[0] = new JRadioButton("Humano versus Humano");
		radioButtons[0].setActionCommand("HvH");
		//res.add(radioButtons[0]);
		radioButtons[1] = new JRadioButton("Humano versus Máquina");
		radioButtons[1].setActionCommand("HvM");
		radioButtons[2] = new JRadioButton("Máquina versus Máquina");
		radioButtons[2].setActionCommand("MvM");
		
		for(int i=0; i<n_botones; i++){
			grupoBotones.add(radioButtons[i]);
			radioButtons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			res.add(radioButtons[i]);
		}
		radioButtons[0].setSelected(true);
		
		JButton b_aceptar = new JButton("Aceptar");
		b_aceptar.addActionListener(new BotonesHandler(this, grupoBotones));
		res.add(b_aceptar);
		
		
		
		return res;
	}
	
	public JPanel preguntaDificultad(BotonesHandler bh){
		JPanel res = new JPanel();
		JLabel label = new JLabel("Seleccione una dificultad para la IA:");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		res.add(label);
		
		String[] str_dificultades = { "Facil", "Medio", "Dificil"};
		JComboBox<String> combo_dificultades = new JComboBox<String>(str_dificultades);
		combo_dificultades.setSelectedIndex(1);
		bh.setCombo(combo_dificultades);
		combo_dificultades.setAlignmentX(Component.CENTER_ALIGNMENT);
		res.add(combo_dificultades);
		
		JButton b_aceptar = new JButton("Aceptar");
		b_aceptar.addActionListener(bh);
		b_aceptar.setAlignmentX(Component.CENTER_ALIGNMENT);
		res.add(b_aceptar);
		this.setContentPane(res);
		this.setVisible(true);
		return res;
	}

	//GETTERS & SETTERS
}
