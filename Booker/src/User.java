/**
 * User represents an individual who can reserve rooms.
 *
 */
public class User {
	// Defines collection of Clearance user constants

	

	private String name;

	// priority variables
	private int priority;
	public static final int PRIORITY_ONE = 3;
	public static final int PRIORITY_TWO = 2;
	public static final int PRIORITY_THREE = 1;

	/**
	 * Clearance represents a government security clearance level.
	 *
	 */
	private int clearance;
	public static final int DEFAULT = 0;
	public static final int CLASSIFIED = 1;
	public static final int SECRET = 2;
	public static final int TOP_SECRET = 3;

	/**
	 * Creates a User with the given properties.
	 * 
	 * @param name      Name of user
	 * @param clearance Clearance level of user
	 * @param priority  Priority level of user depending on their position
	 */
	public User(String name, int clearance, int priority) {
		super();
		this.name = name;
		this.clearance = clearance;
		this.priority = priority;
	}

	/**
	 * Creates a User with default properties.
	 */
	public User() {
		super();
		this.name = "";
		this.clearance = 0;
		this.priority = PRIORITY_THREE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getClearance() {
		return clearance;
	}

	public void setClearance(int clearance) {
		this.clearance = clearance;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return name;
	}

}
