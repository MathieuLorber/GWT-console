package net.mlorber.gwt.console.client.notification;

import net.mlorber.gwt.console.client.ConsolePanel;
import net.mlorber.gwt.console.client.util.StyleHelper;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class SimpleNotificationFactory extends NotificationFactory {

	// FIXME center + about close meme si autoclose notif

	private static final String CSS_CONTAINER = "position: fixed; height:0; width:800px; margin:auto; top: 30px;z-index: "
			+ ConsolePanel.NOTIF_ZINDEX + ";";
	private static String CSS_NOTIFICATION = "position: relative; width: 100%; margin: 2px; padding: 10px; border: 1px solid #fff;";
	{
		// FIXME missing browsers specific
		CSS_NOTIFICATION += "box-shadow: 0px 0px 5px #bbb; -moz-box-shadow: 0px 0px 5px #bbb; -webkit-box-shadow: 0px 0px 5px #bbb;";
		// CSS_NOTIFICATION += "border-radius: 2px; border-radius: 2px; -moz-border-radius: 2px; -webkit-border-radius: 2px;";
		// FIXME see anims
		// CSS_NOTIFICATION +=
		// "-webkit-transition: all 1s ease-in-out;-moz-transition: all 1s ease-in-out;-o-transition: all 1s ease-in-out;transition: all 1s ease-in-out;";
		// CSS_NOTIFICATION += "transition:all 1.0s ease-in-out;";
	}
	private static final String CSS_INFO = "background: #C7DCF2;";
	private static final String CSS_SUCCESS = "background: #D1FAB6;";
	private static final String CSS_WARNING = "background: #EB8D7A;";
	private static final String CSS_ERROR = "background: #732222; color: #fff;";
	private static final String CSS_CLOSE_LABEL = "position: absolute;top: -3px; right: 4px;cursor:pointer; color: #fff; font-size: 24px;";

	private FlowPanel container;

	public SimpleNotificationFactory() {
		container = new FlowPanel();
		StyleHelper.addStyle(container, CSS_CONTAINER);
		RootPanel.get().add(container);
		container.getElement().getStyle().setRight((Window.getClientWidth() - 600) / 2, Unit.PX);
	}

	protected void doShowElement(Element element) {
		element.getStyle().setDisplay(Display.BLOCK);
	}

	// FIXME verify
	protected void doHideElement(Element element, boolean fade) {
		element.removeFromParent();
	}

	// TODO a type ? warning / info... Do not use Level but for notif history
	// FIXME fonction du Level
	// FIXME pas là, plutot dans Console
	// FIXME use SafeHTML
	@Override
	public void showNotification(String message, NotificationType type, boolean autoHide) {
		showNotification(new HTML(message), type, autoHide);
	}

	@Override
	public void showNotification(HTML html, NotificationType type, boolean autoHide) {
		final FlowPanel notificationPanel = new FlowPanel();
		notificationPanel.add(html);
		container.add(notificationPanel);
		// FIXME use class...
		StyleHelper.addStyle(notificationPanel, CSS_NOTIFICATION);
		switch (type) {
		case INFO:
			StyleHelper.addStyle(notificationPanel, CSS_INFO);
			break;
		case SUCCESS:
			StyleHelper.addStyle(notificationPanel, CSS_SUCCESS);
			break;
		case WARNING:
			StyleHelper.addStyle(notificationPanel, CSS_WARNING);
			break;
		case ERROR:
			StyleHelper.addStyle(notificationPanel, CSS_ERROR);
			break;
		}
		notificationPanel.getElement().getStyle().setDisplay(Display.NONE);
		doShowElement(notificationPanel.getElement());
		if (autoHide) {
			new Timer() {
				@Override
				public void run() {
					doHideElement(notificationPanel.getElement(), true);
				}
				// TODO sortir le temps
			}.schedule(8000);
		} else {
			initCloseHandler(notificationPanel);
		}
	}

	private void initCloseHandler(final FlowPanel panel) {
		HTML closeLabel = new HTML("&times;");
		StyleHelper.addStyle(closeLabel, CSS_CLOSE_LABEL);
		closeLabel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doHideElement(panel.getElement(), false);
			}
		});
		panel.add(closeLabel);
	}

}
