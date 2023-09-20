import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("serial")
public class MenuButton extends JLabel {
	
	//View Stocks Button
    public JLabel setVsButton(JPanel contentPane) {
    	JLabel vsButton = new JLabel();
    	vsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    	vsButton.setBounds(41, 78, 242, 48);
    	contentPane.add(vsButton);
    	vsButton.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseClicked(MouseEvent e) {
    			Frame_Stock stock = new Frame_Stock();
    			stock.setVisible(true);
    		}
    	});
    	return vsButton;
	}
    
    //Modify Items Button
    public JLabel setMiButton(JPanel contentPane) {
    	JLabel miButton = new JLabel();
    	miButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    	contentPane.add(miButton);
    	miButton.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseClicked(MouseEvent e) {
    			Frame_Modify mod = new Frame_Modify();
    			mod.setVisible(true);
    		}
    	});
    	return miButton;
    }
    
    //Download CSV Button
    public JLabel setDlCSVButton(JPanel contentPane) {
    	JLabel dlCSVButton = new JLabel();
    	dlCSVButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    	contentPane.add(dlCSVButton);
    	dlCSVButton.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseClicked(MouseEvent e) {
    			try {
    				//Creates a JFileChooser Object and redirecting the default location to the Project Folder
					JFileChooser fileChooser = new JFileChooser(new File("."));
					FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("CSV File", ".csv");
					//Makes the default file to be CSV
					fileChooser.setFileFilter(csvFilter);
					fileChooser.setDialogTitle("Choose Where to save the File");
					//Gets the current date
					LocalDateTime currentDate = LocalDateTime.now();
					//Define the desired date format
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-YYYY-hh-mm");
					//Format the current Date using the formatter
					String formattedDate = currentDate.format(formatter);
					String defaultFileName = formattedDate+ " File.csv";
			        fileChooser.setSelectedFile(new java.io.File(defaultFileName));
					int response = fileChooser.showSaveDialog(null); //Select to save a file
					
					if (response == JFileChooser.APPROVE_OPTION) {
						File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
						File originalFile = new File("src//History Report.csv");
						File newFile = new File(file.toString());
						try {
							Files.copy(originalFile.toPath(), newFile.toPath());
							if (Desktop.isDesktopSupported()) {
							    // Get the Desktop instance
							    Desktop desktop = Desktop.getDesktop();
							    try {
							        // Open the saved file in the Default File explorer
							        desktop.open(file.getParentFile());
							    } catch (Exception e1) {
							    	JOptionPane.showMessageDialog(null, "Error in Opening the File\n");
							        e1.printStackTrace();
							    }
							}
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null, "Please rename the file \n"
									+ "or choose other location\n");
						}
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "ERROR Saving the file");
				}
    		}
    	});
    	return dlCSVButton;
    }
    
    //Back Button
    public JLabel setBackButton(JPanel contentPane) {
    	JLabel backButton = new JLabel();
    	ImageIcon vsIcon = new ImageIcon(getClass().getResource("BACK.png"));
    	backButton.setIcon(vsIcon);
    	backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    	backButton.setBounds(55, 710, 242, 48);
    	contentPane.add(backButton);
    	backButton.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseEntered(MouseEvent e) {
    			ImageIcon backHover = new ImageIcon(getClass().getResource("BACK (HOVERED).png"));
    			backButton.setIcon(backHover);
    		}
    		public void mouseExited(MouseEvent e) {
    			ImageIcon backHover = new ImageIcon(getClass().getResource("BACK.png"));
    			backButton.setIcon(backHover);
    		}
    	});
    	return backButton;
    }
  
    //Minimize Button
    public JLabel setMinimizeButton(JPanel contentPane) {
    	JLabel minimize = new JLabel("");
    	minimize.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ImageIcon icon = new ImageIcon(getClass().getResource("/White/minimize.png"));
        Image image = icon.getImage();
        minimize.setBounds(1281, 14, 19, 19);
        Image newImage = image.getScaledInstance(minimize.getWidth(), minimize.getWidth(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(newImage);
        minimize.setIcon(scaledIcon);
        minimize.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	minimize.setOpaque(true);
            	minimize.setBackground(new Color(67, 67, 67));

            }
            public void mouseExited(MouseEvent e) {
            	minimize.setOpaque(false);
            	minimize.setBackground(null);
            }
        });
        return minimize;
    }
    
    //Close Button
    
    
    public JLabel setCloseButton(JPanel contentPane) {
    	JLabel close = new JLabel("");
    	close.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    	ImageIcon icon = new ImageIcon(getClass().getResource("/White/close.png"));
    	Image image = icon.getImage();
    	close.setBounds(1333, 14, 19, 19);
    	Image newImage = image.getScaledInstance(close.getWidth(), close.getHeight(), Image.SCALE_SMOOTH);
    	ImageIcon scaledIcon = new ImageIcon(newImage);
    	close.setIcon(scaledIcon);
    	close.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseEntered(MouseEvent e) {
    			close.setOpaque(true);
    			close.setBackground(new Color(67, 67, 67));
    			
    		}
    		public void mouseExited(MouseEvent e) {
    			close.setOpaque(false);
    			close.setBackground(null);
    		}
    	});
    	return close;
    }
}
