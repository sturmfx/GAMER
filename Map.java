
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Map
{
    static int n = 10;
    static int min_width = 100;
    static int max_width = 2000;
    static int min_height = 100;
    static int max_height = 2000;

    double width;
    double height;
    double x;
    double y;
    ArrayList<Rectangle> walls;

    public Map(double width, double height, double x, double y)
    {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        walls = new ArrayList<>();
    }

    public static Map loadMap(String path) throws IOException
    {
        int n = 0;
        Map map = new Map(5000, 5000, 5000, 5000);
        final String path1 = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\maps\\map1.txt";
        File file = new File(path1);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        double width = 10000;
        double height = 10000;
        double x = 500;
        double y = 500;
        while((st = br.readLine()) != null)
        {
            if(n == 0)
            {
                width = Double.parseDouble(st);
                n++;
                continue;
            }

            if(n == 1)
            {
                height = Double.parseDouble(st);
                n++;
                continue;
            }

            if(n == 2)
            {
                x = Double.parseDouble(st);
                n++;
                continue;
            }

            if(n == 3)
            {
                y = Double.parseDouble(st);
                map = new Map(width, height, x, y);
                n++;
                continue;
            }

            if(n > 3)
            {
                String[] str = st.split(" ");
                //System.out.println(st);
                double width1 = Double.parseDouble(str[0]);
                double height1 = Double.parseDouble(str[1]);
                double x1 = Double.parseDouble(str[2]);
                double y1 = Double.parseDouble(str[3]);
                boolean visibility1;

                if(Double.parseDouble(str[4]) == 0)
                {
                    visibility1 = false;
                }
                else
                {
                    visibility1 = true;
                }

                boolean bullet1;

                if(Double.parseDouble(str[5]) == 0)
                {
                    bullet1 = false;
                }
                else
                {
                    bullet1 = true;
                }

                boolean player1;

                if(Double.parseDouble(str[6]) == 0)
                {
                    player1 = false;
                }
                else
                {
                    player1 = true;
                }

                int color1 = Integer.parseInt(str[7]);

                if(map != null)
                {
                    map.walls.add(new Rectangle(x1, y1, width1, height1, visibility1, bullet1, player1, Rectangle.colors[color1]));
                }

            }
        }
        return map;
    }

    public void print()
    {
        System.out.println("Map WIDTH: " + this.width + ", HEIGHT: " + this.height + ", start X: " + this.x + ", start Y: " + this.y);
        for(Rectangle rect : this.walls)
        {
            System.out.println("RECTANGLE( X = " + rect.x + ", Y = " + rect.y + ", WIDTH = " + rect.width + ", HEIGHT = " + rect.height + ")");
        }
    }

    public static Map generate()
    {
        Map map = new Map(10000, 10000, 500, 500);
        Random r = new Random();

        for(int i = 0; i < n; i++)
        {
            map.walls.add(new Rectangle(r.nextInt((int) map.width), r.nextInt((int) map.height), min_width + r.nextInt(max_width - min_width), min_height + r.nextInt(max_height - min_height),false, false, false, Color.BLACK));
        }

        return map;
    }
}
