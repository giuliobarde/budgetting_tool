use `expense_tracker_db`;
-- create university entity (1)
insert into university values (1, 'St. Johns University');

-- create departments entities (15)
insert into department values (1,1,'Administrative and Instructional Leadership');
insert into department values (2,1,'Business Analytics & Information Systems');
insert into department values (3,1,'Communication Sciences and Disorders');

-- create employee entities (30 employees, 10 per department)
insert into employee values ('X02900569','$2a$10$54pBo3uetinZdiZZwChvHO8yATyGezMiE.vuOgT/RwKynj7ihyr/y','Eddie','Peterson',1); -- pw:gamer
insert into employee values ('X02873918','$2a$12$0Y/dGy2uxgRCjvYwE09CFOvchQEcNe2SgC3ss/bdwee5VlWTD6OXK','Abigail','Fawkes',1); -- pw:password
insert into employee values ('X05687921','$2a$12$SVmYmsDcT7m/o8oKXeYwXOpqtpxRS8TDNmdIhHgO1tCfr6NFi8IMO','Sally','Chen',1); -- pw:lucky
insert into employee values ('X01984579','$2a$10$u9fFe1nxoPs/WzOoZvo9aeMXQ65.AtQ.27QU4dapjE6.gZ97DOohu','Ronald','Donald',1); -- pw:goodpassword
insert into employee values ('X01930293','$2a$12$5IziuamAP8RsCQy8WsCG4eU62RsnrCW89yT5hIBZeSz2JLYbFyGme','Sam','Roberts',1); -- pw:great
insert into employee values ('X01939582','$2a$10$7jO.3GVinbEZwO/KRq.vTeDxFZhP0lsqGAfDph7vwHfKUqFFFKdRG','Bobby','Tables',1); -- pw:goodpassword | same password as Ronald Donald
insert into employee values ('X04518293','$2a$12$3JYBX22ZFMw3VpO1qR37g.DGQ2i9.vI1o5Cr96W5ImS1Qyxa0rM7K','Nick','Valentine',1); -- pw:12345678
insert into employee values ('X03891832','$2a$12$rRzgzrZ8uGlydDtfs4AhS.7Y561MjiHjoePzQjmegqv.M.vYuLmky','John','Chen',1); -- pw:hunter12
insert into employee values ('X05198402','$2a$12$iNdILmEc2zWllpnFq/6JLOzVOADHXOb2il2pCWcma91r/7Gi85U26','Sally','Nelson',1); -- pw:forcefield
insert into employee values ('X07524843','$2a$12$CXKeTRAld1EZqlkwvSIEsuE1/r.g0xTdjiftzwLP1/YeRuY7dafb.','Gary', 'Fung',1); -- pw:gc6uZGDTot9JrY
insert into employee values ('X05910492','$2a$12$UX3QqFVAF0RM2ZNOdRsh8eVqzrE0NqRpcTzMplXuwWLXSSJGPUiqG','Benjamin','Lauder',2); -- pw:69iyaFPnLD4f8m
insert into employee values ('X07539763','$2a$12$LiwlH8zso.UMCxdEGUyTMeXS/KupZnemsll8mXsQq4Jo8w4tcQprq','Peter', 'Johnson',2); -- pw:fSUMYVtgR5AK5C
insert into employee values ('X06182031','$2a$12$2FD6z4xla.EQ4mf9Art.1uLZW6ftl/7kwhvzjUM0/Nb3wOjUf2Jr.','Ryan','Mullet',2); -- pw:dE8x3C3oNrK4Fv
insert into employee values ('X06597342','$2a$12$WIJT44QXLKZ6dLC7MACJtu8RBXmV1lqjPqOjjCoXD0VB6sMI5Hk2W','David', 'Cunningham', 2); -- pw:Q7bSQUtqufsQp5
insert into employee values ('X05791230','$2a$12$k6Vf11DNxDcLMgso1WCEO.BAi1XW8q/b0nNwij2uwHGvxGxFH8ISO','Jenny','Garcia',2); -- pw:2eA7SM6qKXmF7J
insert into employee values ('X08545365','$2a$10$aXnk00KVNCw0bsixBQq8HuyME6B5hqt6IklxKTFh63yx.F.fAxOZq','Sally','Chen', 2); -- pw:ILOVECATS | regular employee
insert into employee values ('X03217321','$2a$12$aVMwetQvcmaW9oQZ9XrvE.kUO4TSZ2.XtFVwX/JIQp1TYnDguohPm','Madison','Lin',2); -- pw:qwertyuiop
insert into employee values ('X05740129','$2a$12$Pmiii2x2SdK5ATkIjsz7QOM08SJkm6TEO.THzAYjim1p5N7GBiAAu','Myrel','Fluros',2); -- pw:asdfghjkl
insert into employee values ('X03029305','$2a$12$3DppzNdSNog32n9gkfvLiux1t26tVMc2F4kqtlz5ZIfhuPCeLINW.','Nelson','Egglebert',2); -- pw:zxcvbnm,
insert into employee values ('X09836478','$2a$10$Nil4tVstImgNvDYw9KsxGuafs3YeYMV8QaTsf1xGlskiUHBbXsfrq', 'Jearmy', 'Lou',2); -- pw:POWERFULPASSWORD
insert into employee values ('X07691039','$2a$12$oEXNpdfP.g26KFySnhBKPO0dnc.bSRkap8GKnh0VSqgfGVJC5PDWq','Billy','Mackerel',3); -- pw:password1
insert into employee values ('X60797994','$2a$12$h8CqS72QUacazXMKFRSiAef6dmef3f8BU/xzkmO4FjvIjSrgOYL6W', 'Steven','Brook', 3); -- pw:password2 
insert into employee values ('X03981491','$2a$12$MyAcx3aEIidsoOxvP3VC5OIxAnqm5AaDO6ke8w8H0MiHbOWOzs0IS','Victor','Gooberson',3); -- pw:password3
insert into employee values ('X07849003','$2a$12$oKCrWF8EYpxFoklyQsGF1eJ5ca9QnvRb8IfkiYUFmn1aOqBySSIu2','Madison','Lin',3); -- pw:password4
insert into employee values ('X03194855','$2a$12$wAcocfKbzD/czS0oXapkuOIfSXqE1/wBfJ7g1JMNyA6pwRWB7IQFa','John','Smith',3); -- pw:password5
insert into employee values ('X08958482','$2a$12$nfG3ppWDtTMkHYbuasUTWuv3xfrv4jJo/jd7PfEn4Qa6DOX2JrR7O','Morgan','Jones',3); -- pw:password6
insert into employee values ('X04719273','$2a$12$1S2TAUDLglrEDm6y/5aXcO9uASSAGYP391AUm.vvDxSIb/DrKrDca','Winston','Nelson',3); -- pw:password7
insert into employee values ('X08585874','$2a$12$GYmiRzxzHVR2VXhsN2WPT.ds/YJESXfndc1IgstpvOZNhGa9.JhL2','Quinn', 'Smith',3); -- pw:short / person with pcard
insert into employee values ('X05740130','$2a$12$xXNq2vG6LShWznzTdwyA7.iEqvgHu0A19wkaWZdGx71yIb8A88Vc6','Myrel','Fluros',3); -- pw:password8
insert into employee values ('X06887558','$2a$12$LxmxTx7GbDNWLTSyaJNK/Or/sCeV0qAQXm/sAZKOXM65RJuvT7q1a','Gunnar','Joeson',3); -- pw:password9

