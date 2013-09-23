package net.mlorber.gwt.console.client.log;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class LogHandlerPanel extends Composite {

	// TODO centraliser ?
	// tous les css et resources en base64
	// ...
	private static final String CSS_LOG_PANEL = "display: block;background: #fff;margin: 1px;border: 1px solid #ddd;";

	private FlowPanel panel = new FlowPanel();

	public LogHandlerPanel() {
		initWidget(panel);
	}

	// StyleHelper.addStyle(logPanel, CSS_LOG_PANEL);

}
