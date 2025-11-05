package carreTwo.clases;

import java.util.concurrent.ArrayBlockingQueue;

public class Cliente implements Runnable {

	private ArrayBlockingQueue<Cliente> clientes;
	private long[] tiempos;
	private int[] nclientes;
	

	public Cliente(ArrayBlockingQueue<Cliente> clientes, long[] tiempos, int[] nclientes) {

		this.clientes = clientes;
		this.tiempos = tiempos;
		this.nclientes = nclientes;
	}

	@Override
	public void run() {
		

	}

}
