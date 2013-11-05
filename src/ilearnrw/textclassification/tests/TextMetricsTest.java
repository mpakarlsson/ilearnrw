package ilearnrw.textclassification.tests;

import ilearnrw.textclassification.Sentence;
import ilearnrw.textclassification.Text;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.WordVsProblems;
import ilearnrw.user.LanguageCode;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextPane;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;

public class TextMetricsTest extends JFrame {
	
	LanguageCode lc;
	static Text txt;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TextMetricsTest frame = new TextMetricsTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TextMetricsTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);
        splitPane.setResizeWeight(0.75);
        
        final JTextPane textPane = new JTextPane();
        splitPane.setLeftComponent(textPane);
        
        JPanel panel = new JPanel();
        splitPane.setRightComponent(panel);
        panel.setLayout(new GridLayout(1,1));
        
        final JTextPane resultsPane = new JTextPane();
        resultsPane.setEditable(false);
        resultsPane.setBackground(Color.lightGray);
        panel.add(resultsPane);
        resultsPane.setText("Paste Your Text to The Left.\nSwitch Language if needed.");
		
		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		JButton exitButton = new JButton("Exit");
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		toolBar.add(exitButton);
		
		JButton goButton = new JButton("Calculate");
		goButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String str = textPane.getText();
				txt = new Text(str, lc);
				resultsPane.setContentType("text/html");
				resultsPane.setText(testTextMetrics());
			}
		});
		toolBar.add(goButton);

		lc = LanguageCode.GR;
		final JLabel languageLabel = new JLabel(" Text Language | GR | ");
		toolBar.add(languageLabel);
		
		JButton switchLanguageButton = new JButton("Switch Language");
		switchLanguageButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (lc == LanguageCode.GR){
					lc = LanguageCode.EN;
					languageLabel.setText(" Text Language | EN | ");
				}
				else{
					lc = LanguageCode.GR;
					languageLabel.setText(" Text Language | GR | ");
				}
			}
		});
		toolBar.add(switchLanguageButton);
	}
	
	private static String testTextMetrics(){
		String res = "<html> <p style=\"font-size:10px; font-weight:bold;\">";
		
		res = res + "Number of sentences:"+txt.getNumberOfSentences();

		res = res + "<br>Number of words:"+txt.getNumberOfWords();
		
		res = res + "<br>Number of Distinct Words:"+txt.getNumberOfDistinctWords();

		res = res + "<br>Number of Syllables:"+txt.getNumberOfSyllables();

		res = res + "<br>Number of Big Sentences (>=15 words):"+txt.getNumberOfBigSentences();

		res = res + "<br>Number of Polysyllabic Words (>=3 syllables):"+txt.getNumberOfPolysyllabicWords();
		
		res = res + "<br>Total Number of Letters and Numbers:"+txt.getNumberOfLettersAndNumbers();
		
		res = res + "<br>Longest Word Length:"+txt.getLongestWordLength();

		res = res + "<br>Longest Sentence Length:"+txt.getLongestSentenceLength();

		res = res + "<br>Average Words per Sentence:"+txt.getWordsPerSentence();

		res = res + "<br>Average Syllables per Word:"+txt.getSyllablesPerWord();

		res = res + "<br>Average Word Length:"+txt.getAverageWordLength();

		res = res + "<br>Average Longest Word Length:"+txt.getAverageLongestWordLength();
		
		res = res + "<br><br>Readability Tests<br>";

		res = res + "<br>Flesch:"+txt.flesch();

		res = res + "<br>Flesch-Kincaid:"+txt.fleschKincaid();

		res = res + "<br>Automated:"+txt.automated();
		
		res = res + "<br>Coleman-Liau:"+txt.colemanLiau();

		res = res + "<br>SMOG:"+txt.smog();
		
		res = res + "<br>Gunning FOG:"+txt.gunningFog();
		
		res = res + "<br>Dale-Chall:"+txt.daleChall();
		
		HashMap<Word, Integer> hs = txt.getWordsFreq();
		
		Object tmp[] = hs.keySet().toArray();
		Word w = (Word)tmp[0];
		System.out.println(w.toString());
		WordVsProblems wp = new WordVsProblems(w.getLanguageCode());
		System.out.println(wp.toString());
		
		res = res+"</p></html>";
		return res;
	}

}
