package net.mlorber.gwt.console.client.notification;

import com.google.gwt.user.client.ui.HTML;

public abstract class NotificationFactory {

	public static enum NotificationType {
		INFO, SUCCESS, WARNING, ERROR;
	}

	public abstract void showNotification(String message, NotificationType type, boolean autoHide);

	public abstract void showNotification(HTML html, NotificationType notificationType, boolean autoHide);

}
