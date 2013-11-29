package ilearnrw.textclassification.tests.panels;

import javax.swing.*;

import java.awt.*;
public class BackgroundImageJFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	public BackgroundImageJFrame(){
		this.setUndecorated(true);
		//setSize(400,300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		setLayout(new BorderLayout());
		JLabel background=new JLabel(new ImageIcon("data/ilearnLogo.png"));
		add(background);
		background.setLayout(new FlowLayout());

	    ImageIcon loading = new ImageIcon("data/ajax-loader.gif");
	    background.add(new JLabel("loading... ", loading, JLabel.CENTER), BorderLayout.CENTER);
	    this.pack();
	}

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 300);
    }
	public static void main(String args[]){
		new BackgroundImageJFrame();
	}
}