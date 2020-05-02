
import java.awt.*;
import java.util.ArrayList;

public class Rectangle
{
    public static Color[] colors = {Color.BLACK, Color.YELLOW, Color.GREEN, Color.RED};
    final double x;
    final double y;
    final double width;
    final double height;
    final boolean visibility;
    final boolean bullet;
    final boolean player;
    final Color color;

    final double[] xs = new double[4];
    final double[] ys = new double[4];

    public Rectangle(double x, double y, double width, double height, boolean visibility, boolean bullet, boolean player, Color color)
    {
        this.color = color;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.visibility = visibility;
        this.bullet = bullet;
        this.player = player;

        xs[0] = this.x;
        ys[0] = this.y;

        xs[1] = this.x + width;
        ys[1] = this.y;

        xs[2] = this.x + width;
        ys[2] = this.y + height;

        xs[3] = this.x;
        ys[3] = this.y + height;
    }

    public boolean contains(double x, double y, double dx, double dy)
    {
        if(x + dx >= this.x && x + dx <= this.x + this.width && y + dy >= this.y && y + dy <= this.y + this.height)
        {
           return true;
        }
        else
        {
            return false;
        }
    }



    public static Point updatedPoint(double x, double y, double dx, double dy, double width, double height, ArrayList<Rectangle> rectangles)
    {
        boolean x_y = true;
        boolean x_ = true;
        boolean y_ = true;

        for(Rectangle rect : rectangles)
        {
            if(rect.contains(x, y, dx, dy))
            {
                x_y = x_y && false;
            }

            if(rect.contains(x, y, 0, dy))
            {
                x_ = x_ && false;
            }

            if(rect.contains(x, y, dx, 0))
            {
                y_ = y_ && false;
            }
        }

        boolean a = x + dx < width && x + dx > 0 && y + dy < height && y + dy > 0;

        if(x_y)
        {
            if(a)
            {
                return new Point(x + dx, y + dy);
            }
            else
            {
                return new Point(x, y);
            }
        }
        else
        {
            if(x_)
            {
                if(a)
                {
                    return new Point(x + dx, y);
                }
                else
                {
                    return new Point(x, y);
                }
            }
            else
            {
                if(y_)
                {
                    if(a)
                    {
                        return new Point(x, y + dy);
                    }
                    else
                    {
                        return new Point(x, y);
                    }
                }
                else
                {
                    return new Point(x, y);
                }
            }
        }
    }
}
