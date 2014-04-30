package com.work.marketbuddy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.work.marketbuddy.ClientAgent.Trade;

import android.os.AsyncTask;
import android.util.Log;

public class ServerCon {
	
	public int buyStock(Trade trade)		
		throws java.io.IOException {
        String responseBody = "-1";
        Gson gson=new Gson();
        String json=gson.toJson(trade);
        Log.e("Trade", json);
                try {
                		getJSON gj = new getJSON();
                		String buy_url = "http://10.0.2.2:8800/mb/buy/1/" 
                        		+ trade.stockname.trim() + "/" 
                        		+ trade.market 
                        		+ "/" + trade.no_units 
                        		+ "/" + trade.cost;
                		Log.e("Buy", buy_url);
                        responseBody = gj.execute(buy_url).get();
                } catch (Exception e) {
                        e.printStackTrace();
                }	
                if(responseBody.trim() == "1")
                	return 1;
                
        return 0;
		
	}
	
	public int sellStock(Trade trade)		
		throws java.io.IOException {
        String responseBody = "-1";
        
        Gson gson=new Gson();
        String json=gson.toJson(trade);
        Log.e("Trade", json);
                try {
                		getJSON gj = new getJSON();
                        responseBody = gj.execute(
                        		"http://10.0.2.2:8800/mb/sell/1/" 
                        		+ trade.stockname + "/" 
                        		+ trade.market 
                        		+ "/" + trade.no_units 
                        		+ "/" + trade.cost).get();
                } catch (Exception e) {
                        e.printStackTrace();
                }
                
                if(responseBody.trim() == "1")
                	return 1;
                
                return 0;	
	}
	
	public List<Trade> getOpenTrade() 		
		throws java.io.IOException {
            String responseBody = "";
            
                    try {
                    		getJSON gj = new getJSON();
                            responseBody = gj.execute("http://10.0.2.2:8800/mb/status/1").get();
                    } catch (Exception e) {
                            e.printStackTrace();
                    }
            Gson gson = new Gson();
            Type myTradeType = new TypeToken<List<Trade>>(){}.getType();
            List<Trade> myTrade = gson.fromJson(responseBody, myTradeType);
            return myTrade;
	}
	
	
	public List<Trade> getTradeStatus()		
		throws java.io.IOException {
            String responseBody = "";
                    try {
                    		getJSON gj = new getJSON();
                            responseBody = gj.execute("http://10.0.2.2:8800/mb/status/1").get();
                    } catch (Exception e) {
                            e.printStackTrace();
                    }
            Gson gson = new Gson();
            Type myTradeType = new TypeToken<List<Trade>>(){}.getType();
            List<Trade> myTrade = gson.fromJson(responseBody, myTradeType);
            return myTrade;
	}
	

	
	 public class getJSON extends AsyncTask<String, Void, String> {
	    	public String doInBackground(String...uri) {
	    	      BufferedReader in = null;
	              String data = null;
	              try {
	              		  Log.e("JSON", uri[0]);
	                      HttpClient client = new DefaultHttpClient();
	                      URI website = new URI(uri[0]);
	                      HttpGet request = new HttpGet();
	                      request.setURI(website);
	                      HttpResponse response = client.execute(request);
	                      in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	                      StringBuffer sb = new StringBuffer("");
	                      String l = "";
	                      while((l=in.readLine()) != null){
	                              sb.append(l);
	                      }
	                      in.close();
	                      data = sb.toString();
	                      Log.e("JSON", data);
	                      return data;
	              } catch (URISyntaxException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}finally {
	                      if(in != null) {
	                              try {
	                                      in.close();;
	                                      return data;
	                              }
	                      catch(Exception e){
	                              e.printStackTrace();
	                      }
	              }
	          }
	              return data;
	    	}
	    	  
	    }

}
