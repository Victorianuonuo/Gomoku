import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.security.KeyStore.PrivateKeyEntry;
import java.util.Map;
import java.util.PrimitiveIterator.OfDouble;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
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
	private String yourname,rivalname;
    private int place;
    private Check check=new Check();
    final Object lock=new Object();
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
		titlePanel.setLayout(new GridLayout(1, 3));
		titlePanel.add(new JLabel());
		titlePanel.add(new JLabel());
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
		Thread handler=new Thread("handler"){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try{
					while(!this.isInterrupted()){
						Object message=in.take();
						System.err.println(message);
						if(message instanceof String){
							String data=(String) message;
							if(data.startsWith("MOVE")){
								final String[] parts=data.split("\\s+");
								SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        check.move(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                                        Map<Point, Integer> points = GameFrame.this.board.getPoints();
                                        points.clear();
                                        for (int i = 0; i < 19; ++i)
                                            for (int j = 0; j < 19; ++j)
                                                if (check.map[i][j] != 0) {
                                                    points.put(new Point(i, j), check.map[i][j]);
                                                }
                                        board.repaint();
                                        setMove(true);
                                    }
                                });
							}else if(data.startsWith("GIVEUP")){
                                SwingUtilities.invokeLater(new Runnable() {
									
									@Override
									public void run() {
										// TODO Auto-generated method stub
										messageArea.append("END\n"+check.getLoser()+" give up\n");
										messageArea.append("So the winner is "+check.getWinner());
									}
								});
								
							}else if(data.startsWith("END")){
								SwingUtilities.invokeLater(new Runnable() {
									
									@Override
									public void run() {
										// TODO Auto-generated method stub
										messageArea.append("END\nThe winner is "+check.getWinner()+"\n");
										
									}
								});
							}else if(message instanceof Exception){
								final Exception exception = (Exception) message;
	                            SwingUtilities.invokeLater(new Runnable() {
	                                @Override
	                                public void run() {
	                                    JOptionPane.showMessageDialog(GameFrame.this, exception.toString(), "Error", JOptionPane.ERROR_MESSAGE);
	                                    System.exit(2);
	                                }
	                            });
							}
						}
					}
				}catch (InterruptedException ignored) {

                }
			}
		};
		handler.setDaemon(true);
		handler.start();
	}
	
}
