package net.mlorber.gwt.console.client;

import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.logging.client.HasWidgetsLogHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

// TODO choice of save in cookie at runtime
// TODO handle prodmode / devmode ?
// TODO better log date format
public class Console {

	private static final String RESIZE_IMAGE = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAYAAAAGCAYAAADgzO9IAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA09pVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoMTMuMCAyMDEyMDMwNS5tLjQxNSAyMDEyLzAzLzA1OjIxOjAwOjAwKSAgKE1hY2ludG9zaCkiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6QUNCOEY2QzE4QTA4MTFFMThCOTNCOERBMDkxNjYyODMiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6QUNCOEY2QzI4QTA4MTFFMThCOTNCOERBMDkxNjYyODMiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDpBQ0I4RjZCRjhBMDgxMUUxOEI5M0I4REEwOTE2NjI4MyIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDpBQ0I4RjZDMDhBMDgxMUUxOEI5M0I4REEwOTE2NjI4MyIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/Pv02MjEAAAA0SURBVHjaYvj//z8DEPcDsT6UDcYwQRB4jyzJAOW8R5OcD9OKLAkGDEhYH5fEfGQJgAADAAm9fbx5gQhKAAAAAElFTkSuQmCC";

	private static final String CSS_CONSOLE = "background: #616161;color: #fff;z-index: 2000;border: 1px solid #fff;";
	private static final String CSS_CONSOLE_TITLE = "font-size: 10px;padding-left: 5px;text-align: left;";
	private static final String CSS_SCROLLPANEL = "background: #eee;margin: 2px 2px 12px 2px;color: #000;";
	private static final String CSS_RESIZE_IMAGE = "position: absolute;bottom: 4px;right: 4px;cursor:nw-resize;";
	private static final String CSS_CLOSE_LABEL = "position: absolute;top: 0;right: 5px;cursor:pointer;";
	private static final String CSS_SWITCH_BUTTON = "position: absolute;top: 0;right: 0;z-index: 2000;";
	// private static final String CSS_LOG_PANEL = "background: #fff;margin: 1px;border: 1px solid #ddd;";
	// FIXME sixe problem
	private static final String CSS_LOG_DISCLOSURE_PANEL = "background: #fff;margin: 1px;border: 1px solid #ddd;";

	private static final String CONFIG_COOKIE_NAME = "GWT-Console-conf";

	private static final int DISCLOSURE_PANEL_MIN_HEIGHT = 100;
	private static final int DISCLOSURE_PANEL_MIN_WIDTH = 100;

	public static final int KEY_C = 67;

	private static Console instance;

	private PopupPanel popupContainerPanel = new PopupPanel();
	private ScrollPanelWithMinSize scrollPanel = new ScrollPanelWithMinSize(DISCLOSURE_PANEL_MIN_HEIGHT, DISCLOSURE_PANEL_MIN_WIDTH);

	private FlowPanel consoleWidgetsPanel = new FlowPanel();
	private FlowPanel consoleLogsPanel = new FlowPanel();

	private Level notifyLevel = Level.ALL;

	private NotificationWidget notificationWidget;

	private ConsoleConfiguration configuration;

	public static Console get() {
		if (instance == null) {
			instance = new Console();
		}
		return instance;
	}

	private Console() {
		popupContainerPanel.hide();
		StyleHelper.addStyle(popupContainerPanel, CSS_CONSOLE);

		FlowPanel mainPanel = new FlowPanel();
		popupContainerPanel.add(mainPanel);

		mainPanel.add(initTitleLabel());

		mainPanel.add(consoleWidgetsPanel);

		StyleHelper.addStyle(consoleWidgetsPanel, CSS_SCROLLPANEL);
		consoleWidgetsPanel.add(initClearConfigurationCookieButton());
		consoleWidgetsPanel.add(initConfigurationResetButton());

		StyleHelper.addStyle(scrollPanel, CSS_SCROLLPANEL);
		scrollPanel.add(consoleLogsPanel);
		mainPanel.add(scrollPanel);

		mainPanel.add(initResizeHandler());

		mainPanel.add(initCloseHandler());
	}

	private Widget initTitleLabel() {
		final Label titleLabel = new Label("Console");
		StyleHelper.addStyle(titleLabel, CSS_CONSOLE_TITLE);
		new MouseDragHandler() {

			@Override
			public Widget targetHandler() {
				return titleLabel;
			}

			@Override
			public void handleDrag(int absX, int absY) {
				Widget moveTarget = popupContainerPanel;
				RootPanel.get().setWidgetPosition(moveTarget, moveTarget.getAbsoluteLeft() + absX, moveTarget.getAbsoluteTop() + absY);
			}

			@Override
			public void onMouseUp(MouseUpEvent event) {
				super.onMouseUp(event);
				configuration.savePosition(popupContainerPanel.getPopupLeft(), popupContainerPanel.getPopupTop());
			}
		};
		return titleLabel;
	}

