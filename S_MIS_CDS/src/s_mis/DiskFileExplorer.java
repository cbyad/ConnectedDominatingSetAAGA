package s_mis;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DiskFileExplorer {
	public static int edgeThreshold =55 ;
	private String initialpath = "";
	private Boolean recursivePath = false;
	public int filecount = 0;
	public int dircount = 0;

	/**
	 * Constructeur
	 * @param path chemin du répertoire
	 * @param subFolder analyse des sous dossiers
	 */
	public DiskFileExplorer(String path, Boolean subFolder) {
		super();
		this.initialpath = path;
		this.recursivePath = subFolder;
	}

	public DiskFileExplorer(){}

	public void list() {
		//this.listDirectory(this.initialpath);
		this.getInstance(this.initialpath);

	}

	private void listDirectory(String dir) {
		File file = new File(dir);
		File[] files = file.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory() == true) {
					System.out.println("Dossier: " + files[i].getAbsolutePath());
					this.dircount++;
				} else {
					if(files[i].getName().endsWith(".points")){
						System.out.println("  Fichier: " + files[i].getName());
						this.filecount++;
					}

				}
				if (files[i].isDirectory() == true && this.recursivePath == true) {
					this.listDirectory(files[i].getAbsolutePath());
				}
			}
		}
	}  

	public ArrayList<ArrayList<Point>> getInstance(String dir) {
		ArrayList<ArrayList<Point>> instances = new ArrayList<>();
		File file = new File(dir);
		File[] files = file.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory() == true) {
					System.out.println("Dossier: " + files[i].getAbsolutePath());
					this.dircount++;
				} else {
					if(files[i].getName().endsWith(".points")){
						instances.add(GeneratorRandom.readFromFile(files[i].getPath()));
						this.filecount++;
					}

				}
				if (files[i].isDirectory() == true && this.recursivePath == true) {
					this.listDirectory(files[i].getAbsolutePath());
				}
			}
		}
		return instances ;
	}  


	
	public static ArrayList<Point> computeAlgoLi(ArrayList<Point> pts){
		ArrayList<PointWithColor> MIS=Algorithms.computeMisRef16(pts,edgeThreshold);
		
		ArrayList<PointWithColor> greyPoint = Algorithms.tagPointGrey(pts, Algorithms.convertion(MIS));
		ArrayList<PointWithColor> blueTag = new ArrayList<>();
		blueTag = Algorithms.algoLi(MIS, greyPoint, pts, edgeThreshold);		
		ArrayList<Point> MCDS = (ArrayList<Point>)Algorithms.convertion(MIS).clone();
		
		for(PointWithColor p : blueTag)
			MCDS.add(p);
		return MCDS;
			
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {

		int[] size={800,1000,1300,1500};
		String[] path = {"../S_MIS_CDS/tests/800","../S_MIS_CDS/tests/1000","../S_MIS_CDS/tests/1300","../S_MIS_CDS/tests/1500"};
		String outputFileName ="../S_MIS_CDS/benchmark.csv";

		DiskFileExplorer[] test=new DiskFileExplorer[4];

		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName));
		Double[] scores={0.0,0.0,0.0,0.0};
		long[] timeBegin= new long[4];
		long[] timeEnd= new long[4];


		Double 	density1 = (size[0]/Math.PI*GeneratorRandom.rayon*GeneratorRandom.rayon),
				density2 = (size[0]/Math.PI*GeneratorRandom.rayon*GeneratorRandom.rayon),
				density3 = (size[0]/Math.PI*GeneratorRandom.rayon*GeneratorRandom.rayon),
				density4 = (size[0]/Math.PI*GeneratorRandom.rayon*GeneratorRandom.rayon);

		bw.write("nbPoints,score,densite,temps\n");
		
		
		//800
		int i =0 ;
		while(i<4){
			
			//all instances for a specify directory
			test[i]=new DiskFileExplorer();
			ArrayList<ArrayList<Point>> instances =test[i].getInstance(path[i]);
			System.out.println("yo");

			timeBegin[i]=System.nanoTime();
			for(int j=0 ;j<instances.size();j++){
				scores[i]+=computeAlgoLi(instances.get(j)).size();
			}
			timeEnd[i]=System.nanoTime();
			switch (i) {
			case 0:
				bw.write(String.valueOf(800));
				bw.write(",");
				bw.write(String.valueOf(scores[0]/GeneratorRandom.instancesMax));
				System.out.println(scores[i]);
				bw.write(",");
				bw.write(String.valueOf(density1));
				bw.write(",");
				bw.write(String.valueOf(timeEnd[i]-timeBegin[i]));	
				bw.write("\n");
				break;
			case 1:
				bw.write(String.valueOf(1000));
				bw.write(",");
				bw.write(String.valueOf(scores[1]/GeneratorRandom.instancesMax));
				System.out.println(scores[i]);

				bw.write(",");
				bw.write(String.valueOf(density2));
				bw.write(",");
				bw.write(String.valueOf(timeEnd[i]-timeBegin[i]));	
				bw.write("\n");
				break;
			case 2:
				bw.write(String.valueOf(1300));
				bw.write(",");
				bw.write(String.valueOf(scores[2]/GeneratorRandom.instancesMax));
				System.out.println(scores[i]);

				bw.write(",");
				bw.write(String.valueOf(density3));
				bw.write(",");
				bw.write(String.valueOf(timeEnd[i]-timeBegin[i]));	
				bw.write("\n");
				break;
			case 3:
				bw.write(String.valueOf(1500));
				bw.write(",");
				bw.write(String.valueOf(scores[3]/GeneratorRandom.instancesMax));
				System.out.println(scores[i]);

				bw.write(",");
				bw.write(String.valueOf(density4));
				bw.write(",");
				bw.write(String.valueOf(timeEnd[i]-timeBegin[i]));	
				bw.write("\n");
				break;
				
			default:
				break;
			}
			i++;
			

		}
		bw.close();
		
		





	}


}
