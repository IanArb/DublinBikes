package com.ianarbuckle.dublinbikes.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.ianarbuckle.dublinbikes.R;

/**
 * Created by Ian Arbuckle on 14/12/2016.
 *
 */

public class CustomMarkerRenderer extends DefaultClusterRenderer<MarkerItemModel> {

  private final Context mContext;

  private final IconGenerator iconGenerator;

  public CustomMarkerRenderer(Context context, GoogleMap map, ClusterManager<MarkerItemModel> clusterManager) {
    super(context, map, clusterManager);

    mContext = context;
    iconGenerator = new IconGenerator(mContext.getApplicationContext());
  }

  @Override
  protected void onBeforeClusterItemRendered(MarkerItemModel item, MarkerOptions markerOptions) {
    iconGenerator.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_directions_bike));

    final Bitmap icon = iconGenerator.makeIcon();
    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
  }

  @Override
  protected boolean shouldRenderAsCluster(Cluster<MarkerItemModel> cluster) {
    return cluster.getSize() > 1;
  }
}
