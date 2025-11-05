package carreTwo;

import java.util.concurrent.Semaphore;

import carreTwo.clases.Cliente;

public class Simulacion {

	public static void auxilio(int nclientes, int ncajas) {
		Semaphore caja = new Semaphore(ncajas, true);
		for (int i = 0; i < nclientes; i++) {
			Thread t = new Thread(new Cliente(caja), "Cliente " + " " + (i + 1));
			t.start();
		}
	}

	public static void main(String[] args) {

		auxilio(500, 10);
	}

}
