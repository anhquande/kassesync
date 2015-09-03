package de.anhquan.ordertracker.ui.render;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import de.anhquan.ordertracker.ui.model.OrderTrackingStatus;


public class ProgressCellRender extends JProgressBar implements TableCellRenderer {

	private static final long serialVersionUID = 1L;

	Color[] colors = { new Color(0x6B,0x8F,0xD4), new Color(0x45,0x75,0xD4), new Color(0x05,0x29,0x6E), new Color(0x10,0x29,0x6E)};
	
	public ProgressCellRender(){
		super(0,OrderTrackingStatus.values().length);
		setStringPainted(true);
		setBorder(BorderFactory.createEmptyBorder(5, 1, 5, 1));
	}
	
	@Override
	public String getString() {
		int i = getValue();
		OrderTrackingStatus status = OrderTrackingStatus.values()[i-1];
		return status.getTitle();
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        OrderTrackingStatus status = (OrderTrackingStatus)value;
        setValue(1+status.getValue());
        setForeground(colors[status.getValue()]);
        return this;
    }
}
