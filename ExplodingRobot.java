import greenfoot.*;
import java.util.List;

public class ExplodingRobot extends Robot {
    private int explosionRadius = 100;
    private int explosionDamage = 999;
    private int detectionRange = 300;

    public ExplodingRobot(int health, double speed, int range, int damage) {
        super(health, speed, range, damage);
    }

    public void act() {
        if (getHealth() <= 0) {
            explode();
            return;
        }

        Human closest = getClosestHuman();
        if (closest != null) {
            double dist = getDistanceTo(closest);

            if (dist <= explosionRadius) {
                explode();
                return;
            }

            if (dist <= detectionRange) {
                turnTowards(closest);
            }
        }

        move(getSpeed());
    }

    private Human getClosestHuman() {
        List<Human> humans = getWorld().getObjects(Human.class);
        Human closest = null;
        double minDist = Double.MAX_VALUE;
        for (Human h : humans) {
            double d = getDistanceTo(h);
            if (d < minDist) {
                minDist = d;
                closest = h;
            }
        }
        return closest;
    }

    private double getDistanceTo(Actor a) {
        double dx = getPreciseX() - ((SuperSmoothMover)a).getPreciseX();
        double dy = getPreciseY() - ((SuperSmoothMover)a).getPreciseY();
        return Math.hypot(dx, dy);
    }

    private void explode() {
        List<Human> humans = getObjectsInRange(explosionRadius, Human.class);
        for (Human h : humans) {
            h.takeDamage(explosionDamage);
        }
        getWorld().removeObject(this);
    }
}
