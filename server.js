var SERVER_PORT = 8800;
 
var restify = require('restify'),
     mysql      = require('mysql');
var server = restify.createServer();

var connection = mysql.createConnection({
  host     : 'localhost',
  user     : 'mb',
  password : 'mbuddy',
});

////////////////////////////////BUY////////////////////////// 
var respond_buy = function(req, res, next) {
    r_user_id = req.params.user_id;
	r_stockname = req.params.stockname;
	r_market = req.params.market;
	r_no_units = req.params.no_units;
	r_bid_amount = req.params.bid_amount;

	//Buy from market
	//A mock function that makes success or failure randomly
	var buystatus, t_id, new_bal;
	if(Math.floor((Math.random()*10)+1)%2 ==0)
		buystatus = 1;
	else 
		buystatus = 0;

	var sql = 'INSERT INTO marketbuddy.trade (' +
				'user_id' +
				',stockname' +
				',no_units' +
				',cost' +
				',mode' +
				',market' +
				',status' +
				',bid_amount' +
				',status_notified' +
				') VALUES';

	//Entry in trade db
	connection.query(sql, function(err, rows) {

//add quote
		sql += '( ' +
				r_user_id + 
				',"' + r_stockname + '"' +
				',' + r_no_units +
				',' + '10' +
				',' + '1' +
				',"' + r_market + '"' +
				',' + buystatus +
				',' + r_bid_amount +
				',' + '0' +
				')';


	connection.query(sql, function(err, result) {
		
			t_id = (result.insertId);
	if(buystatus == 1){
	
	var sql1 = 'INSERT INTO marketbuddy.bill (' +
				'user_id' +
				',bill_date' +
				',trade_id' +
				',amount' +
				') VALUES';

	//Entry in bill db
	connection.query(sql1, function(err, rows) {
	
//add quote
		sql1 += '(' +
				r_user_id + 
				',' + 'now()' +
				',' + t_id +
				',' + r_bid_amount +
				')';

		connection.query(sql1);			
		console.log('Bill: ', sql1);
		if (err) throw err;
	});

	connection.query('SELECT running_balance as bal FROM marketbuddy.userinfo WHERE user_id = '+ r_user_id + ';', function(err, result){
	var current_bal = result[0].bal;
	new_bal = parseFloat(current_bal) - parseFloat(r_bid_amount);

	var updte =  'UPDATE marketbuddy.userinfo ' + ' SET running_balance = '+ parseFloat(new_bal) + ' WHERE user_id = ' + r_user_id + ';';

	connection.query(updte, function(err){
	console.log('up: ' + updte);
	console.log(new_bal.toString());
	if (err) throw err;
	}); 

	console.log(current_bal.toString());
	if (err) throw err;
});
	
	console.log("User: " + r_user_id + " is buying " + r_stockname + " stocks");

//if ending
}      


	});
	//connection.query(sql);
	console.log('sql: ', sql);
	if (err) throw err;
		
});

	if (buystatus == 0){
	//return status
	console.log("User: " + r_user_id + " failed to buy " + r_stockname + " stocks");}
	//return status
	
 	res.send(buystatus.toString());
}
 



//////////////////////////////////SELL////////////////////////
var respond_sell = function(req, res, next) {
    	r_user_id = req.params.user_id;
	r_stockname = req.params.stockname;
	r_market = req.params.market;
	r_no_units = req.params.no_units;
	r_bid_amount = req.params.bid_amount;

	//sell from market
	//A mock function that makes success or failure 	randomly
	var sellstatus;
	if(Math.floor((Math.random()*10)+1)%2 ==0)
		sellstatus = 1;
	else 
		sellstatus = 0;

	var sql2 = 'INSERT INTO marketbuddy.trade (' +
				'user_id' +
				',stockname' +
				',no_units' +
				',cost' +
				',mode' +
				',market' +
				',status' +
				',bid_amount' +
				',status_notified' +
				') VALUES';

	//Entry in trade db
	connection.query(sql2, function(err, rows) {

	//add quote
		sql2 += '( ' +
				r_user_id + 
				',"' + r_stockname + '"' +
				',' + r_no_units +
				',' + '10' +
				',' + '0' +
				',"' + r_market + '"' +
				',' + sellstatus +
				',' + r_bid_amount +
				',' + '0' +
				')';

	connection.query(sql2);
	console.log('sql: ', sql2);
	if (err) throw err;
		
});

if (sellstatus == 1){
connection.query('SELECT running_balance as bal FROM  marketbuddy.userinfo WHERE user_id = '+ r_user_id + ';', function(err, result)
{
	var current_bal = result[0].bal;
	new_bal = parseFloat(current_bal) + parseFloat(r_bid_amount);

var updte =  'UPDATE marketbuddy.userinfo ' + ' SET running_balance = '+ parseFloat(new_bal) + ' WHERE user_id = ' + r_user_id + ';';

connection.query(updte, function(err)
{
	console.log('update user info: ' + updte);
	console.log(new_bal.toString());
if (err) throw err;
}); 

	console.log(current_bal.toString());
if (err) throw err;
});
	console.log("User: " + r_user_id + " sold " + r_stockname + "stocks");
}

else{
	//return status
	console.log("User: " + r_user_id + " failed to sell " + r_stockname + "stocks");
}
 	res.send(sellstatus.toString());
}



//////////////////////STATUS//////////////////////////////
var respond_status = function(req,res,next) {

    	connection.query('SELECT * FROM marketbuddy.trade LIMIT 10 ', function(err, rows, fields) {
        if (err) throw err;
        
        var arr = new Array();    
    
        for(i = 0; i < rows.length; i++) {
            var trade_obj = {};
            trade_obj.trade_id = rows[i].trade_id;
            trade_obj.user_id = rows[i].user_id;
            trade_obj.stockname = rows[i].stockname;
            trade_obj.no_units = rows[i].no_units;
            trade_obj.mode = rows[i].mode;
            trade_obj.cost = rows[i].cost;
            trade_obj.market = rows[i].market;
            trade_obj.statu = rows[i].statu;
            trade_obj.bid_amount = rows[i].bid_amount;

            arr.push(trade_obj);

            if(arr.length == rows.length) {
                res.send(arr);    
            }
        }
    });
    
    console.log("Status Hit");
    
}
 
var server_up = function() {
    console.log("Market Buddy is up on " + server.url);
}
 

server.get("/mb/buy/:user_id/:stockname/:market/:no_units/:bid_amount",respond_buy);
server.get("/mb/sell/:user_id/:stockname/:market/:no_units/:bid_amount",respond_sell);
server.get("/mb/status/:user_id",respond_status);
 
server.listen(SERVER_PORT, server_up);
