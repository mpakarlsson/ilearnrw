package ilearnrw.textclassification.tests;

import ilearnrw.datalogger.UserStore;
import ilearnrw.prototype.application.Program;
import ilearnrw.textclassification.Classifier;
import ilearnrw.textclassification.Text;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.WordVsProblems;
import ilearnrw.textclassification.tests.panels.HeatMapPanel;
import ilearnrw.textclassification.tests.panels.SmallHeatMapPanel;
import ilearnrw.textclassification.tests.panels.TextPanel;
import ilearnrw.textclassification.tests.panels.UserSeveritiesHeatMapPanel;
import ilearnrw.user.User;
import ilearnrw.user.profile.UserSeverities;
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
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class TextMetricsTest extends JFrame {
	private static final long serialVersionUID = 1L;
	private LanguageCode lc;
	private Text txt;
	private Classifier cls;

	private JPanel contentPane;
	private	JTabbedPane tabbedPane;
	private TextPanel textPanel;
	private	HeatMapPanel heatMapPanel;
	private	UserSeveritiesHeatMapPanel userSeveritiesPanel;
	private User user;
	private boolean firstTime = true;

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
		
		
		/*Fill the user ComboBox*/
		try {
			for( User u : mUserStore.getAllUsers() )
				userCombobox.addItem(new UserListBoxWrapper(u));
			
			/*Select the first user.*/
			user = mUserStore.getAllUsers().get(0);
			userCombobox.setSelectedIndex(0);
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
					userSeveritiesPanel.setUser(user);
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
		heatMapPanel = new HeatMapPanel();
		userSeveritiesPanel = new UserSeveritiesHeatMapPanel(user);
		
		contentPane.add(textPanel, BorderLayout.CENTER);
		

		tabbedPane = new JTabbedPane();
		tabbedPane.addTab( "Text", textPanel );
		tabbedPane.addTab( "Heat Map", heatMapPanel );
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
				String str = textPanel.getText();
				txt = new Text(str, lc);
				
				runTheClassifier();
				heatMapPanel.draw();
				heatMapPanel.test();
				//textPanel.getSmallHeatMapPanel().draw();
				textPanel.getSmallHeatMapPanel().test();
				//textPanel.setResultsText(testTextMetrics());
				textPanel.setResultsTable(testTextMetrics());
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
	
	private String[][] testTextMetrics(){
		
		HashMap<Word, Integer> hs = txt.getWordsFreq();
		
		Object tmp[] = hs.keySet().toArray();
		Word w = (Word)tmp[0];
		System.out.println(w.toString());
		WordVsProblems wp = new WordVsProblems(w.getLanguageCode());
		wp.insertWord(w);
		System.out.println(wp.toString());
		//return res;
		
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
	
	public void runTheClassifier(){
		Text t = new Text(textPanel.getText(), lc);
		cls = new Classifier(user, t);
		heatMapPanel.setClassifier(cls);
		textPanel.getSmallHeatMapPanel().setClassifier(cls);
		cls.test();
		//return cls.getUserProblemsToText().getUserCounters().getCounters();
	}

}
