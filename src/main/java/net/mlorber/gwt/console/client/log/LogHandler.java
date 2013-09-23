package net.mlorber.gwt.console.client.log;

import java.util.logging.LogRecord;

import net.mlorber.gwt.console.client.util.StyleHelper;

import com.google.gwt.logging.client.HasWidgetsLogHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;

// FIXME extends HasWidgetsLogHandler pour le caca avec securityexception
public class LogHandler extends HasWidgetsLogHandler {

	private static final String CSS_SCROLLPANEL = "background: #eee; margin: 2px 2px 12px 2px;";
	private static final String CSS_LOG_BLOC = "background: #fff; margin: 0 2px 0 2px; border-bottom: 1px solid #616161;";

	private static final int DISCLOSURE_PANEL_MIN_HEIGHT = 100;
	private static final int DISCLOSURE_PANEL_MIN_WIDTH = 100;

	// FIXME button et conf
	private boolean autoScroll = true;

	private ScrollPanelWithMinSize scrollPanel = new ScrollPanelWithMinSize(DISCLOSURE_PANEL_MIN_HEIGHT, DISCLOSURE_PANEL_MIN_WIDTH);
	private HasWidgets container;

	// FIXME créer une indirection de plus ou capter comment GWT résout le soucis de SecuException
	// pas vraiment indirection mais faire le job avec LogHandlerPanel
	public LogHandler(HasWidgets container) {
		super(container);
		// obligé de le reprendre car private dans HasWidgetsLogHandler
		this.container = container;
		scrollPanel.add((IsWidget) container);
		StyleHelper.addStyle(scrollPanel, CSS_SCROLLPANEL);
		setFormatter(new LogFormatter());
	}

	// TODO prevoir les autres formatter... faire du super.publish
	@Override
	public void publish(LogRecord logRecord) {
		HTML logBloc = new HTML(getFormatter().format(logRecord));
		StyleHelper.addStyle(logBloc, CSS_LOG_BLOC);
		container.add(logBloc);
		if (autoScroll) {
			scrollPanel.setVerticalScrollPosition(scrollPanel.getElement().getScrollHeight());
		}
	}

	// FIXME use ?
	// @Override
	// public void flush() {
	// // TODO Auto-generated method stub
	// }
	//
	// // FIXME use ?
	// @Override
	// public void close() throws SecurityException {
	// // TODO Auto-generated method stub
	// }

	public ScrollPanelWithMinSize getScrollPanel() {
		return scrollPanel;
	}
}
