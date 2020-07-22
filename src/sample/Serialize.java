package sample;

import objects.Jet;
import objects.State;

import java.io.*;
import java.util.ArrayList;

public class Serialize {

    static ArrayList<SerializeHelper> helpArray = new ArrayList<>();

    public static void serialization(String fileName) throws IOException, ClassNotFoundException {

        for (Jet plane: Main.pearlHarbour.getFleet().getPlanes("US")){
            if (plane.getState() != State.DEAD)
            helpArray.add(new SerializeHelper(
                    plane.getName(), plane.getPosX(), plane.getPosY(), plane.getState(), plane.getAngle(),
                    plane.getNation(), plane.getType(), plane.getPosOnCarrier(), plane.getRatio(), plane.getAgressive()
            ));
        }

        for (Jet plane: Main.pearlHarbour.getFleet().getPlanes("Japan")){
            if (plane.getState() != State.DEAD)
            helpArray.add(new SerializeHelper(
                    plane.getName(), plane.getPosX(), plane.getPosY(), plane.getState(), plane.getAngle(),
                    plane.getNation(), plane.getType(), plane.getPosOnCarrier(), plane.getRatio(), plane.getAgressive()
            ));
        }

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(helpArray);
        }
    }

    public static void deserialization(String fileName) throws FileNotFoundException {

        Main.pearlHarbour.getFleet().getPlanesGroup().getChildren().clear();


        ArrayList<SerializeHelper> newArray = new ArrayList<>();

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));)
        {
            newArray = (ArrayList<SerializeHelper>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Main.pearlHarbour.getFleet().getPlanes("US").clear();
        Main.pearlHarbour.getFleet().getPlanes("Japan").clear();

        for(SerializeHelper plane: newArray) {

            Main.pearlHarbour.getFleet().addJet(plane.nation, plane.name, plane.positionX, plane.positionY, plane.type);
            Main.pearlHarbour.getFleet().getSerializeJet().setState(plane.state);
            Main.pearlHarbour.getFleet().getSerializeJet().setAngle(plane.rotation);
            Main.pearlHarbour.getFleet().getSerializeJet().setPosOnCarrier(plane.posOnCar);
            Main.pearlHarbour.getFleet().getSerializeJet().setRatio(plane.ratio);
            if(plane.aggressive) {
                Main.pearlHarbour.getFleet().getSerializeJet().changeAggressive();
            }
        }
    }
}
