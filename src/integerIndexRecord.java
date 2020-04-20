
public class integerIndexRecord extends indexRecord implements Comparable<integerIndexRecord> {
	int id ;
	
	public integerIndexRecord (int ID , int offset  ) {
		super(offset);
		this.id = ID ;
	}
	
	public int getID() {
		return this.id ;
	}
	
	@Override
	public String toString() {
		return String.valueOf(id)  + " " + String.valueOf(Reference) ;
	}

	@Override
	public int compareTo(integerIndexRecord o) {
		return this.id - o.getID() ;
	}
}
