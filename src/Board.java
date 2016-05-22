import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
    private Check ck;
    
    public Board() {
		// TODO Auto-generated constructor stub
    	setPreferredSize(new Dimension(CELL_SIZE * 19, CELL_SIZE * 19));
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
				int xx = e.getX() - CELL_SIZE / 2;
                int yy = e.getY() - CELL_SIZE / 2;
                int x = (xx + CELL_SIZE / 2) / CELL_SIZE;
                int y = (yy + CELL_SIZE / 2) / CELL_SIZE;
                if (x >= 0 && x < 19 && y >= 0 && y < 19 && x * CELL_SIZE - CIRCLE_RADIUS <= xx && x * CELL_SIZE + CIRCLE_RADIUS >= xx &&
                        y * CELL_SIZE - CIRCLE_RADIUS <= yy && y * CELL_SIZE + CIRCLE_RADIUS >= yy)
                    if (ck!=null) {
                        	if(ck.check(x, y, points))
                        		Board.this.repaint();
                    }
			}
		});
    	
    	
	}
    
    public Map<Point, Integer> getPoints() {
        return points;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        for (int i = 0; i < 19; ++i)
            g.drawLine(CELL_SIZE / 2 + i * CELL_SIZE, CELL_SIZE / 2, CELL_SIZE / 2 + i * CELL_SIZE, CELL_SIZE / 2 + CELL_SIZE * 18);
        for (int j = 0; j < 19; ++j)
            g.drawLine(CELL_SIZE / 2, CELL_SIZE / 2 + j * CELL_SIZE, CELL_SIZE / 2 + CELL_SIZE * 18, CELL_SIZE / 2 + j * CELL_SIZE);
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
    
    public Check getCheck(){
    	return ck;
    }
    
    public void setCheck(Check ck){
    	this.ck=ck;
    }
	
    public interface Check {
		boolean check(int x, int y, Map<Point, Integer> points);
	}
    
}
