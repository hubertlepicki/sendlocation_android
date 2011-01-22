package com.hubertlepicki.sendlocation;

import android.os.Bundle;
import android.widget.EditText;
import android.app.Activity;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import java.lang.Double;
import java.lang.Integer;
import java.net.URLEncoder;

public class AddDescriptionActivity extends Activity {
    private Button submitButton;
    private EditText description;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_description);
        submitButton = (Button)this.findViewById(R.id.submit_button);
        description = (EditText)this.findViewById(R.id.description);
        submitButton.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent msg = new Intent(Intent.ACTION_SEND);
            msg.setType("text/plain");
            msg.putExtra(Intent.EXTRA_SUBJECT, description.getText().toString());
            msg.putExtra(Intent.EXTRA_TEXT, locationUrl(description.getText().toString()));
            startActivity(Intent.createChooser(msg, "Share location..."));
          }
        });
    }

    private String locationUrl(String description) {
      return "http://sendlocation.heroku.com/map/" + 
             Double.toString(SendLocationActivity.currentLocation.getLatitudeE6()/1000000.0) + 
             "/" +
             Double.toString(SendLocationActivity.currentLocation.getLongitudeE6()/1000000.0) + 
             "/" +
             Integer.toString(SendLocationActivity.currentZoomLevel) + 
             "/normal/" +
             URLEncoder.encode(description)
             ;
    }
}
