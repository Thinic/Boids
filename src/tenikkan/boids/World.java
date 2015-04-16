package tenikkan.boids;

import java.util.ArrayList;

public class World
{
    private ArrayList<Boid> boids = new ArrayList<Boid>();
    
    public double radius = 4.0;
    
    public World() 
    {
        
    }
    
    public void update() 
    {
        for(Boid b : boids) 
            b.update(this);
    }
    
    public Boid[] getBoids() 
    {
        return (Boid[])boids.toArray(new Boid[boids.size()]);
    }
    
    public void addBoid(Boid boid) 
    {
        boids.add(boid);
    }
    
    public void removeBoid(Boid boid) 
    {
        boids.remove(boid);
    }
    
    public Boid[] getBoidsAroundPosition(Boid boid) 
    {
        Vec2 position = boid.position;
        
        ArrayList<Boid> close = new ArrayList<Boid>();
        
        for(Boid b : boids) 
        {
            if(collision(position, radius / 2, b.position, radius / 2))
                close.add(b);
        }
        
        return (Boid[])close.toArray(new Boid[close.size()]);
    }
    
    private boolean collision(Vec2 pos1, double rad1, Vec2 pos2, double rad2) 
    {
        double x = pos1.x - pos2.x;
        double y = pos1.y - pos2.y;
        double r = rad1 + rad2;
        return x * x + y * y <= r * r;
    }
}
