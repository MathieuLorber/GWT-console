package net.mlorber.gwt.console.client.notification;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

// TODO see handler code
public class NotificationHandler extends Handler {

	private AbstractNotificationFactory notificationFactory;
	private Level level;

	public NotificationHandler(AbstractNotificationFactory notificationFactory, Level level) {
		this.notificationFactory = notificationFactory;
		this.level = level;
	}

	@Override
	public void publish(LogRecord logrecord) {
		if (logrecord.getLevel().intValue() < level.intValue()) {
			return;
		}
		if (logrecord.getThrown() != null) {
			if ((logrecord.getThrown() instanceof DoNotNotifyException)) {
				return;
			}
		}
		notificationFactory.showNotification(logrecord.getMessage(), logrecord.getLevel());
	}

	@Override
	public void flush() {
		// Do nothing
	}

	@Override
	public void close() {
		// Do nothing
	}

}
