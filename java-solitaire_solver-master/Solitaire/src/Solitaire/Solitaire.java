package Solitaire;

import GUI.BoardPanel;
import GUI.SolitaireGUI;

import javax.swing.*;
import java.io.IOException;

public class Solitaire {

	static Board board = new Board();
	static Print printer;
	static BoardPanel boardPanel;

	public static void main(String[] args) throws IOException {
		// Initialize the GUI
		SwingUtilities.invokeLater(() -> {
			SolitaireGUI gui = new SolitaireGUI();
			gui.setVisible(true);
			boardPanel = gui.getBoardPanel();
		});

		printer = new Print();

		boolean end = false;
		board = new Board();
		board.incrementPile();
		int numberOfMoves = 0;
		while (!end) {
			end = Logic();
			numberOfMoves++;
			updateGUI();
			WaitForInput();
		}

		FinalStatus(numberOfMoves);
	}

	public static boolean Logic() throws IOException {
		boolean cont = true;
		boolean end = true;

		while (cont) {
			cont = Aces();
			if (cont) {
				end = false;
			}
		}
		cont = true;
		while (cont) {
			cont = Shift();
			if (cont) {
				end = false;
			}
		}
		if (Kings()) {
			end = false;
		} else if (Pile(false)) {
			end = false;
		} else if (Bottoms()) {
			end = false;
		} else if (Pile(true)) {
			end = false;
		} else if (PileToFoundation()) {
			end = false;
		}
		return end;
	}

	public static boolean Aces() {
		boolean move = false;
		for (int i = 0; i < 7; i++) {
			if (board.getSizeStack(i) > 0) {
				if (board.getCardStackLast(i).getValue().ordinal() == 0) {
					int foundation = 0;
					for (int j = 0; j < 4; j++) {
						if (board.getSizeFoundation(j) == 0) {
							foundation = j;
							j = 4;
						}
					}
					move = true;
					Suits suit = board.getCardStackLast(i).getSuit();
					board.moveBoardToFoundation(i, foundation);
					printer.PrintActionAces(board, suit);
				}
			}
		}
		return move;
	}

	public static boolean Shift() {
		boolean move = false;
		int columnA = 0;
		while (columnA < 7 && !move) {
			int sizeStackA = board.getSizeStack(columnA);
			if (sizeStackA > 0) {
				Cards card1 = new Cards();
				int row = 0;
				for (int i = (sizeStackA - 1); i >= 0; i--) {
					if (board.getCardStack(columnA, i).getVisibility()) row = i;
				}
				card1 = board.getCardStack(columnA, row);
				int columnB = 0;
				while (columnB < 7 && !move) {
					if (columnB != columnA) {
						if (board.getSizeStack(columnB) > 0) {
							Cards card2 = board.getCardStackLast(columnB);
							Color color1 = card1.getColor();
							Color color2 = card2.getColor();
							int name1 = card1.getValue().ordinal();
							int name2 = card2.getValue().ordinal() - 1;

							if (color1 != color2 && name1 == name2) {
								move = true;
								board.moveBoardToBoard(columnA, row, columnB, false);
								printer.PrintActionShift(board, card1.getValue(), card1.getSuit(), columnA, columnB);
							}
						}
					}
					columnB++;
				}
			}
			columnA++;
		}
		return move;
	}

	public static boolean Kings() {
		boolean move = false;
		int columnA = 0;
		while (columnA < 7 && !move) {
			int sizeStackA = board.getSizeStack(columnA);
			if (sizeStackA > 1) {
				int row = 0;
				for (int i = (sizeStackA - 1); i >= 0; i--) {
					if (board.getCardStack(columnA, i).getVisibility()) row = i;
				}
				if (board.getCardStack(columnA, row).getValue() == Value.King && row > 0) {
					int columnB = 0;
					while (columnB < 7 && !move) {
						if (columnB != columnA) {
							if (board.getSizeStack(columnB) == 0) {
								move = true;
								Suits suit = board.getCardStack(columnA, row).getSuit();
								board.moveBoardToBoard(columnA, row, columnB, true);
								printer.PrintActionKings(board, suit, columnA, columnB);
							}
						}
						columnB++;
					}
				}
			}
			columnA++;
		}
		return move;
	}

