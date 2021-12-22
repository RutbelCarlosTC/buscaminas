package buscaminas;

public class Casilla {
	private int fila,columna;
	private boolean esMina;
	private int numMinasAlrededor;
	private boolean estaAbierta;
	private boolean esComodin;

	public Casilla(int f, int c) {
		setFila(f);
		setColumna(c);
		setMina(false);
		setAbierta(false);
		setComodin(false);
	}
	public int getFila() {
		return fila;
	}
	public int getColumna() {
		return columna;
	}
	public void setFila(int f) {
		fila = f;
	}
	public void setColumna(int c) {
		columna = c;
	}
	public boolean esMina() {
		return esMina;
	}
	public boolean estaAbierta() {
		return estaAbierta;
	}
	public void setAbierta(boolean val) {
		estaAbierta =val;
	}
	public void setMina(boolean val) {
		esMina =val;
	}
	public void setComodin(boolean val) {
		esComodin=val;
	}
	public void incremenetarMinasAlrededor() {
		numMinasAlrededor = getNumMinasAlrededor() + 1;
	}
	public int getNumMinasAlrededor() {
		return numMinasAlrededor;
	}
	
	
}
