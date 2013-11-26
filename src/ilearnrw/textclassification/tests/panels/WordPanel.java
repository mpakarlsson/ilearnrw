package ilearnrw.textclassification.tests.panels;

import ilearnrw.user.User;
import ilearnrw.utils.LanguageCode;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;

import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import javax.swing.JTable;

public class WordPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTextPane mainText;
	private JTable resultsTable;
	private JScrollPane scrollPane;
	private SmallHeatMapPanel smallHeat;
	private JPanel scrpanel;
	private JSplitPane splitPaneUpDown;

	public WordPanel() {
		
	}
	
	public WordPanel(User user) {
		super();
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();

		splitPaneUpDown = new JSplitPane();
		splitPaneUpDown.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPaneUpDown.setDividerLocation(700);
		
		JSplitPane splitPane = new JSplitPane();
		splitPaneUpDown.setLeftComponent(splitPane);
		this.add(splitPaneUpDown, BorderLayout.CENTER);
		splitPane.setDividerLocation(700);
        
		splitPane.setRightComponent(panel);
        panel.setLayout(new GridLayout(1,1));
        
        mainText = new JTextPane();
		mainText.setEditable(false);
        JScrollPane jsp = new JScrollPane(mainText);
        splitPane.setLeftComponent(jsp);
        
        createTable();
        panel.add(scrollPane);
        
        //splitPane_1.setLeftComponent(scrollPane);


		scrpanel = new JPanel();
		scrpanel.setLayout(new GridLayout(1,1));

		splitPaneUpDown.setRightComponent(scrpanel);

        smallHeat = new SmallHeatMapPanel(user);
        smallHeat.draw();
        smallHeat.test();

        scrpanel.add(smallHeat);

	}
	
	public void reset(User user){
		mainText.setText("");
		resetResultsTable();
		scrpanel = new JPanel();
		scrpanel.setLayout(new GridLayout(1,1));
		//splitPane_1.setRightComponent(scrpanel);
        smallHeat = new SmallHeatMapPanel(user);
        smallHeat.draw();
        smallHeat.test();

        scrpanel.add(smallHeat);
		splitPaneUpDown.setRightComponent(scrpanel);
		splitPaneUpDown.setDividerLocation(700);
	}
	
	public SmallHeatMapPanel getSmallHeatMapPanel() {
		return smallHeat;
	}

	public void resetResultsTable(){
		for (int i=0;i<resultsTable.getRowCount();i++){
			//this.resultsTable.setValueAt("", i, 0);
			this.resultsTable.setValueAt("", i, 1);
		}
	}

	public void setResultsTable(String str[][]){
		for (int i=0;i<(str.length<resultsTable.getRowCount()?str.length:resultsTable.getRowCount());i++){
			this.resultsTable.setValueAt(str[i][0], i, 0);
			this.resultsTable.setValueAt(str[i][1], i, 1);
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
		String[] columnNames = {"one", "two"};

		Object[][] data = {
				{"Word:", "-"},
				{"Word Language", "-"},
				{"User Compatible", "-"},
				{"# Letters:", "-"},
				{"# Syllables:", "-"},
				{"Phonetics:", "-"},
				{"Syllables:", "-"},
				{"CV Form:", "-"},
				{"Total Hits:", "-"},
				{"User Hits:", "-"},
				{"Word Difficutly:", "???"},
				{"", ""},
				{"", ""},
				{"", ""},
				{"", ""},
				{"", ""},
				{"", ""},
				{"", ""},
				{"", ""},
				{"", ""},
				{"", ""},
				{"", ""},
				{"", ""}
		};

		resultsTable = new JTable(data, columnNames);
        TableColumn column = resultsTable.getColumnModel().getColumn(1);
        column.setPreferredWidth(resultsTable.getWidth()/4);
        scrollPane = new JScrollPane(resultsTable);
        resultsTable.setFillsViewportHeight(true);
	}
}
