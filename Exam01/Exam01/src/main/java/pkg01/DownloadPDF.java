package pkg01;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class DownloadPDF {

	public static void main(String[] args) {
		new get().start();
	}

}

class get extends Thread{
	HttpClient client = HttpClients.createDefault();
	
	@Override
	public void run() {
		HttpGet get = new HttpGet("http://files.saas.hand-china.com/java/target.pdf");
		try {
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			
			byte [] line = EntityUtils.toByteArray(entity);
//			System.out.println(line);
			
			FileOutputStream fos = new FileOutputStream("localhost.pdf");
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			
			bos.write(line);
				
		
			bos.close();
			fos.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
