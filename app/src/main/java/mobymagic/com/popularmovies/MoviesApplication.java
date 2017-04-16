package mobymagic.com.popularmovies;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.v7.app.AppCompatDelegate;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.io.File;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;
import timber.log.Timber;

public class MoviesApplication extends Application {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    // region Static Variables
    private static MoviesApplication currentApplication = null;
    // endregion

    // region Member Variables
    private RefWatcher refWatcher;
    // endregion

    // region Lifecycle Methods
    @Override
    public void onCreate() {
        super.onCreate();

        initializeTimber();
        initializeLeakCanary();
        initializeRealm();
        initializeStrictMode();

        currentApplication = this;
    }
    // endregion

    // region Helper Methods
    public static MoviesApplication getInstance() {
        return currentApplication;
    }

    public static File getCacheDirectory() {
        return currentApplication.getCacheDir();
    }

    public static RefWatcher getRefWatcher(Context context) {
        MoviesApplication application = (MoviesApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private void initializeLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        refWatcher = LeakCanary.install(this);
    }

    private void initializeRealm(){
        Realm.init(this);
        RealmConfiguration realmConfiguration;

        if(BuildConfig.DEBUG) {
            //Since there will/might be multiple schema changes in development. Just delete the realm
            // file when there is schema change
            realmConfiguration = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
        } else {
            // Don't forget to bump version number up and provide a migration when schema changes
            realmConfiguration = new RealmConfiguration.Builder()
                    .schemaVersion(1) // Must be bumped when the schema changes
                    .migration(new RealmDataMigration())
                    .build();
        }

        Realm.setDefaultConfiguration(realmConfiguration);
    }

    private void initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
                // Add the line number to the tag
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return super.createStackElementTag(element) + ":" + element.getLineNumber();
                }
            });
        } else {
            //Timber.plant(new ReleaseTree());
        }
    }

    private void initializeStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }
    }

    // endregion

    // region Inner Classes

    private class RealmDataMigration implements RealmMigration {

        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
            final RealmSchema schema = realm.getSchema();

            if (oldVersion == 1) {
                // To fix any schema changes in the future
                /*final RealmObjectSchema changedSchema = schema.get("schemaName");
                changedSchema.addField("changedProperty", String.class);*/
            }
        }
    }

    // endregion
}