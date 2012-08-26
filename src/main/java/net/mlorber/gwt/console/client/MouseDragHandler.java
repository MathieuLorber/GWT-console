package net.mlorber.gwt.console.client;

import com.google.gwt.event.dom.client.HasAllMouseHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

abstract class MouseDragHandler implements MouseMoveHandler, MouseUpHandler, MouseDownHandler {
	protected boolean dragging = false;
	protected int dragStartX;
	protected int dragStartY;

	public MouseDragHandler() {
		HasAllMouseHandlers hamh = (HasAllMouseHandlers) targetHandler();
		hamh.addMouseDownHandler(this);
		hamh.addMouseUpHandler(this);
		hamh.addMouseMoveHandler(this);
	}

	public abstract void handleDrag(int absX, int absY);

	public abstract Widget targetHandler();

	@Override
	public void onMouseDown(MouseDownEvent event) {
		dragging = true;
		DOM.setCapture(targetHandler().getElement());
		dragStartX = event.getClientX();
		dragStartY = event.getClientY();
		DOM.eventPreventDefault(DOM.eventGetCurrentEvent());
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		if (dragging) {
			handleDrag(event.getClientX() - dragStartX, event.getClientY() - dragStartY);
			dragStartX = event.getClientX();
			dragStartY = event.getClientY();
		}
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		dragging = false;
		DOM.releaseCapture(targetHandler().getElement());
	}
}
