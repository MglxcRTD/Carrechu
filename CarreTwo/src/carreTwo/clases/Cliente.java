package carreTwo.clases;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Cliente implements Runnable {

	private long[] tiempos;
	private int[] nclientes;
	private Semaphore caja;

	public Cliente(Semaphore caja) {

		this.caja = caja;
		this.tiempos = tiempos;
		this.nclientes = nclientes;
	}

	@Override
	public void run() {
		long tInicial = 0;
		System.out.printf("Asignando caja  a %s...%n", Thread.currentThread().getName());

		try {

			this.caja.acquire();

			System.out.printf("%s: Realizando compra%n", Thread.currentThread().getName());
			tInicial = System.currentTimeMillis();
			Thread.sleep((long) (Math.random() * 20000));

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			long tFinal = System.currentTimeMillis();
			long totaltiempo = tFinal - tInicial;
			System.out.printf("%s atendido. Tiempo de espera: %d%n", Thread.currentThread().getName(),
					(totaltiempo / 1000));
			caja.release();

		}
	}

}
