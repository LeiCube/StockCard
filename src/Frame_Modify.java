import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class Frame_Modify extends JFrame {
	MenuButton btn = new MenuButton();
	private JPanel contentPane;
	private JTable table;
	public DefaultTableModel model;
	private JTextField itemTextField;
	private JTextField descTextField;
	private JTextField qtyTextField;
	private JTextField dateTextField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame_Modify frame = new Frame_Modify();
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
	public Frame_Modify() {
		setUndecorated(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1366, 768);
		setLocationRelativeTo(null);
		setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
		contentPane = new JPanel();
		contentPane.setForeground(new Color(192, 192, 192));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//JScrollPane
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		scrollPane.setBackground(new Color(0, 0, 0));
		scrollPane.setBounds(334, 84, 700, 585);
		contentPane.add(scrollPane);
		
		//JTABLE
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFont(new Font("Poppins", Font.PLAIN, 11));
		table.setBackground(Color.black);
		table.setBorder(null);
		table.setRowHeight(30);
		table.setForeground(new Color(255, 255, 255));
		table.setIntercellSpacing(new Dimension(0, 0));
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setDefaultRenderer(new TableDarkHeader());
		table.setDefaultRenderer(Object.class, new TableDarkCell());
		table.setFocusable(false);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
				itemTextField.setText(model.getValueAt(i, 0).toString());
				descTextField.setText(model.getValueAt(i, 1).toString());
				qtyTextField.setText(model.getValueAt(i, 2).toString());
				dateTextField.setText(model.getValueAt(i, 3).toString());
				dateTextField.setForeground(Color.black);
			}
		});
		model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}};
		Object[] column = {"Item Name", "Item Description", "Quantity", "Date Restock"};
		final Object[] row = new Object[4];
		model.setColumnIdentifiers(column);
		table.setModel(model);
		scrollPane.setViewportView(table);
		
		//reads items.csv and retrieves data
		try {
			String filePath = "src//items.csv";
			File file = new File(filePath);
			if (!file.exists()) { //if no items.csv file found, system will create one
				FileWriter writer = new FileWriter(filePath);
				writer.write("Item,Description,Quantity,Date Restock");
				writer.write("\n");
				writer.close();
			}
			
			FileReader reader = new FileReader(filePath);
			BufferedReader BR = new BufferedReader(reader);
			String line;
			BR.readLine();
			
			//retrieves data from items.csv and displays on table
			while ((line = BR.readLine())!= null) {
				String[] values = line.split(",");
				
				row[0] = values[0];
				row[1] = values[1];
				row[2] = values[2];
				row[3] = values[3];
				
				model.addRow(row);
			}
			BR.close();
		} catch (Exception e) {
			System.out.println("NO FILE YET");
		}
		
		//Item Textfield
		itemTextField = new JTextField();
		itemTextField.setFont(new Font("Poppins", Font.PLAIN, 14));
		itemTextField.setBounds(1107, 116, 180, 30);
		contentPane.add(itemTextField);
		itemTextField.setColumns(10);
		
		//Description Textfield
		descTextField = new JTextField();
		descTextField.setFont(new Font("Poppins", Font.PLAIN, 14));
		descTextField.setBounds(1107, 195, 180, 30);
		contentPane.add(descTextField);
		descTextField.setColumns(10);
		
		//Quantity Textfield
		qtyTextField = new JTextField();
		qtyTextField.setFont(new Font("Poppins", Font.PLAIN, 14));
		qtyTextField.setBounds(1107, 272, 180, 30);
		contentPane.add(qtyTextField);
		qtyTextField.setColumns(10);
		
		//Date Textfield
		dateTextField = new JTextField();
		dateTextField.setFont(new Font("Poppins", Font.PLAIN, 14));
		dateTextField.setForeground(new Color(192, 192, 192));
		dateTextField.setText("MM-DD-YYYY");
		dateTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (dateTextField.getText().equals("MM-DD-YYYY")) {
					dateTextField.setText("");
					dateTextField.setForeground(Color.black);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (dateTextField.getText().equals("")) {
					dateTextField.setText("MM-DD-YYYY");
					dateTextField.setForeground(new Color(192, 192, 192));
				}
			}
		});
		dateTextField.setBounds(1107, 346, 180, 30);
		contentPane.add(dateTextField);
		dateTextField.setColumns(10);
		
		//Adds row to the JTable based from the JTextfields
		JLabel add = new JLabel("Add");
		add.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String filepath = "src//items.csv";
				Info info = new Info();
				if (itemTextField.getText().equals("") || descTextField.getText().equals("") || qtyTextField.getText().equals("") || dateTextField.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Please Complete the Information needed");
					
				}else {
					@SuppressWarnings("unused")
					int num;
					try {
						
						FileWriter fileWriter = new FileWriter(filepath, true); //reads items.csv
						
						//encapsulation, stores the user input to the setter methods from Info class
						info.setItemString(itemTextField.getText());
						info.setDescString(descTextField.getText());
						info.setQtyString(qtyTextField.getText());
						info.setDateString(dateTextField.getText());
						
						//encapsulation, gets the user input from getter methods and writes to items.csv
						fileWriter.write(info.getItemString()+"," + info.getDescString() + "," + info.getQtyString()+ "," + info.getDateString());
						
						fileWriter.write("\n");
						fileWriter.close();

						//encapsulation, gets the user input from getter methods and writes the items in table
						row[0] = info.getItemString();
						row[1] = info.getDescString();
						row[2] = info.getQtyString();
						row[3] = info.getDateString();
						
						model.addRow(row);
						
						//clears the textFields
						itemTextField.setText("");
						descTextField.setText("");
						qtyTextField.setText("");
						dateTextField.setText("");
						
					} catch (NumberFormatException e1) {
						// Pops up when the Quantity is not an integer
						JOptionPane.showMessageDialog(null, "Please enter a WHOLE NUMBER in Quantity");
					}catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Error writing to file");
						System.out.println(e2.getMessage());
					}
				}
			}
			public void mouseEntered(MouseEvent e) {
				add.setForeground(new Color(70, 194, 99));
			}
			public void mouseExited(MouseEvent e) {
				add.setForeground(Color.BLACK);
			}
		});
		add.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add.setHorizontalAlignment(SwingConstants.CENTER);
		add.setFont(new Font("Poppins", Font.PLAIN, 17));
		add.setBounds(1114, 400, 64, 26);
		contentPane.add(add);

		//Edits the selected row
		JLabel edit = new JLabel("Edit");
		edit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
				if (i >= 0) {
					model.setValueAt(itemTextField.getText(), i, 0);
					model.setValueAt(descTextField.getText(), i, 1);
					model.setValueAt(qtyTextField.getText(), i, 2);
					model.setValueAt(dateTextField.getText(), i, 3);
					if ((itemTextField.getText().equals("") ||descTextField.getText().equals("")|| qtyTextField.getText().equals("") || dateTextField.getText().equals(""))) {
						JOptionPane.showMessageDialog(null, "Please Complete the information needed");
					}else {
						try { //reads existing items from items.csv
							ArrayList<String> lines = new ArrayList<>();
							BufferedReader reader = new BufferedReader(new FileReader("src//items.csv"));
							
							String line;
							
							while ((line = reader.readLine()) != null) { //stores data from items.csv to ArrayList lines
								lines.add(line);
							}
							reader.close();
							
							//gets data from user input and changes specific line to csv file
							if (i > -1 && i <= lines.size()) { 
								lines.set(i+1, itemTextField.getText()+ "," + descTextField.getText()+ "," + qtyTextField.getText()+ "," + dateTextField.getText());
								
							}else {
								System.out.println("Invalid");
								return;
							}
							
							//writes the updated lines to csv file
							BufferedWriter writer = new BufferedWriter(new FileWriter("src//items.csv")); 
							for (String updatedLine : lines) {
								writer.write(updatedLine);
								writer.write("\n");
							}
							writer.close();
							
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}else {
					JOptionPane.showMessageDialog(null, "Please Select Item First");
				}
			}
			public void mouseEntered(MouseEvent e) {
				edit.setForeground(new Color(88, 151, 238));
			}
			public void mouseExited(MouseEvent e) {
				edit.setForeground(Color.BLACK);
			}
		});
		edit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		edit.setHorizontalAlignment(SwingConstants.CENTER);
		edit.setFont(new Font("Poppins", Font.PLAIN, 17));
		edit.setBounds(1213, 400, 64, 26);
		contentPane.add(edit);

		//Set View Stock History Button
		JLabel vsButton = btn.setVsButton(contentPane);
		vsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
    		public void mouseEntered(MouseEvent e) {
    			ImageIcon vsHover = new ImageIcon(getClass().getResource("VIEW STOCK HISTORY (HOVERED).png"));
    			vsButton.setIcon(vsHover);
    		}
    		public void mouseExited(MouseEvent e) {
    			ImageIcon vsIcon = new ImageIcon(getClass().getResource("VIEW STOCK HISTORY.png"));
    	    	vsButton.setIcon(vsIcon);
    		}
		});
		ImageIcon vsIcon = new ImageIcon(getClass().getResource("VIEW STOCK HISTORY.png"));
    	vsButton.setIcon(vsIcon);
		contentPane.add(vsButton);
		
		//Set Modify Items Button
		JLabel miButton = btn.setMiButton(contentPane);
		miButton.removeMouseListener(miButton.getMouseListeners()[0]);
		ImageIcon miHover = new ImageIcon(getClass().getResource("MODIFY ITEMS (HOVERED).png"));
		miButton.setIcon(miHover);
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

		//Set Modify Items Page Background
		JLabel modifyBG = new JLabel();
		modifyBG.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				table.clearSelection();
				itemTextField.setText("");
				descTextField.setText("");
				qtyTextField.setText("");
				dateTextField.setText("");
			}
		});
		ImageIcon modifyBGImage = new ImageIcon(getClass().getResource("MODIFY ITEMS TEMPLATE (DARK).png"));
		modifyBG.setIcon(modifyBGImage);
		modifyBG.setBounds(0, 0, 1366, 768);
		contentPane.add(modifyBG);
		
		
	}
	//Designs the Header of the Table
	private class TableDarkHeader extends DefaultTableCellRenderer{
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			com.setBackground(new Color(30, 30, 30));
			com.setForeground(Color.white);
			com.setFont(com.getFont().deriveFont(Font.BOLD,15));
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
			}if (isSelected) {
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