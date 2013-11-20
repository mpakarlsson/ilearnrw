package ilearnrw.textclassification.tests.panels;

import ilearnrw.textclassification.Classifier;
import ilearnrw.user.User;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class SmallHeatMapPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTable heatMap;
	private JTextPane descriptionsText;
	private Classifier classifier;
	private int[][] data;
	private int[][] multi;
	private boolean first;
	private CellRenderer renderer;

	public SmallHeatMapPanel(User user) {
		this.data = copyMatrix(user.getProfile().getUserSeveritiesToProblems().getUserSeverities().getSeverities());
		for (int i=0;i<data.length;i++){
			for (int j=0;j<data[i].length;j++){
				data[i][j] = 0;
			}
		}

        descriptionsText = new JTextPane();
        descriptionsText.setEditable(false);
        descriptionsText.setBackground(Color.lightGray);
        
		heatMap = new JTable(data.length,lengthsMax());
		renderer = new CellRenderer();
		heatMap.setDefaultRenderer(Object.class, renderer);
		heatMap.setShowGrid(false);

		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new GridLayout(1,1));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this.add(splitPane, BorderLayout.CENTER);
        splitPane.setResizeWeight(0.77);
        
		splitPane.setLeftComponent(new JScrollPane(heatMap));
        
		splitPane.setRightComponent(descriptionsText);

		//this.add(heatMap);

		//this.add(descriptionsText);
		formatMatrix();
		first = true;
	}
	
	public void setClassifier(Classifier classifier){
		this.classifier = classifier;
		this.data = copyMatrix(classifier.getUserProblemsToText().getUserCounters().getCounters());
	}
	
	private void formatValues(){
		System.out.println("dfasdf::data[1][1]");
		data[0][0] = 0;
		for (int i=0;i<data.length;i++){
			for (int j=0;j<data[i].length;j++){
				data[0][0] = 0;
			}
		}
	}
	
	public void draw(){
		if (!first)
			this.data = copyMatrix(classifier.getUserProblemsToText().getUserCounters().getCounters());
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
			for (int j=0;j<multi[0].length;j++){
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
			for (int j=0;j<multi[0].length;j++){
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
		if (j<data[i].length){
			res = multi[i][j]+" times\n";
			res = res+ classifier.getUser().getProfile().getUserSeveritiesToProblems().getProblemDefinition(i).toString();
			res = res+classifier.getUser().getProfile().getUserSeveritiesToProblems().getProblemDescription(i, j).toString();
		}
		return res;
	}
	
	
	private class CellRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1L;

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
            	if (matrixMax() == 0){
            		c.setBackground(new Color(255, 235, 235));
            	
            	}
            	else{
            		c.setBackground(new Color(255, 240-20*((12*multi[row][column])/matrixMax()), 240-20*((12*multi[row][column])/matrixMax())));
            	}  
            }
            return c;
        };
	}

}
