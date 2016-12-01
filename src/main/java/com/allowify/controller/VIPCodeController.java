package com.allowify.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.net.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.allowify.model.StatusConfiguration;
import com.allowify.model.VIPCode;
import com.allowify.repository.VIPCodeRepositoryCustom;



@Controller
@RequestMapping("/vip-code")
public class VIPCodeController {
	
	@Autowired
	private VIPCodeRepositoryCustom vipCodeRepositoryCustom;
	
	public static int count1 = 0;
	
	//Only numeric or alphabetic values
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody List<VIPCode> createVIPCodes() {
		
		int checkCount = 0;
		int  count=0;
		char[] charArrayForNumeric = {'0','1','2','3','4','5','6' ,'7','8','9'};
		char[] charArrayForAlphabetic = {'A','B','C','D','E','F',
				'G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		
		String[] codeArray = new String[500];
		
		List<VIPCode> listArray=new ArrayList<VIPCode>();
		
		
		for (int i=0; i<charArrayForNumeric.length; i++)
		{
			for (int j=0; j<charArrayForNumeric.length; j++)
			{
				for (int k=0; k<charArrayForNumeric.length; k++){
					
					for(int l=0;l<charArrayForNumeric.length;l++){
					
						for(int m=0;m<charArrayForNumeric.length;m++){
							
							String codeGeneratesStr = String.valueOf(charArrayForNumeric[i])
									+String.valueOf(charArrayForNumeric[j])
									+String.valueOf(charArrayForNumeric[k])
									+String.valueOf(charArrayForNumeric[l])
									+String.valueOf(charArrayForNumeric[m]);
							
							//System.out.println(codeGeneratesStr);
													
							if(checkCount < 500) {
								codeArray[checkCount] = codeGeneratesStr;
								VIPCode code = new VIPCode();
								code.setCode(codeGeneratesStr);
								code.setStatus(StatusConfiguration.PURCHASABLE);
								listArray.add(code);
								//vipCodeRepositoryCustom.save(listArray.get(checkCount));
								checkCount++;
							}
							else {
								checkCount=0;
								listArray.clear();
								codeArray = new String[0];
								codeArray = new String[500];									
							}
																					
							count++;
						}
					}
					
				}
			}
		
		}
		
		for (int i=0; i<charArrayForAlphabetic.length; i++)
		{
			for (int j=0; j<charArrayForAlphabetic.length; j++)
			{
				for (int k=0; k<charArrayForAlphabetic.length; k++){
					
					for(int l=0;l<charArrayForAlphabetic.length;l++){
					
						for(int m=0;m<charArrayForAlphabetic.length;m++){
							
							String codeGeneratesStr = String.valueOf(charArrayForAlphabetic[i])
									+String.valueOf(charArrayForAlphabetic[j])
									+String.valueOf(charArrayForAlphabetic[k])
									+String.valueOf(charArrayForAlphabetic[l])
									+String.valueOf(charArrayForAlphabetic[m]);
							
							//System.out.println(codeGeneratesStr);
													
							if(checkCount < 500) {
								codeArray[checkCount] = codeGeneratesStr;
								VIPCode code = new VIPCode();
								code.setCode(codeGeneratesStr);
								code.setStatus(StatusConfiguration.PURCHASABLE);
								listArray.add(code);
								//vipCodeRepositoryCustom.save(listArray.get(checkCount));
								checkCount++;
							}
							else{
								checkCount=0;
								listArray.clear();
								codeArray = new String[0];
								codeArray = new String[500];
																
							}
							
														
							count1++;
						}
					}
					
				}
			}
		
		}
		
		System.out.println("counting For Only numeric ="+ count);
		System.out.println("counting  For only alphabet="+ count1);
		
		
		return null;
	}
	
	@RequestMapping("/consecutive")
	@ResponseBody
	public VIPCode consecutiveVIPCodes() {
		
		int checkCount = 0;
		char[] charArrayForNumeric = {'0','1','2','3','4','5','6' ,'7','8','9'
				,'A','B','C','D','E','F',
				'G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
				

		for (int i=0; i<charArrayForNumeric.length; i++)
		{
			for (int j=0; j<charArrayForNumeric.length; j++)
			{
				for (int k=0; k<charArrayForNumeric.length; k++){
					
					for(int l=0;l<charArrayForNumeric.length;l++){
					
						for(int m=0;m<charArrayForNumeric.length;m++){
							
							String codeGeneratesStr = String.valueOf(charArrayForNumeric[i])
									+String.valueOf(charArrayForNumeric[j])
									+String.valueOf(charArrayForNumeric[k])
									+String.valueOf(charArrayForNumeric[l])
									+String.valueOf(charArrayForNumeric[m]);
							
							//System.out.println(codeGeneratesStr);
							Pattern pNumeric = Pattern.compile("^[0-9]*$");
							Pattern pAlpha = Pattern.compile("^[A-Z]*$");
							Matcher matherNumeric = pNumeric.matcher(codeGeneratesStr);
							Matcher matherAlpha = pAlpha.matcher(codeGeneratesStr);
							
													
							char[] selcetedCodeArray = new char[5];
							selcetedCodeArray = codeGeneratesStr.toCharArray();
							char[] postSelcetedCodeArray = new char[5];
							postSelcetedCodeArray[0] = selcetedCodeArray[0];
							int countings = 1;
							if(!matherNumeric.find()) {
								if(!matherAlpha.find()) {
									for(int s = 1; s < selcetedCodeArray.length; s++) {
							    
										if(selcetedCodeArray[s] == selcetedCodeArray[s - 1]) {
											countings ++;
											postSelcetedCodeArray[s]= selcetedCodeArray[s];
										} 	
										else {
											postSelcetedCodeArray[s]= selcetedCodeArray[s];
											
										}
								
									}	
									if(countings>3 && countings <=5 ){
										checkCount++;
										//System.out.println(postSelcetedCodeArray);
								
									} 
								  }
								}
						}
					}
					
				}
			}
		
		}
		System.out.println ("Check count for conscutive numbers "+ checkCount);
				
		return null;
				
	}
	
	@RequestMapping("/series-increment")
	@ResponseBody
	public VIPCode seriesVIPCodes() {
		int checkCount = 0;
		
		char[] charArrayForNumeric = {'0','1','2','3','4','5','6' ,'7','8','9'
				,'A','B','C','D','E','F',
				'G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
				
		for (int i=0; i<charArrayForNumeric.length; i++)
		{
			for (int j=0; j<charArrayForNumeric.length; j++)
			{
				for (int k=0; k<charArrayForNumeric.length; k++){
					
					for(int l=0;l<charArrayForNumeric.length;l++){
					
						for(int m=0;m<charArrayForNumeric.length;m++){
							
							String codeGeneratesStr = String.valueOf(charArrayForNumeric[i])
									+String.valueOf(charArrayForNumeric[j])
									+String.valueOf(charArrayForNumeric[k])
									+String.valueOf(charArrayForNumeric[l])
									+String.valueOf(charArrayForNumeric[m]);
							
							//System.out.println(codeGeneratesStr);
							//Incrementd Order
							
							
							Pattern pNumeric = Pattern.compile("^[0-9]*$");
							Pattern pAlpha = Pattern.compile("^[A-Z]*$");
							Matcher matherNumeric = pNumeric.matcher(codeGeneratesStr);
							Matcher matherAlpha = pAlpha.matcher(codeGeneratesStr);							
							
							char[] selcetedCodeArray = new char[5];
							selcetedCodeArray = codeGeneratesStr.toCharArray();
							char[] postSelcetedCodeArray = new char[5];
							postSelcetedCodeArray[0] = selcetedCodeArray[0];
							int countings = 1;
							
							if(!matherNumeric.find()) {
								if(!matherAlpha.find()) {
						
								for(int s = 1; s < selcetedCodeArray.length; s++) {
							    
									if(((int)selcetedCodeArray[s-1] + 1 ) == (int)selcetedCodeArray[s]) {
										countings ++;
										
										postSelcetedCodeArray[s]= selcetedCodeArray[s];
									} 	
									else {
										
										postSelcetedCodeArray[s]= selcetedCodeArray[s];
									}
								
								}
								if(countings>3 && countings <=5 ){
									checkCount++;
									System.out.println(postSelcetedCodeArray);
								
								} 
								
								
								
							}}
						
						}
					}
					
				}
			}
		
		}
		System.out.println("checkcount" +checkCount);
			
		return null;
	}
	
	
	@RequestMapping("/series-decrement")
	@ResponseBody
	public VIPCode seriesVIPCodesDecrementOrder() {
		int checkCount = 0;
				
		char[] charArrayForNumeric = {'0','1','2','3','4','5','6' ,'7','8','9'
				,'A','B','C','D','E','F',
				'G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
				
		for (int i=0; i<charArrayForNumeric.length; i++)
		{
			for (int j=0; j<charArrayForNumeric.length; j++)
			{
				for (int k=0; k<charArrayForNumeric.length; k++){
					
					for(int l=0;l<charArrayForNumeric.length;l++){
					
						for(int m=0;m<charArrayForNumeric.length;m++){
							
							String codeGeneratesStr = String.valueOf(charArrayForNumeric[i])
									+String.valueOf(charArrayForNumeric[j])
									+String.valueOf(charArrayForNumeric[k])
									+String.valueOf(charArrayForNumeric[l])
									+String.valueOf(charArrayForNumeric[m]);
							
										
							Pattern pNumeric = Pattern.compile("^[0-9]*$");
							Pattern pAlpha = Pattern.compile("^[A-Z]*$");
							Matcher matherNumeric = pNumeric.matcher(codeGeneratesStr);
							Matcher matherAlpha = pAlpha.matcher(codeGeneratesStr);							
							
									
							//Decremented order
							char[] selcetedCodeArray1 = new char[5];
							selcetedCodeArray1 = codeGeneratesStr.toCharArray();
							char[] postSelcetedCodeArray1 = new char[5];
							int countings1 = 1;
							if(!matherNumeric.find()){
								if(!matherAlpha.find()) {
									for(int s = selcetedCodeArray1.length-1; s > 0; s--) {
							    
										if(((int)selcetedCodeArray1[s-1]-1 ) == (int)selcetedCodeArray1[s]) {
											countings1 ++;
											postSelcetedCodeArray1[s]= selcetedCodeArray1[s];
										} 
										else {
											
											postSelcetedCodeArray1[s]= selcetedCodeArray1[s];
										}
								
									}
									if(((int)selcetedCodeArray1[0]-1 ) == (int)selcetedCodeArray1[1]){
										postSelcetedCodeArray1[0]= selcetedCodeArray1[0];
									}else {
										postSelcetedCodeArray1[0]= selcetedCodeArray1[0];
									}
									if(countings1>3 && countings1 <=5 ){
										checkCount++;
										System.out.println(postSelcetedCodeArray1);
									} 
								}
							 }
							
						}
					}
					
				}
			}
		
		}
		System.out.println("checkcount" +checkCount);
		return null;
		
	}
	
	
	@RequestMapping("/series-numeric")
	@ResponseBody
	public VIPCode findSeriesNumericDigits() {
			
		int checkCount = 0;
		
		char[] charArrayForNumeric = {'0','1','2','3','4','5','6' ,'7','8','9'};
			
		for (int i=0; i<charArrayForNumeric.length; i++)
		{
			for (int j=0; j<charArrayForNumeric.length; j++)
			{
				for (int k=0; k<charArrayForNumeric.length; k++){
					
					for(int l=0;l<charArrayForNumeric.length;l++){
					
						for(int m=0;m<charArrayForNumeric.length;m++){
							
							String codeGeneratesStr = String.valueOf(charArrayForNumeric[i])
									+String.valueOf(charArrayForNumeric[j])
									+String.valueOf(charArrayForNumeric[k])
									+String.valueOf(charArrayForNumeric[l])
									+String.valueOf(charArrayForNumeric[m]);
														
							//multiple +2 values
							char[] selcetedCodeArray3 = new char[5];
							selcetedCodeArray3 = codeGeneratesStr.toCharArray();
							char[] postSelcetedCodeArray3 = new char[5];
							postSelcetedCodeArray3[0] = selcetedCodeArray3[0];
							int countings3 = 1;
							for(int s = 1; s < selcetedCodeArray3.length; s++) {
							    
										if(((int)selcetedCodeArray3[s-1] + 2 ) == 
												(int)selcetedCodeArray3[s]) {
											countings3 ++;
											postSelcetedCodeArray3[s]= selcetedCodeArray3[s];
										} 	
										else {
											postSelcetedCodeArray3[s]= selcetedCodeArray3[s];
										}
								
									}
									if(countings3>3 && countings3 <=5 ){
										checkCount++;
										System.out.println(postSelcetedCodeArray3);
									} 
								}
							  }
							}
						}
					}
					
			
		System.out.println("+2 values of series" + checkCount);
		return null;
	}
	
	@RequestMapping("/dictonary")
	@ResponseBody
	public VIPCode findDictonaryWords() throws InterruptedException {
		
		String getUrl = null;
		int checkCount = 0;
					
		char[] charArrayForNumeric = {'0','1','2','3','4','5','6' ,'7','8','9'
				,'A','B','C','D','E','F',
				'G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
				
		for (int i=0; i<charArrayForNumeric.length; i++)
		{
			for (int j=0; j<charArrayForNumeric.length; j++)
			{
				for (int k=0; k<charArrayForNumeric.length; k++){
					
					for(int l=0;l<charArrayForNumeric.length;l++){
					
						for(int m=0;m<charArrayForNumeric.length;m++){
							
							String codeGeneratesStr = String.valueOf(charArrayForNumeric[i])
									+String.valueOf(charArrayForNumeric[j])
									+String.valueOf(charArrayForNumeric[k])
									+String.valueOf(charArrayForNumeric[l])
									+String.valueOf(charArrayForNumeric[m]);
							
							String startingIndexString = codeGeneratesStr.substring(0, 4);
							String endingIndexString = codeGeneratesStr.substring(1, 5);
							
							Pattern pAlpha = Pattern.compile("^[A-Z]*$");
							
							if(pAlpha.matcher(startingIndexString.toString()).find()) {
								
								getUrl = "http://api.microsofttranslator.com/V2/Ajax.svc/Translate?"
										+ "oncomplete"
										+ "=MicrosoftTranslateComplete&appId="
										+ "EF45DE6734F756B2F1DEF91B9DFCE3FD0B03748B&text="
										+ startingIndexString
										+ "&from=EN&to=HI";
								if(checkCount == 450) {
									 checkCount = 0;
									 Thread.sleep(180000);
								 }
								 checkCount++;
								 checkURL(getUrl, startingIndexString);
								
								 
							}
						    if(pAlpha.matcher(endingIndexString.toString()).find()) {
						    	
						    	getUrl = "http://api.microsofttranslator.com/V2/Ajax.svc/Translate?"
										+ "oncomplete"
										+ "=MicrosoftTranslateComplete&appId="
										+ "EF45DE6734F756B2F1DEF91B9DFCE3FD0B03748B&text="
										+ endingIndexString
										+"&from=EN&to=HI";
						    	if(checkCount == 450) {
									 checkCount = 0;
									 Thread.sleep(180000);
								 }
						    	
						    	checkCount++;
						    	checkURL(getUrl, endingIndexString);
						    	
						    }
								
						}
					}
					
				}
			}
		
		}
			    	    
	    return null;
	}
	
	private void checkURL(String getUrl, String wordChecker) {
		URL url;
	    InputStream is = null;
	    DataInputStream dis;
	    String st;
	     
	    try {
		    	 url = new URL(getUrl);
		         is = url.openStream();         // throws an IOException
		         dis = new DataInputStream(new BufferedInputStream(is));
		         while ((st = dis.readLine()) != null) {
		        	 
		        	 Pattern pattern = Pattern.compile("\"([^\"]*)\"");
		    	     Matcher matcher = pattern.matcher(st.toString());
		    	     if(matcher.find()) {
		    	    	 if(!(matcher.group(1).equals(wordChecker))) {
		    	    		 System.out.println("Meaningful String  =" + wordChecker);
		    	    	 }
		    	     }
		    
		         }
		 
		      } catch (MalformedURLException mue) {
		    	   
		         mue.printStackTrace();
		         System.exit(1);
		 
		      } catch (IOException ioe) {
		    	 
		         ioe.printStackTrace();
		         System.exit(1);
		 
		      } finally {
		 
		         try {
		            is.close();
		         } catch (IOException ioe) {
		            
		         }
		 
		      } 								
	   
	}
	

}
