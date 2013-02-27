package net.mlorber.gwt.console.client;

import com.google.web.bindery.event.shared.UmbrellaException;

// TODO it'a all about UmbrellaException
// make something of this ?
public abstract class ExceptionParser {

	// ATTENTION use com.google.web.bindery.event.shared.UmbrellaException and NOT
	// com.google.gwt.event.shared.UmbrellaException
	// FIXME check ok for classes extending UmbrellaE (GWT...)
	/**
	 * 
	 * @param throwable
	 * @return true if it had been
	 */
	public boolean parseAndHandle(Throwable throwable) {
		boolean identified = false;
		if (throwable instanceof UmbrellaException) {
			if (((UmbrellaException) throwable).getCauses() != null) {
				for (Throwable subthrowable : ((UmbrellaException) throwable).getCauses()) {
					identified |= parseAndHandle(subthrowable);
				}
			}
		} else {
			// FIXME verify useless if umbrella
			if (throwable.getCause() != null) {
				identified |= parseAndHandle(throwable.getCause());
			}
		}
		identified |= identifyAndHandle(throwable);
		return identified;
	}

	// ou une map de class - executor
	// attention ordre !
	// si isInstance supporté par gwt

	/**
	 * 
	 * @param throwable
	 * @return true if parent parsed exception must be considered as traitée, false if must be notified as unknown
	 */
	protected abstract boolean identifyAndHandle(Throwable throwable);

}
