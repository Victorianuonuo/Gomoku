import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FindRivalFrame extends JFrame{
	    JLabel userLabel,vsLabel,rivalLabel;
	    JPanel titlePanel,buttonPanel;
	    JButton createButton,joinButton;
	    private ThreadGroup threadGroup;
	    private DefaultListModel<Rivals> model;
	    private Rivals rivalTobe;
	    final JList<Rivals> list;
	    private String username,rivalname;
	    
        public FindRivalFrame(String username) {
			// TODO Auto-generated constructor stub
        	super("Seeking your rival");
        	this.username=username;
        	userLabel.setText(username);
        	vsLabel.setText("VS");
        	titlePanel.setLayout(new GridLayout(1, 3));
        	titlePanel.add(userLabel);
        	titlePanel.add(vsLabel);
        	titlePanel.add(rivalLabel);
        	buttonPanel.setLayout(new GridLayout(1,3));
        	createButton.setText("Create");
        	createButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(threadGroup!=null){
						threadGroup.interrupt();
						threadGroup=null;
					}
					Thread serverThread=new ServerThread();
					serverThread.setDaemon(true);
					serverThread.start();
					final WaitForConnectionDialog waitDialog=new WaitForConnectionDialog(FindRivalFrame.this, "Wait for another player to join");
					waitDialog.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							// TODO Auto-generated method stub
							super.windowClosed(e);
							System.err.println("Exit at the waiting process");
							System.exit(0);
						}
					});
					Thread handlerThread=new Thread(new Thread("message-thread"){
		        		@Override
		        		public void run() {
		        			// TODO Auto-generated method stub
		        			Thread handler=new Broadcast("Start the game by "+username);
		        			handler.setDaemon(true);
		        			handler.start();
		        			try{
		        				Board gameFrame=new Board();
			        			gameFrame.setTurn(true);
			        			gameFrame.setVisible(true);
			        			gameFrame.start();
			        			waitDialog.setVisible(false);
			        			waitDialog.dispose();
		        			}catch (InterruptedException ignored) {
		        				
	                        }
		        			handler.interrupt();
		        		}
		        	});
					 handlerThread.setDaemon(true);
					 handlerThread.start();
					 FindRivalFrame.this.setVisible(false);
					 waitDialog.setVisible(true);
				}
			});
        	joinButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(rivalname==null)
						return;
					final WaitForConnectionDialog waitDialog=new WaitForConnectionDialog(FindRivalFrame.this, "Waiting for connection...");
					waitDialog.setModal(true);
					Thread clientThread=new ClientThread("client-thread",rivalTobe.getAddress());
					clientThread.setDaemon(true);
					clientThread.start();
					Thread handlerThread=new Thread(new Thread("join-thread"){
		        		@Override
		        		public void run() {
		        			// TODO Auto-generated method stub
		        			try {
	                            final Object respond = MessageBus.getMessageBus().waitForChannel("OK");
	                            System.err.println(respond);
	                            if (respond instanceof Exception) {
	                                SwingUtilities.invokeLater(new Runnable() {
	                                    @Override
	                                    public void run() {
	                                        JOptionPane.showMessageDialog(FindRivalFrame.this, ((Exception) respond).getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	                                        waitDialog.setVisible(false);
	                                    }
	                                });
	                            } else if ("SUCCESSFUL".equals((String) respond)) {
	                                SwingUtilities.invokeLater(new Runnable() {
	                                    @Override
	                                    public void run() {
	                                        if (threadGroup != null)
	                                            threadGroup.interrupt();
	                                        waitDialog.setVisible(false);
	                                        Board gameFrame = new Board();
	                                        FindRivalFrame.this.setVisible(false);
	                                        gameFrame.setLocationRelativeTo(null);
	                                        gameFrame.setVisible(true);
	                                        gameFrame.start();
	                                    }
	                                });
	                            }
	                        } catch (InterruptedException ignored) {
	                        }
		        		}
		        	});
					handlerThread.setDaemon(true);
					handlerThread.start();
					waitDialog.setVisible(true);
				}
			});
        	buttonPanel.add(createButton);
        	buttonPanel.add(new JLabel());
        	buttonPanel.add(joinButton);
        	model=new DefaultListModel<Rivals>();
        	list=new JList<Rivals>(model);
        	setLayout(new BorderLayout());
        	add(titlePanel, BorderLayout.NORTH);
        	add(buttonPanel, BorderLayout.SOUTH);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    Rivals RivalsToCheck = list.getSelectedValue();
                    if (RivalsToCheck != null) {
                    	rivalTobe=RivalsToCheck;
                    	rivalname=rivalTobe.getName();
                    	rivalLabel.setText(rivalname);
                    }
                }
            });
        	add(new JScrollPane(list),BorderLayout.CENTER);
        	pack();
        	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        	setLocationRelativeTo(null);
        	setVisible(true);
        	process();
		}
        
        private void process(){
        	threadGroup=new ThreadGroup("thread-group");
        	Thread listener=new Listener(threadGroup,"listener");
        	listener.setDaemon(true);
        	listener.start();
        	final Map<Rivals,Long> timeToTrace=new LinkedHashMap<>();
        	Thread hander=new Thread(threadGroup,"handler"){
        		@Override
        		public void run() {
        			// TODO Auto-generated method stub
        			while(!this.isInterrupted()){
        				try{
        					Rivals rival=(Rivals)MessageBus.getMessageBus().waitForChannel("Seeking");
        					synchronized (timeToTrace) {
								timeToTrace.put(rival,(new Date()).getTime());
							}
        				}catch(InterruptedException ignored){
        					return ;
        				}
        			}
        		}
        	};
        	hander.setDaemon(true);
        	hander.start();
        	Thread fetcher=new Thread(threadGroup,"fetcher"){
        		@Override
        		public void run() {
        			// TODO Auto-generated method stub
        			while(!this.isInterrupted()){
        				model.clear();
        				synchronized (timeToTrace) {
							for(Map.Entry<Rivals,Long> entry: timeToTrace.entrySet()){
								if((new Date()).getTime()-entry.getValue()<20000){
									model.addElement(entry.getKey());
								}
							}
						}
        				try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ignored) {
                            return;
                        }
        			}
        		}
        	};
        	fetcher.setDaemon(true);
        	fetcher.start();
        }
}
