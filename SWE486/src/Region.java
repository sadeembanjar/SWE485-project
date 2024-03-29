import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Region {
String code ; 
String [] Pcolors;
Region [] Adjacents;
String MyColor;
boolean isColorSet = false ; 
boolean hasAdj = false ; 


public Region (String [] c , String code){
this.code = code ;
Pcolors= c;
}


public void setAdjacents(Region [] ad){

this.Adjacents =ad;
 this.hasAdj = true ; 

}

public void setMyColor(){
for (int i=0 ; i<Pcolors.length;i++){
// check if removing the color from adjacents makes thier color list empty dodo 
// then remove the color from adjacents ++ and assign it to mycolor
if(hasAdj){
 if (checkAdjacent(Pcolors[i])) {
                MyColor = Pcolors[i];
                isColorSet = true ; 
                break;
            }
           
}else{
    MyColor = Pcolors[i];
    isColorSet = true ; 
    break;
}


}

}


@Override
public String toString() {
    return "Region "+ code + " has the Color=" + MyColor ;
}


    private boolean checkAdjacent(String c) {

    for (int i = 0; i < Adjacents.length; i++) {
        // Skip if color is already set for the adjacent region
        if (Adjacents[i].isColorSet) {
            continue;
        }

        // Assume color c is not in the adjacent's possible colors
        boolean containsC = false;
        for (String color : Adjacents[i].Pcolors) {
            if (color.equals(c)) {
                containsC = true;
                break;
            }
        }

        // If c is a possible color, prepare to remove it
        if (containsC) {
            List<String> updatedColorsList = new ArrayList<>(Arrays.asList(Adjacents[i].Pcolors));
            updatedColorsList.remove(c); // Remove color c
            Adjacents[i].Pcolors = updatedColorsList.toArray(new String[0]);
            if (updatedColorsList.isEmpty()) {
                // If removing c leaves no possible colors, return false
              
                return false;
            }
        }
    }
    return true;
}

}


