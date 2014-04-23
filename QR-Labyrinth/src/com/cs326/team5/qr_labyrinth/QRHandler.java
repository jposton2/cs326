package com.cs326.team5.qr_labyrinth;

import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRHandler {
	public Grid getGrid(String contents, int qrwidth, int qrheight){
		
		QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = null;
        try{
       	 matrix = writer.encode(contents,  BarcodeFormat.QR_CODE,  qrwidth, qrheight);  	 
	        
        }
        catch (WriterException e) {
       	    e.printStackTrace();
        }
        if(matrix == null)
       	 return null;
        
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
        
        
        PointData[][] gridArray = new PointData[xEnd*xSize-xStart][yEnd*xSize-yStart];
        // This was my test case. Prints out whether or not a cell is black at the relative value
        for(int i = xStart; i < xEnd*xSize; i+=xSize){
        	for(int j = xStart; j < yEnd*ySize; j+=ySize){
        		gridArray[i-xStart][j-yStart] = (matrix.get(i, j) ? new PointData(true,i-xStart, j-yStart) : new PointData(false, i-xStart, j-yStart));
        	}
        }
		return new Grid(gridArray, xEnd-xStart, yEnd-yStart);
	}
	

	public String getLevel(int i){
		String s = "null";
		switch(i){
		case 1:
			s = "Taumatawhakatangihangakoauauotamateapokaiwhenuakitanatahu";
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
		

