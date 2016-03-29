package sg.edu.nus.iss.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class BaseDao {

	public ArrayList<String> readFromFile(String fullPath) throws IOException{
		ArrayList<String> readList=new ArrayList<>();
		File file=new File(fullPath);
		FileReader fr;
		fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line="";
		
		while((line=br.readLine())!=null){
			readList.add(line);
		}
		br.close();
		fr.close();
		
		return readList;		
	}
	
	public void writeToFile(ArrayList<StringBuffer> writeList,String fullPath) throws IOException{
		File file=new File(fullPath);
		FileWriter fw=new FileWriter(file);
		BufferedWriter bw=new BufferedWriter(fw);
		PrintWriter pw=new PrintWriter(bw);
		Iterator<StringBuffer> i=writeList.iterator();
		while (i.hasNext()) {
			StringBuffer lineWrite = (StringBuffer) i.next();
			pw.println(lineWrite);
		}
		pw.close();
		bw.close();
		fw.close();
		
	}
}