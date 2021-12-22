package buscaminas;

import java.util.*;
import java.util.function.Consumer;
public class TableroBuscaminas {
	
	private Casilla [][] casillas;
	private int numFilas,numColumnas,numMinas;
	private int numCasillasAbiertas;
	private Consumer<ArrayList<Casilla>> eventoPartidaPerdida;
	private Consumer<Casilla> eventocasillaAbierta;
	private Consumer<ArrayList<Casilla>> eventoPartidaGanada;
	
	public TableroBuscaminas(int filas,int columnas,int minas) {
		numFilas = filas;
		numColumnas = columnas;
		numMinas=minas;
		inicializarCasillas();
	}
	public void inicializarCasillas() {
		casillas = new Casilla[numFilas][numColumnas];
		for(int i=0;i<casillas.length;i++) {
			for(int j=0;j<casillas[0].length;j++) {
				casillas[i][j] = new Casilla(i,j);
			}
		}
		generarMinas();
		generarCasillasEspeciales();
	}
	public void generarMinas() {
		Random rand = new Random();
		int minasCreadas=0;
		while(minasCreadas<numMinas) {
			int fila = rand.nextInt(casillas.length);
			int col = rand.nextInt(casillas[0].length);
			if(!casillas[fila][col].esMina()) {
				casillas[fila][col].setMina(true);
				minasCreadas++;
			}
		}
		actualizarMinasAlrededor();
	}
	public void generarCasillasEspeciales() {
		Random rand = new Random();
		int numMinasEspeciales = numMinas/2; 
		int minasEsp=0;
		while(minasEsp<numMinasEspeciales) {
			int fila = rand.nextInt(casillas.length);
			int col = rand.nextInt(casillas[0].length);
			if(!casillas[fila][col].esMina()) {
				casillas[fila][col].setComodin(true);
				minasEsp++;
			}
		}
	}
	public void actualizarMinasAlrededor() {
		for(int i=0;i<casillas.length;i++) {
			for(int j=0;j<casillas[0].length;j++) {
				if(casillas[i][j].esMina()) {
					ArrayList<Casilla> casillasAlrededor = getCasillasAlrededor(i, j);
					for(Casilla c: casillasAlrededor) {
						c.incremenetarMinasAlrededor();
					}
				}
			}
		}
	}
	public ArrayList<Casilla> getCasillasAlrededor(int fila,int col){
		ArrayList<Casilla> listaCasillas = new ArrayList<Casilla>();
		int tmpFila;
		int tmpColumna;
		for(int i=-1;i<2;i++) {
			tmpFila = fila+i;
			for(int j=-1;j<2;j++) {
				tmpColumna= col+j;
				if(tmpFila==fila && tmpColumna==col) {
					continue; 
				}
				if((tmpFila>=0 && tmpFila<casillas.length) && (tmpColumna>=0 && tmpColumna<casillas[0].length)) {
					listaCasillas.add(casillas[tmpFila][tmpColumna]);
				}
			}
		}
		return listaCasillas;
		
	}
	public ArrayList<Casilla> getCasillasConMina(){
		ArrayList<Casilla> casillasConMina = new ArrayList<Casilla>();
		for(int i=0;i<casillas.length;i++) {
			for(int j=0;j<casillas.length;j++) {
				if(casillas[i][j].esMina()) {
					casillasConMina.add(casillas[i][j]);
				}
			}
		}
		return casillasConMina;
	}
	public void seleccionarCasilla(int fila,int col) {
		eventocasillaAbierta.accept(casillas[fila][col]);
		if(casillas[fila][col].esMina()) {
			ArrayList<Casilla> casillasConMina = getCasillasConMina();
			eventoPartidaPerdida.accept(casillasConMina);
		}
		else if(casillas[fila][col].getNumMinasAlrededor()==0) {
			marcarCasilla(fila, col);
			ArrayList<Casilla> casillasAlrededor = getCasillasAlrededor(fila, col);
			for(Casilla c: casillasAlrededor) {
				if(!c.estaAbierta()) {
					seleccionarCasilla(c.getFila(),c.getColumna()); 
				}
			}
		}
		else {
			marcarCasilla(fila, col);
		}
		if(partidaGanada()) {
			 eventoPartidaGanada.accept(getCasillasConMina());
		}
	}
	public void marcarCasilla(int fila, int col) {
		if(!casillas[fila][col].estaAbierta()) {
			casillas[fila][col].setAbierta(true);
			numCasillasAbiertas++;
		}
	}
	public boolean partidaGanada() {
		return numCasillasAbiertas>=numFilas*numColumnas-numMinas;
	}
	public void setEventoPartidaPerdida(Consumer<ArrayList<Casilla>> evento) {
		this.eventoPartidaPerdida = evento;
	}
	public void setEventoCasillaAbierta(Consumer<Casilla> evento) {
		this.eventocasillaAbierta = evento;
	}
	public void setEventoPartidaGanada(Consumer<ArrayList<Casilla>> evento) {
		this.eventoPartidaGanada = evento;
	}
	public void printTablero() {
		for(Casilla [] fila: casillas) {
			for(Casilla c: fila) {
				if(c.esMina()) {
					System.out.print("X ");
				}
				else {
					System.out.print("O ");
				}
			}
			System.out.println();
		}
	}	
	public void printPistas() {
		for(Casilla [] fila: casillas) {
			for(Casilla c: fila) {
				System.out.print(c.getNumMinasAlrededor()+" ");
			}
			System.out.println();
		}
	}	
	public static void main(String[] args) {
		TableroBuscaminas tablero = new TableroBuscaminas(4, 5, 6);
		tablero.printTablero();
		System.out.println("/////");
		tablero.printPistas();
	}
	
	

}