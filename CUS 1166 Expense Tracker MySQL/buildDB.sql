-- check if preexisting db
DROP SCHEMA IF EXISTS `expense_tracker_db`;
CREATE SCHEMA `expense_tracker_db`;

use `expense_tracker_db`;

-- create entities
DROP TABLE IF EXISTS `Employee`;
create table Employee(employeeId char(9) primary key not null, 
					  password char(60),
                      fname varchar(20),
                      lname varchar(20),
                      departmentId int);

DROP TABLE IF EXISTS `University`;                      
create table University(universityId int primary key not null,
						universityName varchar(30) not null);
DROP TABLE IF EXISTS `Department`;                        
create table Department(departmentId int not null,
						universityId int not null,
                        dname varchar(60),
                        primary key (departmentId, universityId));
DROP TABLE IF EXISTS `Expense`;                        
create table Expense(expenseId int primary key auto_increment,
					 employeeId char(9),
					 approvalStatus varchar(15), 
                     amount double,
                     expenseType varchar(25),
                     date Date,
                     memo varchar(255),
                     cardType varchar(15),
                     cardNumber char(16), 
                     approverId char(9));
DROP TABLE IF EXISTS `PCard`;
create table PCard(PCardId char(16) primary key not null,
				   employeeId char(9),
                   dailyLimit double,
                   monthlyLimit double);
DROP TABLE IF EXISTS `Message`;                   
create table Message(messageId int primary key auto_increment,
					 date Date,
                     headerText varchar(60),
                     bodyText varchar(255),
                     creatorId char(9));
                     
DROP TABLE IF EXISTS `policy`;
create table Policy(policyId int primary key auto_increment,
				    policyName varchar(30),
					policyAmount double);
-- alter tables to add foreign keys                     
alter table Employee
					add foreign key (departmentId) 
                    references Department(departmentId);
                    
alter table Department
					add foreign key (universityId)
                    references University(universityId);

alter table Message
					add foreign key (creatorId)
                    references Employee(employeeId);
                    
alter table Expense
					add foreign key (employeeId)
                    references Employee(employeeId),
                    
					add foreign key (approverId)
                    references Employee(employeeId);
-- create tables for relationships    
DROP TABLE IF EXISTS `budgets_for`;                
create table budgets_for (employeeId char(9) not null, 
						  departmentId int not null,
                          foreign key (employeeId) references Employee(employeeId),
                          foreign key (departmentId) references Department(departmentId),
                          primary key(employeeId, departmentId));
                          
DROP TABLE IF EXISTS `heads`;                          
create table heads (employeeId char(9) not null, 
					departmentId int not null,
					foreign key (employeeId) references Employee(employeeId),
					foreign key (departmentId) references Department(departmentId),
					primary key(employeeId, departmentId));
DROP TABLE IF EXISTS `approves_expense`;
create table approves_expense(employeeId char(9) not null,
					  expenseId int not null,
                      date Date,
                      comment varchar(255),
                      foreign key (employeeId) references Employee(employeeId),
                      foreign key (expenseId) references Expense(expenseId),
                      primary key (employeeId, expenseId));
                     
DROP TABLE IF EXISTS `employee_views_message`;                     
create table employee_views_message(employeeId char(9) not null,
									messageId int not null,
                                    viewStatus boolean,
                                    foreign key (employeeId) references Employee(employeeId),
                                    foreign key (messageId) references Message(messageId),
                                    primary key (employeeId,messageId));
DROP TABLE IF EXISTS `administrates`;
create table administrates(employeeId char(9) primary key not null,
						   foreign key (employeeId) references Employee(employeeId)); 

DROP TABLE IF EXISTS `administrates_pcard`;
create table administrates_pcard(employeeId char(9) primary key not null,
						   foreign key (employeeId) references Employee(employeeId)); 
                           
DROP TABLE IF EXISTS `employee_requests_pcard`;
create table employee_requests_pcard(requestId int primary key auto_increment not null,
									 requesterId char(9) not null, -- one who is requesting pcard
									 employeeId char(9) not null, -- one who is getting pcard
									 memo varchar(255), 
                                     dailyLimit double,
                                     monthlyLimit double,
                                     pCardAdminId char(9),
                                     date date,
                                     approvalStatus varchar(15),
                                     
                                     foreign key (requesterId) references Employee(employeeId),
                                     foreign key (employeeId) references Employee(employeeId),
                                     foreign key (pCardAdminId) references administrates_pcard(employeeId));
                                     
DROP TABLE IF EXISTS `approves_pcard`;
create table approves_pcard (approverId char(9) not null,
							 requestId int not null, 
							 date Date,
                             comment varchar(255),
                             primary key (approverId,requestId),
                             foreign key (approverId) references employee(employeeId),
                             foreign key (requestId) references employee_requests_pcard(requestId));
        

DROP TABLE IF EXISTS `university_holds_policy`;
create table university_holds_policy(universityId int, 
									 policyId int,
                                     
                                     primary key (universityId,policyId),
                                     foreign key (universityId) references University(universityId),
                                     foreign key (policyId) references Policy(policyId));
DROP TABLE IF EXISTS `department_holds_policy`;
create table department_holds_policy(departmentId int, 
									 policyId int,
                                     primary key (departmentId,policyId),
                                     foreign key (departmentId) references Department(departmentId),
                                     foreign key (policyId) references Policy(policyId));						

DROP TABLE IF EXISTS `university_selects_policy`;
create table university_selects_policy(universityId int primary key not null, policyId int,
										foreign key (universityId) references University(universityId),
                                        foreign key (policyId) references Policy(policyId)); 									
DROP TABLE IF EXISTS `department_selects_policy`;
create table department_selects_policy(departmentId int primary key not null, policyId int,
										foreign key (departmentId) references Department(departmentId),
                                        foreign key (policyId) references Policy(policyId)); 
                           

	

			
