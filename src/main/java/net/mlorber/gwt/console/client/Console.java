package net.mlorber.gwt.console.client;

import java.util.Date;
import java.util.Iterator;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasAllMouseHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.logging.client.HasWidgetsLogHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class Console implements HasWidgets {

	private static final String RESIZE_IMAGE = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAYAAAAGCAYAAADgzO9IAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA09pVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoMTMuMCAyMDEyMDMwNS5tLjQxNSAyMDEyLzAzLzA1OjIxOjAwOjAwKSAgKE1hY2ludG9zaCkiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6QUNCOEY2QzE4QTA4MTFFMThCOTNCOERBMDkxNjYyODMiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6QUNCOEY2QzI4QTA4MTFFMThCOTNCOERBMDkxNjYyODMiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDpBQ0I4RjZCRjhBMDgxMUUxOEI5M0I4REEwOTE2NjI4MyIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDpBQ0I4RjZDMDhBMDgxMUUxOEI5M0I4REEwOTE2NjI4MyIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/Pv02MjEAAAA0SURBVHjaYvj//z8DEPcDsT6UDcYwQRB4jyzJAOW8R5OcD9OKLAkGDEhYH5fEfGQJgAADAAm9fbx5gQhKAAAAAElFTkSuQmCC";

	private static final String CSS_CONSOLE = "background: #616161;color: #fff;z-index: 2000;";
	private static final String CSS_CONSOLE_TITLE = "font-size: 10px;padding-left: 5px;text-align: left;";
	private static final String CSS_SCROLLPANEL = "background: #eee;margin: 2px 2px 12px 2px;color: #000;";
	private static final String CSS_RESIZE_IMAGE = "position: absolute;bottom: 4px;right: 4px;cursor:nw-resize;";
	private static final String CSS_CLOSE_LABEL = "position: absolute;top: 0;right: 5px;cursor:pointer;";
	private static final String CSS_SWITCH_BUTTON = "position: absolute;top: 0;right: 0;z-index: 2000;";
	private static final String CSS_LOG_PANEL = "background: #fff;margin: 1px;border: 1px solid #ddd;";
	// FIXME sixe problem
	private static final String CSS_LOG_DISCLOSURE_PANEL = "background: #fff;margin: 1px;border: 1px solid #ddd;";

	public static final int KEY_C = 67;

	private static Console instance = new Console();

	private PopupPanel popupContainerPanel = new PopupPanel();
	private ScrollPanelWithMinSize scrollPanel = new ScrollPanelWithMinSize();
	private FlowPanel consoleContentPanel = new FlowPanel();

	private Integer popupLeftPosition;
	private Integer popupTopPosition;

	private Level notifyLevel = Level.ALL;

	private NotificationWidget notificationWidget;

	private Console() {
		popupContainerPanel.hide();
		StyleHelper.addStyle(popupContainerPanel, CSS_CONSOLE);

		FlowPanel contentPanel = new FlowPanel();
		popupContainerPanel.add(contentPanel);

		Label titleLabel = new Label("Console");
		StyleHelper.addStyle(titleLabel, CSS_CONSOLE_TITLE);
		new WindowMoveHandler(titleLabel);
		contentPanel.add(titleLabel);

		StyleHelper.addStyle(scrollPanel, CSS_SCROLLPANEL);
		scrollPanel.setPixelSize(1000, 500);
		scrollPanel.add(consoleContentPanel);
		contentPanel.add(scrollPanel);

		Image resizeImage = new Image();
		resizeImage.setUrl(RESIZE_IMAGE);
		StyleHelper.addStyle(resizeImage, CSS_RESIZE_IMAGE);
		resizeImage.setAltText("Resize");
		new WindowResizeHandler(resizeImage);
		contentPanel.add(resizeImage);

		HTML closeLabel = new HTML("&times;");
		StyleHelper.addStyle(closeLabel, CSS_CLOSE_LABEL);
		contentPanel.add(closeLabel);
		closeLabel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				popupContainerPanel.hide();
			}
		});
	}

	public void quickInit(boolean registerToRootLogger, boolean registerShorcut, boolean addSwitchButtonOnTopRight) {
		if (registerToRootLogger) {
			registerToRootLogger();
		}
		if (registerShorcut) {
			registerShorcut();
		}
		if (addSwitchButtonOnTopRight) {
			addSwitchButtonOnTopRight();
		}
	}

	public void quickInit(boolean registerToRootLogger, Level notifyLevel, boolean registerShorcut, boolean addSwitchButtonOnTopRight) {
		quickInit(registerToRootLogger, registerShorcut, addSwitchButtonOnTopRight);
		this.notifyLevel = notifyLevel;
	}

	public static Console get() {
		return instance;
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

	public void notify(String message) {
		if (notificationWidget == null) {
			notificationWidget = new NotificationWidget();
			RootPanel.get().add(notificationWidget);
		}
		notificationWidget.showNotification(message);
	}

	// FIXME root ?
	public void registerToRootLogger() {
		Handler handler = new HasWidgetsLogHandler(Console.get());
		Logger.getLogger("").addHandler(handler);
		// FIXME test !
		// handler.setFormatter(new TextFormatter());
	}

	public void log(String logMessage, String messageTitle) {
		// FIXME use notifyLevel
		// TODO check XSS
		// SafeHtml.sanitize is sufficient ?
		addDisclosurePanelWithWidget(new HTML(logMessage), messageTitle);
	}

	private void switchConsoleDisplay() {
		if (!popupContainerPanel.isShowing()) {
			popupContainerPanel.show();
			if (popupLeftPosition == null) {
				popupContainerPanel.center();
			} else {
				popupContainerPanel.setPopupPosition(popupLeftPosition, popupTopPosition);
			}
		} else {
			popupLeftPosition = popupContainerPanel.getPopupLeft();
			popupTopPosition = popupContainerPanel.getPopupTop();
			popupContainerPanel.hide();
		}
	}

	private static class ScrollPanelWithMinSize extends ScrollPanel {
		private int minScrollPanelHeight = 100;
		private int minScrollPanelWidth = 100;
		private int scrollPanelHeight;
		private int scrollPanelWidth;

		public void incrementPixelSize(int width, int height) {
			setPixelSize(scrollPanelWidth + width, scrollPanelHeight + height);
		}

		@Override
		public void setPixelSize(int width, int height) {
			super.setPixelSize(scrollPanelWidth = Math.max(width, minScrollPanelWidth), scrollPanelHeight = Math.max(height, minScrollPanelHeight));
		}
	}

	private class WindowMoveHandler extends MouseDragHandler {
		public WindowMoveHandler(Widget dragHandle) {
			super(dragHandle);
		}

		@Override
		public void handleDrag(int absX, int absY) {
			Widget moveTarget = popupContainerPanel;
			RootPanel.get().setWidgetPosition(moveTarget, moveTarget.getAbsoluteLeft() + absX, moveTarget.getAbsoluteTop() + absY);
		}
	}

	private class WindowResizeHandler extends MouseDragHandler {
		public WindowResizeHandler(Widget dragHandle) {
			super(dragHandle);
		}

		@Override
		public void handleDrag(int absX, int absY) {
			scrollPanel.incrementPixelSize(absX, absY);
		}
	}

	private abstract class MouseDragHandler implements MouseMoveHandler, MouseUpHandler, MouseDownHandler {
		protected boolean dragging = false;
		protected Widget dragHandle;
		protected int dragStartX;
		protected int dragStartY;

		public MouseDragHandler(Widget dragHandle) {
			this.dragHandle = dragHandle;
			HasAllMouseHandlers hamh = (HasAllMouseHandlers) dragHandle;
			hamh.addMouseDownHandler(this);
			hamh.addMouseUpHandler(this);
			hamh.addMouseMoveHandler(this);
		}

		public abstract void handleDrag(int absX, int absY);

		@Override
		public void onMouseDown(MouseDownEvent event) {
			dragging = true;
			DOM.setCapture(dragHandle.getElement());
			dragStartX = event.getClientX();
			dragStartY = event.getClientY();
			DOM.eventPreventDefault(DOM.eventGetCurrentEvent());
		}

		@Override
		public void onMouseMove(MouseMoveEvent event) {
			if (dragging) {
				handleDrag(event.getClientX() - dragStartX, event.getClientY() - dragStartY);
				dragStartX = event.getClientX();
				dragStartY = event.getClientY();
			}
		}

		@Override
		public void onMouseUp(MouseUpEvent event) {
			dragging = false;
			DOM.releaseCapture(dragHandle.getElement());
		}
	}

	@Override
	public void add(Widget widget) {
		addSimplePanelWithWidget(widget);
	}

	private void addSimplePanelWithWidget(Widget widget) {
		FlowPanel logPanel = new FlowPanel();
		StyleHelper.addStyle(logPanel, CSS_LOG_PANEL);
		logPanel.add(widget);
		consoleContentPanel.add(logPanel);
		updateScrollPosition();
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
		consoleContentPanel.add(logDisclosurePanel);
		updateScrollPosition();
	}

	// FIXME not if user is scrolling...
	private void updateScrollPosition() {
		scrollPanel.setScrollPosition(scrollPanel.getElement().getScrollHeight());
	}

	@Override
	public void clear() {
		consoleContentPanel.clear();
	}

	// TODO container panels problem : iterate on given panels or
	// containing ones ?
	@Override
	public Iterator<Widget> iterator() {
		return consoleContentPanel.iterator();
	}

	// TODO as in iterator()...
	// or keep subwidgets to be able to remove them with the disclosure
	@Override
	public boolean remove(Widget widget) {
		return consoleContentPanel.remove(widget);
	}

	public Level getNotifyLevel() {
		return notifyLevel;
	}

	public void setNotifyLevel(Level notifyLevel) {
		this.notifyLevel = notifyLevel;
	}

	// FIXME new instance each time ?
	public Handler createHandler() {
		return new HasWidgetsLogHandler(Console.get());
	}
}