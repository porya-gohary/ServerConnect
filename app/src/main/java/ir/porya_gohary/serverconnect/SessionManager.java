package ir.porya_gohary.serverconnect;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    Context context;
    SharedPreferences preferences;
    String pr_name="Pref";

    boolean logedIn=false;
    String prl="logedin";


    public SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(pr_name,Context.MODE_PRIVATE);
    }

    public boolean isLogedIn() {
        boolean l = preferences.getBoolean(prl,false);
        return l;
    }

    public void setLogedIn(boolean logedIn) {
        preferences.edit().putBoolean(prl,logedIn).apply();
    }
}
