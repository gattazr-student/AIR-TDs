package jus.aoo.aaa.hello;

import fr.dyade.aaa.agent.Notification;

public class HelloWorldNot extends Notification {
	/**
	 *
	 */
	private static final long serialVersionUID = 387177884580245760L;

	public String msg = "helloworld";

	public HelloWorldNot(String msg) {
		this.msg = msg;
	}
}
