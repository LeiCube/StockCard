import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicScrollBarUI;

@SuppressWarnings("serial")
public class Frame_Terms extends JFrame {
	@SuppressWarnings("unused")
	private Frame_Main mainFrame;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	Frame_Main frameMain = new Frame_Main();
                    Frame_Terms frameTerms = new Frame_Terms(frameMain);
                    frameTerms.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */	
    public Frame_Terms(Frame_Main mainFrame) {
    	addWindowListener(new WindowAdapter() {
    		@Override
    		public void windowClosed(WindowEvent e) {
    			mainFrame.setEnabled(true);
    			mainFrame.setAlwaysOnTop(true);
    		}
    	});
    	this.mainFrame = mainFrame;
        setUndecorated(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(615, 700);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
        getContentPane().setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setBounds(0, 653, 615, 33);
        panel.setBackground(new Color(255, 0, 0));
        getContentPane().add(panel);
        panel.setLayout(null);
        
        JButton close = new JButton("Close");
        close.setBorderPainted(false);
        close.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		dispose();
        	}
        });
        close.setContentAreaFilled(false);
        close.setForeground(new Color(255, 255, 255));
        close.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        close.setBounds(268, 0, 78, 33);
        close.setFont(new Font("Poppins", Font.PLAIN, 16));
        panel.add(close);
        ImageIcon termsImg = new ImageIcon(getClass().getResource("TERMS & CONDITIONS.png"));
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
        	@Override
        	public void adjustmentValueChanged(AdjustmentEvent e) {
        		JScrollBar scrollBar = (JScrollBar) e.getAdjustable();
        		if (scrollBar.getValue() + scrollBar.getVisibleAmount() == scrollBar.getMaximum()) {
        			close.setVisible(true);
        			close.setText("<html>Close</html>");
        		} else {
        			close.setVisible(false);
        		}
        	}
        });
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		scrollPane.setBackground(new Color(0, 0, 0));
        scrollPane.setBounds(0, 0, 615, 700);
        getContentPane().add(scrollPane);
        
        JLabel terms = new JLabel("");
        scrollPane.setViewportView(terms);
        terms.setIcon(termsImg);
    }

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
