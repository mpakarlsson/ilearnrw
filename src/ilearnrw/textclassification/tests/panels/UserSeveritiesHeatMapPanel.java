package ilearnrw.textclassification.tests.panels;
import ilearnrw.user.User;
import ilearnrw.user.profile.UserSeverities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class UserSeveritiesHeatMapPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTable heatMap;
	private JTextPane descriptionsText;
	private User user;
	private int[][] data;
	private int[][] multi;
	private CellRenderer renderer;
	private JSplitPane splitPane;
	private boolean first = true;

	public UserSeveritiesHeatMapPanel(User user){
		this.user = user;

		this.data = copyMatrix(user.getProfile().getUserSeveritiesToProblems().getUserSeverities().getSeverities());

        descriptionsText = new JTextPane();
        descriptionsText.setEditable(false);
        descriptionsText.setBackground(Color.lightGray);
        
		heatMap = new JTable(data.length,lengthsMax());
		renderer = new CellRenderer();
		heatMap.setDefaultRenderer(Object.class, renderer);
		heatMap.setShowGrid(false);

		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new GridLayout(1,1));
		
		splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this.add(splitPane, BorderLayout.CENTER);
        splitPane.setResizeWeight(0.77);
        
		splitPane.setLeftComponent(new JScrollPane(heatMap));
        
		splitPane.setRightComponent(descriptionsText);

		//this.add(new JScrollPane(heatMap));

		//this.add(descriptionsText);
		first = true;
	}
	
	public void setUser(User user){
		this.user = user;

		this.data = copyMatrix(user.getProfile().getUserSeveritiesToProblems().getUserSeverities().getSeverities());
		heatMap = new JTable(data.length,lengthsMax());
		renderer = new CellRenderer();
		heatMap.setDefaultRenderer(Object.class, renderer);
		heatMap.setShowGrid(false);

		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new GridLayout(1,1));
		splitPane.setLeftComponent(new JScrollPane(heatMap));
		
		draw();
		test();
	}
	public void updateUser() {
		if(user == null)
			return;
		UserSeverities severities = user.getProfile().getUserSeveritiesToProblems().getUserSeverities();
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
		user.getProfile().getUserSeveritiesToProblems().setUserSeverities(severities);
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
		this.data = copyMatrix(user.getProfile().getUserSeveritiesToProblems().getUserSeverities().getSeverities());
		heatMap.setRowHeight(60);
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
		//if (j==0){
		//	res = user.getProfile().getUserSeveritiesToProblems().getProblemDefinition(i).toString();
		//}
		if (j<data[i].length){
			res = user.getProfile().getUserSeveritiesToProblems().getProblemDefinition(i).toString();
			res = res + user.getProfile().getUserSeveritiesToProblems().getProblemDescription(i, j).toString();
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
            		c.setBackground(new Color(255, 240-20*((12*multi[row][column])/matrixMax()), 240-20*((12*multi[row][column])/matrixMax())));
            	}                 
            
            isBordered = user.getProfile().getUserSeveritiesToProblems().getWorkingIndex(row) == column;
            
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

}
