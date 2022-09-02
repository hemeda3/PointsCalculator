CREATE TABLE CUSTOMER (cust_id int, cust_name VARCHAR2(255) );
CREATE TABLE TRANSACTION (trans_id int,cust_id int ,trans_date DATE,trans_amount int);
INSERT INTO CUSTOMER(cust_id,cust_name) values (9999,'Ahmed');
INSERT INTO CUSTOMER(cust_id,cust_name) values (8888,'Mohammed');

INSERT INTO TRANSACTION(trans_id,cust_id,trans_date,trans_amount) VALUES (120,9999,'1999-10-10',120);
INSERT INTO TRANSACTION(trans_id,cust_id,trans_date,trans_amount) VALUES (130,9999,'2099-10-10',120);


INSERT INTO TRANSACTION(trans_id,cust_id,trans_date,trans_amount) VALUES (200,8888,'1999-10-10',120);
INSERT INTO TRANSACTION(trans_id,cust_id,trans_date,trans_amount) VALUES (210,8888,'2099-10-10',120);
COMMIT;
