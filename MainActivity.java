package com.work.marketbuddy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import com.work.marketbuddy.ClientAgent.Trade;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {


	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	static ClientAgent myAgent = new ClientAgent();
	
	public void buyStockOnClickHandler(View v){

		EditText stockname = (EditText) findViewById(R.id.et_stockname);
		EditText marketname = (EditText) findViewById(R.id.et_market);
		EditText units = (EditText) findViewById(R.id.et_units);
		EditText bid = (EditText) findViewById(R.id.et_bid);
		
		Trade trade = new Trade();
		trade.stockname = stockname.getText().toString();
		trade.market = marketname.getText().toString();
		trade.no_units = Integer.parseInt(units.getText().toString());
		trade.bid_amount = Double.parseDouble(bid.getText().toString());
		
		int stat = myAgent.buyStock(trade);
		String status = "";
		
		if(stat == 1)
			status = "is succesful";
		else if(stat == 0)
			status = "is in progress";
		else if(stat == -1)
			status = "cannot be completed";
			
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);
		alertDialogBuilder.setTitle("Market Buddy!");
		alertDialogBuilder
		.setMessage("Your trade " + status)
		.setCancelable(false)
		.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		  });

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
 
			
		Log.e("BUY", stockname.getText().toString());
		
		stockname.setText("");
		marketname.setText("");
		units.setText("");
		bid.setText("");

	}
	
	public void sellStockOnClickHandler(View v){
		
		EditText stockname = (EditText) findViewById(R.id.et_stockname_s);
		EditText marketname = (EditText) findViewById(R.id.et_market_s);
		EditText units = (EditText) findViewById(R.id.et_units_s);
		EditText bid = (EditText) findViewById(R.id.et_bid_s);
		
		Trade trade = new Trade();
		trade.stockname = stockname.getText().toString();
		trade.market = marketname.getText().toString();
		trade.no_units = Integer.parseInt(units.getText().toString());
		trade.bid_amount = Double.parseDouble(bid.getText().toString());
		
		
		int stat = myAgent.sellStock(trade);
		String status = "";
		
		if(stat == 1)
			status = "is succesful";
		else if(stat == 0)
			status = "is in progress";
		else if(stat == -1)
			status = "cannot be completed";
			
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);
		alertDialogBuilder.setTitle("Market Buddy!");
		alertDialogBuilder
		.setMessage("Your trade " + status)
		.setCancelable(false)
		.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		  });

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
		
		Log.e("SELL", stockname.getText().toString());
		
		stockname.setText("");
		marketname.setText("");
		units.setText("");
		bid.setText("");
		
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		myAgent = new ClientAgent();
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0: {
				Fragment fragment = new BuyStockFragment();
				Bundle args = new Bundle();
				args.putInt(BuyStockFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				return fragment;
			}
			case 1: {
				Fragment fragment = new SellStockFragment();
				Bundle args = new Bundle();
				args.putInt(SellStockFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				return fragment;
			}
			case 2: {
				Fragment fragment = new TradeStatusFragment();
				Bundle args = new Bundle();
				args.putInt(TradeStatusFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				return fragment;
			}
			}
			return null;

		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}


	public static class BuyStockFragment extends Fragment {

		public static final String ARG_SECTION_NUMBER = "section_number";

		public BuyStockFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.buystock,
					container, false);

			return rootView;
		}
	}
	
	public static class SellStockFragment extends Fragment {

		public static final String ARG_SECTION_NUMBER = "section_number";

		public SellStockFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.sellstock,
					container, false);

			return rootView;
		}
	}

	
	public static class TradeStatusFragment extends Fragment {

		public static final String ARG_SECTION_NUMBER = "section_number";

		public TradeStatusFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.tradestatus,
					container, false);
			
			List<Trade> openTradeList = myAgent.getOpenTrade();

			final ListView listview = (ListView) rootView.findViewById(R.id.lv_tradestat);
			
			 final ArrayList<String> list = new ArrayList<String>();
			    for (int i = 0; i < openTradeList.size(); ++i) {
			    
			    	String status = "";
			    	if(openTradeList.get(i).status == 0)
			    		status = "In progress";
			    	else if(openTradeList.get(i).status == 1)
			    		status = "Success";
			    	else if(openTradeList.get(i).status == -1)
			    		status = "Failure";
			    	
			    	list.add("Stock: " + openTradeList.get(i).stockname + "\n"
			    			+ "Market: " + openTradeList.get(i).market + "\n"
			    			+ "Status: " + status);
			    }
			    
			    final StableArrayAdapter adapter = new StableArrayAdapter(rootView.getContext(),
			        android.R.layout.simple_list_item_1, list);
			    listview.setAdapter(adapter);
			  
			return rootView;
		}
		private class StableArrayAdapter extends ArrayAdapter<String> {

		    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		    public StableArrayAdapter(Context context, int textViewResourceId,
		        List<String> objects) {
		      super(context, textViewResourceId, objects);
		      for (int i = 0; i < objects.size(); ++i) {
		        mIdMap.put(objects.get(i), i);
		      }
		    }

		    @Override
		    public long getItemId(int position) {
		      String item = getItem(position);
		      return mIdMap.get(item);
		    }

		    @Override
		    public boolean hasStableIds() {
		      return true;
		    }

		  }
	}
	



}
