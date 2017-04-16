package mobymagic.com.popularmovies.data.local.realm.model;

import io.realm.RealmObject;

public class RealmBoolean extends RealmObject {

    private boolean val;

    public RealmBoolean() {}

    public RealmBoolean(boolean val) {
        this.val = val;
    }

    public boolean getVal() {
        return val;
    }

    public void setVal(boolean val) {
        this.val = val;
    }

}