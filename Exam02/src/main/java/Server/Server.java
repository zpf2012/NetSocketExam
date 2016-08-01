package Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

	@Override
	public void run() {

		try {
			ServerSocket ss = new ServerSocket(8899);
			Socket s = null;
			System.out.println("服务器已启动。。。");
			while (true) {
				s = ss.accept();
				
				FileInputStream fis = new FileInputStream("target.pdf");
				BufferedInputStream bis = new BufferedInputStream(fis);
				
				FileOutputStream fos = new FileOutputStream("target.pdf");
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				
				byte [] out = new byte [100];
				bis.read(out);
				
				PrintWriter pw = new PrintWriter(bos, true);
				pw.println(s.getOutputStream());

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Server s = new Server();
		s.start();
	}
}
