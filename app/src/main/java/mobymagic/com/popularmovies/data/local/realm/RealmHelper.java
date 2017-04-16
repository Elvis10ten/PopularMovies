package mobymagic.com.popularmovies.data.local.realm;

import android.support.annotation.NonNull;

import io.realm.Realm;
import io.realm.RealmObject;

public class RealmHelper {

    /**
     * Get the realm object next integer primary key. Since realm doesn't offer this functionality
     * currently, this method takes a realm object and its primary column name, then gets the maximum
     * value that has been assigned to that column, then increments it by one and returns. If no maximum
     * number was found, it returns 1.
     * @param realm A realm object
     * @param realmObjectClass The Realm object to work with
     * @param primaryColumn The realm object integer primary key field name
     * @param <T> The Realm object type
     * @return The next value that can be used as this object primary key value
     */
    public static <T extends RealmObject> int getNextIntegerPrimaryKeyValue(@NonNull Realm realm,
                                                                      @NonNull Class<T> realmObjectClass,
                                                                      @NonNull String primaryColumn) {
        // increment index
        Number num = realm.where(realmObjectClass).max(primaryColumn);
        int nextID;

        if(num == null) {
            nextID = 1;
        } else {
            nextID = num.intValue() + 1;
        }

        return nextID;
    }

    /**
     * Helper methods that copies an object to realm while beginning and committing transaction in the background
     * @param realm A realm instance
     * @param object A realm object to save
     * @param <T> The realm object type
     */
    public static <T extends RealmObject> void copyToRealm(@NonNull Realm realm, @NonNull T object) {
        realm.beginTransaction();
        realm.copyToRealm(object);
        realm.commitTransaction();
    }
}
