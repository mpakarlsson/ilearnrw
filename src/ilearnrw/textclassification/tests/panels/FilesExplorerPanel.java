package ilearnrw.textclassification.tests.panels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.AbstractTableModel;

public class FilesExplorerPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	public FilesExplorerPanel(final JTabbedPane tabbedPane, final TextPanel textPanel) {
		super();
		final JTable table = new JTable(new MonModel());
		table.addMouseListener(new MouseAdapter() {
			   public void mouseClicked(MouseEvent e) {
				      if (e.getClickCount() == 2) {
				    	  MonModel mm = (MonModel)table.getModel();
				    	  JTable target = (JTable)e.getSource();
				    	  int row = target.getSelectedRow();
				    	  int column = target.getSelectedColumn();
				    	  tabbedPane.setSelectedIndex(0);
				    	  String text = "";
				    	  try {
				    		  text = new Scanner( new File("data/"+mm.getFileName(row)), "UTF-8" ).useDelimiter("\\A").next();
				    	  } catch (FileNotFoundException e1) {
				    		  // TODO Auto-generated catch block
				    		  e1.printStackTrace();
				    	  }
				    	  textPanel.testMethod(text);
				      }
				   }
				});
		
		this.setLayout(new GridLayout(1,1));
		this.add(new JScrollPane(table));
	}

	
	private class MonModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private ArrayList<IlearnFile> l;
	    private final String[] columnNames = new String[]{"File Name", "Score"};

	    public MonModel() {
	        super();
	        l = new ArrayList<IlearnFile>();

	        loadFiles();
	    }

	    @Override
	    public String getColumnName(int column) {
	        return columnNames[column];
	    }

	    public int getColumnCount() {
	        return columnNames.length;
	    }

	    public int getRowCount() {
	        return l.size();
	    }

	    public Object getValueAt(int rowIndex, int columnIndex) {
	        if(columnIndex==0){
	            return l.get(rowIndex).getName();
	        }
	        else if(columnIndex==1){
	            return l.get(rowIndex).getIlearnScore();
	        }
	        return null;
	    }
	    
	    public String getFileName(int i){
	    	return l.get(i).getName();
	    }
		
		private void loadFiles(){
		 
		  // Directory path here
			String path = "data/"; 

			String files;
			File folder = new File(path);
			File[] listOfFiles = folder.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()){
					files = listOfFiles[i].getName();
					if (files.endsWith(".txt") || files.endsWith(".TXT")){
						l.add(new IlearnFile(files, 79.95-i));
					}
				}
			}
		}
	}
	
	private class IlearnFile {
	    private String name;
	    private double ilearnScore;

	    public IlearnFile(String name, double ilearnScore) {
	        this.name = name;
	        this.ilearnScore = ilearnScore;
	    }

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public double getIlearnScore() {
			return ilearnScore;
		}

		public void setIlearnScore(double ilearnScore) {
			this.ilearnScore = ilearnScore;
		}
	}
}
