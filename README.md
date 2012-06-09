GWT-console
===========

A console for GWT logging and notifications.

#### HOW-TO

1) Add jar in your dependencies ([jar file here](http://www.mlorber.net/maven_repo/net/mlorber/gwt/console/1.0/console-1.0.jar), below for Maven).

2) Add inherence in your gwt.xml file :

	<inherits name='net.mlorber.gwt.console.Console' />

3) In your GWT code :

	Console.get().quickInit(true, true, true);

You can build the library with Maven (mvn install)

#### WHY ?

The console is for development purpose.
It's a handler for [log API](https://developers.google.com/web-toolkit/doc/latest/DevGuideLogging), more convenient than the default console handler.
Furthermore, it gives you the ability to :
* manually log informations
* embed test widgets without disrupting application interface
* display notifications
The console can be moved and resized.

#### CONSOLE API

	// Init
	Console.get().quickInit(boolean registerToRootLogger, Level notifyLevel, boolean registerShorcut, boolean addSwitchButtonOnTopRight);
		registerToRootLogger is pretty well named
		registerShorcut : if true, shift + alt + C will show/hide the console
		addSwitchButtonOnTopRight : if true, a show/hide button will be displayed at the top-right of the screen

	// Log
	Console.get().log(String logMessage, String messageTitle);
		message and title can be HTML (beware XSS !)

	Console.get().notify(String message);
		notification is displayed 5 seconds at the top right of the screen

	Console.get().add(Widget widget);
		allows to embed test widgets (to manually fire an event for example)

#### MISC

Use GWT.isProd() to only use it in development.

#### MAVEN DEPENDENCY

	<dependencies>
		[...]
		<dependency>
			<groupId>net.mlorber.gwt</groupId>
			<artifactId>console</artifactId>
			<version>1.0</version>
		</dependency>
	</dependencies>

	<repositories>
		[...]
		<repository>
			<id>GWT console repo</id>
			<url>http://www.mlorber.net/maven_repo</url>
		</repository>
	<repositories>
