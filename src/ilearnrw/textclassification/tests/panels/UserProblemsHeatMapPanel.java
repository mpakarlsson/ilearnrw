package ilearnrw.textclassification.tests.panels;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.greek.GreekWord;
import ilearnrw.textclassification.tests.panels.userproblems.TrickyWordsPanel;
import ilearnrw.user.User;
import ilearnrw.user.profile.UserSeverities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public class UserProblemsHeatMapPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTable heatMap;
	private JTextPane descriptionsText;
	private User user;
	private int[][] data;
	private int[][] multi;
	private ArrayList<Word> trickyWords;
	private CellRenderer renderer;
	private JSplitPane splitPane;
	private int colorMode = 0;
	private JPanel upperPanel;
	private TrickyWordsPanel trickyPanel;
	private JButton addButton, removeButton;

	public UserProblemsHeatMapPanel(User user){
		this.user = user;

		this.data = copyMatrix(user.getProfile().getUserProblems().getUserSeverities().getSeverities());
		this.trickyWords = user.getProfile().getUserProblems().getTrickyWords();

        descriptionsText = new JTextPane();
        descriptionsText.setEditable(false);
        descriptionsText.setBackground(Color.lightGray);
        
		heatMap = new JTable(data.length,lengthsMax());
		for (int i=0;i<lengthsMax(); i++)
			heatMap.getTableHeader().getColumnModel().getColumn(i).setHeaderValue(""+(i+1));
		
		renderer = new CellRenderer();
		heatMap.setDefaultRenderer(Object.class, renderer);
		heatMap.setShowGrid(false);

		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new GridLayout(1,1));
		
		splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this.add(splitPane, BorderLayout.CENTER);
        splitPane.setResizeWeight(0.77);
		
		upperPanel = new JPanel(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(heatMap);
		upperPanel.add(scrollPane, BorderLayout.CENTER);

		ArrayList<Word> tmp = user.getProfile().getUserProblems().getTrickyWords();
        trickyPanel = new TrickyWordsPanel(tmp.toArray());

        upperPanel.add(trickyPanel, BorderLayout.PAGE_END);
		splitPane.setLeftComponent(upperPanel);
        
		splitPane.setRightComponent(descriptionsText);

	}

	public int getColorMode() {
		return colorMode;
	}

	public void setColorMode(int colorMode) {
		this.colorMode = colorMode;
	}
	
	public void setUser(User user){
		this.user = user;

		this.data = copyMatrix(user.getProfile().getUserProblems().getUserSeverities().getSeverities());
		heatMap = new JTable(data.length,lengthsMax());
		for (int i=0;i<lengthsMax(); i++)
			heatMap.getTableHeader().getColumnModel().getColumn(i).setHeaderValue(""+(i+1)); 
		
		renderer = new CellRenderer();
		heatMap.setDefaultRenderer(Object.class, renderer);
		heatMap.setShowGrid(false);

		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new GridLayout(1,1));
		
		upperPanel = new JPanel(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(heatMap);
		upperPanel.add(scrollPane, BorderLayout.CENTER);

		ArrayList<Word> tmp = user.getProfile().getUserProblems().getTrickyWords();
        trickyPanel = new TrickyWordsPanel(tmp.toArray());

        upperPanel.add(trickyPanel, BorderLayout.PAGE_END);
		splitPane.setLeftComponent(upperPanel);
		
		draw();
		test();
	}
	public void updateUser() {
		if(user == null)
			return;
		UserSeverities severities = user.getProfile().getUserProblems().getUserSeverities();
		for (int i=0;i<multi.length;i++){
			for (int j=0;j<multi[i].length;j++){
				Object o = heatMap.getValueAt(i, j);
				if( o != null )
				{
					try{
					severities.setSeverity(i, j, (Integer) o);
					} catch( Exception ex ) {
						severities.setSeverity(i, j, Integer.parseInt((String) o));
					}
				}
			}
		}
		user.getProfile().getUserProblems().setUserSeverities(severities);
		user.getProfile().getUserProblems().setTrickyWords(trickyPanel.getWords());
	}
	
	
	private void setValues(){
		if(user == null)
			return;
		for (int i=0;i<multi.length;i++){
			for (int j=0;j<multi[i].length;j++){
				if (multi[i][j] != -1)
					heatMap.setValueAt(multi[i][j], i, j);
			}
		}
	}
	
	public void draw(){
		this.data = copyMatrix(user.getProfile().getUserProblems().getUserSeverities().getSeverities());
		heatMap.setRowHeight(45);
		heatMap.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
		TableColumn column = null;
	    for (int i = 0; i < heatMap.getColumnCount(); i++) {
	        column = heatMap.getColumnModel().getColumn(i);
	        column.setPreferredWidth(45);
	    }  
		createMatrix();
		setValues();

		heatMap.setDefaultRenderer(Object.class, renderer);

		heatMap.repaint();
		heatMap.revalidate();
		
		/*if( heatMap != null )
			this.remove(heatMap);
		if( descriptionsText != null )
			this.remove(descriptionsText);

		heatMap = new JTable(data.length,lengthsMax());
		heatMap.setShowGrid(false);

		descriptionsText = new JTextPane();

		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new BorderLayout(0, 0));

		this.add(new JScrollPane(heatMap), BorderLayout.CENTER);

		this.add(descriptionsText, BorderLayout.SOUTH);
		
		heatMap.setRowHeight(80);*/

	}
	
	public void test(){
	}
	
	private int matrixMax(){
		int max = multi[0][0];
		for (int i=0;i<multi.length;i++){
			for (int j=0;j<multi[0].length;j++){
				if (multi[i][j] > max)
					max = multi[i][j];
			}
		}
		return max;
	}
	
	private int sumsMax(){
		int max = multi[0][0];
		for (int i=0;i<multi.length;i++){
			if (multi[i][0] > max)
				max = multi[i][0];
		}
		return max;
	}
	
	private int lengthsMax(){
		int max = data[0].length;
		for (int i=0;i<data.length;i++){
			if (data[i].length > max)
				max = data[i].length;
		}
		return max;
	}
	
	private void createMatrix(){
		multi = new int[data.length][lengthsMax()];
		for (int i=0;i<multi.length;i++){
			for (int j=0; j < lengthsMax();j++){
				if (j >= data[i].length)
					multi[i][j] = -1;
				else
					multi[i][j] = data[i][j];
			}
		}
	}
	
	private String displayCellInfo(int i, int j){
		String res = "null";
		if (user == null) 
			return res;
		//if (j==0){
		//	res = user.getProfile().getUserSeveritiesToProblems().getProblemDefinition(i).toString();
		//}
		if (j<data[i].length){
			res = "Problem Title:"+user.getProfile().getUserProblems().getProblemDefinition(i).getType().getUrl();
			if (!user.getProfile().getUserProblems().getProblemDefinition(i).getURI().
					equalsIgnoreCase(user.getProfile().getUserProblems().getProblemDefinition(i).getType().getUrl()))
				res = res + ", Targeted Area:"+user.getProfile().getUserProblems().getProblemDefinition(i).getURI();
			//res = user.getProfile().getUserSeveritiesToProblems().getProblemDefinition(i).toString();
			res = res + "\nMatching word characteristics:"+user.getProfile().getUserProblems().getProblemDescription(i, j).getProblemType();
			res = res + " {"+user.getProfile().getUserProblems().getProblemDescription(i, j).getDescriptionsTosString()+"}";
		}
		return res;
	}
	
	private int[][] copyMatrix(int p[][]){
		int tmp[][] = new int[p.length][];
		for (int i=0;i<p.length;i++){
			tmp[i] = new int[p[i].length];
			for (int j=0;j<p[i].length;j++){
				tmp[i][j] = p[i][j];
			}
		}
		return tmp;
	}
	
	
	private class CellRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1L;
		Border unselectedBorder = null;
		Border selectedBorder = null;
		boolean isBordered = false;

		@Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
        		boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (hasFocus){
            	// TODO add a window that displays problem description here!
            	descriptionsText.setText(displayCellInfo(row, column));
            	c.setBackground(Color.black);
            }
            if (multi[row][column]==-1)
            	c.setBackground(new Color(210, 210, 210)); 
            else{
            	if (matrixMax() == 0)
            		c.setBackground(new Color(255, 235, 235));
            	else
            		if (colorMode==0) 
            			c.setBackground(new Color(255, 220-11*((20*multi[row][column])/matrixMax()), 220-11*((20*multi[row][column])/matrixMax())));
            		else
            			c.setBackground(ConvertTotalToRgb(matrixMax(), multi[row][column]));
            	}                 
            
            isBordered = user.getProfile().getUserProblems().getSystemIndex(row) == column;            
            if (isBordered) {
				if (isSelected) {
					if (selectedBorder == null) {
						selectedBorder = BorderFactory.createMatteBorder(2,5,2,5, Color.black);
					}
					setBorder(selectedBorder);
				}
				else {
					if (unselectedBorder == null) {
						unselectedBorder = BorderFactory.createMatteBorder(2,5,2,5, Color.black);
					}
					setBorder(unselectedBorder);
				}
			} 
			setToolTipText(displayCellInfo(row, column));

            return c;
        };
	}
	
	public Color ConvertTotalToRgb(int range, int cell){
	    float h = (float)0.2-((float)0.2)*(cell/(float)range);
	    Color c = Color.getHSBColor(h,(float)0.75,(float)0.9);
	    return c;
	}

}
