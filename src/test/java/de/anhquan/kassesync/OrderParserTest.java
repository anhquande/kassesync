package de.anhquan.kassesync;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import de.anhquan.ordertracker.parser.CustomerMapper;
import de.anhquan.ordertracker.parser.OrderBuildHelper;
import de.anhquan.ordertracker.parser.OrderParser;
import de.anhquan.ordertracker.parser.OrderParsingException;
import de.anhquan.ordertracker.parser.model.Order;

public class OrderParserTest {

	@Test
	public void testParse() throws IOException, OrderParsingException {
		
		CustomerMapper.loadFromFile();

		Order order = OrderParser.parse(new File("test-incoming.txt"));
		System.out.println(OrderBuildHelper.toXML(order));
		//OrderBuildHelper.toXML(order, "C:/ProgramData/PixelPlanet/WinOrder/EShop/Incoming");
		OrderBuildHelper.toXML(order, "C:/Users/anhquan/AppData/Local/VirtualStore/ProgramData/PixelPlanet/WinOrder/EShop/Incoming");
		
	}
}
