package com.hubertlepicki.sendlocation;

import android.os.Bundle;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import android.widget.LinearLayout;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ZoomControls;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;

public class SendLocationActivity extends MapActivity
{
    private MapView map;
    private MapController controller;
    private ZoomControls zoomControls;
    private SendLocationOverlay overlay;
    static public com.google.android.maps.GeoPoint currentLocation;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initMapView();
        initMyLocation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Send Location");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      if (item.getItemId() == 1) {
        startActivity(new Intent(this, AddDescriptionActivity.class));
      }
      return true;
    }


    @Override
    public void onStart() {
      super.onStart();
      overlay.enableMyLocation();
    }

    @Override
    public void onPause() {
      super.onPause();
      overlay.disableMyLocation();
    }

    @Override
    public boolean isRouteDisplayed() {
      return false;
    }

    private void initMapView() {
      map = (MapView) findViewById(R.id.map);
      controller = map.getController();
      map.setSatellite(false);
      //map.setBuiltInZoomControls(true);
      zoomControls = (ZoomControls) findViewById(R.id.zoomcontrols);
      zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                      controller.zoomIn();
              }
      });
      zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                      controller.zoomOut();
              }
      });
    }

    private class SendLocationOverlay extends MyLocationOverlay {
      SendLocationOverlay(android.content.Context context, MapView mapView) {
        super(context, mapView);
      }

      public void onLocationChanged(android.location.Location location) {
        super.onLocationChanged(location);
        controller.animateTo(overlay.getMyLocation());
        currentLocation = overlay.getMyLocation();
      }
    }

    private void initMyLocation() {
      overlay = new SendLocationOverlay(this, map);
      overlay.runOnFirstFix(new Runnable() {
        public void run() {
          controller.setZoom(8);
          controller.animateTo(overlay.getMyLocation());
          currentLocation = overlay.getMyLocation();
        }
      });
      map.getOverlays().add(overlay);
    }
}

