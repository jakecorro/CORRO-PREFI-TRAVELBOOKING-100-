package travelagencysystem;

import java.util.Scanner;

public class Booking {
    Scanner scan = new Scanner(System.in);
    Config conf = new Config();
    
    public void bookingConfig() {
        int option;
        do {
            System.out.println("\n--- Booking Menu ---");
            System.out.println("1. Add Booking");
            System.out.println("2. View Bookings");
            System.out.println("3. Edit Booking");
            System.out.println("4. Delete Booking");
            System.out.println("5. Exit");
            
            System.out.print("Choose an option: ");
            option = scan.nextInt();
            scan.nextLine();

            switch (option) {
                case 1:
                    addBooking();
                    break;
                case 2:
                    viewBookings();
                    break;
                case 3:
                    editBooking();
                    break;
                case 4:
                    deleteBooking();
                    break;
                case 5:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (option != 5);
    }

    private void addBooking() {
        System.out.println("Enter Booking Details:");

        int customerId;
        do {
            System.out.print("\nCustomer ID: ");
            customerId = scan.nextInt();
            if (!conf.doesIDExist("customer", customerId)) {
                System.out.println("Customer ID doesn't exist.");
            }
        } while (!conf.doesIDExist("customer", customerId));

        int tripId;
        do {
            System.out.print("Trip ID: ");
            tripId = scan.nextInt();
            if (!conf.doesIDExist("trip", tripId)) {
                System.out.println("Trip ID doesn't exist.");
            }
        } while (!conf.doesIDExist("trip", tripId));

        scan.nextLine(); 

        System.out.print("Booking Date (YYYY-MM-DD): ");
        String bookingDate = scan.nextLine();
        System.out.print("Status: ");
        String status = scan.nextLine();

        String sql = "INSERT INTO booking (customer_id, trip_id, booking_date, status) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, customerId, tripId, bookingDate, status);
    }

    public void viewBookings() {
        String query = "SELECT * FROM booking";
        String[] headers = {"ID", "Customer ID", "Trip ID", "Booking Date", "Status"};
        String[] columns = {"id", "customer_id", "trip_id", "booking_date", "status"};
        conf.viewRecords(query, headers, columns);
    }

    private void editBooking() {
        System.out.print("Enter Booking ID to edit: ");
        int id = scan.nextInt();
        scan.nextLine();

        if (!conf.doesIDExist("booking", id)) {
            System.out.println("Booking ID not found.");
            return;
        }

        System.out.println("Enter New Booking Details:");
        System.out.print("New Customer ID: ");
        int customerId = scan.nextInt();
        System.out.print("New Trip ID: ");
        int tripId = scan.nextInt();
        scan.nextLine();
        System.out.print("New Booking Date (YYYY-MM-DD): ");
        String bookingDate = scan.nextLine();
        System.out.print("New Status: ");
        String status = scan.nextLine();

        String sql = "UPDATE booking SET customer_id = ?, trip_id = ?, booking_date = ?, status = ? WHERE id = ?";
        conf.updateRecord(sql, customerId, tripId, bookingDate, status, id);
    }

    private void deleteBooking() {
        System.out.print("Enter Booking ID to delete: ");
        int id = scan.nextInt();

        String sql = "DELETE FROM booking WHERE id = ?";
        conf.deleteRecord(sql, id);
    }
}
