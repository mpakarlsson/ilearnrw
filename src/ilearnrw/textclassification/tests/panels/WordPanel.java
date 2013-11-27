package ilearnrw.textclassification.tests.panels;

import ilearnrw.textclassification.Text;
import ilearnrw.textclassification.tests.TextMetricsTest;
import ilearnrw.user.User;
import ilearnrw.utils.LanguageCode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import javax.swing.JTable;

public class WordPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTextField word;
	private JButton goButton;
	private JTable resultsTable;
	private JScrollPane scrollPane;
	private WordHeatMapPanel smallHeat;
	private JPanel scrpanel;
	private JPanel panel;
	private JPanel wordPanel, wordAndResultsPanel;;
	private JSplitPane splitPane;

	public WordPanel() {
		
	}
	
	public WordPanel(User user, final TextMetricsTest metrics) {
		super();
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new BorderLayout(0, 0));
		panel = new JPanel();
		
		splitPane = new JSplitPane();
		this.add(splitPane);
		splitPane.setDividerLocation(400);
        
		splitPane.setRightComponent(panel);
        panel.setLayout(new GridLayout(1,1));
        
        word = new JTextField(25);
        wordPanel = new JPanel();
        wordPanel.setLayout(new BoxLayout(wordPanel, BoxLayout.PAGE_AXIS));
        goButton = new JButton("Calculate");
		goButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!word.getText().trim().isEmpty())
					metrics.classifierResults(true);
			}
		});
		wordPanel.add(new JLabel("Insert a word Bellow"));
		wordPanel.add(Box.createRigidArea(new Dimension(0,5)));
		wordPanel.add(word);
		wordPanel.add(Box.createRigidArea(new Dimension(0,5)));
		

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(goButton);

		wordPanel.add(buttonPane);
		

        createTable();
        wordAndResultsPanel = new JPanel();
        wordAndResultsPanel.setLayout(new BorderLayout());
        wordAndResultsPanel.add(wordPanel, BorderLayout.PAGE_START);
        wordAndResultsPanel.add(new JScrollPane(resultsTable), BorderLayout.CENTER);
        splitPane.setLeftComponent(wordAndResultsPanel);
        
        //splitPane_1.setLeftComponent(scrollPane);


		scrpanel = new JPanel();
		scrpanel.setLayout(new GridLayout(1,1));

		//mainManel.setRightComponent(scrpanel);

        smallHeat = new WordHeatMapPanel(user);
        smallHeat.draw();
        smallHeat.test();

        
        //panel.add(scrollPane);
        //scrpanel.add(smallHeat);
        panel.add(smallHeat);
        scrpanel.add(scrollPane);

	}
	
	public void reset(User user){
		word.setText("");
		resetResultsTable();
		scrpanel = new JPanel();
		scrpanel.setLayout(new GridLayout(1,1));
		//splitPane_1.setRightComponent(scrpanel);
        smallHeat = new WordHeatMapPanel(user);
        smallHeat.draw();
        smallHeat.test();

        panel = new JPanel();
		splitPane.setDividerLocation(400);
		splitPane.setRightComponent(panel);
        panel.setLayout(new GridLayout(1,1));
        panel.add(smallHeat);
        scrpanel.add(scrollPane);
		//mainManel.setRightComponent(scrpanel);
		//mainManel.setDividerLocation(700);
	}
	
	public WordHeatMapPanel getSmallHeatMapPanel() {
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
				this.resultsTable.setValueAt(str[i][j], i, j);
				//this.resultsTable.setValueAt(str[i][1], i, 1);
			}
		}
	}

	public void setMainText(JTextField word) {
		this.word = word;
	}

	public JTextField getMainText(){
		return this.word;
	}

	public String getText(){
		return this.word.getText();
	}
	

	public void testMethod(String str){
		word.setText(str);
	}
	
	private void createTable(){
		String[] columnNames = {"Property", "Value"};

		Object[][] data = {
				{"Word:", "-"},//+w.toString()},
				{"Word Language", "-"},//+findLanguage(textPanel.getText()) == LanguageCode.GR?"Greek":"English"},
				{"User Compatible", "-"},//+findLanguage(textPanel.getText()) == user.getDetails().getLanguage()?"True":"False"},
				{"# Letters:", "-"},//+w.getLength()},
				{"# Syllables:", "-"},//+w.getNumberOfSyllables()},
				{"Phonetics:", "-"},//+w.getPhonetics()},
				{"Syllables:", "-"},//+w.getWordInToSyllables()},
				{"CV Form:", "-"},//+w.getCVForm()},
				{"Total Hits:", "-"},//+cls.getUserProblemsToText().getTotalHits()},
				{"User Hits:", "-"},//+cls.getUserProblemsToText().getUserHits()},
				{"Word Difficutly:", "???"}
		};

		resultsTable = new JTable(data, columnNames);
        TableColumn column = resultsTable.getColumnModel().getColumn(1);
        column.setPreferredWidth(resultsTable.getWidth()/4);
        scrollPane = new JScrollPane(resultsTable);
        resultsTable.setFillsViewportHeight(true);
	}
}