-- create expenses (10)
insert into expense(employeeId,approvalStatus,amount,expenseType,date,memo,cardType,cardNumber,approverId) values('X02900569','Approved',145.20,'Food','2024-04-01','Pizza Party for Club Event','Credit Card','0129391212344321','X02900569');
insert into expense(employeeId,approvalStatus,amount,expenseType,date,memo,cardType,cardNumber,approverId) values('X02900569','Pending',500.55,'Development','2023-09-15','Expense for purchasing supplies','PCard','1223455678890112','X02900569');
insert into expense(employeeId,approvalStatus,amount,expenseType,date,memo,cardType,cardNumber,approverId) values('X03029305','Returned',100.15,'Transportation','2021-10-30','Plane trip for Networking Convention','PCard','8475192010295647','X02900569');
insert into expense(employeeId,approvalStatus,amount,expenseType,date,memo,cardType,cardNumber,approverId) values('X03981491','Pending',65.11,'Food','2024-04-01','Catering for Event','PCard','1928475869571029','X02900569');
insert into expense(employeeId,approvalStatus,amount,expenseType,date,memo,cardType,cardNumber,approverId) values('X03891832','Approved',77.07,'Transportation','2020-10-20','Gas for Work Car','Credit Card','6677839422210998','X05687921');
insert into expense(employeeId,approvalStatus,amount,expenseType,date,memo,cardType,cardNumber,approverId) values('X04719273','Approved',81.33,'Supplies','2023-12-23','Reasonable Reason','Credit Card','1234567890123456','X05687921');
insert into expense(employeeId,approvalStatus,amount,expenseType,date,memo,cardType,cardNumber,approverId) values('X05198402','Returned',199.99,'Other','2024-03-23','Unreasonable Reason','PCard','0987654321098765','X06182031');
insert into expense(employeeId,approvalStatus,amount,expenseType,date,memo,cardType,cardNumber,approverId) values('X05687921','Pending',200.45,'Food','2023-12-04','Reasonable Reason','Credit Card','6554778776763221','X06182031');
insert into expense(employeeId,approvalStatus,amount,expenseType,date,memo,cardType,cardNumber,approverId) values('X03891832','Approved',444.12,'Food','2023-12-05','Catering for Event','Credit Card','4545233498860112','X03981491');
insert into expense(employeeId,approvalStatus,amount,expenseType,date,memo,cardType,cardNumber,approverId) values('X04518293','Returned',550.00,'Supplies','2025-09-12','Reasonable Reason','PCard','5667677878898990','X05740129');

