package ilearnrw.textclassification.tests;

import ilearnrw.datalogger.IUserAdministration.AuthenticationException;
import ilearnrw.datalogger.UserStore;
import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.languagetools.english.EnglishLanguageAnalyzer;
import ilearnrw.languagetools.greek.GreekLanguageAnalyzer;
import ilearnrw.prototype.application.Program;
import ilearnrw.textclassification.Classifier;
import ilearnrw.textclassification.Text;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.tests.panels.FilesExplorerPanel;
import ilearnrw.textclassification.tests.panels.TextPanel;
import ilearnrw.textclassification.tests.panels.UserSeveritiesHeatMapPanel;
import ilearnrw.textclassification.tests.panels.WordPanel;
import ilearnrw.user.User;
import ilearnrw.user.problems.ProblemDefinitionIndex;
import ilearnrw.user.profile.UserSeverities;
import ilearnrw.user.profile.UserSeveritiesToProblems;
import ilearnrw.utils.LanguageCode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

public class TextMetricsTest extends JFrame {
	private static final long serialVersionUID = 1L;
	private static String path = "data/";
	private LanguageCode lc;
	private Text txt;
	private Classifier cls;

	private JPanel contentPane;
	private JLabel languageLabel;
	private	JTabbedPane tabbedPane;
	private TextPanel textPanel;
	private WordPanel wordPanel;
	private FilesExplorerPanel explorerPanel;
	private GreekLanguageAnalyzer greekAnalyzer;
	private EnglishLanguageAnalyzer englishAnalyzer;
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
			String databaseFile = "the_users";//Program.getStringArg("--db", args);
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
		/*try { 
	        UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
	    } 
	    catch(Exception e){ 
	    }*/

		greekAnalyzer = new GreekLanguageAnalyzer();
		englishAnalyzer = new EnglishLanguageAnalyzer();

		languageLabel = new JLabel();
		