	private Widget initClearConfigurationCookieButton() {
		Button b = new Button("Clear configuration cookie");
		b.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ConsoleConfiguration.clearCookie(CONFIG_COOKIE_NAME);
			}
		});
		return b;
	}

	private Widget initConfigurationResetButton() {
		Button b = new Button("Reset configuration");
		b.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				configuration.reset();
				initFromConfiguration();
			}
		});
		return b;

	}

	private Widget initResizeHandler() {
		final Image resizeImage = new Image();
		resizeImage.setUrl(RESIZE_IMAGE);
		StyleHelper.addStyle(resizeImage, CSS_RESIZE_IMAGE);
		resizeImage.setAltText("Resize");
		new MouseDragHandler() {

			@Override
			public Widget targetHandler() {
				return resizeImage;
			}

			@Override
			public void handleDrag(int absX, int absY) {
				scrollPanel.incrementPixelSize(absX, absY);
			}

			@Override
			public void onMouseUp(MouseUpEvent event) {
				super.onMouseUp(event);
				configuration.saveSize(scrollPanel.getOffsetWidth(), scrollPanel.getOffsetHeight());
			}
		};
		return resizeImage;
	}

	private Widget initCloseHandler() {
		HTML closeLabel = new HTML("&times;");
		StyleHelper.addStyle(closeLabel, CSS_CLOSE_LABEL);
		closeLabel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				popupContainerPanel.hide();
			}
		});
		return closeLabel;
	}

	public void init(boolean registerToRootLogger, Level notifyLevel, boolean registerShorcut, boolean addSwitchButtonOnTopRight, boolean saveConfigurationInCookie) {
		if (registerToRootLogger) {
			registerToRootLogger();
		}
		if (registerShorcut) {
			registerShorcut();
		}
		if (addSwitchButtonOnTopRight) {
			addSwitchButtonOnTopRight();
		}
		if (saveConfigurationInCookie) {
			configuration = new ConsoleConfiguration(CONFIG_COOKIE_NAME);
		} else {
			configuration = new ConsoleConfiguration();
			ConsoleConfiguration.clearCookie(CONFIG_COOKIE_NAME);
		}
		initFromConfiguration();
		this.notifyLevel = notifyLevel;
	}

	public void addSwitchButtonOnTopRight() {
		Button switchButton = getSwitchButton();
		StyleHelper.addStyle(switchButton, CSS_SWITCH_BUTTON);
		RootPanel.get().add(switchButton);
	}

	public Button getSwitchButton() {
		Button switchButton = new Button("Console");
		switchButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				switchConsoleDisplay();
			}
		});
		return switchButton;
	}

	private void registerShorcut() {
		Event.addNativePreviewHandler(new NativePreviewHandler() {
			@Override
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				if (event.getTypeInt() == Event.ONKEYDOWN && event.getNativeEvent().getShiftKey() && event.getNativeEvent().getAltKey()
						&& event.getNativeEvent().getKeyCode() == KEY_C) {
					switchConsoleDisplay();
				}
			}
		});
	}

	private void initFromConfiguration() {
		popupContainerPanel.setPopupPosition(configuration.getConsoleLeftPosition(), configuration.getConsoleTopPosition());
		scrollPanel.setPixelSize(configuration.getConsoleWidth(), configuration.getConsoleHeight());
		if (configuration.isShowConsole()) {
			popupContainerPanel.show();
		}
	}

	public void notify(String message) {
		if (notificationWidget == null) {
			notificationWidget = new NotificationWidget();
			RootPanel.get().add(notificationWidget);
		}
		notificationWidget.showNotification(message);
	}

	// FIXME root ?
	public void registerToRootLogger() {
		Handler handler = new HasWidgetsLogHandler(consoleLogsPanel);
		Logger.getLogger("").addHandler(handler);
		// FIXME test !
		// handler.setFormatter(new TextFormatter());
	}

	// FIXME a log with no disclosure, disclosure as an option
	public void log(String logMessage, String messageTitle) {
		// FIXME use notifyLevel
		// TODO check XSS
		// SafeHtml.sanitize is sufficient ?
		addDisclosurePanelWithWidget(new HTML(logMessage), messageTitle);
	}

	private void switchConsoleDisplay() {
		if (!popupContainerPanel.isShowing()) {
			popupContainerPanel.show();
		} else {
			popupContainerPanel.hide();
		}
		configuration.saveShowConsole(popupContainerPanel.isShowing());
	}

	private void addDisclosurePanelWithWidget(Widget widget, String disclosurePanelTitle) {
		DisclosurePanel logDisclosurePanel = new DisclosurePanel(new Date() + " : " + disclosurePanelTitle);
		StyleHelper.addStyle(logDisclosurePanel, CSS_LOG_DISCLOSURE_PANEL);
		logDisclosurePanel.setContent(widget);
		logDisclosurePanel.addOpenHandler(new OpenHandler<DisclosurePanel>() {
			@Override
			public void onOpen(OpenEvent<DisclosurePanel> event) {
				updateScrollPosition();
			}
		});
		consoleLogsPanel.add(logDisclosurePanel);
		updateScrollPosition();
	}

	// FIXME not if user is scrolling...
	private void updateScrollPosition() {
		scrollPanel.setScrollPosition(scrollPanel.getElement().getScrollHeight());
	}

	public Level getNotifyLevel() {
		return notifyLevel;
	}

	public void setNotifyLevel(Level notifyLevel) {
		this.notifyLevel = notifyLevel;
	}
}