/**
 * J<i>ava</i> U<i>tilities</i> for S<i>tudents</i>
 */
package jus.aor.printing;

import java.lang.management.ManagementFactory;
import java.util.Date;

/**
 * Représentation d'une identification de Job (travail d'impression),
 * l'identification de la JVM cliente et la date en millisecondes doivent
 * suffire pour être unique.
 *
 * @author Morat
 */
public class JobKey {
	private static String SEPARATOR = new String("::");

	/** la date */
	public long date;

	/** l'identification de la jvm */
	public String serverId;

	/**
	 * construit un JobKey
	 */
	public JobKey() {
		this(new Date().getTime());
	}

	/**
	 * Construit un JobKey à partir du tableau de bytes représentant la forme
	 * textuelle du JobKey
	 *
	 * @param marshal
	 *            le tableau de bytes de la forme textuelle
	 */
	public JobKey(byte[] marshal) {
		// -----------------------------------------------------------------------------
		String wString = new String(marshal);
		String[] wSplitted = wString.split(SEPARATOR);
		this.serverId = wSplitted[0];
		this.date = Long.parseLong(wSplitted[1]);
	}

	/**
	 * construit un JobKey ayant la date date fournie.
	 *
	 * @param date
	 *            la date du job
	 */
	public JobKey(long date) {
		this.serverId = ManagementFactory.getRuntimeMXBean().getName();
		this.date = date;
	}

	/**
	 * L'équivalence de 2 JobKey, si jobId est vide il n'est pas pris en compte,
	 * si date est 0 elle n'est pas prise en compte.
	 *
	 * @param other
	 *            l'autre opérande
	 * @return vrai si l'équivalence est vérifiée, faux sinon.
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof JobKey)) {
			return false;
		}
		JobKey o = (JobKey) other;
		if (!serverId.equals(o.serverId)) {
			return false;
		}
		if (o.date == 0) {
			return true;
		}
		return date == o.date;
	}

	/**
	 * restitue le tableau de bytes de la forme textuelle.
	 *
	 * @return le tableau de bytes de la forme textuelle
	 */
	public byte[] marshal() {
		// -----------------------------------------------------------------------------
		return toString().getBytes();
	}

	/**
	 * la forme textuelle : "<<b>serverId</b>>¤<<b>date</b>>" où <<b>X</b>> est
	 * la repésentation textuelle de l'attribut correspondant
	 */
	@Override
	public String toString() {
		return (serverId + SEPARATOR + date);
	}
}
