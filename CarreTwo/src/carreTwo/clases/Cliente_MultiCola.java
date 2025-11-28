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
			Thread.sleep(new Random().nextInt(0, 51));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		long tInicial = 0;
		int mejorCaja = 0;

		synchronized (this.colas) {
			int min = Integer.MAX_VALUE;
			int index = 0;
			for (int i = 0; i < colas.length(); i++) {
				int valor = colas.get(i);
				if (valor < min) {
					min = valor;
					index = i;
				}
			}
			mejorCaja = index;
			colas.incrementAndGet(mejorCaja);
		}

		System.out.printf("Asignando caja a %s...%n", Thread.currentThread().getName());

		try {
			cajas[mejorCaja].acquire();
			System.out.printf("Cliente %d asignado a caja %d (cola actual: %d clientes)%n", id, mejorCaja,
					colas.get(mejorCaja));
			tInicial = System.currentTimeMillis();
			System.out.printf("Cliente %d realizando compra en caja %d%n", id, mejorCaja);
			Thread.sleep(new Random().nextInt(0, 51));

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			long tFinal = System.currentTimeMillis();
			long totaltiempo = tFinal - tInicial;

			synchronized (tiempos) {
				tiempos[id] = totaltiempo;
			}

			colas.decrementAndGet(mejorCaja);
			cajas[mejorCaja].release();

			System.out.printf("Cliente %d atendido. Tiempo de espera: %d ms%n", id, totaltiempo);
		}
	}
}
