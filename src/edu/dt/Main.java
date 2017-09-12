package edu.dt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.text.html.HTMLDocument.Iterator;

/**
 * Created by tphadke on 8/29/17.
 */
@SuppressWarnings("unused")
public class Main {
	
    public static Map<Processor, List<Processor>> graph ;
    public static Processor root;
    public static Processor p1;
    public static Processor p2;
    public static Processor p3;
    public static Processor p4;
    public static Processor p5;
    public static Processor p6;
    
    public  Main(){
    		init();
    }

    public void init(){
    		graph = new HashMap<Processor, List<Processor>>();
        p1 = new Processor();
        p2 = new Processor();
        p3 = new Processor();
        p4 = new Processor();
        p5 = new Processor();
        p6 = new Processor();
        ArrayList<Processor> p1neighbors = new ArrayList<Processor>();
        p1neighbors.add(p2);
        p1neighbors.add(p3);
        ArrayList<Processor> p2neighbors = new ArrayList<Processor>();
        p2neighbors.add(p1);
        p2neighbors.add(p4);
        p2neighbors.add(p5);
        ArrayList<Processor> p3neighbors = new ArrayList<Processor>();
        p3neighbors.add(p4);
        p3neighbors.add(p5);
        ArrayList<Processor> p4neighbors = new ArrayList<Processor>();
        p4neighbors.add(p5);
        p4neighbors.add(p6);
        ArrayList<Processor> p5neighbors = new ArrayList<Processor>();
        p5neighbors.add(p4);
        p5neighbors.add(p6);
        ArrayList<Processor> p6neighbors = new ArrayList<Processor>();
        p6neighbors.add(p4);
        p6neighbors.add(p5);
        graph.put(p1, p1neighbors);
        graph.put(p2, p2neighbors);
        graph.put(p3, p3neighbors);
        graph.put(p4, p4neighbors);
        graph.put(p5, p5neighbors);
        graph.put(p6, p6neighbors);
        p1.unexplored.add(p2);
        p1.unexplored.add(p3);
        p2.unexplored.add(p1);
        p2.unexplored.add(p4);
        p2.unexplored.add(p5);
        p3.unexplored.add(p4);
        p3.unexplored.add(p5);
        p4.unexplored.add(p5);
        p4.unexplored.add(p6);
        p5.unexplored.add(p6);
        p5.unexplored.add(p4);
        p6.unexplored.add(p4);
        p6.unexplored.add(p5);
    }

    public static void main ( String args[]){
        Main m = new Main();
        System.out.println("working");
        //TODO: Choose a processor as a Root
        Main.root = p1;
        //TODO: Send an initial message Message.M to this processor.
        Main.root.sendMessgeToMyBuffer(Message.M);
        for(Processor p : graph.keySet() ) {
        		CopyOnWriteArrayList<Processor> children = (CopyOnWriteArrayList<Processor>) p.children;
        		System.out.println("Processor "+p.id +"'s Children: ");
        		for (Processor child: children) {
        			System.out.print("P#"+ child.id + " ");
        		}
        		System.out.println();
        }
        for(Processor p : graph.keySet()) {
        		if(p.parent!=null) {
        			System.out.println("Processor "+ p.id +"'s parent: " + "Processor #" + p.parent.id);
        		}
        }
    }

}
