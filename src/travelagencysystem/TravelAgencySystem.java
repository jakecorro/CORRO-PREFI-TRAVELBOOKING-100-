package travelagencysystem;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TravelAgencySystem {
    static Config conf = new Config();
    
    static Scanner scan = new Scanner(System.in);
    static Customer customer = new Customer();
    static Trip trip = new Trip();
    static Booking booking = new Booking();
    
    public static void main(String[] args) { 
        Config.connectDB();
        int choice;

        do {
            try {
                System.out.println("\n   + Travel Agency Booking System +\n");
                System.out.println("1. Customers");
                System.out.println("2. Trips");
                System.out.println("3. Bookings");
                System.out.println("4. Generate Reports");
                System.out.println("5. Exit");
                
                System.out.print("\nEnter Option: ");
                choice = scan.nextInt();
                scan.nextLine();
                System.out.println("");

                switch (choice) {
                    case 1:  
                        customer.customerConfig();
                        break;
                    case 2:
                        trip.tripConfig();
                        break;
                    case 3:
                        booking.bookingConfig();
                        break;
                    case 4:
                        generateReports();
                        break;
                    case 5:                      
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid Option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine();
                choice = -1;
            }
        } while (choice != 5);
    }

    static void generateReports() {
        System.out.println("\n\t\t\t--- CUSTOMER BOOKING REPORT ---");
        customer.viewCustomers();

        int custId;
        do {
            System.out.print("\nEnter Customer ID for the report: ");
            custId = scan.nextInt();
            if (!conf.doesIDExist("customer", custId)) {
                System.out.println("Customer ID not found. Please try again.");
            }
        } while (!conf.doesIDExist("customer", custId));

        System.out.println("\n=================================");
        System.out.println("       CUSTOMER DETAILS          ");
        System.out.println("=================================");
        System.out.printf("Customer ID  : %d%n", custId);
        System.out.printf("Name         : %s%n", conf.getDataFromID("customer", custId, "name"));
        System.out.printf("Email        : %s%n", conf.getDataFromID("customer", custId, "email"));
        System.out.println("---------------------------------");

        if (conf.isTableEmpty("booking WHERE customer_id = " + custId)) {
            System.out.println("No booking records found for this customer.");
        } else {
            System.out.println("Booking Details:");
            String sql = "SELECT booking.id, trip.destination, booking.booking_date, booking.status " +
                         "FROM booking " +
                         "JOIN trip ON booking.trip_id = trip.id " +
                         "WHERE booking.customer_id = " + custId;

            String[] headers = {"Booking ID", "Destination", "Booking Date", "Status"};
            String[] columns = {"id", "destination", "booking_date", "status"};

            conf.viewRecords(sql, headers, columns);
            System.out.println("\n=================================");
        }
    }
}
