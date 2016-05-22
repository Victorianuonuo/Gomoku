import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SetNameFrame extends JFrame{
	private JLabel welcome;
	private JLabel setName;
	private JPanel name;
	private JButton start;
	private JTextField username;
    public SetNameFrame() {
		// TODO Auto-generated constructor stub
    	 super("Five In a Row");
    	 welcome.setText("Welcome!");
    	 setName.setText("Enter your name: ");
    	 name.setLayout(new GridLayout(1,2));
    	 name.add(setName);
    	 name.add(username);
    	 start.setText("Start!");
    	 start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(username.getText().isEmpty())
					return;
				FindRivalFrame findRivalFrame=new FindRivalFrame(username.getText());
				SetNameFrame.this.setVisible(false);
				findRivalFrame.setVisible(true);
			}
		});
    	 setLayout(new BorderLayout());
    	 add(welcome,BorderLayout.NORTH);
    	 add(name, BorderLayout.CENTER);
    	 add(start, BorderLayout.SOUTH);	 
    	 pack();
    	 setVisible(true);
    	
	}    
     
}
