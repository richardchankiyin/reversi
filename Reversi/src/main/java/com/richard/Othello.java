package com.richard;

import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class Othello 
{
	public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
	
	protected final char getblank() { return '-'; }
	protected final char getdark() { return 'X'; }
	protected final char getlight() { return '0'; }
    
    /**
     * This method aims to initialize
     * the chessboard. The chessboard
     * is an array of char with size 64
     * (8 x 8). For row 1 col a, array[0]
     * (1st) value is array[0]. For row 1
     * col b value is array[1] (2nd). For
     * row 2 col a value is array[8] (9th)
     * @return
     */
    protected char[] initchessboard() {
    	char[] r = new char[64];
    	Map<Integer,Character> map = new HashMap<Integer,Character>();
    	map.put(27, getlight());
    	map.put(28, getdark());
    	map.put(35, getdark());
    	map.put(36, getlight());
    	
    	for (int i = 0; i < 64; i++) {
    		r[i] = map.getOrDefault(i, getblank());
    	}
    	
    	return r;
    }
    
    
}
