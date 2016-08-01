package Client;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("127.0.0.1",8899);
			
			System.out.println("客户端已连接上");
			
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			
			int size = 500;
			byte [] in = new byte [size];
			DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("new_target.pdf")));
			
			
			while((dis.read(in))!= -1){
				dos.write(in);
			}
			dos.close();
			dis.close();
			System.out.println("文件传输完成");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
