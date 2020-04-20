import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class TestClass {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		int fileSize = Integer.parseInt(sc.nextLine());
		// fileSize represents number of records not bytes. For example, if fileSize = 64 , then the file has 64 records
		
		RandomAccessFile indexFile  = new RandomAccessFile("stringIndex.bin", "rw");
		indexFile.seek(0);		
		
		prepareFile("hashedIndex.bin", fileSize);
		beginStringHashing(indexFile, "hashedIndex.bin", fileSize);
		
		RandomAccessFile hashedFile  = new RandomAccessFile("hashedIndex.bin", "rw");
		hashedFile.seek(0);
		
		displayFileContents(hashedFile, fileSize);
		
		indexFile.close();

	}
	
	public static void prepareFile(String filename , int fileSize) throws IOException {
		File f= new File(filename);        
		f.delete()  ;
		RandomAccessFile file  = new RandomAccessFile(filename, "rw");
		file.seek(0);
		for(int i = 0 ; i < fileSize ; i++) {
			file.writeBytes("....");
			file.writeInt(70);
		}
		file.close();
	}	
	
	public static void beginStringHashing(RandomAccessFile indexFile ,String hashedFile, int fileSize) throws IOException {
		StringHashing stringH = new StringHashing();
		stringH.setFileName(hashedFile);
		for(int i = 0 ; i < fileSize ; i++) {
			stringIndexRecord record = readRecord(indexFile);
			long hashValue = stringH.hash(record.getKey() , record.getReference() , fileSize);
		}
		
	}	
	
	public static void displayFileContents(RandomAccessFile file , int fileSize) throws IOException {
		file.seek(0);

		for(int i = 0 ; i < fileSize ; i++) {
			if(file.length() == file.getFilePointer())	return ;
			System.out.println((i) + ". " + readRecord(file));
		}
	}
	
	public static stringIndexRecord readRecord(RandomAccessFile file) throws IOException {
		StringBuilder sb = new StringBuilder();
        for (int j = 0; j < 4; j++)
        {
            byte c = file.readByte();
            if(c == 0 && j == 0)	return null ;
            char ch = (char) c ;
            if (c != 0)
                sb.append(ch);
        }
        int off = file.readInt() ;
		stringIndexRecord record = new stringIndexRecord(sb.toString(), off);
		return record ;
	}
}
