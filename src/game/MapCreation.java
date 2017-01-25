package game;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MapCreation {

	private static int[][] map;
	private BufferedImage tileSheet;

	/**
	 * Reads from a text file and creates a array representation of the map
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static void createFromFile() throws NumberFormatException, IOException {
		int x = 0;
		int y = 0;
		int map[][] = new int[5][5];
		
		// Read in lines of the text file map
		BufferedReader in = new BufferedReader(new FileReader("src/game/map.txt"));
		
		// The current line
		String line;
	
		// Split lines on their separating value ,
		while((line = in.readLine()) != null) {
			String[] values = line.split(",");
		
			for(String str : values) {
				int str_int = Integer.parseInt(str);
				map[x][y] = str_int;
				System.out.print(map[x][y] + " ");
				x++;			
	 		}
			System.out.println(" ");
			y++;
			x = 0;
		}
		in.close();
	}
	
	public static void getTiles() {
		// TODO: Method that imports the necessary tiles
	}
	
	public static void main(String args[]) throws NumberFormatException, IOException {
		createFromFile();
	}

}
