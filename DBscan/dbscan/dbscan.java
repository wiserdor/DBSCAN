package dbscan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class dbscan {
	Utility utility;

	public double e = 5;
	public Vector<List> resultList = new Vector<List>();
	public Vector<Point> pointList;
	public Vector<Point> borderList;
	public Vector<Point> Neighbours;
	public String filePath,savePath;

	public double minpt;

	/**
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		String command;
		
		System.out.println(" _       __     __                             __           ____               _           __     _____________");
		 System.out.println("| |     / /__  / /________  ____ ___  ___     / /_____     / __ \\_________    (_)__  _____/ /_   <  / ___/__  /");
		 System.out.println("| | /| / / _ \\/ / ___/ __ \\/ __ `__ \\/ _ \\   / __/ __ \\   / /_/ / ___/ __ \\  / / _ \\/ ___/ __/   / / __ \\ /_ <"); 
		 System.out.println("| |/ |/ /  __/ / /__/ /_/ / / / / / /  __/  / /_/ /_/ /  / ____/ /  / /_/ / / /  __/ /__/ /_    / / /_/ /__/ /"); 
		 System.out.println("|__/|__/\\___/_/\\___/\\____/_/ /_/ /_/\\___/   \\__/\\____/  /_/   /_/   \\____/_/ /\\___/\\___/\\__/   /_/\\____/____/");  
		 System.out.println("                                                                        /___/ 								  ");	                                  
		
		System.out.println("Please enter the following String: [Minimum points] [Distance] [File Path] [Folder Output]");
		Scanner s = new Scanner(System.in);
		command = s.nextLine();
		String[] arr=command.split(" ");
		double minpt=Double.parseDouble(arr[0]);
		double dist=Double.parseDouble(arr[1]);
		String path=arr[2];
		String savePath=arr[3];
		
		
		dbscan p = new dbscan();
		p.applyDbscan(minpt,dist,path,savePath);

		return;
	}

	void writeToCSV() {
		String FILE_HEADER = "x,y,z";
		String clustN = "clust1";
		Point p;
		FileWriter fileWriter = null;
		try {

			for (int i = 0; i < this.resultList.size(); i++) {
				fileWriter = new FileWriter(savePath + clustN.substring(0, clustN.length() - 2) + i + ".csv");
				// Write the CSV file header
				fileWriter.append(FILE_HEADER.toString());
				// Add a new line separator after the header
				fileWriter.append("\n");
				for (int j = 0; j < this.resultList.get(i).size(); j++) {
					// x
					p = (Point) this.resultList.get(i).get(j);
					fileWriter.append(String.valueOf(p.getX()));
					fileWriter.append(",");
					// y
					fileWriter.append(String.valueOf(p.getY()));
					fileWriter.append(",");
					// z
					fileWriter.append(String.valueOf(p.getZ()));
					fileWriter.append("\n");
				}
				fileWriter.close();
			}

		} catch (

		Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		}
	}

	void printClusters() {
		int index = 0;
		while (!pointList.isEmpty()) {
			System.out.println(pointList.get(index).getX() + " " + pointList.get(index).getY() + " "
					+ pointList.get(index).getZ());
			index++;
		}
	}

	public void pointsFromFile() throws FileNotFoundException {
		int x = 0, y = 0, z = 0, i = 0;
		Scanner scanner = new Scanner(new File(filePath));
		scanner.useDelimiter(",");
		this.pointList = new Vector<Point>();
		String[] line = scanner.nextLine().split(",");
		for (i = 0; i < line.length; i++) {
			switch (line[i]) {
			case "X":
			case "x":
				if (x == 0)
					x = i;
				break;
			case "Y":
			case "y":
				if (y == 0)
					y = i;
				break;
			case "Z":
			case "z":
				if (z == 0)
					z = i;
				break;
			}
		}
		while (scanner.hasNextLine()) {
			line = scanner.nextLine().split(",");
			this.pointList.add(
					new Point(Double.parseDouble(line[x]), Double.parseDouble(line[y]), Double.parseDouble(line[z])));

		}
		scanner.close();

	}

	/**
	 * 
	 */
	public void applyDbscan(Double minpt,Double dis,String path,String sPath) {
		this.minpt=minpt;
		this.e=dis;
		this.filePath=path;
		this.savePath=sPath;
		// Define utility class
		utility = new Utility(this);

		// Get list of points
		try {
			this.pointsFromFile();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Clear results
		resultList.clear();

		// clear memory of visited points
		utility.VisitList.clear();

		int index2 = 0;

		while (index2 < pointList.size()) {
			Point p = pointList.get(index2);

			if (!utility.isVisited(p)) {
				utility.Visited(p);

				Neighbours = utility.getNeighbours(p);

				if (Neighbours.size() >= minpt) {
					int ind = 0;
					while (Neighbours.size() > ind) {
						Point r = Neighbours.get(ind);
						if (!utility.isVisited(r)) {
							utility.Visited(r);
							Vector<Point> Neighbours2 = utility.getNeighbours(r);
							if (Neighbours2.size() >= minpt) {
								Neighbours = utility.Merge(Neighbours, Neighbours2);
							}
						}
						ind++;
					}
					System.out.println("N" + Neighbours.size());
					resultList.add(Neighbours);
				}
			}
			index2++;
		}
		this.writeToCSV();
	}
}
