import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerDemo {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			// open server socket
			serverSocket = new ServerSocket(Config.SOCKET_PORT);
			
			System.out.println("wating for connections on host:"+ serverSocket.getInetAddress().getHostName() + ", port: " + Config.SOCKET_PORT + " ... ");
			Socket clientSocket = serverSocket.accept();
			System.out.println("connected.");
			
			
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
	
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	
			String inputLine;
			while ((inputLine = in.readLine()) != null) //read from socket input stream
			{
				//write back received input to socket output stream => send text back to client
				out.println(inputLine);
			}
			
			if (!serverSocket.isClosed()) serverSocket.close();			
		} catch (IOException e) { 
			e.printStackTrace();
			
			if (serverSocket != null && !serverSocket.isClosed()) {
				try {
					serverSocket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		System.out.println("Server done.");
	}

}
