
import java.util.ArrayList;

public class Way
{
    static double delta = 3;
    int index = 0;
    int size = 0;
    ArrayList<Point> points;

    public Way(Point p)
    {
        points = new ArrayList<>();
        points.add(p);
        size = points.size();
    }

    public void add(Point point)
    {
        synchronized(points)
        {
            points.add(point);
            size++;
        }
    }

    public void getTargetPoint(Point point)
    {
        synchronized(points)
        {
            Point p = points.get(index);
            if(Math.abs(p.x - point.x) < delta && Math.abs(p.y - point.y) < delta)
            {

            }
        }
    }

}
