package carreTwo.clases;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class Cliente_MultiCola implements Runnable {

	private final int id;
	private final Semaphore[] cajas;
	private final AtomicIntegerArray colas;
	private final long[] tiempos;

	public Cliente_MultiCola(int id, Semaphore[] cajas, AtomicIntegerArray colas, long[] tiempos) {
		this.id = id;
		this.cajas = cajas;
		this.colas = colas;
		this.tiempos = tiempos;
	}

	@Override
	public void run() {

		try {
			Thread.sleep(new Random().nextInt(0,2000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long tInicial = 0;

		int mejorCaja = 0;
		int minCola = Integer.MAX_VALUE;
		for (int i = 0; i < this.cajas.length; i++) {
			int colaActual = this.colas.get(i);
			if (colaActual < minCola) {
				minCola = colaActual;
				mejorCaja = i;
			}
		}

		System.out.printf("Asignando caja a %s...%n", Thread.currentThread().getName());

		try {
			this.colas.incrementAndGet(mejorCaja);
			this.cajas[mejorCaja].acquire();
			System.out.printf("Cliente %d asignado a caja %d (cola actual: %d clientes)%n", id, mejorCaja, minCola);
			tInicial = System.currentTimeMillis();
			System.out.printf("Cliente %d realizando compra en caja %d%n", id, mejorCaja);
			Thread.sleep(new Random().nextInt(0, 10000));

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			long tFinal = System.currentTimeMillis();
			long totaltiempo = tFinal - tInicial;
			this.tiempos[this.id] = totaltiempo;

			System.out.printf("Cliente %d atendido. Tiempo de espera: %d ms%n", id, (totaltiempo / 1000));
			this.colas.decrementAndGet(mejorCaja);
			cajas[mejorCaja].release();
		}
	}
}
