package net.mlorber.gwt.console.client.log;

import java.util.HashSet;

import com.google.gwt.core.client.impl.SerializableThrowable;
import com.google.gwt.logging.client.HtmlLogFormatter;

// FIXME documenter google code et pouvoir use un formatter d'origine
public class LogFormatter extends HtmlLogFormatter {

	public LogFormatter() {
		super(true);
	}

	@Override
	protected String getStackTraceAsString(Throwable e, String newline, String indent) {
		if (e == null) {
			return "";
		}
		// For each cause, print the requested number of entries of its stack
		// trace, being careful to avoid getting stuck in an infinite loop.
		//
		StringBuffer s = new StringBuffer(newline);
		Throwable currentCause = e;
		String causedBy = "";
		HashSet<Throwable> seenCauses = new HashSet<Throwable>();
		while (currentCause != null && !seenCauses.contains(currentCause)) {
			seenCauses.add(currentCause);
			s.append(causedBy);
			causedBy = newline + "Caused by: "; // after 1st, all say "caused by"
			if (currentCause instanceof SerializableThrowable.ThrowableWithClassName) {
				s.append(((SerializableThrowable.ThrowableWithClassName) currentCause).getExceptionClass());
			} else {
				s.append(currentCause.getClass().getName());
			}
			s.append(": " + currentCause.getMessage());
			StackTraceElement[] stackElems = currentCause.getStackTrace();
			if (stackElems != null) {
				// FIXME stackElems.length
				for (int i = 0; i < 2; ++i) {
					s.append(newline + indent + "at ");
					s.append(stackElems[i].toString());
				}
			}

			currentCause = currentCause.getCause();
		}
		return s.toString();
	}

}
