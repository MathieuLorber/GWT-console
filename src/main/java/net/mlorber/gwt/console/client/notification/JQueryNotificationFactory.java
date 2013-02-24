package net.mlorber.gwt.console.client.notification;

import com.google.gwt.dom.client.Element;

// FIXME for autoswitch between simple & jquery impl ?
public class JQueryNotificationFactory extends SimpleNotificationFactory {

	@Override
	protected native void showNotification(Element element) /*-{
		$wnd.$(element).fadeIn(200);
	}-*/;

	@Override
	protected native void hideNotification(Element element) /*-{
		$wnd.$(element).fadeOut(2000);
	}-*/;

}
