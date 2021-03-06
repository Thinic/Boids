package tenikkan.boids;

import java.awt.Color;

public class Boid
{
    public Vec2 position;
    public Vec2 direction;
    public Color color;
    
    public static boolean followMouse = false;
    
    public static double speed = 5.0;
    
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
        
        Vec2 acc = getNewDirection(boids);
        
        direction.x += acc.x * delta * 30;
        direction.y += acc.y * delta * 30;
        direction = direction.normalized();
        
        position.x += direction.x * delta * speed;
        position.y += direction.y * delta * speed;
        
        if(position.x < 0) direction.x += 0.5;
        if(position.x > 1024 / Display.scale) direction.x -= 0.5;
        if(position.y < 0) direction.y += 0.5;
        if(position.y > 768 / Display.scale) direction.y -= 0.5;
    }
    
    private Vec2 getNewDirection(Boid[] boids) 
    {
        Vec2 v1 = getDirectionTowardsCenterOfMass(boids);
        Vec2 v2 = getAverageDirection(boids);
        Vec2 v3 = getSteerAwayDirection(boids);
        Vec2 v4 = getMouseMove();
        
        Vec2 res = new Vec2(0, 0);
        res.x = (v1.x*1 + v2.x*2 + v3.x*4);
        res.y = (v1.y*1 + v2.y*2 + v3.y*4);
        
        if(followMouse) 
        {
            res.x += v4.x * 1;
            res.y += v4.y * 1;
        }
        
        return res.normalized();
    }
    
    private Vec2 getMouseMove() 
    {
        // go to
//        return new Vec2(Mouse.x / Display.scale - position.x, Mouse.y / Display.scale - position.y).normalized();
        // stay away
        if(!collision(
                new Vec2(Mouse.x/Display.scale, Mouse.y/Display.scale),
                World.radius / 2,
                position,
                World.radius / 2
                ))
            return new Vec2(0, 0);
        return new Vec2(-Mouse.x / Display.scale + position.x, -Mouse.y / Display.scale + position.y).normalized();
    }
    
    private Vec2 getSteerAwayDirection(Boid[] boids) 
    {
        Vec2 dir = new Vec2(0.0, 0.0);
        
//        int count = 0;
        for(Boid b : boids) 
        {
            if(!collision(position, World.pushRadius / 2.0, b.position, World.pushRadius / 2.0)) 
                continue;
            double dx = (position.x - b.position.x);
            double dy = (position.y - b.position.y);
            
            if(dx != 0) dir.x += World.pushRadius / dx;
            if(dy != 0) dir.y += World.pushRadius / dy;
//            count++;
        }
        
//        if(count > 0) 
//        {
//            dir.x /= count;
//            dir.y /= count;
//        }
        
        return dir.normalized();
    }
    
    private Vec2 getDirectionTowardsCenterOfMass(Boid[] boids) 
    {
        Vec2 dir = new Vec2(0.0, 0.0);
        if(boids.length == 0) return dir;
        
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
        if(boids.length == 0) return dir;
        
        for(Boid b : boids) 
        {
            dir.x += b.direction.x;
            dir.y += b.direction.y;
        }
        
        dir.x /= boids.length;
        dir.y /= boids.length;
        
        return dir.normalized();
    }
    
    private boolean collision(Vec2 pos1, double rad1, Vec2 pos2, double rad2) 
    {
        double x = pos1.x - pos2.x;
        double y = pos1.y - pos2.y;
        double r = rad1 + rad2;
        return x * x + y * y <= r * r;
    }
    
    public String toString() 
    {
        return "Boid[x="+position.x+",y="+position.y+",dx="+position.x+",dy="+position.x+"]";
    }
}

