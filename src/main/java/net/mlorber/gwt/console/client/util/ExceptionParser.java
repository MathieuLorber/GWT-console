package net.mlorber.gwt.console.client.util;

import com.google.web.bindery.event.shared.UmbrellaException;

// TODO it'a all about UmbrellaException
// make something of this ?
// FIXME extends UncaughtExceptionHandler ?
// ou pas... n'apporte rien (?)
public abstract class ExceptionParser {

	// ATTENTION use com.google.web.bindery.event.shared.UmbrellaException and NOT
	// com.google.gwt.event.shared.UmbrellaException
	// FIXME check ok for classes extending UmbrellaE (GWT...)
	/**
	 * @param throwable
	 * @return true if a cause has been handled and parsing loop be stopped
	 */
	public boolean parse(Throwable throwable) {
		boolean identified = false;
		if (throwable.getCause() != null) {
			identified |= parse(throwable.getCause());
		}
		if (throwable instanceof UmbrellaException) {
			if (((UmbrellaException) throwable).getCauses() != null) {
				for (Throwable subthrowable : ((UmbrellaException) throwable).getCauses()) {
					if (!identified) {
						identified |= parse(subthrowable);
					}
				}
			}
		}
		if (!identified) {
			identified |= handleLoop(throwable);
		}
		return identified;
	}

	// ou une map de class - executor
	// attention ordre !
	// si isInstance supporté par gwt

	/**
	 * 
	 * @param throwable
	 * @return true if throwable has been handled and parsing loop be stopped
	 */
	protected abstract boolean handleLoop(Throwable throwable);

}
