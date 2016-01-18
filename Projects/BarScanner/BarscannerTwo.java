import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;


public class BarscannerTwo {
	@SuppressWarnings("rawtypes")
	private LinkedList[] thetable;
	
	@SuppressWarnings("unchecked")
	public BarscannerTwo(){
		thetable = (LinkedList<ItemPrice>[]) new LinkedList[1000];
	}
	
	private int hashFunction(long value){
        return (int) (value % 1000);
    }
	
	public void addItemToStore(long UPC, double price){
		LinkedList<ItemPrice> list;
		if(thetable[hashFunction(UPC)] != null) list = thetable[hashFunction(UPC)];
		else list = new LinkedList<ItemPrice>();
		boolean alreadyExists = false;
		ListIterator<ItemPrice> itr = (ListIterator<ItemPrice>) list.iterator();
		while(itr.hasNext()){
			ItemPrice item = itr.next();
			if(item.getUPC() == UPC){
				itr.set(new ItemPrice(UPC, price));
				alreadyExists = true;
				break;
			}
		}
		if(!alreadyExists) {
			list.add(new ItemPrice(UPC, price));
			thetable[hashFunction(UPC)] = list;
		}
	}
	
	public double findPrice(long UPC){
		if(thetable[hashFunction(UPC)] != null){
			LinkedList<ItemPrice> list = thetable[hashFunction(UPC)];
			Iterator<ItemPrice> itr = list.iterator();
			while(itr.hasNext()){
				ItemPrice item = itr.next();
				if(item.getUPC() == UPC){
					return item.getPrice();
				}
			}
		}
		return -1.0f;
	}
	
	public void printTheTable(){
		for(LinkedList<ItemPrice> ll : thetable){
			if(ll != null){
				ListIterator<ItemPrice> itr = (ListIterator<ItemPrice>) ll.iterator();
				while(itr.hasNext()){
					System.out.println(itr.next());
				}
			}
		}
	}
}
