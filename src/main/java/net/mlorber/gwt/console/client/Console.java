package net.mlorber.gwt.console.client;

import java.util.Date;
import java.util.Iterator;
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
	private static Console instance = new Console();

	private PopupPanel popupContainerPanel = new PopupPanel();
	private ScrollPanelWithMinSize scrollPanel = new ScrollPanelWithMinSize();
	private FlowPanel consoleContentPanel = new FlowPanel();

	private Integer popupLeftPosition;
	private Integer popupTopPosition;

	private final String consoleCss = "background: #616161;color: #fff;z-index: 100;";
	private final String consoleTitleCss = "font-size: 10px;padding-left: 5px;text-align: left;";
	private final String scrollPanelCss = "background: #eee;margin: 2px 2px 12px 2px;color: #000;";
	private final String resizeImageCss = "position: absolute;bottom: 4px;right: 4px;";
	private final String switchButtonCss = "position: absolute;top: 0;right: 0;";
	private final String logPanelCss = "background: #fff;margin: 1px;border: 1px solid #ddd;";
	// FIXME sixe problem
	private final String logDisclosurePanelCss = "background: #fff;margin: 1px;border: 1px solid #ddd;";

	private Console() {
		popupContainerPanel.hide();
		addStyle(popupContainerPanel, consoleCss);

		FlowPanel contentPanel = new FlowPanel();
		popupContainerPanel.add(contentPanel);

		Label titleLabel = new Label("Console");
		addStyle(titleLabel, consoleTitleCss);
		new WindowMoveHandler(titleLabel);
		contentPanel.add(titleLabel);

		addStyle(scrollPanel, scrollPanelCss);
		scrollPanel.setPixelSize(1000, 500);

		scrollPanel.add(consoleContentPanel);
		contentPanel.add(scrollPanel);

		Image resizeImage = new Image();
		resizeImage
				.setUrl("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHUlEQVR42mO4du3afwZsAFkCRRGMg1UnXBDEQMYAd90jCwaZIMYAAAAASUVORK5CYII=");
		addStyle(resizeImage, resizeImageCss);
		resizeImage.setAltText("Resize");
		new WindowResizeHandler(resizeImage);
		contentPanel.add(resizeImage);

		Image closeImage = new Image();
		closeImage
				.setUrl("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVR42mO4du3afwYkAOfDGOgKcAtgaEVWAAB2Mh+Ku4pROgAAAABJRU5ErkJggg==");
		addStyle(closeImage, "position: absolute;top: 4px;right: 4px;");
		closeImage.setAltText("Close");
		contentPanel.add(closeImage);
		closeImage.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				popupContainerPanel.hide();
			}
		});

		Button switchButton = new Button("Console");
		addStyle(switchButton, switchButtonCss);
		RootPanel.get().add(switchButton);
		switchButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				switchConsoleDisplay();
			}
		});

		Event.addNativePreviewHandler(new NativePreviewHandler() {
			@Override
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				if (event.getTypeInt() == Event.ONKEYDOWN && event.getNativeEvent().getShiftKey() && event.getNativeEvent().getAltKey()
						&& event.getNativeEvent().getKeyCode() == KeyboardCodes.KEY_C) {
					switchConsoleDisplay();
				}
			}
		});
	}
	
	private void addStyle(Widget widget, String style) {
		String updatedString = widget.getElement().getAttribute("style") + ";" + style;
		widget.getElement().setAttribute("style", updatedString);
	}
	
	public static Console getInstance() {
		return instance;
	}

	public void registerToRootLogger() {
		Logger.getLogger("").addHandler(new HasWidgetsLogHandler(Console.getInstance()));
	}
	
	public void log(String logMessage, String messageTitle) {
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
			super.setPixelSize(scrollPanelWidth = Math.max(width, minScrollPanelWidth),
					scrollPanelHeight = Math.max(height, minScrollPanelHeight));
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

		public void onMouseDown(MouseDownEvent event) {
			dragging = true;
			DOM.setCapture(dragHandle.getElement());
			dragStartX = event.getClientX();
			dragStartY = event.getClientY();
			DOM.eventPreventDefault(DOM.eventGetCurrentEvent());
		}

		public void onMouseMove(MouseMoveEvent event) {
			if (dragging) {
				handleDrag(event.getClientX() - dragStartX, event.getClientY() - dragStartY);
				dragStartX = event.getClientX();
				dragStartY = event.getClientY();
			}
		}

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
		addStyle(logPanel, logPanelCss);
		logPanel.add(widget);
		consoleContentPanel.add(logPanel);
		updateScrollPosition();
	}

	private void addDisclosurePanelWithWidget(Widget widget, String disclosurePanelTitle) {
		DisclosurePanel logDisclosurePanel = new DisclosurePanel(new Date() + " : " + disclosurePanelTitle);
		addStyle(logDisclosurePanel, logDisclosurePanelCss);
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
	@Override
	public boolean remove(Widget widget) {
		return consoleContentPanel.remove(widget);
	}
}