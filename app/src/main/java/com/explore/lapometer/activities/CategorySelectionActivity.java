package com.explore.lapometer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.explore.lapometer.util.AppConstants;

import com.explore.lapometer.R;

public class CategorySelectionActivity extends Activity {
    Button buttonCategory1;
    Button buttonCategory2;
    Button buttonCategory3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        setComponents();
    }

    private void setComponents() {
        buttonCategory1 = (Button) findViewById(R.id.buttonCategory1);
        buttonCategory2 = (Button) findViewById(R.id.buttonCategory2);
        buttonCategory3 = (Button) findViewById(R.id.buttonCategory3);

        buttonCategory1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategorySelectionActivity.this, TopParticipantsActivity.class);
                intent.putExtra(AppConstants.CATEGORY, 1);
                startActivity(intent);
            }
        });
        buttonCategory2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategorySelectionActivity.this, TopParticipantsActivity.class);
                intent.putExtra(AppConstants.CATEGORY, 2);
                startActivity(intent);
            }
        });
        buttonCategory3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategorySelectionActivity.this, TopParticipantsActivity.class);
                intent.putExtra(AppConstants.CATEGORY, 3);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.category_selection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
