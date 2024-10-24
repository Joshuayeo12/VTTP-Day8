package day08.server;

//who wins is determined on the server side

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException; //(throws input and output exceptions)
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

import day08.Deck;

public class ServerApp {
    public static void main(String[] args) throws IOException {
        // creating server port
        int port = 3000;
        if (args.length > 0) {
            // for the terminal to read entry at args[0], the port number, as an integer
            port = Integer.parseInt(args[0]);
        }

        int numberofDecks = 1;
        numberofDecks = Integer.parseInt(args[1]);

        Deck cardDeck = new Deck();

        ServerSocket server = new ServerSocket(port);

        while (true) {

            System.out.println("Waiting for connection...");
            Socket sock = server.accept();
            System.out.println("Got a new connection");
            InputStream inputS = sock.getInputStream();
            Reader reader = new InputStreamReader(inputS);
            BufferedReader bReader = new BufferedReader(reader);
            OutputStream outputS = sock.getOutputStream();
            Writer writer = new OutputStreamWriter(outputS);
            BufferedWriter bWriter = new BufferedWriter(writer);
            String fromClient = bReader.readLine();
            System.out.println(">>>CLIENT: " + fromClient);

            bWriter.write(fromClient);
            bWriter.newLine();
            bWriter.flush();
            outputS.flush();
            outputS.close();
            inputS.close();
            sock.close();
        }

        
    }
}