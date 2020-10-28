package GameOfLife;

import java.util.*;

public class boardDB {
	static int size, position, prevPos, total;
	static List<boolean[][]> boardList;
	static boolean[][] saveBool;
	static boolean boolReturn[][];
	
	
	boardDB() {
		size = 50;
		position = 0;
		total = 0;
		boardList = new ArrayList<boolean[][]>(5);
		boolReturn = new boolean[size][size];
	}
	
	//Allows for a total of 5 previous board states.
	public void saveBoard(boolean board[][]) {
		saveBool = board;
	}
	
	//Goes through the list.
	public boolean[][] getPreviousBoard() {
		return saveBool;
	}
	
	public static boolean[][] getRandomPreset() {
		Random rnd = new Random();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				boolReturn[i][j] = rnd.nextInt(100) < 30;// random movement
			}
		}
		return boolReturn;
	}
	
	public static boolean[][] getExploderPreset() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				boolReturn[i][j] = false;
			}
		}
		boolReturn[23][23] = true;
		boolReturn[23][25] = true;
		boolReturn[23][27] = true;
		boolReturn[24][23] = true;
		boolReturn[24][27] = true;
		boolReturn[25][23] = true;
		boolReturn[25][27] = true;
		boolReturn[26][23] = true;
		boolReturn[26][27] = true;
		boolReturn[27][23] = true;
		boolReturn[27][25] = true;
		boolReturn[27][27] = true;
		return boolReturn;
	}
	
	public static boolean[][] getGliderPreset() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				boolReturn[i][j] = false;
			}
		}
		boolReturn[24][25] = true;
		boolReturn[25][26] = true;
		boolReturn[26][24] = true;
		boolReturn[26][25] = true;
		boolReturn[26][26] = true;
		return boolReturn;
	}
}
