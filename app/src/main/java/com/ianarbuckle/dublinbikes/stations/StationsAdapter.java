package com.ianarbuckle.dublinbikes.stations;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ianarbuckle.dublinbikes.R;
import com.ianarbuckle.dublinbikes.models.Station;
import com.ianarbuckle.dublinbikes.utiity.TextUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Ian Arbuckle on 12/12/2016.
 *
 */

public class StationsAdapter extends RecyclerView.Adapter<StationsAdapter.StationsCardViewHolder> {

  private List<Station> stationArrayList;

  Context context;

  public static class StationsCardViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.nameTv)
    TextView nameTv;
    @BindView(R.id.bikeStandsTv)
    TextView standsTv;
    @BindView(R.id.bikesTv)
    TextView availTv;
    @BindView(R.id.slotsTv)
    TextView slotsTv;
    @BindView(R.id.updateTv)
    TextView updateTv;
    @BindView(R.id.statusTv)
    TextView statusTv;
    @BindView(R.id.shareIv)
    ImageView shareIv;
    @BindView(R.id.distanceTv)
    TextView distanceTv;

    public StationsCardViewHolder(final View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      shareIv.setVisibility(View.GONE);
      distanceTv.setVisibility(View.GONE);
    }

  }

  public StationsAdapter(List<Station> stationList, Context context) {
    this.stationArrayList = stationList;
    this.context = context;
  }

  @Override
  public StationsCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_stations_list, parent, false);

    return new StationsCardViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final StationsCardViewHolder holder, final int position) {
    TextView name = holder.nameTv;
    TextView bikeStands = holder.standsTv;
    TextView avail = holder.availTv;
    TextView slots = holder.slotsTv;
    TextView status = holder.statusTv;
    TextView update = holder.updateTv;

    String nameValue = stationArrayList.get(position).getName();
    name.setText(nameValue);
    String bikeStandsValue = String.valueOf(stationArrayList.get(position).getBikeStands());
    bikeStands.setText(bikeStandsValue);
    String availValue = String.valueOf(stationArrayList.get(position).getAvailableBikes());
    avail.setText(availValue);
    String slotsValue = String.valueOf(stationArrayList.get(position).getAvailableBikeStands());
    slots.setText(slotsValue);
    long lastUpdate = stationArrayList.get(position).getLastUpdate();
    String formatTime = TextUtils.getDuration(lastUpdate);
    update.setText(formatTime);
    String statusValue = String.valueOf(stationArrayList.get(position).getStatus());
    status.setText(statusValue);

    if(statusValue.equals("OPEN")) {
      status.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));
    } else {
      status.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
    }

    holder.setIsRecyclable(true);
  }

  @Override
  public int getItemCount() {
    return stationArrayList.size();
  }

}
