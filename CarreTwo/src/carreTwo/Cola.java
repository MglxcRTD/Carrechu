package carreTwo;

public class Cola<T> {

	private T[] datos;
	private int entrada, salida, ocupado, size;

	public Cola(int size) {
		// this.datos = new T[size];
		this.size = size;
		this.ocupado = ocupado;
		this.entrada = 1;
		this.salida = 1;
	}

	public synchronized void push(T dato) {
		try {
			while (this.ocupado == this.size) {
				this.wait();
				this.datos[this.entrada] = dato;
				this.entrada = (this.entrada + 1) % this.size;
				this.ocupado++;
				this.notify();

			}
		} catch (InterruptedException e) {
			System.err.println(e);
		}
	}

	public synchronized T pop() {
		return null;
	}
}
