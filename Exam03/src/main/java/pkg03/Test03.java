package pkg03;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Test03 {
	public static void main(String[] args) {
		new get().start();
	}

}

class get extends Thread{
	HttpClient client = HttpClients.createDefault();
	
	@Override
	public void run() {
		HttpGet get = new HttpGet("http://hq.sinajs.cn/list=sz300170");
		try {
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			
			String line = EntityUtils.toString(entity, "UTF-8");
			System.out.println(line);
		
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.newDocument();
				
				Element root = doc.createElement("xml");
				Element stock = doc.createElement("stock");
				Element name = doc.createElement("name");
				Element open = doc.createElement("open");
				Element close = doc.createElement("close");
				Element current = doc.createElement("current");
				Element high = doc.createElement("high");
				Element low = doc.createElement("low");
				
				String[] arr = line.split(",");
				String [] arr3 = arr[0].split("\"");
//				System.out.println(arr3[1]);
		        
	        	name.setTextContent(arr3[1]);
				open.setTextContent(arr[1]);
				close.setTextContent(arr[2]);
				current.setTextContent(arr[3]);
				high.setTextContent(arr[4]);
				low.setTextContent(arr[5]);		           
				
				stock.appendChild(name);
				stock.appendChild(open);
				stock.appendChild(close);
				stock.appendChild(current);
				stock.appendChild(high);
				stock.appendChild(low);
				root.appendChild(stock);
				doc.appendChild(root);
				
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				StringWriter writer = new StringWriter();
				transformer.transform(new DOMSource(doc), new StreamResult(writer));
//				System.out.println(writer.toString());				
				transformer.transform(new DOMSource(doc), new StreamResult(new File("stock.xml")) );
				
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			}
			
			String[] arr2 = line.split(",");
			System.out.println(arr2[0]);
			String [] arr3 = arr2[0].split("\"");
			System.out.println(arr3[1]);
			 

			
			File file = new File("stock.json");
			FileOutputStream fos=new FileOutputStream(file);
			JsonObject f = new JsonObject();
			f.addProperty("name",arr3[1]);
			f.addProperty("open",arr2[1]);
			f.addProperty("close",arr2[2]);
			f.addProperty("current",arr2[3]);
			f.addProperty("high",arr2[4]);
			f.addProperty("low",arr2[5]);	
			fos.write(f.toString().getBytes("UTF-8"));
			
			fos.flush();
			fos.close();

			System.out.println(f.toString());
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}