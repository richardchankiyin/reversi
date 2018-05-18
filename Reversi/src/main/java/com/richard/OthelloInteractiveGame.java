package com.richard;

import java.util.Scanner;

public class OthelloInteractiveGame {

	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//output("test"); String r = input();
		//output(r);
		rungame();
	}

	private static void rungame() {
		boolean isContinue = true;
		Othello game = new Othello(100);
		
		output(game.getChessBoardStr());
		
		while (isContinue) {
			char player = game.getPlayer();
			output(String.format("Player '%s' move:", player));
			String step = input();
			try {
				game.playGameSingleRound(step);
				output(game.getChessBoardStr());
			}
			catch (InvalidMoveException ie) {
				output("Invalid move. Please try again.\n\n");
			}
			catch (GameIsOverException e) {
				isContinue = false;
				output("game is over\n");
				
				output(game.getChessBoardStr());
				
				int[] counts = game.getDiskCounts();
				int darkc = counts[0];
				int lightc = counts[1];
				
				StringBuilder str = new StringBuilder();
				str.append("No further moves available\n");
				if (darkc == lightc) {
					str.append(String.format("Draw (%s vs %s)\n", darkc, lightc));
				} else {
					char winner = darkc > lightc ? game.getdark() : game.getlight();
					str.append(String.format("Player '%s' wins (%s vs %s)\n"
							, winner, darkc, lightc));
				}
				
				output(str.toString());
			}
		}
		
		
	}
	
	private static void output(String msg) {
		System.out.printf(msg);
	}
	
	private static String input() {
		return scanner.next();
	}
}
