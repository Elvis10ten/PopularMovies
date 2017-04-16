package mobymagic.com.popularmovies.data.local.realm.model;

import io.realm.RealmObject;

public class RealmInt extends RealmObject {

    private int val;

    public RealmInt() {}

    public RealmInt(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}