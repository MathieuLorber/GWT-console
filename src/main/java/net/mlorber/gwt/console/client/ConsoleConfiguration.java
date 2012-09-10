package net.mlorber.gwt.console.client;

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;

class ConsoleConfiguration {

	private static final String COOKIE_SHOW_CONSOLE = "s";
	private static final String COOKIE_LEFT_POSITION = "l";
	private static final String COOKIE_TOP_POSITION = "r";
	private static final String COOKIE_HEIGHT_SIZE = "h";
	private static final String COOKIE_WIDTH_SIZE = "w";

	private static final int CONSOLE_DEFAULT_WIDTH = 1000;
	private static final int CONSOLE_DEFAULT_HEIGHT = 500;

	private JSONObject json;
	private String cookieName;

	private boolean showConsole;
	private int consoleLeftPosition;
	private int consoleTopPosition;
	private int consoleHeight;
	private int consoleWidth;

	public ConsoleConfiguration() {
	}

	public ConsoleConfiguration(String cookieName) {
		this.cookieName = cookieName;
		String configurationCookieString = Cookies.getCookie(cookieName);
		if (configurationCookieString != null) {
			json = (JSONObject) JSONParser.parseLenient(configurationCookieString);
			// FIXME about cookie format verification
			showConsole = ((JSONBoolean) json.get(COOKIE_SHOW_CONSOLE)).booleanValue();
			consoleLeftPosition = Integer.parseInt(json.get(COOKIE_LEFT_POSITION).toString());
			consoleTopPosition = Integer.parseInt(json.get(COOKIE_TOP_POSITION).toString());
			consoleWidth = Integer.parseInt(json.get(COOKIE_WIDTH_SIZE).toString());
			consoleHeight = Integer.parseInt(json.get(COOKIE_HEIGHT_SIZE).toString());
		} else {
			// FIXME create if String is not awaited one ? (console update for example...) => handle Exception
			json = new JSONObject();
			showConsole = false;
			saveShowConsole(showConsole);
			reset();
		}
	}

	public void saveShowConsole(boolean showConsole) {
		if (json != null) {
			json.put(COOKIE_SHOW_CONSOLE, JSONBoolean.getInstance(showConsole));
			saveCookie();
		}
		this.showConsole = showConsole;
	}

	public void savePosition(int consoleLeftPosition, int consoleTopPosition) {
		if (json != null) {
			json.put(COOKIE_LEFT_POSITION, new JSONNumber(consoleLeftPosition));
			json.put(COOKIE_TOP_POSITION, new JSONNumber(consoleTopPosition));
			saveCookie();
		}
		this.consoleLeftPosition = consoleLeftPosition;
		this.consoleTopPosition = consoleTopPosition;
	}

	public void saveSize(int consoleWidth, int consoleHeight) {
		if (json != null) {
			json.put(COOKIE_HEIGHT_SIZE, new JSONNumber(consoleHeight));
			json.put(COOKIE_WIDTH_SIZE, new JSONNumber(consoleWidth));
			saveCookie();
		}
		this.consoleHeight = consoleHeight;
		this.consoleWidth = consoleWidth;
	}

	public void reset() {
		consoleLeftPosition = (Window.getClientWidth() - CONSOLE_DEFAULT_WIDTH) / 2;
		consoleTopPosition = (Window.getClientHeight() - CONSOLE_DEFAULT_HEIGHT) / 2;
		consoleWidth = CONSOLE_DEFAULT_WIDTH;
		consoleHeight = CONSOLE_DEFAULT_HEIGHT;
		savePosition(consoleLeftPosition, consoleTopPosition);
		saveSize(consoleWidth, consoleHeight);
	}

	private void saveCookie() {
		Cookies.setCookie(cookieName, json.toString());
	}

	public static void clearCookie(String cookieName) {
		Cookies.removeCookie(cookieName);
	}

	public boolean isShowConsole() {
		return showConsole;
	}

	public int getConsoleLeftPosition() {
		return consoleLeftPosition;
	}

	public int getConsoleTopPosition() {
		return consoleTopPosition;
	}

	public int getConsoleHeight() {
		return consoleHeight;
	}

	public int getConsoleWidth() {
		return consoleWidth;
	}
}
