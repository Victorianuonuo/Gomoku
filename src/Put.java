import java.awt.Point;
import java.util.Map;

public interface Put{
    	boolean canPut(int x,int y,Map<Point, Integer> points);
    }