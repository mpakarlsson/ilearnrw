package ilearnrw.textclassification.tests;

import ilearnrw.datalogger.UserStore;
import ilearnrw.prototype.application.Program;
import ilearnrw.textclassification.Classifier;
import ilearnrw.textclassification.Text;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.WordVsProblems;
import ilearnrw.textclassification.tests.panels.HeatMapPanel;
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
import javax.swing.JToolBar;
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
					System.out.println(user.getDetails().getUsername());
					System.out.println(user.getProfile().getUserSeveritiesToProblems().toString());
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
		
		textPanel = new TextPanel();
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

		contentPane.add( tabbedPane, BorderLayout.CENTER );
		
		
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
				textPanel.setResultsText(testTextMetrics());
				if (firstTime){
					runTheClassifier();
					heatMapPanel.draw();
					heatMapPanel.test();
					firstTime = false;
				}
				else{
					runTheClassifier();
					heatMapPanel.draw();
					heatMapPanel.test();
				}
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
	
	private String testTextMetrics(){
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
		wp.insertWord(w);
		System.out.println(wp.toString());
		
		res = res+"</p></html>";
		return res;
	}
	
	public void runTheClassifier(){
		Text t = new Text(textPanel.getText(), lc);
		cls = new Classifier(user, t);
		heatMapPanel.setClassifier(cls);
		cls.test();
		//return cls.getUserProblemsToText().getUserCounters().getCounters();
	}

}
