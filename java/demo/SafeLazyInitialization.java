package source.demo;

public class SafeLazyInitialization {
	
	private static Instance instance;
	
	public synchronized static Instance getInstance() {
		if (instance == null) {
			return new Instance();
		}
		return instance;
	}

}
