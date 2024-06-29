package processor;

import java.io.FileNotFoundException;

public class OrderProcessorThread implements Runnable {

	private String fileName;
	private ItemList itemList;
	private OrderList orderList;
	
	public OrderProcessorThread(String filename, ItemList itemlist, OrderList orderlist) {
		fileName = filename;
		itemList = itemlist;
		orderList = orderlist;
	}
	
	@Override
	public void run() {
		synchronized(orderList){ // only only thread can access this at a time
			Order order = null;
			try {
				order = OrdersProcessor.readFile(fileName, itemList);
				orderList.addOrderToList(order);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} 
	}
}
