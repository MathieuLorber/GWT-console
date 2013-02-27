package net.mlorber.gwt.console.client.notification;

import java.util.logging.Level;

import net.mlorber.gwt.console.client.StyleHelper;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class SimpleNotificationFactory extends NotificationFactory {

	private static final String CSS_CONTAINER = "position: fixed; height:0; width:600px; margin:auto; top: 30px;z-index: 1000;";
	// FIXME missing browsers specific radius
	private static final String CSS_NOTIFICATION = "width: 100%; margin: 2px; padding: 10px; background: #C7DCF2; border: 3px solid #fff; box-shadow: 0px 0px 5px #bbb; -moz-box-shadow: 0px 0px 5px #bbb; -webkit-box-shadow: 0px 0px 5px #bbb; border-radius: 6px; border-radius: 6px; -moz-border-radius-bottomright: 6px; -moz-border-radius-bottomleft: 6px; -webkit-border-radius: 6px; -webkit-border-radius: 6px;";
	private FlowPanel container;

	public SimpleNotificationFactory() {
		container = new FlowPanel();
		StyleHelper.addStyle(container, CSS_CONTAINER);
		RootPanel.get().add(container);
	}

	// TODO a type ? warning / info... Do not use Level but for notif history
	// FIXME fonction du Level
	@Override
	public void showNotification(String message, Level level) {
		container.getElement().getStyle().setRight((Window.getClientWidth() - 600) / 2, Unit.PX);
		final Label notificationLabel = new Label(message);
		container.add(notificationLabel);
		StyleHelper.addStyle(notificationLabel, CSS_NOTIFICATION);
		notificationLabel.getElement().getStyle().setDisplay(Display.NONE);
		showNotification(notificationLabel.getElement());
		new Timer() {
			@Override
			public void run() {
				hideNotification(notificationLabel.getElement());
			}
		}.schedule(3000);
	}

	protected void showNotification(Element element) {
		element.getStyle().setDisplay(Display.BLOCK);
	}

	// FIXME verify
	protected void hideNotification(Element element) {
		element.removeFromParent();
	}

}
