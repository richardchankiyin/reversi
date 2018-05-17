package com.richard;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

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
	protected final char getlight() { return 'O'; }
    
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
    
    protected UnaryOperator<Integer> getUpOperator() {
    	return i-> {
    		if (i >= 8 && i <= 63) {
    			return i - 8;
    		}
    		else {
    			throw new IllegalArgumentException("not allowed");
    		}
    	};
    }
    
    protected UnaryOperator<Integer> getDownOperator() {
    	return i-> {
    		if (i >= 0 && i <= 55) {
    			return i + 8;
    		}
    		else {
    			throw new IllegalArgumentException("not allowed");
    		}
    	};
    }
    
    protected UnaryOperator<Integer> getLeftOperator() {
    	return i-> {
    		if (i >= 0 && i <= 63) {
    			if (i % 8 == 0) {
    				throw new IllegalArgumentException("left edge");
    			} else {
    				return i - 1;
    			}
    		} else {
    			throw new IllegalArgumentException("not allowed");
    		}
    	};
    }
    
    protected UnaryOperator<Integer> getRightOperator() {
    	return i-> {
    		if (i >= 0 && i <= 63) {
    			if (i % 8 == 7) {
    				throw new IllegalArgumentException("right edge");
    			} else {
    				return i + 1;
    			}
    		} else {
    			throw new IllegalArgumentException("not allowed");
    		}
    	};
    }
    
    protected UnaryOperator<Integer> getUpLeftOperator() {
    	return i->getLeftOperator().apply(getUpOperator().apply(i));
    }
    
    protected UnaryOperator<Integer> getUpRightOperator() {
    	return i->getRightOperator().apply(getUpOperator().apply(i));
    }
    
    protected UnaryOperator<Integer> getDownLeftOperator() {
    	return i->getLeftOperator().apply(getDownOperator().apply(i));
    }
    
    protected UnaryOperator<Integer> getDownRightOperator() {
    	return i->getRightOperator().apply(getDownOperator().apply(i));
    }
    
    protected String getchessboardStr(char[] input) {
    	if (input == null) {
    		throw new IllegalArgumentException("chessboard null!");
    	}
    	
    	if (input.length != 64) {
    		throw new IllegalArgumentException("chessboard length <> 64!");
    	}
    	
    	StringBuilder str = new StringBuilder();
    	for (int i = 1; i <= 8; i++) {
    		str.append(i).append(' ');
    		for (int j = 0; j <= 7; j++) {
    			str.append(input[(i - 1) * 8 + j]);
    		}
    		str.append('\n');
    	}
    	
    	// print foot row
    	str.append("  abcdefgh\n");
    	return str.toString();
    }
    
}
