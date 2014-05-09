package ilearnrw.textclassification.tests.panels;

import ilearnrw.textclassification.Word;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class WordListPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private static Word value = null;
    private JTextArea wordList;
    private ArrayList<Word> words;

	public WordListPanel(ArrayList<Word> wordl, String title) {
		this.words = wordl;
        
        
		wordList = new JTextArea("");

        
        JScrollPane listScroller = new JScrollPane(wordList);
        listScroller.setPreferredSize(new Dimension(220, 180));
        listScroller.setAlignmentX(LEFT_ALIGNMENT);

        //Create a container so that we can add a title around
        //the scroll pane.  Can't add a title directly to the
        //scroll pane because its background would be white.
        //Lay out the label and scroll pane from top to bottom.
        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel(title);
        label.setLabelFor(wordList);
        listPane.add(label);
        listPane.add(Box.createRigidArea(new Dimension(0,5)));
        listPane.add(listScroller);
        listPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));


        this.add(listPane, BorderLayout.CENTER);
	}

    //Handle clicks on the Set and Cancel buttons.
    public void mouseClicked(ActionEvent e) {
        if ("Set".equals(e.getActionCommand())) {
        }
    }
    
    public ArrayList<Word> getWords(){
    	return this.words;
    }
    
    public void setText(String t){
    	wordList.setText(t);
    }

}
