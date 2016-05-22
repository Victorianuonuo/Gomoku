import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class WaitForConnectionDialog extends JDialog{

	public WaitForConnectionDialog(Frame parent,String situation) {
		// TODO Auto-generated constructor stub
		super(parent,"Waiting...");
		setLayout(new BorderLayout());
		JProgressBar jProgressBar=new JProgressBar();
		jProgressBar.setIndeterminate(true);
		JLabel jLabel=new JLabel();
		jLabel.setText(situation);
		add(jProgressBar,BorderLayout.CENTER);
		add(jLabel, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(null);
		System.err.println(situation);
	}
	
}
