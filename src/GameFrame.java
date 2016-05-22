import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.BlockingDeque;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class GameFrame extends JFrame{

	private Board board;
	private boolean turn=false;
	private boolean end=false;
	private JButton giveupButton;
	private JTextArea messageArea;
	private JLabel stateLabel;
	private final BlockingQueque<Object> out=MessageBus.getMessageBus().getOrCreateChannel("OUT");
	private final BlockingDeque<Object> in=MessageBus.getMessageBus().getOrCreateChannel("IN");
	
	
	public GameFrame() {
		// TODO Auto-generated constructor stub
		super("Five In A Row");
		board=new Board();
		setLayout(new BorderLayout());
		add(board,BorderLayout.CENTER);
		giveupButton=new JButton("Give up");
		giveupButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!end){
					end=true;
					try{
						
					}
				}
			}
		});
		add(giveupButton, BorderLayout.EAST);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosed(e);
				System.exit(1);
			}
		});
		add(giveupButton, BorderLayout.SOUTH);
		add(messageArea, BorderLayout.EAST);
		
	}
	
	public setTurn(boolean turn){
		this.turn=turn;
		
	}
	
}
