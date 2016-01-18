import java.util.LinkedList;


public class BarscannerOne {
	private ListNode<ItemPrice>[] hash;
	
    public BarscannerOne(){
        hash = new ListNode[1000];
        for (int i = 0; i < hash.length; i++) hash[i] = null;
    }

    private int hashFunction(long value){
        return (int) (value % 1000);
    }
    
    public void addItemToStore(long UPC, double price){
        ListNode<ItemPrice> index = hash[hashFunction(UPC)];
        boolean added = false;
        if(index != null){
            while(index.getNext() != null) {
                if(index.getValue().getUPC() == UPC){
                    index.getValue().changePrice(price);
                    added = true;
                    break;
                }else index = index.getNext();
            }
            if(!added) index.setNext(new ListNode<ItemPrice>(new ItemPrice(UPC, price)));
        }else hash[hashFunction(UPC)] = new ListNode<ItemPrice>(new ItemPrice(UPC, price));
    }
    
    public double findPrice(long UPC){
    	if(hash[hashFunction(UPC)] != null){
    		ListNode<ItemPrice> list = hash[hashFunction(UPC)];
    		while(list.getNext() != null){
    			if(list.getValue().getUPC() == UPC) return list.getValue().getPrice();
    			list = list.getNext();
    		}
    	}
    	return -1.0f;
    }
    
    public void printTheTable(){
        for(ListNode<ItemPrice> index : hash){
            if(index != null){
                System.out.println(index.getValue());
                while(index.getNext() != null) {
                    index = index.getNext();
                    System.out.println(index.getValue());
                }
            }
        }
    } 
}
