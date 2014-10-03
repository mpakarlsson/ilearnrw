package ilearnrw.textclassification.tests;

import ilearnrw.datalogger.IUserAdministration.AuthenticationException;
import ilearnrw.datalogger.UserStore;
import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.languagetools.english.EnglishLanguageAnalyzer;
import ilearnrw.languagetools.greek.GreekLanguageAnalyzer;
import ilearnrw.textclassification.TextClassificationResults;
import ilearnrw.textclassification.Text;
import ilearnrw.textclassification.UserProblemsToText;
import ilearnrw.textclassification.UserProblemsToWord;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.WordClassificationResults;
import ilearnrw.textclassification.english.EnglishWord;
import ilearnrw.textclassification.greek.GreekWord;
import ilearnrw.textclassification.tests.panels.FilesExplorerPanel;
import ilearnrw.textclassification.tests.panels.TextPanel;
import ilearnrw.textclassification.tests.panels.UserProblemsHeatMapPanel;
import ilearnrw.textclassification.tests.panels.WordPanel;
import ilearnrw.user.User;
import ilearnrw.user.problems.ProblemDefinitionIndex;
import ilearnrw.user.profile.UserProfile;
import ilearnrw.user.profile.UserSeverities;
import ilearnrw.utils.LanguageCode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

public class TextMetricsTest extends JFrame implements TextMetricsGUI{
	private static final long serialVersionUID = 1L;
	private LanguageCode lc;
	private UserProblemsToText upt;
	private UserProblemsToWord upw;
	private TextClassificationResults tcr;
	private WordClassificationResults wcr;

	private JPanel contentPane;
	private JLabel languageLabel;
	private	JTabbedPane tabbedPane;
	private TextPanel textPanel;
	private WordPanel wordPanel;
	private FilesExplorerPanel explorerPanel;
	private GreekLanguageAnalyzer greekAnalyzer;
	private EnglishLanguageAnalyzer englishAnalyzer;
	private	UserProblemsHeatMapPanel userProblemsPanel;
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

		greekAnalyzer = GreekLanguageAnalyzer.getInstance();
		englishAnalyzer = EnglishLanguageAnalyzer.getInstance();

		languageLabel = new JLabel();
		
		/*Fill the user ComboBox*/
		try {
			for( User u : mUserStore.getAllUsers() ){
				/*if (u.getDetails().getUsername().contains("_Inter")){
					Random rand = new Random();
					ProblemDefinitionIndex problems = u.getProfile().getUserProblems().getProblems();
					UserSeverities userSeverities = u.getProfile().getUserProblems().getUserSeverities();
					for (int i=0;i<problems.getIndexLength(); i++){
						int wi =2*problems.getRowLength(i)/4 + rand.nextInt(1);
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
				}*/

				mUserStore.update(u);
			}
			
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

				userProblemsPanel.updateUser();
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
					textPanel.reset(user.getProfile());
					wordPanel.reset(user.getProfile());
					userProblemsPanel.setUser(user);
					explorerPanel.setLanguageAnalyzer(getLanguageAnalyzer(user.getProfile()));
					explorerPanel.setUser(user.getProfile());
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

		textPanel = new TextPanel(user.getProfile());
		wordPanel = new WordPanel(user.getProfile(), this);
		userProblemsPanel = new UserProblemsHeatMapPanel(user);
		userProblemsPanel.setColorMode(getColorMode());
		textPanel.setColorMode(getColorMode());
		wordPanel.setColorMode(getColorMode());
		
		contentPane.add(textPanel, BorderLayout.CENTER);
		

		tabbedPane = new JTabbedPane();
		tabbedPane.addTab( "Text & Metrics", textPanel );
		tabbedPane.addTab( "Word & Metrics", wordPanel );
		//tabbedPane.addTab( "Heat Map", heatMapPanel );
		explorerPanel = new FilesExplorerPanel(user.getProfile(), tabbedPane, textPanel, this, getLanguageAnalyzer(user.getProfile()));
		tabbedPane.addTab( "File Explorer", explorerPanel );
		tabbedPane.addTab( "User Profile", userProblemsPanel );
		
		// TODO change the 2 following rows / send them inside the UserSeveritiesHeatMapPanel class
		userProblemsPanel.draw();
		userProblemsPanel.test();

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
				textPanel.reset(user.getProfile());
				wordPanel.reset(user.getProfile());
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
					userProblemsPanel.updateUser();
					/* Store any changes to user*/
					mUserStore.update(user);
					try {
						for( User u : mUserStore.getAllUsers() )
							if(u.getUserId() == user.getUserId() )
								user = u;
					} catch (AuthenticationException e1) {
						e1.printStackTrace();
					}
					userProblemsPanel.setUser(user);
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
		if (lc == null || lc!=user.getProfile().getLanguage())
			result = true;
		lc = user.getProfile().getLanguage();
		if (lc == LanguageCode.EN){
			languageLabel.setText(" Language | EN | ");
		}
		else{
			languageLabel.setText(" Language | GR | ");
		}
		return result;
	}
	
