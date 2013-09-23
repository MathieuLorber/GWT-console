package net.mlorber.gwt.console.client;

import net.mlorber.gwt.console.client.log.LogHandlerPanel;
import net.mlorber.gwt.console.client.log.ScrollPanelWithMinSize;
import net.mlorber.gwt.console.client.util.StyleHelper;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class ConsolePanel {

	private static final int CONSOLE_ZINDEX = 2000;
	public static final int BUTTON_ZINDEX = 1999;
	// FIXME sortir ailleurs ?
	public static final int NOTIF_ZINDEX = 2001;

	private static final String CSS_CONSOLE_PANEL = "background: #616161;color: #fff;z-index: " + CONSOLE_ZINDEX
			+ ";border: 1px solid #fff;";
	private static final String CSS_CONSOLE_TITLE = "font-size: 10px;padding-left: 5px;text-align: left;";
	private static final String CSS_WIDGET_PANEL = "overflow: auto;";
	private static final String CSS_RESIZE_IMAGE = "position: absolute;bottom: 4px;right: 4px;cursor:nw-resize;";
	private static final String CSS_CLOSE_LABEL = "position: absolute;top: 0;right: 5px;cursor:pointer;";
	private static final String CSS_SCROLLPANEL = "background: #eee; margin: 2px 2px 12px 2px;";

	private static final String RESIZE_IMAGE = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAYAAAAGCAYAAADgzO9IAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA09pVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoMTMuMCAyMDEyMDMwNS5tLjQxNSAyMDEyLzAzLzA1OjIxOjAwOjAwKSAgKE1hY2ludG9zaCkiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6QUNCOEY2QzE4QTA4MTFFMThCOTNCOERBMDkxNjYyODMiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6QUNCOEY2QzI4QTA4MTFFMThCOTNCOERBMDkxNjYyODMiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDpBQ0I4RjZCRjhBMDgxMUUxOEI5M0I4REEwOTE2NjI4MyIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDpBQ0I4RjZDMDhBMDgxMUUxOEI5M0I4REEwOTE2NjI4MyIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/Pv02MjEAAAA0SURBVHjaYvj//z8DEPcDsT6UDcYwQRB4jyzJAOW8R5OcD9OKLAkGDEhYH5fEfGQJgAADAAm9fbx5gQhKAAAAAElFTkSuQmCC";

	private static final int DISCLOSURE_PANEL_MIN_HEIGHT = 100;
	private static final int DISCLOSURE_PANEL_MIN_WIDTH = 100;

	private PopupPanel popupContainerPanel = new PopupPanel();

	private FlowPanel widgetPanel = new FlowPanel();
	private ScrollPanelWithMinSize scrollPanel = new ScrollPanelWithMinSize(DISCLOSURE_PANEL_MIN_HEIGHT, DISCLOSURE_PANEL_MIN_WIDTH);
	private LogHandlerPanel logHandlerPanel = new LogHandlerPanel(scrollPanel);

	private ConsoleConfiguration configuration;

	public ConsolePanel(ConsoleConfiguration configuration) {
		this.configuration = configuration;

		StyleHelper.addStyle(popupContainerPanel, CSS_CONSOLE_PANEL);
		StyleHelper.addStyle(widgetPanel, CSS_WIDGET_PANEL);
		StyleHelper.addStyle(scrollPanel, CSS_SCROLLPANEL);

		FlowPanel mainPanel = new FlowPanel();
		mainPanel.add(initTitleLabel());
		mainPanel.add(widgetPanel);
		mainPanel.add(scrollPanel);
		mainPanel.add(initResizeHandler());
		mainPanel.add(initCloseHandler());

		widgetPanel.setVisible(false);
		scrollPanel.add(logHandlerPanel);

		popupContainerPanel.setPopupPosition(configuration.getConsoleLeftPosition(), configuration.getConsoleTopPosition());
		scrollPanel.setPixelSize(configuration.getConsoleWidth(), configuration.getConsoleHeight());
		widgetPanel.setWidth(configuration.getConsoleWidth() + "px");
		if (configuration.isShowConsole()) {
			popupContainerPanel.show();
		}

		popupContainerPanel.add(mainPanel);
	}

	public LogHandlerPanel getLogHandlerPanel() {
		return logHandlerPanel;
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
				widgetPanel.setWidth(scrollPanel.getOffsetWidth() + "px");
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
				configuration.saveShowConsole(false);
			}
		});
		return closeLabel;
	}

	@Deprecated
	private void initAutoScroll() {
		// FIXME
		// scrollPanel.addScrollHandler(new ScrollHandler() {
		//
		// @Override
		// public void onScroll(ScrollEvent event) {
		// // if (scrollPanel.getScrollPosition() ==
		// // scrollPanel.getElement().getScrollHeight()) {
		// // autoScroll = true;
		// // } else {
		// // autoScroll = false;
		// // }
		// }
		// });
	}

	public void toggleDisplay() {
		if (!popupContainerPanel.isShowing()) {
			popupContainerPanel.show();
		} else {
			popupContainerPanel.hide();
		}
		configuration.saveShowConsole(popupContainerPanel.isShowing());
	}

	public void addHelperWidget(Widget widget) {
		widgetPanel.setVisible(true);
		widgetPanel.add(widget);
	}

	public void clearHelperWidgets() {
		widgetPanel.clear();
		widgetPanel.setVisible(false);
	}

}
