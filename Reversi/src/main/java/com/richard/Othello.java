package com.richard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class Othello 
{
	private Logger logger = LoggerFactory.getLogger(Othello.class);
	private static final Set<Character> ROW_INDEX = new HashSet<Character>();
	private static final Set<Character> COL_INDEX = new HashSet<Character>();
	static {
		ROW_INDEX.add('1');
		ROW_INDEX.add('2');
		ROW_INDEX.add('3');
		ROW_INDEX.add('4');
		ROW_INDEX.add('5');
		ROW_INDEX.add('6');
		ROW_INDEX.add('7');
		ROW_INDEX.add('8');
		
		COL_INDEX.add('a');
		COL_INDEX.add('b');
		COL_INDEX.add('c');
		COL_INDEX.add('d');
		COL_INDEX.add('e');
		COL_INDEX.add('f');
		COL_INDEX.add('g');
		COL_INDEX.add('h');
	}
	
	protected final char getblank() { return '-'; }
	protected final char getdark() { return 'X'; }
	protected final char getlight() { return 'O'; }
	protected final boolean isValidDisk(char input) {
		return (input == getdark() || input == getlight());
	}
	protected final char getCounterpartyDisk(char input) {
		if (!isValidDisk(input)) {
			throw new IllegalArgumentException("input disk invalid: " + input);
		}
		return input == getdark() ? getlight() : getdark();
	}
	
	protected final Set<Character> getrowindices() { return ROW_INDEX; }
	protected final Set<Character> getcolindices() { return COL_INDEX; }
    
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
    
    protected List<UnaryOperator<Integer>> getAllOperators() {
    	List<UnaryOperator<Integer>> op = new ArrayList<UnaryOperator<Integer>>();
    	op.add(getUpOperator());
    	op.add(getDownOperator());
    	op.add(getLeftOperator());
    	op.add(getRightOperator());
    	op.add(getUpLeftOperator());
    	op.add(getUpRightOperator());
    	op.add(getDownLeftOperator());
    	op.add(getDownRightOperator());
    	return op;
    }
    
    protected int convertCharsCoordinates2Int(char c1, char c2) {
    	int row = c1 - 48 - 1; //48 is the ascii value of '1', '1' -> 1, zero base -> 0
    	int col = c2 - 97; // 97 is the ascii value of 'a'
    	logger.debug("c1: {} c2: {} row: {} col:{}", c1, c2, row, col);
    	if (row >= 0 && row <= 7 && col >= 0 && col <= 7) {
    		return row * 8 + col;
    	}
    	else {
    		throw new IllegalArgumentException("out of the bound " + row + " " + col);
    	}
    }
    
    /**
     * convert String coordinates to a int
     * zero based position for char[] to
     * retrieve its value
     * 
     * @param input
     * @return
     */
    protected int convertStrCoordinates2Int(String input) {
    	if (StringUtils.isBlank(input)) {
    		throw new IllegalArgumentException("blank string not accepted");
    	}
    	
    	if (input.length() != 2) {
    		throw new IllegalArgumentException("2 char length only");
    	}
    	
    	char c1 = input.charAt(0);
    	char c2 = input.charAt(1);
    	
    	if (getrowindices().contains(c1) && getcolindices().contains(c2)) {
    		return convertCharsCoordinates2Int(c1, c2);
    	} else if (getrowindices().contains(c2) && getcolindices().contains(c1)) {
    		return convertCharsCoordinates2Int(c2, c1);
    	} else {
    		throw new IllegalArgumentException("unknown combination: " + input);
    	}
    	
    }
    
    
    protected List<Integer> getListOfCoordinatesCanTurnDisk(char diskType, char[] chessboard, UnaryOperator<Integer> checkdirectionops, int startingpos) {
    	if (!isValidDisk(diskType))
    		throw new IllegalArgumentException("disk invalid: " + diskType);
    	
    	boolean iscontinue = true;
    	boolean isvalid = false;
    	int pos = startingpos;
    	List<Integer> result = new ArrayList<Integer>();
    	while (iscontinue) {
    		try {
    			int nextpos = checkdirectionops.apply(pos);
    			if (chessboard[nextpos] == getCounterpartyDisk(diskType)) {
    				result.add(nextpos);
    				pos = nextpos;
    			}
    			else if (chessboard[nextpos] == diskType && !result.isEmpty()) {
    				// something absorbed and eventually meet the same disk type
    				iscontinue = false;
    				isvalid = true;
    			}
    			else {
    				iscontinue = false;
    				isvalid = false;
    			}
    			
    		}
    		catch (IllegalArgumentException e) {
    			logger.debug("exception caught", e);
    			iscontinue = false;
    			isvalid = false;
    		}
    	}
    	logger.debug("getListOfCoordinatesCanTurnDisk disk: {} op: {} startpos: {} isvalid: {} result: {}", diskType, checkdirectionops, startingpos, isvalid, result);
    	return isvalid ? result : new ArrayList<Integer>();
    }
    
    protected List<Integer> getListOfCoordinatesCanTurnDisk(char diskType, char[] chessboard, int startpos) {
    	// check disktype
    	if (!isValidDisk(diskType))
    		throw new IllegalArgumentException("not a valid disk");
    	
    	// checking whether startpos is within range and is blank
    	if (startpos < 0 || startpos > 63) {
    		throw new IllegalArgumentException("startpos not in range!" + startpos + " not within 0 to 63 inclusively");
    	}
    	
    	// checking target position is blank or not
    	if (chessboard[startpos] != getblank()) {
    		throw new IllegalArgumentException("this position is not blank!");
    	}
    	
    	List<Integer> result = new ArrayList<Integer>();
    	
    	getAllOperators().forEach(op->{result.addAll(getListOfCoordinatesCanTurnDisk(
    		diskType, chessboard, op, startpos));});
    	
    	logger.debug("result from getListOfCoordinatesCanTurnDisk: {}", result);
    	
    	return result;
    }
    
    
    protected char[] updateChessBoard(char[] chessboard, char diskType, List<Integer> updatepos) {
    	updatepos.forEach(i->{chessboard[i] = diskType; });
    	return chessboard;
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
    
    protected char getPlayerBasedOnRoundsPlayed(int noOfRoundsPlayed) {
    	if (noOfRoundsPlayed < 0)
    		throw new IllegalArgumentException("not accept negative");
    	return noOfRoundsPlayed % 2 == 0 ? getdark() : getlight();
    }
    
    /********** instance variables and public methods ***************/
    private char[] gamechessboard = null;
    private int noofrounds = 0;
    private long sleeptimeperround = 1000;
    public Othello(long sleeptimeperround) {
    	this.gamechessboard = this.initchessboard();
    	this.sleeptimeperround = sleeptimeperround;
    }
    public Othello() {
    	this(0);
    }
    
    public void playGame(String step) {
    	
    }
}
