package Solitaire;

import java.util.ArrayList;
import java.util.List;

public class Board {
	private Deck deck;
	private ArrayList<ArrayList<Integer>> stacks = new ArrayList<>();
	private ArrayList<ArrayList<Integer>> foundation = new ArrayList<>();
	private ArrayList<Integer> pile = new ArrayList<>();
	private int pileShowMax = 0;
	private int pileRuns = 0;
	private int pileRunMax = 3;
	private int pileMoves = 0;

	public Board() {
		CreateBoard();
		deck = new Deck();
		deck.Shuffle();
		Deal();
	}

	private void CreateBoard() {
		for (int i = 0; i < 7; i++) {
			stacks.add(new ArrayList<>());
		}
		for (int i = 0; i < 4; i++) {
			foundation.add(new ArrayList<>());
		}
	}

	private void CheckVisibility(int column) {
		if (stacks.get(column).size() > 0) {
			int cardNum = (stacks.get(column).get(stacks.get(column).size() - 1));
			if (!deck.getCardVisibility(cardNum)) {
				deck.setCardVisibility(cardNum, true);
			}
		}
	}

	public void Deal() {
		int a = 0;
		for (int i = 0; i < 7; i++) {
			for (int j = i; j < 7; j++) {
				stacks.get(j).add(a++);
			}
			deck.setCardVisibility(stacks.get(i).get(getSizeStack(i) - 1), true);
		}
		while (a < 52) {
			pile.add(a++);
			deck.setCardVisibility(a - 1, true);
		}
	}

	public List<List<Cards>> getCardsGrid() {
		List<List<Cards>> grid = new ArrayList<>();
		for (int i = 0; i < 11; i++) {
			List<Cards> row = new ArrayList<>();
			for (int j = 0; j < 7; j++) {
				if (i < stacks.get(j).size()) {
					row.add(deck.getCard(stacks.get(j).get(i)));
				} else {
					row.add(null);
				}
			}
			grid.add(row);
		}
		return grid;
	}

	public Cards getCardStack(int column, int row) {
		return deck.getCard(stacks.get(column).get(row));
	}

	public Cards getCardStackLast(int column) {
		if (stacks.get(column).size() == 0) {
			System.out.println("Error: Attempted to get card from empty stack!");
		}
		return deck.getCard(stacks.get(column).get(stacks.get(column).size() - 1));
	}

	public Cards getCardFoundation(int column) {
		return deck.getCard(foundation.get(column).get(foundation.get(column).size() - 1));
	}

	public Cards getCardPile(int i) {
		return deck.getCard(pile.get(i));
	}

	public Cards getCardPileLast() {
		return deck.getCard(pile.get(pileShowMax - 1));
	}

	public int getPileShowMax() {
		return pileShowMax;
	}

	public boolean usePile() {
		return pileShowMax > 0 && (pileMoves < (pileRunMax * 52));
	}

	public int getSizeFoundation(int column) {
		return foundation.get(column).size();
	}

	public int getSizeStack(int stackNum) {
		return stacks.get(stackNum).size();
	}

	public int getSizePile() {
		return pile.size();
	}

	public int getMaxSizeStack() {
		int a = 0;
		for (int i = 0; i < stacks.size(); i++) {
			int b = getSizeStack(i);
			if (a < b) a = b;
		}
		return a;
	}

	public void incrementPile() {
		int pileSize = pile.size();
		if (pileShowMax == pileSize) pileShowMax = 0;
		else {
			pileShowMax += 1;
			if (pileShowMax > pileSize) pileShowMax = pileSize;
		}
		pileMoves++;
	}

	public void moveBoardToBoard(int columnStart, int rowStart, int columnFinish, boolean kings) {
		if (!deck.getCardVisibility(stacks.get(columnStart).get(rowStart))) {
			System.out.println("Cannot move this card");
		} else if (kings || (deck.getCardColor(stacks.get(columnStart).get(rowStart)) != deck.getCardColor(stacks.get(columnFinish).get(stacks.get(columnFinish).size() - 1)))) {
			while (stacks.get(columnStart).size() > rowStart) {
				stacks.get(columnFinish).add(stacks.get(columnStart).get(rowStart));
				stacks.get(columnStart).remove(rowStart);
			}
		} else {
			System.out.println("Invalid move");
		}
		CheckVisibility(columnStart);
	}

	public void moveBoardToFoundation(int columnStacks, int columnFoundation) {
		int row = stacks.get(columnStacks).size() - 1;
		foundation.get(columnFoundation).add(stacks.get(columnStacks).get(row));
		stacks.get(columnStacks).remove(row);
		CheckVisibility(columnStacks);
	}

	public void movePileToBoard(int column) {
		stacks.get(column).add(pile.get(pileShowMax - 1));
		pile.remove(pileShowMax - 1);
		pileShowMax--;
		pileMoves += (pileRunMax - pileRuns);
	}

	public void movePileToFoundation(int column) {
		foundation.get(column).add(pile.get(pileShowMax - 1));
		pile.remove(pileShowMax - 1);
		pileShowMax--;
		pileMoves += (pileRunMax - pileRuns);
	}

	public boolean ValidateFoundation() {
		boolean pass = true;
		for (int column = 0; column < 4; column++) {
			int row = 0;
			while ((row + 1) < foundation.get(column).size()) {
				Cards card1 = deck.getCard(foundation.get(column).get(row));
				Cards card2 = deck.getCard(foundation.get(column).get(row + 1));
				if (card1.getSuit() != card2.getSuit()) pass = false;
				else if (card1.getValue().ordinal() != card2.getValue().ordinal() - 1) pass = false;
				row++;
			}
		}
		return pass;
	}
}
