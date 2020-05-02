

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Client extends JFrame implements KeyListener
{
    static Map map;
    static ArrayList<Player> players = new ArrayList<>();
    static ArrayList<Bullet> bullets = new ArrayList<>();
    static ArrayList<Bullet> temp_bullets = new ArrayList<>();
    static Timer timer;
    static Random random;
    static Canvas canvas;

    static boolean go = true;
    static long counter = 0;
    static int direction = 5;

    static volatile double x;
    static volatile double y;
    static volatile double dx;
    static volatile double dy;

    static double width;
    static double height;

    boolean isW = false;
    boolean isA = false;
    boolean isS = false;
    boolean isD = false;

    public Client(double width, double height) throws IOException
    {
        random  = new Random();
        //map = Map.loadMap("dfgdfg");
        map = Map.generate();
        map.print();
        x = map.x;
        y = map.y;
        MiniMap.init(map);

        map.walls.add(new Rectangle(-10, -10, map.width + 20, 10, false, false, false, Color.RED));
        map.walls.add(new Rectangle(-10, 0, 10, map.height, false, false, false, Color.RED));
        map.walls.add(new Rectangle(-10, map.height, map.width + 20, 10, false, false, false, Color.RED));
        map.walls.add(new Rectangle(map.width, 0, 10, map.height, false, false, false, Color.RED));
        initUI(width, height);
        initDrawTimer();
    }

    private void initUI(double width, double height)
    {
        Client.width = width;
        Client.height = height;
        canvas = new Canvas(Client.width, Client.height);
        canvas.setDoubleBuffered(true);
        add(canvas);
        pack();
        setVisible(true);
        setTitle("GAME");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.addMouseListener(canvas);
        addKeyListener(this);
    }

    private void initDrawTimer()
    {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                if(go)
                {
                    counter++;
                    updateDirection();
                    updatePlayer();
                    updateBullets();
                    updateBots();

                    if(counter % 20 == 0)
                    {
                        repaint();
                    }

                    if(counter % 21 == 0)
                    {
                        addBullets();
                    }

                    if(counter % 103 == 0)
                    {
                        for(int i = 0; i < bullets.size(); i++)
                        {
                            if(!bullets.get(i).alive)
                            {
                                bullets.remove(i);
                            }
                        }

                    }

                    if(counter % 3007 == 0 && players.size() < Data.player_limit)
                    {
                        addBot();
                    }

                    if(counter % 5011 == 0)
                    {
                        for(int i = 0; i < players.size(); i++)
                        {
                            if(!players.get(i).alive)
                            {
                                players.remove(i);
                            }
                        }
                    }
                }
            }
        }, 0, 1);
    }

    private void updateDirection()
    {
        int direction = 5;

        if(isW && isA && !isS && !isD)
        {
            direction = 1;
            dx = Data.player_speed * Data.delta * -1;
            dy = Data.player_speed * Data.delta * -1;
        }

        if(isW && !isA && !isS && !isD)
        {
            direction = 2;
            dy = Data.player_speed * -1;
            dx = 0;
        }

        if(isW && !isA && !isS && isD)
        {
            direction = 3;
            dx = Data.player_speed * Data.delta;
            dy = Data.player_speed * Data.delta * -1;
        }

        if(!isW && isA && !isS && !isD)
        {
            direction = 4;
            dx = Data.player_speed * -1;
            dy = 0;
        }

        if(!isW && !isA && !isS && !isD)
        {
            direction = 5;
            dx = 0;
            dy = 0;
        }

        if(!isW && !isA && !isS && isD)
        {
            direction = 6;
            dx = Data.player_speed;
            dy = 0;
        }

        if(!isW && isA && isS && !isD)
        {
            direction = 7;
            dx = Data.player_speed * Data.delta * -1;
            dy = Data.player_speed * Data.delta;
        }

        if(!isW && !isA && isS && !isD)
        {
            direction = 8;
            dy = Data.player_speed;
            dx = 0;
        }

        if(!isW && !isA && isS && isD)
        {
            direction = 9;
            dx = Data.player_speed * Data.delta;
            dy = Data.player_speed * Data.delta;
        }

        this.direction = direction;

    }

    private void updatePlayer()
    {
        Point p = Rectangle.updatedPoint(Client.x, Client.y, Client.dx, Client.dy, map.width, map.height, map.walls);
        x = p.x;
        y = p.y;
    }

    private void updateBullets()
    {
        synchronized (bullets)
        {
            for (Bullet b : bullets)
            {
                if (b.alive)
                {
                    b.update(map.walls, players);
                }
            }
        }
    }

    private synchronized void addBullets()
    {

                for (Bullet b : temp_bullets)
                {
                    bullets.add(b);
                }

                temp_bullets.clear();
    }

    private void addBot()
    {
        boolean a  = true;
        Random r = new Random();
        int x = 100;
        int y = 100;
        int target_x;
        int target_y;
        double dx;
        double dy;

        while(a)
        {
            x = r.nextInt((int) map.width);
            y = r.nextInt((int) map.height);
            for(Rectangle rect : map.walls)
            {
                if(!rect.contains(x, y, 0, 0))
                {
                    a = false;
                }
            }
        }
        target_x = r.nextInt((int) map.width);
        target_y = r.nextInt((int) map.height);
        double temp_dx = target_x - x;
        double temp_dy = target_y - y;
        double temp_c = Math.sqrt(temp_dx * temp_dx + temp_dy * temp_dy);
        double k = temp_c / Data.speed;
        dx = temp_dx / k;
        dy = temp_dy / k;

        players.add(new Player(x, y,dx, dy));
    }

    private void updateBots()
    {
        synchronized (players)
        {
            for (Player player : players)
            {
                if (player.alive)
                {
                    player.update();
                }
            }
        }
    }

    public void print_bullets()
    {
        System.out.println("                        ");
        System.out.println("                        ");
        System.out.println("Bullets in temp_bullets:");
        for(Bullet b : temp_bullets)
        {
            b.print();
        }
        System.out.println("                        ");
        System.out.println("Bullets in bullets:");
        for(Bullet b : bullets)
        {
            b.print();
        }
    }


    public static void main(String[] args) throws IOException
    {
        System.setProperty("sun.java2d.opengl", "true");
        Client client = new Client(1600, 800);
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyChar() == 'w') isW = true;
        if(e.getKeyChar() == 'a') isA = true;
        if(e.getKeyChar() == 's') isS = true;
        if(e.getKeyChar() == 'd') isD = true;
        if(e.getKeyChar() == 'W') isW = true;
        if(e.getKeyChar() == 'A') isA = true;
        if(e.getKeyChar() == 'S') isS = true;
        if(e.getKeyChar() == 'D') isD = true;
        updateDirection();
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyChar() == 'w') isW = false;
        if(e.getKeyChar() == 'a') isA = false;
        if(e.getKeyChar() == 's') isS = false;
        if(e.getKeyChar() == 'd') isD = false;
        if(e.getKeyChar() == 'W') isW = false;
        if(e.getKeyChar() == 'A') isA = false;
        if(e.getKeyChar() == 'S') isS = false;
        if(e.getKeyChar() == 'D') isD = false;
        updateDirection();
    }
}
