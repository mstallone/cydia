public class ItemPrice {
	private long UPC;
	private double price;
	
	public ItemPrice(long UPC, double price){
		this.UPC = UPC;
		this.price = price;
	}
	
	public long getUPC(){
		return UPC;
	}
	
	public double getPrice(){
		return price;
	}

	public void changePrice(double price){
		this.price = price;
	} 
	
	public void changeUPC(long UPC){
		this.UPC = UPC;
	}
	
	public String toString(){
		return "[UPC: " + UPC + " price: " + price + "]";
	}
}
