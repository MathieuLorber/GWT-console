package net.mlorber.gwt.console.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.*;

import net.mlorber.gwt.console.client.log.LogHandler;
import net.mlorber.gwt.console.client.notification.NotificationFactory;
import net.mlorber.gwt.console.client.notification.NotificationFactory.NotificationType;
import net.mlorber.gwt.console.client.notification.SimpleNotificationFactory;
import net.mlorber.gwt.console.client.util.StyleHelper;

// TODO :
// * choice of save in cookie at runtime
// * handle prodmode / devmode ?
// * better log date format
// * uncatched exceptions
// * better log lines...
// * auto notify with LogLevel
// * implement initAutoScroll
// * check registerToRootLogger and implement formatter
public class Console {

	private static Logger logger;

	private static final String CONFIG_COOKIE_NAME = "GWT-Console-conf";

	private static final String CSS_SWITCH_BUTTON = "position: absolute;top: 0;right: 0;z-index: " + ConsolePanel.BUTTON_ZINDEX + ";";

	private static final int KEY_C = 67;

	private static Console instance;

	// private ConsoleConfiguration configuration;

	private ConsolePanel consolePanel;
	private NotificationFactory notificationFactory;
	private Button switchButton;

	private LogHandler logHandler;

	// FIXME i18n
	private String unknownErrorMessage = "Unknown error : ";

	public static Console get() {
		if (instance == null) {
			instance = new Console();
		}
		return instance;
	}

	private Console() {
	}

	// TODO notifyLevel impl
	// + make a method register(logger, notifyLevel)
	// TODO remove saveConfigurationInCookie, use delegate
	// TODO doc logUncaughtExceptions, log, not catch
	public void init(final Logger logger, Level notifyLevel) {
		init(logger, notifyLevel, new SimpleNotificationFactory());
	}

	// FIXME log uncaught en appel de méthode
	// par défaut yes ?
	// FIXME sortir notifier
	public void init(final Logger logger, Level notifyLevel, NotificationFactory notificationFactory) {
		this.logger = logger;
		this.notificationFactory = notificationFactory;

		ConsoleConfiguration configuration = new ConsoleConfiguration(CONFIG_COOKIE_NAME);
		consolePanel = new ConsolePanel(configuration);
		logHandler = new LogHandler(consolePanel.getLogHandlerPanel());
		// TODO own HtmlLogFormatter ?
		logger.addHandler(logHandler);
		// logger.addHandler(new NotificationHandler(notificationFactory,
		// notifyLevel));

		// this.notifyLevel = notifyLevel;

		// FIXME or just not... do it after if you want yours...
		// if (logUncaughtExceptions) {
		// GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
		// @Override
		// public void onUncaughtException(Throwable e) {
		// logUncaughtException(e);
		// }
		// });
		// }

		// FIXME methode pour specifier save dans cookie (ou autre)
		// if (saveConfigurationInCookie) {
		// configuration = new ConsoleConfiguration(CONFIG_COOKIE_NAME);
		// } else {
		// configuration = new ConsoleConfiguration();
		// ConsoleConfiguration.clearCookie(CONFIG_COOKIE_NAME);
		// }
	}

	// TODO in sample !
	public void toggleDisplaySwitchButton() {
		if (switchButton == null) {
			switchButton = new Button("Console");
			switchButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					toggleDisplay();
				}
			});
			StyleHelper.addStyle(switchButton, CSS_SWITCH_BUTTON);
			RootPanel.get().add(switchButton);
		} else {
			switchButton.setVisible(!switchButton.isVisible());
		}
	}

	public void registerShorcut() {
		Event.addNativePreviewHandler(new NativePreviewHandler() {
			@Override
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				if (event.getTypeInt() == Event.ONKEYDOWN && event.getNativeEvent().getShiftKey() && event.getNativeEvent().getAltKey()
						&& event.getNativeEvent().getKeyCode() == KEY_C) {
					toggleDisplay();
				}
			}
		});
	}

	public void toggleDisplay() {
		consolePanel.toggleDisplay();
	}

	public void showNotification(String message, NotificationType notificationType) {
		showNotification(message, notificationType, true);
	}

	public void showNotification(String message, Throwable throwable, NotificationType notificationType) {
		showNotification(message, notificationType, true);
	}

	public void showNotification(String message, NotificationType notificationType, boolean autoHide) {
		notificationFactory.showNotification(message, notificationType, autoHide);
	}

	public void showNotification(String message, Throwable throwable, NotificationType notificationType, boolean autoHide) {
		notificationFactory.showNotification(message, notificationType, autoHide);
	}

	public void showNotification(HTML html, NotificationType notificationType, boolean autoHide) {
		notificationFactory.showNotification(html, notificationType, autoHide);
	}

	public void showNotification(HTML html, Throwable throwable, NotificationType notificationType, boolean autoHide) {
	}

	// FIXME pourquoi initialement un getPanel ?
	public void addHelperWidget(Widget widget) {
		consolePanel.addHelperWidget(widget);
	}

	public void clearHelperWidgets() {
		consolePanel.clearHelperWidgets();
	}

	// FIXME rien à faire là, use loggers
	// public void log(String message) {
	// // FIXME check XSS, SafeHtml.sanitize is sufficient ?
	// // FIXME refaire quelque chose =)
	// // logHandler.add(new HTML(message));
	// logger.log(Level.INFO, message);
	// }

	// public void logUncaughtException(Throwable e) {
	// // FIXME just log
	// // TODO notify as severe
	// // Shows umbrellaexception even with that
	// // if (!(e instanceof DoNotNotifyException)) {
	// // FIXME just check prod et virer...
	// // If we have a problem with notifier (jQuery impl instancied without
	// // jQuery for example), initialUncaughtExceptionHandler is called
	// // before the crash
	// // TODO remove unknownErrorMessage ?
	// // showNotification(unknownErrorMessage + e.getLocalizedMessage());
	// // TODO ?
	// logger.log(Level.WARNING, e.getMessage(), e);
	// }

	public String getUnknownErrorMessage() {
		return unknownErrorMessage;
	}

	public void setUnknownErrorMessage(String unknownErrorMessage) {
		this.unknownErrorMessage = unknownErrorMessage;
	}

}