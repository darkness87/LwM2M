package com.cnu.lwm2m.client;

import java.io.IOException;
import java.util.Arrays;

import org.eclipse.californium.elements.exception.ConnectorException;

public class Client {

	private static final String HELLO_WORLD = GETClient.class.getSimpleName();
	private static final String MULTICAST = MulticastTestClient.class.getSimpleName();

	public static void main(String[] args) throws IOException, ConnectorException, InterruptedException {
		String start = args.length > 0 ? args[0] : null;
		if (start != null) {
			String[] args2 = Arrays.copyOfRange(args, 1, args.length);
			if (HELLO_WORLD.equals(start)) {
				GETClient.main(args2);
				return;
			} else if (MULTICAST.equals(start)) {
				MulticastTestClient.main(args2);
				return;
			}
		}
		System.out.println("\nCalifornium (Cf) Server-Starter");
		System.out.println("(c) 2020, Bosch.IO GmbH and others");
		System.out.println();
		System.out.println("Usage: " + Client.class.getSimpleName() + " (" + HELLO_WORLD + "|" + MULTICAST + ")");
		if (start != null) {
			System.out.println("   '" + start + "' is not supported!");
		}
		System.exit(-1);
	}
}