	public static boolean Bottoms() {
		boolean move = false;
		for (int columnA = 0; columnA < 7; columnA++) {
			if (board.getSizeStack(columnA) > 0) {
				Cards card1 = board.getCardStackLast(columnA);
				for (int foundation = 0; foundation < 4; foundation++) {
					if (board.getSizeFoundation(foundation) > 0) {
						Cards card2 = board.getCardFoundation(foundation);
						Suits suit1 = card1.getSuit();
						Suits suit2 = card2.getSuit();
						int value1 = card1.getValue().ordinal();
						int value2 = card2.getValue().ordinal() + 1;

						if (suit1 == suit2 && value1 == value2) {
							move = true;
							Value value = card1.getValue();
							board.moveBoardToFoundation(columnA, foundation);
							printer.PrintActionBottoms(board, value, suit1, columnA);
						}
					}
				}
			}
		}
		return move;
	}

	public static boolean Pile(boolean kings) {
		boolean move = false;
		int count = 0;
		while (!move && count < board.getSizePile()) {
			if (board.usePile()) {
				Cards card1 = board.getCardPileLast();
				if (card1.getValue().ordinal() == 0) {
					int foundation = 0;
					for (int j = 0; j < 4; j++) {
						if (board.getSizeFoundation(j) == 0) {
							foundation = j;
							j = 4;
						}
					}
					move = true;
					board.movePileToFoundation(foundation);
				} else {
					int column = 0;
					while (column < 7 && !move) {
						if (kings) {
							if (board.getSizeStack(column) == 0 && card1.getValue() == Value.King) {
								move = true;
								board.movePileToBoard(column);
								printer.PrintActionPileToBoard(board, card1.getValue(), card1.getSuit(), column);
							}
						} else if (board.getSizeStack(column) > 0) {
							Cards card2 = board.getCardStackLast(column);
							Color color1 = card1.getColor();
							Color color2 = card2.getColor();
							int value1 = card1.getValue().ordinal();
							int value2 = card2.getValue().ordinal() - 1;

							if (color1 != color2 && value1 == value2) {
								move = true;
								board.movePileToBoard(column);
								printer.PrintActionPileToBoard(board, card1.getValue(), card1.getSuit(), column);
							}
						}
						column++;
					}
				}
			}
			board.incrementPile();
			count++;
		}
		return move;
	}

	public static boolean PileToFoundation() {
		boolean move = false;
		int count = 0;
		while (!move && count < board.getSizePile()) {
			if (board.usePile()) {
				Cards card1 = board.getCardPileLast();
				for (int foundation = 0; foundation < 4; foundation++) {
					if (board.getSizeFoundation(foundation) > 0) {
						Cards card2 = board.getCardFoundation(foundation);
						Suits suit1 = card1.getSuit();
						Suits suit2 = card2.getSuit();
						int value1 = card1.getValue().ordinal();
						int value2 = card2.getValue().ordinal() + 1;

						if (suit1 == suit2 && value1 == value2) {
							move = true;
							board.movePileToFoundation(foundation);
							printer.PrintActionPileToFoundation(board, card1.getValue(), suit1);
						}
					}
				}
			}
			board.incrementPile();
			count++;
		}
		return move;
	}

	public static boolean FinalStatus(int numberOfMoves) {
		boolean pass = true;
		if (board.getSizePile() > 0) pass = false;
		for (int column = 0; column < 7; column++) {
			if (board.getSizeStack(column) > 0) pass = false;
		}
		if (!board.ValidateFoundation()) pass = false;
		printer.PrintStatusFinal(pass, numberOfMoves);
		return pass;
	}

	public static void WaitForInput() throws IOException {
		printer.PrintString("Press Enter to continue...%n%n", true, false);
	}

	public static void updateGUI() {
		if (boardPanel != null) {
			boardPanel.updateBoard(board);
		}
	}
}
