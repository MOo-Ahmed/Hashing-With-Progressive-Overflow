
public class stringIndexRecord extends indexRecord  implements Comparable<stringIndexRecord>  {
	String key ;
	
	public stringIndexRecord(String key , int offset) {
		super(offset);
		this.key = key ;
	}

	@Override
	public String toString() {
		return key  + " " + String.valueOf(Reference) ;
	}
	
	public String getKey() {
		return key ;
	}

	@Override
	public int compareTo(stringIndexRecord o) {
		return this.getKey().compareTo(o.getKey()) ;
	}

}
