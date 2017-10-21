package s_mis;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

import steiner.Steiner;
import steiner.Tree2D;

public class BenchMark {
	private String initialpath = "";
	private Boolean recursivePath = false;
	public int filecount = 0;
	public int dircount = 0;

	/**
	 * Constructeur
	 * @param path chemin du répertoire
	 * @param subFolder analyse des sous dossiers
	 */
	public BenchMark(String path, Boolean subFolder) {
		super();
		this.initialpath = path;
		this.recursivePath = subFolder;
	}

	public BenchMark(){}
			
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
					this.getInstance(files[i].getAbsolutePath());
				}
			}
		}
		return instances ;
	}  

	public static ArrayList<Point> computeAlgoLi(ArrayList<Point> pts){
		ArrayList<PointWithColor> blackPoint = new ArrayList<>();
		blackPoint=Algorithms.computeMisRef16(pts,GeneratorRandom.edgeThreshold);

		ArrayList<PointWithColor> greyPoint = Algorithms.tagPointGrey(pts, Algorithms.convertion(blackPoint));
		ArrayList<PointWithColor> blueTag = new ArrayList<>();
		blueTag = Algorithms.algoLi(blackPoint, greyPoint, pts, GeneratorRandom.edgeThreshold);		

		for(PointWithColor p : blueTag)
			blackPoint.add(p);
		return Algorithms.convertion(blackPoint);
	}


	public static ArrayList<Point> computeSteiner(ArrayList<Point> pts){
		ArrayList<PointWithColor> MIS=new ArrayList<>();
		MIS = Algorithms.computeMisRef16(pts,GeneratorRandom.edgeThreshold);
		Steiner util = new Steiner();

		Tree2D arbre = util.calculSteiner(pts, GeneratorRandom.edgeThreshold, Algorithms.convertion(MIS));
		ArrayList<Point> mcds = Algorithms.unfold(arbre, new ArrayList<>());
		return mcds;
	}


	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("process....");

		int[] size={800,1000,1300,1500};
		String[] path = {"../S_MIS_CDS/tests/800","../S_MIS_CDS/tests/1000","../S_MIS_CDS/tests/1300","../S_MIS_CDS/tests/1500"};
		String outputFileName ="../S_MIS_CDS/benchmark.csv";

		BenchMark[] test=new BenchMark[4];
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName));
		int[] scores_li={0,0,0,0};
		int[] scores_steiner={0,0,0,0};

		long[] timeBegin_li= new long[4];
		long[] timeEnd_li= new long[4];

		long[] timeBegin_steiner= new long[4];
		long[] timeEnd_steiner= new long[4];


		Double 	density1 = (size[0]/(Math.PI*GeneratorRandom.rayon*GeneratorRandom.rayon)),
				density2 = (size[1]/(Math.PI*GeneratorRandom.rayon*GeneratorRandom.rayon)),
				density3 = (size[2]/(Math.PI*GeneratorRandom.rayon*GeneratorRandom.rayon)),
				density4 = (size[3]/(Math.PI*GeneratorRandom.rayon*GeneratorRandom.rayon));

		bw.write("nb Points,mcds Li,mcsd Steiner,densite,temps Li,temps Steiner\n");

		int i =0 ;
		while(i<4){
			//all instances for a specify directory
			test[i]=new BenchMark();
			ArrayList<ArrayList<Point>> instances =test[i].getInstance(path[i]);

			timeBegin_li[i]=System.nanoTime();
			for(int j=0 ;j<instances.size();j++){
				scores_li[i]+=computeAlgoLi(instances.get(j)).size();
			}
			timeEnd_li[i]=System.nanoTime();
			System.out.println("Li computing "+i+" done");

			timeBegin_steiner[i]=System.nanoTime();
			for(int j=0 ;j<instances.size();j++){
				scores_steiner[i]+=computeSteiner(instances.get(j)).size();
			}
			timeEnd_steiner[i]=System.nanoTime();
			System.out.println("Steiner computing "+i+" done");


			switch (i) {
			case 0:
				bw.write(String.valueOf(800));
				bw.write(",");
				bw.write(String.valueOf(scores_li[0]/GeneratorRandom.instancesMax));
				bw.write(",");
				bw.write(String.valueOf(scores_steiner[0]/GeneratorRandom.instancesMax));
				bw.write(",");
				bw.write(String.valueOf(density1));
				bw.write(",");
				bw.write(String.valueOf((timeEnd_li[i]-timeBegin_li[i])/1000000000.0));	//in seconds
				bw.write(",");
				bw.write(String.valueOf((timeEnd_steiner[i]-timeBegin_steiner[i])/1000000000.0));	
				bw.write("\n");
				break;
			case 1:
				bw.write(String.valueOf(1000));
				bw.write(",");
				bw.write(String.valueOf(scores_li[1]/GeneratorRandom.instancesMax));
				bw.write(",");
				bw.write(String.valueOf(scores_steiner[1]/GeneratorRandom.instancesMax));
				bw.write(",");
				bw.write(String.valueOf(density2));
				bw.write(",");
				bw.write(String.valueOf((timeEnd_li[i]-timeBegin_li[i])/1000000000.0));	
				bw.write(",");
				bw.write(String.valueOf((timeEnd_steiner[i]-timeBegin_steiner[i])/1000000000.0));	
				bw.write("\n");		
				break;
			case 2:
				bw.write(String.valueOf(1300));
				bw.write(",");
				bw.write(String.valueOf(scores_li[2]/GeneratorRandom.instancesMax));
				bw.write(",");
				bw.write(String.valueOf(scores_steiner[2]/GeneratorRandom.instancesMax));
				bw.write(",");
				bw.write(String.valueOf(density3));
				bw.write(",");
				bw.write(String.valueOf((timeEnd_li[i]-timeBegin_li[i])/1000000000.0));	
				bw.write(",");
				bw.write(String.valueOf((timeEnd_steiner[i]-timeBegin_steiner[i])/1000000000.0));	
				bw.write("\n");		
				break;
			case 3:
				bw.write(String.valueOf(1500));
				bw.write(",");
				bw.write(String.valueOf(scores_li[3]/GeneratorRandom.instancesMax));
				bw.write(",");
				bw.write(String.valueOf(scores_steiner[3]/GeneratorRandom.instancesMax));
				bw.write(",");
				bw.write(String.valueOf(density4));
				bw.write(",");
				bw.write(String.valueOf((timeEnd_li[i]-timeBegin_li[i])/1000000000.0));	
				bw.write(",");
				bw.write(String.valueOf((timeEnd_steiner[i]-timeBegin_steiner[i])/1000000000.0));	
				bw.write("\n");
				break;

			default:
				break;
			}
			i++;
		}
		bw.close();
		System.out.println("fini!");
		java.awt.Toolkit.getDefaultToolkit().beep();
	}
}
