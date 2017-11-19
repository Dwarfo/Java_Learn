package LAB052;

import java.io.IOException;
import java.util.function.Function;

public interface IThrowable<T,Q> extends Function<T,Q> {

	@Override
	default Q apply(T t) {
		try {
			return applyThrowable(t);
		} catch (Exception e){
			throw new RuntimeException(e);
		}
	};
	
	Q applyThrowable(T t) throws IOException;
}
