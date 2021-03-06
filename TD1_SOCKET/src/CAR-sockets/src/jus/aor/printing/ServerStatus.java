/**
 * J<i>ava</i> U<i>tilities</i> for S<i>tudents</i>
 */
package jus.aor.printing;

/**
 * Représentation du status du serveur
 *
 * @author Morat
 */
public class ServerStatus {
	/** la durée estimée pour réaliser l'ensemble de ces impressions */
	public long delay;
	/** le nombre de jobs en attente d'impression sur le serveur */
	public int nbJobs;

	/**
	 * Construction d'un ServerStatus à partir du tableau de bytes des
	 * représentations hexadécimales des attributs séparées par ¤
	 *
	 * @param data
	 *            le tableau de sa représentation textuelle
	 */
	public ServerStatus(byte[] data) {
		// --------------------------------------------------------------------------
		// A COMPLETER
	}

	/**
	 * Construction du status courant du serveur
	 *
	 * @param nbJobs
	 *            le nombre de jobs
	 * @param delay
	 *            le delai estimé
	 */
	public ServerStatus(int nbJobs, long delay) {
		this.nbJobs = nbJobs;
		this.delay = delay;
	}

	/**
	 * mise sous forme d'un tableau de bytes des représentations hexadécimales
	 * des attributs du ServerStatus séparées par ¤
	 *
	 * @return le tableau de bytes des représentations hexadécimales du
	 *         ServerStatus séparées par ¤
	 */
	public byte[] marshal() {
		// --------------------------------------------------------------------------
		// A COMPLETER
		return null;
	}

	/**
	 * la représentation textuelle de la forme :
	 * "<<b>nbJobs</b>>¤<<b>delay</b>>" où <<b>X</b>> est la repésentation
	 * textuelle de l'attribut correspondant
	 */
	@Override
	public String toString() {
		return nbJobs + "¤" + delay;
	}
}
