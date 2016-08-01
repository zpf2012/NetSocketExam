package Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
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
				File file = new File("target.pdf");

				DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				
				int size = (int) file.length();
				byte [] out = new byte [size];
				while ((fis.read(out))!= -1) {
					dos.write(out);
				}
				
				dos.close();
				fis.close();
				s.close();
				System.out.println("文件传输完成");

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
