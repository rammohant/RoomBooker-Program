import java.util.Calendar;
import java.util.Scanner;

public class RoomBooker {
	public static void main(String args[]) {
		RoomBooker booker = new RoomBooker();
		User curUser = booker.login();
		Room r1 = new Room(1, 1, false, false, false, false, Room.DEFAULT);
		Room r2 = new Room(2, 234, true, true, true, true, Room.TOP_SECRET);
		Room r3 = new Room(3, 30, true, false, true, true, Room.CLASSIFIED);
		// Room r4 = new Room(4, 45, true, true, false, true, 2);
		// Room r5 = new Room(5, 100, true, true, true, false, 3);
		// Room r6 = new Room(6, 56, false, false, false, false, 2);
		// Room r7 = new Room(7, 100, false, true, true, false, 2);
		Room[] rooms = { r1, r2, r3 };
		User u = new User("Greg", User.DEFAULT, User.PRIORITY_THREE);
		User u2 = new User("Johnson", User.DEFAULT, User.PRIORITY_THREE);
		booker.roomBook(1, 1, 1, 2019, 1, 11, u2, rooms);
		booker.roomBook(1, 1, 1, 2019, 13, 23, u2, rooms);
		booker.roomBook(3, 1, 1, 2019, 1, 11, u2, rooms);
		booker.roomBook(3, 1, 1, 2019, 13, 23, u2, rooms);
		booker.roomBook(2, 1, 1, 2019, 1, 11, u2, rooms);
		booker.roomBook(2, 1, 1, 2019, 16, 23, u2, rooms);

		System.out.println("Welcome to the room booker program.");
		boolean booking = true;
		int selection;
		Scanner in = new Scanner(System.in);
		while (booking == true) {
			System.out.println(
					"Would you like to book a room (1) or would you like to search for available rooms (2)? If you would like to check a room's bookings, press 3.");

			selection = Integer.parseInt(in.next());
			while (selection != 1 && selection != 2 && selection != 3) {
				System.out.println("Please select a valid response.");
				selection = Integer.parseInt(in.nextLine());
			}
			if (selection == 1) {
				int room, day, month, year, startTime, endTime;
				System.out.println("Select a room to book.");
				room = in.nextInt();
				System.out.println("Select a day to book.");
				day = in.nextInt();
				System.out.println("Select a month to book.");
				month = in.nextInt();
				System.out.println("Select a year to book.");
				year = in.nextInt();
				System.out.println("Select the start time of your booking.");
				startTime = in.nextInt();
				System.out.println("Select the end time of your booking.");
				endTime = in.nextInt();
				booker.roomBook(room, day, month, year, startTime, endTime, u, rooms);
				System.out.println("Would you like to book another room? Press 1 to book again, press 2 to exit.");
				int selection2 = in.nextInt();
				while (selection2 != 1 && selection2 != 2) {
					System.out.println("Please select a valid response.");
					selection2 = in.nextInt();
				}
				if (selection2 == 2) {
					booking = false;
					System.out.println("Have a nice day!");
				}
			} else if (selection == 2) {
				System.out.println("Type in the earliest date you would like to book in the format dd/mm/yyyy");
				String earliest = in.next();
//				System.out.println("this is earliest " + earliest);
				Calendar earliestDate = Calendar.getInstance();
				int day = Integer.parseInt(earliest.substring(0, earliest.indexOf("/")));
				earliest = earliest.substring(earliest.indexOf("/") + 1, earliest.length());
				int month = Integer.parseInt(earliest.substring(0, earliest.indexOf("/")));
				earliest = earliest.substring(earliest.indexOf("/") + 1, earliest.length());
				int year = Integer.parseInt(earliest);
				earliestDate.set(year, month, day, 0, 0);
//				System.out.println(earliestDate.MONTH);
//				System.out.println(earliestDate.DATE);
				System.out.println("Type in the latest date you would like to book in the format dd/mm/yyyy");
				String latest = in.next();
				Calendar latestDate = Calendar.getInstance();
				int day2 = Integer.parseInt(latest.substring(0, latest.indexOf("/")));
				latest = latest.substring(latest.indexOf("/") + 1, latest.length());
				int month2 = Integer.parseInt(latest.substring(0, latest.indexOf("/")));
				latest = latest.substring(latest.indexOf("/") + 1, latest.length());
				int year2 = Integer.parseInt(latest);
				latestDate.set(year2, month2, day2, 23, 0);
//				System.out.println(earliestDate.MONTH);
//				System.out.println(earliestDate.DATE);
				System.out.println("How long would you like to book a room for in hours?");
				int reservationLength = in.nextInt();

				// Room test = new Room();
				System.out.println("Current Schedule of rooms in date range");
				for (Room x : rooms) {
					if (x.spaceAvailable(reservationLength, earliestDate, latestDate)) {
						if (x.getMinClearance() <= curUser.getClearance()) {
							System.out.println(
									"room number: " + x.getRoomNumber() + " , current bookings: " + x.getBookings());
						}
					}
				}
			} else {
				System.out.println("What room would you like to check the bookings for?");
				int roomCheck = Integer.parseInt(in.next());
				System.out.println("The following is the bookings for room number " + roomCheck + ".");
				boolean bool = false;
				for (int i = 0; i < rooms.length; i++) {
					if (rooms[i].getRoomNumber() == roomCheck) {
						System.out.println(rooms[i].getBookings());
						bool = true;
					}
				}
				if (bool == false) {
					System.out.println("This room does not exist.");
				}
			}
			/*
			 * booker.roomBook(1, 1, 1, 2019, 1, 11, u2, rooms); booker.roomBook(1, 1, 1,
			 * 2019, 13, 23, u2, rooms); booker.roomBook(3, 1, 1, 2019, 1, 11, u2, rooms);
			 * booker.roomBook(3, 1, 1, 2019, 13, 23, u2, rooms); booker.roomBook(2, 1, 1,
			 * 2019, 1, 11, u2, rooms); booker.roomBook(2, 1, 1, 2019, 16, 23, u2, rooms);
			 * /* System.out.
			 * println("Select a room. date, and time in the format room:day:month:year:startTime:endTime"
			 * ); Scanner input = new Scanner(System.in); String parse = input.nextLine();
			 * String[] nums = parse.split(":"); int room, day, month, year, startTime,
			 * endTime; room = Integer.parseInt(nums[0]); day = Integer.parseInt(nums[1]);
			 * month = Integer.parseInt(nums[2]); year = Integer.parseInt(nums[3]);
			 * startTime = Integer.parseInt(nums[4]); endTime = Integer.parseInt(nums[5]);
			 * booker.roomBook(room, day, month, year, startTime, endTime, u, rooms);
			 * 
			 * Calendar d = Calendar.getInstance(); Calendar f = Calendar.getInstance();
			 * d.set(2019, 1, 1, 0, 0); f.set(2019, 1, 1, 23, 0); for (int i = 0; i <
			 * rooms.length; i++) { if (rooms[i].spaceAvailable(4, d, f)) {
			 * System.out.println("PREPARE FOR THE BEST AVAIL ROOM");
			 * System.out.println(rooms[i].getRoomNumber()); System.out.println("PREPARE");
			 * } }
			 */
		}
	}

