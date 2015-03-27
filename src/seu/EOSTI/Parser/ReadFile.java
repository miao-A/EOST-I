package seu.EOSTI.Parser;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
* @author   Yam

*@version   1.1

*@see     文件读取类


*/
public class ReadFile {
	
    private List<String> fileList =new ArrayList<String>();
    private static List<String> javaFileList =new ArrayList<String>();
    private static List<String> jarFileList =new ArrayList<String>();
    
    private String filepath;
    
    /**
    * @author   Yam

    *@version  1.1

    *@see     文件读取类

    *@param    项目文件名

    *@return   空

    */    
    public ReadFile(String filepath){
    	this.filepath = filepath;
    }
    /**
     * 读取某个文件夹下的所有文件
     */
    public  boolean readAllFile(String filepath) throws FileNotFoundException, IOException {
            try {

                    File file = new File(filepath);
                    if (!file.isDirectory()) {
                            System.out.println("文件");
                            System.out.println("path=" + file.getPath());
                            System.out.println("absolutepath=" + file.getAbsolutePath());
                            System.out.println("name=" + file.getName());

                    } else if (file.isDirectory()) {
                            System.out.println("文件夹");
                            String[] filelist = file.list();
                            for (int i = 0; i < filelist.length; i++) {
                                    File readfile = new File(filepath + "\\" + filelist[i]);
                                    if (!readfile.isDirectory()) {
                                            System.out.println("path=" + readfile.getPath());
                                            System.out.println("absolutepath="
                                                            + readfile.getAbsolutePath());
                                            System.out.println("name=" + readfile.getName());

                                    } else if (readfile.isDirectory()) {
                                            readAllFile(filepath + "\\" + filelist[i]);
                                    }
                            }

                    }

            } catch (FileNotFoundException e) {
                    System.out.println("readfile()   Exception:" + e.getMessage());
            }
            return true;
    }

    public List<String> readProjectFile() throws FileNotFoundException, IOException {
        
    	File file= new File(filepath);
		if (!file.isDirectory()) {
		        System.out.println("Not directory!");
		} else if (file.isDirectory()) {
		        System.out.println("文件夹");

		        int index = 0;
		        for (int i = 0; i < file.list().length; i++) {
		                File readfile = new File(filepath + "\\" + file.list()[i]);
		                if (readfile.isDirectory()) {		                	
		                	fileList.add(filepath + "\\" + file.list()[i]);		                	
		                }
		        }

		}
        return fileList;
    }

    public List<String> readJavaFiles() {                                 
		File file = new File(filepath);
		javaFileList.clear();
		if (file.isDirectory() == true) {
		    	readFiles(file, ".java",javaFileList);
		}
    	return javaFileList;		
	}
    
    public List<String> readJarFiles() {
		File file = new File(filepath);
		jarFileList.clear();
		if (file.isDirectory() == true) {
		    	readFiles(file, ".jar",jarFileList);
		}
    	return jarFileList;		
	}
    
    private void readFiles(File file, String suffix, List<String> fList){
    	if (file != null) {
			if (file.isDirectory()) {
				File f[] = file.listFiles();
				if (f != null) {
					for (int i = 0; i < f.length; i++) {
						readFiles(f[i], suffix,fList);
					}
				}
			} else if(file.getName().endsWith(suffix)){
				fList.add(file.toString());
			}
		}
    }
    
/*    public static void main(String[] args) {
    	ReadFile readFile = new ReadFile("D:/ProjectEOfHW/heritrix-3.0.0");
    	System.out.println("begin:");
    	for (String string : readFile.readJavaFiles()) {
			System.out.println(string);
		}

    	System.out.println("ok");
    	readFile = new ReadFile("D:/ProjectEOfHW/heritrix-3.1.0");
    	System.out.println("begin:");
    	for (String string : readFile.readJavaFiles()) {
			System.out.println(string);
		}  	 	

    	System.out.println("ok");
	}*/
}