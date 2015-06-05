package ilearnrw.textclassification.tests.panels;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import ilearnrw.user.profile.UserProfile;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;

import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;

public class TextPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTextPane mainText;
	private JTable resultsTable;
	private JScrollPane scrollPane;
	private SmallHeatMapPanel smallHeat;
	private JPanel scrpanel;
	private JSplitPane splitPaneUpDown;
	private JPanel panel;
	private JSplitPane splitPane;

	public TextPanel() {
		
	}
	
	public TextPanel(UserProfile userProfile) {
		super();
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new BorderLayout(0, 0));
		panel = new JPanel();

		splitPaneUpDown = new JSplitPane();
		splitPaneUpDown.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPaneUpDown.setDividerLocation(700);
		
		splitPane = new JSplitPane();
		splitPaneUpDown.setLeftComponent(splitPane);
		this.add(splitPaneUpDown, BorderLayout.CENTER);
		splitPane.setDividerLocation(500);
        
		splitPane.setRightComponent(panel);
        panel.setLayout(new GridLayout(1,1));
        
        mainText = new JTextPane();
		//mainText.setEditable(false);
        JScrollPane jsp = new JScrollPane(mainText);
        splitPane.setLeftComponent(jsp);
        
        //splitPane_1.setLeftComponent(scrollPane);


		scrpanel = new JPanel();
		scrpanel.setLayout(new GridLayout(1,1));

		splitPaneUpDown.setRightComponent(scrpanel);

        smallHeat = new SmallHeatMapPanel(userProfile);
        smallHeat.draw();
        smallHeat.test();

        
        createTable();
        //panel.add(scrollPane);
        //scrpanel.add(smallHeat);
        panel.add(smallHeat);
        scrpanel.add(scrollPane);

	}

	public int getColorMode() {
		return smallHeat.getColorMode();
	}

	public void setColorMode(int colorMode) {
		smallHeat.setColorMode(colorMode);
	}
	
	public void reset(UserProfile userProfile){
		mainText.setText("");
		resetResultsTable();
		scrpanel = new JPanel();
		scrpanel.setLayout(new GridLayout(1,1));
		//splitPane_1.setRightComponent(scrpanel);
        smallHeat = new SmallHeatMapPanel(userProfile);
        smallHeat.draw();
        smallHeat.test();

        panel = new JPanel();
		splitPane.setDividerLocation(500);
		splitPane.setRightComponent(panel);
        panel.setLayout(new GridLayout(1,1));
        panel.add(smallHeat);
        scrpanel.add(scrollPane);
		splitPaneUpDown.setRightComponent(scrpanel);
		splitPaneUpDown.setDividerLocation(700);
	}
	
	public SmallHeatMapPanel getSmallHeatMapPanel() {
		return smallHeat;
	}

	public void resetResultsTable(){
		for (int i=0;i<resultsTable.getRowCount();i++){
			for (int j=1; j<resultsTable.getColumnCount(); j+=2){
				//this.resultsTable.setValueAt("", i, 0);
				this.resultsTable.setValueAt("", i, j);
			}
		}
	}

	public void setResultsTable(String str[][]){
		for (int i=0;i<(str.length<resultsTable.getRowCount()?str.length:resultsTable.getRowCount());i++){
			for (int j=0;j<(str[i].length<resultsTable.getColumnCount()?str[i].length:resultsTable.getColumnCount());j++){
				this.resultsTable.setValueAt(" "+str[i][j]+" ", i, j);
				//this.resultsTable.setValueAt(str[i][1], i, 1);
			}
		}
	}

	public void setMainText(JTextPane mainText) {
		this.mainText = mainText;
	}

	public JTextPane getMainText(){
		return this.mainText;
	}

	public String getText(){
		return this.mainText.getText();
	}
	

	public void testMethod(String str){
		mainText.setText(str);
	}
	
	private void createTable(){
		String[] columnNames = {"Property", "Value", "Property", "Value", "Test", "Value"};

		Object[][] data = {
				{" # Paragraphs", "- ", " Longest Word Length:", "- ", " Flesch:", "- "},
				{" # Sentences:", "- ", " Longest Sentence Length:", "- ", " Flesch-Kincaid:", "- "},
				{" # Words:", "- ", " Avg Words per Sentence:", "- ", " Automated:", "- "},
				{" # Distinct Words", "- ", " Avg Syllables per Word:", "- ", " Coleman-Liau:", "- "},
				{" # Syllables:", "- ", " Avg Word Length:", "- ", " SMOG:", "- "},
				{" # Big Sentences:", "- ", " Avg Longest Word Length:", "- ", " Gunning FOG:", "- "},
				{" # Polysyllabic Words:", "- ", "", "", " iLearnRW:", "- "},
				{" # Letters and Numbers:", "- ", "", "- ", " Text Score:", "- "}
		};

		resultsTable = new JTable(data, columnNames);
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
		for (int i=0;i<resultsTable.getColumnCount(); i+=2){
			resultsTable.getColumnModel().getColumn(i+1).setCellRenderer( rightRenderer );
			resultsTable.getColumnModel().getColumn(i+1).setPreferredWidth(60);
		}
		resultsTable.getColumnModel().getColumn(0).setPreferredWidth(170);
		resultsTable.getColumnModel().getColumn(2).setPreferredWidth(200);
		resultsTable.getColumnModel().getColumn(4).setPreferredWidth(140);
		
        //TableColumn column = resultsTable.getColumnModel().getColumn(1);
        //column.setPreferredWidth(resultsTable.getWidth()/4);
        resultsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        resultsTable.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        scrollPane = new JScrollPane(resultsTable);
        //resultsTable.setFillsViewportHeight(true);
	}
}
