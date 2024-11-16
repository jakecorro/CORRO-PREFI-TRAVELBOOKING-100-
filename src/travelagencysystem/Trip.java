package travelagencysystem;

import java.util.Scanner;

public class Trip {
    Scanner scan = new Scanner(System.in);
    Config conf = new Config();
    
    public void tripConfig() {
        int option;
        do {
            System.out.println("\n╔═════════════════════════════╗");
            System.out.println("║                    TRIP MENU                  ║");
            System.out.println("╚═════════════════════════════╝\n");
            System.out.println("1. Add Trip");
            System.out.println("2. View Trips");
            System.out.println("3. Edit Trip");
            System.out.println("4. Delete Trip");
            System.out.println("5. Exit");
            
            System.out.print("Choose an option: ");
            option = scan.nextInt();
            scan.nextLine();

            switch (option) {
                case 1:
                    addTrip();
                    break;
                case 2:
                    viewTrips();
                    break;
                case 3:
                    editTrip();
                    break;
                case 4:
                    deleteTrip();
                    break;
                case 5:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (option != 5);
    }

    private void addTrip() {
        System.out.println("Enter Trip Details:");
        System.out.print("\nDestination: ");
        String destination = scan.nextLine();
        
        String startDate;
        while (true) {
        System.out.print("Start Date (YYYY-MM-DD): ");
        startDate = scan.nextLine();
         if (startDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            System.out.println("Valid date: " + startDate);
            break;
        } else {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }
         String endDate;
        while (true) {
        System.out.print("End Date (YYYY-MM-DD): ");
        endDate = scan.nextLine();
         if (endDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            System.out.println("Valid date: " + endDate);
            break;
        } else {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }
        System.out.print("Price: ");
        double price = scan.nextDouble();
        scan.nextLine();
        
        System.out.println("Status(Available/ Unavailable): ");
         String status = scan.nextLine();
         while (!status.equalsIgnoreCase("Available") && !status.equalsIgnoreCase("Unavailable"))
         {
             System.out.println("Invalid status. Please enter 'Available' or 'Unavailable'.");
             System.out.println("Status: ");
             status= scan.nextLine();
         }
        
        String sql = "INSERT INTO trip (destination, start_date, end_date, price, status) VALUES (?, ?, ?, ?, ?)";
        conf.addRecord(sql, destination, startDate, endDate, price, status);
    }

    public void viewTrips() {
        String query = "SELECT * FROM trip";
        String[] headers = {"ID", "Destination", "Start Date", "End Date", "Price", "Status"};
        String[] columns = {"id", "destination", "start_date", "end_date", "price", "Status"};
        conf.viewRecords(query, headers, columns);
    }

    private void editTrip() {
        System.out.print("Enter Trip ID to edit: ");
        int id = scan.nextInt();
        scan.nextLine();

        if (!conf.doesIDExist("trip", id)) {
            System.out.println("Trip ID not found.");
            return;
        }

        System.out.println("Enter New Trip Details:");
        System.out.print("New Destination: ");
        String destination = scan.nextLine();
        String startDate;
        while (true) {
        System.out.print("Start Date (YYYY-MM-DD): ");
        startDate = scan.nextLine();
         if (startDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            System.out.println("Valid date: " + startDate);
            break;
        } else {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }
         String endDate;
        while (true) {
        System.out.print("End Date (YYYY-MM-DD): ");
        endDate = scan.nextLine();
         if (endDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            System.out.println("Valid date: " + endDate);
            break;
        } else {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }
        System.out.print("New Price: ");
        double price = scan.nextDouble();
        scan.nextLine();
        
        System.out.println("Status(Available/ Unavailable): ");
         String status = scan.nextLine();
         while (!status.equalsIgnoreCase("Available") && !status.equalsIgnoreCase("Unavailable"))
         {
             System.out.println("Invalid status. Please enter 'Available' or 'Unavailable'.");
             System.out.println("Status: ");
             status= scan.nextLine();
         }

        String sql = "UPDATE trip SET destination = ?, start_date = ?, end_date = ?, price = ?, status= ? WHERE id = ?";
        conf.updateRecord(sql, destination, startDate, endDate, price,status, id);
    }

    private void deleteTrip() {
        System.out.print("Enter Trip ID to delete: ");
        int id = scan.nextInt();

        String sql = "DELETE FROM trip WHERE id = ?";
        conf.deleteRecord(sql, id);
    }
}
