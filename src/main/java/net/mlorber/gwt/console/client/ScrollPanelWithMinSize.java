package net.mlorber.gwt.console.client;

import com.google.gwt.user.client.ui.ScrollPanel;

class ScrollPanelWithMinSize extends ScrollPanel {
	private int minScrollPanelHeight;
	private int minScrollPanelWidth;
	private int scrollPanelHeight;
	private int scrollPanelWidth;

	public ScrollPanelWithMinSize(int minScrollPanelHeight, int minScrollPanelWidth) {
		super();
		this.minScrollPanelHeight = minScrollPanelHeight;
		this.minScrollPanelWidth = minScrollPanelWidth;
	}

	public void incrementPixelSize(int width, int height) {
		setPixelSize(scrollPanelWidth + width, scrollPanelHeight + height);
	}

	@Override
	public void setPixelSize(int width, int height) {
		super.setPixelSize(scrollPanelWidth = Math.max(width, minScrollPanelWidth), scrollPanelHeight = Math.max(height, minScrollPanelHeight));
	}
}
