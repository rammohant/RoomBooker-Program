import java.util.Calendar;

/**
 * Booking represents a reservation between two Calendar datetimes created by a
 * User.
 *
 */
public class Booking implements Comparable<Booking> {

	Calendar start;
	Calendar end;
	User booker;

	/**
	 * Creates a Booking object with the given properties.
	 * 
	 * @param start  Datetime for the meeting to begin
	 * @param end    Datetime for the meeting to end
	 * @param booker User who creates the reservation
	 */
	public Booking(Calendar start, Calendar end, User booker) {
		super();
		this.start = start;
		this.end = end;
		this.booker = booker;
	}

	public Calendar getStart() {
		return start;
	}

	public Calendar getEnd() {
		return end;
	}

	public User getBooker() {
		return booker;
	}

	/**
	 * @return Value representing difference between Bookings based on datetime
	 *         difference.
	 */
	@Override
	public int compareTo(Booking o) {
		return this.start.compareTo(o.start); // compare by start date
	}

	@Override
	public String toString() {
		return "Booking from " + start.get(Calendar.YEAR) + "-" + start.get(Calendar.MONTH) + "-"
				+ start.get(Calendar.DAY_OF_MONTH) + " " + start.get(Calendar.HOUR_OF_DAY) + ":00 to " + end.get(Calendar.HOUR_OF_DAY)
				+ ":00" + ", booked by " + booker.toString();

	}
}
