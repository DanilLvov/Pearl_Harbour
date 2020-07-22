package objects;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.Main;

import java.util.ArrayList;

public class US_Fighter extends Jet {

    private double m_Cannon1X = (19.5 * 2) - 59;
    private double m_Cannon1Y = 8 - 47;
    private double m_Cannon2X = (38.5 * 2) - 59;
    private double m_Cannon2Y = 8 - 47;
    private final double ATTACK_RANGE = 350;
    private int counter = 0;
    private int timer = 0;
    private ArrayList<Bullet> bullets;
    private Jet m_EnemyPlane;
    protected Ship m_EnemyShip;

    public US_Fighter(String name, double positionX, double positionY, Pane group, Ship ship, Ship enemyShip) {

        this(name, positionX, positionY, ship, enemyShip);

        viewName = "US_fighter.png";
        viewNameActive = "US_fighter_active.png";

        m_Nation = "US";

        // створення зображення літаку
        m_View = new ImageView(TextureManager
                .textures
                .get(viewName));

        //додавання усіх 3-х графічних примітивів до групи
        m_Group.getChildren().addAll(m_View, nameLine, m_healthBar);
        group.getChildren().add(m_Group);

        m_Type = "Fighter";
    }

    protected US_Fighter(String name, double positionX, double positionY, Ship ship, Ship enemyShip) {
        super(name, positionX, positionY, ship);
        bullets = new ArrayList<>();
        m_EnemyShip = enemyShip;
    }

    @Override
    public void update() {
        if(m_State != State.DEAD && m_State != State.ON_PARADE) {
            updateFighter();
            bulletUpdate();
            defaultUpdate();
        }
    }

    private void updateFighter(){
        if( m_State == State.MOVING_TO_ENEMY || m_State == State.AROUND_CARRIER || m_State == State.MOVING_TO_CARRIER || m_State == State.ATTACK) {
            checkEnemy();
        }
        switch (m_State) {
            case AROUND_CARRIER:
                moveToPoint(m_Ship.getCarrierProtectX()[counter], m_Ship.getCarrierProtectY()[counter]);
                if (checkInRange (m_Ship.getCarrierProtectX()[counter], m_Ship.getCarrierProtectY()[counter], m_CenterX, m_CenterY, 10)) {
                    counter++;
                }
                if(counter == 8) counter = 0;
                break;

            case MOVING_TO_ENEMY:
                moveToPoint(m_EnemyShip.getCenterX(), m_EnemyShip.getCenterY());
                if (checkInRange (m_EnemyShip.getCenterX(), m_EnemyShip.getCenterY(), m_CenterX, m_CenterY, 40)) {
                    m_State = State.MOVING_TO_CARRIER;
                }
                break;

            case IN_FLIGHT:
                m_State = State.AROUND_CARRIER;
                m_Ship.getProtectArray().addLast(this);
                break;

            case ATTACK:
                if(m_EnemyPlane.m_State == State.EXPLODED || m_EnemyPlane.m_State == State.DEAD) {
                    m_State = State.MOVING_TO_CARRIER;
                }
                moveToPoint(m_EnemyPlane.getCenterX(), m_EnemyPlane.getCenterY());
                if (Math.atan2 (m_EnemyPlane.getCenterY() - m_CenterY, m_EnemyPlane.getCenterX() - m_CenterX) * 180 / Math.PI + 90 < m_Angle + 1
                    && Math.atan2 (m_EnemyPlane.getCenterY() - m_CenterY, m_EnemyPlane.getCenterX() - m_CenterX) * 180 / Math.PI + 90 > m_Angle - 1
                    && !m_Aggressive && checkInRange(m_EnemyPlane.getCenterX(), m_EnemyPlane.getCenterY(), m_CenterX, m_CenterY, 300))
                shoot();
                break;
        }
    }


    @Override
    public void shoot() {
        if(timer == 0) {
            bullets.add (new Bullet (
                    transformCordX (getAngle(), m_Cannon1X, m_Cannon1Y),
                    transformCordY (getAngle(), m_Cannon1X, m_Cannon1Y),
                    getAngle(), m_Group, m_Nation));
        }
        if(timer == 20) {
            bullets.add (new Bullet (
                    transformCordX (getAngle(), m_Cannon2X, m_Cannon2Y),
                    transformCordY (getAngle(), m_Cannon2X, m_Cannon2Y),
                    getAngle(), m_Group, m_Nation));
        }
        timer++;
        if(timer == 40) timer = 0;
    }

    protected void bulletUpdate() {
        if(!bullets.isEmpty()){
            for (Bullet bullet: bullets) {
                bullet.update();
            }

            if(bullets.size() > 1000) {
                    // створення тимчасового ArrayList
                    ArrayList<Bullet> array = new ArrayList<>();
                    for (Bullet bullet: bullets) {
                        if(bullet.isInFlight()) {
                            array.add(bullet);
                        }
                    }
                bullets = array; // наш основний ArrayList тепер посилаеться на тимчасовий ArrayList
            }
        }
    }

    private void checkEnemy() {
        double minRange = ATTACK_RANGE;
        for (Jet plane: Main.pearlHarbour.getFleet().getPlanes(m_Nation)) {
            if ( plane.getState() != State.DEAD && plane.getState() != State.EXPLODED && plane.getState() != State.ON_GROUND &&
                    plane.getState() != State.ON_PARADE &&
                    checkInRange (m_CenterX, m_CenterY, plane.getCenterX(), plane.getCenterY(), minRange)) {
                minRange = countRange(m_CenterX, m_CenterY, plane.getCenterX(), plane.getCenterY());
                m_EnemyPlane = plane;
                m_State = State.ATTACK;
            }
        }
    }

    private double transformCordX (double rotation, double x, double y) {
        rotation *= Math.PI / 180;
        x = x * Math.cos(rotation) - y * Math.sin(rotation);
        x += getCenterX();
        return x;
    }

    private double transformCordY (double rotation, double x, double y) {
        rotation *= Math.PI / 180;
        y = x * Math.sin(rotation) + y * Math.cos(rotation);
        y += getCenterY();
        return y;
    }



    // реалізація глибинного клонування
    //  realization of Cloneable interface
    @Override
    public US_Fighter clone() throws CloneNotSupportedException {
        US_Fighter cloned = (US_Fighter) super.clone();

                    ArrayList<Bullet> clonedBullets = new ArrayList<>();
            for (Bullet bullet: bullets) {
                clonedBullets.add(bullet.clone());
            }
            cloned.bullets = clonedBullets;
        return cloned;
    }

}
