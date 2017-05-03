package at.fhs.nos.demosockets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientDemo {

	public static void main(String[] args) {
		Socket echoSocket = null;
		
		try {
			System.out.println("Client starting...");
			echoSocket = new Socket(Config.SOCKET_NAME, Config.SOCKET_PORT);

			//send stream to remote server host
			PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
			
			//receive a stream from remote server host
			BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
	
			// standard-in reader
			BufferedReader stdIn = new BufferedReader(
			                    new InputStreamReader(System.in));
	
			String userInput;
			while (!echoSocket.isClosed() && (userInput = stdIn.readLine()) != null)
			{	
				if (userInput.equals("quit")) {
					// close connection if quit has been entered
					echoSocket.close();
				} else {
					//sending to server over socket output stream
					out.println(userInput);
					out.flush();
					
					//receiving from server over socket input stream
					System.out.println("server at "+ echoSocket.getInetAddress().getHostAddress() + " responses with echo: " + in.readLine());
				}
			}			
		} catch (IOException e) {
			e.printStackTrace();
			
			closeConnection(echoSocket);
		}
		
		closeConnection(echoSocket);
		
		System.out.println("Client Done.");
	}
	
	/**
	 * closes socket connection
	 * 
	 * @param echoSocket socket for current connection
	 */
	private static void closeConnection(Socket echoSocket) {
		if (echoSocket != null && !echoSocket.isClosed()) {
			try {
				echoSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		};
	}

}
