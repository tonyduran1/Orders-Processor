package processor;

import java.util.HashMap;
import java.util.Map;

public class ItemList {

	private Map<String,Item> itemList = new HashMap<String,Item>();
	
	public void addItemToList(String name , double price) {
		itemList.put(name, new Item(name,price));
	}
	public Map<String,Item> getList(){
		return itemList;
	}
}
