package processor;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order implements Comparable<Order> {

	 
	private int orderId;
	private Map<String,Item> cart;
	
	public Order(int orderId) {
		this.orderId = orderId;
		cart = new HashMap<String,Item>();
	}
	public int getOrderId() {
		return orderId;
	}
	public void addItemToOrder(Item item) { 
		String itemName = item.getName();
		
	    if (cart.containsKey(itemName)) { 
	        Item existingItem = cart.get(itemName); 
	        existingItem.increaseQuantity();  
	    }
	    else { 
	        Item newItem = new Item(item.getName(), item.getPrice());
	        // this line above creates a new item with the string of the item object 
	        // in the parameter is not in the list yet this item will have the same name and 
	        // price as the other item but starts with a quantity of one 
	        // always so no matter what the quantity of the item that call this method on,
	        // it will have no effect on the quantity of the item that is being added to the list
	        cart.put(itemName, newItem);
	    }
	}
	
	 
	 
	public String getOrderDetails() {
		 
		System.out.println("Reading order for client with id: " + orderId);
		double total = 0.0;
		 
		// now should have a list of the items with respective quantity
		String s = "----- Order details for client with Id: " + orderId + " -----\n";
		 
		List<Map.Entry<String,Item>> listOfItems = new ArrayList<>(cart.entrySet()); 
											// make the map a set then make an arraylist of the set 
		Collections.sort(listOfItems, Comparator.comparing(Map.Entry::getKey));
																			
		
		for(Map.Entry<String,Item> entry : listOfItems) {
			Item item = entry.getValue();
			s += "Item's name: " + item.getName() + ", Cost per item: "
					+ NumberFormat.getCurrencyInstance().format(item.getPrice()) 
					+ ", Quantity: " +  item.getQuantity() 
					+ ", Cost: " + NumberFormat.getCurrencyInstance().format(item.getCost()) + "\n"; 
			 
			total += (item.getPrice() * item.getQuantity());
		}
		 
		s+= "Order Total: " + NumberFormat.getCurrencyInstance().format(total);
		return s; 
	}
	public Map<String, Item> getCart() {
		return cart;
	}
	@Override
	public int compareTo(Order o) {
		return this.orderId-o.orderId;
	}
}
