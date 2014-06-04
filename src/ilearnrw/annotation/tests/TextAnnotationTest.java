package ilearnrw.annotation.tests;

import ilearnrw.datalogger.IUserAdministration.AuthenticationException;
import ilearnrw.datalogger.UserStore;
import ilearnrw.user.User;
import ilearnrw.user.profile.UserProfile;
import ilearnrw.utils.LanguageCode;
import ilearnrw.textadaptation.TextAnnotationModule;
import ilearnrw.textadaptation.PresentationRulesModule;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

public class TextAnnotationTest extends JFrame {
	private static final long serialVersionUID = 1L;
	private LanguageCode lc;

	private JPanel contentPane;
	private JLabel languageLabel;
	private	UserProblemsMatrix userProblemsMatrix;
	private User user;
	
	private TextAnnotationModule txModule;
	private PresentationRulesModule rulesModule;
	private Mediator m;
	
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
					TextAnnotationTest frame = new TextAnnotationTest();
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
	public TextAnnotationTest() {

		languageLabel = new JLabel();

		m = new Mediator();
		
		/*Fill the user ComboBox*/
		try {
			for( User u : mUserStore.getAllUsers() ){
				mUserStore.update(u);
			}

			for( User u : mUserStore.getAllUsers() )
				userCombobox.addItem(new UserListBoxWrapper(u));

			/*Select the first user.*/
			user = mUserStore.getAllUsers().get(0);
			userCombobox.setSelectedIndex(0);
			updateLanguageLabel();

			m.setProfile(user.getProfile());
			this.txModule = new TextAnnotationModule("C:\\Users\\Fouli\\Desktop\\Input-2.html", user.getProfile());
			//System.out.println(this.txModule.getTextFile());
			this.rulesModule = new PresentationRulesModule(user.getProfile());
			m.setTextAnnotationModule(txModule);
			m.setPresentationRulesModule(rulesModule);
			
			System.out.println(this.rulesModule.getRulesTable()[0][1]);
			
			
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

				userProblemsMatrix.updateUser();
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
					userProblemsMatrix.setUser(user);
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

		userProblemsMatrix = new UserProblemsMatrix(user, m);
		userProblemsMatrix.setColorMode(getColorMode());


		// TODO change the 2 following rows / send them inside the UserSeveritiesHeatMapPanel class
		userProblemsMatrix.draw();
		userProblemsMatrix.test();

		contentPane.add(userProblemsMatrix, BorderLayout.CENTER );


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



		JButton saveButton = new JButton("Save User");
		saveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				userProblemsMatrix.updateUser();
				/* Store any changes to user*/
				mUserStore.update(user);
				try {
					for( User u : mUserStore.getAllUsers() )
						if(u.getUserId() == user.getUserId() )
							user = u;
				} catch (AuthenticationException e1) {
					e1.printStackTrace();
				}
				userProblemsMatrix.setUser(user);
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
