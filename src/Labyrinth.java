import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;


public class Labyrinth {
	Graph Laby = null;// graph for Labyrinth;
	int brickbomb;// the number of brickbomb
	int acidbomb;// the number of acidbomb
	int row;//number of row
	int column;//number of column
	int number;//number of node 
	Node start,exit;//starting node, exit node
	Stack<Node> s = new Stack();//store path node;
	
	public Labyrinth(String inputFile) throws LabyrinthException, GraphException {
		BufferedReader in;//input the file
		
		String line;//the content of the file
	      

		ArrayList<String> temp = new ArrayList<String>();//store the content 
		try {
				in = new BufferedReader(new FileReader(inputFile));
				line = in.readLine();//input the file
				while(line != null){
					temp.add(line);//make sure the line is not empty
					line = in.readLine();
				}
				in.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				throw new LabyrinthException();//if the file is not found, raise the exception;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();//if the input error occurs, raise the exception;
			}
		    row = Integer.parseInt(temp.get(1));//get the row
		    column = Integer.parseInt(temp.get(2));// get the column
		    number = row*column;//node number = row * column;
		    brickbomb = Integer.parseInt(temp.get(3));// get the brickbomb number;
		    acidbomb = Integer.parseInt(temp.get(4));//get the acidbomb number;
		    
			Laby = new Graph(number);// create the new graph 
			
			for(int i = 5; i < temp.size();i = i+2){
				
				if(i+1 > temp.size()){break;}//if the loop reaches the end of table,end the loop;
				
				String line1 = temp.get(i); // the first line;
				
				String line2 = null;
				if (i+1 < temp.size()){
				  line2 = temp.get(i+1); //the second line(edge);
				}
			
				//Vertical edge 
				//indicate the name of the first node in this row;
				int line1name = ((i-5)/2+1)*row - row;
				int line2name = line1name;
				int index = 0;
				//go through line1
				while(index < line1.length()){
					
					
					if (index%2 == 0  ){
						if(line1.charAt(index) =='b'){// store the starting node
							
							start = Laby.getNode(line1name);
						}
						if(line1.charAt(index) =='x'){// store the exit node
							exit = Laby.getNode(line1name);
						}
						if(i+1  <temp.size()){//store the edge between two node
						String type = getEdgeType(line2.charAt(index));
						
						Node u = Laby.getNode(line2name);//linename is node number
						
						Node v = Laby.getNode(line2name+row);
						
						if(type != null){
							Laby.insertEdge(u, v, type);// if there is edge ,add it to the graph
							}
						line2name++;
						}
					}
					//edge for line1  
					else{
						//this is for edge between the nodeline;
						String type = getEdgeType(line1.charAt(index));
						Node u = Laby.getNode(line1name);
						Node v = Laby.getNode(line1name+1);
						if(type != null){
						Laby.insertEdge(u, v, type);
						}
						line1name++;
					}
				
					index++;
				}
				
			}
			//in case the start , exit is at the last line, we have to go through the lsat
			String lastline = temp.get(temp.size()-1);
			
			int index = 0;
			int lastname = number - row;
			while (index < lastline.length()){
				if(index%2 == 0){
					if(lastline.charAt(index) =='b'){//store the starting node
						start = Laby.getNode(lastname);
					}
					if(lastline.charAt(index) =='x'){//store the exit node
						
						exit = Laby.getNode(lastname);
					} 
					lastname ++;
				}
				index++;
			}
		}
	//helper function to indicate the edge type with switch case;
	private String getEdgeType(char c)
	{
		switch(c){
		case 'h':
			return "wall";
		
		case 'H':
			return "thick wall";
		case 'm':
			return "metal wall";
		case 'v':
			return "wall";
		case 'V':
			return "thick wall";
		case 'M': 
			return "metal wall";
		case '-':
			return "corridor";
		case '|':
			return "corridor";
		default:
			return null;
		
	}
	
	}
	public Graph getGraph() throws LabyrinthException{
		if (Laby == null){//if the graph is not defined , throw the exception;
			throw new LabyrinthException();
		}
		//otherwise , return the graph
		return Laby;
	}
	public Iterator solve() throws GraphException{
		
		if (path(start,exit) == false){
			// if there is no path between start and exit return null
			return null;
		}
		//otherwise , return the path iterator.
		Iterator<Node> iter = s.iterator();
		
		
		return iter;
	}
	//helper function for solve()
	private boolean path(Node u, Node v) throws GraphException {
		int default_brickbomb = brickbomb, default_acidbomb = acidbomb;
		u.setMark(true);//Mark first node u;
		s.push(u);//push it into the stack
		if (u == v){// if the u equal to v , it means find the path and return true;
		    return true;
		}
		else{
			//go through each edge of u 
			Iterator<Edge> temp = Laby.incidentEdges(u);
			while (temp.hasNext()){
				Edge e = temp.next();
				Node w = e.secondEndpoint();// w is the other end point of the edge , which is different from u
				if (w.getName() == u.getName()){
					w = e.firstEndpoint();
				}

				// case for corridor
				if(w.getMark() == false) {
				if (e.getType() == "corridor"){					
						if(path(w,v) == true){
							return true;
						}}
				//case for mental wall
				else if(e.getType()=="metal wall"){
					if (acidbomb>0){
						acidbomb = acidbomb-- ;
						
						if(path(w,v) == true){
							return true;}
						else {
							//reset the bomb
							acidbomb = default_acidbomb;
							brickbomb = default_brickbomb;
							
						}
						}}
				//case for thick wall
				else if(e.getType()=="thick wall"){
					if (brickbomb>=2){
						brickbomb = brickbomb-2;
						
						
						
							if(path(w,v) == true){
								return true;}
						else {
							//reset the bomb
							acidbomb = default_acidbomb;
							brickbomb = default_brickbomb;
							
						}
						}}
				//case for the wall
				else if(e.getType()=="wall"){
					if (brickbomb>0){
						brickbomb = brickbomb--;
						if(path(w,v) == true){
							return true;}
						else {
							//reset the bomb
							acidbomb = default_acidbomb;
							brickbomb = default_brickbomb;
						
						}
						}}
			

		}	}
			
			s.pop();
			u.setMark(false);// set the mark to false
			
			return false;
			
	}}
}
	