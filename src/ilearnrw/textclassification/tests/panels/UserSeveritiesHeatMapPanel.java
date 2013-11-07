package ilearnrw.textclassification.tests.panels;
import ilearnrw.user.User;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class UserSeveritiesHeatMapPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTable heatMap;
	private JLabel messagesLabel;
	private User user;
	private int[][] data;
	private int[][] multi;

	public UserSeveritiesHeatMapPanel(User user){
		this.user = user;
	}
	
	public void setUser(User user){
		this.user = user;
	}
	
	private void setValues(){
		for (int i=0;i<multi.length;i++){
			for (int j=0;j<multi[i].length;j++){
				if (multi[i][j] != -1)
					heatMap.setValueAt(multi[i][j], i, j);
			}
		}
	}
	
	public void draw(){
		this.data = user.getProfile().getUserSeveritiesToProblems().getUserSeverities().getSeverities();
		heatMap = new JTable(data.length,data[0].length+2);
		heatMap.setShowGrid(false);
		
		messagesLabel = new JLabel("hello");

		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new BorderLayout(0, 0));

		this.add(heatMap, BorderLayout.CENTER);

		this.add(messagesLabel, BorderLayout.SOUTH);
		
		heatMap.setRowHeight(80);

	}
	
	public void test(){
		heatMap.repaint();
		createMatrix();
		setValues();
		
		heatMap.setDefaultRenderer(Object.class, new CellRenderer());
	}
	
	private int matrixMax(){
		int max = multi[0][2];
		for (int i=0;i<multi.length;i++){
			for (int j=2;j<multi[0].length;j++){
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
		multi = new int[data.length][lengthsMax()+2];
		for (int i=0;i<multi.length;i++){
			int sum = 0;
			for (int j=multi[0].length-1;j>1;j--){
				if (j>=data[i].length+2)
					multi[i][j] = -1;
				else{
					multi[i][j] = data[i][j-2];
					sum += data[i][j-2];
				}
			}
			multi[i][0] = user.getProfile().getUserSeveritiesToProblems().getWorkingIndex(i);
			multi[i][1] = -1;
		}
	}
	
	private String displayCellInfo(int i, int j){
		String res = "null";
		if (j==0){
			res = user.getProfile().getUserSeveritiesToProblems().getProblemDefinition(i).toString();
		}
		else if (j>1 && j<data[i].length+2){
			res = user.getProfile().getUserSeveritiesToProblems().getProblemDescription(i, j-2).toString();
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
            	messagesLabel.setText(displayCellInfo(row, column));
            	c.setBackground(Color.black);
            }
            if (column==0)
            	c.setBackground(new Color(170, 255-20*((12*multi[row][column])/sumsMax()), 255-((12*multi[row][column])/sumsMax())));                        
            else if (multi[row][column]==-1)
            	c.setBackground(Color.white); 
            else
            	c.setBackground(new Color(170, 255-20*((12*multi[row][column])/matrixMax()), 255-((12*multi[row][column])/matrixMax())));                        

            return c;
        };
	}

}
