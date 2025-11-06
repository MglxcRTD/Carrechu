package carreTwo;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

import carreTwo.clases.Cliente_UniCola;

public class Simulacion {

	public static void unacolamuchascajas(int nclientes, int ncajas) {
		Semaphore cajas_disponibles = new Semaphore(ncajas, true);
		long[] tiempos = new long[nclientes];
		Thread[] clientes = new Thread[nclientes];
		Queue<Integer> cajas_libres = new ConcurrentLinkedQueue<>();
		double media_total = 0;
		double desviacion_tipica = 0;

		System.out.println("===== CARRETWO =====");
		System.out.println();
		try {
			Thread.sleep(2000);
			System.out.println("Carretwo abre sus puertas...");
			Thread.sleep(2000);
			System.out.println();
			System.out.println("Preparando cajas...");
			Thread.sleep(2000);
			System.out.println();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 1; i <= ncajas; i++) {
			cajas_libres.add(i);
		}

		for (int i = 0; i < nclientes; i++) {
			clientes[i] = new Thread(new Cliente_UniCola(i, cajas_disponibles, cajas_libres, tiempos),
					"Cliente " + " " + (i + 1));
			clientes[i].start();
		}

		for (Thread t : clientes) {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		int sumatotal = 0;

		for (int i = 0; i < tiempos.length; i++) {
			sumatotal += tiempos[i];
		}

		media_total = ((double) sumatotal / nclientes) / 1000;

		double varianza = 0;

		for (int i = 0; i < tiempos.length; i++) {
			varianza += Math.pow(tiempos[i] - media_total, 2);
		}

		desviacion_tipica = Math.sqrt(varianza / tiempos.length) / 1000;

		System.out.println();
		System.out.println("CarreTwo cierra sus puertas");
		System.out.printf("Tiempo medio de espera: %.2fms.%n", media_total);
		System.out.printf("Desviacion Tipica: %.2fms.%n", desviacion_tipica);

	}

	public static void main(String[] args) {

		try {
			PrintStream salida = new PrintStream("simulaciones_cola_unica.txt");
			System.setOut(salida);
			for (int i = 1; i <= 5; i++) {
				System.out.printf("====SIMULACION %d====%n", i);
				unacolamuchascajas(20, 5);
				System.out.println();
			}

			salida.close();
			System.out.println("simulaciones completadas");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
