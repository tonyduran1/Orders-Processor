package processor;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OrderList {

	 
	private ArrayList<Order> orderList = new ArrayList<Order>();
	
	public void addOrderToList(Order order) {
		orderList.add(order);
	}
	public void sort() {
		Collections.sort(orderList);
	}
	public String getDetailsAndSummary() {
		double total = 0.0;
		String s = "";
		for(int i = 0; i < orderList.size(); i++) {
			s += orderList.get(i).getOrderDetails(); 
			s+= "\n";
		}
		
		s+= "***** Summary of all orders *****\n";
		Map<String, Integer> totalQuantity = new HashMap<>(); // lists for the quantity and costs of each item
		Map<String, Double> totalCost = new HashMap<>();

		for(Order order : orderList) {
			for(Item item : order.getCart().values()) {
				String name = item.getName();
				int quantity = item.getQuantity();
				double cost = item.getCost();
				
				int currentQuantityOfItem = totalQuantity.getOrDefault(name, 0); 
															// returns value for that item or zero
														    // if the item is not in the map
				totalQuantity.put(name, currentQuantityOfItem + quantity);	
													// the line above updates the item in the map to increase
													// by the quantity amount
				double currentCostOfItem = totalCost.getOrDefault(name, 0.0);
				totalCost.put(name, currentCostOfItem + cost);
			}
		}
		Set<Map.Entry<String,Integer>> set = totalQuantity.entrySet();
		List<Map.Entry<String,Integer>> list = new ArrayList<>(set);
		Collections.sort(list, Comparator.comparing(Map.Entry::getKey)); // made the map a set and the set a list to sort and iterate
		
		for(Map.Entry<String,Integer> entry : list) { // now have the total quantity and cost of each item
			String item = entry.getKey();
			int quantity = entry.getValue();
			double cost = totalCost.get(item);
			
			s+= "Summary - Item's name: " + item + ", Cost per item: " + NumberFormat.getCurrencyInstance().format(cost / quantity) 
					+ ", Number sold: " + quantity + ", Item's Total: " + NumberFormat.getCurrencyInstance().format(cost) + "\n" ;
			total+= cost;
		}
	 
		s+= "Summary Grand Total: " + NumberFormat.getCurrencyInstance().format(total) +"\n";
	
		return s;
	}
	public String getSummary() {
		String s = "";
		double total = 0.0;
		s+= "***** Summary of all orders *****\n";
		Map<String, Integer> totalQuantity = new HashMap<>(); // lists for the quantity and costs of each item
		Map<String, Double> totalCost = new HashMap<>();

		for(Order order : orderList) {
			for(Item item : order.getCart().values()) {
				String name = item.getName();
				int quantity = item.getQuantity();
				double cost = item.getCost();
				
				int currentQuantityOfItem = totalQuantity.getOrDefault(name, 0); 
															// returns value for that item or zero
														    // if the item is not in the map
				totalQuantity.put(name, currentQuantityOfItem + quantity);	
													// the line above updates the item in the map to increase
													// by the quantity amount
				double currentCostOfItem = totalCost.getOrDefault(name, 0.0);
				totalCost.put(name, currentCostOfItem + cost);
			}
		}
		Set<Map.Entry<String,Integer>> set = totalQuantity.entrySet();
		List<Map.Entry<String,Integer>> list = new ArrayList<>(set);
		Collections.sort(list, Comparator.comparing(Map.Entry::getKey));
		
		for(Map.Entry<String,Integer> entry : list) { // now have the total quantity and cost of each item
			String item = entry.getKey();
			int quantity = entry.getValue();
			double cost = totalCost.get(item);
			
			s+= "Summary - Item's name: " + item + ", Cost per item: " + NumberFormat.getCurrencyInstance().format(cost / quantity) 
					+ ", Number sold: " + quantity + ", Item's Total: " + NumberFormat.getCurrencyInstance().format(cost) + "\n" ;
			total+= cost;
		}
	 
		s+= "Summary Grand Total: " + NumberFormat.getCurrencyInstance().format(total) +"\n";
	
		return s;
	}
}
