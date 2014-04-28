package com.cs326.team5.qr_labyrinth;

import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRHandler {
	public static Grid getGrid(String contents, int qrwidth, int qrheight, String name){
		
		QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = null;
        try{
       	 matrix = writer.encode(contents,  BarcodeFormat.QR_CODE,  qrwidth, qrheight);  	 
	        
        }
        catch (WriterException e) {
       	    e.printStackTrace();
        }
        if(matrix == null){
        	Log.d("Quick stuff happening","matrix is null");
        	return null;
        }
        
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
        Log.w("MainActivity", Integer.toString(yStart));
        Log.w("MainActivity", Integer.toString(xEnd));
        Log.w("MainActivity", Integer.toString(yEnd));
        
        
        PointData[][] gridArray = new PointData[xEnd][yEnd];
        // This was my test case. Prints out whether or not a cell is black at the relative value
//        String l = "";
        for(int i = 0; i < xEnd; i++){
        	for(int j = 0; j < yEnd; j++){
        		gridArray[i][j] = (matrix.get((i*xSize)+xStart, (j*ySize)+yStart) ? new PointData(true,i, j) : new PointData(false, i, j));
//        		l+= String.valueOf(gridArray[i][j].isBlack());
        	}
//        	Log.w("Grid", l);
//        	l = "";
        }
        Bitmap bmpMonochrome = Bitmap.createBitmap(xEnd*xSize, yEnd*ySize, Bitmap.Config.ARGB_8888);
        int[] pixels = new int[10];
        pixels[0] = 0;
        for(int i = 0; i <= xEnd+8; i++){
        	for(int j = 0; j <= yEnd+8; j++){
        		for(int l = 0; l <= xSize; l ++){
        			for(int m = 0; m <= ySize; m++){
                		if(matrix.get((i*xSize)+xStart, (j*ySize)+yStart))
                			bmpMonochrome.setPixel(i+l, j+m, Color.BLACK);
                		else
                			bmpMonochrome.setPixel(i+l, j+m, Color.WHITE);
        		
        				}
        			}
        		}
        }
		String file_path = Environment.getExternalStorageDirectory() + "/Lolz";
		File dir = new File(file_path);
		if(!dir.exists())
		    dir.mkdirs();
	        try {
	            FileOutputStream out = new FileOutputStream(new File(dir, name+".bmp"));
	            bmpMonochrome.compress(Bitmap.CompressFormat.PNG, 90, out);
	            out.flush();
	            out.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }	         //levelList.add(g);
		return new Grid(gridArray, xEnd, yEnd, name, 0);
	}
	

	public static String getLevel(int i){
		String s = "null";
		switch(i){
		case 1:
			//s = "Taumatawhakatangihangakoauauotamateapokaiwhenuakitanatahu";
			s = "f";
			break;
		case 2:
			s = "Llanfairpwllgwyngyll";
			break;
		case 3:
			s = "Chaubunagungamaug";
			break;
		case 4:
			s = "Pekwachnamaykoskwaskwaypinwanik";
			break;
		case 5:
			s = "Venkatanarasimharajuvaripeta";
			break;
		case 6:
			s = "Onafhankelijkheidsplein";
			break;
		case 7:
			s = "Newtownmountkennedy";
			break;
		case 8:
			s = "Bienwaldziegelhutte";
			break;
		case 9:
			s = "Gasselterboerveenschemond";
			break;
		case 10:
			s = "Muckanaghederdauhaulia";
			break;
		}
		return s;
	}

}
		

