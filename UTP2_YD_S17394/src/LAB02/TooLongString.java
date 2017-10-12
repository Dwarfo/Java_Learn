package LAB02;

public class TooLongString implements Selector<String> {

	private int stringLength;
	
	public TooLongString(int stringLength) {
		this.stringLength = stringLength;
	}
	
	@Override
	public boolean select(String str) {
		if (str.length() > this.stringLength)
			return true;
		else
			return false;
	}
}
