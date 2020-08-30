import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

/**
 * The Room class represents a specific room which can hold and control
 * reservations.
 * 
 */
public class Room {

	// general room information
	private int roomNum;
	private int capacity;
	private int minClearance;

	// bookings collection
	private ArrayList<Booking> bookings = new ArrayList<Booking>();

	// amenities variables
	private boolean hasWhiteboard;
	private boolean hasTVProjector;
	private boolean hasSmartBoard;
	private boolean hasVideoConferencing;

	// clearance variables
	public static final int DEFAULT = 0;
	public static final int CLASSIFIED = 1;
	public static final int SECRET = 2;
	public static final int TOP_SECRET = 3;

	/**
	 * Creates a Room object with default properties.
	 * 
	 */
	public Room() {
		super();
		this.roomNum = 0;
		this.capacity = 0;
		this.hasWhiteboard = false;
		this.hasTVProjector = false;
		this.hasSmartBoard = false;
		this.hasVideoConferencing = false;
	}

	/**
	 * Creates a Room object with the provided properties.
	 * 
	 * @param roomNum              Room number
	 * @param capacity             Maximum number of individuals that can be
	 *                             accommodated
	 * @param hasWhiteboard        Whether room includes a whiteboard
	 * @param hasTVProjector       Whether room includes a television and projector
	 * @param hasSmartBoard        Whether room includes a SmartBoard
	 * @param hasVideoConferencing Whether room can accommodate video conferencing
	 * @param clearance            Minumum clearance level required to use the room
	 */
	public Room(int roomNum, int capacity, boolean hasWhiteboard, boolean hasTVProjector, boolean hasSmartBoard,
			boolean hasVideoConferencing, int clearance) {
		super();
		this.roomNum = roomNum;
		this.capacity = capacity;
		this.hasWhiteboard = hasWhiteboard;
		this.hasTVProjector = hasTVProjector;
		this.hasSmartBoard = hasSmartBoard;
		this.hasVideoConferencing = hasVideoConferencing;
		this.minClearance = clearance;
	}

	/**
	 * @return Room number
	 */
	public int getRoomNumber() {
		return roomNum;
	}

	/**
	 * Set room number to given number.
	 * 
	 */
	public void setRoomNumber(int roomID) {
		this.roomNum = roomID;
	}

	/**
	 * @return Capacity of room
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Sets room capacity to given value.
	 * 
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * @return Minimum clearance required to reserve room.
	 */
	public int getMinClearance() {
		return minClearance;
	}

	/**
	 * Set minimum clearance for room to given value.
	 * 
	 */
	public void setMinClearance(int minClearance) {
		this.minClearance = minClearance;
	}

	/**
	 * @return The list of bookings associated with a room
	 */
	public ArrayList<Booking> getBookings() {
		return bookings;
	}

	/**
	 * Attempts to reserve a room for the given time period for the given user
	 * 
	 * @param startTime Datetime for the meeting to begin
	 * @param endTime   Datetime for the meeting to end
	 * @param user      User creating the reservation
	 * @throws RoomBookingException           if there is a conflicting reservation
	 *                                        associated with a user of equal or
	 *                                        higher priority
	 * @throws InsufficientClearanceException if user does not have clearance
	 *                                        required to reserve room
	 * @throws IllegalArgumentException       if end datetime is not after start
	 *                                        datetime
	 */
	public void reserve(Calendar start, Calendar end, User user)
			throws RoomBookingException, IllegalArgumentException, InsufficientClearanceException {

		if (start.compareTo(end) >= 0) {
			throw new IllegalArgumentException("End datetime must be after start datetime.");
		}

		ArrayList<Booking> conflicts = this.getConflicts(start, end);

		// if clearance not high enough, don't allow reservation
		if (user.getClearance() < this.getMinClearance()) {
			throw new InsufficientClearanceException("Insufficient clearance for room reservation.");
		}

		// if there are conflicts
		if (conflicts.size() != 0) {

			boolean prioritySufficient = true;

			// check if priority is not high enough to override any conflicts
			for (Booking c : conflicts) {
				if (c.getBooker().getPriority() >= user.getPriority()) {
					prioritySufficient = false;
					break;
				}
			}

			// if a current booking was booked with higher priority
			if (!prioritySufficient) {
				throw new RoomBookingException("Room already booked during this time.");
			}

			System.out.println("Reservations in conflict removed. Affected users notified.");
			// TODO: test if this works
			for (Booking c : conflicts) {
				bookings.remove(c);
				// TODO: notify user
			}
		}

		bookings.add(new Booking(start, end, user));
		Collections.sort(bookings);

	}

	/**
	 * Identifies conflicting meetings within a given time period.
	 * 
	 * @param start Lower bound on time period in question
	 * @param end   Upper bound on time period in question
	 * @return Bookings in conflict
	 */
	private ArrayList<Booking> getConflicts(Calendar start, Calendar end) {

		ArrayList<Booking> conflicts = new ArrayList<Booking>();

		for (Booking b : bookings) {
			if (b.getStart().compareTo(start) >= 0 && b.getStart().compareTo(end) < 0) {
				conflicts.add(b);
			} else if (b.getEnd().compareTo(start) > 0 && b.getEnd().compareTo(end) <= 0) {
				conflicts.add(b);
			}
		}
		return conflicts;
	}

	/**
	 * Attempts to remove a reservation at a given time for a given user
	 * 
	 * @param start Datetime for start of meeting to remove
	 * @param user  User attempting to remove reservation
	 * @throws RoomBookingException     if reservation to remove is not associated
	 *                                  with given user
	 * @throws IllegalArgumentException if reservation to remove does not exist
	 */
	public void unreserve(Calendar start, User user) throws RoomBookingException, IllegalArgumentException {
		for (Booking b : bookings) {
			if (b.getStart().equals(start)) {
				if (!b.getBooker().equals(user)) {
					throw new RoomBookingException(
							"Room cannot be unreserved by other user. Users with higher priority should reserve a meeting time to remove conflicting reservations.");
				}
				bookings.remove(b);
			}
		}
		throw new IllegalArgumentException("Room booking does not exist to unreserve.");
	}

	/**
	 * Checks whether space exists to accommodate a meeting of given length
	 * 
	 * @param meetingLengthHrs length of proposed meeting in hours
	 * @param start            Datetime for the proposed meeting to begin
	 * @param end              Datetime for the proposed meeting to end
	 * @return true if space exists to accommodate meeting
	 */
	public boolean spaceAvailable(int meetingLengthHrs, Calendar start, Calendar end) {
		int maxDiff;
		if (bookings.size() == 0) {
			maxDiff = start.compareTo(end);
		} else {
			maxDiff = bookings.get(0).getStart().get(Calendar.HOUR_OF_DAY) - start.get(Calendar.HOUR_OF_DAY);
			for (int i = 0; i < bookings.size() - 1; i++) {
				if (bookings.get(i + 1).getStart().get(Calendar.HOUR_OF_DAY)
						- bookings.get(i).getEnd().get(Calendar.HOUR_OF_DAY) > maxDiff) {
					maxDiff = bookings.get(i + 1).getStart().get(Calendar.HOUR_OF_DAY)
							- bookings.get(i).getEnd().get(Calendar.HOUR_OF_DAY);
				}
			}
		}

		return maxDiff >= meetingLengthHrs;
	}

}
