/*
 * Created by JFormDesigner on Sat Dec 10 19:23:57 GMT 2011
 */



import java.awt.*;
import javax.swing.*;


public class WaitDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1756232325738727502L;
	public WaitDialog() {
		initComponents();
	}
	
	public void isVisible(boolean b){
		this.setVisible(b);
	}
	
	private void initComponents() {
		
	    Icon warnIcon = new ImageIcon("book.gif");
	    label1 = new JLabel(warnIcon);

		//======== Label with Image ========
		{
			this.setTitle("Waiting Dialog");
			Container dialog1ContentPane = this.getContentPane();
			dialog1ContentPane.add(label1);
			this.pack();
			this.setLocationRelativeTo(this.getOwner());
		}
	}

	private JLabel label1;
}

