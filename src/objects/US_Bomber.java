package objects;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;


public class US_Bomber extends US_Fighter {
    private ImageView m_Bomb;
    private boolean bomb = false;
    private double bombRatio = 1;
    private double bombPosX;
    private double bombPosY;
    private final double BOMB_X = 51 * 2;
    private final double BOMB_Y = 51 * 2;
    private double m_BombRotate;
    private int timer = 0;

    public US_Bomber(String name, double positionX, double positionY, Pane group, Ship ship, Ship enemyShip) {
        this(name, positionX, positionY, ship, enemyShip);
        viewName = "US_bomber.png";
        viewNameActive = "US_bomber_active.png";

        m_Nation = "US";

        // створення зображення літаку
        m_View = new ImageView(TextureManager
                .textures
                .get(viewName));

        m_View.setFitWidth(SIZE_X);
        m_View.setFitHeight(SIZE_Y);

        //додавання усіх 3-х графічних примітивів до групи
        m_Group.getChildren().addAll(m_View, nameLine, m_healthBar);
        group.getChildren().add(m_Group);
    }

    protected US_Bomber(String name, double positionX, double positionY, Ship ship, Ship enemyShip) {
        super(name, positionX, positionY, ship, enemyShip);
        m_Bomb = new ImageView();

        m_Type = "Bomber";
    }


    @Override
    public void update() {
        if(m_State != State.DEAD && m_State != State.ON_PARADE) {
            updateBomber();
            if(bomb) bombUpdate();
            bulletUpdate();
            defaultUpdate();
        }
    }

    protected void updateBomber() {
        switch (m_State) {
            case MOVING_TO_ENEMY:
                moveToPoint(m_EnemyShip.getCenterX(), m_EnemyShip.getCenterY());
                if (checkInRange (m_EnemyShip.getCenterX(), m_EnemyShip.getCenterY(), m_CenterX, m_CenterY, 140)) {
                    bomb();
                    m_State = State.MOVING_TO_CARRIER;
                }
                break;
            case IN_FLIGHT:
                m_State = State.MOVING_TO_ENEMY;
                if(!m_Ship.getProtectArray().isEmpty()) {
                    m_Ship.getProtectArray().getFirst().setState(State.MOVING_TO_ENEMY);
                    m_Ship.getProtectArray().removeFirst();
                }
                if(!m_Ship.getProtectArray().isEmpty()) {
                    m_Ship.getProtectArray().getFirst().setState(State.MOVING_TO_ENEMY);
                    m_Ship.getProtectArray().removeFirst();
                }

                break;
        }
    }

    //public void shoot(int a) {}

    public void bomb () {
        m_Bomb.setImage(TextureManager.textures.get(EXP_VIEW_NAMES[0]));
        bombPosX = m_CenterX - 27 * 2;
        bombPosY = m_CenterY - 26 * 2;
        m_Bomb.setX (bombPosX);
        m_Bomb.setY (bombPosY);
        m_Group.getChildren().add(m_Bomb);
        m_Group.getChildren().remove(m_View);
        m_Group.getChildren().add(m_View);
        m_BombRotate = m_Angle - 90;
        m_Bomb.setRotate(m_BombRotate);
        bomb = true;
    }

    public void bombUpdate() {
        if(bombRatio > 0.4) {
            bombRatio -= 0.002;
            bombPosX += SIZE_X * 0.001;
            bombPosY += SIZE_Y * 0.001;
            bombPosX += Math.cos(m_BombRotate * (Math.PI / 180)) * 5 * 0.1;
            bombPosY += Math.sin(m_BombRotate * (Math.PI / 180)) * 5 * 0.1;
            m_Bomb.setX(bombPosX);
            m_Bomb.setY(bombPosY);
            m_Bomb.setFitWidth(BOMB_X * bombRatio);
            m_Bomb.setFitHeight(BOMB_Y * bombRatio);

        }
        else {
            timer ++;
            if(timer % 10 == 0) {
                m_Bomb.setImage(TextureManager
                        .textures
                        .get(EXP_VIEW_NAMES[timer / 10]));
            }
            if(timer == 69) {
                m_Group.getChildren().remove(m_Bomb);
                timer = 0;
                bombRatio = 1;
                bomb = false;
            }
        }
    }
}
