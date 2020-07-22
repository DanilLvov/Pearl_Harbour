package objects;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.Main;

import java.util.ArrayList;

import static java.lang.Math.cos;

public class Bullet implements Cloneable{

    private double m_BulletSpeed = 500;
    private float elapsedTime = 0.01f;
    private ImageView m_Bullet;
    private Pane m_Group;
    private String[] viewName = new String[] {"explosion0.png", "explosion1.png", "explosion2.png",
                                              "explosion3.png", "explosion4.png", "explosion5.png",
                                              "explosion6.png", "explosion7.png"};

    // Keep track of the bullet
    private boolean m_InFlight = true;
    private double m_PositionX;
    private double m_PositionY;
    private double m_Rotation;
    private String bulletNation;
    private double m_Way = 0;
    private int  counter = 0;
    private int animationtimer = 0;

    public Bullet(double startX, double startY, double rotation, Pane group, String nation)
    {
        m_Group = new Pane();
        m_Bullet = new ImageView(TextureManager
                                    .textures
                                    .get(viewName[0]));
        m_PositionX = startX - 20;
        m_PositionY = startY - 20;
        m_Rotation = rotation;

        m_Bullet.setX(m_PositionX);
        m_Bullet.setY(m_PositionY);

        m_InFlight = true;
        m_Group.getChildren().add(m_Bullet);
        group.getChildren().add(m_Group);
        bulletNation = nation;
    }

    public void stop()
    {
        m_InFlight = false;
    }

    public boolean isInFlight()
    {
        return m_InFlight;
    }

    public void update()
    {
        if (m_Way <= 300 && m_PositionX >= 5 && m_PositionY >= 5 && m_PositionX <= 3000 - 5 && m_PositionY <= 1800)
        {
            // Update the bullet position variables
            m_PositionX += m_BulletSpeed = 1000 * elapsedTime * Math.sin(m_Rotation * Math.PI / 180);
            m_PositionY-= m_BulletSpeed = 1000 * elapsedTime * Math.cos(m_Rotation * Math.PI / 180);
            m_Way += m_BulletSpeed = 1000 * elapsedTime;

            // Move the bullet
            m_Bullet.setX(m_PositionX);
            m_Bullet.setY(m_PositionY);

            // Check collision
            for (Jet plane: Main.pearlHarbour.getFleet().getPlanes(bulletNation)) {
                if(Math.sqrt (Math.pow ( (plane.getCenterX() - m_PositionX), 2) + Math.pow ( (plane.getCenterY() - m_PositionY), 2)) < 42)
                    if(plane.m_State != State.DEAD && plane.m_State != State.EXPLODED && plane.m_State != State.ON_GROUND
                            && plane.getState() != State.ON_PARADE) {
                        plane.explode();
                        m_InFlight = false;
                        m_Group.getChildren().remove(m_Bullet);
                    }
            }
        }

        // Has the bullet gone out of range?
        if (m_Way > 300 || m_PositionX < 5 || m_PositionY < 5 || m_PositionX > 3000 - 5 || m_PositionY > 1800)
        {
           animationtimer++;
           if(counter == 8)
           {
                m_InFlight = false;
                m_Group.getChildren().remove(m_Bullet);
           }
           if(animationtimer > 8 * counter && m_InFlight) {
               m_Bullet.setImage(TextureManager
                                   .textures
                                   .get(viewName[counter]));
               counter++;
           }
        }


    }

    public ImageView getView()
    {
        return m_Bullet;
    }

    // реалізація глибинного клонування
    //  realization of Cloneable interface
    @Override
    public Bullet clone() throws CloneNotSupportedException {
        Bullet cloned = (Bullet) super.clone();

        return cloned;
    }
}
