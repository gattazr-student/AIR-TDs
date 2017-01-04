package jus.aor.printing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

/**
 * Représentation du Client du serveur d'impression.
 *
 * @author Morat
 */
public class Client {
	/** 1 second timeout for waiting replies */
	protected static final int TIMEOUT = 1000;

	/**
	 * @param args
	 */
	public static void main(String args[]) {
		new Client();
	}

	/** le listener UDP est-il vivant */
	private boolean alive = true;
	/** l'interfaçage avec la console du client */
	private ClientGUI GUI;
	/** la machine supportant le serveur d'impression */
	private String host = "localhost";
	/** le logger du client */
	private Logger log = Logger.getLogger("Jus.Aor.Printing.Client", "jus.aor.printing.Client");
	/** le port d'installation du serveur d'impression */
	private int port = 3000;

	/**
	 * construction d'un client
	 */
	public Client() {
		GUI = new ClientGUI(this);
		log.setLevel(Level.INFO_2);
	}

	/**
	 * Se déconnecte d'un serveur d'impression, on ne peut plus envoyer de
	 * requête au serveur.
	 */
	public void deselectPrinter() {
		// arrêt du listener de spooler
		// -----------------------------------------------------------------------------
		// A COMPLETER
	}

	/**
	 * @param key
	 *            la cle du du job
	 * @throws IOException
	 */
	private void jobTerminated(JobKey key) throws IOException {
		GUI.removePrintList(key);
	}

	/**
	 * protocole du print d'un fichier
	 *
	 * @param f
	 *            le fichier à imprimer
	 */
	private void onePrint(File f) {
		try (InputStream fis = new FileInputStream(f)) {
			Notification ret = null;
			Socket wServeur;
			// --------------------------------------------------------------------------
			// A COMPLETER
			try {
				wServeur = new Socket(this.host, this.port);
				log.log(java.util.logging.Level.INFO,
						"Client.selectPrinter.OpenConnexion " + wServeur.getInetAddress());
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}

			JobKey wJobKey = new JobKey();
			try {
				TCP.writeProtocole(wServeur, Notification.QUERY_PRINT);
				TCP.writeJobKey(wServeur, wJobKey);
				System.out.println(wJobKey);
				TCP.writeData(wServeur, fis, (int) f.length());
			} catch (IOException e) {
				e.printStackTrace();
			}

			ret = TCP.readProtocole(wServeur);

			if (ret == Notification.REPLY_PRINT_OK) {
				// ------------------------------------------------------------------------
				// A COMPLETER
				// Dans le cas où tout est correct on ajoute le job à la liste
				// des encours.
				log.log(Level.INFO_3, "Client.QueryPrint.Processing", wJobKey);
				GUI.addPrintList(wJobKey);
				ret = TCP.readProtocole(wServeur);
				if (ret == Notification.REPLY_PRINT_ENDED) {
					GUI.removePrintList(wJobKey);
				}

			} else {
				log.log(java.util.logging.Level.WARNING, "Client.QueryPrint.Failed", ret.toString());
			}
		} catch (NumberFormatException e) {
			log.log(java.util.logging.Level.SEVERE, "Client.QueryPrint.Port.Error", e.getMessage());
		} catch (UnknownHostException e) {
			log.log(java.util.logging.Level.SEVERE, "Client.QueryPrint.Remote.Error", e.getMessage());
		} catch (IOException e) {
			log.log(java.util.logging.Level.SEVERE, "Client.QueryPrint.IO.Error", e.getMessage());
		}
	}

	/**
	 * protocole de délai d'impression
	 *
	 * @param key
	 *            la clé du job
	 */
	public void queryJobs(JobKey key) {
		// --------------------------------------------------------------------------
		// A COMPLETER
	}

	/**
	 * Réalise l'émission quasi-simultanée de n requêtes d'impression à l'aide
	 * de onePrint
	 *
	 * @param f
	 *            le fichier à imprimer
	 * @param n
	 *            nombre de requêtes d'impression à faire
	 */
	public void queryPrint(final File f, int n) {
		// --------------------------------------------------------------------------
		onePrint(f);

	}

	/**
	 * protocole du server status
	 */
	public void queryStatus() {
		// --------------------------------------------------------------------------
		// A COMPLETER
	}

	/**
	 * L'écoute des communications du spooler
	 */
	protected void runUDP() {
		// -----------------------------------------------------------------------------
		// A COMPLETER
	}

	/**
	 * Choix d'une imprimante distante en indiquant l'@ du servive d'impression
	 * associé : se connecte à ce serveur d'impression, dès lors on peut envoyer
	 * des requêtes à celui-ci.
	 */
	public void selectPrinter() {
		// constuction du listener de spooler
		// -----------------------------------------------------------------------------
		// A COMPLETER
	}

	/**
	 * Fixe le remote host
	 *
	 * @param host
	 *            le remote host supportant le serveur d'impression
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Fixe le port du serveur d'impression
	 *
	 * @param port
	 *            le port supportant le serveur d'impression
	 */
	public void setPort(int port) {
		this.port = port;
	}
}
