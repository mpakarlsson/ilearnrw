package ilearnrw.textclassification.tests.panels;

import ilearnrw.textclassification.Classifier;
import ilearnrw.textclassification.Text;
import ilearnrw.textclassification.tests.TextMetricsTest;
import ilearnrw.user.User;
import ilearnrw.utils.LanguageCode;

import java.awt.GridLayout;
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
import javax.swing.table.AbstractTableModel;

public class FilesExplorerPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private final String path = "texts/";
	private User user;
	private JTable table;
	
	public FilesExplorerPanel(User user, final JTabbedPane tabbedPane, final TextPanel textPanel, final TextMetricsTest metrics) {
		super();
		this.user = user;
		table = new JTable(new MonModel());
		table.setAutoCreateRowSorter(true);
		table.getColumnModel().getColumn(0).setPreferredWidth(3*table.getColumnModel().getColumn(1).getPreferredWidth());
		table.addMouseListener(new MouseAdapter() {
			   public void mouseClicked(MouseEvent e) {
				      if (e.getClickCount() == 2) {
				    	  MonModel mm = (MonModel)table.getModel();
				    	  JTable target = (JTable)e.getSource();
				    	  int row = target.getSelectedRow();
				    	  //int column = target.getSelectedColumn();
				    	  tabbedPane.setSelectedIndex(0);
				    	  String text = "";
				    	  try {
				    		  text = new Scanner( new File(path+mm.getFileName(row)), "UTF-8" ).useDelimiter("\\A").next();
				    	  } catch (FileNotFoundException e1) {
				    		  // TODO Auto-generated catch block
				    		  e1.printStackTrace();
				    	  }
				    	  textPanel.testMethod(text);
				    	  metrics.classifierResults();
				      }
				   }
				});
		
		this.setLayout(new GridLayout(1,1));
		this.add(new JScrollPane(table));
	}

	
	public void setUser(User user){
		this.user = user;
		table.setModel(new MonModel());
		//table.fireTableDataChanged();
	}
	
	private class MonModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private ArrayList<IlearnFile> l;
	    private final String[] columnNames = new String[]{"File Name", "# Words", "Flesch", 
	    		"Flesch-Kincaid", "Automated", "Coleman-Liau", "SMOG", "Gunning FOG", "Dale-Chall", "iLearnRW"};

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
	            return l.get(rowIndex).getNumberOfWords();
	        }
	        else if(columnIndex==2){
	            return l.get(rowIndex).getFlesch();
	        }
	        else if(columnIndex==3){
	            return l.get(rowIndex).getFleschKincaid();
	        }
	        else if(columnIndex==4){
	            return l.get(rowIndex).getAutomated();
	        }
	        else if(columnIndex==5){
	            return l.get(rowIndex).getColemanLiau();
	        }
	        else if(columnIndex==6){
	            return l.get(rowIndex).getSMOG();
	        }
	        else if(columnIndex==7){
	            return l.get(rowIndex).getGunningFOG();
	        }
	        else if(columnIndex==8){
	            return l.get(rowIndex).getDaleChall();
	        }
	        else if(columnIndex==9){
	            return l.get(rowIndex).getiLearnRW();
	        }
	        return null;
	    }
	    
	    public String getFileName(int i){
	    	return l.get(i).getName();
	    }
		
		private void loadFiles(){

			String files;
			File folder = new File(path);
			File[] listOfFiles = folder.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()){
					files = listOfFiles[i].getName();
					if (files.endsWith(".txt") || files.endsWith(".TXT")){
						l.add(new IlearnFile(files));
					}
				}
			}
		}
	}
	
	private class IlearnFile {
	    private String name;
	    private int numberOfWords;
	    private double Flesch, FleschKincaid, Automated, ColemanLiau, 
	    SMOG, gunningFOG, DaleChall, iLearnRW;

		public IlearnFile(String name) {
	        this.name = name;
	        read();
	    }

	    private void read(){
	    	String text = "";
	    	  try {
	    		  text = new Scanner( new File(path+name), "UTF-8" ).useDelimiter("\\A").next();
	    	  } catch (FileNotFoundException e1) {
	    		  // TODO Auto-generated catch block
	    		  e1.printStackTrace();
	    	  }
	  		Text t = new Text(text, findLanguage(text));
			Classifier cls = new Classifier(user, t);
			cls.test();
			numberOfWords = t.getNumberOfWords();
			Flesch = t.flesch();
			FleschKincaid = t.fleschKincaid();
			Automated = t.automated();
			ColemanLiau = t.colemanLiau(); 
		    SMOG = t.smog();
		    gunningFOG = t.gunningFog();
		    DaleChall = t.daleChall();
		    iLearnRW = cls.getDifficulty();
	    }

		public int getNumberOfWords() {
			return numberOfWords;
		}

		public void setNumberOfWords(int numberOfWords) {
			this.numberOfWords = numberOfWords;
		}
	    
	    public String getFlesch() {
			return String.format("%.2f",Flesch);
		}

		public void setFlesch(double flesch) {
			Flesch = flesch;
		}

		public String getFleschKincaid() {
			return String.format("%.2f",FleschKincaid);
		}

		public void setFleschKincaid(double fleschKincaid) {
			FleschKincaid = fleschKincaid;
		}

		public String getAutomated() {
			return String.format("%.2f",Automated);
		}

		public void setAutomated(double automated) {
			Automated = automated;
		}

		public String getColemanLiau() {
			return String.format("%.2f",ColemanLiau);
		}

		public void setColemanLiau(double colemanLiau) {
			ColemanLiau = colemanLiau;
		}

		public String getSMOG() {
			return String.format("%.2f",SMOG);
		}

		public void setSMOG(double sMOG) {
			SMOG = sMOG;
		}

		public String getGunningFOG() {
			return String.format("%.2f",gunningFOG);
		}

		public void setGunningFOG(double gunningFOG) {
			this.gunningFOG = gunningFOG;
		}

		public String getDaleChall() {
			return String.format("%.2f",DaleChall);
		}

		public void setDaleChall(double daleChall) {
			DaleChall = daleChall;
		}

		public String getiLearnRW() {
			return String.format("%.2f",iLearnRW);
		}

		public void setiLearnRW(double iLearnRW) {
			this.iLearnRW = iLearnRW;
		}
	    
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		private LanguageCode findLanguage(String text){
			char[] lowerCase = {'α', 'β', 'γ', 
		    		'δ', 'ε', 'ζ', 'η', 'θ', 
		    		'ι', 'κ', 'λ', 'μ', 'ν', 
		    		'ξ', 'ο', 'π', 'ρ', 'σ', 
		    		'τ', 'υ', 'φ', 'χ', 'ψ', 
		    		'ω', 'ά', 'έ', 'ή', 'ί', 
		    		'ό', 'ύ', 'ώ', 'ϊ', 'ϋ', 
		    		'ΐ','ΰ', 'ς'};
		    char[] upperCase = {'Α', 'Β', 'Γ', 
		    		'Δ', 'Ε', 'Ζ', 'Η', 'Θ', 
		    		'Ι', 'Κ', 'Λ', 'Μ', 'Ν', 
		    		'Ξ', 'Ο', 'Π', 'Ρ', 'Σ', 
		    		'Τ', 'Υ', 'Φ', 'Χ', 'Ψ', 
		    		'Ω', 'Ά', 'Έ', 'Ή', 'Ί', 
		    		'Ό', 'Ύ', 'Ώ', 'Ϊ', 'Ϋ' };
		    for (char c : lowerCase){
		    	if (text.contains(""+c))
		    		return LanguageCode.GR;
		    }
		    for (char c : upperCase){
		    	if (text.contains(""+c))
		    		return LanguageCode.GR;
		    }
		    return LanguageCode.EN;
		}
	}
}
