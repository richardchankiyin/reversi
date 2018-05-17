package com.richard;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

public class OthelloTest {
	
	private Othello obj = null;
	
	@Before
	public void setup() {
		obj = new Othello();
	}

	@Test
	public void testInitChessBoard() {
		char[] result = obj.initchessboard();
		assertEquals(64, result.length);
	}

}
