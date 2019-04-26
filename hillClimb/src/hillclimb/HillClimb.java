/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hillclimb;

import Puzzle.Puzzle;

/**
 *
 * @author amitsparta
 */

class node {

    node ch[];
    double disp;
    int i;
    int j;
    node parent;
    
    private node() {
    }
    
    node(int i,int j) {
        ch = new node[4];
        parent = new node();
        this.i = i;
        this.j = j;
        
    }
}

public class HillClimb {

    char puzzle[][] = Puzzle.puzzle; // Change this to change puzzle
    
    node dest;
    	
    //check if path is available	
    boolean isPath(int i,int j){
        return puzzle[i][j] != '#' && puzzle[i][j] != '*' && puzzle[i][j] != '.' ;
    }
    
    //find heurestic displacement from destination 
    double displacement(node n){
	double dx,dy;
        dx = dest.i - n.i;
        dy = dest.j - n.j;
        return Math.sqrt(dx*dx + dy*dy);
    }
    
    void getDisplacements(node n){
        for(int i=0;i<n.ch.length;i++){
            if(n.ch[i] != null)
                n.ch[i].disp = displacement(n.ch[i]);
        }
    }
    
    void createChild(node n) {
        int k =0;
        
        if(this.isPath(n.i-1,n.j)){
            node n1 = new node(n.i-1, n.j);
            n.ch[k] = n1;
            n.ch[k++].parent = n;
        } else {
            n.ch[k++] = null;
        }
        if(this.isPath(n.i+1,n.j)){
            node n1 = new node(n.i+1, n.j);
            n.ch[k] = n1;
            n.ch[k++].parent = n;
        } else {
            n.ch[k++] = null;
        }
        if(this.isPath(n.i,n.j-1)){
            node n1 = new node(n.i, n.j-1);
            n.ch[k] = n1;
            n.ch[k++].parent = n;
        } else {
            n.ch[k++] = null;
        }
        if(this.isPath(n.i,n.j+1)){
            node n1 = new node(n.i, n.j+1);
            n.ch[k] = n1;
            n.ch[k++].parent = n;
        } else {
            n.ch[k++] = null;
        }
    }
    
    int getNextPath(node n) {
        double min = Double.MAX_VALUE;
        int index = -1;
        for(int i = 0; i < n.ch.length; i++) {
            if(n.ch[i]!= null && n.ch[i].disp <= min) {
                min = n.ch[i].disp;
                
                index = i;
            }
        }
        return index;
    }
    
    //searching
    void searchPath(node n){
        if(n.i == dest.i && n.j == dest.j){
            puzzle[n.i][n.j] = 'W';
            printPuzzle();
            return;
        }
        
        createChild(n);
        
        getDisplacements(n);
        int indexOfMin = getNextPath(n);
        if(indexOfMin != -1 ){
                puzzle[n.ch[indexOfMin].i][n.ch[indexOfMin].j] = '*';
                searchPath(n.ch[indexOfMin]);
                return;
        }
        puzzle[n.i][n.j] = '.';
        searchPath(n.parent);
    }
    
    void printPuzzle() {
        for(int i=0; i< puzzle.length; i++) {
            for(int j = 0;j < puzzle[i].length; j++) {
                System.out.print(puzzle[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    //main
    public static void main(String[] args) {
        HillClimb obj = new HillClimb();
        obj.dest = new node(18,5);
        node n = new node(1,5);
        
        obj.searchPath(n);
        
        
    }
    
}
