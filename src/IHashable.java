import java.io.IOException;

public interface IHashable<Object> {
	public long hash(Object key, int reference , int TableSize) throws IOException ;
	
}