		/*Fill the user ComboBox*/
		try {
			/*for( User u : mUserStore.getAllUsers() ){
				if (u.getDetails().getUsername().equals("greek_few_problems")){
					Random rand = new Random();
					ProblemDefinitionIndex problems = u.getProfile().getUserSeveritiesToProblems().getProblems();
					UserSeverities userSeverities = u.getProfile().getUserSeveritiesToProblems().getUserSeverities();
					for (int i=0;i<problems.getIndexLength(); i++){
						int wi = 2*problems.getRowLength(i)/3 + rand.nextInt(3);
						userSeverities.setWorkingIndex(i, wi);
						for (int j=0; j<userSeverities.getSeverityLength(i); j++){
							if (j<wi/2)
								userSeverities.setSeverity(i, j, 0);// rand.nextInt(2));
							else if(j<wi)
								userSeverities.setSeverity(i, j, 1);// rand.nextInt(3));
							else if (j<(wi+userSeverities.getSeverityLength(i))/2)
								userSeverities.setSeverity(i, j, 2);// rand.nextInt(4));
							else 
								userSeverities.setSeverity(i, j, 3);//  rand.nextInt(3)+1);
						}
					}
				}

				mUserStore.update(u);
			}*/
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
					wordPanel.reset(user);
					userSeveritiesPanel.setUser(user);
					explorerPanel.setLanguageAnalyzer(getLanguageAnalyzer(user));
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
		wordPanel = new WordPanel(user, this);
		userSeveritiesPanel = new UserSeveritiesHeatMapPanel(user);
		
		contentPane.add(textPanel, BorderLayout.CENTER);
		

		tabbedPane = new JTabbedPane();
		tabbedPane.addTab( "Text & Metrics", textPanel );
		tabbedPane.addTab( "Word & Metrics", wordPanel );
		//tabbedPane.addTab( "Heat Map", heatMapPanel );
		explorerPanel = new FilesExplorerPanel(user, tabbedPane, textPanel, this, getLanguageAnalyzer(user));
		tabbedPane.addTab( "File Explorer", explorerPanel );
		tabbedPane.addTab( "User Profile", userSeveritiesPanel );
		
		// TODO change the 2 following rows / send them inside the UserSeveritiesHeatMapPanel class
		userSeveritiesPanel.draw();
		userSeveritiesPanel.test();

		contentPane.add(tabbedPane, BorderLayout.CENTER );
		
		
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
		toolBar.addSeparator();
		
		JButton clearButton = new JButton("Clear");
		clearButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textPanel.reset(user);
				wordPanel.reset(user);
			}
		});
		toolBar.add(clearButton);  
		
        JButton goButton = new JButton("Calculate");
        goButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                        if (!textPanel.getText().trim().isEmpty())
                                classifierResults(false);
                }
        });
        toolBar.add(goButton);
		
		JButton saveButton = new JButton("Save User");
		saveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals("User Profile")){
					userSeveritiesPanel.updateUser();
					/* Store any changes to user*/
					mUserStore.update(user);
					try {
						for( User u : mUserStore.getAllUsers() )
							if(u.getUserId() == user.getUserId() )
								user = u;
					} catch (AuthenticationException e1) {
						e1.printStackTrace();
					}
					userSeveritiesPanel.setUser(user);
				}
			}
		});
		toolBar.add(saveButton);

		toolBar.addSeparator();
		
		toolBar.add(new JLabel("Select User:"));
		toolBar.add(userCombobox);
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
	
	public void classifierResults(boolean isWord){
		String str;
		if (isWord)
			str = wordPanel.getText();
		else
			str = textPanel.getText();
		txt = new Text(str, lc);
		
		runTextClassifier(isWord);
		if (isWord){
			wordPanel.getSmallHeatMapPanel().test();
			wordPanel.setResultsTable(testWordMetrics());
		}
		else{
			textPanel.getSmallHeatMapPanel().test();
			textPanel.setResultsTable(testTextMetrics());
		}
	}
	
	private String[][] testTextMetrics(){
		/*
		HashMap<Word, Integer> hs = txt.getWordsFreq();
		System.out.println("Text Language -- "+txt.getLanguageCode());
		
		Object tmp[] = hs.keySet().toArray();
		Word w = (Word)tmp[0];
		System.out.println(w.toString()+ " -- "+w.getLanguageCode());
		WordVsProblems wp = new WordVsProblems(w.getLanguageCode());
		wp.insertWord(w);
		System.out.println(wp.toString());
		*/
		String[][] data = {
				{"# Paragraphs", ""+txt.getNumberOfParagraphs(), 
					"Longest Word Length:", ""+txt.getLongestWordLength(), 
					"Flesch:", String.format("%.2f",txt.flesch())},
				{"# Sentences:", ""+txt.getNumberOfSentences(), 
						"Longest Sentence Length:", ""+txt.getLongestSentenceLength(), 
						"Flesch-Kincaid:", String.format("%.2f",txt.fleschKincaid())},
				{"# Words:", ""+txt.getNumberOfWords(), 
							"Avg Words per Sentence:", String.format("%.2f",txt.getWordsPerSentence()), 
							"Automated:", String.format("%.2f",txt.automated())},
				{"# Distinct Words", ""+txt.getNumberOfDistinctWords(), 
					"Avg Syllables per Word:", String.format("%.2f",txt.getSyllablesPerWord()), 
					"Coleman-Liau:", String.format("%.2f",txt.colemanLiau())},
				{"# Syllables:", ""+txt.getNumberOfSyllables(), 
					"Avg Word Length:", String.format("%.2f",txt.getAverageWordLength()), 
					"SMOG:", String.format("%.2f",txt.smog())},
				{"# Big Sentences:", ""+txt.getNumberOfBigSentences(), 
					"Avg Longest Word Length:", String.format("%.2f",txt.getAverageLongestWordLength()), 
					"Gunning FOG:", String.format("%.2f",txt.gunningFog())},
				{"# Polysyllabic Words:", ""+txt.getNumberOfPolysyllabicWords(), 
					"", "", 
					"Dale-Chall:", String.format("%.2f",txt.daleChall())},
				{"# Letters and Numbers:", ""+txt.getNumberOfLettersAndNumbers(), 
					"Formula:", cls.getDifficultyToString(), 
					"iLearnRW:", String.format("%.2f",cls.getDifficulty())}
				/*
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
				{"iLearnRW", String.format("%.2f",cls.getDifficulty())}*/
		};
		return data;
	}
	
	private String[][] testWordMetrics(){
		
		HashMap<Word, Integer> hs = txt.getWordsFreq();
		
		Object tmp[] = hs.keySet().toArray();
		Word w = (Word)tmp[0];
		/*System.out.println(w.toString()+ " -- "+w.getLanguageCode());
		WordVsProblems wp = new WordVsProblems(w.getLanguageCode());
		wp.insertWord(w);
		System.out.println(wp.toString());
		*/
		String[][] data = {
				{"Word:", ""+w.toString()},
				{"Word Language", findLanguage(wordPanel.getText()) == LanguageCode.GR?"Greek":"English"},
				{"User Compatible", findLanguage(wordPanel.getText()) == user.getDetails().getLanguage()?"True":"False"},
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
	
	public void runTextClassifier(boolean isWord){
		if (isWord){
			Text t = new Text(wordPanel.getText(), lc);
			cls = new Classifier(user, t, getLanguageAnalyzer(user));
			wordPanel.getSmallHeatMapPanel().setClassifier(cls);
		}
		else{
			Text t = new Text(textPanel.getText(), lc);
			cls = new Classifier(user, t, getLanguageAnalyzer(user));
			//heatMapPanel.setClassifier(cls);
			textPanel.getSmallHeatMapPanel().setClassifier(cls);
		}
	}

	private LanguageAnalyzerAPI getLanguageAnalyzer(User user){
		if (user.getDetails().getLanguage() == LanguageCode.GR)
			return greekAnalyzer;
		else 
			return englishAnalyzer;
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
