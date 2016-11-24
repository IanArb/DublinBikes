package com.ianarbuckle.dublinbikes;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Ian Arbuckle on 23/11/2016.
 *
 */

public class DublinBikesApplication extends Application {

  // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
  private static final String TWITTER_KEY = "eVuhajCjJ7AzIFq8R3gue9dKw";
  private static final String TWITTER_SECRET = "5Rmw0ys4E44ZtK3Fwr6TDUJD3ir05KcVc24fxTf46zTynzZJ67";

  private ApplicationComponent applicationComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    getApplicationComponent(this);

    TwitterAuthConfig authConfig = initTwitter();
    initFabric(authConfig);

  }

  @NonNull
  private TwitterAuthConfig initTwitter() {
    return new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
  }

  private void initFabric(TwitterAuthConfig authConfig) {
    Fabric.with(this, new Twitter(authConfig));
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