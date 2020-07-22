package objects;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.Main;

import static java.lang.Math.*;

public class Jet implements Cloneable, Comparable<Jet> {

    protected ImageView m_BigExplosion;
    protected final String[] EXP_VIEW_NAMES = new String[] {"bomb0.png", "bomb1.png", "bomb2.png",
            "bomb3.png", "bomb4.png", "bomb5.png", "bomb6.png"};


    protected final int SIZE_X = 118;
    protected final int SIZE_Y = 94;

    protected String m_Nation; // країна
    protected String m_Type;

    // координати X і Y літака
    protected double m_CenterX;
    protected double m_CenterY;

    // кут повороту літака
    protected double m_Angle = 90;

    // стан об'єкту (активний, неактивний)
    protected State m_State = State.MOVING_TO_ENEMY;
    protected boolean m_ActivePossible = true;
    protected boolean m_Aggressive = false;

    protected Ship m_Ship;

    protected String viewName;
    protected String viewNameActive;
    protected Text nameLine;
    protected Line m_healthBar;
    protected Pane m_Group;

    protected ImageView m_View;

    private final int SPEED = 10;
    private final float TIMER = 0.01f;

    // змінні об'екта классу
    private int m_Speed = SPEED; // швидкість
    private String m_Name; // ім'я

    private double m_PositionX;
    private double m_PositionY;

    private int m_PosOnCarrier = 0;

    private int timer = 0;
    private int counter = 0;
    private int size = 100;

    // графічні примітиви: зображення літака,
    // його ім'я та лінія здоров'я
    private double m_ViewRatio = 1;
    private int paradeNumber;



    // основний конструктор, отримує ім'я та координати літака в якості параметру,
    // а також країну, що визначає деякі особливі значення
    public Jet(String name, double positionX, double positionY, Pane group, Ship ship) {
        this(name, positionX, positionY, ship);

        viewName = "Japan_plane.png";
        viewNameActive = "Japan_plane_active.png";

        m_Nation = "Japan";

        // створення зображення літаку
        m_View = new ImageView(TextureManager
                                .textures
                                .get(viewName));

        //додавання усіх 3-х графічних примітивів до групи
        m_Group.getChildren().addAll(m_View, nameLine, m_healthBar);
        group.getChildren().add(m_Group);

        m_Type = "Jet";
    }

    protected Jet(String name, double positionX, double positionY, Ship ship) {
        this();
        this.m_Name = name;
        this.m_PositionX = positionX;
        this.m_PositionY = positionY;
        // створення імені літака, що буде виводитись на єкран
        nameLine = new Text(m_Name);
        nameLine.setFont(Font.font("Verdana", 12));
        nameLine.setFill(Color.BLACK);

        // створення лінії здоров'я, що буде виводитись на єкран
        m_healthBar = new Line(positionX, positionY, positionX + 100, positionY);

        m_CenterX = m_PositionX + SIZE_X / 2.0;
        m_CenterY = m_PositionY + SIZE_Y / 2.0;

        m_Ship = ship;
        m_BigExplosion = new ImageView(TextureManager
                .textures
                .get(EXP_VIEW_NAMES[1]));
    }

    private Jet() {
        m_Group = new Pane();
    }

    // набір аксесорів
    public double getPosX()      { return m_PositionX; }
    public double getPosY()      { return m_PositionY; }
    public double getCenterX()   { return m_CenterX; }
    public double getCenterY()   { return m_CenterY; }
    public double getAngle()     { return m_Angle; }
    public String getName()      { return m_Name; }
    public String getNation()    { return m_Nation; }
    public boolean getAgressive(){ return m_Aggressive; }
    public ImageView getView()   { return m_View; }
    public Pane getGroup()       { return m_Group; }
    public State getState()      { return m_State; }
    public int getPosOnCarrier() { return m_PosOnCarrier; }
    public String getType()      { return m_Type; }
    public double getRatio()     { return m_ViewRatio; }

    // мутатори
    // public void setActive (boolean value) { m_IsActive = value; }
    // public void setShip (Ship ship) { m_Ship = ship; }
    public void setParadeNumber (int num) { paradeNumber = num; };
    public void setAngle (double angle) { m_Angle = angle; }
    public void setPosOnCarrier (int position) { m_PosOnCarrier = position; }
    public void changeAggressive () {m_Aggressive = !m_Aggressive;}
    public void setRatio (double ratio) { m_ViewRatio = ratio; }
    public void setState (State state) {
        if(state == State.ACTIVE || state == State.IN_FLIGHT || state == State.MOVING_TO_CARRIER ||
                state == State.MOVING_TO_ENEMY || state == State.AROUND_CARRIER || state == State.ATTACK) {
            m_ActivePossible = true;
        }
        else m_ActivePossible = false;

        if (state == State.ACTIVE) {
            m_View.setImage(TextureManager
                    .textures
                    .get(viewNameActive));
        }

        if ((m_State == State.ACTIVE && state != State.ON_PARADE) || m_State == State.ON_PARADE) {
            m_View.setImage(TextureManager
                    .textures
                    .get(viewName));
        }
        m_State = state;
    }

