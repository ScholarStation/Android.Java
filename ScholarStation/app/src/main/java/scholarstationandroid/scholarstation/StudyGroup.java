package scholarstationandroid.scholarstation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonat on 3/30/2016.
 */
public class StudyGroup {
    String name;
    String age;
    int photoId;

    StudyGroup(String name, String age, int photoId) {
        this.name = name;
        this.age = age;
        this.photoId = photoId;
    }

private List<StudyGroup> groupList;

    // This method creates an ArrayList that has three Person objects
// Checkout the project associated with this tutorial on Github if
// you want to use the same images.
    private void initializeData(){
        groupList = new ArrayList<>();
        groupList.add(new StudyGroup("Emma Wilson", "23 years old", R.drawable.ic_menu_send));
        groupList.add(new StudyGroup("Lavery Maiss", "25 years old", R.drawable.ic_menu_camera));
        groupList.add(new StudyGroup("Lillie Watts", "35 years old", R.drawable.fab_button));
    }
}