package www.spring.com.framework;

import lombok.Data;

@Data
public class Pair<F, S> {
	private F first;
	private S second;
}
