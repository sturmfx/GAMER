

import java.awt.*;
import java.util.ArrayList;

public class Bullet
{
    static final Color color = Color.RED;
    static int id_counter = 0;
    int id;
    int team_id;
    double x;
    double y;
    double dx;
    double dy;
    final double start_x;
    final double start_y;
    final double click_x;
    final double click_y;
    long start;
    long end;
    int radius;
    double speed;
    boolean alive;
    int damage;

    public Bullet(int team, int damage1,  double st_x, double st_y, double cl_x, double cl_y)
    {
        start = System.currentTimeMillis();
        alive = true;
        team_id = team;
        radius = 5;
        speed = 0.003;
        damage = damage1;
        id = id_counter;
        id_counter++;
        start_x = st_x;
        start_y = st_y;
        click_x = cl_x;
        click_y = cl_y;
        x = start_x;
        y = start_y;

        double temp_dx = click_x - start_x;
        double temp_dy = click_y - start_y;

        double temp_c = Math.sqrt(temp_dx * temp_dx + temp_dy * temp_dy);
        double k = temp_c / speed;
        dx = temp_dx / k;
        dy = temp_dy / k;
    }

    public Bullet(int team, int damage1,  double st_x, double st_y, double cl_x, double cl_y, double ddx, double ddy)
    {
        start = System.currentTimeMillis();
        alive = true;
        team_id = team;
        radius = 5;
        speed = 0.003;
        damage = damage1;
        id = id_counter;
        id_counter++;
        start_x = st_x;
        start_y = st_y;
        click_x = cl_x;
        click_y = cl_y;
        x = start_x;
        y = start_y;


        dx = ddx;
        dy = ddy;
    }



    public void update(ArrayList<Rectangle> rectangles, ArrayList<Player> players)
    {
        boolean a = x + dx < Client.map.width && x + dx > 0 && y + dy < Client.map.height && y + dy > 0;
        if(a)
        {
            for (Rectangle rect : rectangles)
            {
                if(rect.contains(x, y, dx, dy))
                {
                    alive = false;
                    end = System.currentTimeMillis();
                    break;
                }
                else
                {
                    if(team_id == 0)
                    {
                        for (Player player : players)
                        {
                            if(player.alive && alive)
                            {
                                if (radius + player.radius >= Math.sqrt((x - player.x) * (x - player.x) + (y - player.y) * (y - player.y)))
                                {
                                    player.hp = player.hp - damage;
                                    alive = false;
                                    end = System.currentTimeMillis();
                                    break;
                                }
                            }
                        }
                        x += dx;
                        y += dy;
                    }
                    else
                    {
                        if(radius + Data.player_radius >= Math.sqrt((x - Client.x) * (x - Client.x) + (y - Client.y) * (y - Client.y)))
                        {
                            Data.hp = Data.hp - damage;
                            alive = false;
                            end = System.currentTimeMillis();
                            break;
                        }
                        else
                        {
                            x += dx;
                            y += dy;
                        }
                    }
                }
            }
        }
        else
        {
            alive = false;
            end = System.currentTimeMillis();
        }
    }

    public void print()
    {
        System.out.println("Bullet START_X: " + start_x + ", START_Y: " + start_y + ", CLICK_X: " + click_x + ", CLICK_Y: " + click_y + ", TEAM: " + team_id + ", ALIVE: " + alive + ", DX: " + dx + ", DY: " + dy);
    }
}
