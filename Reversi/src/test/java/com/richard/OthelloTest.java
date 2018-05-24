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
	private static Logger logger = LoggerFactory.getLogger(OthelloTest.class);

	private Othello obj = null;

	@Before
	public void setup() {
		obj = new Othello();
	}

	@Test
	public void testIsValidDisk() {
		assertTrue(obj.isValidDisk(obj.getDark()));
		assertTrue(obj.isValidDisk(obj.getLight()));
		assertFalse(obj.isValidDisk(obj.getBlank()));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetCounterpartyDiskInvalid() {
		obj.getCounterpartyDisk(obj.getBlank());
	}
	
	@Test
	public void testGetCounterpartyDiskValid() {
		assertEquals(obj.getLight(),obj.getCounterpartyDisk(obj.getDark()));
		assertEquals(obj.getDark(),obj.getCounterpartyDisk(obj.getLight()));
	}
	
	@Test
	public void testInitChessBoard() {
		char[] r = obj.initChessBoard();
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
		obj.getChessBoardStr(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetchessboardStrNotValid2() {
		obj.getChessBoardStr(new char[2]);
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
		String actual = obj.getChessBoardStr(obj.initChessBoard());
		logger.debug("expect init chessboard str:\n{}", expect);

		logger.debug("actual init chessboard str:\n{}", actual);
		assertEquals(expect, actual);
	}

	
	@Test(expected = IllegalArgumentException.class)
	public void testVerticalBackwardDisplacementInvalid1() {
		Displacement d = new VerticalBackwardDisplacement();
		d.next(7);
	}

	
	@Test(expected = IllegalArgumentException.class)
	public void testVerticalBackwardDisplacementInvalid2() {
		Displacement d = new VerticalBackwardDisplacement();
		d.next(65);
	}

	
	@Test
	public void testVerticalBackwardDisplacementValid() {
		Displacement d = new VerticalBackwardDisplacement();
		assertTrue(3 == d.next(11));
		assertTrue(20 == d.next(28));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testVerticalForwardDisplacementInvalid1() {
		Displacement d = new VerticalForwardDisplacement();
		d.next(-1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testVerticalForwardDisplacementInvalid2() {
		Displacement d = new VerticalForwardDisplacement();
		d.next(60);
	}
	
	@Test
	public void testVerticalForwardDisplacementValid() {
		Displacement d = new VerticalForwardDisplacement();
		assertTrue(11 == d.next(3));
		assertTrue(28 == d.next(20));
	}

	
	@Test(expected = IllegalArgumentException.class)
	public void testHorizontalDisplacementBackwardInvalid1() {
		Displacement d = new HorizontalBackwardDisplacement();
		d.next(-1);
	}

	
	@Test(expected = IllegalArgumentException.class)
	public void testHorizontalDisplacementBackwardInvalid2() {
		Displacement d = new HorizontalBackwardDisplacement();
		d.next(65);
	}

	
	@Test
	public void testHorizontalDisplacementBackwardInvalid3() {
		Displacement d = new HorizontalBackwardDisplacement();
		int exceptioncaught = 0;
		for (int i = 0; i < 8; i++) {
			try {
				d.next(i * 8);
			} catch (Exception e) {
				exceptioncaught++;
			}
		}

		assertEquals(8, exceptioncaught);
	}

	
	@Test
	public void testHorizontalDisplacementBackwardValid() {
		Displacement d = new HorizontalBackwardDisplacement();
		assertTrue(0 == d.next(1));
		assertTrue(10 == d.next(11));
	}

	
	@Test(expected = IllegalArgumentException.class)
	public void testHorizontalForwardDisplacementInvalid1() {
		Displacement d = new HorizontalForwardDisplacement();
		d.next(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHorizontalForwardDisplacementInvalid2() {
		Displacement d = new HorizontalForwardDisplacement();
		d.next(68);
	}

	
	@Test
	public void testHorizontalForwardDisplacementInvalid3() {
		Displacement d = new HorizontalForwardDisplacement();
		int exceptioncaught = 0;
		for (int i = 0; i < 8; i++) {
			try {
				d.next(7 + i * 8);
			} catch (Exception e) {
				exceptioncaught++;
			}
		}

		assertEquals(8, exceptioncaught);
	}

	
	@Test
	public void testHorizontalForwardDisplacementValid() {
		Displacement d = new HorizontalForwardDisplacement();
		assertTrue(1 == d.next(0));
		assertTrue(12 == d.next(11));
	}

	
	@Test
	public void testDiagonalLeft2RightBackwardDisplacementResultsInvalid() {
		Displacement d = new DiagonalLeft2RightBackwardDisplacement();
		int exceptioncaught = 0;
		try {
			d.next(5);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		
		try {
			d.next(8);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		
		try {
			d.next(68);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		
		assertTrue(3 == exceptioncaught);
		
	}

	
	@Test
	public void testDiagonalLeft2RightBackwardDisplacementResultsValid() {
		Displacement d = new DiagonalLeft2RightBackwardDisplacement();
		assertTrue(11 == d.next(20));
		assertTrue(46 == d.next(55));
	}
	
	@Test
	public void testDiagonalRight2LeftForwardDisplacementInvalid() {
		Displacement d = new DiagonalRight2LeftForwardDisplacement();
		int exceptioncaught = 0;
		try {
			d.next(57);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		
		try {
			d.next(-1);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		
		try {
			d.next(16);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		
		assertTrue(3 == exceptioncaught);
	}
	
	@Test
	public void testDiagonalRight2LeftForwardDisplacementValid() {
		Displacement d = new DiagonalRight2LeftForwardDisplacement();
		assertTrue(27 == d.next(20));
		assertTrue(40 == d.next(33));
	}
	
	@Test
	public void testDiagonalRight2LeftBackwardDisplacementInvalid() {
		Displacement d = new DiagonalRight2LeftBackwardDisplacement();
		int exceptioncaught = 0;
		try {
			d.next(-1);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		
		try {
			d.next(7);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		
		try {
			d.next(15);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		
		assertTrue(3 == exceptioncaught);
	}

	
	@Test
	public void testDiagonalLeft2RightForwardDisplacementResultsInvalid() {
		Displacement d = new DiagonalLeft2RightForwardDisplacement();
		int exceptioncaught = 0;
		try {
			d.next(-1);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		
		try {
			d.next(60);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		
		try {
			d.next(23);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		
		assertTrue(3 == exceptioncaught);
	}

	
	@Test
	public void testDiagonalLeft2RightForwardResultsValid() {
		Displacement d = new DiagonalLeft2RightForwardDisplacement();
		assertTrue(29 == d.next(20));
		assertTrue(42 == d.next(33));
	}
	
	@Test
	public void testIsPassBack() {
		assertFalse(obj.isPassBackRequest(""));
		assertFalse(obj.isPassBackRequest(" "));
		assertFalse(obj.isPassBackRequest(null));
		assertFalse(obj.isPassBackRequest("2"));
		assertFalse(obj.isPassBackRequest("2b"));
		assertFalse(obj.isPassBackRequest("b2"));
		assertFalse(obj.isPassBackRequest("nnnnn"));
		
		assertTrue(obj.isPassBackRequest("pb"));
		assertTrue(obj.isPassBackRequest("PB"));
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
		char[] chessboard = obj.initChessBoard();
		List<Integer> result1 = obj.getListOfCoordinatesCanTurnDisk(obj.getDark(), chessboard, new HorizontalForwardDisplacement(), 26);
		logger.debug("result1:{}", result1);
		assertEquals(1, result1.size());
		assertTrue(27==result1.get(0));
		
		List<Integer> result2 = obj.getListOfCoordinatesCanTurnDisk(obj.getDark(), chessboard, new VerticalForwardDisplacement(), 19);
		logger.debug("result2:{}", result2);
		assertEquals(1, result2.size());
		assertTrue(27==result2.get(0));
		
		List<Integer> result3 = obj.getListOfCoordinatesCanTurnDisk(obj.getDark(), chessboard, new VerticalBackwardDisplacement(), 44);
		logger.debug("result3:{}", result3);
		assertEquals(1, result3.size());
		assertTrue(36==result3.get(0));
		
		List<Integer> result4 = obj.getListOfCoordinatesCanTurnDisk(obj.getDark(), chessboard, new HorizontalBackwardDisplacement(), 37);
		logger.debug("result4:{}", result4);
		assertEquals(1, result4.size());
		assertTrue(36==result4.get(0));
	}
	
	@Test
	public void testGetListOfCoordinatesCanTurnDiskNotFound() {
		char[] chessboard = obj.initChessBoard();
		List<Integer> result1 = obj.getListOfCoordinatesCanTurnDisk(obj.getDark(), chessboard, new HorizontalForwardDisplacement(), 18);
		logger.debug("result1:{}", result1);
		assertEquals(0, result1.size());
		
		List<Integer> result2 = obj.getListOfCoordinatesCanTurnDisk(obj.getDark(), chessboard, new HorizontalForwardDisplacement(), 0);
		logger.debug("result2:{}", result2);
		assertEquals(0, result2.size());
		
		// hitting edge
		List<Integer> result3 = obj.getListOfCoordinatesCanTurnDisk(obj.getDark(), chessboard, new HorizontalForwardDisplacement(), 0);
		logger.debug("result3:{}", result3);
		assertEquals(0, result3.size());
		
		// manually update chessboard through altering char values (not for actual game playing disk turning
		chessboard[0] = obj.getLight();
		if (logger.isDebugEnabled())
			logger.debug("after manually altering chessboard:\n{}", obj.getChessBoardStr(chessboard));
		
		// caught 1 light but eventually hit edge
		List<Integer> result4 = obj.getListOfCoordinatesCanTurnDisk(obj.getDark(), chessboard, new HorizontalBackwardDisplacement(), 1);
		logger.debug("result4:{}", result4);
		assertEquals(0, result4.size());
	}
	
	@Test
	public void getListOfCoordinatesCanTurnDiskNotValid() {
		char[] chessboard = obj.initChessBoard();
		int exceptioncaught = 0;
		try {
			obj.getListOfCoordinatesCanTurnDisk(obj.getDark(), chessboard, -1);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		try {
			obj.getListOfCoordinatesCanTurnDisk(obj.getDark(), chessboard, 64);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		try {
			obj.getListOfCoordinatesCanTurnDisk(obj.getDark(), chessboard, 27);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		
		try {
			obj.getListOfCoordinatesCanTurnDisk(obj.getBlank(), chessboard, 26);
		}
		catch (Exception e) {
			exceptioncaught++;
		}
		
		assertTrue(4 == exceptioncaught);
	}
	
	@Test
	public void getListOfCoordinatesCanTurnDiskValid() {
		char[] chessboard = obj.initChessBoard();
		List<Integer> result1 = obj.getListOfCoordinatesCanTurnDisk(obj.getDark(), chessboard, 26);
		logger.debug("getListOfCoordinatesCanTurnDiskValid result1:{}", result1);
		assertEquals(1, result1.size());
		assertTrue(27==result1.get(0));
		
		List<Integer> result2 = obj.getListOfCoordinatesCanTurnDisk(obj.getDark(), chessboard, 18);
		logger.debug("getListOfCoordinatesCanTurnDiskValid result2:{}", result2);
		assertEquals(0, result2.size());
	}
	
	@Test
	public void testUpdateChessBoard() {
		char[] chessboard = obj.initChessBoard();
		List<Integer> updatepos = Arrays.asList(0,1,2);
		char[] newchessboard = obj.updateChessBoard(chessboard, obj.getLight(), updatepos);
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
		String actual = obj.getChessBoardStr(newchessboard);
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
		assertEquals(obj.getDark(), obj.getPlayerBasedOnRoundsPlayed(0));
		assertEquals(obj.getLight(), obj.getPlayerBasedOnRoundsPlayed(1));
		assertEquals(obj.getDark(), obj.getPlayerBasedOnRoundsPlayed(2));
		assertEquals(obj.getLight(), obj.getPlayerBasedOnRoundsPlayed(3));
	}
	
	@Test
	public void testCanValidMoveBeFound() {
		char[] chessboard = obj.initChessBoard();
		
		// init chess
		assertTrue(obj.canValidMoveBeFound(obj.getDark(), chessboard));
		assertTrue(obj.canValidMoveBeFound(obj.getLight(), chessboard));
		
		char[] chessboard2 = new char[64];
		for (int i = 0; i < 64; i++) {
			chessboard2[i] = obj.getDark();
		}
		
		assertFalse(obj.canValidMoveBeFound(obj.getDark(), chessboard2));
		assertFalse(obj.canValidMoveBeFound(obj.getLight(), chessboard2));
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
	
	@Test
	public void testPlayGameSingleStepValid3() {
		Othello game = new Othello();
		game.playGameSingleRound("4c");
		assertTrue(1==game.getNoOfRoundsPlayed());
		assertTrue(1==game.getStepsGoneThrough().size());
		game.playGameSingleRound("5c");
		assertTrue(2==game.getNoOfRoundsPlayed());
		List<String> steps = game.getStepsGoneThrough();
		assertTrue(2==steps.size());
		assertEquals("4c", steps.get(0));
		assertEquals("5c", steps.get(1));
		
		StringBuilder str = new StringBuilder();
		str.append("1 --------").append('\n');
		str.append("2 --------").append('\n');
		str.append("3 --------").append('\n');
		str.append("4 --XXX---").append('\n');
		str.append("5 --OOO---").append('\n');
		str.append("6 --------").append('\n');
		str.append("7 --------").append('\n');
		str.append("8 --------").append('\n');
		str.append("  abcdefgh").append('\n');
		
		String expect = str.toString();
		String actual = game.getChessBoardStr();
		
		logger.debug("testPlayGameSingleStepValid3 actual:\n{}", actual);
		assertEquals(expect,actual);
	}
	
	@Test
	public void testPlayGameSingleStepValid4() {
		// with passback
		Othello game = new Othello();
		game.playGameSingleRound("4c");
		assertTrue(1==game.getNoOfRoundsPlayed());
		assertTrue(1==game.getStepsGoneThrough().size());
		game.playGameSingleRound("pb");
		assertTrue(2==game.getNoOfRoundsPlayed());
		List<String> steps = game.getStepsGoneThrough();
		assertTrue(2==steps.size());
		assertEquals("4c", steps.get(0));
		assertEquals("pb", steps.get(1));
		
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
		
		logger.debug("testPlayGameSingleStepValid4 actual:\n{}", actual);
		assertEquals(expect,actual);
	}
	
	@Test
	public void testPlayGame() {
		Othello game = new Othello();
		String inputSteps = "f5, 6f, f7, 4f, f3, 3e, d3, c5";
		game.playGame(inputSteps, null, false);
		
		assertTrue(8==game.getNoOfRoundsPlayed());
		List<String> steps = Arrays.asList(new String[]{"f5", "6f", "f7", "4f", "f3", "3e", "d3", "c5"});
		assertEquals(steps, game.getStepsGoneThrough());
		
		StringBuilder str = new StringBuilder();
		str.append("1 --------").append('\n');
		str.append("2 --------").append('\n');
		str.append("3 ---XXX--").append('\n');
		str.append("4 ---XXX--").append('\n');
		str.append("5 --OOOX--").append('\n');
		str.append("6 -----X--").append('\n');
		str.append("7 -----X--").append('\n');
		str.append("8 --------").append('\n');
		str.append("  abcdefgh").append('\n');
		
		String expect = str.toString();
		String actual = game.getChessBoardStr();
		
		logger.debug("testPlayGame actual:\n{}", actual);
		assertEquals(expect,actual);
	}
	
	@Test(expected=InvalidMoveException.class)
	public void testPlayGameHitInvalid() {
		Othello game = new Othello();
		// 1a is invalid move
		String inputSteps = "f5, 6f, f7, 4f, f3, 3e, d3, c5, 1a, 5g";
		try {
			game.playGame(inputSteps, null, false);
		}
		catch (InvalidMoveException ie) {
			assertTrue(8==game.getNoOfRoundsPlayed());
			List<String> steps = Arrays.asList(new String[]{"f5", "6f", "f7", "4f", "f3", "3e", "d3", "c5"});
			assertEquals(steps, game.getStepsGoneThrough());
			throw ie;
		}
	}
	
	@Test(expected=InvalidMoveException.class)
	public void testPlayGameHitInvalid2() {
		Othello game = new Othello();
		// 1a is invalid move
		String inputSteps = "f5, 6f, f7, 4f, f3, 3e, d3, c5, 99, 5g";
		try {
			game.playGame(inputSteps, null, false);
		}
		catch (InvalidMoveException ie) {
			assertTrue(8==game.getNoOfRoundsPlayed());
			List<String> steps = Arrays.asList(new String[]{"f5", "6f", "f7", "4f", "f3", "3e", "d3", "c5"});
			assertEquals(steps, game.getStepsGoneThrough());
			throw ie;
		}
	}
	
	@Test
	public void testGetDiskCounts() {
		char[] chessboard = obj.initChessBoard();
		int[] r = obj.getDiskCounts(chessboard);
		assertTrue(2==r[0] && 2==r[1]);
	}
	
	@Test
	public void testIsEndGame() {
		char[] chessboard = new char[64];
		for (int i = 0; i < 64; i++) {
			chessboard[i] = obj.getDark();
		}
		assertTrue(obj.isEndGameDetected(chessboard, 0));
		assertTrue(obj.isEndGameDetected(chessboard, 1));
		
		char[] chessboard2 = obj.initChessBoard();
		Arrays.asList(4,12,13,16,17,18,19,20,21,22,26,27,28,29,34,35,36)
			.forEach(i->chessboard2[i] = obj.getLight());
		Arrays.asList(23,31,39)
			.forEach(i->chessboard2[i] = obj.getDark());
		if (logger.isDebugEnabled())
			logger.debug("chessboard2:\n{}", obj.getChessBoardStr(chessboard2));
		
		assertTrue(obj.isEndGameDetected(chessboard2, 15));
	}
}
