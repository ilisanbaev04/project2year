package GUI;

import Solitaire.Board;
import Solitaire.Cards;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BoardPanel extends JPanel {
    private Board board;

    public BoardPanel() {
        board = new Board(); // Use your existing game logic
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));
        setBackground(new java.awt.Color(128, 0, 0)); // Green background for the board

        renderBoard();
    }

    private void renderBoard() {
        removeAll(); // Clear previous components

        JPanel boardPanel = new JPanel(new GridLayout(11, 7, 10, 10)); // 11 rows and 7 columns with gaps
        List<List<Cards>> cardsGrid = board.getCardsGrid();
        for (List<Cards> row : cardsGrid) {
            for (Cards card : row) {
                if (card != null) {
                    boardPanel.add(new CardComponent(card));
                } else {
                    boardPanel.add(new JLabel()); // Empty label for empty slots
                }
            }
        }

        add(boardPanel, BorderLayout.CENTER);

        JPanel foundationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for (int i = 0; i < 4; i++) {
            if (board.getSizeFoundation(i) > 0) {
                foundationPanel.add(new CardComponent(board.getCardFoundation(i)));
            } else {
                foundationPanel.add(new JLabel("___")); // Placeholder for empty foundation
            }
        }

        add(foundationPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    public void refresh() {
        renderBoard();
    }

    public void updateBoard(Board newBoard) {
        this.board = newBoard;
        refresh();
    }
}
