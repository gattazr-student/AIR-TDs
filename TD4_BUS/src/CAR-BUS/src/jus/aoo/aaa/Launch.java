package jus.aoo.aaa;

import fr.dyade.aaa.agent.Agent;
import fr.dyade.aaa.agent.AgentServer;
import fr.dyade.aaa.agent.Channel;
import jus.aoo.aaa.hello.HelloWorld;
import jus.aoo.aaa.hello.HelloWorldClient;
import jus.aoo.aaa.hello.StartNot;

public class Launch {

	public static void main(String args[]) {
		try {
			AgentServer.init(args); // initialisation du serveur

			Agent wAg = new HelloWorld(Short.parseShort(args[0]));
			wAg.deploy();

			Agent wAgClient = new HelloWorldClient(Short.parseShort(args[0]), wAg.getId());
			wAgClient.deploy();

			// Send a notification
			Channel.sendTo(wAgClient.getId(), new StartNot());

			/*
			 * création des objets localement, réalisation des initialisations
			 * nécessaires et déploiement de ceux-ci sur les serveurs qui leur
			 * ont * été respectivement affectés.
			 */
			AgentServer.start(); // lancement du serveur
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
}
