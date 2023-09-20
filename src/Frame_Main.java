import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import java.awt.Cursor;

@SuppressWarnings("serial")
public class Frame_Main extends JFrame {
	private Frame_Terms termsFrame;
	private JPanel contentPane;
	private JTable table;
	public DefaultTableModel model;
	private JTextField nameTextField;
	private JTextField itemTextField;
	private boolean termAccepted = false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame_Main frame = new Frame_Main();
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
	public Frame_Main() {
		setUndecorated(true);
		setTitle("Stock Card Inventory System");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1366, 768);
		setLocationRelativeTo(null);
		setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//JSpinner
		SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, 100, 1);
		JSpinner spinner = new JSpinner(spinnerModel);
		spinner.setUI(new SpinnerUI());
		spinner.setOpaque(false);
		spinner.setFont(new Font("Poppins", Font.PLAIN, 14));
		Component [] components = spinner.getComponents();
		for (Component component : components) {
			if (component instanceof JButton) {
				JButton button = (JButton) component;
				
				button.setBackground(new Color(67, 67, 67)); // Set background color
				button.setForeground(Color.WHITE); // Set foreground (text) color
				button.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add a border

			}
		}
		
		//JScrollPane
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		scrollPane.setBackground(new Color(0, 0, 0));
		scrollPane.setBounds(168, 131, 1032, 454);
		contentPane.add(scrollPane);
		
		//Creates table
		table = new JTable();
		table.setFont(new Font("Poppins", Font.PLAIN, 11));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}};
		Object[] column = {"Item Name", "Quantity"};
		Object[] row = new Object[2];
		table.setRowHeight(30);
		table.setForeground(new Color(255, 255, 255));
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.setIntercellSpacing(new Dimension(0, 0));
		table.getTableHeader().setDefaultRenderer(new TableDarkHeader());
		table.setDefaultRenderer(Object.class, new TableDarkCell());
		table.setFocusable(false);
		model.setColumnIdentifiers(column);
		table.setModel(model);
		scrollPane.setViewportView(table);
		
		//reads content of items.csv to display available items
		try {
			String filePath = "src//items.csv";
			File file = new File(filePath);
			if (!file.exists()) { //if no file exists
				FileWriter writer = new FileWriter(filePath);
				writer.write("Item,Description,Quantity,Date of Restock,");
				writer.write("\n");
				writer.close();
			}
			
			
			FileReader reader = new FileReader(filePath);
			BufferedReader BR = new BufferedReader(reader);
			String line;
			BR.readLine();
			
			//displays content of file to table
			while ((line = BR.readLine())!= null) {
				String[] values = line.split(",");
				
				row[0] = values[0];
				row[1] = values[2];
				
				model.addRow(row);
			}
			BR.close();
		} catch (Exception e) {
			System.out.println("No Items yet");
		}
		//Sets the Item textfield by the selected row
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
				itemTextField.setText(model.getValueAt(i, 0).toString());
			}
		});
		
		//Name JTextfield
		nameTextField = new JTextField();
		nameTextField.setFont(new Font("Poppins", Font.PLAIN, 14));
		nameTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		nameTextField.setBounds(201, 644, 224, 30);
		contentPane.add(nameTextField);
		nameTextField.setColumns(10);
		
		//Sets the Maximum value of the JSpinner based on the selected row
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// Get the selected row index
				if (!e.getValueIsAdjusting()) {
					int selectedRow = table.getSelectedRow();
					// Update the spinner maximum value based on the selected row
					if (selectedRow != -1) {
						Object valueObject = table.getValueAt(selectedRow, 1);
						if (valueObject instanceof Integer) {
							int maxNum = (int)valueObject;
							spinnerModel.setMaximum(maxNum);
						}else if (valueObject instanceof String) {
							try {
								 int maxNum = Integer.parseInt((String) valueObject);
	                                spinnerModel.setMaximum(maxNum);
							} catch (Exception e2) {
								JOptionPane.showMessageDialog(null, "ERROR\n"+"Maximun Number of Item");
							}
						}
					}
				}
				
			}
		});		
		spinner.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		spinner.setBounds(712, 644, 100, 30);
		contentPane.add(spinner);
		
		//CONFIRM BUTTON
		JButton confirmButton = new JButton("Confirm");
		confirmButton.setContentAreaFilled(false);
		confirmButton.setBorderPainted(false);
		confirmButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		confirmButton.setFont(new Font("Poppins", Font.PLAIN, 14));
		confirmButton.setOpaque(false);
		confirmButton.setEnabled(termAccepted);
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setAlwaysOnTop(false);
				int i = table.getSelectedRow();
				if (i < 0) {
					JOptionPane.showMessageDialog(null, "Please Select an Item First");
				}else if (nameTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please Enter your Name First");
				}else if (spinner.getValue().equals(0)) {
					JOptionPane.showMessageDialog(null, "Please Enter Quantity to Receive");
				}else {
					String message = "   Review Items to Receive:\n\n" 
							+ "   Item:   " + model.getValueAt(i, 0)  + "\n"
							+ "   Quantity:   " + spinner.getValue()+ "\n"
							+ "   Received by:   " + nameTextField.getText() + "\n\n";
			int response = JOptionPane.showConfirmDialog(null, message, "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			
			if (response == JOptionPane.YES_OPTION) {
				
				//Gets the current date
				LocalDateTime currentDate = LocalDateTime.now();
				//Define the desired date format
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY-hh:mm");
				//Format the current Date using the formatter
				String formattedDate = currentDate.format(formatter);
				//gets the value from the JSpinner
				int qtyOrdered = (int)spinner.getValue();
				//gets from total Quantity
				int selectedRow = table.getSelectedRow();
				Object totalQtyFromRow = table.getValueAt(selectedRow, 1);
				int totalQuantity = Integer.parseInt(totalQtyFromRow.toString());
				int qtyLeft = totalQuantity - qtyOrdered;
				String itemOrdered = (String) table.getValueAt(selectedRow, 0);
				
				try {
					String filepath = "src//History Report.csv";
					File file = new File(filepath);
					
					//Creates a file if file does not exists yet
					if (!file.exists()) {
						FileWriter writer = new FileWriter(filepath);
						writer.write("Name of Receiver,Item Ordered,Quantity Ordered,Quantity Left,Date");
						writer.write("\n");
						writer.close();
					}
					
					//Add values to the history file
					FileWriter fileWriter = new FileWriter(filepath, true);
					fileWriter.write(nameTextField.getText() + "," 
											+ itemOrdered + ","
											+ qtyOrdered + ","
											+ qtyLeft + ","
											+ formattedDate);
					fileWriter.write("\n");
					fileWriter.close();
					
					//Reads the items.csv and stores it in the Arraylist
					try {
						ArrayList<String> lines = new ArrayList<>();
						BufferedReader reader = new BufferedReader(new FileReader("src//items.csv"));
						
						String line;
						
						while ((line = reader.readLine()) != null) {
							lines.add(line);
						}
						reader.close();
						
						if (i > -1 && 1 <= lines.size()) {
							String updatedLine = lines.get(i + 1);
							String[] values = updatedLine.split(",");
							if (values.length >= 4) {
								//Updates the quantity in the Table
								values[2] = Integer.toString(qtyLeft);
								//Reconstruct the Updated line
								updatedLine = String.join(",", values);
								lines.set(i + 1, updatedLine);
							}else {
								System.out.println("Invalid line format");
								return;
							}
						}else {
							System.out.println("Invalid Index");
							return;
						}
					//Writes the updated line on the csv	
					BufferedWriter writer = new BufferedWriter(new FileWriter("src//items.csv"));
					for (String updatedLine : lines) {
						writer.write(updatedLine);
						writer.write("\n");
					}
					writer.close();
					} catch (Exception e2) {
						e2.printStackTrace();
						JOptionPane.showMessageDialog(null, "items.csv Error");
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "CSV File Error");
					e1.printStackTrace();
				}
				dispose();
//						table.setVisible(true);
				Frame_Main frame = new Frame_Main();
				frame.setVisible(true);
				spinner.setValue(1);
				
			}else if (response == JOptionPane.NO_OPTION) {
				
			}
				}
			}
		});
		confirmButton.setBounds(1098, 647, 107, 27);
		contentPane.add(confirmButton);
		
		//Item Textfield
		itemTextField = new JTextField();
		itemTextField.setEditable(false);
		itemTextField.setFont(new Font("Poppins", Font.PLAIN, 14));
		itemTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		itemTextField.setBounds(452, 644, 224, 30);
		contentPane.add(itemTextField);
		
		//JCheckbox
		JCheckBox checkbox = new JCheckBox("");
		checkbox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		checkbox.setFont(new Font("Poppins", Font.PLAIN, 11));
		checkbox.setOpaque(false);
		checkbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkbox.isSelected()) {
					termAccepted = true;
					confirmButton.setEnabled(termAccepted);
				}else {
					confirmButton.setEnabled(false);
				}
			}
		});
		checkbox.setFocusable(false);
		checkbox.setBounds(201, 688, 25, 25);
		contentPane.add(checkbox);
		
		JLabel termsLabel = new JLabel("<html>Terms & Conditions</html>");
		termsLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		termsLabel.setFont(new Font("Poppins", Font.PLAIN, 12));
		termsLabel.setForeground(new Color(255, 255, 255));
		termsLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
		        termsFrame = new Frame_Terms(Frame_Main.this); // Pass the current Frame_Main instance to Frame_Terms
		        termsFrame.setVisible(true);
		        termsFrame.setAlwaysOnTop(true);
		        setEnabled(false); // Disable the current Frame_Main instance

			}
			public void mouseEntered(MouseEvent e) {
				termsLabel.setText("<html><u>Terms & Conditions</u></html>");
			}
			public void mouseExited(MouseEvent e) {
				termsLabel.setText("<html>Terms & Conditions</html>");
			}
		});
		termsLabel.setBounds(228, 688, 154, 23);
		contentPane.add(termsLabel);
		
		//Set clickable DPWH Logo for admin page
		JLabel DPWH = new JLabel("");
		DPWH.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		DPWH.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setAlwaysOnTop(false);
				JPasswordField adminPass = new JPasswordField(24);
				JLabel txt = new JLabel ("Enter Admin Password: ");
				Box box = Box.createHorizontalBox();
				box.add(txt);
				box.add(adminPass);
				int result = JOptionPane.showConfirmDialog(null, box, "Enter Password", JOptionPane.OK_CANCEL_OPTION);
				
				if (result == JOptionPane.OK_OPTION) {
					char[] pass = adminPass.getPassword();
					String enteredPass = new String(pass);
					
					if (enteredPass.equals("a")) {
						dispose();
						Frame_AdminPage ap = new Frame_AdminPage();
						ap.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "Incorrect password!");
					}
				}
			}
		});
		ImageIcon dpwhLogo = new ImageIcon(getClass().getResource("dpwh.png"));
		Image getDPWH = dpwhLogo.getImage();
		DPWH.setBounds(663, 3, 41, 41);
		Image newDPWH = getDPWH.getScaledInstance(DPWH.getHeight(), DPWH.getWidth(), Image.SCALE_SMOOTH);
		ImageIcon newScaledDPWH = new ImageIcon(newDPWH);
		DPWH.setIcon(newScaledDPWH);
		contentPane.add(DPWH);
		
		//Clears the itemTextfield and the Value of the JSpinner if mainBG is clicked
		JLabel mainBG = new JLabel("");
		mainBG.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				table.clearSelection();
				itemTextField.setText("");
				spinner.setValue(0);
			}
		});
		ImageIcon mainBGImg = new ImageIcon(getClass().getResource("MAIN TEMPLATE (DARK).png"));
		mainBG.setIcon(mainBGImg);
		mainBG.setBounds(0, 0, 1366, 768);
		contentPane.add(mainBG);
		
		
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
