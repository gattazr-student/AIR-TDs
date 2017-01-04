package jus.aoo.aaa.hello;

import fr.dyade.aaa.agent.Agent;
import fr.dyade.aaa.agent.AgentId;
import fr.dyade.aaa.agent.Notification;

public class HelloWorld extends Agent {

	/**
	 *
	 */
	private static final long serialVersionUID = -1766467598254006689L;

	public HelloWorld(short to) {
		super(to);
	}

	public HelloWorld(short to, String n) {
		super(to, n);
	}

	@Override
	public void react(AgentId from, Notification n) throws Exception {

		if (n instanceof HelloWorldNot) {
			System.out.println(((HelloWorldNot) n).msg);
		} else {
			super.react(from, n);
		}
	}

}