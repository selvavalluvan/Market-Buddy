Create table marketbuddy.trade (
trade_id int auto_increment, 
user_id int, 
stockname varchar(20),
no_units int, 
mode int, 
cost float, 
market varchar(10), 
status int, 
bid_amount int, 
status_notified int,
primary key (trade_id));


Create table marketbuddy.bill (
bill_id int auto_increment 
,user_id int 
,bill_date Date 
,trade_id int 
,amount float 
,primary key(bill_id));


create table marketbuddy.userinfo (
 user_id int auto_increment
,name varchar(20) 
,address varchar(50) 
,phone varchar(10)
,running_balance float(20)
,primary key (user_id));
