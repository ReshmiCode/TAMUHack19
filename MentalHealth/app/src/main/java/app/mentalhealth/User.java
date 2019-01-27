package app.mentalhealth;

import java.util.ArrayList;

public class User {
    private String email, password;
    private boolean isDoctor;
    private ArrayList<String> moods, meds;

    public User(String email, String password, boolean isDoctor) {
        this.email = email;
        this.password = password;
        this.isDoctor = isDoctor;
        moods = new ArrayList<>();
        meds = new ArrayList<>();
    }
}
