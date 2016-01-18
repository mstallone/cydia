import java.util.*;

public class TreeNode<T> {
	private ArrayList<TreeNode<T>> connections; 
	private T value;
	
	public TreeNode(T v, TreeNode<T> a, TreeNode<T> b){
	    connections = new ArrayList<TreeNode<T>>();
		value = v;
		connections.add(a);
		connections.add(b);
	}
	
	public TreeNode(T v, TreeNode<T> a){
		this(v, a, null);
	}
	
	public TreeNode(T v){
		this(v, null, null);
	}

	public void addConnection(TreeNode<T> a){
		connections.add(a);
	}
	
	public ArrayList<TreeNode<T>> getConnections(){
		return connections;
	}
	
	public TreeNode<T> getConnection(int i){
		return connections.get(i);
	}
	
	public void setConnection(int i, TreeNode<T> a){
		connections.set(i, a);
	}

	public void deleteConnection(int i){
		connections.remove(i);
	}
	
	public void setValue(T v){
		value = v;
	}
	
	public T getValue(){
		return value;
	}
}
