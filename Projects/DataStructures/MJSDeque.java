import java.util.*;
/**
 * Write a description of class Deque here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MJSDeque<T>{
    private MJSDoubleNode<T> first;
    private MJSDoubleNode<T> rear;

    public void addFirst(T item){
        MJSDoubleNode<T> ln = new MJSDoubleNode<T>(item);
        if(isEmtpy()){
            first = ln;
            rear = ln;
        }else{
            first.setPrev(ln);
            ln.setNext(first);
            first = ln;
        }
    }
    
    public void addLast(T item){
        MJSDoubleNode<T> ln = new MJSDoubleNode<T>(item);
        if(isEmtpy()){
            first = ln;
            rear = ln;
        }else{
            rear.setNext(ln);
            ln.setPrev(rear);
            rear = ln;
        }
    }
    
    public T peekFirst(){
        if(first != null) return first.getValue();
        throw new NoSuchElementException();
    }
    
    public T peekLast(){
        if(rear != null) return rear.getValue();
        throw new NoSuchElementException();
    }
    
    public T removeFirst(){
        MJSDoubleNode<T> removee = first;
        if(first == rear && first != null){
            first = null;
            rear = null;
            return removee.getValue();
        }
        if(first != null) {
            first = first.getNext();
            first.setPrev(null);
            return removee.getValue();
        }throw new NoSuchElementException();
    }
    
    public T removeLast(){
        MJSDoubleNode<T> removee = rear;
        if(first == rear && rear != null){
            first = null;
            rear = null;
            return removee.getValue();
        } 
        if(rear != null) {
            rear = rear.getPrev();
            rear.setNext(null);
            return removee.getValue();
        }throw new NoSuchElementException();
    }
    
    public boolean isEmtpy(){
        if(first == null && rear == null) return true;
        else return false;
    }
}
