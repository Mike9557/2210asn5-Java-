
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
//constructor for Graph
public class Graph implements GraphADT{
	private int number;
    Node [] Nodes;
	Edge [][] Matrix;
	
	  
	public Graph(int n ){
		//n represents the number of node, and the matrix is used to store the edge, the list is to store the nodes
		number = n;
		Matrix = new Edge[n][n];
		Nodes = new Node[n];
		//set up the node
		for(int i = 0; i < n; i ++){
			Nodes[i] = new Node(i);
		}
		
		

		
	}
	//insert the edge and the edge type into the matrix 
	public void insertEdge(Node nodeu, Node nodev, String edgeType) throws GraphException {
		// if the node's name is greater than the size, raise the exception;
		if(nodeu.getName()>= number || nodev.getName()>= number){
			
			throw new GraphException();
		}
		else{
			//get the name 
			int nameu = nodeu.getName();
			int namev = nodev.getName();
			
			//if the edge is already exist, raise the exception
			if(Matrix [nameu][namev] != null && Matrix[namev][nameu] != null){
			
				throw new GraphException();
			}
			else{
				// insert the edge(two location
				Edge temp = new Edge(nodeu,nodev,edgeType);
				Matrix[nameu][namev] = temp;
				Matrix[namev][nameu] = temp;
			}
		}
		
	}
	@Override
	//get the node 
	public Node getNode(int name) throws GraphException {
		// if the node's name is greater than the size, raise the exception;
		if (name >=number){
			
			throw new GraphException();
		}
		//else return the node
		return Nodes[name];
	}
	@Override
	public Iterator incidentEdges(Node u) throws GraphException {
		// TODO Auto-generated method stub
		// if the node's name is greater than the size, raise the exception;
		if (u.getName()>=number){
			
			throw new GraphException();
		}
		//set up new stack 
		Stack temp = new Stack();
		for (int i = 0; i < number ; i++){
			//if the edge is not null, push it
			if(Matrix[u.getName()][i]!=null){
				temp.push(Matrix[u.getName()][i]);
			}
		}
		//return its iter;
	
		Iterator<Edge> iter = temp.iterator();
		return iter;
	}
	@Override
	public Edge getEdge(Node u, Node v) throws GraphException {
		// TODO Auto-generated method stub
		// if the node's name is greater than the size, raise the exception;
		if(u.getName()>= number || v.getName()>=number){
			
			throw new GraphException();
		}
		//if the edge is null, then raise the exception
		else if(Matrix[u.getName()][v.getName()] == null && Matrix[v.getName()][u.getName()] == null){
			
			throw new GraphException();
		}
		//else return the edge
		else{
			return Matrix[u.getName()][v.getName()];
		}
			
		
	}
	//return true if the node u and v is adjacent to each other.
	public boolean areAdjacent(Node u, Node v) throws GraphException {
		// if the node's name is greater than the size, raise the exception;
		if(u.getName()>= number || v.getName()>=number){
			
			throw new GraphException();
		}
		//if the edge is not null, return true
		else if(Matrix[u.getName()][v.getName()] != null && Matrix[u.getName()][v.getName()]!= null){
			return true;
		}
		//otherwise return false;
		else{
			return false;
		}
	}
	
}
	
