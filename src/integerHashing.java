
public class integerHashing implements IHashable<Integer>{

	@Override
	public long hash(Integer key, int ref , int TableSize) {
		long pnum = (long) 2654435761.0 ;
		long hash = key*pnum & (1000 - 1);
		return hash;
	}

}
