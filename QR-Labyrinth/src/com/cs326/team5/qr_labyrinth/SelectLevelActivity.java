package com.cs326.team5.qr_labyrinth;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class SelectLevelActivity extends Activity {
	
	private int qrheight = 400;
	private int qrwidth = 400;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_level);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
