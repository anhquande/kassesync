package de.anhquan.ordertracker.ui.model;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.TableCellRenderer;

import de.anhquan.ordertracker.MainWindow;
import de.anhquan.ordertracker.ui.Utils;
import de.anhquan.ordertracker.ui.render.DateCellRender;
import de.anhquan.ordertracker.ui.render.GenericCellRender;
import de.anhquan.ordertracker.ui.render.OrderNumberCellRender;
import de.anhquan.ordertracker.ui.render.ProgressCellRender;


public class OrderTable extends JTable {

	private static final long serialVersionUID = 1L;
	
	
	private class PreviousStatusAction extends AbstractAction{
		
		private static final long serialVersionUID = 1L;
		
		public void actionPerformed(ActionEvent e) {
			int curRow = OrderTable.this.getSelectedRow();
			OrderTable.this.model.prevStatus(curRow);
		}
	}

	private class NextStatusAction extends AbstractAction{

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			int curRow = OrderTable.this.getSelectedRow();
			OrderTable.this.model.nextStatus(curRow);
		}
	}
	
	private class ViewStatusAction extends AbstractAction{

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			int curRow = OrderTable.this.getSelectedRow();
			try {
				Utils.openWebpage(new URL(OrderTable.this.model.createStatusURL(curRow)));
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private class CreateStatusAction extends AbstractAction{

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			int curRow = OrderTable.this.getSelectedRow();
			Utils.doPostRequest(OrderTable.this.model.createStatusURL(curRow));
		}
	}
	
	private class ClearStatusAction extends AbstractAction{

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			int curRow = OrderTable.this.getSelectedRow();
			Utils.doPostRequest(OrderTable.this.model.clearStatusURL(curRow));
		}
	}

	private class ViewRouteAction extends AbstractAction{

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			int curRow = OrderTable.this.getSelectedRow();
			String url = OrderTable.this.model.viewRouteURL(curRow);
			if (!url.isEmpty())
				try {
					Utils.openWebpage(new URL(url));
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
	}
	
	private class ShowHelpAction extends AbstractAction{

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			//Open Help Dialog 
			parent.showHelpDialog();
		}
	}

	OrderTableModel model;
	MainWindow parent;

	public OrderTable(MainWindow parent, OrderTableModel model){
		super(model);
		this.parent = parent;
		this.model = model;
		this.getColumn("Status").setCellRenderer(new ProgressCellRender());
		
		final String nextStatus = "NextStatus";
		final String prevStatus = "PrevStatus";
		final String viewStatus = "Viewtatus";
		final String createStatus = "CreateStatus";
		final String clearStatus = "ClearStatus";
		final String viewRoute = "viewRoute";
		final String showHelp = "showHelp";
		
		KeyStroke ctrlRight = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, java.awt.event.InputEvent.CTRL_DOWN_MASK);
		KeyStroke ctrlLeft = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,java.awt.event.InputEvent.CTRL_DOWN_MASK  );
		KeyStroke ctrlO = KeyStroke.getKeyStroke(KeyEvent.VK_O,java.awt.event.InputEvent.CTRL_DOWN_MASK  );
		KeyStroke ctrlN = KeyStroke.getKeyStroke(KeyEvent.VK_N,java.awt.event.InputEvent.CTRL_DOWN_MASK  );
		KeyStroke ctrlC = KeyStroke.getKeyStroke(KeyEvent.VK_C,java.awt.event.InputEvent.CTRL_DOWN_MASK  );
		KeyStroke f11 = KeyStroke.getKeyStroke(KeyEvent.VK_F11,0  );
		KeyStroke f1 = KeyStroke.getKeyStroke(KeyEvent.VK_F1,0 );
		
		InputMap inputMap = getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);		
		inputMap.put(ctrlRight, nextStatus);
		inputMap.put(ctrlLeft, prevStatus);
		inputMap.put(ctrlO, viewStatus);
		inputMap.put(ctrlN, createStatus);
		inputMap.put(ctrlC, clearStatus);
		inputMap.put(f11, viewRoute);
		inputMap.put(f1, showHelp);
		
		ActionMap actionMap = getActionMap();
		actionMap.put(nextStatus, new NextStatusAction());
		actionMap.put(prevStatus, new PreviousStatusAction());
		actionMap.put(viewStatus, new ViewStatusAction());
		actionMap.put(createStatus, new CreateStatusAction());
		actionMap.put(clearStatus, new ClearStatusAction());
		actionMap.put(viewRoute, new ViewRouteAction());
		actionMap.put(showHelp, new ShowHelpAction());
		
		setRowHeight(40);
		setColumnWidth(OrderTableModel.COL_ORDER_NUMBER,100);
		setColumnWidth(OrderTableModel.COL_ORDER_DATE,80);
		setColumnWidth(OrderTableModel.COL_CUSTOMER,120);
		setColumnWidth(OrderTableModel.COL_ADDRESS,200);
		setColumnWidth(OrderTableModel.COL_STATUS,100);
		setDefaultRenderer(Date.class, new DateCellRender());
		setDefaultRenderer(OrderNumber.class, new OrderNumberCellRender());
		setDefaultRenderer(Object.class, new GenericCellRender());

		setVisibleRowCount(10);
	}
	
	public void showAll(){
		
	}
	
	public void showOpenedOrders(){
		
	}
	
	public void showClosedOrders(){
		
	}
	
	public void showOrdersByDeliver(String deliver){
		
	}
	
	public void setVisibleRowCount(int rows){ 
	    int height = 0; 
	    for(int row=0; row<rows; row++) 
	        height += getRowHeight(row); 
	 
	    setPreferredScrollableViewportSize(new Dimension( 
	            getPreferredScrollableViewportSize().width, 
	            height 
	    )); 
	}

	private void setColumnWidth(String col, int width){
		getColumn(col).setWidth(width);
		getColumn(col).setMaxWidth(width);
		getColumn(col).setMinWidth(width);
		getColumn(col).setPreferredWidth(width);
	}
	
	
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row,
			int column) {
		// TODO Auto-generated method stub
		Component c = super.prepareRenderer(renderer, row, column);
//		JComponent jc = (JComponent)c;

		// Alternate row color

//		if (!isRowSelected(row))
//			c.setBackground(row % 2 == 0 ? getBackground() : Color.LIGHT_GRAY);
//		else
//			jc.setBorder(new MatteBorder(0, 1, 0, 0, Color.RED) );

		return c;
	}

}
