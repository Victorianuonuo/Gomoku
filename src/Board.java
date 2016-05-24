import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ObjectOutputStream.PutField;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;


public class Board extends JComponent{

	public final int STATUS_BLACK = 1;
    public final int STATUS_WHITE = 2;
    private final int CELL_SIZE = 30;
    private final int CIRCLE_RADIUS = 12;
    private Map<Point, Integer> points = new HashMap<>();
    private boolean end=false;
    private Put put;
    
    public Board() {
		// TODO Auto-generated constructor stub
    	setPreferredSize(new Dimension(CELL_SIZE *16, CELL_SIZE * 16));
    	setMaximumSize(getPreferredSize());
    	setMinimumSize(getPreferredSize());
    	addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(end)
					return;
				int xx = e.getX() - CELL_SIZE / 2;
                int yy = e.getY() - CELL_SIZE / 2;
                int x = (xx + CELL_SIZE / 2) / CELL_SIZE;
                int y = (yy + CELL_SIZE / 2) / CELL_SIZE;
                if (x >= 0 && x < 16 && y >= 0 && y < 16 && x * CELL_SIZE - CIRCLE_RADIUS <= xx && x * CELL_SIZE + CIRCLE_RADIUS >= xx &&
                        y * CELL_SIZE - CIRCLE_RADIUS <= yy && y * CELL_SIZE + CIRCLE_RADIUS >= yy)
                if(put!=null){
                	if(put.canPut(x, y, points))
                     Board.this.repaint();
                }
                	
			}
		});
    	
    	
	}
    
    public void setEnd(boolean end) {
		this.end = end;
	}
    
    public Map<Point, Integer> getPoints() {
        return points;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        for (int i = 0; i < 16; ++i)
            g.drawLine(CELL_SIZE / 2 + i * CELL_SIZE, CELL_SIZE / 2, CELL_SIZE / 2 + i * CELL_SIZE, CELL_SIZE / 2 + CELL_SIZE * 15);
        for (int j = 0; j < 16; ++j)
            g.drawLine(CELL_SIZE / 2, CELL_SIZE / 2 + j * CELL_SIZE, CELL_SIZE / 2 + CELL_SIZE * 15, CELL_SIZE / 2 + j * CELL_SIZE);
        for (Map.Entry<Point, Integer> entry : points.entrySet()) {
            Point point = entry.getKey();
            if (entry.getValue().equals(STATUS_WHITE)) {
                g.setColor(Color.WHITE);
            } else g.setColor(Color.BLACK);
            g.fillArc(CELL_SIZE / 2 + CELL_SIZE * point.x - CIRCLE_RADIUS, CELL_SIZE / 2 + CELL_SIZE * point.y - CIRCLE_RADIUS, CIRCLE_RADIUS * 2, CIRCLE_RADIUS * 2, 0, 360);
            g.setColor(Color.BLACK);
            g.drawArc(CELL_SIZE / 2 + CELL_SIZE * point.x - CIRCLE_RADIUS, CELL_SIZE / 2 + CELL_SIZE * point.y - CIRCLE_RADIUS, CIRCLE_RADIUS * 2, CIRCLE_RADIUS * 2, 0, 360);
        }
    }
    
    public Put getPut(){
    	return put;
    }
    
    public void setPut(Put put){
    	this.put=put;
    }
	
    public interface Put{
    	boolean canPut(int x,int y,Map<Point, Integer> points);
    }
    
}