    // зміна стану літака
    // якщо літак активний, він стає неактивним і навпаки
    // також змінюється зображення цього літака
    protected void changeActivity() {
        if(m_ActivePossible) {
            if(m_State != State.ACTIVE) {
                m_View.setImage(TextureManager
                        .textures
                        .get(viewNameActive));
               setState (State.ACTIVE);
            }
            else {
                m_View.setImage(TextureManager
                        .textures
                        .get(viewName));
                setState (State.MOVING_TO_CARRIER);
            }
        }
    }

    // перевірка знаходження миші в літаку
    public void mouseCheck(double x, double y) {
        if (Math.sqrt(Math.pow((m_CenterX - x), 2) + Math.pow((m_CenterY - y), 2)) < 42) {
            changeActivity();
        }
    }
    // поворот вліво
    public void rotateLeft() {
        m_Angle -= (90 * TIMER);
        if (m_Angle < -90) {
            m_Angle += 360;
        }
    }
    // поворот вправо
    public void rotateRight() {
        m_Angle += (90 * TIMER); // m_Acceleration впливає на швидкість повороту
        if (m_Angle > 270) {
            m_Angle -= 360;
        }
    }

    public void shoot() { }

    // оновлення графічних примітивів (зміна їх позицій)

    public void update() {
        if(m_State != State.DEAD && m_State != State.ON_PARADE)
        defaultUpdate();
    }

    public void toParade() {
        if(m_Nation == "Japan") {
            m_PositionX = 0;
            m_PositionY = 140 * paradeNumber;
            m_Angle = 90;
        }
        else {
            m_PositionX = 3000 - 130;
            m_PositionY = 140 * paradeNumber;
            m_Angle = 90 + 180;
        }
        m_CenterX = m_PositionX + SIZE_X / 2.0;
        m_CenterY = m_PositionY + SIZE_Y / 2.0;
        m_View.setRotate(m_Angle);
        m_View.setX(m_PositionX);
        m_View.setY(m_PositionY);
        nameLine.setX(m_PositionX + 30);
        nameLine.setY(m_PositionY - 10);
        m_healthBar.setStartX(m_PositionX);
        m_healthBar.setEndX(m_PositionX + 114);
        m_healthBar.setStartY(m_PositionY);
        m_healthBar.setEndY(m_PositionY);
    }

