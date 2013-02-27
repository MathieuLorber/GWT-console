package net.mlorber.gwt.console.client.notification;

import java.util.logging.Level;

// TODO rename ?
public abstract class NotificationFactory {

	public void showNotification(String message) {
		showNotification(message, Level.INFO);
	}

	public abstract void showNotification(String message, Level level);

}
