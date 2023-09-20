import java.awt.TextField;

import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSpinnerUI;

public class SpinnerUI extends BasicSpinnerUI {
	@Override
	public void installUI(JComponent c) {
		// TODO Auto-generated method stub
		super.installUI(c);
		//spinner.setEditor(new Editor(spinner));
	}
	
	@SuppressWarnings("serial")
	public class Editor extends TextField implements ChangeListener{
		public Editor(JSpinner spinner) {
			spinner.addChangeListener(this);
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			JSpinner spinner = (JSpinner) e.getSource();
			setText(spinner.getValue().toString());
		}
	}
}
