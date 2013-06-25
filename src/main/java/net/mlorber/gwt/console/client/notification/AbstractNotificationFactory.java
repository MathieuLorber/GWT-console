package net.mlorber.gwt.console.client.notification;

import java.util.logging.Level;

// TODO rename ?
public abstract class AbstractNotificationFactory {

	// TODO info / success / warning / error

	public static enum MessageType {
		INFO, SUCCESS, WARNING, ERROR;
	}

	@Deprecated
	public final void showNotification(String message, Level level) {
		// TODO what is off ?
		int logLevel = level.intValue();
		if (logLevel >= Level.SEVERE.intValue()) {
			showNotification(message, MessageType.ERROR, false);
			return;
		}
		if (logLevel >= Level.WARNING.intValue()) {
			showNotification(message, MessageType.WARNING, true);
			return;
		}
		showNotification(message, MessageType.INFO, true);
	}

	public abstract void showNotification(String message, MessageType messageType, boolean autoHide);

}
