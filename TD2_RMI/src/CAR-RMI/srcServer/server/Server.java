package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author morat
 */
public class Server {
	/**
	 * @param args
	 */
	public static void main(final String args[]) {

		String nom = "";
		int nombre = 3;
		int port = 1099;

		Registry registry = null;

		// récupération des arguments
		if (args.length > 0) {
			if (args.length != 3) {
				System.out.println("Server <nom générique des objets distants> <nombre de noms> <port du registry>");
				System.exit(1);
			}

			try {
				nom = args[0];
				port = Integer.parseInt(args[1]);
				nombre = Integer.parseInt(args[2]);

			} catch (Exception e) {
				System.out.println("Server <nom générique des objets distants> <nombre de noms> <port du registry>");
				System.exit(1);
			}
		}

		// INSTALLATIOND'UN SECURITYMANAGER
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		// A COMPLETER : MISE EN PLACE DU REGISTRY
		try {
			registry = LocateRegistry.createRegistry(port);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

		try {
			for (int i = 0; i < nombre; i++) {
				// CONSTRUCTION ET EXPORTATION DES OBJETS DISTANTS
				Supplier wSupl = new Supplier(i);
				registry.bind(nom + i, wSupl);
			}
			System.out.println("Tous les objets sont enregistrés dans le serveur d'objets distants");
		} catch (Exception e) {
			System.out.println("Server err: " + e);
		}
	}
}
