package com.sml.test1;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class Test {

	public static void main(String[] args) {
		
		
		try {
//			getTamapiXMLElement("queuecapacity");
			setTamapiXMLElement("queuecapacity");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	private static void getTamapiXMLElement(String queuecapacity) throws Exception {
		  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		  DocumentBuilder builder = dbf.newDocumentBuilder();
		  Document doc = builder.parse(new File("tamapi_config.xml")); // 获取到xml文件
		  // 下面开始读取
		  Element root = doc.getDocumentElement(); // 获取根元素
		  NodeList students = root.getElementsByTagName(queuecapacity);

		  System.out.println(students.item(0).getFirstChild().getNodeValue());
		  System.out.println("sdfdf");
	}
	
	private static void setTamapiXMLElement(String queuecapacity) throws Exception {
		  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		  DocumentBuilder builder = dbf.newDocumentBuilder();
		  Document doc = builder.parse(new File("tamapi_config.xml")); // 获取到xml文件
		  // 下面开始读取
		  Element root = doc.getDocumentElement(); // 获取根元素
		  NodeList students = root.getElementsByTagName(queuecapacity);

		  students.item(0).getFirstChild().setNodeValue("3000");
		  
		  doc2XmlFile(doc,"tamapi_config.xml");
		  
	}
	
	private static boolean doc2XmlFile(Document document,String filename) 
	{ 
		boolean flag = true; 
		try{ 
			TransformerFactory tFactory = TransformerFactory.newInstance();    
			Transformer transformer = tFactory.newTransformer();  
			/** 编码 */ 
			//transformer.setOutputProperty(OutputKeys.ENCODING, "GB2312"); 
			DOMSource source = new DOMSource(document);  
			StreamResult result = new StreamResult(new File(filename));    
			transformer.transform(source, result);  
		}catch(Exception ex) { 
			flag = false; 
			ex.printStackTrace(); 
		} 
		return flag;       
	}

}
