package tenikkan.boids;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener
{
    public static int x = 0;
    public static int y = 0;
    
    public void mouseClicked( MouseEvent arg0 )
    {
        x = arg0.getX();
        y = arg0.getY();
    }

    public void mouseEntered( MouseEvent arg0 )
    {
    }

    public void mouseExited( MouseEvent arg0 )
    {
    }

    public void mousePressed( MouseEvent arg0 )
    {
    }

    public void mouseReleased( MouseEvent arg0 )
    {
    }
    
}
