package LAB02;

public class IntMapper implements Mapper<Integer, Integer> {
	
	private int numberAdd;
	
	public IntMapper(int numberAdd) {
		this.numberAdd = numberAdd;
	}
	
	@Override
	public Integer map(Integer number) {
		return (number + this.numberAdd);
	}

}
