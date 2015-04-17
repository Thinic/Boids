package tenikkan.boids;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Display
{
    private JFrame frame;
    private Canvas canvas;
    
    private BufferedImage img;
    
    private Mouse mouse;
    
    public static double scale = 50.0;
    
    public static boolean debug = false;
    
    public Display(String title, int width, int height) 
    {
        frame = new JFrame(title);
        
        canvas = new Canvas();
        canvas.setSize(width, height);
        
        frame.add(canvas);
        frame.pack();
        
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        mouse = new Mouse();
        canvas.addMouseListener(mouse);
    }
    
    public void render(World world) 
    {
        Graphics2D g = (Graphics2D)img.getGraphics();
        int w = img.getWidth();
        int h = img.getHeight();
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, w, h);
        
        int rad = (int)scale / 5;
        for(Boid b : world.getBoids()) 
        {
            g.setColor(Color.WHITE);
            g.drawLine((int)(b.position.x * scale), (int)(b.position.y * scale), 
                    (int)(b.position.x * scale + b.direction.x * rad * 2), (int)(b.position.y * scale + b.direction.y * rad * 2));
            g.setColor(b.color);
            g.fillOval((int)(b.position.x * scale - rad), (int)(b.position.y * scale - rad), rad * 2, rad * 2);
            
            if(debug)
            {
                g.setColor(Color.RED);
                g.drawOval(
                        (int)(b.position.x * scale - world.radius * scale), 
                        (int)(b.position.y * scale - world.radius * scale), 
                        (int)(world.radius * scale * 2), 
                        (int)(world.radius * scale * 2));
                g.setColor(Color.GREEN);
                g.drawOval(
                        (int)(b.position.x * scale - world.pushRadius * scale), 
                        (int)(b.position.y * scale - world.pushRadius * scale), 
                        (int)(world.pushRadius * scale * 2), 
                        (int)(world.pushRadius * scale * 2));
            }
        }
        
        g.setColor(Color.GREEN);
        if(!Boid.followMouse) g.setColor(Color.GRAY);
        g.fillOval(
                (int)(Mouse.x - 10), 
                (int)(Mouse.y - 10), 
                (int)(20), 
                (int)(20));
        
        g.dispose();
    }
    
    public void show() 
    {
        BufferStrategy bs = canvas.getBufferStrategy();
        if(bs == null)
        {
            canvas.createBufferStrategy(3);
            return;
        }
        Graphics2D g = (Graphics2D)bs.getDrawGraphics();
        
        g.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight(), canvas);
        
        g.dispose();
        bs.show();
    }
}
