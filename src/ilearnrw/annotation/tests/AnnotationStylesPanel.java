package ilearnrw.annotation.tests;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import ilearnrw.textadaptation.PresentationRulesModule;
import ilearnrw.textadaptation.Rule;
import ilearnrw.textadaptation.TextAnnotationModule;

public class AnnotationStylesPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JList list;
	private ArrayList<String> words;
	private final Mediator mediator;

	public AnnotationStylesPanel(Object data[], Mediator mediator) {
		words = new ArrayList<String>();
		this.mediator = mediator;

		for (int i = 0; i < data.length; i++)
			words.add((String) data[i]);

		list = new JList(words.toArray()) {
			private static final long serialVersionUID = 1L;

			public int getScrollableUnitIncrement(Rectangle visibleRect,
					int orientation, int direction) {
				int row;
				if (orientation == SwingConstants.VERTICAL && direction < 0
						&& (row = getFirstVisibleIndex()) != -1) {
					Rectangle r = getCellBounds(row, row);
					if ((r.y == visibleRect.y) && (row != 0)) {
						Point loc = r.getLocation();
						loc.y--;
						int prevIndex = locationToIndex(loc);
						Rectangle prevR = getCellBounds(prevIndex, prevIndex);

						if (prevR == null || prevR.y >= r.y) {
							return 0;
						}
						return prevR.height;
					}
				}
				return super.getScrollableUnitIncrement(visibleRect,
						orientation, direction);
			}
		};

		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		// list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = list.locationToIndex(e.getPoint());
					System.err.println("hehehe" + index); // emulate button
															// click

				}
			}
		});
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 100));
		listScroller.setAlignmentX(LEFT_ALIGNMENT);

		JPanel listPane = new JPanel();
		listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
		JLabel label = new JLabel("Presentation Rules Options");

		listPane.setLayout(new java.awt.BorderLayout());

		JPanel upPane = new JPanel();
		upPane.add(label, java.awt.BorderLayout.CENTER);

		JPanel downPane = new JPanel();

		downPane.setLayout(new java.awt.GridLayout(5, 0));

		javax.swing.JButton b1 = new javax.swing.JButton(
				"0. PAINT_PROBLEMATIC_PARTS");
		b1.addActionListener(new MyListener(mediator));

		javax.swing.JButton b2 = new javax.swing.JButton("1. PAINT_WHOLE_WORD");
		javax.swing.JButton b3 = new javax.swing.JButton(
				"2. HIGHLIGHT_PROBLEMATIC_PARTS");
		javax.swing.JButton b4 = new javax.swing.JButton(
				"3. HIGHLIGHT_WHOLE_WORD");
		javax.swing.JButton b5 = new javax.swing.JButton(
				"4. BOLD_PROBLEMATIC_PARTS");
		javax.swing.JButton b6 = new javax.swing.JButton("5. DO_NOTHING");
		javax.swing.JButton b7 = new javax.swing.JButton("5. ANNOTATE");
		b7.addActionListener(new AnnotateListener(mediator));

		downPane.add(b1);
		downPane.add(b2);
		downPane.add(b3);
		downPane.add(b4);
		downPane.add(b5);
		downPane.add(b6);
		downPane.add(b7);

		listPane.add(upPane, java.awt.BorderLayout.NORTH);
		listPane.add(downPane, java.awt.BorderLayout.CENTER);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.PAGE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(Box.createVerticalGlue());

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(listPane);
		this.add(buttonPane);
	}

	public ArrayList<String> getWords() {
		return this.words;
	}

	class MyListener implements java.awt.event.ActionListener {
		private Mediator m;

		public MyListener(Mediator m) {
			this.m = m;
		}

		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (m.getXCellCoord() != -1 && m.getYCellCoord() != -1) {
				m.getPresentationRulesModule().getRulesTable()[m
						.getXCellCoord()][m.getYCellCoord()]
						.setPresentationStyle(Rule.HIGHLIGHT_PROBLEMATIC_PARTS);
			}
		}
	}

	public String readFile(String path, java.nio.charset.Charset encoding)
			throws java.io.IOException {
		byte[] encoded = java.nio.file.Files.readAllBytes(java.nio.file.Paths
				.get(path));
		return new String(encoded, encoding);
	}

	class AnnotateListener implements java.awt.event.ActionListener {
		private Mediator m;

		public AnnotateListener(Mediator m) {
			this.m = m;
		}

		public void actionPerformed(java.awt.event.ActionEvent e) {
			String JSONfile = "C:\\Users\\Fouli\\Desktop\\response.json";
			TextAnnotationModule t = new TextAnnotationModule("");
			t.initializePresentationModule(mediator.getUserProfile());
			try {
				String htmlFile = readFile(
						"C:\\Users\\Fouli\\Desktop\\input.html",
						java.nio.charset.StandardCharsets.UTF_8);
				// String json = readFile(JSONfile,
				// java.nio.charset.StandardCharsets.UTF_8);

				java.io.BufferedReader br = new java.io.BufferedReader(
						new java.io.InputStreamReader(
								new java.io.FileInputStream(JSONfile), "UTF8"));

				String strLine;

				StringBuffer text = new StringBuffer();

				while ((strLine = br.readLine()) != null) {
					text.append(strLine.trim());
				}
				br.close();
				System.out.println(text.toString());

				t.setJSONFile(text.toString());
				t.setInputHTMLFile(htmlFile);

				t.setAnnotatedHTMLFile(htmlFile);

				t.annotateText();

				org.apache.commons.io.FileUtils.writeStringToFile(
						new java.io.File(
								"C:\\Users\\Fouli\\Desktop\\output.html",
								"UTF-8"), htmlFile);
			} catch (java.io.IOException a) {

			}
		}
	}
}
