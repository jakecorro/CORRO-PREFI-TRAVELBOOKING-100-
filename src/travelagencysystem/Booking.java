package travelagencysystem;

import java.util.Scanner;

public class Booking {
    Scanner scan = new Scanner(System.in);
    Config conf = new Config();
    
    public void bookingConfig() {
        int option;
        do {
            System.out.println("\n╔═════════════════════════════╗");
            System.out.println("║                  BOOKING MENU                 ║");
            System.out.println("╚═════════════════════════════╝\n");
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

    
    String tripStatus = conf.getTripStatus(tripId); 
    if (tripStatus.equalsIgnoreCase("unavailable")) {
        System.out.println("Cannot book this trip. The trip is currently unavailable.");
        return; 
    }
              
         String bookingDate;
        while (true) {
        System.out.print("Booking Date (YYYY-MM-DD): ");
        bookingDate = scan.nextLine();
         if (bookingDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            System.out.println("Valid date: " + bookingDate);
            break;
        } else {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }
         System.out.println("Status(Rescheduled/Cancelled/Confirmed): ");
         String status = scan.nextLine();
         while (!status.equalsIgnoreCase("Rescheduled") && !status.equalsIgnoreCase("Cancelled") && !status.equalsIgnoreCase("Confirmed"))
         {
             System.out.println("Invalid status. Please enter 'Rescheduled', 'Cancelled', or 'Confirmed'.");
             System.out.println("Status: ");
             status= scan.nextLine();
         }
        
        System.out.println("Is this a solo trip or group travel? (Enter 'Solo' or 'Group'): ");
        String tripType = scan.nextLine().trim();
        
            if(!tripType.equalsIgnoreCase("Solo")&& !tripType.equalsIgnoreCase("Group"))
            {
                System.out.println("Invalid input.Please enter 'Solo' or 'Group'.");
            }while (! tripType.equalsIgnoreCase ("Solo") && !tripType.equalsIgnoreCase("Group"));
                int groupSize = 1;
                
                   if (tripType.equalsIgnoreCase("Group")){
                       do { 
                           System.out.println("Enter the number of people (Maximum 5 for group travel): ");
                           groupSize = scan.nextInt();
                                if (groupSize < 2 || groupSize > 5){
                                    System.out.println("invalid group size. Group travel must be between 2 to 5 people.");
                                }
                       }while (groupSize < 2 || groupSize > 5 );
                            }
    
               
        String sql = "INSERT INTO booking (customer_id, trip_id, booking_date, status, trip_type, group_size) VALUES (?, ?, ?, ?, ?, ?)";
        conf.addRecord(sql, customerId, tripId, bookingDate, status, tripType, groupSize );
    
}

    public void viewBookings() {
        String query = "SELECT * FROM booking";
        String[] headers = {"ID", "Customer ID", "Trip ID", "Booking Date", "status", "Trip Type", "Group Size"};
        String[] columns = {"id", "customer_id", "trip_id", "booking_date", "status", "trip_type", "group_size"};
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
        String bookingDate;
        while (true) {
        System.out.print("Booking Date (YYYY-MM-DD): ");
        bookingDate = scan.nextLine();
         if (bookingDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            System.out.println("Valid date: " + bookingDate);
            break;
        } else {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }
         System.out.println("Status(Rescheduled/Cancelled/Confirmed): ");
         String status = scan.nextLine();
         while (!status.equalsIgnoreCase("Rescheduled") && !status.equalsIgnoreCase("Cancelled") && !status.equalsIgnoreCase("Confirmed"))
         {
             System.out.println("Invalid status. Please enter 'Rescheduled', 'Cancelled', or 'Confirmed'.");
             System.out.println("Status: ");
             status= scan.nextLine();
         }
        
        System.out.println("Is this a solo trip or group travel? (Enter 'Solo' or 'Group'): ");
        String tripType = scan.nextLine().trim();
        
            if(!tripType.equalsIgnoreCase("Solo")&& !tripType.equalsIgnoreCase("Group"))
            {
                System.out.println("Invalid input.Please enter 'Solo' or 'Group'.");
            }while (! tripType.equalsIgnoreCase ("Solo") && !tripType.equalsIgnoreCase("Group"));
                int groupSize = 1;
                
                   if (tripType.equalsIgnoreCase("Group")){
                       do { 
                           System.out.println("Enter the number of people (Maximum 5 for group travel): ");
                           groupSize = scan.nextInt();
                                if (groupSize < 2 || groupSize > 5){
                                    System.out.println("invalid group size. Group travel must be between 2 to 5 people.");
                                }
                       }while (groupSize < 2 || groupSize > 5 );
                            }

        String sql = "UPDATE booking SET customer_id = ?, trip_id = ?, booking_date = ?, status = ?, trip_type = ?, group_size =? WHERE id = ?";
        conf.updateRecord(sql, customerId, tripId, bookingDate, status, tripType, groupSize, id);
    }

    private void deleteBooking() {
        System.out.print("Enter Booking ID to delete: ");
        int id = scan.nextInt();

        String sql = "DELETE FROM booking WHERE id = ?";
        conf.deleteRecord(sql, id);
    }
}
