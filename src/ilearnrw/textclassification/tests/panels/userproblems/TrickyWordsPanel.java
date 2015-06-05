package ilearnrw.textclassification.tests.panels.userproblems;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import ilearnrw.textclassification.Word;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;


public class TrickyWordsPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private static Word value = null;
    private JList list;
    private ArrayList<Word> words;

	public TrickyWordsPanel(Object data[]) {
		words = new ArrayList<Word>();
		for (int i=0;i<data.length; i++)
			words.add((Word)data[i]);
		
        JButton addButton = new JButton("Add Tricky Word");
        final JButton removeButton = new JButton("Remove Tricky Word");
        
        
		addButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try{
					String s = (String)JOptionPane.showInputDialog("Insert bellow the word to add\n",
		                    JOptionPane.PLAIN_MESSAGE);
					Word wordToAdd = new Word(s);
		            if (!s.isEmpty() && !words.contains(wordToAdd)){
		        		words.add(wordToAdd);
		            	System.out.println("ok");
		            	list.setListData(words.toArray());
		            }
				}
				catch (java.lang.NullPointerException ex){}
			}
		});
        
		removeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try{
		        	TrickyWordsPanel.value = new Word(list.getSelectedValue().toString());
		        	int n = JOptionPane.showConfirmDialog(null, "Do you really want to delete the word \'"+
		        	TrickyWordsPanel.value+"\'?\n", "Remove Tricky Word", JOptionPane.YES_NO_OPTION);
		        	if (n==0){
		        		words.remove(TrickyWordsPanel.value);
		            	list.setListData(words.toArray());
		            }
				}
				catch (java.lang.NullPointerException ex){}
			}
		});
        
		list = new JList(words.toArray()) {
			private static final long serialVersionUID = 1L;

			//Subclass JList to workaround bug 4832765, which can cause the
            //scroll pane to not let the user easily scroll up to the beginning
            //of the list.  An alternative would be to set the unitIncrement
            //of the JScrollBar to a fixed value. You wouldn't get the nice
            //aligned scrolling, but it should work.
            public int getScrollableUnitIncrement(Rectangle visibleRect,
                                                  int orientation,
                                                  int direction) {
                int row;
                if (orientation == SwingConstants.VERTICAL &&
                      direction < 0 && (row = getFirstVisibleIndex()) != -1) {
                    Rectangle r = getCellBounds(row, row);
                    if ((r.y == visibleRect.y) && (row != 0))  {
                        Point loc = r.getLocation();
                        loc.y--;
                        int prevIndex = locationToIndex(loc);
                        Rectangle prevR = getCellBounds(prevIndex, prevIndex);

                        if (prevR == null || prevR.y >= r.y) {
                            return 0;
                        }
                        return prevR.height;
                    }
                }
                return super.getScrollableUnitIncrement(
                                visibleRect, orientation, direction);
            }
        };

        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    removeButton.doClick(); //emulate button click
                }
            }
        });
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(250, 100));
        listScroller.setAlignmentX(LEFT_ALIGNMENT);

        //Create a container so that we can add a title around
        //the scroll pane.  Can't add a title directly to the
        //scroll pane because its background would be white.
        //Lay out the label and scroll pane from top to bottom.
        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel("Tricky Words List");
        label.setLabelFor(list);
        listPane.add(label);
        listPane.add(Box.createRigidArea(new Dimension(0,5)));
        listPane.add(listScroller);
        listPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));


        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.PAGE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPane.add(Box.createVerticalGlue());
        buttonPane.add(addButton);
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPane.add(removeButton);


        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(listPane);
        this.add(buttonPane);
	}

    //Handle clicks on the Set and Cancel buttons.
    public void mouseClicked(ActionEvent e) {
        if ("Set".equals(e.getActionCommand())) {
        }
    }
    
    public ArrayList<Word> getWords(){
    	return this.words;
    }

}
