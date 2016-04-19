package com.nec.jp.tims.client;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.nec.jp.tims.inter.ServiceInterface;

public final class Client {

	/**
	 * Load the Spring Integration Application Context
	 * 
	 * @param args
	 *            - command line arguments
	 */
	public static void main(final String... args) {

		final Scanner scanner = new Scanner(System.in);

		System.out
				.println("Please enter the location where you would like to place the file :");

		String fileLocation = scanner.nextLine();

		System.out.println("###############################################");
		System.out.println("1. dummy.dat");
		System.out.println("2. dummy.json");
		System.out.println("3. dummy.txt");
		System.out.println("###############################################");

		final GenericXmlApplicationContext context = setupContext();

		final ServiceInterface gateway = context
				.getBean(ServiceInterface.class);

		while (true) {

			System.out
					.println("Please enter the index of the requested file :");

			final String input = scanner.nextLine();

			if ("q".equals(input.trim())) {
				break;
			} else {
				final String result = gateway.send(input);
				String file = FileMapping.getMapper().get(
						Integer.parseInt(input));

				try {
					FileOutputStream fileOutputStream = new FileOutputStream(
							fileLocation + "/" + file);
					fileOutputStream.write(result.getBytes());
					fileOutputStream.flush();
					fileOutputStream.close();
				} catch (IOException exception) {
					exception.printStackTrace();
				}

				System.out.println("File Successfully Downloaded");
			}
		}

		scanner.close();

		System.out.println("Exiting application...bye.");
		System.exit(0);

	}

	public static GenericXmlApplicationContext setupContext() {

		final GenericXmlApplicationContext context = new GenericXmlApplicationContext();

		/*
		 * System.out.print("Detect open server socket..."); int
		 * availableServerSocket = SocketUtils.findAvailableServerSocket(5678);
		 * final Map<String, Object> sockets = new HashMap<String, Object>();
		 * sockets.put("availableServerSocket", availableServerSocket);
		 * 
		 * final MapPropertySource propertySource = new
		 * MapPropertySource("sockets", sockets);
		 * 
		 * context.getEnvironment().getPropertySources().addLast(propertySource);
		 * 
		 * System.out.println("using port " +
		 * context.getEnvironment().getProperty("availableServerSocket"));
		 */

		context.load("classpath:META-INF/spring/integration/applicationConfig.xml");
		context.registerShutdownHook();
		context.refresh();

		return context;
	}
}
