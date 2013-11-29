package ilearnrw.textclassification.tests.panels;

import ilearnrw.languagetools.LanguageAnalyzerAPI;
import ilearnrw.textclassification.Classifier;
import ilearnrw.textclassification.Text;
import ilearnrw.textclassification.tests.TextMetricsTest;
import ilearnrw.user.User;
import ilearnrw.utils.LanguageCode;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public class FilesExplorerPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private final String path = "texts/";
	private User user;
	private JTable table;
	private LanguageAnalyzerAPI languageAnalyzer;
	// TODO remove the following
	private String unknown = "";
	
	public FilesExplorerPanel(User user, final JTabbedPane tabbedPane, final TextPanel textPanel, 
			final TextMetricsTest metrics, LanguageAnalyzerAPI languageAnalyzer) {
		super();
		

	    JFrame frame = new JFrame("Test");

	    ImageIcon loading = new ImageIcon("data/ajax-loader.gif");
	    frame.add(new JLabel("", loading, JLabel.CENTER));

	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(400, 300);
	    frame.setVisible(true);
		
		this.user = user;
		this.languageAnalyzer = languageAnalyzer;
		table = new JTable(new MonModel());
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
		for (int i=1;i<table.getColumnCount(); i++)
			table.getColumnModel().getColumn(i).setCellRenderer( rightRenderer );
		
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
				    	  //System.out.println(unknown);
				    	  metrics.classifierResults(false);
				      }
				   }
				});
		
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(table), BorderLayout.CENTER);
		
		addLegentPanel();

	    frame.setVisible(false);
	    frame = null;
		//this.add(new JLabel("asdf asdf "), BorderLayout.SOUTH);
	}
	
	
	private void addLegentPanel(){
		Object rowData[][] = { { "#W: words", "#DW: Difficut Words", "#VDW: Very Difficult Words"},
            { "#P: Paragraphs", "#BS: Big Sentences (>=15 Words)", "WpS: Words per Sentence"},
			{"F-K: Flesch-Kincaid", "D-C: Dale-Chall", "iLRW: iLearnRW WARD"}
		};
		Object columnNames[] = { "Column One", "Column Two", "Column Three" };
		JTable legent = new JTable(rowData, columnNames);
		Dimension d = legent.getPreferredSize();
		d.width = 700;
		legent.setPreferredScrollableViewportSize(d);
		legent.setShowGrid(false);
		legent.setTableHeader(null);
		legent.setOpaque(false);
		((DefaultTableCellRenderer)legent.getDefaultRenderer(Object.class)).setOpaque(false);
		JPanel legentPanel = new JPanel();
		legentPanel.add(new JScrollPane(legent));
		

        JPanel thePanel = new JPanel();
        thePanel .setLayout(new BoxLayout(thePanel , BoxLayout.PAGE_AXIS));
        JLabel legentLabel = new JLabel("Legent");
        thePanel.add(legentLabel);
        legentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        thePanel.add(Box.createRigidArea(new Dimension(0,5)));
        thePanel.add(legentPanel);
		
		
		this.add(thePanel, BorderLayout.SOUTH);
	}
	
	public LanguageAnalyzerAPI getLanguageAnalyzer() {
		return languageAnalyzer;
	}


	public void setLanguageAnalyzer(LanguageAnalyzerAPI languageAnalyzer) {
		this.languageAnalyzer = languageAnalyzer;
	}


	public void setUser(User user){
		this.user = user;
		table.setModel(new MonModel());
		//table.fireTableDataChanged();
	}
	
	private class MonModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private ArrayList<IlearnFile> l;
	    private final String[] columnNames = new String[]{"File Name", "#W", "#DW", "#VDW", 
	    		"#P", "#BS", "WpS", "F-K", "D-C", "iLRW"};

	    public MonModel() {
	        super();
	        l = new ArrayList<IlearnFile>();
	        loadFiles(); 
	    }

	    @Override
	    public String getColumnName(int column) {
	        return columnNames[column];
	    }

	    @Override
	    public Class getColumnClass(int column) {
	        if (column == 0) {
	            return String.class;
	        } else {
	            return Double.class;
	        }
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
	            return l.get(rowIndex).getNumberOfDifficultWords();
	        }
	        else if(columnIndex==3){
	            return l.get(rowIndex).getNumberOfVeryDifficultWords();
	        }
	        else if(columnIndex==4){
	            return l.get(rowIndex).getNumberOfParagraphs();
	        }
	        else if(columnIndex==5){
	            return l.get(rowIndex).getNumberOfBigSentences();
	        }
	        else if(columnIndex==6){
	            return Double.parseDouble(l.get(rowIndex).getAvgWordsPerSentence());
	        }
	        else if(columnIndex==7){
	            return Double.parseDouble(l.get(rowIndex).getFleschKincaid());
	        }
	        else if(columnIndex==8){
	            return Double.parseDouble(l.get(rowIndex).getDaleChall());
	        }
	        else if(columnIndex==9){
	            return Double.parseDouble(l.get(rowIndex).getiLearnRW());
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
						IlearnFile file = new IlearnFile(files);
						if (file.suitable())
							l.add(file);
					}
				}
			}
			// TODO open here to check the unknown words!!!
			/*HashMap<String, Integer> test = languageAnalyzer.getUnknownWords();
			Map<String, Integer> t = sortByValue(test);
			if (t!=null){
				unknown = "";
				for (Map.Entry<String,Integer> entry : t.entrySet()) {
					String key = entry.getKey();
					int value = entry.getValue();
					unknown = unknown +"\n"+key;//+"  "+value;
				}
				unknown = unknown +"\nΣύνολο:"+t.size();
			}*/
		}
	}
	
	private Map<String, Integer> sortByValue(Map<String, Integer> map) {
		if (map==null)
			return null;
		LinkedList<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

            public int compare(Map.Entry<String, Integer> m1, Map.Entry<String, Integer> m2) {
                return (m2.getValue()).compareTo(m1.getValue());
            }
        });

        Map<String, Integer> result = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
	
	private class IlearnFile {

		private String name;
	    private int numberOfWords, totalHits, numberOfDifficultWords, numberOfVeryDifficultWords, 
	    numberOfParagraphs, numberOfBigSentences;
		private double FleschKincaid, DaleChall, iLearnRW, avgWordsPerSentence;
	    private boolean isSuitableToTheUser;

		public IlearnFile(String name) {
	        this.name = name;
	        read();
	    }

	    private void read(){
	    	String text = "";
	    	  try {
	    		  text = new Scanner( new File(path+name), "UTF-8" ).useDelimiter("\\A").next();
	    	  } catch (FileNotFoundException e1) {
	    		  e1.printStackTrace();
	    	  }
	    	  LanguageCode lan = findLanguage(text);
	    	  isSuitableToTheUser = lan == user.getDetails().getLanguage();
	    	  Text t = new Text(text, lan);
	    	  Classifier cls = new Classifier(user, t, languageAnalyzer);
	    	  cls.calculateProblematicWords(false);
	    	  numberOfWords = t.getNumberOfWords();
	    	  numberOfDifficultWords = cls.getDiffWords();
	    	  numberOfVeryDifficultWords = cls.getVeryDiffWords();
	    	  totalHits = cls.getUserHits();
	    	  numberOfBigSentences = t.getNumberOfBigSentences();
	    	  numberOfParagraphs = t.getNumberOfParagraphs();
	    	  FleschKincaid = t.fleschKincaid();
	    	  DaleChall = t.daleChall();
	    	  iLearnRW = cls.getDifficulty();
	    	  avgWordsPerSentence = t.getWordsPerSentence();
	    }

		public boolean suitable() {
			return isSuitableToTheUser;
		}

		public int getNumberOfWords() {
			return numberOfWords;
		}

		public void setNumberOfWords(int numberOfWords) {
			this.numberOfWords = numberOfWords;
		}

		public int getNumberOfDifficultWords() {
			return numberOfDifficultWords;
		}

		public int getNumberOfVeryDifficultWords() {
			return numberOfVeryDifficultWords;
		}
		
	    public int getNumberOfParagraphs() {
			return numberOfParagraphs;
		}
		
	    public int getNumberOfBigSentences() {
			return numberOfBigSentences;
		}

		public void setNumberOfBigSentences(int numberOfBigSentences) {
			this.numberOfBigSentences = numberOfBigSentences;
		}

		public void setNumberOfDifficultWords(int numberOfDifficultWords) {
			this.numberOfDifficultWords = numberOfDifficultWords;
		}

		public String getFleschKincaid() {
			return String.format("%.2f",FleschKincaid);
		}

		public void setFleschKincaid(double fleschKincaid) {
			FleschKincaid = fleschKincaid;
		}

	    public int getTotalHits() {
			return totalHits;
		}

		public void setTotalHits(int totalHits) {
			this.totalHits = totalHits;
		}

		public String getAvgWordsPerSentence() {
			return String.format("%.2f",avgWordsPerSentence);
		}

		public void setAvgWordsPerSentence(double avgWordsPerSentence) {
			this.avgWordsPerSentence = avgWordsPerSentence;
		}

		public boolean isSuitableToTheUser() {
			return isSuitableToTheUser;
		}

		public void setSuitableToTheUser(boolean isSuitableToTheUser) {
			this.isSuitableToTheUser = isSuitableToTheUser;
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
