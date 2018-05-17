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
}
