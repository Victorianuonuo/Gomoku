import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
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
					Thread clientThread=new ClientThread();
					
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
        	Thread handlerThread=new Thread(new Thread("message-thread"){
        		@Override
        		public void run() {
        			// TODO Auto-generated method stub
        			
        		}
        	});
        	pack();
        	setVisible(true);
        	
		}
}
