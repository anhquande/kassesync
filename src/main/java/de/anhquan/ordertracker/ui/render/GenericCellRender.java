package de.anhquan.ordertracker.ui.render;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import de.anhquan.ordertracker.ui.model.OrderTrackingStatus;


public class GenericCellRender extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub
		Component c = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);

		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		OrderTrackingStatus status = (OrderTrackingStatus) table.getModel().getValueAt(row, 4);

		switch (status) {
		case READY:
			c.setForeground(Color.RED);
			c.setFont(new java.awt.Font("ARIAL UNICODE MS", Font.BOLD, 13));
			break;
		case COOKING:
			c.setForeground(Color.BLUE);
			c.setFont(new java.awt.Font("ARIAL UNICODE MS", Font.PLAIN, 13));
			break;
		case SENDING:
			c.setForeground(Color.gray);
			c.setFont(new java.awt.Font("ARIAL UNICODE MS", Font.PLAIN, 12));
			break;
		default:
			break;
		}

		return c;
	}
}
