package com.work.marketbuddy;

import java.io.IOException;
import java.util.List;

public class ClientAgent {
	
	public List<Trade> openTrade;
	private ServerCon secretAgent;
	
	public ClientAgent() {
		secretAgent = new ServerCon();
		
	}
	
	public static class Trade {
		public int trade_id;
		public int user_id;
		public String stockname;
		public int no_units;
		public int mode;
		public double cost;
		public String market;
		public int status;
		public double bid_amount;
		
		public Trade() {

		}
		
	}
	
	public int buyStock(Trade trade) {	
		
		try {
			return secretAgent.buyStock(trade);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
		
	}
	
	public int sellStock(Trade trade) {
		
		try {
			return secretAgent.sellStock(trade);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
		
	}
	
	public List<Trade> getOpenTrade() {
		
		try {
			return secretAgent.getOpenTrade();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return null;
	}
	
	
	public List<Trade> getTradeStatus(List<Trade> trades) {
		
		return null;
	}
	

}
