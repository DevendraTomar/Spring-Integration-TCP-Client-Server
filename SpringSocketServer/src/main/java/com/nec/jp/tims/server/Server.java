package com.nec.jp.tims.server;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.util.TestingUtilities;

public class Server {

	public static void main(String[] args) {

		final GenericXmlApplicationContext context = Server.setupContext();
		final AbstractServerConnectionFactory crLfServer = context
				.getBean(AbstractServerConnectionFactory.class);

		System.out.println("Server is running !");
		TestingUtilities.waitListening(crLfServer, 10000L);

	}

	public static GenericXmlApplicationContext setupContext() {
		final GenericXmlApplicationContext context = new GenericXmlApplicationContext();

		context.load("classpath:/applicationConfig.xml");
		context.registerShutdownHook();
		context.refresh();

		return context;
	}

}
