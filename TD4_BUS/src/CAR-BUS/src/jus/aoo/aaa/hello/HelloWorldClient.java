package jus.aoo.aaa.hello;

import fr.dyade.aaa.agent.Agent;
import fr.dyade.aaa.agent.AgentId;
import fr.dyade.aaa.agent.Notification;

public class HelloWorldClient extends Agent {
	/**
	 *
	 */
	private static final long serialVersionUID = 3016420964430952183L;

	public AgentId dest;

	public HelloWorldClient(short to, AgentId aDest) {
		super(to);
		this.dest = aDest;
	}

	@Override
	public void react(AgentId from, Notification n) throws Exception {
		if (n instanceof StartNot) {
			sendTo(dest, new HelloWorldNot("HelloWorld"));
		} else {
			super.react(from, n);
		}
	}
}
