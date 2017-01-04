package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.ISupplier;

public class Supplier extends UnicastRemoteObject implements ISupplier {

	/**
	 *
	 */
	private static final long serialVersionUID = 7032974786658867290L;

	private int pId;

	protected Supplier(int aId) throws RemoteException {
		super();
		this.pId = aId;
	}

	@Override
	public String name() throws RemoteException {
		return toString();
	}

	@Override
	public String question() throws RemoteException {
		return String.format("os.name=[%s]", (String) System.getProperties().get("os.name"));
	}

	@Override
	public String question(String s) throws RemoteException {
		return String.format("%s=[%s]", s, System.getProperties().get(s));
	}

	@Override
	public String toString() {
		return new String("supplier" + this.pId);
	}

}
