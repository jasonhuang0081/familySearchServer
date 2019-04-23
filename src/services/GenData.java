package services;

import com.google.gson.Gson;
import model.Event;
import model.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.Random;

public class GenData {

    private NameList fList;
    private NameList mList;
    private NameList sList;
    private Locations locationList;
    private final String currentUser;

    public GenData(String currentUser) {
        this.currentUser = currentUser;
        try {
            File fFile = new File("source" + File.separator + "json" + File.separator + "fnames.json");
            File mFile = new File("source" + File.separator + "json" + File.separator + "mnames.json");
            File sFile = new File("source" + File.separator + "json" + File.separator + "snames.json");
            File locationFile = new File("source" + File.separator + "json" + File.separator + "locations.json");
            Reader readerF = new FileReader(fFile);
            Reader readerM = new FileReader(mFile);
            Reader readerS = new FileReader(sFile);
            Reader readerL = new FileReader(locationFile);
            Gson gson = new Gson();
            fList = gson.fromJson(readerF, NameList.class);
            mList = gson.fromJson(readerM, NameList.class);
            sList = gson.fromJson(readerS, NameList.class);
            locationList = gson.fromJson(readerL, Locations.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static String genID(int length) {
        String choice = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            Random randGen = new Random();
            int randInt = randGen.nextInt(choice.length());
            if (randInt - 1 == -1) {
                randInt = 0;
            } else {
                randInt--;
            }
            temp.append(choice.charAt(randInt));
        }
        return temp.toString();
    }

    public Event genEvent(String personID, int year, String type, String eventID) {
        Random rand = new Random();
        int n = rand.nextInt(locationList.getData().size());
        Event event = locationList.getData().get(n);
        event.setDescendant(currentUser);
        event.setEventType(type);
        event.setPersonID(personID);
        event.setEventID(eventID);
        event.setYear(year);
        return event;
    }

    public Person genPerson(String gender, String spouse, String personID) {
        Random rand = new Random();
        String firstName;
        if (gender.equals("f")) {
            int n = rand.nextInt(fList.getData().size());
            firstName = fList.getData().get(n);
        } else {
            int n = rand.nextInt(mList.getData().size());
            firstName = mList.getData().get(n);
        }
        String mother = genID(15);
        String father = genID(15);
        int n = rand.nextInt(sList.getData().size());
        String lastName = sList.getData().get(n);
        return new Person(personID, currentUser, firstName, lastName, gender, father, mother, spouse);
    }

    private class NameList {
        private final List<String> data;

        NameList(List<String> data) {
            this.data = data;
        }

        List<String> getData() {
            return data;
        }
    }

    private class Locations {
        private final List<Event> data;

        Locations(List<Event> data) {
            this.data = data;
        }

        List<Event> getData() {
            return data;
        }
    }
}
