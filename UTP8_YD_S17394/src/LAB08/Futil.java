package LAB08;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;

import static java.nio.file.FileVisitResult.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class Futil extends SimpleFileVisitor<Path>{

	
	private static String finalle = "";
//	public static void processDir(String dirName, String resultFileName) {
//		
//		Futil fut = new Futil();
//		Files.walkFileTree(dirName, fut);
//		
//	}
//	
//	@Override
//    public FileVisitResult visitFile(Path file,
//                                   BasicFileAttributes attr) {
//        if (attr.isSymbolicLink()) {
//            System.out.format("Symbolic link: %s ", file);
//        } else if (attr.isRegularFile()) {
//            System.out.format("Regular file: %s ", file);
//        } else {
//            System.out.format("Other: %s ", file);
//        }
//        System.out.println("(" + attr.size() + "bytes)");
//        return CONTINUE;
//    }
//
//    @Override
//    public FileVisitResult postVisitDirectory(Path dir,
//                                          IOException exc) {
//        System.out.format("Directory: %s%n", dir);
//        return CONTINUE;
//    }
//
//   
//    @Override
//    public FileVisitResult visitFileFailed(Path file,
//                                       IOException exc) {
//        System.err.println(exc);
//        return CONTINUE;
//    }
//    
    public static void processDir(String in, String out)  {
        Path startPath = Paths.get(in);
        try {
			Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() { 
			    @Override
			    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
			        
			    {
			    	try {
			    		String line;
			    		System.out.println(file.toString());
			    	    FileInputStream input = new FileInputStream(file.toString());
			    	    InputStreamReader reader = new InputStreamReader(input, "Cp1250");
			    	    BufferedReader buffr = new BufferedReader(reader);
			    	    FileOutputStream output = new FileOutputStream(out);
			    	    OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
			    	    BufferedWriter buffw = new BufferedWriter(writer);
			    	    
			    	    //System.out.println(reader.getEncoding());
			    	    //int read = buffr.read();
			    	    while ((line = buffr.readLine()) != null) {
			    	        // buffer.append(line);
			    	    	finalle += line;
			    	    	
			    	    }
			    	    FileWriter fw = new FileWriter(out);
			    	    fw.write(finalle);
			    	    System.out.println(finalle);
			    	    fw.close();
			    	    //finalle = "";
			    	} catch (Exception e) {
			    		e.printStackTrace();
			    	}
			    	 return FileVisitResult.CONTINUE;
			    }
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // <- you were missing a terminating ");"
    
    
    
    }


}
