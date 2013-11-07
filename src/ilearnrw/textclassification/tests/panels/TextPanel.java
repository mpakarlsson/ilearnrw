package ilearnrw.textclassification.tests.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class TextPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTextPane mainText, resultsText;

	public TextPanel() {
		super();
		mainText = new JTextPane();
		resultsText = new JTextPane();
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		
		JSplitPane splitPane = new JSplitPane();
		this.add(splitPane, BorderLayout.CENTER);
        splitPane.setResizeWeight(0.77);
        
		splitPane.setRightComponent(panel);
        panel.setLayout(new GridLayout(1,1));
        
        mainText = new JTextPane();
        JScrollPane jsp = new JScrollPane(mainText);
        splitPane.setLeftComponent(jsp);
        
        resultsText = new JTextPane();
        resultsText.setEditable(false);
        resultsText.setBackground(Color.lightGray);
        panel.add(resultsText);
        resultsText.setContentType("text/html");
        resultsText.setText("Paste Your Text to The Left.<br>Switch Language if needed.");
	}
	
	public JTextPane getResultsText() {
		return resultsText;
	}

	public void setResultsText(JTextPane resultsText) {
		this.resultsText = resultsText;
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
	
	public void setResultsText(String str){
		this.resultsText.setText(str);
	}

}
