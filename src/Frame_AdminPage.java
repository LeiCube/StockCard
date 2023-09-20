import java.awt.Color;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Frame_AdminPage extends JFrame {
	private MenuButton btn = new MenuButton();
	private JPanel contentPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame_AdminPage frame = new Frame_AdminPage();
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
	public Frame_AdminPage() {
		setUndecorated(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1366, 768);
		setLocationRelativeTo(null);
		setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Set View Stock History (LOGO)
		JLabel vsButton = btn.setVsButton(contentPane);
		vsButton.setText("View Stocks");
		vsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
			public void mouseEntered(MouseEvent e) {
				vsButton.setForeground(new Color(76, 76, 76));
			}
			public void mouseExited(MouseEvent e) {
				vsButton.setForeground(Color.BLACK);
			}
		});
		vsButton.setForeground(Color.BLACK);
		vsButton.setFont(new Font("Poppins", Font.BOLD, 28));
		vsButton.setHorizontalAlignment(SwingConstants.CENTER);
		vsButton.setBounds(145, 426, 270, 35);
		contentPane.add(vsButton);
		
		//Set View Stock History (ICON)
		JLabel viewIcon = btn.setVsButton(contentPane);
        viewIcon.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		dispose();
        	}
        });
        viewIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ImageIcon vIcon = new ImageIcon (getClass().getResource("/White/ready-stock.png"));
        Image getVIcon = vIcon.getImage();
        viewIcon.setBounds(247, 331, 63, 63);
        Image newVIcon = getVIcon.getScaledInstance(viewIcon.getWidth(), viewIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledViewIcon = new ImageIcon(newVIcon);
        viewIcon.setIcon(scaledViewIcon);
        contentPane.add(viewIcon);
		
		//Set Modify Items Button (LABEL)
		JLabel miButton = btn.setMiButton(contentPane);
		miButton.setText("Modify Items");
		miButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
			public void mouseEntered(MouseEvent e) {
    			miButton.setForeground(new Color(76, 76, 76));
    		}
    		public void mouseExited(MouseEvent e) {
    			miButton.setForeground(Color.BLACK);
    		}
		});
		miButton.setForeground(Color.BLACK);
		miButton.setFont(new Font("Poppins", Font.BOLD, 28));
		miButton.setHorizontalAlignment(SwingConstants.CENTER);
    	miButton.setBounds(548, 426, 270, 35);
		contentPane.add(miButton);
		
		//Set Modify Items Button (ICON)
		JLabel modifyIcon = btn.setMiButton(contentPane);
        modifyIcon.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		dispose();
        	}
        });
        ImageIcon mIcon = new ImageIcon(getClass().getResource("/White/edit.png"));
        Image getMImage = mIcon.getImage();
        modifyIcon.setBounds(659, 331, 51, 51);
        Image newMImage = getMImage.getScaledInstance(modifyIcon.getWidth(), modifyIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledModifyIcon = new ImageIcon(newMImage);
        modifyIcon.setIcon(scaledModifyIcon);
        contentPane.add(modifyIcon);
		
		//Set Download CSV Button (LABEL)
		JLabel dlCSVButton = btn.setDlCSVButton(contentPane);
		dlCSVButton.setText("Download CSV");
		dlCSVButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				dlCSVButton.setForeground(new Color(76, 76, 76));
			}
			public void mouseExited(MouseEvent e) {
				dlCSVButton.setForeground(Color.BLACK);
			}
		});
		dlCSVButton.setForeground(Color.BLACK);
    	dlCSVButton.setFont(new Font("Poppins", Font.BOLD, 28));
    	dlCSVButton.setHorizontalAlignment(SwingConstants.CENTER);
		dlCSVButton.setBounds(953, 426, 270, 35);
		contentPane.add(dlCSVButton);
		
		//Set Download CSV Button (ICON)
		JLabel dlCSVIcon = btn.setDlCSVButton(contentPane);
		ImageIcon dlIcon = new ImageIcon(getClass().getResource("/White/files-medical.png"));
        Image getDLIcon = dlIcon.getImage();
        dlCSVIcon.setBounds(1060, 331, 55, 55);
        Image newDLIcon = getDLIcon.getScaledInstance(dlCSVIcon.getWidth(), dlCSVIcon.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledDLIcon = new ImageIcon(newDLIcon);
        dlCSVIcon.setIcon(scaledDLIcon);
        contentPane.add(dlCSVIcon);


		//Set Exit Admin Button
		JLabel exitAdmin = new JLabel("");
		Frame_Main main = new Frame_Main();
		exitAdmin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				main.setVisible(true);
			}
			public void mouseEntered(MouseEvent e) {
				ImageIcon exitAdminImg = new ImageIcon(getClass().getResource("EXIT ADMIN (HOVERED).png"));
				exitAdmin.setIcon(exitAdminImg);
			}
			public void mouseExited(MouseEvent e) {
				ImageIcon exitAdminImg = new ImageIcon(getClass().getResource("EXIT ADMIN.png"));
				exitAdmin.setIcon(exitAdminImg);
			}
		});
		exitAdmin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		ImageIcon exitAdminImg = new ImageIcon(getClass().getResource("EXIT ADMIN.png"));
		exitAdmin.setIcon(exitAdminImg);
		exitAdmin.setBounds(44, 708, 242, 48);
		contentPane.add(exitAdmin);
		
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
		
		//Set Admin Page Background
		JLabel adminBG = new JLabel("");
		ImageIcon adminBGImg = new ImageIcon(getClass().getResource("ADMIN TEMPLATE (DARK).png"));
		adminBG.setIcon(adminBGImg);
		adminBG.setBounds(0, 0, 1366, 768);
		contentPane.add(adminBG);
		
		
	}

}
