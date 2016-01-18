import java.util.*;
/**
 * This is an extension of of the Java stack class.
 * 
 * @author Matthew J. Stallone
 * @version 0.0.1
 */
public class MJSStack<T>
{   
    private ArrayList<T> a;
    
    public MJSStack(){
        a = new ArrayList<T>();
    }
    
    public T peek(){
        if(a.size() == 0) throw new EmptyStackException();
        else{
            return a.get(a.size()-1);
        }
    }
    
    public T pop(){
        if(a.size() == 0) throw new EmptyStackException();
        else{
            T item = a.get(a.size()-1);
            a.remove(a.size()-1);
            return item;
        }
    }
    
    public T push(T item){
        a.add(item);
        return item;
    }
    
    public boolean empty(){
        if(a.size() == 0) return true;
        else return false;
    }
    //--//Method below only used for the gui postfix calculator//--//
    public int size(){
        return a.size();
    }
}
