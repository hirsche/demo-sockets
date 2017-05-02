package at.fhs.nos.demosockets;
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
			
			// info output
			System.out.println("wating for connections on host:"+ serverSocket.getInetAddress().getHostName() + ", port: " + Config.SOCKET_PORT + " ... ");
			Socket clientSocket = serverSocket.accept();
			System.out.println("connected.");
			
			// open print writer
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
	
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	
			String inputLine;
			while ((inputLine = in.readLine()) != null) //read from socket input stream
			{
				//write back received input to socket output stream => send text back to client
				out.println(inputLine);
			}
		} catch (IOException e) { 
			e.printStackTrace();
			
			closeConnection(serverSocket);
		}
		
		closeConnection(serverSocket);
		System.out.println("Server done.");
	}

	/**
	 * closes socket connection
	 * 
	 * @param serverSocket socket for current connection
	 */
	private static void closeConnection(ServerSocket serverSocket) {
		if (serverSocket != null && !serverSocket.isClosed()) {
			try {
				serverSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		};
	}
}
