package ilearnrw.textclassification.tests.panels;

import ilearnrw.textclassification.StringMatchesInfo;
import ilearnrw.textclassification.WordClassificationResults;
import ilearnrw.textclassification.WordProblemInfo;
import ilearnrw.user.profile.UserProfile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public class WordHeatMapPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTable heatMap;
	private JTextPane descriptionsText;
	private WordClassificationResults classifier;
	private UserProfile userProfile;
	private int[][] data;
	private int[][] multi;
	private boolean first;
	private CellRenderer renderer;
	private int colorMode = 0;

	public WordHeatMapPanel(UserProfile userProfile) {
		this.userProfile = userProfile;
		this.data = copyMatrix(userProfile.getUserProblems().getUserSeverities().getSeverities());
		for (int i=0;i<data.length;i++){
			for (int j=0;j<data[i].length;j++){
				data[i][j] = 0;
			}
		}

        descriptionsText = new JTextPane();
        descriptionsText.setEditable(false);
        //descriptionsText.setBackground(Color.lightGray);
        
		heatMap = new JTable(data.length,lengthsMax()); 
		for (int i=0;i<lengthsMax(); i++)
			heatMap.getTableHeader().getColumnModel().getColumn(i).setHeaderValue(""+(i+1));
		
		renderer = new CellRenderer();
		heatMap.setDefaultRenderer(Object.class, renderer);
		heatMap.setRowHeight(25);
		heatMap.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
		TableColumn column = null;
	    for (int i = 0; i < heatMap.getColumnCount(); i++) {
	        column = heatMap.getColumnModel().getColumn(i);
	        column.setPreferredWidth(25);
	    }  
		//heatMap.setShowGrid(false);

		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new GridLayout(1,1));
		
		JPanel panel = new JPanel();
		//JSplitPane splitPane = new JSplitPane();
        panel.setLayout(new GridLayout(2,1));
		//splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this.add(panel, BorderLayout.CENTER);
		//this.add(splitPane, BorderLayout.CENTER);
        //splitPane.setResizeWeight(0.5);
        JScrollPane scrollPane = new JScrollPane(heatMap);
        panel.add(scrollPane);
		//splitPane.setLeftComponent(new JScrollPane(heatMap));
        panel.add(descriptionsText);
		//splitPane.setRightComponent(descriptionsText);

		//this.add(heatMap);

		//this.add(descriptionsText);
		formatMatrix();
		first = true;
	}

	public int getColorMode() {
		return colorMode;
	}

	public void setColorMode(int colorMode) {
		this.colorMode = colorMode;
	}
	
	public void setClassifier(WordClassificationResults classifier){
		this.classifier = classifier;
		this.data = copyMatrix(classifier.getUserCounters().getCounters());
	}
	
	public void draw(){
		if (!first)
			this.data = copyMatrix(classifier.getUserCounters().getCounters());
		else{
			first = false;
			//formatValues();
		}
	}
	
	public void test(){
		createMatrix();
		//setValues();

		heatMap.setDefaultRenderer(Object.class, renderer);

		heatMap.repaint();
		heatMap.revalidate();
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
	
	private void formatMatrix(){
		multi = new int[data.length][lengthsMax()];
		for (int i=0;i<multi.length;i++){
			for (int j=0;j<multi[i].length;j++){
				if (j>=data[i].length)
					multi[i][j] = -1;
				else{
					multi[i][j] = 1;
				}
			}
		}
	}
	
	private void createMatrix(){
		multi = new int[data.length][lengthsMax()];
		for (int i=0;i<multi.length;i++){
			for (int j=0;j<multi[i].length;j++){
				if (j>=data[i].length)
					multi[i][j] = -1;
				else{
					multi[i][j] = data[i][j];
				}
			}
		}
	}
	
	private String displayCellInfo(int i, int j){
		String res = "null";
		if (classifier == null) 
			return res;
		//if (j==0){
		//	res = user.getProfile().getUserSeveritiesToProblems().getProblemDefinition(i).toString();
		//}
		if (j<data[i].length){
			res = "Problem Title:"+userProfile.getUserProblems().getProblemDefinition(i).getType().getUrl();
			if (!userProfile.getUserProblems().getProblemDefinition(i).getUri().
					equalsIgnoreCase(userProfile.getUserProblems().getProblemDefinition(i).getType().getUrl()))
				res = res + ", Targeted Area:"+userProfile.getUserProblems().getProblemDefinition(i).getUri();
			//res = user.getProfile().getUserSeveritiesToProblems().getProblemDefinition(i).toString();
			res = res + "\nMatching word characteristics:"+userProfile.getUserProblems().getProblemDescription(i, j).getProblemType();
			res = res + " {"+userProfile.getUserProblems().getProblemDescription(i, j).returnDescriptionsAsString()+"}";
			res = res + " \nFound "+multi[i][j]+" times\n";
		}
		return res;
	}
	
	private String displayAllProbsInfo(){
		String res = "null";
		if (classifier == null) 
			return res;
		String t = "";
		String w = classifier.getWord();
		String extras[] = new String[w.length()+2];
		for (WordProblemInfo wpi : classifier.getWprobs().getWordProblems()){
			for (int i=0; i<extras.length; i++)
				extras[i] = "_";
			extras[extras.length-1] = " ";
			for (StringMatchesInfo smi : wpi.getMatched()){
				extras[smi.getStart()] = "{";
				if (smi.getEnd()<extras.length-1)
					extras[smi.getEnd()] = "}";
				else
					extras[extras.length-1] = "***";
			}
			String tmp = "";
			for (int i=0;i<w.length();i++)
				tmp = tmp + extras[i]+w.charAt(i);
			tmp = tmp+extras[extras.length-2];
			tmp = tmp+extras[extras.length-1];
			String info = "("+wpi.getCategory()+", "+wpi.getIndex()+") "+ userProfile.getUserProblems().getProblemDescription(wpi.getCategory(), wpi.getIndex()).getProblemType().toString();
			info = info + " {"+userProfile.getUserProblems().getProblemDescription(wpi.getCategory(), wpi.getIndex()).returnDescriptionsAsString()+"}";
			t = t+info+" "+tmp+"\n";
		}
		return t;
	}
	
	
	private class CellRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1L;

		@Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
        		boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (hasFocus){
            	// TODO add a window that displays problem description here!
            	if (!displayCellInfo(row, column).equals("null"))
            		descriptionsText.setText(displayCellInfo(row, column));
            	else 
            		descriptionsText.setText(displayAllProbsInfo());
            	c.setBackground(Color.black);
            }
            if (multi[row][column]==-1)
            	c.setBackground(new Color(210, 210, 210)); 
            else{
            	if (matrixMax() == 0){
            		if (colorMode == 0)
                		c.setBackground(new Color(255, 250, 250));
            		else
            			c.setBackground(Color.getHSBColor((float)0.4,(float)0.75,(float)0.9));
            	
            	}
            	else if (multi[row][column]==0){
            		if (colorMode == 0)
                		c.setBackground(new Color(255, 255, 255));
            		else
            			c.setBackground(Color.getHSBColor((float)0.4,(float)0.75,(float)0.9));
            	}
            	else{
            		if (colorMode == 0)
            			c.setBackground(new Color(255, 220-11*((20*multi[row][column])/matrixMax()), 220-11*((20*multi[row][column])/matrixMax())));
            		else
            			c.setBackground(ConvertTotalToRgb(matrixMax(), multi[row][column]));
            	}  
            }
            return c;
        };
        
	}
	
	public Color ConvertTotalToRgb(int range, int cell){
	    float h = (float)0.2-((float)0.2)*(cell/(float)range);
	    Color c = Color.getHSBColor(h,(float)0.75,(float)0.9);
	    return c;
	}


}
