package processor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

public class OrdersProcessor {	

 
	public static void main(String[] args) throws IOException, InterruptedException {
	
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter item's data file name: ");
		String fileName = scanner.next();
		System.out.println("Enter 'y' for multiple threads, any other character otherwise: ");
		String threadOrNo  = scanner.next();
		System.out.println("Enter number of orders to process: ");
		int numberOfOrders = scanner.nextInt();
		System.out.println("Enter order's base filename: ");
		String baseFileName = scanner.next();
		System.out.println("Enter result's filename: ");
		String resultsFileName = scanner.next();
		
		long startTime = System.currentTimeMillis();
		
		

		ItemList itemList = readItemList(fileName); // with this method it makes an List object which is just a 
													// object that has a map of all the items that the store sells
		OrderList orderList = new OrderList();
		
		
		
		
		if(threadOrNo.equals("y")) { // multiple thread path
			
			Thread[] threads = new Thread[numberOfOrders];
			for(int i = 1; i <= numberOfOrders; i++) {
				String orderFile = baseFileName + i + ".txt";
				threads[i-1] = new Thread(new OrderProcessorThread(orderFile,itemList,orderList)); // i-1 because array starts at 0
				threads[i-1].start();	
			}
			for(Thread thread : threads) {
				thread.join();
			}
			// starting and joining every method so nothing else performs before this 
			orderList.sort();
			writeOrderInFile(resultsFileName, orderList.getDetailsAndSummary());
		}
		else{ // single thread path
			 
		
			for(int i = 1; i <= numberOfOrders; i++) {
												//  now that have all the items in the list, go through each order
												// 	file and read the order 
				String string = baseFileName + i + ".txt";
				Order currentOrder = readFile(string,itemList);
				orderList.addOrderToList(currentOrder);
			
			}
	
			writeOrderInFile(resultsFileName, orderList.getDetailsAndSummary());
			/*
			 * So for the first test the inputs would be :
			 * 
			 * fileName = itemsData.txt  * the item data file is a list of all the items this store sells 
			 * threadOrNo = n
			 * numberOfOrders = 3
			 * baseFile = example
		  	 * resultsFile = pubTestResults.txt
		 	 * 
		 	 */
			}
		long endTime = System.currentTimeMillis();
		System.out.println("Processing time (msec): " + (endTime-startTime));
		System.out.println("Results can be found in the file: " + resultsFileName);
	 
	}
	public static ItemList readItemList(String file) throws FileNotFoundException {

		Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)));
		ItemList itemList = new ItemList();
		while(scanner.hasNext()) {
			String currentLine = scanner.nextLine();
			String item = currentLine.substring(0,currentLine.indexOf(" "));
			double price = Double.parseDouble(currentLine.substring(currentLine.indexOf(" ")));
			itemList.addItemToList(item, price); 
		}
		return itemList;
	}
	
	
	public static Order readFile(String file, ItemList list) throws FileNotFoundException {
	
		 
		Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)));
		
		String currentLine = "";
		int orderNumber = 0;
		Order newOrder = null;
		
		while(scanner.hasNext()) {
			currentLine = scanner.nextLine();
			String item = currentLine.substring(0,currentLine.indexOf(" ")).trim();
			
			if(!item.equals("ClientId:")) {
														// the path that adds that item to that specific order
				Map<String,Item> newList = list.getList();
				Item currentItem = newList.get(item);
				newOrder.addItemToOrder(currentItem);	// now the order adds an item with a price
														// even though the order does not list the price
			}
			else {
													// the path to create the order if the string is "ClientId"
				String orderId = currentLine.substring(currentLine.indexOf(" ")).trim();
				orderNumber = Integer.parseInt(orderId);
				newOrder = new Order(orderNumber);
			}
			 
		}
		
	 return newOrder;
	}
	
	public static void writeOrderInFile(String filename, String results) throws IOException {
		BufferedWriter writing = new BufferedWriter(new FileWriter(new File(filename)));
		writing.write(results);
		writing.close();
	}
	
}