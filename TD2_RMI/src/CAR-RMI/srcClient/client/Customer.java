package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

import common.ISupplier;

/**
 * @author morat
 */
public class Customer extends Thread {
	private static int nbQuestion = 0;
	private static String[] properties;

	static {
		properties = System.getProperties().keySet().toArray(new String[] {});
		for (java.lang.reflect.Method m : ISupplier.class.getDeclaredMethods()) {
			if (m.getName().equals("question")) {
				nbQuestion++;
			}
		}
	}

	int num, client;
	private ISupplier obj;
	String ou, qui;

	/**
	 *
	 * @param ou
	 *            désignation de la machine distante
	 * @param qui
	 *            nom générique du Provider
	 * @param num
	 *            numémo spécifique du Provider
	 * @param client
	 *            numéro du C
	 */
	public Customer(String ou, String qui, int num, int client) {
		this.ou = ou;
		this.qui = qui;
		this.num = num;
		this.client = client;
	}

	@Override
	public void run() {
		try {
			synchronized (Customer.class) {
				System.out.print(this + "->" + "://" + ou + "/" + qui + num);
				// A COMPLETER : ACQUISITION DE L'OBJET DISTANT
				Registry registry = LocateRegistry.getRegistry(1099);
				this.obj = (ISupplier) registry.lookup(this.qui + this.client);
				System.out.println(" est lié a " + obj.name());
			}
			try {
				sleep((int) (Math.random() * 1000));
			} catch (Exception e) {
			}
			synchronized (Customer.class) {
				switch (new Random().nextInt(nbQuestion)) {
				case 0: { // cas de l'utilisation de question()
					System.out.println(this + "->" + obj.name() + ".question() => " + obj.question());
					break;
				}
				case 1: { // cas de l'utilisation de question(String)
					String s = select();
					System.out.println(this + "->" + obj.name() + ".question(" + s + ") => " + obj.question(s));
					break;
				}
				default: {
					System.err.println(this + "->" + obj.name() + " question inconnue");
				}
				}
			}
		} catch (Throwable e) {
			synchronized (Customer.class) {
				System.out.println("Provider error: " + e);
			}
		}
	}

	private String select() {
		return properties[(int) (Math.random() * properties.length)];
	}

	@Override
	public String toString() {
		return "Customer" + client;
	}
}
