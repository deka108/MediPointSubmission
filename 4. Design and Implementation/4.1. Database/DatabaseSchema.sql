BEGIN TRANSACTION;
CREATE TABLE specialty (specialtyId INTEGER PRIMARY KEY AUTOINCREMENT,specialtyName VARCHAR(30) );
INSERT INTO `specialty` VALUES (1,'ENT');
INSERT INTO `specialty` VALUES (2,'Dental Services');
INSERT INTO `specialty` VALUES (3,'Women''s Health');
INSERT INTO `specialty` VALUES (4,'General Medicine');
CREATE TABLE service (serviceId INTEGER PRIMARY KEY AUTOINCREMENT,specialtyName VARCHAR(30),specialtyId INTEGER,serviceDuration INTEGER,preAppointmentActions TEXT, FOREIGN KEY(specialtyId) REFERENCES specialty(specialtyId) );
INSERT INTO `service` VALUES (1,'General',1,1,'None');
INSERT INTO `service` VALUES (2,'Periodic ENT',1,1,'None');
INSERT INTO `service` VALUES (3,'OSA',1,2,'1. Sleep Diary
2.Avoid Alcohol');
INSERT INTO `service` VALUES (4,'Otology',1,4,'None');
INSERT INTO `service` VALUES (5,'Routine Scaling',2,1,'None');
INSERT INTO `service` VALUES (6,'Polishing',2,2,'None');
INSERT INTO `service` VALUES (7,'Fillings',2,2,'None');
INSERT INTO `service` VALUES (8,'Tooth Extraction',2,4,'1.X-Ray of Tooth');
INSERT INTO `service` VALUES (9,'Root Canal',2,6,'None');
INSERT INTO `service` VALUES (10,'Gynecologists',3,2,'1.Avoid Sex the Night Before
2.Urine Test');
INSERT INTO `service` VALUES (11,'Obstetrician',3,2,'None');
INSERT INTO `service` VALUES (12,'Dietetic Services',4,2,'None');
INSERT INTO `service` VALUES (13,'Physiotherapy',4,2,'None');
INSERT INTO `service` VALUES (14,'Child Care',4,2,'None');
INSERT INTO `service` VALUES (15,'Chronic care',4,6,'None');
CREATE TABLE patient (patientId INTEGER PRIMARY KEY,dob LONG,age INTEGER,medicalHistory TEXT,allergies TEXT,treatments TEXT,medications TEXT, FOREIGN KEY(patientId) REFERENCES account(accountId) );
INSERT INTO `patient` VALUES (2,1429277193324,0,'Bleeding Gums
Had Braces Treatment
Teeth Sensitive to Hot, Cold
Heart Diseases
','Other : adsadsa
','asdsad','sadsad');
INSERT INTO `patient` VALUES (3,811172022027,19,'Had Braces Treatment
Teeth Sensitive to Hot, Cold
','Other : allergy
','braces control','');
CREATE TABLE doctorSchedule (doctorScheduleId INTEGER PRIMARY KEY AUTOINCREMENT,doctorId INTEGER,clinicId INTEGER,day VARCHAR(10),startTime LONG,endTime LONG, FOREIGN KEY(doctorId) REFERENCES doctor(doctorId), FOREIGN KEY(clinicId) REFERENCES clinic(clinicId));
CREATE TABLE doctor (doctorId INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(30),specializationId INTEGER,practiceDuration INTEGER,clinicId INTEGER, FOREIGN KEY(specializationId) REFERENCES specialty(specialtyId) FOREIGN KEY(clinicId) REFERENCES clinic(clinicId) );
INSERT INTO `doctor` VALUES (1,'Dr. Stefan',1,2,1);
INSERT INTO `doctor` VALUES (2,'Dr. Zillion',2,3,1);
INSERT INTO `doctor` VALUES (3,'Dr. Deka',3,4,1);
INSERT INTO `doctor` VALUES (4,'Dr. Ankur',1,5,1);
INSERT INTO `doctor` VALUES (5,'Dr. Aristo',4,6,1);
INSERT INTO `doctor` VALUES (6,'Dr. Shreyas',2,4,1);
INSERT INTO `doctor` VALUES (7,'Dr. Raul',1,2,2);
INSERT INTO `doctor` VALUES (8,'Dr. John',2,3,2);
INSERT INTO `doctor` VALUES (9,'Dr. Alice',3,4,2);
INSERT INTO `doctor` VALUES (10,'Dr. Noopur',1,5,2);
INSERT INTO `doctor` VALUES (11,'Dr. Stuart',4,6,2);
INSERT INTO `doctor` VALUES (12,'Dr. Sanjana',2,4,2);
INSERT INTO `doctor` VALUES (13,'Dr. Siddharth',1,2,3);
INSERT INTO `doctor` VALUES (14,'Dr. Divesh',2,3,3);
INSERT INTO `doctor` VALUES (15,'Dr. Loh Wao',3,4,3);
INSERT INTO `doctor` VALUES (16,'Dr. Annie',1,5,3);
INSERT INTO `doctor` VALUES (17,'Dr. Andy',4,6,3);
INSERT INTO `doctor` VALUES (18,'Dr. Isabelle',2,4,3);
INSERT INTO `doctor` VALUES (19,'Dr. Joshua',1,2,4);
INSERT INTO `doctor` VALUES (20,'Dr. Kevin',2,3,4);
INSERT INTO `doctor` VALUES (21,'Dr. Mark',3,4,4);
INSERT INTO `doctor` VALUES (22,'Dr. Peter',1,5,4);
INSERT INTO `doctor` VALUES (23,'Dr. Arya',4,6,4);
INSERT INTO `doctor` VALUES (24,'Dr. Bingsheng',2,4,4);
INSERT INTO `doctor` VALUES (25,'Dr. Samantha',1,2,5);
INSERT INTO `doctor` VALUES (26,'Dr. Akash',2,3,5);
INSERT INTO `doctor` VALUES (27,'Dr. Sandeep',3,4,5);
INSERT INTO `doctor` VALUES (28,'Dr. Robert',1,5,5);
INSERT INTO `doctor` VALUES (29,'Dr. Limanto',4,6,5);
INSERT INTO `doctor` VALUES (30,'Dr. Chandra',2,4,5);
INSERT INTO `doctor` VALUES (31,'Dr. Ava',1,2,6);
INSERT INTO `doctor` VALUES (32,'Dr. Jessica',2,3,6);
INSERT INTO `doctor` VALUES (33,'Dr. Harvey',3,4,6);
INSERT INTO `doctor` VALUES (34,'Dr. Donna',1,5,6);
INSERT INTO `doctor` VALUES (35,'Dr. Rachael',4,6,6);
INSERT INTO `doctor` VALUES (36,'Dr. Dana',2,4,6);
CREATE TABLE clinic (clinicId INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(30),address VARCHAR(50),country INTEGER,zipCode VARCHAR(10),telNumber VARCHAR(10),faxNumber VARCHAR(10),email VARCHAR(30) );
INSERT INTO `clinic` VALUES (1,'DjZass Boonlay Care','Boonlay Way 123','Singapore','612345','655512','655513','djzass.boonlay@medipoint.com');
INSERT INTO `clinic` VALUES (2,'DjZass Changi Clinic','Changi Street 321','Singapore','654321','655521','655531','djzass.changi@medipoint.com');
INSERT INTO `clinic` VALUES (3,'DjZass Kuala Lumpur Care','Syah Ali Road 123','Malaysia','712345','755512','755513','djzass.kl@medipoint.com');
INSERT INTO `clinic` VALUES (4,'DjZass Johor Baru Clinic','Jalan Syed Baro 321','Malaysia','754321','755521','755531','djzass.jb@medipoint.com');
INSERT INTO `clinic` VALUES (5,'DjZass Bangkok Care','Bangkok St. 123','Thailand','812345','855512','855513','djzass.bangkok@medipoint.com');
INSERT INTO `clinic` VALUES (6,'DjZass Krabi Clinic','City Square 321','Thailand','854321','855521','855531','djzass.krabi@medipoint.com');
CREATE TABLE appointment (appointmentId INTEGER PRIMARY KEY AUTOINCREMENT,clinicId INTEGER,patientId INTEGER,doctorId INTEGER,referrerId INTEGER,dateTime LONG,service INTEGER,specialty INTEGER,preAppointmentActions TEXT,startTime INTEGER,endTime INTEGER, FOREIGN KEY(clinicId) REFERENCES clinic(clinicId), FOREIGN KEY(patientId) REFERENCES patient(patientId), FOREIGN KEY(doctorId) REFERENCES doctor(doctorId), FOREIGN KEY(service) REFERENCES service(serviceId) );
INSERT INTO `appointment` VALUES (1,1,2,1,-1,1429455633705,1,1,'None',22,23);
INSERT INTO `appointment` VALUES (7,1,3,1,1,1429630230273,1,1,'None',23,24);
INSERT INTO `appointment` VALUES (8,4,3,23,-1,1429709456703,12,4,'None',19,21);
CREATE TABLE android_metadata (locale TEXT);
INSERT INTO `android_metadata` VALUES ('en_US');
CREATE TABLE account (accountId INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(50),nric CHAR(10),email VARCHAR(30),contactNo VARCHAR(10),address VARCHAR(50),dob LONG,gender CHAR(1),maritalStatus VARCHAR(10),citizenship VARCHAR(30),countryOfResidence VARCHAR(30),username VARCHAR(30),password VARCHAR(30),notifyEmail INTEGER,notifySms INTEGER );
INSERT INTO `account` VALUES (1,'Test','A142049','deka108@gmail.com','82342891','NTU',791614800000,'Female','Single','Singaporean','Singapore','test','123',1,1);
INSERT INTO `account` VALUES (2,'a','123453454','a@gmail.com','22321321','a',1429277193324,'Male','Single','android.widget.Spinner{b16d5268 VFED..C. ........ 48,212-1032,362 #7f0a008f app:id/CitizenshipSpinner}','android.widget.Spinner{b1715968 VFED..C. ........ 48,490-1032,640 #7f0a0091 app:id/CountryOfResidenceSpinner}','a1234','a1234',1,1);
INSERT INTO `account` VALUES (3,'Zillion Govin','U1320174E','zillion.govin@gmail.com','83861645','NTU Hall 1',811172022027,'Male','Single','android.widget.Spinner{b17e90e8 VFED..C. ........ 48,212-1032,362 #7f0a008f app:id/CitizenshipSpinner}','android.widget.Spinner{b187e888 VFED..C. ........ 48,490-1032,640 #7f0a0091 app:id/CountryOfResidenceSpinner}','zillion','1234',1,1);
COMMIT;
