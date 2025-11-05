package carreTwo.clases;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Cliente implements Runnable {

	private long[] tiempos;
	private int[] nclientes;
	private Semaphore[] cajas;

	public Cliente(Semaphore[] cajas, long[] tiempos, int[] nclientes) {

		this.cajas = cajas;
		this.tiempos = tiempos;
		this.nclientes = nclientes;
	}

	@Override
	public void run() {
		long tInicial = 0;
		System.out.printf("Asignando caja a %s...%n", Thread.currentThread().getName());
		Semaphore caja = this.cajas[new Random().nextInt(this.cajas.length)];

		try {

			caja.acquire();
			tInicial = System.currentTimeMillis();

			System.out.printf("%s: Realizando compra%n", Thread.currentThread().getName());
			Thread.sleep(1000, 10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			long tFinal = System.currentTimeMillis();
			long totaltiempo = tFinal - tInicial;
			System.out.printf("%s atendido. Tiempo de espera: %d%n", Thread.currentThread().getName(), totaltiempo);
			caja.release();
			
		}
	}

}
