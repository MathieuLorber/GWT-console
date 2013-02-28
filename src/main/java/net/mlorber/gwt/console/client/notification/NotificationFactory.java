package net.mlorber.gwt.console.client.notification;

import java.util.logging.Level;

// TODO rename ?
public abstract class NotificationFactory {

	// TODO info / success / warning / error

	public static enum MessageType {
		INFO, SUCCESS, WARNING, ERROR;
	}

	public final void showNotification(String message) {
		showNotification(message, Level.INFO);
	}

	public final void showNotification(String message, Level level) {
		// TODO what is off ?
		int logLevel = level.intValue();
		if (logLevel >= Level.SEVERE.intValue()) {
			showNotification(message, MessageType.ERROR);
			return;
		}
		if (logLevel >= Level.WARNING.intValue()) {
			showNotification(message, MessageType.WARNING);
			return;
		}
		showNotification(message, MessageType.INFO);
	}

	public abstract void showNotification(String message, MessageType messageType);

}
