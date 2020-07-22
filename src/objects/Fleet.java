package objects;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;

public class Fleet {
    // ArrayList мікрооб'єктів
    private ArrayList<Jet> JapanPlanes;
    private ArrayList<Jet> US_Planes;
    private Jet serializeJet;

    private Ship JapanCarrier;
    private Ship US_carrier;
    private Pane m_Group;
    private Pane m_ShipGroup;

    // змінні для відслідковування, куди рухаються активовані мікрооб'єкти
    private boolean m_Right = false;
    private boolean m_Left  = false;
    private boolean m_Shoot = false;

    // функція для додавання нового мікрооб'єкта
    public void addJet(String nation, String name, double positionX, double positionY, String type) {
        // в залежності від типу літаку, ми викликаєио різні конструктори

        if (nation.equals("Japan")) {
            if(type.equals("Jet")){
                serializeJet = new Jet(name, positionX, positionY, m_Group, JapanCarrier);
            }
            if(type.equals("Fighter")){
                serializeJet = new JapanFighter(name, positionX, positionY, m_Group, JapanCarrier, US_carrier);
            }
            if(type.equals("Bomber")){
                serializeJet = new JapanBomber(name, positionX, positionY, m_Group, JapanCarrier, US_carrier);
            }
            JapanPlanes.add (serializeJet);
        }

        if (nation.equals("US")) {
            if(type.equals("Fighter")){
               serializeJet = new US_Fighter(name, positionX, positionY, m_Group, US_carrier, JapanCarrier);
            }
            if(type.equals("Bomber")){
               serializeJet = new US_Bomber(name, positionX, positionY, m_Group, US_carrier, JapanCarrier);
            }
            US_Planes.add (serializeJet);
        }
    }

    // конструктор, який створює ArrayList, завантажує зображення літаків у программу та створює чотири літаки
    public Fleet(){
        m_Group = new Pane();
        m_ShipGroup = new Pane();
        JapanPlanes = new ArrayList<>();
        US_Planes = new ArrayList<>();
        //positionsArray = new int[38][23];
        //Arrays.fill(positionsArray, 0);

        // створення кораблів та додавання їх до групи
        JapanCarrier = new Ship( "Japan",300, 140, -10, m_ShipGroup, "Japan_Ship");
        US_carrier   = new Ship ( "US",1400, 1640, 195, m_ShipGroup, "US_Ship");

        // 3 стартові літаки, які також додаються ф-єю addJet
        addJet("Japan","Konugaba",0,0,"Jet");
        addJet("Japan","Kioto",2600,350,"Bomber");
        addJet("Japan", "Naginata", 2400, 360, "Fighter");
        addJet("Japan","Kara",20,50,"Bomber");
        addJet("Japan", "Zero", 100, 150, "Fighter");
        addJet("US","Tiger",1000,1600,"Fighter");
        addJet("US","Paramon",1700,1500,"Bomber");
        addJet("US", "Wolf", 1400, 1290, "Fighter");
        addJet("US","Paramon",1700,1500,"Bomber");
        addJet("Japan","Konugaba",50,50,"Jet");
        addJet("Japan","Kioto",1500,450,"Bomber");
        addJet("Japan", "Naginata", 40, 360, "Fighter");
        addJet("Japan","Kara",200,50,"Bomber");
        addJet("Japan", "Zero", 160, 250, "Fighter");
        addJet("US","Tiger",1070,1000,"Fighter");
        addJet("US","Paramon",2700,1500,"Bomber");
        addJet("US", "Wolf", 1300, 1090, "Fighter");
        addJet("US","Paramon",2700,1400,"Bomber");
    }

    // набір мутаторів для керування активними літаками
    public void startLeft  () { m_Left = true; }
    public void startRight () { m_Right = true; }
    public void startShoot () { m_Shoot = true; }

    public void stopLeft   () { m_Left = false; }
    public void stopRight  () { m_Right = false; }
    public void stopShoot  () { m_Shoot = false; }

    public Pane getPlanesGroup () { return m_Group; }
    public Jet getSerializeJet () { return serializeJet; }

    public ArrayList<Jet> getPlanes(String nation) {
        if(nation.equals("Japan")) return US_Planes;
        else return JapanPlanes;
    }

    // функція, що перевіряеє, чи потрапила миша у літак, якщо так, то змінює його стан
    public void mouseClick(double x, double y) {
        for (Jet jet: JapanPlanes) {
            // якщо об'єкт пересікаеється з мишою, ми змінюємо його стан
            jet.mouseCheck(x, y);
        }
    }

    // ф-ія оновлення усіх літаків
    public void update(){
        for (Jet plane: JapanPlanes) {
            // активні літаки можуть повертати та рухатися з додатнім прискоренням
            if (plane.getState() == State.ACTIVE) {
                if (m_Left)  { plane.rotateLeft(); }
                if (m_Right) { plane.rotateRight(); }
                if (m_Shoot) { plane.shoot(); }
            }

            // функція класу Jet, що змінює координати літака
            plane.update();
        }

        for (Jet plane: US_Planes) {
            plane.update();
        }
    }

    // ф-ія видалення усіх активних літаків
    public void deleteActive() {
        // створення тимчасового ArrayList
        ArrayList<Jet> array = new ArrayList<>();
        for (Jet plane: JapanPlanes) {

            if(plane.getState() == State.ACTIVE) {
                plane.setState(State.DEAD);
                m_Group.getChildren().remove(plane.getGroup()); // активні літаки видаляються з групи
            }
            else {
                array.add(plane); // неактивні літаки додаються до тимчасового ArrayList
            }
        }
        JapanPlanes = array; // наш основний ArrayList тепер посилаеться на тимчасовий ArrayList
    }

    // ф-ія деактивації усіх активних літаків
    public void deactivate() {
        for (Jet plane: JapanPlanes) {
            if(plane.getState() == State.ACTIVE) {
                // якщо мікрооб'єкт активний, змінюємо його стан
                plane.changeActivity();
            }
        }
    }

    public void addToGroup(Pane group) {
        group.getChildren().addAll(m_ShipGroup, m_Group);
    }

    public void startLanding() {
        for (Jet plane: JapanPlanes) {
            if (plane.getState() == State.ACTIVE) {
                plane.setState(State.MOVING_TO_CARRIER);
            }
        }
    }

    public void changeInteraction() {
        for (Jet plane: JapanPlanes) {
            plane.changeAggressive();
        }

        for (Jet plane: US_Planes) {
            plane.changeAggressive();
        }
    }

    public void stopLanding() {
        for (Jet plane: JapanPlanes) {
            if (plane.getState() == State.MOVING_TO_CARRIER) {
                plane.setState(State.ACTIVE);
            }
        }
    }

    public void takeOff() {
         JapanCarrier.getPlanesOnCarrier().getFirst().setState (State.TAKING_OFF);
    }

    public void sort(ArrayList<Jet> planes) {

            Jet[] clonedArray= new Jet[planes.size()];
            Jet[] copiedArray;
            for (int i = 0; i < planes.size(); i++) {
                try {
                    clonedArray[i] = planes.get(i).clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
            copiedArray = Arrays.copyOf(clonedArray, clonedArray.length);
            Arrays.sort(copiedArray);
            System.out.println("Sort by positions from left to right");
            for (int i = 0; i < copiedArray.length; i++) {
                System.out.println(copiedArray[i].getName() + ". On position: " + copiedArray[i].getPosX());
            }

    }
}