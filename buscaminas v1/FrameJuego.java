package buscaminas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FrameJuego extends JFrame {
	
	private int numFilas=10,numColumnas=10,numMinas = 15;
	private JButton[][] botones;
	private static final int ANCHO = 600;
	private static final int ALTO = 600;
	private TableroBuscaminas tableroBuscaminas;
	
	public FrameJuego(){
		setTitle("tablero buscaminas");
		setSize(ANCHO,ALTO);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(numFilas, numColumnas));
		cargarBotones();
		crearTableroBuscaminas();
		setVisible(true);
	}
	public void crearTableroBuscaminas() {
		tableroBuscaminas = new TableroBuscaminas(numFilas,numColumnas,numMinas);
		tableroBuscaminas.setEventoPartidaPerdida(new Consumer<ArrayList<Casilla>>() {
			@Override
			public void accept(ArrayList<Casilla> t) {
				for(Casilla mina: t) {
					botones[mina.getFila()][mina.getColumna()].setText("*");
				}
				
			}
		});
		tableroBuscaminas.printTablero();
		System.out.println();
		tableroBuscaminas.printPistas();
		tableroBuscaminas.setEventoCasillaAbierta(new Consumer<Casilla>() {
			@Override
			public void accept(Casilla t) {
				botones[t.getFila()][t.getColumna()].setEnabled(false);
				String minasAlrededor = String.valueOf(t.getNumMinasAlrededor());
				if(minasAlrededor.equals("0")) {
					minasAlrededor="";
				}
				botones[t.getFila()][t.getColumna()].setText(minasAlrededor) ;;
			}
		});
		
		tableroBuscaminas.setEventoPartidaGanada(new Consumer<ArrayList<Casilla>>() {
			@Override
			public void accept(ArrayList<Casilla> t) {
				for(Casilla mina: t) {
					botones[mina.getFila()][mina.getColumna()].setText(":)");
				}
				
			}
		});
	}
	public void cargarBotones() {
		Listener listener= new Listener();
		botones = new JButton[numFilas][numColumnas];
		for(int i=0;i<botones.length;i++) {
			for(int j=0;j<botones[0].length;j++) {
				botones[i][j] = new JButton();
				botones[i][j].setName(i+","+j);
				//botones[i][j].setBorder(null);
				add(botones[i][j]);
				botones[i][j].addActionListener(listener);
			}
		}
	}
	private class Listener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton)e.getSource();
			String[] coordenada = btn.getName().split(",");
			int posFila = Integer.parseInt(coordenada[0]);
			int posCol= Integer.parseInt(coordenada[1]);
			//JOptionPane.showMessageDialog(rootPane, posFila+","+posCol);
			tableroBuscaminas.seleccionarCasilla(posFila, posCol);			
		}
		
	}
	public static void main(String[] args) {
		new FrameJuego();

	}

}