	public void classifierResults(boolean isWord){		
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
				{"# Paragraphs", ""+tcr.getNumberOfParagraphs(), 
					"Longest Word Length:", ""+tcr.getLongestWordLength(), 
					"Flesch:", String.format("%.2f",tcr.getFlesch())},
				{"# Sentences:", ""+tcr.getNumberOfSentences(), 
						"Longest Sentence Length:", ""+tcr.getLongestSentenceLength(), 
						"Flesch-Kincaid:", String.format("%.2f",tcr.getFleschKincaid())},
				{"# Words:", ""+tcr.getNumberOfTotalWords(), 
							"Avg Words per Sentence:", String.format("%.2f",tcr.getAverageWordsPerSentence()), 
							"Automated:", String.format("%.2f",tcr.getAutomated())},
				{"# Distinct Words", ""+tcr.getNumberOfDistinctWords(), 
					"Avg Syllables per Word:", String.format("%.2f",tcr.getAverageSyllablesPerWord()), 
					"Coleman-Liau:", String.format("%.2f",tcr.getColemanLiau())},
				{"# Syllables:", ""+tcr.getNumberOfSyllables(), 
					"Avg Word Length:", String.format("%.2f",tcr.getAverageWordLength()), 
					"SMOG:", String.format("%.2f",tcr.getSmog())},
				{"# Big Sentences:", ""+tcr.getNumberOfBigSentences(), 
					"Avg Longest Word Length:", String.format("%.2f",tcr.getAverageLongestWordLength()), 
					"Gunning FOG:", String.format("%.2f",tcr.getGunningFog())},
				{"# Polysyllabic Words:", ""+tcr.getNumberOfPolysyllabicWords(), 
					"", "", 
					""},
				{"# Letters and Numbers:", ""+tcr.getNumberOfLettersAndNumbers(), 
					"", "", 
					"Text Score:", String.format("%.2f",tcr.getTscore())}
		};
		return data;
	}
	
	private String[][] testWordMetrics(){
		String[][] data = {
				{"Word:", ""+wcr.getWord()},
				{"Word Language", findLanguage(wordPanel.getText()) == LanguageCode.GR?"Greek":"English"},
				{"User Compatible", findLanguage(wordPanel.getText()) == user.getProfile().getLanguage()?"True":"False"},
				{"# Letters:", ""+wcr.getLength()},
				{"# Syllables:", ""+wcr.getNumberOfSyllables()},
				{"Phonetics:", ""+wcr.getPhonetics()},
				{"Syllables:", ""+wcr.getWordInSyllables()},
				{"CV Form:", ""+wcr.getCvForm()},
				{"Total Hits:", ""+wcr.getTotalHits()},
				{"User Hits:", ""+wcr.getUserHits()},
				{"Word Score:", ""+wcr.getWscore()},
				{"is Difficult:", ""+wcr.isDifficult()},
				{"is Very Difficult:", ""+wcr.isVeryDifficult()}
		};
		return data;
	}
	
	public void runTextClassifier(boolean isWord){
		if (isWord){
			Word ww;
			if (lc==LanguageCode.GR)
				ww = new GreekWord(wordPanel.getText());
			else {
				String word = wordPanel.getText();
				if(EnglishLanguageAnalyzer.getInstance().getDictionary().getDictionary().containsKey(word))
					ww = EnglishLanguageAnalyzer.getInstance().getDictionary().getDictionary().get(word);
				else
					ww = new EnglishWord(word);
			
			}
			upw = new UserProblemsToWord(user.getProfile(), ww, getLanguageAnalyzer(user.getProfile()));
			wcr = upw.getWordClassificationResults();
			wordPanel.getSmallHeatMapPanel().setClassifier(wcr);
		}
		else{
			upt = new UserProblemsToText(user.getProfile(), new Text(textPanel.getText(), lc), 
					getLanguageAnalyzer(user.getProfile()));
			tcr = upt.getTextClassificationResults();
			textPanel.getSmallHeatMapPanel().setClassifier(tcr);
		}
	}

	private LanguageAnalyzerAPI getLanguageAnalyzer(UserProfile userProfile){
		if (userProfile.getLanguage() == LanguageCode.GR)
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
	
	private int getColorMode(){
  	  String t = "";
  	  try {
  		  t = new Scanner( new File("params/colors.txt")).useDelimiter("\\A").next();
  	  } catch (FileNotFoundException e1) {
  		  System.out.println(e1.toString());
  		  return 0;
  	  }
  	  if (t.contains("1"))
  		  return 1;
  	  return 0;
	}
}
