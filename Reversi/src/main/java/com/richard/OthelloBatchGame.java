package com.richard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OthelloBatchGame {
	private static Logger logger = LoggerFactory.getLogger(OthelloBatchGame.class);
	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("Usage java OthelloBatchGame [game steps], e.g. \"1a, 2b\"");
			System.exit(-1);
		}

		Othello game = new Othello();
		try {
			game.playGame(args[0]);
		}
		catch (InvalidMoveException ie) {
			output("hit an invalid move....");
			output("steps gone through: " + game.getStepsGoneThrough());
		}
		catch (GameIsOverException ge) {
			output("game is over");
		}
		catch (Exception e) {
			output(e.getMessage());
		}
		
		output(game.getChessBoardStr());
		
		
		
		if (logger.isDebugEnabled()) {
			logger.debug("chessboard:\n{}", game.getChessBoardStr());
			logger.debug("steps:{}",game.getStepsGoneThrough());
		}
	}

	private static void output(String msg) {
		System.out.println(msg);
	}
}
