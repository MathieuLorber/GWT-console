package net.mlorber.gwt.console.client.util;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

public class StyleHelper {
	public static void addStyle(Widget widget, String style) {
		addStyle(widget.getElement(), style);
	}

	public static void addStyle(Element element, String style) {
		String updatedString = element.getAttribute("style") + ";" + style;
		element.setAttribute("style", updatedString);
	}
}
