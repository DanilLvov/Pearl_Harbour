package objects;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.MiniMap;


public class Harbour {

    private Fleet fleet; // Об'єкт, що містить усі інші об'єкти, та керує їми

    public Pane objectsGroup;
    public Pane backGround;
    private ImageView m_View;
    private Rectangle rectFrame;
    private int score_US = 0;
    private int score_Japan = 0;
    protected Text text_US;
    protected Text text_Japan;

    public Harbour() {
        // створення макрооб'екту та додавання його у групу
        objectsGroup = new Pane();
        backGround = new Pane();

        text_Japan = new Text (String.valueOf (score_Japan));
        text_Japan.setFont(Font.font("Verdana", 50));
        text_Japan.setFill(Color.RED);
        text_Japan.setX(10);
        text_Japan.setY(50);

        text_US = new Text (String.valueOf (score_US));
        text_US.setFont(Font.font("Verdana", 50));
        text_US.setFill(Color.GRAY);
        text_US.setX(1366 - 40);
        text_US.setY(50);

        rectFrame = new Rectangle(1366, 768);
        rectFrame.setStrokeWidth(15);
        rectFrame.setFill(Color.TRANSPARENT);
        rectFrame.setStroke(Color.YELLOW);
        rectFrame.setX(0);
        rectFrame.setY(0);

        m_View = new ImageView(TextureManager
                .textures
                .get("PearlHarbour.png"));

        m_View.setPreserveRatio(true);
        m_View.setFitWidth(3000);
        backGround.getChildren().add(m_View);

        // створення масиву мікрооб'єктів та додавання його до групи
        fleet = new Fleet();
        fleet.addToGroup(objectsGroup);

        objectsGroup.getChildren().addAll(rectFrame, text_Japan, text_US);
    }

    private int mainAnimTimer = 9;
    public void mainAnimLaunch() {
        // Основна анімація, у якій оновлюються усі мікрооб'єкти
        AnimationTimer mainTimer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                mainAnimTimer++;
                fleet.update();
                if(mainAnimTimer == 10) {
                    mainAnimTimer = 0;
                    MiniMap.actualize(); // Оновлення усіх мікрооб'єктів
                }
            }
        };

        mainTimer.start(); // запуск анімації
    }

    public Fleet getFleet() { return fleet; }

    public void addHarbourGroup(Pane group) {
        group.getChildren().add(backGround);
        group.getChildren().add(objectsGroup);
    }

    public void changeJapanScore() {
        score_Japan++;
        text_Japan.setText (String.valueOf (score_Japan));
    }

    public void changeUS_Score() {
        score_US++;
        text_US.setText (String.valueOf (score_US));
    }

    public void moveFrame(double x, double y) {
        rectFrame.setX(-x);
        rectFrame.setY(-y);

        text_Japan.setX(-x + 10);
        text_Japan.setY(-y + 50);

        text_US.setX(-x + 1366 - 40);
        text_US.setY(-y + 50);
    }
}

