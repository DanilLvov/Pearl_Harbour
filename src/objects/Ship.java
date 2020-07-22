package objects;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayDeque;

public class Ship {

    private final float SIZE_X = 523, SIZE_Y = 108;

    private ImageView m_View;
    private double m_CenterX;
    private double m_CenterY;
    private double m_Angle;

    private int valY = -400;
    private double[] landingPointsX = new double[] { 160, 190, 230, 284, 298, 204, 100, 0};
    private double[] landingPointsY = new double[] { 550, 540, 490, 330, 144, 20, -10, -10};
    private double[] carrierPlacesX = new double[] { -450, -400, -350, -300, -250, -200, -150, -100};
    private double[] carrierPlacesY = new double[] { -10, -10, -10, -10, -10, -10, -10, -10};
    private double[] carrierProtectX = new double[] { 0 - (SIZE_X / 2.0), 282 - (SIZE_X / 2.0), 400 - (SIZE_X / 2.0), 282 - (SIZE_X / 2.0), 0 - (SIZE_X / 2.0), -282 - (SIZE_X / 2.0), -400 - (SIZE_X / 2.0), -282 - (SIZE_X / 2.0)};
    private double[] carrierProtectY = new double[] { -400 - valY, -282 - valY,  - valY, 282 - valY, 400 - valY, 282 - valY, - valY, -282 - valY};

    private ArrayDeque<Jet> planesOnShip;
    private ArrayDeque<Jet> protectionPlanes;

    public Ship (String nation, double positionX, double positionY, double rotation, Pane group, String name) {

        planesOnShip = new ArrayDeque<>();
        protectionPlanes = new ArrayDeque<>();

        if (nation.equals("US")) {
            m_View = new ImageView(TextureManager.textures.get("US_ship.png"));
        }
        if (nation.equals("Japan")) {
            m_View = new ImageView(TextureManager.textures.get("Japan_ship.png"));
        }

        m_View.setX(positionX);
        m_View.setY(positionY);
        m_View.setRotate(rotation);
        m_View.setPreserveRatio(true);
        m_View.setFitWidth(SIZE_X);

        m_CenterX = positionX + (SIZE_X / 2.0);
        m_CenterY = positionY + (SIZE_Y / 2.0);
        m_Angle = rotation;

        rotation *= Math.PI / 180;

        // створення імені літака, що буде виводитись на єкран
        Text nameLine = new Text(name);
        nameLine.setFont(Font.font("Verdana", 12));
        nameLine.setFill(Color.BLACK);
        nameLine.setX(m_CenterX - 40);
        nameLine.setY(m_CenterY - 80);

        // створення лінії здоров'я, що буде виводитись на єкран
        Line m_healthBar = new Line(m_CenterX - 45, m_CenterY - 70, m_CenterX + 45, m_CenterY - 70);

        transformCords(rotation, landingPointsX, landingPointsY);
        transformCords(rotation, carrierPlacesX, carrierPlacesY);
        transformCords(rotation, carrierProtectX, carrierProtectY);

        group.getChildren().addAll(m_View, nameLine, m_healthBar);
    }

    public double getCenterX () { return m_CenterX; }
    public double getCenterY () { return m_CenterY; }
    public double[] getLandingPointsX ()  { return landingPointsX; }
    public double[] getLandingPointsY ()  { return landingPointsY; }
    public double[] getCarrierPlacesX ()  { return carrierPlacesX; }
    public double[] getCarrierPlacesY ()  { return carrierPlacesY; }
    public double[] getCarrierProtectX () { return carrierProtectX; }
    public double[] getCarrierProtectY () { return carrierProtectY; }
    public double getAngle () { return m_Angle; }
    public ArrayDeque<Jet> getProtectArray () { return protectionPlanes; }
    public ArrayDeque<Jet> getPlanesOnCarrier () { return planesOnShip; }

    public void addPlane ( Jet jet ) {
        planesOnShip.addLast(jet);
        jet.setPosOnCarrier(planesOnShip.size());
    }

    public void removePlane () {
        planesOnShip.removeFirst();
        for (Jet plane: planesOnShip) {
            plane.setPosOnCarrier (plane.getPosOnCarrier() - 1);
        }
    }

    private void transformCords (double rotation, double[] arrayX, double[] arrayY) {
        for (int i = 0; i < landingPointsX.length; i++) {
            double x = -1 * arrayX[i] - (SIZE_X / 2.0);
            double y = arrayY[i];
            arrayX[i] = x * Math.cos(rotation) - y * Math.sin(rotation);
            arrayY[i] = x * Math.sin(rotation) + y * Math.cos(rotation);
            arrayX[i] += m_CenterX;
            arrayY[i] += m_CenterY;
        }
    }
}
