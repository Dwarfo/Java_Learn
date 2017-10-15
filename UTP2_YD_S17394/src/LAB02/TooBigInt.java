package LAB02;

public class TooBigInt implements Selector<Integer> {

	private int selectNumber;
	
	public TooBigInt(int selectNumber){
		this.selectNumber = selectNumber;
	}
	
	@Override
	public boolean select(Integer number){
		if(number < this.selectNumber)
			return true;
		else
			return false;
	}
}
