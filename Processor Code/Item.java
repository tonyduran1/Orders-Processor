package processor;

public class Item {

	private String name;
	private double price;
	private int quantity;
	
	public Item(String name) {
		this.name = name;
		this.price = 0.0;
		quantity = 1;
	}
	public Item(String name,double price) {
		this.name = name;
		this.price = price;
		quantity = 1;
	}
	public String toString() {
		return name + " Price: " + price;
	}
	public void increaseQuantity() {
		quantity+=1;
	}
	public String getName() {
		return name;
	}
	public double getPrice() {
		return price;
	}
	public int getQuantity() {
		return quantity;
	}
	public double getCost() {
		return price* (double)quantity ;
	}
	
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(!(obj instanceof Item)) {
			return false;
		}
		Item item = (Item) (obj);
		return item.getName().equals(this.getName());
	}
}
