package application.models.id;

public class ContainerId {
	
	private static Long id = 1L;
	
	public static Long newContainerId() {
		return id++;
	}
	
}
