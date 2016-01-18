
/**
 * Write a description of class DoubleNode here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MJSDoubleNode<T>{
    private T value;
    private MJSDoubleNode<T> next; 
    private MJSDoubleNode<T> prev;

    public MJSDoubleNode(T initValue, MJSDoubleNode<T> initNext, MJSDoubleNode<T> initPrev){
        prev = initPrev;
        value = initValue;
        next = initNext;
    }

    public MJSDoubleNode(T initValue) {
        this(initValue, null, null);
    }
    
    public T getValue(){
        return value;
    }
    
    public MJSDoubleNode<T> getNext(){
        return next;
    }
    
    public MJSDoubleNode<T> getPrev(){
        return prev;
    }
    
    public void setValue(T theNewValue){
        value = theNewValue;
    }
    
    public void setNext(MJSDoubleNode<T> theNewNext){
        next = theNewNext;
    }
    
    public void setPrev(MJSDoubleNode<T> theNewPrev){
        prev = theNewPrev;
    }
}
