
/**
 * Write a description of class MJSQueue here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MJSQueue<T>
{
    private ListNode<T> front = null;
    private ListNode<T> rear = null;

    public boolean add(T item){
        ListNode<T> ln = new ListNode<T>(item);
        if(isEmtpy()){
            front = ln;
            rear = ln;
        }else{
            rear.setNext(ln);
            rear = rear.getNext();
        }
        return true;
    }

    public T remove(){
        ListNode<T> removee = front;
        if(front == rear){
            front = null;
            rear = null;
        }else front = front.getNext();
        if(removee == null) throw new IndexOutOfBoundsException();
        else return removee.getValue();
    }

    public T peek(){
        if(front != null) return front.getValue();
        throw new IndexOutOfBoundsException();
    }

    public boolean isEmtpy(){
        if(front == null && rear == null) return true;
        else return false;
    }
    
    public int size(){
        int count = 0;
        ListNode<T> temp = front;
        while(temp != null){
            count++;
            temp = temp.getNext();
        }
        return count;
    }
}