    protected void defaultUpdate() {
        switch (m_State) {
            case ON_GROUND:
                double x = m_Ship.getCarrierPlacesX()[m_PosOnCarrier - 1];
                double y = m_Ship.getCarrierPlacesY()[m_PosOnCarrier - 1];

                if(Math.sqrt (Math.pow ( (x - m_CenterX), 2) + Math.pow ( (y - m_CenterY), 2)) < 20) {
                    m_Speed = 0;
                }
                else m_Speed = SPEED;

                if (m_PosOnCarrier == 1 && m_Speed == 0) {
                    timer ++;
                }

                if(timer == 60 * 10) {
                    setState (State.TAKING_OFF);
                    timer = 0;
                }
                break;

            case TAKING_OFF:
                m_Speed = SPEED;
                if (m_ViewRatio < 1) {
                    m_ViewRatio += 0.002;
                    m_PositionX -= SIZE_X * 0.001;
                    m_PositionY -= SIZE_Y * 0.001;
                    m_View.setFitHeight(SIZE_Y * m_ViewRatio);
                    m_View.setFitWidth(SIZE_X * m_ViewRatio);
                }
                else {
                    setState (State.IN_FLIGHT);
                    m_Ship.removePlane();
                    size = 100;
                    counter = 0;
                }
                break;

            case MOVING_TO_CARRIER:
                moveToPoint(m_Ship.getLandingPointsX()[counter], m_Ship.getLandingPointsY()[counter]);

                if(counter == 1) size = 80;

                if (checkInRange (m_Ship.getLandingPointsX()[counter], m_Ship.getLandingPointsY()[counter], m_CenterX, m_CenterY, size)) {
                    counter++;
                    size -= 10;
                }

                if(counter > 4) {
                    if (m_ViewRatio > 0.5) {
                        m_ViewRatio -= 0.002;
                        m_PositionX += SIZE_X * 0.001;
                        m_PositionY += SIZE_Y * 0.001;
                    }
                    m_View.setFitHeight(SIZE_Y * m_ViewRatio);
                    m_View.setFitWidth(SIZE_X * m_ViewRatio);
                }
                if(counter == 8) {
                    setState (State.ON_GROUND);
                    m_Angle = 90 + m_Ship.getAngle();
                    m_Ship.addPlane(this);
                }
                break;
            case EXPLODED:
                timer ++;
                if(timer % 10 == 0) {
                    m_BigExplosion.setImage(TextureManager
                            .textures
                            .get(EXP_VIEW_NAMES[timer / 10]));
                }
                if(timer == 69) {
                    m_Group.getChildren().remove(m_BigExplosion);
                    setState (State.DEAD);
                }
                break;
        }

        if (m_State != State.EXPLODED && m_State != State.DEAD) {
            // розрахунок поточної позиції літака
            m_PositionX += Math.sin(m_Angle * (Math.PI / 180)) * m_Speed * 0.1;
            m_PositionY -= Math.cos(m_Angle * (Math.PI / 180)) * m_Speed * 0.1;
            m_CenterX += Math.sin(m_Angle * (Math.PI / 180)) * m_Speed * 0.1;
            m_CenterY -= Math.cos(m_Angle * (Math.PI / 180)) * m_Speed * 0.1;

            // зміна позицій графічних примітивів
            m_View.setRotate(m_Angle);
            m_View.setX(m_PositionX);
            m_View.setY(m_PositionY);
            nameLine.setX(m_PositionX + 30);
            nameLine.setY(m_PositionY - 10);
            m_healthBar.setStartX(m_PositionX);
            m_healthBar.setEndX(m_PositionX + 114);
            m_healthBar.setStartY(m_PositionY);
            m_healthBar.setEndY(m_PositionY);

            if (m_CenterX > 3000 || m_CenterX < 0 ||
                    m_CenterY > 1833 || m_CenterY < 0) {
                explode();
            }

            if (m_Aggressive) {
                for (Jet plane: Main.pearlHarbour.getFleet().getPlanes(m_Nation)) {
                    if(Math.sqrt (Math.pow ( (plane.getCenterX() - m_CenterX), 2) + Math.pow ( (plane.getCenterY() - m_CenterY), 2)) < 84)
                        if(plane.m_State != State.DEAD && plane.m_State != State.EXPLODED && plane.m_State != State.ON_GROUND) {
                            plane.explode();
                            explode();
                        }
                }
            }
        }
    }

    protected void moveToPoint(double x, double y) {
        double angle =
                Math.atan2(y - m_CenterY, x - m_CenterX) * 180 / Math.PI + 90;
        angle = Math.round(angle);

        // we rotate plane with some speed
        if (m_Angle > 270) {
            m_Angle -= 360;
        }

        else if (m_Angle < -90) {
            m_Angle += 360;
        }

        if(Math.abs (m_Angle - angle) > 0.5) {
            if (angle < m_Angle) {
                if (abs(180 - m_Angle) + abs(-180 - angle) < abs(m_Angle - angle)) {
                    m_Angle += 90 * TIMER;
                }
                else {
                    m_Angle -= 90 * TIMER;
                }
            }
            else {
                if (abs(180 - angle) + abs(-180 - m_Angle) < abs(angle - m_Angle)) {
                    m_Angle -= 90 * TIMER;
                }
                else {
                    m_Angle += 90 * TIMER;
                }
            }
        }
    }

    protected boolean checkInRange (double x1, double y1, double x2, double y2, double r) {
        return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2)) < r;
    }

    protected double countRange (double x1, double y1, double x2, double y2) {
        return Math.sqrt (Math.pow ( (x1 - x2), 2) + Math.pow ( (y1 - y2), 2));
    }

    public void explode() {
        setState (State.EXPLODED);
        m_ActivePossible = false;
        m_BigExplosion.setX(m_CenterX - 27 * 2);
        m_BigExplosion.setY(m_CenterY - 26 * 2);
        m_Group.getChildren().clear();
        m_Group.getChildren().add(m_BigExplosion);
        timer = 10;
    }



    //  realization of Comparable interface to sort by health
    @Override
    public int compareTo(Jet other) {
        return (int)this.getPosX() - (int)other.getPosX();
    }

    @Override
    public Jet clone() throws CloneNotSupportedException {
        return (Jet) super.clone();
    }
}

