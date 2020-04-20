import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class StringHashing implements IHashable<String>{
	RandomAccessFile file = null ; 
	
	
	@Override
	public long hash(String key, int ref , int TableSize) throws IOException {
		// djb2 hash function
		// This function is good, produces small hash values , 
		//but causes a lot of collisions
	    long hash = 5381;
	    char c;
	    for(int i = 0 ; i < key.length() ; i++) {
	    	c = key.charAt(i);
	        hash = ((hash << 5) + hash) + c; 
	    }
	    hash = hash%TableSize;
	    
	    file.seek(hash*8);
	    if(TestClass.readRecord(file).getKey().equals ("....") == false ) {
	    	getAlternativeHashValue(key, ref, TableSize, hash);
	    }
	    else {
	    	writeToFile(key, ref, hash);
	    }
	    return hash ;
	}
	
	public void setFileName(String filename) throws FileNotFoundException 
	{
		file = new RandomAccessFile(filename, "rw");
	}
	
	private long getAlternativeHashValue(String key, int ref , int TableSize , long hashValue) throws IOException {
		boolean isEmptyPlaceFound = false ;
		long offset = -1;
		if(hashValue != TableSize -1)	{
			
			file.seek(8 + 8*hashValue);
			while(!isEmptyPlaceFound && file.getFilePointer() != hashValue*8) {
				
				stringIndexRecord record = TestClass.readRecord(file);
				if(record.getKey().equals ("....")) {
					offset = (file.getFilePointer()- 8) / 8 ;
					isEmptyPlaceFound = true ;
					file.seek(offset*8);
					writeToFile(key, ref, offset);
				}
				else if(file.getFilePointer() == file.length()) {
					file.seek(0);
				}
			}
		}
		else {
			file.seek(0);
			while(!isEmptyPlaceFound && file.getFilePointer() != hashValue*8) {
				stringIndexRecord record = TestClass.readRecord(file);
				if(record.getKey().equals("....")) {
					offset = (file.getFilePointer()- 8) / 8 ;
					isEmptyPlaceFound = true ;
					file.seek(offset*8);
					writeToFile(key, ref, offset);
				}
			}
		}
		return offset/8;
	}
	
	private void writeToFile(String key, int reference, long hashValue) throws IOException {
		
		file.seek(hashValue*8);
		file.writeBytes(key);
		file.writeInt(reference);
	}
	
}
