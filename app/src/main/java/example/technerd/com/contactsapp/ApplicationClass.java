package example.technerd.com.contactsapp;

import android.app.Application;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import java.util.List;

public class ApplicationClass extends Application
{
    public static final String APPLICATION_ID ="801BA5E4-CD7D-908B-FF2A-9F85DB911B00";
    public static final String API_KEY ="735E457E-3349-7242-FF5A-0A0A941A5F00";
    //public static final String SERVER_URL ="https://api.backendless.com&quot";
    public static final String SERVER_URL ="https://api.backendless.com";

    public static BackendlessUser user;

    public static List<Contact>contacts;


    @Override
    public void onCreate() {
        super.onCreate();

        Backendless.setUrl( SERVER_URL );
        Backendless.initApp( getApplicationContext(),
                APPLICATION_ID,
                API_KEY );
    }
}
