package de.anhquan.ordertracker.ui.render;

import javax.swing.SwingConstants;

import de.anhquan.ordertracker.ui.model.OrderNumber;


public class OrderNumberCellRender extends GenericCellRender {

	public OrderNumberCellRender() {
		super();
		setHorizontalAlignment(SwingConstants.CENTER);
	}

	@Override
	protected void setValue(Object aValue) {
		Object result = aValue;
		if ((aValue != null) && (aValue instanceof OrderNumber)) {
			result = "#"+aValue.toString();
		}
		super.setValue(result);
	}

}
