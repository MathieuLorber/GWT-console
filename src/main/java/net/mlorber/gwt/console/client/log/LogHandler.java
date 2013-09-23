package net.mlorber.gwt.console.client.log;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import net.mlorber.gwt.console.client.util.StyleHelper;

import com.google.gwt.logging.client.HasWidgetsLogHandler;
import com.google.gwt.logging.client.HtmlLogFormatter;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;

// FIXME extends HasWidgetsLogHandler pour le caca avec securityexception
public class LogHandler extends HasWidgetsLogHandler {

	// TODO centraliser ?
	// tous les css et resources en base64
	// ...
	private static final String CSS_LOG_PANEL = "background: #fff; padding: 4px 4px 4px 4px; border-bottom: 1px solid #616161;";

	private HasWidgets container;

	// FIXME créer une indirection de plus ou capter comment GWT résout le soucis de SecuException
	// pas vraiment indirection mais faire le job avec LogHandlerPanel
	public LogHandler(HasWidgets container) {
		super(container);
		// obligé de le reprendre car private dans HasWidgetsLogHandler
		this.container = container;
		setFormatter(new LogFormatter());
	}

	// TODO prevoir les autres formatter... faire du super.publish
	@Override
	public void publish(LogRecord record) {
		if (!isLoggable(record)) {
			return;
		}
		Formatter formatter = getFormatter();
		String msg = formatter.format(record);
		// We want to make sure that unescaped messages are not output as HTML to
		// the window and the HtmlLogFormatter ensures this. If you want to write a
		// new formatter, subclass HtmlLogFormatter and override the getHtmlPrefix
		// and getHtmlSuffix methods.
		if (formatter instanceof LogFormatter) {
			HTML logPanel = new HTML(msg);
			StyleHelper.addStyle(logPanel, CSS_LOG_PANEL);
			container.add(logPanel);
		} else if (formatter instanceof HtmlLogFormatter) {
			container.add(new HTML(msg));
		} else {
			container.add(new Label(msg));
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
}
