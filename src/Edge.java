
public class Edge {
	//attribute for Edge object
     private Node FirstEndPoint;
     private Node SecondEndPoint;
     private String EdgeType;
     //constructor for Edge
     public Edge(Node u, Node v, String type){
    	 FirstEndPoint = u;
    	 SecondEndPoint = v;
    	 EdgeType = type;
     }
     //to get the first end point
     public Node firstEndpoint(){
    	 return FirstEndPoint;
     }
     // to get the second end point
     public Node secondEndpoint(){
    	 return SecondEndPoint;
     }
     //get the string type of the edge
     public String getType(){
    	 return EdgeType;
     }
     //set the type of the edge 
     public void setType(String type){
    	 EdgeType = type;
     }

}
