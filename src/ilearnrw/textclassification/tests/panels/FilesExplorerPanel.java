package ilearnrw.textclassification.tests.panels;

import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class FilesExplorerPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	public FilesExplorerPanel() {
		super();
		JTable table = new JTable(new MonModel());
		this.setLayout(new GridLayout(1,1));
		this.add(new JScrollPane(table));
	}

	
	private class MonModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private ArrayList<IlearnFile> l;
	    private final String[] columnNames = new String[]{"Longitude", "Latitude"};

	    public MonModel() {
	        super();
	        l = new ArrayList<IlearnFile>();

	        l.add(new IlearnFile("first", 79.95));
	        l.add(new IlearnFile("second", 73.90));
	        l.add(new IlearnFile("third", 9.295));
	        loadFiles();
	    }

	    @Override
	    public String getColumnName(int column) {
	        return columnNames[column];
	    }

	    public int getColumnCount() {
	        return 2;
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
