package seu.EOSTI;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {
	
    private static List<String> filelist =new ArrayList<String>();
    private String filepath;
    
    public ReadFile(String filepath){
    	this.filepath = filepath;
    }
    /**
     * 读取某个文件夹下的所有文件
     */
    public static boolean readAllFile(String filepath) throws FileNotFoundException, IOException {
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
                                    File readfile = new File(filepath + "/" + filelist[i]);
                                    if (!readfile.isDirectory()) {
                                            System.out.println("path=" + readfile.getPath());
                                            System.out.println("absolutepath="
                                                            + readfile.getAbsolutePath());
                                            System.out.println("name=" + readfile.getName());

                                    } else if (readfile.isDirectory()) {
                                            readAllFile(filepath + "/" + filelist[i]);
                                    }
                            }

                    }

            } catch (FileNotFoundException e) {
                    System.out.println("readfile()   Exception:" + e.getMessage());
            }
            return true;
    }

    public List<String> readProjectFile() throws FileNotFoundException, IOException {
        
    	File file = new File(filepath);
		if (!file.isDirectory()) {
		        System.out.println("Not directory!");
		} else if (file.isDirectory()) {
		        System.out.println("文件夹");

		        int index = 0;
		        for (int i = 0; i < file.list().length; i++) {
		                File readfile = new File(filepath + "/" + file.list()[i]);
		                if (readfile.isDirectory()) {		                	
		                	filelist.add(filepath + "/" + file.list()[i]);		                	
		                }
		        }

		}
        return filelist;
    }
    
    public static void main(String[] args) {
        try {
        	ReadFile readFile = new ReadFile("C:/Users/Administrator/Desktop/软件演化项目");
        	readFile.readProjectFile();
        	for (String string : filelist) {
				System.out.println(string);
			}

        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.println("ok");
        }
    


}

/*public class FilePath {
	private static void readFiles(File file, String suffix, ArrayList<String> filesList) {
		if (file != null) {
			if (file.isDirectory()) {
				File f[] = file.listFiles();
				if (f != null) {
					for (int i = 0; i < f.length; i++) {
						readFiles(f[i], suffix, filesList);
					}
				}
			} else if(file.getName().endsWith(suffix)){
				filesList.add(file.toString());
			}
		}
	}
	
	public static String[] getAllFiles(String projectPath, String suffix) {
		ArrayList<String> filesList = new ArrayList<String>();                                  
		File file = new File(projectPath);
		if (file.isDirectory() == true) {
			readFiles(file, suffix, filesList);
		}
		
		int size=filesList.size();  
        String[] array = (String[])filesList.toArray(new String[size]);  
		return array;
	}
}

 * */
