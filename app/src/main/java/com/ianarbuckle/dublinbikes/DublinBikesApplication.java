package com.ianarbuckle.dublinbikes;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.ianarbuckle.dublinbikes.authentication.firebase.AuthenticationHelper;
import com.ianarbuckle.dublinbikes.authentication.firebase.AuthenticationHelperImpl;

/**
 * Created by Ian Arbuckle on 23/11/2016.
 *
 */

public class DublinBikesApplication extends Application {

  private ApplicationComponent applicationComponent;

  private AuthenticationHelper authenticationHelper;

  private static DublinBikesApplication appInstance;

  @Override
  public void onCreate() {
    super.onCreate();

    getApplicationComponent(this);

    initFirebase();

  }

  private void initFirebase() {
    if(!FirebaseApp.getApps(this).isEmpty()) {
      appInstance = this;
      FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
      authenticationHelper = new AuthenticationHelperImpl(firebaseAuth);
    }
  }

  public static DublinBikesApplication getAppInstance() {
    return appInstance;
  }

  public AuthenticationHelper getAuthenticationHelper() {
    return authenticationHelper;
  }

  public static ApplicationComponent getApplicationComponent(Context context) {
    DublinBikesApplication application = (DublinBikesApplication) context.getApplicationContext();
    if(application.applicationComponent == null) {
      application.applicationComponent = DaggerApplicationComponent.builder()
          .applicationModule(application.getApplicationModule())
          .build();
    }
    return application.applicationComponent;
  }

  protected ApplicationModule getApplicationModule() {
    return new ApplicationModule(this);
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }


}
