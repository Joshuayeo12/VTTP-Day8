package day08.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) throws IOException {
        // Default IP and port
        String ip = "localhost"; // Change this to the server's IP address if needed
        int port = 3000;

        // If arguments are provided, split them to get IP and port
        if (args.length > 0) {
            String[] split = args[0].split(":");
            ip = split[0];
            port = Integer.parseInt(split[1]);
        }

        System.out.println("Connecting to server...");
        // Create a new socket to connect to the specified IP and port
        Socket sock = new Socket(ip, port);
        System.out.println("Connected!");

        // Setup input and output streams
        Scanner scanner = new Scanner(System.in);
        OutputStream outputS = sock.getOutputStream();
        Writer writer = new OutputStreamWriter(outputS);
        BufferedWriter bWriter = new BufferedWriter(writer);
        InputStream inputS = sock.getInputStream();
        Reader reader = new InputStreamReader(inputS);
        BufferedReader bReader = new BufferedReader(reader);

        while (true) {
            System.out.print("Input: ");
            String theMessage = scanner.nextLine();

            // Check for empty input
            if (theMessage == null || theMessage.trim().isEmpty()) {
                System.out.println("Invalid input, try again.");
                continue;
            }

            // Handle close command
            if (theMessage.equalsIgnoreCase("close")) {
                bWriter.write(theMessage);
                bWriter.newLine();
                bWriter.flush();
                break; // Exit the loop after sending the close command
            }

            // Handle play-game command
            if (theMessage.equalsIgnoreCase("play-game")) {
                bWriter.write(theMessage);
                bWriter.newLine();
                bWriter.flush();

                // Read and display the response from the server
                String fromServer = bReader.readLine();
                if (fromServer != null) {
                    System.out.println(">>>SERVER: " + fromServer);
                } else {
                    System.out.println("Invalid response received");
                }
            } else {
                System.out.println("Invalid command. Enter 'play-game' or 'close'");
            }
        }

        // Close resources
        outputS.close();
        inputS.close();
        sock.close();
        scanner.close();
    }
}
