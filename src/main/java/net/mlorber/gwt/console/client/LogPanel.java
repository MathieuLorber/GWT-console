package net.mlorber.gwt.console.client;

import java.util.Iterator;

import net.mlorber.gwt.console.client.util.StyleHelper;


import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public abstract class LogPanel extends Composite implements HasWidgets {

	private static final String CSS_LOG_PANEL = "display: block;background: #fff;margin: 1px;border: 1px solid #ddd;";

	private FlowPanel panel = new FlowPanel();

	public LogPanel() {
		initWidget(panel);
	}

	protected abstract void widgetAdded();

	@Override
	public void add(Widget widget) {
		FlowPanel logPanel = new FlowPanel();
		StyleHelper.addStyle(logPanel, CSS_LOG_PANEL);
		logPanel.add(widget);
		panel.add(logPanel);
		widgetAdded();
	}

	@Override
	public void clear() {
		panel.clear();
	}

	@Override
	public Iterator<Widget> iterator() {
		return panel.iterator();
	}

	@Override
	public boolean remove(Widget widget) {
		if (panel.remove(widget)) {
			return true;
		}
		while (iterator().hasNext()) {
			if (((FlowPanel) iterator().next()).getWidgetIndex(widget) != -1) {
				return panel.remove(iterator().next());
			}
		}
		return false;
	}
}
