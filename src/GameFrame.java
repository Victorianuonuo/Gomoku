import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.security.KeyStore.PrivateKeyEntry;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.TitlePaneLayout;

public class GameFrame extends JFrame{

	private Board board;
	private boolean canMove=false;
	private boolean end=false;
	private JButton giveupButton;
	private JTextArea messageArea;
	private JPanel titlePanel;
	private JLabel vsLabel;
	private String yourname,rivalname;
    private int place;
    private String[] Colors={"BLACK","WHITE"};
	private final BlockingQueue<Object> out=MessageBus.getMessageBus().getOrCreateChannel("OUT");
	private final BlockingQueue<Object> in=MessageBus.getMessageBus().getOrCreateChannel("IN");
	
	
	
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
						
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
			}
		});
		vsLabel.setText("Waiting...");
		titlePanel.setLayout(new GridLayout(1, 3));
		titlePanel.add(new JLabel());
		titlePanel.add(vsLabel);
		titlePanel.add(giveupButton);
		add(titlePanel, BorderLayout.NORTH);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosed(e);
				System.exit(1);
			}
		});
		add(messageArea, BorderLayout.EAST);
		pack();
	}
	
	public void setMove(boolean canMove){
		this.canMove=canMove;	
	}
	

	public void setPlace(int place){
		this.place=place;
	}
	
	public void setName(String yourname,String rivalname){
		this.yourname=yourname;
		this.rivalname=rivalname;
	}
	
	public void start(){
		
	}
	
}