-- create message (2)
insert into message(date,headerText,bodyText,creatorId) values('2023-03-24', 'Hello World!', 'Test Message','X02900569');
insert into message(date,headerText,bodyText,creatorId) values('2023-03-25', 'Welcome to the Expense Tracker System', 'Bottom Text','X09836478');

insert into message(date,headerText,bodyText,creatorId) values ('2023-03-26','Message for Sally','This is a message for Sally only','X09836478'); -- message that only Sally should see
-- create Pcard (5)
insert into pcard values('1234123412341234','X02873918',100,2000); 
insert into pcard values('1010203454321023','X06887558',250,1000);
insert into pcard values('1928301388491831','X01984579',200,700);
insert into pcard values('8292810391840482','X05740129',300,700);
insert into pcard values('7491038108308302','X08585874',500,1000);

-- create policies

insert into policy(policyName,policyAmount) values('University Policy 2023-2024',1000);
insert into policy(policyName,policyAmount) values('University Policy 2024-2025',1250);

insert into policy(policyName,policyAmount) values('Department Policy 2022-2023',350);
insert into policy(policyName,policyAmount) values('Department Policy 2023-2024',300);
insert into policy(policyName,policyAmount) values('Department Policy 2024-2025',330);


-- create relations (Budget Admin -> First person of each department)
insert into budgets_for values('X02900569',1);
insert into budgets_for values('X05687921',2);
insert into budgets_for values('X07691039',3);

insert into approves_expense values('X05687921',1,'2024-04-03','Approved. -Eddie');
insert into approves_expense values('X07691039',3,'2021-11-01','Denied. Please send to appropriate budget admin. -Billy');
insert into approves_expense values('X04518293',5,'2020-11-01','Approved. -Nick');
insert into approves_expense values('X03194855',6,'2024-01-05','Approved. -John Smith');
insert into approves_expense values('X04518293',7,'2024-03-25','Denied. Please refer to list of acceptable expenses. -Nick');
insert into approves_expense values('X04518293',9,'2021-12-06','Approved. -Nick');
insert into approves_expense values('X07691039',10,'2025-10-31','Denied. Please send this request to Myrel from Sociology - Billy');

