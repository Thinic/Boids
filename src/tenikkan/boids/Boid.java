package tenikkan.boids;

import java.awt.Color;

public class Boid
{
    public Vec2 position;
    public Vec2 direction;
    public Color color;
    
    public Boid(Vec2 pos, Vec2 dir, int col) 
    {
        position = pos;
        direction = dir;
        color = new Color(col);
    }
    
    public void update(World world) 
    {
        double delta = 1 / 60.0;
        
        Boid[] boids = world.getBoidsAroundPosition(this);
        
        direction = getNewDirection(boids);
        
        position.x += direction.x * delta * 1.5;
        position.y += direction.y * delta * 1.5;
    }
    
    private Vec2 getNewDirection(Boid[] boids) 
    {
        Vec2 v1 = getDirectionTowardsCenterOfMass(boids);
        Vec2 v2 = getAverageDirection(boids);
        Vec2 v3 = getSteerAwayDirection(boids);
        
        Vec2 res = new Vec2(0, 0);
        res.x = (v1.x + v2.x + v3.x) / 3;
        res.y = (v1.y + v2.y + v3.y) / 3;
        
        return res.normalized();
    }
    
    // avg dir for now
    private Vec2 getSteerAwayDirection(Boid[] boids) 
    {
        Vec2 dir = new Vec2(0.0, 0.0);
        
        for(Boid b : boids) 
        {
            dir.x += b.direction.x;
            dir.y += b.direction.y;
        }
        
        dir.x /= boids.length;
        dir.y /= boids.length;
        
        return dir.normalized();
    }
    
    private Vec2 getDirectionTowardsCenterOfMass(Boid[] boids) 
    {
        Vec2 dir = new Vec2(0.0, 0.0);
        
        for(Boid b : boids) 
        {
            dir.x += b.position.x - position.x;
            dir.y += b.position.y - position.y;
        }
        
        dir.x /= boids.length;
        dir.y /= boids.length;
        
        return dir.normalized();
    }
    
    private Vec2 getAverageDirection(Boid[] boids) 
    {
        Vec2 dir = new Vec2(0.0, 0.0);
        
        for(Boid b : boids) 
        {
            dir.x += b.direction.x;
            dir.y += b.direction.y;
        }
        
        dir.x /= boids.length;
        dir.y /= boids.length;
        
        return dir.normalized();
    }
    
    public String toString() 
    {
        return "Boid[x="+position.x+",y="+position.y+",dx="+position.x+",dy"+position.x+"]";
    }
}

