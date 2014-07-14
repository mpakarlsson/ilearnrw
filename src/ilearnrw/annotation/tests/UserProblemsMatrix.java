package ilearnrw.annotation.tests;
import ilearnrw.languagetools.extras.WordListLoader;
import ilearnrw.textclassification.Word;
import ilearnrw.user.User;
import ilearnrw.user.profile.UserProblems;
import ilearnrw.user.profile.UserSeverities;
import ilearnrw.utils.LanguageCode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public class UserProblemsMatrix extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTable heatMap;
	private JTextPane descriptionsText;
	private User user;
	private int[][] data;
	private int[][] multi;
	private CellRenderer renderer;
	private JSplitPane splitPane;
	private int colorMode = 0;
	private JPanel upperPanel;
	private AnnotationStylesPanel annotationStylesPanel;
	private Mediator m;

	public UserProblemsMatrix(User user, Mediator m){
		this.user = user;
		this.m = m;
		this.data = copyMatrix(user.getProfile().getUserProblems().getUserSeverities().getSeverities());

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

		List<Word> tmp = user.getProfile().getUserProblems().getTrickyWords();
        annotationStylesPanel = new AnnotationStylesPanel(tmp.toArray(), m);

        JPanel wordsInfo = new JPanel(new BorderLayout());
        wordsInfo.setLayout(new BoxLayout(wordsInfo, BoxLayout.LINE_AXIS));
        wordsInfo.setPreferredSize(new Dimension(500, 250));
        wordsInfo.setAlignmentX(CENTER_ALIGNMENT);
        wordsInfo.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        //wordsInfo.add(Box.createHorizontalGlue());
        wordsInfo.add(Box.createRigidArea(new Dimension(10, 0)));
        wordsInfo.add(annotationStylesPanel);
		
        upperPanel.add(wordsInfo, BorderLayout.PAGE_END);
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

		List<Word> tmp = user.getProfile().getUserProblems().getTrickyWords();
        annotationStylesPanel = new AnnotationStylesPanel(tmp.toArray(), m);

        JPanel wordsInfo = new JPanel(new BorderLayout());
        wordsInfo.setLayout(new BoxLayout(wordsInfo, BoxLayout.LINE_AXIS));
        wordsInfo.setPreferredSize(new Dimension(500, 250));
        wordsInfo.setAlignmentX(CENTER_ALIGNMENT);
        wordsInfo.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        //wordsInfo.add(Box.createHorizontalGlue());
        wordsInfo.add(Box.createRigidArea(new Dimension(10, 0)));
        wordsInfo.add(annotationStylesPanel);
		
        upperPanel.add(wordsInfo, BorderLayout.PAGE_END);
		splitPane.setLeftComponent(upperPanel);
		
		draw();
		test();
	}
	public void updateUser() {
		if(user == null)
			return;
		UserProblems probs = user.getProfile().getUserProblems();
		UserSeverities severities = user.getProfile().getUserProblems().getUserSeverities();
		for (int i=0;i<multi.length;i++){
			for (int j=0;j<multi[i].length;j++){
				Object o = heatMap.getValueAt(i, j);
				if( o != null )
				{
					try{
					probs.setUserSeverity(i, j, (Integer) o);
					} catch( Exception ex ) {
						probs.setUserSeverity(i, j, Integer.parseInt((String) o));
					}
				}
			}
		}
		user.getProfile().getUserProblems().setUserSeverities(severities);
		//user.getProfile().getUserProblems().setTrickyWords(trickyPanel.getWords());
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
			if (!user.getProfile().getUserProblems().getProblemDefinition(i).getUri().
					equalsIgnoreCase(user.getProfile().getUserProblems().getProblemDefinition(i).getType().getUrl()))
				res = res + ", Targeted Area:"+user.getProfile().getUserProblems().getProblemDefinition(i).getUri();
			//res = user.getProfile().getUserSeveritiesToProblems().getProblemDefinition(i).toString();
			res = res + "\nMatching word characteristics:"+user.getProfile().getUserProblems().getProblemDescription(i, j).getProblemType();
			res = res + " {"+user.getProfile().getUserProblems().getProblemDescription(i, j).returnDescriptionsAsString()+"}";
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
    			String lan = "GR";
    			if (user.getProfile().getLanguage() ==  LanguageCode.EN)
    				lan = "EN";
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
			m.setXCellCoord(row);
			m.setYCellCoord(column);
			System.out.println(m.getXCellCoord() + " " + m.getYCellCoord());
            return c;
        };
	}
	
	public Color ConvertTotalToRgb(int range, int cell){
	    float h = (float)0.2-((float)0.2)*(cell/(float)range);
	    Color c = Color.getHSBColor(h,(float)0.75,(float)0.9);
	    return c;
	}

}
