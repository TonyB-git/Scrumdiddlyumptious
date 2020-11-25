
package com.csc131.scrumdiddlyumptious.Entertaineon.models;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;



public class CSVParser {
	
	private String filename;
	private StringBuffer stringBuffer;
	private BufferedReader input;
	private boolean hasColumnHeaders;
	private ArrayList<String> valueList;
	private String [] headerList;
	
	private final char DELIMITER = ',';
	private final char QUOTE = '\"';
	private final char NEWLINE = '\n';
	private final char CARRIAGERETURN = '\r';
	

	public CSVParser(String filename, boolean hasColumnHeaders) {
		this.filename = filename;
		this.hasColumnHeaders = hasColumnHeaders;
	}
	
	public ArrayList<String> parse() {
		
		
		
		Boolean inQuotes = false;
		
		stringBuffer = new StringBuffer();
		valueList = new ArrayList<>();
		
		try {
			//open new buffered reader on file that was passed in
			//FUTURE: maybe upgrade this so that it can accept different character sets to pass on
			input = new BufferedReader(new InputStreamReader(new FileInputStream(this.filename), "UTF-8"));
			
			
			if(hasColumnHeaders) {
				String headerLine = input.readLine();
				headerList = headerLine.split(",");
			}
			
			
			//read a char as long as we aren't at EOF
			int current = input.read();
			while(current != -1) {
				
				char c = (char)current;
				
				switch(c) {
				
					case QUOTE:
						
						//either we are looking at the beginning of a quoted string or the end
						if(inQuotes) {
							inQuotes = false;
							
						} else {
							inQuotes = true;
						}
						
						break;
						
					case DELIMITER:
						
						//if in a quoted string, keep it intact
						if(inQuotes) {
							stringBuffer.append(c);	
						} else {
							
							//otherwise, tokenize what we have so far and add it to the list
							if(stringBuffer.length() > 0) {
								//System.out.println(stringBuffer.toString().trim());
								valueList.add(stringBuffer.toString().trim());
								stringBuffer.setLength(0);
							}else {
								valueList.add("");
							}
							
						}
						
						break;
						
					case NEWLINE:
					case CARRIAGERETURN:
						
						//if we are at the end of the line, check our stringbuffer and tokenize whats in it
						if(stringBuffer.length() > 0) {
							//System.out.println(stringBuffer.toString().trim());
							valueList.add(stringBuffer.toString().trim());
							stringBuffer.setLength(0);
						}
						
						break;
						
					default:
						
						//default is to add to the stringbuffer
						stringBuffer.append(c);
						break;
				
				}
				
				
			    current = input.read();
				
			    
			}
			
			input.close();
			
			
		}catch(IOException e) {
			
			e.printStackTrace();
			
		}finally{
			
			
		}
		
		System.out.println("Finished parsing " + filename);
		return valueList;
		
	}
}
