import java.util.Arrays;
import java.util.Scanner;

public class CinemaRoomManager {
	
	public static Scanner sc = new Scanner(System.in);


	public static void main(String[] args) {
		
		int rows;
		int seats;
		String option = "1";
		String[] room;
		int row = 0;
		int seat = 0;
		int sold = 0;
		int totalPrice = 0;

		System.out.println("Enter the number of rows:");
		rows = sc.nextInt();
		System.out.println("Enter the number of seats in each row:");
		seats = sc.nextInt();
		sc.nextLine();
		room = new String[rows * seats];
		Arrays.fill(room, "S");
		try {
			while (!option.equals("0")) {
				System.out.println();
				System.out.println("1. Show the seats");
				System.out.println("2. Buy a ticket");
				System.out.println("3. Statistics");
				System.out.println("0. Exit");
				System.out.println();
				option = sc.next();
				int[] values = callMenu(room, rows, seats, row, seat, option, sold, totalPrice);
				sold += values[0];
				totalPrice += values[1];;
			}
		} finally {
			sc.close();
		} 

	}

	public static void printRoom(String[] s, int rows, int seats) {
		System.out.println("Cinema:");
		String seatPrint = " ";
		for (int i = 1; i <= seats; i++) {
			seatPrint += " " + (i);
		}
		System.out.println(seatPrint);
		
		int auxPosition = 0;
		int line = seats;
		String rowPrint = "";
		for (int i = 1; i <= rows; i++) {
			rowPrint += i;
			while (line != 0) {
				rowPrint += " " + s[auxPosition];
				auxPosition++;
				line--;
			}
			System.out.println(rowPrint);
			line = seats;
			rowPrint = "";
		}
	}
	
	public static int[] buyTicket(String[] room, int rows, int seats, int row, int seat, String option) {
		System.out.println("Enter a row number:");
		row = sc.nextInt();
		System.out.println("Enter a seat number in that row:");
		seat = sc.nextInt();
		int position = (seats * row)- (seats-seat) - 1;
		while (row > rows || seat > seats) {
			System.out.println("Wrong input!");
			System.out.println();
			System.out.println("Enter a row number:");
			row = sc.nextInt();
			System.out.println("Enter a seat number in that row:");
			seat = sc.nextInt();
			position = (seats * row)- (seats-seat) - 1;
		}
		while (room[position].contains("B")) {
			System.out.println("That ticket has already been purchased!");
			System.out.println();
			System.out.println("Enter a row number:");
			row = sc.nextInt();
			System.out.println("Enter a seat number in that row:");
			seat = sc.nextInt();
			position = (seats * row)- (seats-seat) - 1;
		}
		int soldIn = 0;
		int totalPrice = 0;
		int price = 0;
		if ((rows * seats) < 60) {
			price = 10;
		} else {
			price = (row >= Math.ceil(rows / 2.0)) ? 8 : 10;
		}
		soldIn++;
		totalPrice += price;
		System.out.println("Ticket price: $" + price);
		for (int i = 0; i < room.length; i++) {
			if (i == position) {
				room[i] = "B";
			}
		}
		return new int[] {soldIn, totalPrice};
	}
	
	public static int[] callMenu(String[] room, int rows, int seats, int row, int seat, String option, int sold, int totalPrice ) {
		int[] values = new int[2];
		switch (option) {
			case "0":
				return values;
		case "1":
				printRoom(room, rows, seats);
				break;
		case "2":
				values = buyTicket(room, rows, seats, row, seat, option);
				break;
		case "3":
			statistics(rows, seats, sold, totalPrice);
			break;
		}
		return values;
	}

	public static void statistics(int rows, int seats, int sold, int totalPrice) {
		int totalSeats = rows * seats;
		Double percentage = (sold * 100.0) / totalSeats;
		int price = 0;
		if ((rows * seats) < 60) {
			price = (rows * seats * 10);
		} else {
			double a = (Math.floor((float)rows / 2) * seats * 10);
			double b =  (Math.ceil((float)rows / 2) * seats * 8);
			price = (int) (a + b);
		}
		System.out.println("Number of purchased tickets: " + sold);
		System.out.println(String.format("Percentage: %.2f%%", percentage));
		System.out.println("Current income: $" + totalPrice);
		System.out.println("Total income: $" + price);
	}
}
