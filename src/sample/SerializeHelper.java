package sample;

import objects.State;

import java.io.Serializable;

public class SerializeHelper implements Serializable {
    public double positionX;
    public double positionY;
    public double rotation;
    public int posOnCar;
    public State state;
    public String nation;
    public String name;
    public String type;
    public double ratio;
    public boolean aggressive;

    public SerializeHelper(String name, double x, double y, State state, double rotation, String nation, String type, int posOnCar, double ratio, boolean aggressive){
        this.positionX = x;
        this.positionY = y;
        this.rotation = rotation;
        this.state = state;
        this.nation = nation;
        this.name = name;
        this.type = type;
        this.posOnCar = posOnCar;
        this.ratio = ratio;
        this.aggressive = aggressive;
    }
}
