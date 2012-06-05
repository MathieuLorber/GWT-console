package net.mlorber.gwt.console.client;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class NotificationWidget extends Composite {

	private static String CSS_PANEL = "display:none;position: absolute;top: 0;right: 10px;padding: 2px;width: 200px;background: #fff;box-shadow: 0px 0px 5px #bbb;-moz-box-shadow: 0px 0px 5px #bbb;-webkit-box-shadow: 0px 0px 5px #bbb;border-bottom-right-radius: 6px;border-bottom-left-radius: 6px;-moz-border-radius-bottomright: 6px;-moz-border-radius-bottomleft: 6px;-webkit-border-bottom-right-radius: 6px;-webkit-border-bottom-left-radius: 6px;";

	private FlowPanel panel = new FlowPanel();

	public NotificationWidget() {
		StyleHelper.addStyle(panel, CSS_PANEL);
		initWidget(panel);
	}

	public void showNotification(String notification) {
		final Label notificationLabel = new Label(notification);
		panel.add(notificationLabel);
		panel.getElement().getStyle().setDisplay(Display.BLOCK);
		new Timer() {
			@Override
			public void run() {
				notificationLabel.removeFromParent();
				hidePanelIfClear();
			}
		}.schedule(5000);
	}

	private void hidePanelIfClear() {
		if (panel.getWidgetCount() == 0) {
			panel.getElement().getStyle().setDisplay(Display.NONE);
		}
	}

}
