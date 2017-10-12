package LAB02;

public class StringMapper implements Mapper<String, Integer> {

	private int lengthAdd;
	
	public StringMapper(int lengthAdd) {
		this.lengthAdd = lengthAdd;
	}
	
	@Override
	public Integer map(String str) {
		return (str.length() + this.lengthAdd);
	}
}
