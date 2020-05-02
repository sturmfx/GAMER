
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Main
{
    static int n = 30;
    static int min_width = 100;
    static int max_width = 2000;
    static int min_height = 100;
    static int max_height = 2000;

    public static void main(String[] args) throws InterruptedException
    {
        Map map = new Map(10000, 10000, 500, 500);
        Random r = new Random();

        for(int i = 0; i < n; i++)
        {
            map.walls.add(new Rectangle(r.nextInt((int) map.width), r.nextInt((int) map.height), min_width + r.nextInt(max_width - min_width), min_height + r.nextInt(max_height - min_height),false, false, false, Color.BLACK));
        }

        MiniMap.init(map);
        save(MiniMap.map_image);
        Thread.sleep(5000);
        save(MiniMap.minimap_image);
        save(MiniMap.getMiniMap(7000, 3000, 1600, 800));
    }

    public static void save(BufferedImage bi)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy--HH-mm-ss");
        Date date = new Date();
        final String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\generated\\image" + formatter.format(date) +  ".png";
        try
        {
            final RenderedImage ri =  bi;
            ImageIO.write(ri, "png", new File(path));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
