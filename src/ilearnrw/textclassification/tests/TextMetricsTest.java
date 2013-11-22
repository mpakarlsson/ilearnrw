package ilearnrw.textclassification.tests;

import ilearnrw.datalogger.UserStore;
import ilearnrw.prototype.application.Program;
import ilearnrw.textclassification.Classifier;
import ilearnrw.textclassification.Text;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.WordVsProblems;
import ilearnrw.textclassification.english.EnglishWord;
import ilearnrw.textclassification.greek.GreekWord;
import ilearnrw.textclassification.tests.panels.FilesExplorerPanel;
import ilearnrw.textclassification.tests.panels.HeatMapPanel;
import ilearnrw.textclassification.tests.panels.TextPanel;
import ilearnrw.textclassification.tests.panels.UserSeveritiesHeatMapPanel;
import ilearnrw.user.User;
import ilearnrw.utils.LanguageCode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class TextMetricsTest extends JFrame {
	private static final long serialVersionUID = 1L;
	private LanguageCode lc;
	private Text txt;
	private Classifier cls;

	private JPanel contentPane;
	private JLabel languageLabel;
	private	JTabbedPane tabbedPane;
	private TextPanel textPanel;
	private FilesExplorerPanel explorerPanel;
	//private	HeatMapPanel heatMapPanel;
	private	UserSeveritiesHeatMapPanel userSeveritiesPanel;
	private User user;

	private static UserStore mUserStore = null;
	final class UserListBoxWrapper {
		public UserListBoxWrapper(User u) {
			user = u;
		}
		public int getUserId() { return user.getUserId(); }
		@Override
		public String toString() {
			return user.getDetails().getUsername();
		}
		User user;
	};
	private JComboBox userCombobox = new JComboBox();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			/* Load the user database.*/
			String databaseFile = Program.getStringArg("--db", args);
			mUserStore = new UserStore(databaseFile);
			/* We have to auth as admin to access the database.*/
			mUserStore.authenticateAdmin("ilearn");
		} catch (Exception e) {
			/* Could not load any users, no point to continue.*/
			e.printStackTrace();
			return;
		}
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

		try { 
	        UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
	    } 
	    catch(Exception e){ 
	    }

		languageLabel = new JLabel();
		
		/*Fill the user ComboBox*/
		try {
			for( User u : mUserStore.getAllUsers() )
				userCombobox.addItem(new UserListBoxWrapper(u));
			
			/*Select the first user.*/
			user = mUserStore.getAllUsers().get(0);
			userCombobox.setSelectedIndex(0);
			updateLanguageLabel();
		} catch (Exception ex){
			ex.printStackTrace();
		}
		userCombobox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/* Selected user has changed*/
				UserListBoxWrapper selectedUser = (UserListBoxWrapper) userCombobox.getSelectedItem();
				if(selectedUser.getUserId() == user.getUserId())
					/*Same as already selected*/
					return;

				userSeveritiesPanel.updateUser();
				/* Store any changes to user*/
				mUserStore.update(user);
				try {
					/* Change user to reflect the new user.
					 * Note, we get the user fresh from the UserStore
					 * and do not reuse the one stored in the ComboBox.
					 */
					for( User u : mUserStore.getAllUsers() )
						if(u.getUserId() == selectedUser.getUserId() )
							user = u;
					updateLanguageLabel();
					textPanel.reset(user);
					userSeveritiesPanel.setUser(user);
					explorerPanel.setUser(user);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		setTitle( "iLearnRW Demo Application" );
		setSize( 300, 200 );
		setBackground( Color.gray );
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		textPanel = new TextPanel(user);
		//heatMapPanel = new HeatMapPanel();
		userSeveritiesPanel = new UserSeveritiesHeatMapPanel(user);
		
		contentPane.add(textPanel, BorderLayout.CENTER);
		

		tabbedPane = new JTabbedPane();
		tabbedPane.addTab( "Text & Metrics", textPanel );
		//tabbedPane.addTab( "Heat Map", heatMapPanel );
		explorerPanel = new FilesExplorerPanel(user, tabbedPane, textPanel, this);
		tabbedPane.addTab( "File Explorer", explorerPanel );
		tabbedPane.addTab( "User Severities", userSeveritiesPanel );
		
		// TODO change the 2 following rows / send them inside the UserSeveritiesHeatMapPanel class
		userSeveritiesPanel.draw();
		userSeveritiesPanel.test();

		contentPane.add(tabbedPane, BorderLayout.CENTER );
		
		
		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		toolBar.add(userCombobox);
		
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
				if (!textPanel.getText().trim().isEmpty())
					classifierResults();
			}
		});
		toolBar.add(goButton);
		
		JButton clearButton = new JButton("Clear");
		clearButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textPanel.reset(user);
			}
		});
		toolBar.add(clearButton);

		updateLanguageLabel();
		toolBar.add(languageLabel);
	}
	
	private boolean updateLanguageLabel(){
		boolean result = false;
		if (lc == null || lc!=user.getDetails().getLanguage())
			result = true;
		lc = user.getDetails().getLanguage();
		if (lc == LanguageCode.EN){
			languageLabel.setText(" Language | EN | ");
		}
		else{
			languageLabel.setText(" Language | GR | ");
		}
		return result;
	}
	
	public void classifierResults(){
		String str = textPanel.getText();
		txt = new Text(str, lc);
		
		runTextClassifier();
		textPanel.getSmallHeatMapPanel().test();
		if (textPanelIsWord()){
			textPanel.setResultsTable(testWordMetrics());
		}
		else{
			textPanel.setResultsTable(testTextMetrics());
		}
	}
	
	private String[][] testTextMetrics(){
		
		HashMap<Word, Integer> hs = txt.getWordsFreq();
		System.out.println("Text Language -- "+txt.getLanguageCode());
		
		Object tmp[] = hs.keySet().toArray();
		Word w = (Word)tmp[0];
		System.out.println(w.toString()+ " -- "+w.getLanguageCode());
		WordVsProblems wp = new WordVsProblems(w.getLanguageCode());
		wp.insertWord(w);
		System.out.println(wp.toString());
		
		String[][] data = {
				{"# Paragraphs", ""+txt.getNumberOfParagraphs()},
				{"# Sentences:", ""+txt.getNumberOfSentences()},
				{"# Words:", ""+txt.getNumberOfWords()},
				{"# Distinct Words", ""+txt.getNumberOfDistinctWords()},
				{"# Syllables:", ""+txt.getNumberOfSyllables()},
				{"# Big Sentences (>=15 words)", ""+txt.getNumberOfBigSentences()},
				{"# Polysyllabic Words (>=3 syllables)", ""+txt.getNumberOfPolysyllabicWords()},
				{"# Letters and Numbers", ""+txt.getNumberOfLettersAndNumbers()},
				{"Longest Word Length", ""+txt.getLongestWordLength()},
				{"Longest Sentence Length", ""+txt.getLongestSentenceLength()},
				{"Avg Words per Sentence", String.format("%.2f",txt.getWordsPerSentence())},
				{"Avg Syllables per Word", String.format("%.2f",txt.getSyllablesPerWord())},
				{"Avg Word Length", String.format("%.2f",txt.getAverageWordLength())},
				{"Avg Longest Word Length", String.format("%.2f",txt.getAverageLongestWordLength())},
				{"Flesch", String.format("%.2f",txt.flesch())},
				{"Flesch-Kincaid", String.format("%.2f",txt.fleschKincaid())},
				{"Automated", String.format("%.2f",txt.automated())},
				{"Coleman-Liau", String.format("%.2f",txt.colemanLiau())},
				{"SMOG", String.format("%.2f",txt.smog())},
				{"Gunning FOG", String.format("%.2f",txt.gunningFog())},
				{"Dale-Chall", String.format("%.2f",txt.daleChall())},
				{"Formula", cls.getDifficultyToString()},
				{"iLearnRW", String.format("%.2f",cls.getDifficulty())}
		};
		return data;
	}
	
	private String[][] testWordMetrics(){
		
		HashMap<Word, Integer> hs = txt.getWordsFreq();
		System.out.println("Text Language -- "+txt.getLanguageCode());
		
		Object tmp[] = hs.keySet().toArray();
		Word w = (Word)tmp[0];
		System.out.println(w.toString()+ " -- "+w.getLanguageCode());
		WordVsProblems wp = new WordVsProblems(w.getLanguageCode());
		wp.insertWord(w);
		System.out.println(wp.toString());
		
		String[][] data = {
				{"Word:", ""+w.toString()},
				{"Word Language", findLanguage(textPanel.getText()) == LanguageCode.GR?"Greek":"English"},
				{"User Compatible", findLanguage(textPanel.getText()) == user.getDetails().getLanguage()?"True":"False"},
				{"# Letters:", ""+w.getLength()},
				{"# Syllables:", ""+w.getNumberOfSyllables()},
				{"Phonetics:", ""+w.getPhonetics()},
				{"Syllables:", ""+w.getWordInToSyllables()},
				{"CV Form:", ""+w.getCVForm()},
				{"Total Hits:", ""+cls.getUserProblemsToText().getTotalHits()},
				{"User Hits:", ""+cls.getUserProblemsToText().getUserHits()},
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
		return data;
	}
	
	public void runTextClassifier(){
		Text t = new Text(textPanel.getText(), lc);
		cls = new Classifier(user, t);
		//heatMapPanel.setClassifier(cls);
		textPanel.getSmallHeatMapPanel().setClassifier(cls);
		cls.test();
	}

	private boolean textPanelIsWord(){
		String t = textPanel.getText().trim();
		return !t.contains(" ");		
	}
	
	private LanguageCode findLanguage(String text){
		char[] lowerCase = {'α', 'β', 'γ', 
	    		'δ', 'ε', 'ζ', 'η', 'θ', 
	    		'ι', 'κ', 'λ', 'μ', 'ν', 
	    		'ξ', 'ο', 'π', 'ρ', 'σ', 
	    		'τ', 'υ', 'φ', 'χ', 'ψ', 
	    		'ω', 'ά', 'έ', 'ή', 'ί', 
	    		'ό', 'ύ', 'ώ', 'ϊ', 'ϋ', 
	    		'ΐ','ΰ', 'ς'};
	    char[] upperCase = {'Α', 'Β', 'Γ', 
	    		'Δ', 'Ε', 'Ζ', 'Η', 'Θ', 
	    		'Ι', 'Κ', 'Λ', 'Μ', 'Ν', 
	    		'Ξ', 'Ο', 'Π', 'Ρ', 'Σ', 
	    		'Τ', 'Υ', 'Φ', 'Χ', 'Ψ', 
	    		'Ω', 'Ά', 'Έ', 'Ή', 'Ί', 
	    		'Ό', 'Ύ', 'Ώ', 'Ϊ', 'Ϋ' };
	    for (char c : lowerCase){
	    	if (text.contains(""+c))
	    		return LanguageCode.GR;
	    }
	    for (char c : upperCase){
	    	if (text.contains(""+c))
	    		return LanguageCode.GR;
	    }
	    return LanguageCode.EN;
	}
}
