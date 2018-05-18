package com.richard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OthelloTest {
	private Logger logger = LoggerFactory.getLogger(OthelloTest.class);

	private Othello obj = null;

	@Before
	public void setup() {
		obj = new Othello();
	}

	@Test
	public void testIsValidDisk() {
		assertTrue(obj.isValidDisk(obj.getdark()));
		assertTrue(obj.isValidDisk(obj.getlight()));
		assertFalse(obj.isValidDisk(obj.getblank()));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetCounterpartyDiskInvalid() {
		obj.getCounterpartyDisk(obj.getblank());
	}
	
	@Test
	public void testGetCounterpartyDiskValid() {
		assertEquals(obj.getlight(),obj.getCounterpartyDisk(obj.getdark()));
		assertEquals(obj.getdark(),obj.getCounterpartyDisk(obj.getlight()));
	}
	
	@Test
	public void testInitChessBoard() {
		char[] r = obj.initchessboard();
		assertEquals(64, r.length);
		for (int i = 0; i < 64; i++) {
			if (i == 27)
				assertEquals('O', r[i]);
			else if (i == 28)
				assertEquals('X', r[i]);
			else if (i == 35)
				assertEquals('X', r[i]);
			else if (i == 36)
				assertEquals('O', r[i]);
			else
				assertEquals('-', r[i]);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetchessboardStrNotValid1() {
		obj.getchessboardStr(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetchessboardStrNotValid2() {
		obj.getchessboardStr(new char[2]);
	}

	@Test
	public void testGetchessboardStrInit() {
		/*
		 * 1 -------- 2 -------- 3 -------- 4 ---OX--- 5 ---XO--- 6 -------- 7
		 * -------- 8 -------- abcdefgh
		 */
		StringBuilder str = new StringBuilder();
		str.append("1 --------").append('\n');
		str.append("2 --------").append('\n');
		str.append("3 --------").append('\n');
		str.append("4 ---OX---").append('\n');
		str.append("5 ---XO---").append('\n');
		str.append("6 --------").append('\n');
		str.append("7 --------").append('\n');
		str.append("8 --------").append('\n');
		str.append("  abcdefgh").append('\n');
		String expect = str.toString();
		String actual = obj.getchessboardStr(obj.initchessboard());
		logger.debug("expect init chessboard str:\n{}", expect);

		logger.debug("actual init chessboard str:\n{}", actual);
		assertEquals(expect, actual);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpOperatorResultInvalid1() {
		obj.getUpOperator().apply(7);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpOperatorResultInvalid2() {
		obj.getUpOperator().apply(65);
	}

	@Test
	public void testUpOperatorResultValid() {
		assertTrue(3 == obj.getUpOperator().apply(11));
		assertTrue(20 == obj.getUpOperator().apply(28));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDownOperatorResultInvalid1() {
		obj.getDownOperator().apply(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDownOperatorResultInvalid2() {
		obj.getDownOperator().apply(60);
	}

	@Test
	public void testDownOperatorResultValid() {
		assertTrue(11 == obj.getDownOperator().apply(3));
		assertTrue(28 == obj.getDownOperator().apply(20));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLeftOperatorResultInvalid1() {
		obj.getLeftOperator().apply(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLeftOperatorResultInvalid2() {
		obj.getLeftOperator().apply(65);
	}

	@Test
	public void testLeftOperatorResultInvalid3() {
		int exceptioncaught = 0;
		for (int i = 0; i < 8; i++) {
			try {
				obj.getLeftOperator().apply(i * 8);
			} catch (Exception e) {
				exceptioncaught++;
			}
		}

		assertEquals(8, exceptioncaught);
	}
	
	@Test
	public void testLeftOperatorResultValid() {
		assertTrue(0 == obj.getLeftOperator().apply(1));
		assertTrue(10 == obj.getLeftOperator().apply(11));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRightOperatorResultInvalid1() {
		obj.getRightOperator().apply(-1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRightOperatorResultInvalid2() {
		obj.getRightOperator().apply(68);
	}
	
	@Test
	public void testRightOperatorResultInvalid3() {
		int exceptioncaught = 0;
		for (int i = 0; i < 8; i++) {
			try {
				obj.getRightOperator().apply(7 + i * 8);
			} catch (Exception e) {
				exceptioncaught++;
			}
		}

		assertEquals(8, exceptioncaught);
	}
	
	@Test
	public void testRightOperatorResultValid() {
		assertTrue(1 == obj.getRightOperator().apply(0));
		assertTrue(12 == obj.getRightOperator().apply(11));
	}
	
	@Test
	public void testUpLeftOperatorResultsInvalid() {
		int exceptioncaught = 0;
		try {
			obj.getUpLeftOperator().apply(5);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		
		try {
			obj.getUpLeftOperator().apply(8);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		
		try {
			obj.getUpLeftOperator().apply(68);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		
		assertTrue(3 == exceptioncaught);
	}
	
	@Test
	public void testUpLeftOperatorResultsvalid() {
		assertTrue(11 == obj.getUpLeftOperator().apply(20));
		assertTrue(46 == obj.getUpLeftOperator().apply(55));
	}
	
	@Test
	public void testDownLeftOperatorResultsInvalid() {
		int exceptioncaught = 0;
		try {
			obj.getDownLeftOperator().apply(57);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		
		try {
			obj.getDownLeftOperator().apply(-1);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		
		try {
			obj.getDownLeftOperator().apply(16);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		
		assertTrue(3 == exceptioncaught);
	}
	
	@Test
	public void testDownLeftOperatorResultsvalid() {
		assertTrue(27 == obj.getDownLeftOperator().apply(20));
		assertTrue(40 == obj.getDownLeftOperator().apply(33));
	}
	
	@Test
	public void testDownRightOperatorResultsInvalid() {
		int exceptioncaught = 0;
		try {
			obj.getDownRightOperator().apply(-1);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		
		try {
			obj.getDownRightOperator().apply(60);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		
		try {
			obj.getDownRightOperator().apply(23);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		
		assertTrue(3 == exceptioncaught);
	}
	
	@Test
	public void testDownRightOperatorResultsvalid() {
		assertTrue(29 == obj.getDownRightOperator().apply(20));
		assertTrue(42 == obj.getDownRightOperator().apply(33));
	}
	
	@Test
	public void testIsPassBack() {
		assertTrue(obj.isPassBackRequest(""));
		assertTrue(obj.isPassBackRequest(" "));
		assertTrue(obj.isPassBackRequest(null));
		assertFalse(obj.isPassBackRequest("2"));
		assertFalse(obj.isPassBackRequest("2b"));
		assertFalse(obj.isPassBackRequest("b2"));
		assertFalse(obj.isPassBackRequest("nnnnn"));
	}
	
	@Test
	public void testConvertStrCoordinates2IntInvalid1() {
		//blank string
		int exceptioncaught = 0;
		try {
			obj.convertStrCoordinates2Int(null);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		//blank string
		try {
			obj.convertStrCoordinates2Int("");
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		//too long
		try {
			obj.convertStrCoordinates2Int("12a");
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		//too short
		try {
			obj.convertStrCoordinates2Int("b");
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		assertTrue(4 == exceptioncaught);
	}
	
	@Test
	public void testConvertStrCoordinates2IntInvalid2() {
		int exceptioncaught = 0;
		// same type
		try {
			obj.convertStrCoordinates2Int("ab");
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		try {
			obj.convertStrCoordinates2Int("13");
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		assertTrue(2 == exceptioncaught);
	}
	
	@Test
	public void testConvertStrCoordinates2IntInvalid3() {
		int exceptioncaught = 0;
		// out of bound
		try {
			obj.convertStrCoordinates2Int("a9");
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		try {
			obj.convertStrCoordinates2Int("z2");
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		try {
			obj.convertStrCoordinates2Int("z9");
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		assertTrue(3 == exceptioncaught);
	}
	
	@Test
	public void testConvertStrCoordinates2IntValid() {
		assertTrue(16==obj.convertStrCoordinates2Int("a3"));
		assertTrue(16==obj.convertStrCoordinates2Int("3a"));
		
		assertTrue(33==obj.convertStrCoordinates2Int("b5"));
		assertTrue(33==obj.convertStrCoordinates2Int("5b"));
	}
	
	@Test
	public void testGetListOfCoordinatesCanTurnDiskFound() {
		char[] chessboard = obj.initchessboard();
		List<Integer> result1 = obj.getListOfCoordinatesCanTurnDisk(obj.getdark(), chessboard, obj.getRightOperator(), 26);
		logger.debug("result1:{}", result1);
		assertEquals(1, result1.size());
		assertTrue(27==result1.get(0));
		
		List<Integer> result2 = obj.getListOfCoordinatesCanTurnDisk(obj.getdark(), chessboard, obj.getDownOperator(), 19);
		logger.debug("result2:{}", result2);
		assertEquals(1, result2.size());
		assertTrue(27==result2.get(0));
		
		List<Integer> result3 = obj.getListOfCoordinatesCanTurnDisk(obj.getdark(), chessboard, obj.getUpOperator(), 44);
		logger.debug("result3:{}", result3);
		assertEquals(1, result3.size());
		assertTrue(36==result3.get(0));
		
		List<Integer> result4 = obj.getListOfCoordinatesCanTurnDisk(obj.getdark(), chessboard, obj.getLeftOperator(), 37);
		logger.debug("result4:{}", result4);
		assertEquals(1, result4.size());
		assertTrue(36==result4.get(0));
	}
	
	@Test
	public void testGetListOfCoordinatesCanTurnDiskNotFound() {
		char[] chessboard = obj.initchessboard();
		List<Integer> result1 = obj.getListOfCoordinatesCanTurnDisk(obj.getdark(), chessboard, obj.getRightOperator(), 18);
		logger.debug("result1:{}", result1);
		assertEquals(0, result1.size());
		
		List<Integer> result2 = obj.getListOfCoordinatesCanTurnDisk(obj.getdark(), chessboard, obj.getRightOperator(), 0);
		logger.debug("result2:{}", result2);
		assertEquals(0, result2.size());
		
		// hitting edge
		List<Integer> result3 = obj.getListOfCoordinatesCanTurnDisk(obj.getdark(), chessboard, obj.getLeftOperator(), 0);
		logger.debug("result3:{}", result3);
		assertEquals(0, result3.size());
		
		// manually update chessboard through altering char values (not for actual game playing disk turning
		chessboard[0] = obj.getlight();
		if (logger.isDebugEnabled())
			logger.debug("after manually altering chessboard:\n{}", obj.getchessboardStr(chessboard));
		
		// caught 1 light but eventually hit edge
		List<Integer> result4 = obj.getListOfCoordinatesCanTurnDisk(obj.getdark(), chessboard, obj.getLeftOperator(), 1);
		logger.debug("result4:{}", result4);
		assertEquals(0, result4.size());
	}
	
	@Test
	public void getListOfCoordinatesCanTurnDiskNotValid() {
		char[] chessboard = obj.initchessboard();
		int exceptioncaught = 0;
		try {
			obj.getListOfCoordinatesCanTurnDisk(obj.getdark(), chessboard, -1);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		try {
			obj.getListOfCoordinatesCanTurnDisk(obj.getdark(), chessboard, 64);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		try {
			obj.getListOfCoordinatesCanTurnDisk(obj.getdark(), chessboard, 27);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		
		try {
			obj.getListOfCoordinatesCanTurnDisk(obj.getblank(), chessboard, 26);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		
		assertTrue(4 == exceptioncaught);
	}
	
	@Test
	public void getListOfCoordinatesCanTurnDiskValid() {
		char[] chessboard = obj.initchessboard();
		List<Integer> result1 = obj.getListOfCoordinatesCanTurnDisk(obj.getdark(), chessboard, 26);
		logger.debug("getListOfCoordinatesCanTurnDiskValid result1:{}", result1);
		assertEquals(1, result1.size());
		assertTrue(27==result1.get(0));
		
		List<Integer> result2 = obj.getListOfCoordinatesCanTurnDisk(obj.getdark(), chessboard, 18);
		logger.debug("getListOfCoordinatesCanTurnDiskValid result2:{}", result2);
		assertEquals(0, result2.size());
	}
	
	@Test
	public void testUpdateChessBoard() {
		char[] chessboard = obj.initchessboard();
		List<Integer> updatepos = Arrays.asList(0,1,2);
		char[] newchessboard = obj.updateChessBoard(chessboard, obj.getlight(), updatepos);
		StringBuilder str = new StringBuilder();
		str.append("1 OOO-----").append('\n');
		str.append("2 --------").append('\n');
		str.append("3 --------").append('\n');
		str.append("4 ---OX---").append('\n');
		str.append("5 ---XO---").append('\n');
		str.append("6 --------").append('\n');
		str.append("7 --------").append('\n');
		str.append("8 --------").append('\n');
		str.append("  abcdefgh").append('\n');
		String expect = str.toString();
		String actual = obj.getchessboardStr(newchessboard);
		logger.debug("actual:\n{}", actual);
		assertEquals(expect,actual);
	}
	
	@Test
	public void testGetPlayerBasedOnRoundsPlayed() {
		int exceptioncaught = 0;
		try {
			obj.getPlayerBasedOnRoundsPlayed(-1);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		assertTrue(1 == exceptioncaught);
		assertEquals(obj.getdark(), obj.getPlayerBasedOnRoundsPlayed(0));
		assertEquals(obj.getlight(), obj.getPlayerBasedOnRoundsPlayed(1));
		assertEquals(obj.getdark(), obj.getPlayerBasedOnRoundsPlayed(2));
		assertEquals(obj.getlight(), obj.getPlayerBasedOnRoundsPlayed(3));
	}
	
	@Test
	public void testCanValidMoveBeFound() {
		char[] chessboard = obj.initchessboard();
		
		// init chess
		assertTrue(obj.canValidMoveBeFound(obj.getdark(), chessboard));
		assertTrue(obj.canValidMoveBeFound(obj.getlight(), chessboard));
		
		char[] chessboard2 = new char[64];
		for (int i = 0; i < 64; i++) {
			chessboard2[i] = obj.getdark();
		}
		
		assertFalse(obj.canValidMoveBeFound(obj.getdark(), chessboard2));
		assertFalse(obj.canValidMoveBeFound(obj.getlight(), chessboard2));
	}
	
	@Test
	public void testIsEndGameDetected() {
		char[] chessboard = new char[64];
		for (int i = 0; i < 64; i++) {
			chessboard[i] = obj.getdark();
		}
		
		boolean[] result1 = obj.isEndGameDetected(chessboard, 0, 1);
		
		assertTrue(result1[0]);
		assertTrue(result1[1]);

		boolean[] result2 = obj.isEndGameDetected(chessboard, 0, 3);
		
		assertFalse(result2[0]);
		assertTrue(result2[1]);
	}
	
	@Test(expected=InvalidMoveException.class)
	public void testPlayGameSingleStepInvalidMove1() {
		Othello game = new Othello();
		try {
			// left upper corner far away from starting
			game.playGameSingleRound("1a");
		} catch (InvalidMoveException ie) {
			assertTrue(0==game.getNoOfRoundsPlayed());
			assertTrue(game.getStepsGoneThrough().isEmpty());
			throw ie;
		}
		
	}
	
	@Test(expected=InvalidMoveException.class)
	public void testPlayGameSingleStepInvalidMove2() {
		Othello game = new Othello();
		try {
			// attempt to move to a non-blank position
			game.playGameSingleRound("4d");
		} catch (InvalidMoveException ie) {
			assertTrue(0==game.getNoOfRoundsPlayed());
			assertTrue(game.getStepsGoneThrough().isEmpty());
			throw ie;
		}
	}
	
	@Test
	public void testPlayGameSingleStepValid1() {
		Othello game = new Othello();
		game.playGameSingleRound("3d");
		assertTrue(1==game.getNoOfRoundsPlayed());
		assertTrue(game.getLastRoundDetectedInvalid() < 0);
		assertTrue(1==game.getStepsGoneThrough().size());
		
		StringBuilder str = new StringBuilder();
		str.append("1 --------").append('\n');
		str.append("2 --------").append('\n');
		str.append("3 ---X----").append('\n');
		str.append("4 ---XX---").append('\n');
		str.append("5 ---XO---").append('\n');
		str.append("6 --------").append('\n');
		str.append("7 --------").append('\n');
		str.append("8 --------").append('\n');
		str.append("  abcdefgh").append('\n');
		
		String expect = str.toString();
		String actual = game.getChessBoardStr();
		
		logger.debug("testPlayGameSingleStepValid1 actual:\n{}", actual);
		assertEquals(expect,actual);
		
	}
	
	@Test
	public void testPlayGameSingleStepValid2() {
		Othello game = new Othello();
		game.playGameSingleRound("4c");
		assertTrue(1==game.getNoOfRoundsPlayed());
		assertTrue(game.getLastRoundDetectedInvalid() < 0);
		assertTrue(1==game.getStepsGoneThrough().size());
		
		StringBuilder str = new StringBuilder();
		str.append("1 --------").append('\n');
		str.append("2 --------").append('\n');
		str.append("3 --------").append('\n');
		str.append("4 --XXX---").append('\n');
		str.append("5 ---XO---").append('\n');
		str.append("6 --------").append('\n');
		str.append("7 --------").append('\n');
		str.append("8 --------").append('\n');
		str.append("  abcdefgh").append('\n');
		
		String expect = str.toString();
		String actual = game.getChessBoardStr();
		
		logger.debug("testPlayGameSingleStepValid2 actual:\n{}", actual);
		assertEquals(expect,actual);
	}
}