insert into employee_views_message values('X02900569',1,0);
insert into employee_views_message values('X02900569',2,1);
insert into employee_views_message values('X02873918',1,0);
insert into employee_views_message values('X02873918',2,0);
insert into employee_views_message values('X05687921',1,1);
insert into employee_views_message values('X05687921',2,1);
insert into employee_views_message values('X01984579',1,1);
insert into employee_views_message values('X01984579',2,0);
insert into employee_views_message values('X01930293',1,0);
insert into employee_views_message values('X01930293',2,1);
insert into employee_views_message values('X01939582',1,1);
insert into employee_views_message values('X01939582',2,1);
insert into employee_views_message values('X04518293',1,0);
insert into employee_views_message values('X04518293',2,1);
insert into employee_views_message values('X03891832',1,1);
insert into employee_views_message values('X03891832',2,0);
insert into employee_views_message values('X05198402',1,0);
insert into employee_views_message values('X05198402',2,1);
insert into employee_views_message values('X07524843',1,1);
insert into employee_views_message values('X07524843',2,1);
insert into employee_views_message values('X05910492',1,1);
insert into employee_views_message values('X05910492',2,0);
insert into employee_views_message values('X07539763',1,1);
insert into employee_views_message values('X07539763',2,1);
insert into employee_views_message values('X06182031',1,1);
insert into employee_views_message values('X06182031',2,0);
insert into employee_views_message values('X06597342',1,0);
insert into employee_views_message values('X06597342',2,1);
insert into employee_views_message values('X05791230',1,1);
insert into employee_views_message values('X05791230',2,0);
insert into employee_views_message values('X08545365',1,0);
insert into employee_views_message values('X08545365',2,0);
insert into employee_views_message values('X03217321',1,1);
insert into employee_views_message values('X03217321',2,1);
insert into employee_views_message values('X05740129',1,0);
insert into employee_views_message values('X05740129',2,1);
insert into employee_views_message values('X03029305',1,0);
insert into employee_views_message values('X03029305',2,1);
insert into employee_views_message values('X09836478',1,1);
insert into employee_views_message values('X09836478',2,0);
insert into employee_views_message values('X07691039',1,0);
insert into employee_views_message values('X07691039',2,0);
insert into employee_views_message values('X60797994',1,1);
insert into employee_views_message values('X60797994',2,0);
insert into employee_views_message values('X03981491',1,1);
insert into employee_views_message values('X03981491',2,1);
insert into employee_views_message values('X07849003',1,1);
insert into employee_views_message values('X07849003',2,0);
insert into employee_views_message values('X03194855',1,0);
insert into employee_views_message values('X03194855',2,0);
insert into employee_views_message values('X08958482',1,0);
insert into employee_views_message values('X08958482',2,1);
insert into employee_views_message values('X04719273',1,1);
insert into employee_views_message values('X04719273',2,0);
insert into employee_views_message values('X08585874',1,0);
insert into employee_views_message values('X08585874',2,0);
insert into employee_views_message values('X05740130',1,1);
insert into employee_views_message values('X05740130',2,1);
insert into employee_views_message values('X06887558',1,1);
insert into employee_views_message values('X06887558',2,0);

insert into employee_views_message values ('X08545365',3,0); -- message for Sally 
-- second of each department
insert into heads values('X02873918',1);
insert into heads values('X07539763',2);
insert into heads values('X07691039',3);

-- system admin (can create new accounts) (Jearmy Lou)
insert into administrates values ('X09836478');

-- p card admin
insert into administrates_pcard values ('X01984579');


insert into university_holds_policy values (1,1);
insert into university_holds_policy values (1,2);

insert into department_holds_policy values (1,3);
insert into department_holds_policy values (2,4);
insert into department_holds_policy values (3,5);

insert into university_selects_policy values (1,1);

insert into department_selects_policy values (1,3);
insert into department_selects_policy values (2,4);
insert into department_selects_policy values (3,5);

insert into employee_requests_pcard(requesterId, employeeId, memo, dailyLimit, monthlyLimit, pCardAdminId,date,approvalStatus) values ('X02900569', 'X05687921','Give me pcard plz',150,500,'X01984579','2024-02-24','Pending');
insert into employee_requests_pcard(requesterId, employeeId, memo, dailyLimit, monthlyLimit, pCardAdminId,date,approvalStatus) values ('X05687921', 'X03891832','I love PCards',100,450,'X01984579','2023-01-23','Pending');

