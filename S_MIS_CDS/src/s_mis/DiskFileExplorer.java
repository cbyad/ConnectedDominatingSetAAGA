package s_mis;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DiskFileExplorer {
	 
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
    
    private ArrayList<ArrayList<Point>> getInstance(String dir) {
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
                		instances.add(GeneratorRandom.readFromFile(files[i].getName()));
                         this.filecount++;
                	}
                   
                }
                if (files[i].isDirectory() == true && this.recursivePath == true) {
                    this.listDirectory(files[i].getAbsolutePath());
                }
            }
        }
        System.out.println(instances.size());
        return instances ;
    }  
    
    
    public static void main(String[] args) throws IOException, InterruptedException {
		
    	DiskFileExplorer t=new DiskFileExplorer("../S_MIS_CDS/tests/800",false);
    	t.list();
    	System.out.println(t.filecount);
    	    	
    	
	}
    
    
}
