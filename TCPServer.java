import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1111); // Create a server socket
            
            while (true) {
                Socket socket = serverSocket.accept(); // Accept a connection from a client
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Input stream to receive data from the client
                PrintWriter outToClient = new PrintWriter(socket.getOutputStream(), true); // Output stream to send data to the client

                while (true) {
                    String request = inFromClient.readLine(); // Read a request from the client

                    if (request == null) {
                        outToClient.println("500 Request is empty");
                        continue;
                    }

                    if (request.length() < 2) {
                        outToClient.println("300 Bad request");
                        continue;
                    }

                    char command = request.charAt(0); // Extract command
                    String number = request.substring(1); // Extract number

                    if (command != 'B' && command != 'H') {
                        outToClient.println("300 Bad request");
                        continue;
                    }

                    if (number.isEmpty()) {
                        outToClient.println("400 The number is missing");
                        continue;
                    }

                    try {
                        int num = Integer.parseInt(number); // Parse the number
                        String result = (command == 'B') ? Integer.toBinaryString(num) : Integer.toHexString(num).toUpperCase();
                        outToClient.println("200 " + result); // Send the result
                    } catch (NumberFormatException e) {
                        outToClient.println("400 The number is missing");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
