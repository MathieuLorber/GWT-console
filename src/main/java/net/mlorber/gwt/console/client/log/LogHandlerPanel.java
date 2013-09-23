package net.mlorber.gwt.console.client.log;

import java.util.Iterator;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class LogHandlerPanel extends Composite implements HasWidgets {

	// FIXME button et conf
	private boolean autoScroll = true;

	private FlowPanel container = new FlowPanel();

	private ScrollPanelWithMinSize scrollPanel;

	// FIXME pas fou de passer le scrollPanel ?
	public LogHandlerPanel(ScrollPanelWithMinSize scrollPanel) {
		this.scrollPanel = scrollPanel;
		initWidget(container);
	}

	@Override
	public void add(Widget w) {
		container.add(w);
		if (autoScroll) {
			scrollPanel.setVerticalScrollPosition(scrollPanel.getElement().getScrollHeight());
		}
	}

	@Override
	public void clear() {
		container.clear();
	}

	@Override
	public Iterator<Widget> iterator() {
		return container.iterator();
	}

	@Override
	public boolean remove(Widget w) {
		return container.remove(w);
	}

}
