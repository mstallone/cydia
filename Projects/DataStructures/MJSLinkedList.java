
/**
 * Write a description of class MJSLinkedList here.
 * 
 * @author Matthew J. Stallone
 * @version 0.0.1a
 */
public class MJSLinkedList<T>
{

    private ListNode<T> first;

    public void add(int index, T item){
        ListNode<T> newln = new ListNode<T>(item);
        if(index == 0 && first == null){
            first = newln;
        }else if(index == 0){
            ListNode<T> temp = first;
            first = newln;
            newln.setNext(temp);
        }else if(index <= size()){
            ListNode<T> temp = first;
            for(int i = 1; i < index; i++) temp = temp.getNext();
            newln.setNext(temp.getNext());
            temp.setNext(newln);
        }else throw new IndexOutOfBoundsException();
    }

    public boolean add(T item){
        add(size(), item);
        return true;
    }

    public T get(int index){
        if(index >= size()) throw new IndexOutOfBoundsException();
        else{
            ListNode<T> temp = first;
            for(int i = 1; i <= index; i++) temp = temp.getNext();
            return temp.getValue();
        }
    }
    
    public T set(int index, T item){
        if(index >= size()) throw new IndexOutOfBoundsException();
        else{
            ListNode<T> temp = first;
            for(int i = 1; i <= index; i++) temp = temp.getNext();
            temp.setValue(item);
            return item;
        }
    }
    
    public T remove(int index){
        if(index >= size()) throw new IndexOutOfBoundsException();
        else if(index == 0){
            T item = first.getValue();
            first = first.getNext();
            return item;
        }else{
            ListNode<T> temp = first;
            for(int i = 1; i < index; i++) temp = temp.getNext();
            T item = temp.getNext().getValue();
            temp.setNext(temp.getNext().getNext());
            return item;
        }
    }

    public int size(){
        int count = 0;
        ListNode<T> temp = first;
        while(temp != null){
            count++;
            temp = temp.getNext();
        }
        return count;
    }

}
