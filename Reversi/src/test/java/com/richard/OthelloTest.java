package com.richard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
}
