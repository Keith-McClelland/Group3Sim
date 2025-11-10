import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

public abstract class Units extends SuperSmoothMover
{
    protected int health;
    protected int maxHealth;
    protected double speed;
    protected int damage; 
    protected boolean isRobot;
    protected static int numUnits;
    protected int range;

    protected SuperStatBar healthBar;
    
    public Units(int health, double speed, int range, int damage, boolean isRobot){
        this.health = health;
        this.maxHealth = health; 
        this.speed = speed;
        this.range = range;
        this.damage = damage;
        this.isRobot = isRobot;
        numUnits++;
    }

    public int getHealth(){
        return health;
    }

    public void setHealth(int hp){
        this.health = hp;
        updateHealthBar();
    }

    public boolean isRobot() {
        return isRobot;
    }
    
    public double getSpeed(){
        return speed;
    }

    public void takeDamage(int dmg) {
        health -= dmg;
        updateHealthBar();
        if (health <= 0) {
            if (getWorld() != null) {
                getWorld().removeObject(healthBar);
                getWorld().removeObject(this);
            }
        }
    }

    public abstract void attack(); 
    
    public void act(){
        if (isRobot){
            move(speed);
        } else {
            move(-speed);
        }
        updateHealthBar(); 
    }

    protected void addedToWorld(World world) {
        healthBar = new SuperStatBar(maxHealth, health, this, 40, 6, 30, Color.GREEN, Color.RED, true, Color.BLACK, 1);
        world.addObject(healthBar, getX(), getY() + 30);
    }

    /** Keeps the health bar in sync with the unit’s current health */
    protected void updateHealthBar() {
        if (healthBar != null) {
            healthBar.update(health);
        }
    }
}
