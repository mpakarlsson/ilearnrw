package ilearnrw.textclassification.tests.panels;

import ilearnrw.user.User;
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
	private JPanel panel;
	//private JSplitPane splitPane_1;

	public WordPanel() {
		
	}
	
	public WordPanel(User user) {
		super();
		mainText = new JTextPane();
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		this.add(splitPane, BorderLayout.CENTER);
        splitPane.setResizeWeight(0.5);
        
        
        mainText = new JTextPane();
        JScrollPane jsp = new JScrollPane(mainText);
		JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(2,1));
		createTable();
		panel2.add(jsp);
		panel2.add(scrollPane);
        splitPane.setLeftComponent(panel2);

        panel = new JPanel();
        panel.setLayout(new GridLayout(1,1));
		splitPane.setRightComponent(panel);
        //splitPane_1 = new JSplitPane();
        //splitPane_1.setResizeWeight(0.40);
        //splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        //panel.add(splitPane_1);


		scrpanel = new JPanel();
		scrpanel.setLayout(new GridLayout(1,1));
		//splitPane_1.setRightComponent(scrpanel);
		panel.add(scrpanel);

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
		panel.removeAll();
		panel.add(scrpanel);
		//splitPane_1.setRightComponent(scrpanel);
        smallHeat = new SmallHeatMapPanel(user);
        smallHeat.draw();
        smallHeat.test();

        scrpanel.add(smallHeat);
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
				{"# Paragraphs", "-"},
				{"# Sentences:", "-"},
				{"# Words:", "-"},
				{"# Distinct Words", "-"},
				{"# Syllables:", "-"},
				{"# Big Sentences (>=15 words)", "-"},
				{"# Polysyllabic Words (>=3 syllables)", "-"},
				{"# Letters and Numbers", "-"},
				{"Longest Word Length", "-"},
				{"Longest Sentence Length", "-"},
				{"Avg Words per Sentence", "-"},
				{"Avg Syllables per Word", "-"},
				{"Avg Word Length", "-"},
				{"Avg Longest Word Length", "-"},
				{"Flesch", "-"},
				{"Flesch-Kincaid", "-"},
				{"Automated", "-"},
				{"Coleman-Liau", "-"},
				{"SMOG", "-"},
				{"Gunning FOG", "-"},
				{"Dale-Chall", "-"},
				{"Formula", "-"},
				{"iLearnRW", "-"}
		};

		resultsTable = new JTable(data, columnNames);
        TableColumn column = resultsTable.getColumnModel().getColumn(1);
        column.setPreferredWidth(resultsTable.getWidth()/4);
        scrollPane = new JScrollPane(resultsTable);
        resultsTable.setFillsViewportHeight(true);
	}
}
