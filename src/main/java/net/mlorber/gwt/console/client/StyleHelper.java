package net.mlorber.gwt.console.client;

import com.google.gwt.user.client.ui.Widget;

class StyleHelper {
	public static void addStyle(Widget widget, String style) {
		String updatedString = widget.getElement().getAttribute("style") + ";" + style;
		widget.getElement().setAttribute("style", updatedString);
	}
}
