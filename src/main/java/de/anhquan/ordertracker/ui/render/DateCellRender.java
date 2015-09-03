package de.anhquan.ordertracker.ui.render;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.SwingConstants;

import de.anhquan.ordertracker.ui.model.OrderTrackingStatus;


public class DateCellRender extends GenericCellRender {

	private static final long serialVersionUID = 1L;
	SimpleDateFormat df = new SimpleDateFormat("HH:mm");

	public DateCellRender() {
		super();
		setHorizontalAlignment(SwingConstants.CENTER);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		Object result = value;
		if ((value != null) && (value instanceof Date)) {
			result = df.format((Date) value);
			
			OrderTrackingStatus status = (OrderTrackingStatus) table.getModel().getValueAt(row, 4);

			switch (status) {
			case COOKING:
				result = "\u2668 "+result;
				break;
			case SENDING:
				result = "\u2713 "+result;	//2708 Flight  // 2713 Checked
				break;
			default:
				result = "\u2708 "+result;
				break;
			}
		}
		
		Component c = super.getTableCellRendererComponent(table, result, isSelected, hasFocus,
				row, column);
		return c;
	}
}
