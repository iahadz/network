import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) {
       try {
            Socket socket = new Socket("192.168.0.94", 1111); // Connect to the server
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Input stream to receive data from the server
            PrintWriter outToServer = new PrintWriter(socket.getOutputStream(), true); // Output stream to send data to the server
            Scanner scanner = new Scanner(System.in); // Scanner to read user input

            while (true) {
                // Display menu
                System.out.println("\nEnter one of the following commands:");
                System.out.println("B: Convert to binary");
                System.out.println("H: Convert to hexadecimal");
                System.out.println("Q: Quit the client program");
                System.out.print("Your command: ");
                String command = scanner.nextLine().toUpperCase();

                // Check if command is empty
                if (command.isEmpty()) {
                    outToServer.println(); // Send an empty request to the server
                    String response = inFromServer.readLine(); // Receive response from the server
                    System.out.println("Server response: " + response);
                    continue;
                }

                // Check user input
                if (command.equals("Q")) {
                    break; // Quit if user enters 'Q'
                } else if (command.equals("B") || command.equals("H") ||command.equals(" ")||command.equals(null)) {
                    System.out.print("Enter a number: ");
                    String number = scanner.nextLine();
                    // Send command and number to the server
                    outToServer.println(command + number);
                    // Receive response from the server
                    String response = inFromServer.readLine();
                    System.out.println("Server response: " + response);
                } else {
                    System.out.println("Invalid command. Please try again.");
                }
            }

            socket.close(); // Close the socket
            scanner.close(); // Close the scanner
        } catch (IOException e) {
            System.out.println("Server is down, please try later.");
        }
    }
}
