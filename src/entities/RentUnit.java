package entities;

import java.util.ArrayList;

public class RentUnit {

   private ArrayList<SportEquipment> units;

   public RentUnit(){
        this.units = new ArrayList<>();
    }

    public void setUnits(SportEquipment unit){
        units.add(unit);
    }

    public ArrayList<SportEquipment> getUnits(){
        return units;
    }

    public void removeUnits(SportEquipment unit){
        units.remove(unit);
    }

}
