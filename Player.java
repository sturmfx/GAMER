
import java.awt.*;

public class Player
{
    static final Color color = Color.GREEN;
    static int counter = 0;
    int id;
    int team_id;
    double x;
    double y;
    double dx;
    double dy;
    int radius;
    int hp = 100;
    double speed;
    boolean alive;

    public Player(double start_x, double start_y, double ddx, double ddy)
    {
        alive = true;
        speed = Data.speed * 0.3;
        x = start_x;
        y = start_y;
        dx = ddx;
        dy = ddy;
        radius = 20;
        team_id = 1;
        id = counter;
        counter++;

    }

    public Player() {

    }

    public void update()
    {
        if(hp > 0)
        {
            boolean a = x + dx < Client.map.width && x + dx > 0 && y + dy < Client.map.height && y + dy > 0;
            if (a)
            {
                x+=dx;
                y+=dy;
            }
        }
        else
        {
            alive = false;
        }
    }

    public void print()
    {
        System.out.println("Bot X: " + x + ", Y: " + y + ", DX: " + dx + ", DY: " + dy + ", ALIVE: " + alive);
    }

    public static void printPlayers()
    {
        synchronized (Client.players)
        {
            System.out.println("        ");
            System.out.println("Players:");
            for(int i = 0; i < Client.players.size(); i++)
            {
                Client.players.get(i).print();
            }
            System.out.println("        ");
        }
    }
}
