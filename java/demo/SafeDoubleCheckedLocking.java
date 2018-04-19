package source.demo;

public class SafeDoubleCheckedLocking {
	
	private volatile static Instance instance;
	
	public static Instance getInstance() {
		if (instance == null) {
			synchronized (SafeDoubleCheckedLocking.class) {
				if (instance == null) {
					return new Instance();
				}
			}
		}
		return instance;
	}
}
