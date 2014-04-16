package com.cs326.team5.qr_labyrinth;




import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	private int qrheight = 400;
	private int qrwidth = 400;
	
	// QR example here https://github.com/zxing/zxing/blob/master/androidtest/src/com/google/zxing/client/androidtest/ZXingTestActivity.java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void scan(View view){
    	Intent intent = new Intent("com.google.zxing.client.android.SCAN");
    	intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
    	startActivityForResult(intent, 0);
    }
    

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	   if (requestCode == 0) {
    	      if (resultCode == RESULT_OK) {
    	    	 TextView test = (TextView) findViewById(R.id.textView2);
    	         String contents = intent.getStringExtra("SCAN_RESULT");
    	         String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
    	         test.setText(contents);
    	         QRCodeWriter writer = new QRCodeWriter();
    	         BitMatrix matrix = null;
    	         try{
    	        	 matrix = writer.encode(contents,  BarcodeFormat.QR_CODE,  qrwidth, qrheight);  	 
        	        
    	         }
    	         catch (WriterException e) {
    	        	    e.printStackTrace();
    	        }
    	         if(matrix == null)
    	        	 return;
    	         
    	         // squareCount is the size of the "pixels" for each QR code segment
    	         int xSize = 0;
    	         int ySize = 0;
    	         // Start is where the QR Code start is excluding the leading whitespace
    	         int xStart = 0;
    	         int yStart = 0;
    	         int xEnd = 0;
    	         int yEnd = 0;
    	         
    	         loop:
    	         for(int i = 0; i < qrwidth; i++){
    	        	for(int j = 0; j < qrheight; j++){
    	        		if(xStart == 0 && matrix.get(i, j)){
    	        			xStart = i;
    	        			yStart = j;
    	        			break loop;
    	        		}	
    	        	}
    	        }
    	         int currX = xStart;
    	         int currY = yStart;
    	         while(matrix.get(currX, currY)){
    	        	 currX++;
    	        	 currY++;
    	         }
    	         
    	 
    	         // -1 for obv reasons
    	         xSize = currX - xStart-1;
    	         ySize = currY - yStart-1;
    	         
    	         // This stuff gets how many blocks to the ending corners of the QR code
    	         int blackCount = 1;
    	         currX = xStart + xSize*2;
    	         currY = yStart;
    	         while(blackCount < 7){
    	        	 xEnd++;
    	         	 currX += xSize;
    	        	 if(matrix.get(currX, currY))
    	        		 blackCount ++;
    	       
    	        	 else
    	        		 blackCount = 0;
    	         }
    	         currX = xStart;
    	         currY = yStart + ySize*2;
    	         blackCount = 0;
    	         while(blackCount < 7){
    	        	 yEnd++;
    	         	 currY += ySize;
    	        	 if(matrix.get(currX, currY))
    	        		 blackCount ++;
    	       
    	        	 else
    	        		 blackCount = 0;
    	         }
    	         
    	         // Print out the stuff found up there to the LogCat.
    	         Log.w("MainActivity", Integer.toString(xSize));
    	         Log.w("MainActivity", Integer.toString(ySize));
    	         Log.w("MainActivity", Integer.toString(xEnd));
    	         Log.w("MainActivity", Integer.toString(yEnd));
    	         
    	         
    	         // This was my test case. Prints out wheither or not a cell is black at the relative value
    	         Log.w("MainActivity", Boolean.toString(matrix.get(xStart + (xSize * 9), yStart + (ySize * 3))));
    	         
    	      } else if (resultCode == RESULT_CANCELED) {
    	         // Handle cancel
    	      }
    	   }
    	}
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
