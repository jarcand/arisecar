package fog.vision;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class FogMap {
	
	public enum FogEnum{
		known, unknown;
	}
	
	ArrayList<ArrayList<FogEnum>> fogMap = new ArrayList<ArrayList<FogEnum>>();
	
	int size = 20;
	int maxTop = 25;
	int maxBottom = 25;
	int maxLeft = 25;
	int maxRight = 25;
	int number = 60;
	
	public FogMap(){
		for(int i=0; i<number; i++){
			fogMap.add(new ArrayList<FogEnum>());
			for(int j=0; j<number; j++){
				fogMap.get(i).add(FogEnum.unknown);
			}
		}
	}
	
	public void draw(Graphics2D g){
		for(int i=maxLeft; i<fogMap.size()-maxRight; i++){
			for(int j=maxTop; j<fogMap.get(i).size()-maxBottom; j++){
				if(fogMap.get(i).get(j) == FogEnum.unknown){
					g.setColor(Color.gray);
					g.fillRect(i*size-number/2*size, j*size-number/2*size, size, size);
				}
			}
		}
	}
	
	public void setKnown(int x, int y){
		fogMap.get(x).set(y, FogEnum.known);
		if(x < maxLeft){
			maxLeft = x-2;
		}
		if(y < maxTop){
			maxTop = y-2;
		}
		while(x > fogMap.size()-maxRight){
			maxRight -= 4;
		}
		while(y >= fogMap.size()-maxBottom){
			maxBottom -= 3;
		}
	}

}
