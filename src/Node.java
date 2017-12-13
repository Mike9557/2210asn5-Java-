//Class for Node
public class Node {
	
	//attribute for node
	private int Nodename;
	private boolean Nodemark;
	//constructor
	public Node(int name){
		Nodename = name;
	}
	//function to set the node mark
	public void setMark(boolean mark){
		Nodemark = mark;
	}
	//function to get the node mark
	public boolean getMark(){
		return Nodemark;
	}
	//function to get the node name
	public int getName(){
		return Nodename;
	}

}
