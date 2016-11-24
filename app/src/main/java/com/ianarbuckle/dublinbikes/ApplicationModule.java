package com.ianarbuckle.dublinbikes;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ian Arbuckle on 10/10/2016.
 *
 */
@Module
public class ApplicationModule {
  private DublinBikesApplication application;

  public ApplicationModule(DublinBikesApplication application) {
    this.application = application;
  }

  @Provides
  public Context provideContext() {
    return application.getApplicationContext();
  }

}
