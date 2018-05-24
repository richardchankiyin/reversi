package com.richard;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
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
	private static Logger logger = LoggerFactory.getLogger(Othello.class);
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
	
	public final char getBlank() { return '-'; }
	public final char getDark() { return 'X'; }
	public final char getLight() { return 'O'; }
	protected final boolean isValidDisk(char input) {
		return (input == getDark() || input == getLight());
	}
	/**
	 * This method will return the counterparty disk
	 * getdark() -> getlight(); getlight() -> getdark();
	 * Otherwise will throw IllegalArgumentException
	 * 
	 * @param input
	 * @return
	 */
	protected final char getCounterpartyDisk(char input) {
		if (!isValidDisk(input)) {
			throw new IllegalArgumentException("input disk invalid: " + input);
		}
		return input == getDark() ? getLight() : getDark();
	}
	
	protected final Set<Character> getRowIndices() { return ROW_INDEX; }
	protected final Set<Character> getColIndices() { return COL_INDEX; }
    
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
    protected char[] initChessBoard() {
    	char[] r = new char[64];
    	Map<Integer,Character> map = new HashMap<Integer,Character>();
    	map.put(27, getLight());
    	map.put(28, getDark());
    	map.put(35, getDark());
    	map.put(36, getLight());
    	
    	for (int i = 0; i < 64; i++) {
    		r[i] = map.getOrDefault(i, getBlank());
    	}
    	
    	return r;
    }
    
    /**
     * This operator will base on input to determine
     * the new index which is up
     * 
     * @return
     */
    @Deprecated
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
    
    /**
     * This operator will base on input to determine
     * the new index which is down
     * 
     * @return
     */
    @Deprecated
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
    
    /**
     * This operator will base on input to determine
     * the new index which is left
     * 
     * @return
     */
    @Deprecated
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
    
    /**
     * This operator will base on input to determine
     * the new index which is right
     * 
     * @return
     */
    @Deprecated
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
    
    /**
     * This operator will base on input to determine
     * the new index which is up and left
     * 
     * @return
     */
    @Deprecated
    protected UnaryOperator<Integer> getUpLeftOperator() {
    	return i->getLeftOperator().apply(getUpOperator().apply(i));
    }
    
    /**
     * This operator will base on input to determine
     * the new index which is up and right
     * 
     * @return
     */
    @Deprecated
    protected UnaryOperator<Integer> getUpRightOperator() {
    	return i->getRightOperator().apply(getUpOperator().apply(i));
    }
    
    /**
     * This operator will base on input to determine
     * the new index which is down and left
     * 
     * @return
     */
    @Deprecated
    protected UnaryOperator<Integer> getDownLeftOperator() {
    	return i->getLeftOperator().apply(getDownOperator().apply(i));
    }
    
    /**
     * This operator will base on input to determine
     * the new index which is down and right
     * 
     * @return
     */
    @Deprecated
    protected UnaryOperator<Integer> getDownRightOperator() {
    	return i->getRightOperator().apply(getDownOperator().apply(i));
    }
    
    @Deprecated
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
    
    protected List<Displacement> getAllDisplacementOps() {
    	return Arrays.asList(new VerticalBackwardDisplacement(), new VerticalForwardDisplacement()
    	, new HorizontalBackwardDisplacement(), new HorizontalForwardDisplacement()
    	, new DiagonalLeft2RightBackwardDisplacement(), new DiagonalLeft2RightForwardDisplacement()
    	, new DiagonalRight2LeftBackwardDisplacement(), new DiagonalRight2LeftForwardDisplacement());
    }
    
    /**
     * Define the pattern of input String to be 
     * interpreted as passback request
     * 
     * @param input
     * @return
     */
    protected boolean isPassBackRequest(String input) {
    	if (StringUtils.isBlank(input)) return false;
    	return getPassBackStr().equals(input.toLowerCase().trim());
    }
    
    protected String getPassBackStr() { return "pb"; }
    
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
    	
    	if (getRowIndices().contains(c1) && getColIndices().contains(c2)) {
    		return convertCharsCoordinates2Int(c1, c2);
    	} else if (getRowIndices().contains(c2) && getColIndices().contains(c1)) {
    		return convertCharsCoordinates2Int(c2, c1);
    	} else {
    		throw new IllegalArgumentException("unknown combination: " + input);
    	}
    	
    }
    
    /**
     * Return the zero-based position based on row index: c1 (1-8)
     * and col index: c2 (a-h)
     * @param c1
     * @param c2
     * @return
     */
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
     * This method will return a list of counterparty disk positions
     * to be absorbed by input diskType. This method is just checking
     * a single operator point of view
     * 
     * @param diskType
     * @param chessboard
     * @param displacementops
     * @param startingpos
     * @return
     */
    protected List<Integer> getListOfCoordinatesCanTurnDisk(char diskType, char[] chessboard, Displacement ops, int startingpos) {
    	if (!isValidDisk(diskType))
    		throw new IllegalArgumentException("disk invalid: " + diskType);
    	
    	boolean iscontinue = true;
    	boolean isvalid = false;
    	int pos = startingpos;
    	List<Integer> result = new ArrayList<Integer>();
    	while (iscontinue) {
    		try {
    			int nextpos = ops.next(pos);
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
    	logger.debug("getListOfCoordinatesCanTurnDisk disk: {} op: {} startpos: {} isvalid: {} result: {}", diskType, ops, startingpos, isvalid, result);
    	return isvalid ? result : new ArrayList<Integer>();
    }
    
    /**
     * This method will list all the counterparty disk position
     * to be "absorbed" by that particular move. It is a aggregate
     * method to check all possible operators. It is just to
     * include all positions detected of all possible operators
     * and return this list of position
     * 
     * @param diskType
     * @param chessboard
     * @param startpos
     * @return
     */
    protected List<Integer> getListOfCoordinatesCanTurnDisk(char diskType, char[] chessboard, int startpos) {
    	// check disktype
    	if (!isValidDisk(diskType))
    		throw new IllegalArgumentException("not a valid disk");
    	
    	// checking whether startpos is within range and is blank
    	if (startpos < 0 || startpos > 63) {
    		throw new IllegalArgumentException("startpos not in range!" + startpos + " not within 0 to 63 inclusively");
    	}
    	
    	// checking target position is blank or not
    	if (chessboard[startpos] != getBlank()) {
    		throw new IllegalArgumentException("this position is not blank!");
    	}
    	
    	List<Integer> result = new ArrayList<Integer>();
    	
    	getAllDisplacementOps().forEach(op->{result.addAll(getListOfCoordinatesCanTurnDisk(
    		diskType, chessboard, op, startpos));});
    	
    	logger.debug("result from getListOfCoordinatesCanTurnDisk: {}", result);
    	
    	return result;
    }
    
    /**
     * Check a particular position is possible for
     * the diskType to make a move
     * 
     * @param diskType
     * @param chessboard
     * @param startpos
     * @return
     */
    protected boolean isValidMove(char diskType, char[] chessboard, int startpos) {
    	try {
    		List<Integer> r = getListOfCoordinatesCanTurnDisk(diskType, chessboard, startpos);
    		logger.debug("isValidMove diskType: {} startpos: {} result: {}", diskType, startpos, r);
    		return !r.isEmpty();
    	}
    	catch (IllegalArgumentException e) {
    		logger.debug("invalid move", e);
    		return false;
    	}
    }
    
    /**
     * This method will accept a list of positions to be updated
     * in the chessboard based on diskType.
     * 
     * @param chessboard
     * @param diskType
     * @param updatepos
     * @return
     */
    protected char[] updateChessBoard(char[] chessboard, char diskType, List<Integer> updatepos) {
    	updatepos.forEach(i->{chessboard[i] = diskType; });
    	return chessboard;
    }
    
    /**
     * This is about returning a makeup string
     * to render the chessboard.
     * @param input
     * @return
     */
    protected String getChessBoardStr(char[] input) {
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
    
    /**
     * Base on the noofround played to determine upcoming
     * which diskType can move. 0,2,4.... for getdark()
     * 1,3,5.... for getlight()
     * 
     * @param noOfRoundsPlayed
     * @return
     */
    protected char getPlayerBasedOnRoundsPlayed(int noOfRoundsPlayed) {
    	if (noOfRoundsPlayed < 0)
    		throw new IllegalArgumentException("not accept negative");
    	return noOfRoundsPlayed % 2 == 0 ? getDark() : getLight();
    }
    
    /**
     * This method go through the chessboard and see
     * any positions can accept the diskType to make
     * a move. If there is 1 possible move found, 
     * searching will stop and return the result as
     * true. If no invalid move can be found even 
     * finish going through the chessboard, false 
     * will be returned. 
     * 
     * @param diskType
     * @param chessboard
     * @return
     */
    protected boolean canValidMoveBeFound(char diskType, char[] chessboard) {
    	boolean isFound = false;
    	boolean isContinue = true;
    	int pos = -1;
    	for (int i = 0; i < 64 && isContinue; i++) {    		
    		if (isValidMove(diskType, chessboard, i)) {
    			isContinue = false;
    			isFound = true;
    			pos = i;
    		}
    	}
    	
    	logger.debug("canValidMoveBeFound disk: {} valid pos: {}", diskType, pos);
    	
    	return isFound;
    }
    
    /**
     * Checking whether the current chessboard status
     * is the game end status. Based on the no of rounds
     * played, we can determine the diskType (dark/light)
     * , then we can see any valid moves found for that 
     * disk type. If there is no, we will further check
     * the counterparty disk has any valid move. If both
     * cannot move, we will claim this is an end game
     * 
     * @param chessboard
     * @param currentRound
     * @return
     */
    protected boolean isEndGameDetected(char[] chessboard, int currentRound) {
    	char diskType = getPlayerBasedOnRoundsPlayed(currentRound);
    	return !canValidMoveBeFound(diskType, chessboard) && !canValidMoveBeFound(getCounterpartyDisk(diskType), chessboard);
    }
    
    /**
     * Get disk counts on the chessboard
     * Two values will be returned in an
     * int[]. int[0] represents dark count.
     * int[1] represents light count.
     * 
     * @param chessboard
     * @return
     */
    protected int[] getDiskCounts(char[] chessboard) {
    	int darkc = 0;
    	int lightc = 0;
    	for (int i = 0; i < 64; i++) {
    		if (chessboard[i] == getDark()) darkc++;
    		if (chessboard[i] == getLight()) lightc++;
    	}
    	
    	return new int[] {darkc, lightc};
    	
    }
    
    /********** instance variables and public/non-mutable private methods ***************/
    private char[] gamechessboard = null;
    private int noofrounds = 0;
    private long sleeptimeperround = 1000;
    private List<String> stepsGoneThrough = new ArrayList<String>();

    private boolean isEndGame = false;
    
    public Othello(long sleeptimeperround) {
    	this.gamechessboard = this.initChessBoard();
    	this.sleeptimeperround = sleeptimeperround;
    }
    public Othello() {
    	this(0);
    }
    
    public boolean isEndGame() { return isEndGame; }
    
    public int getNoOfRoundsPlayed() { return noofrounds; }
    
    public char getPlayer() {
    	return getPlayerBasedOnRoundsPlayed(getNoOfRoundsPlayed());
    }

    
    public int[] getDiskCounts() {
    	return this.getDiskCounts(gamechessboard);
    }
    
    public List<String> getStepsGoneThrough() { 
    	// return a immutable list rather
    	// than a list reference to 
    	// external caller
    	return Arrays.asList(
    			stepsGoneThrough.toArray(
    					new String[stepsGoneThrough.size()])); 
    }
    
    public String getChessBoardStr() {
    	return this.getChessBoardStr(gamechessboard);
    }
    
    /**
     * There is a playing single round game
     * @param step
     */
    public void playGameSingleRound(String step) {
    	prePlayGame();
    	playGameCore(step);
    	postPlayGame();
    }
    
    private void prePlayGame() {
    	
    }
    
    private void postPlayGame() {
    	boolean isCurrentEndGame = isEndGameDetected(gamechessboard, noofrounds);
    	
    	if (isCurrentEndGame)
    		isEndGame = isCurrentEndGame;
    	
    	if (isEndGame) throw new GameIsOverException();
    	
    	try {
    		Thread.sleep(sleeptimeperround);
    	}
    	catch (InterruptedException e) {
    		logger.warn("sleep interrupted",e);
    	}
    }

    private void playGameCore(String step) {
    	logger.info("Step: {} is progress...", step);
    	if (isEndGame()) {
    		throw new GameIsOverException();
    	}
    	
    	if (this.isPassBackRequest(step)) {
    		// add 1 more round
    		noofrounds++;
    		 // as pass pack is a sign of no valid move
    		stepsGoneThrough.add(step);
    		
    	} else {
    		try {
    			// get the startpos request
    			int startpos = convertStrCoordinates2Int(step);
    			// get disk type
    			char diskType = getPlayerBasedOnRoundsPlayed(noofrounds);
    			
    			// get affecteddiskpos
    			List<Integer> affecteddiskpos= getListOfCoordinatesCanTurnDisk(diskType, gamechessboard, startpos);
    			
    			logger.info("noofroundplayed: {} diskType: {} step: {} startpos: {} affecteddiskpos: {}", noofrounds, diskType, step, startpos, affecteddiskpos);
    			
    			// check this move has affected disk position
    			if (affecteddiskpos.isEmpty()) {
    				throw new IllegalArgumentException("this move involves no counterparty disk pos affected!");
    			}
    			
    			// for updating chessboard, include the position to be moved
    			affecteddiskpos.add(startpos);
    			// update the chessboard
    			this.updateChessBoard(gamechessboard, diskType, affecteddiskpos);
    			
    			// update rounds and step gone thru
    			noofrounds++;
        		stepsGoneThrough.add(step);
        		
    		}
    		catch (IllegalArgumentException e) {
    			//as this move is invalid, we did not
    			//increment noofrounds and record
    			//step
    			throw new InvalidMoveException(e);
    		}
    	}
    }

    public void playGame(String inputSteps) {
    	this.playGame(inputSteps, System.out, true);
    }
    
    /**
     * The entry to play games in
     * batch mode
     * @param inputSteps
     * @param out
     * @parem isPrintRequired
     */
    public void playGame(String inputSteps, PrintStream out, boolean isPrintRequired ) {
    	
    	try {
	    	Arrays.asList(inputSteps.split(",")).forEach(
	    		step -> {
	    			String step2 = step.trim().toLowerCase();
	    			this.playGameSingleRound(step2);
	    			if (isPrintRequired) {
		    			out.printf("Step: %s\n", step);
		    			out.printf("--------------\n%s\n", this.getChessBoardStr());
	    			}
	    		}
	    	);
    	} catch (RuntimeException re) {
    		logger.error("issue found during running through the steps...", re);
    		throw re;
    	} 
    }
}
