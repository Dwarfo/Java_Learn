package LAB053;

import java.awt.event.MouseListener;
import java.util.function.Consumer;

import com.sun.glass.events.MouseEvent;

public interface bb{

	void accept(Consumer e);
	public static void onMouseClick(MouseEvent e) {
		((bb) e).accept(null);
	}
}
