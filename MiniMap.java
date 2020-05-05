

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class MiniMap
{
    static final double width_limit = 300;
    static final double height_limit = 300;
    static BufferedImage result;
    static BufferedImage map_image;
    static BufferedImage minimap_image;
    static double map_width;
    static double map_height;
    static double mini_width;
    static double mini_height;

    static double k;

    public static void init(Map map)
    {
        map_image = new BufferedImage((int) map.width, (int) map.height, BufferedImage.TYPE_INT_ARGB);

        map_width = map.width;
        map_height = map.height;

        drawMapImage(map);

        if(map_width >= map_height)
        {
            k = map_width / width_limit;
            mini_width = width_limit;
            mini_height = map_height / k;
        }
        else
        {
            k = map_height / height_limit;
            mini_height = height_limit;
            mini_width = map_width / k;
        }

        minimap_image = new BufferedImage((int) mini_width, (int) mini_height, BufferedImage.TYPE_INT_ARGB);

        final AffineTransform at = AffineTransform.getScaleInstance(1/k, 1/k);
        final AffineTransformOp ato = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);

        minimap_image = ato.filter(map_image, minimap_image);

    }

    public static void drawMapImage(Map map)
    {
        Graphics2D g2d = (Graphics2D) map_image.getGraphics().create();

        for(Rectangle rect : map.walls)
        {
            g2d.setColor(rect.color);
            g2d.fillRect((int) rect.x, (int) rect.y, (int) rect.width, (int) rect.height);
        }
    }

    public static BufferedImage getMiniMap(double x, double y, double width, double height)
    {
        result = clone(minimap_image);
        Graphics2D g2d = (Graphics2D) result.getGraphics().create();

        g2d.setColor(Color.BLUE);
        g2d.fillOval((int)(x / k) - 5, (int)(y/k) - 5, 10, 10);
        g2d.drawRect((int)((x - width * 0.5) / k), (int)((y - height * 0.5) / k), (int)(width / k), (int)(height / k));

        for(int i = 0; i < Client.players.size(); i++)
        {
            g2d.setColor(Color.RED);
            g2d.fillOval((int)(Client.players.get(i).x / k) - 2, (int)(Client.players.get(i).y / k) - 2, 4, 4);
        }
        return result;
    }

    public static final BufferedImage clone(BufferedImage image)
    {
        BufferedImage clone = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D g2d = clone.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return clone;
    }
}
