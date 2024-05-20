package GUI;

import javax.swing.*;
import java.awt.Color;

public class SolitaireGUI extends JFrame {
    private BoardPanel boardPanel;

    public SolitaireGUI() {
        setTitle("Solitaire");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        boardPanel = new BoardPanel();
        add(boardPanel);
        setBackground(Color.GREEN);
    }

    public BoardPanel getBoardPanel() {

        return boardPanel;
    }



}
