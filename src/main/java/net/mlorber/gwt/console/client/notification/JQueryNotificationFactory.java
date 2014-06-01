package net.mlorber.gwt.console.client.notification;

import com.google.gwt.dom.client.Element;

// FIXME for autoswitch between simple & jquery impl ?
// FIXME utiliser css3 en fait ?
public class JQueryNotificationFactory extends SimpleNotificationFactory {

	@Override
	protected native void doShowElement(Element element) /*-{
		$wnd.$(element).fadeIn(200);
	}-*/;

	@Override
	protected void doHideElement(Element element, boolean fade) {
		if (fade) {
			doFadeElement(element);
		} else {
			super.doHideElement(element, fade);
		}
	}

	// TODO sortir le temps
	protected native void doFadeElement(Element element) /*-{
		$wnd.$(element).fadeOut(2000);
	}-*/;

}
