package carreTwo.clases;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Cliente_UniCola implements Runnable {

	private int id;
	private long[] tiempos;
	private final Semaphore cajas_disponibles;
	private final Queue<Integer> cajas_libres;

	public Cliente_UniCola(int id, Semaphore cajas_disponibles, Queue<Integer> cajas_libres, long[] tiempos) {

		this.id = id;
		this.cajas_disponibles = cajas_disponibles;
		this.cajas_libres = cajas_libres;
		this.tiempos = tiempos;

	}

	@Override
	public void run() {
		long tInicial = 0;
		int caja_asignada = 0;
		System.out.printf("Asignando caja  a %s...%n", Thread.currentThread().getName());

		try {

			this.cajas_disponibles.acquire();
			caja_asignada = this.cajas_libres.poll();
			System.out.printf("%s asignado a la caja %d%n", Thread.currentThread().getName(), caja_asignada);
			tInicial = System.currentTimeMillis();
			System.out.printf("%s: Realizando compra%n", Thread.currentThread().getName());
			Thread.sleep(new Random().nextInt(0, 10000));

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			long tFinal = System.currentTimeMillis();
			long totaltiempo = tFinal - tInicial;
			this.tiempos[this.id] = totaltiempo;

			System.out.printf("%s atendido en la caja %d. Tiempo de espera: %d ms.%n", Thread.currentThread().getName(),
					caja_asignada, (totaltiempo / 1000));

			this.cajas_libres.add(caja_asignada);
			this.cajas_disponibles.release();

		}
	}

}
