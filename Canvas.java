
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class Canvas extends JPanel implements MouseListener
{
    BufferedImage bi;
    double width;
    double height;

    public Canvas(double width, double height)
    {
        super();
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension((int) this.width, (int) this.height));
    }

    private void doDraw(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;



        for(Rectangle rect : Client.map.walls)
        {
            g2d.setColor(rect.color);
            g2d.fillRect((int)(rect.x - Client.x + width * 0.5), (int)(rect.y - Client.y + height * 0.5), (int)rect.width, (int)rect.height);
        }

        synchronized (Client.bullets)
        {
            for (Bullet b : Client.bullets)
            {
                if (b.alive)
                {
                    g2d.setColor(Bullet.color);
                    g2d.fillOval((int) (b.x - Client.x + width * 0.5 - b.radius), (int) (b.y - Client.y + height * 0.5 - b.radius), 2 * b.radius, 2 * b.radius);

                }
            }
        }

        synchronized (Client.players)
        {
            for(Player player : Client.players)
            {
                if(player.alive)
                {
                    g2d.setColor(Player.color);
                    g2d.fillOval((int) (player.x - Client.x + width * 0.5 - player.radius), (int) (player.y - Client.y + height * 0.5 - player.radius), 2 * player.radius, 2 * player.radius);
                    g2d.setColor(Color.RED);
                    g2d.fillRect((int) ((int) player.x - Client.x + width * 0.5 - 2.5 * player.radius), (int) ((int) player.y- Client.y + height * 0.5 - 1.5 * player.radius), 100, 5);
                    g2d.setColor(Color.GREEN);
                    g2d.fillRect((int) ((int) player.x - Client.x + width * 0.5- 2.5 * player.radius), (int) ((int) player.y - Client.y + height * 0.5 - 1.5 * player.radius), player.hp, 5);
                }
            }
        }
        //g2d.drawImage(MiniMap.getMiniMap(Client.x, Client.y, Client.width, Client.height), (int) (Client.width - MiniMap.mini_width), (int) Client.height, null);
        bi = MiniMap.getMiniMap(Client.x, Client.y, Client.width, Client.height);

        g2d.clearRect((int)Client.width - bi.getWidth(), 0, bi.getWidth(), bi.getHeight());

        g2d.drawImage(bi, (int)Client.width - bi.getWidth(), 0, bi.getWidth(), bi.getHeight(), null);

        g2d.setColor(Color.GREEN);
        g2d.drawRect((int)Client.width - bi.getWidth(), 0, bi.getWidth(), bi.getHeight());

        g2d.setColor(Color.BLUE);
        g2d.fillOval((int) width/2 - Data.player_radius, (int) height/2 - Data.player_radius, 2 * Data.player_radius, 2 * Data.player_radius);
        g2d.setColor(Color.RED);
        g2d.fillRect((int) ((int) width * 0.5 - 2.5 * Data.player_radius), (int) ((int) height * 0.5 - 1.5 * Data.player_radius), 100, 5);
        g2d.setColor(Color.GREEN);
        g2d.fillRect((int) ((int) width  * 0.5 - 2.5 * Data.player_radius), (int) ((int) height * 0.5 - 1.5 * Data.player_radius), Data.hp, 5);

    }



    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        doDraw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();

        double temp_dx = x - Client.width * 0.5;
        double temp_dy = y - Client.height * 0.5;

        double temp_c = Math.sqrt(temp_dx * temp_dx + temp_dy * temp_dy);
        double k = temp_c / Data.speed;

        double dx = temp_dx / k;
        double dy = temp_dy / k;
        synchronized(Client.temp_bullets)
        {
            Client.temp_bullets.add(new Bullet(0, Data.damage, Client.x, Client.y, (e.getX() + Client.x - width * 0.5), (e.getY() + Client.y - height * 0.5), dx, dy));
        }

       // Player.printPlayers();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
