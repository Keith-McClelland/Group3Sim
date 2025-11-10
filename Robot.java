import greenfoot.*;
import java.util.ArrayList;

public abstract class Robot extends Units {
    private static int numRobots = 0;

    private int attackCooldown = 0;
    private static final int attackDelay = 50;

    protected Robot(int health, double speed, int range, int damage) {
        super(health, speed, range, damage, true);
        numRobots++;
    }

    public void act() {
        super.act(); 
        if (attackCooldown > 0) attackCooldown--;

        if (attackCooldown == 0) {
            attack();
            attackCooldown = attackDelay;
        }

        if (getHealth() <= 0) {
            getWorld().removeObject(this);
        }
    }

    public void attack() {
        ArrayList<Human> targets = new ArrayList<>(getObjectsInRange(range, Human.class));
        for (Human h : targets) {
            h.takeDamage(damage);
        }
    }

    public static int getNumRobots() {
        return numRobots;
    }

    public void removedFromWorld(World world) {
        numRobots--;
    }
}
