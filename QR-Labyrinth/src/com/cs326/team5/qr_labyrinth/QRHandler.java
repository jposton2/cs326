package com.cs326.team5.qr_labyrinth;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * Handles generation of Grids from QR codes
 * @author Team 5
 */
public class QRHandler {
	/**
	 * Generates a Grid based on the contents returned from a scan
	 * @param contents contents returned from a QR code scan
	 * @param qrwidth width of the qr code
	 * @param qrheight height of a qr code
	 * @param name name of the qr code
	 * @return Grid Grid generated from contents returned from scan
	 */
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
        // We start with Y, add two, and then check for the ending part of the code
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
        
        // Add three to xEnds because of earlier adding 3
        yEnd += 3;
        xEnd += 3;
        yEnd = xEnd;
        
        // Convert the matrix to a gridArray
        PointData[][] gridArray = new PointData[xEnd][yEnd];
        for(int i = 0; i < xEnd; i++){
        	for(int j = 0; j < yEnd; j++){
        		gridArray[i][j] = (matrix.get((i*xSize)+xStart, (j*ySize)+yStart) ? new PointData(true,i, j) : new PointData(false, i, j));
        	}
        }
        
		return new Grid(gridArray, xEnd, yEnd, name, 0, 1);
	}
	

	/**
	 * Returns the i'th default level qr code contents
	 * @param i
	 * @return s qr code contents
	 */
	public static String getLevel(int i){
		String s = "null";
		switch(i){
		case 1:
			s = "funstringnumberone";
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
			s = "Venkatanarasimhara";
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
		