	public void roomBook(int r, int d, int m, int y, int start, int end, User u, Room[] rArr) {
		Calendar startBook = Calendar.getInstance();
		startBook.set(y, m, d, start, 0);
		Calendar endBook = Calendar.getInstance();
		endBook.set(y, m, d, end, 0);
		for (int i = 0; i < rArr.length; i++) {
			if (rArr[i].getRoomNumber() == r) {
				try {
					rArr[i].reserve(startBook, endBook, u);
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				} catch (RoomBookingException e) {
					System.out.println(e.getMessage());
				} catch (InsufficientClearanceException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	public User login() {
		String name;
		int prior = 0, clear = 7;
		Scanner log = new Scanner(System.in);
		System.out.println("Enter your name.");
		name = log.nextLine();
		while (prior != 1 && prior != 2 && prior != 3 && prior != 1000) {
			System.out.println("Enter your priority level (1,2, or 3).");
			prior = log.nextInt();
		}
		while (clear != 0 && clear != 1 && clear != 2 && clear != 3) {
			System.out.println("Enter your clearance level, Default(0), Classified(1), Secret(2), or TopSecret(3)");
			clear = log.nextInt();
		}
		System.out.println("Welcome User " + name + ", priority level " + prior + ", clearance level " + clear);
		return new User(name, clear, prior);
	}
}
