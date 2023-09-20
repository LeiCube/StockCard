import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class Frame_Stock extends JFrame {
	private MenuButton btn = new MenuButton();
	private JPanel contentPane;
	private JTable table;
	private JTable table2;
	private DefaultTableModel model;
	private DefaultTableModel model2;
	private Object[] row2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame_Stock frame = new Frame_Stock();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame_Stock() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1366, 768);
		setLocationRelativeTo(null);
		setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		scrollPane.setBackground(new Color(0, 0, 0));
		scrollPane.setBounds(320, 99, 249, 590);
		contentPane.add(scrollPane);
		
		//JTABLE 1
				table = new JTable();
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				table.setFont(new Font("Poppins", Font.PLAIN, 11));
				table.setBackground(new Color(0, 0, 0));
				model = new DefaultTableModel() {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}};
				Object[] column1 = {"Item Name"};
				Object[] row = new Object[2];
				model.setColumnIdentifiers(column1);
				table.setModel(model);
				table.getTableHeader().setReorderingAllowed(false);
				table.setShowGrid(false);
				table.setIntercellSpacing(new Dimension(0, 0));
				table.getTableHeader().setDefaultRenderer(new TableDarkHeader());
				table.setDefaultRenderer(Object.class, new TableDarkCell());
				table.setFocusable(false);
				table.setForeground(new Color(255, 255, 255));
				table.setRowHeight(30);
				
				scrollPane.setViewportView(table);
				try {//reads items.csv file
					String filePath = "src//items.csv";
					FileReader reader = new FileReader(filePath);
					BufferedReader BR = new BufferedReader(reader);
					String line;
					BR.readLine();
					String[] values;
					
					while ((line = BR.readLine())!= null) {
						values = line.split(",");
						row[0] = values[0];
						
						model.addRow(row);
					}
					BR.close();
				} catch (Exception e) {
					System.out.println("No Items yet");
				}
				
				table.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int i = table.getSelectedRow();
						model2.setRowCount(0);
						
						try { //reads history report.csv
							String filepath = "src//History Report.csv";
							String search = model.getValueAt(i, 0).toString();
							FileReader reader = new FileReader(filepath);
							BufferedReader br = new BufferedReader(reader);
							String line;
							while ((line = br.readLine()) != null) {
					                String[] data = line.split(",");
					                if (data[1].equals(search)) { //if item from History Report.csv is equal to the item selected, the list from csv file will display
										row2[0] = data[0];
										row2[1] = data[1];
										row2[2] = data[2];
										row2[3] = data[3];
										row2[4] = data[4];
										model2.addRow(row2);
										
									}
							 	}
							 br.close();
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null, "History Report.csv ERROR");
							e2.printStackTrace();
						}
						
					}
				});
				
				//JSCROLLPANE 2
				JScrollPane scrollPane2 = new JScrollPane();
				scrollPane2.setBackground(new Color(0, 0, 0));
				scrollPane2.setBounds(632, 99, 678, 590);
				contentPane.add(scrollPane2);
				
				//JTABLE 2
				table2 = new JTable();
				table2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				table2.setFont(new Font("Poppins", Font.PLAIN, 11));
				table2.setIntercellSpacing(new Dimension(0, 0));
				table2.setBackground(Color.black);
				table2.setBorder(null);
				table2.setRowHeight(30);
				table2.setForeground(new Color(255, 255, 255));
				table2.getTableHeader().setDefaultRenderer(new TableDarkHeader());
				table2.setDefaultRenderer(Object.class, new TableDarkCell());
				table2.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
					}
				});
				model2 = new DefaultTableModel() {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}};
				table2.getTableHeader().setReorderingAllowed(false);
				table2.setShowGrid(false);
				table.getTableHeader().setResizingAllowed(false);
				Object[] column2 = {"Receiver Name","Item Received", "Quantity Ordered", "Quantity Left", "Date Received"};
				
				// Get the column model of the table
				table2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

				row2 = new Object[5];
				model2.setColumnIdentifiers(column2);
				table2.setModel(model2);
				scrollPane2.setViewportView(table2);		
				
		//Set View Stocks Button
		JLabel vsButton = btn.setVsButton(contentPane);
		vsButton.removeMouseListener(vsButton.getMouseListeners()[0]);
		ImageIcon vsHover = new ImageIcon(getClass().getResource("VIEW STOCK HISTORY (HOVERED).png"));
		vsButton.setIcon(vsHover);
		contentPane.add(vsButton);
		
		//Set Modify Items Button
		JLabel miButton = btn.setMiButton(contentPane);
		miButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
			public void mouseEntered(MouseEvent e) {
    			ImageIcon miHover = new ImageIcon(getClass().getResource("MODIFY ITEMS (HOVERED).png"));
    			miButton.setIcon(miHover);
    		}
    		public void mouseExited(MouseEvent e) {
    			ImageIcon miIcon = new ImageIcon(getClass().getResource("MODIFY ITEMS.png"));
    			miButton.setIcon(miIcon);
    		}
		});
		ImageIcon miIcon = new ImageIcon(getClass().getResource("MODIFY ITEMS.png"));
    	miButton.setIcon(miIcon);
    	miButton.setBounds(41, 143, 242, 48);
		contentPane.add(miButton);
		
		//Set Download CSV Button
		JLabel dlCSVButton = btn.setDlCSVButton(contentPane);
		dlCSVButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon CSVIconHover = new ImageIcon (getClass().getResource("DOWNLOAD CSV (HOVERED).png"));
				dlCSVButton.setIcon(CSVIconHover);
			}
			public void mouseExited(MouseEvent e) {
				ImageIcon CSVIconHover = new ImageIcon (getClass().getResource("DOWNLOAD CSV.png"));
				dlCSVButton.setIcon(CSVIconHover);
			}
		});
    	ImageIcon dlIcon = new ImageIcon(getClass().getResource("DOWNLOAD CSV.png"));
    	dlCSVButton.setIcon(dlIcon);
		dlCSVButton.setBounds(41, 208, 242, 48);
		contentPane.add(dlCSVButton);
		
		//Set Back Button
		JLabel backButton = btn.setBackButton(contentPane);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				Frame_AdminPage admin = new Frame_AdminPage();
				admin.setVisible(true);
			}
		});
		contentPane.add(backButton);
		
		//Set Minimize Button
		JLabel minimize = btn.setMinimizeButton(contentPane);
		minimize.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setState(JFrame.ICONIFIED);
			}
		});
		contentPane.add(minimize);

        //Set Close Button
		JLabel close = btn.setCloseButton(contentPane);
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		contentPane.add(close);

		//Set View Stocks Page Background
		JLabel stockBG = new JLabel();
		stockBG.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				table.clearSelection();
			}
		});
		ImageIcon stockIcon = new ImageIcon(getClass().getResource("VIEW STOCK HISTORY TEMPLATE (DARK).png"));
		stockBG.setBounds(0, 0, 1366, 768);
		stockBG.setIcon(stockIcon);
		contentPane.add(stockBG);
	}
	
	//Designs the Header of the Table
	private class TableDarkHeader extends DefaultTableCellRenderer{
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			com.setBackground(new Color(30, 30, 30));
			com.setForeground(Color.white);
			
			com.setFont(new Font("Poppins", Font.BOLD, 15));
			return com;
		}
		
	}
	//Designs the Cells of the Table
	private class TableDarkCell extends DefaultTableCellRenderer{
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if (table.isCellSelected(row, column)) {
				if (row%2==0) {
					com.setBackground(new Color(33, 103, 153));
				}else {
					com.setBackground(new Color(29, 86, 127));
				}
			}else {
				if (row%2==0) {
					com.setBackground(new Color(50, 50, 50));
				}else {
					com.setBackground(new Color(30, 30, 30));
				}
			}
			if (isSelected) {
				com.setBackground(Color.YELLOW);
			}
			return com;
		}
	}
	//Designs the JScrollBar
	class CustomScrollBarUI extends BasicScrollBarUI {
	    private final int radius = 9;

	    @Override
	    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
	        Graphics2D g2 = (Graphics2D) g.create();
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2.setPaint(new Color(119, 119, 119));
	        g2.fillRoundRect(thumbBounds.x + 2, thumbBounds.y + 2, thumbBounds.width - 4, thumbBounds.height - 4, radius,
	                radius);
	        g2.setColor(new Color(150, 150, 150));
	        g2.dispose();
	    }

	    @Override
	    protected JButton createDecreaseButton(int orientation) {
	        return createZeroButton();
	    }

	    @Override
	    protected JButton createIncreaseButton(int orientation) {
	        return createZeroButton();
	    }

	    @Override
	    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
	        g.setColor(new Color(200, 200, 200));
	        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
	    }

	    private JButton createZeroButton() {
	        JButton button = new JButton();
	        Dimension zeroDim = new Dimension(0, 0);
	        button.setPreferredSize(zeroDim);
	        button.setMinimumSize(zeroDim);
	        button.setMaximumSize(zeroDim);
	        return button;
	    }
	}
}
