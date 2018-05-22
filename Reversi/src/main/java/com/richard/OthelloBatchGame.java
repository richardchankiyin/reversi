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
		
		if (logger.isInfoEnabled()) {
			logger.info("chessboard:\n{}", game.getChessBoardStr());
			logger.info("steps:{}",game.getStepsGoneThrough());
		}
		
		output(game.getChessBoardStr());
		//output(game.getStepsGoneThrough().toString());
		//output(String.valueOf(game.getLastRoundDetectedInvalid()));
		
		if (game.isEndGame()) {
			int[] counts = game.getDiskCounts();
			int darkc = counts[0];
			int lightc = counts[1];
			
			StringBuilder str = new StringBuilder();
			str.append("No further moves available\n");
			if (darkc == lightc) {
				str.append(String.format("Draw (%s vs %s)\n", darkc, lightc));
			} else {
				char winner = darkc > lightc ? game.getDark() : game.getLight();
				str.append(String.format("Player '%s' wins (%s vs %s)\n"
						, winner, darkc, lightc));
			}
			
			output(str.toString());
		}
		
	}

	private static void output(String msg) {
		System.out.println(msg);
	}
}
