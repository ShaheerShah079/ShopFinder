create schema ShopFinder;

use ShopFinder;
create table Users(UserId int primary key not null auto_increment,UserFName varchar(20) not null,UserLName varchar(20) not null, UserCellNo varchar(20) unique, UserEmail varchar(45) not null unique,
 UserPassword Varchar(15) not null);
 
create table Shopkeepers(ShopkeepersId int primary key not null auto_increment,ShopkeepersFName varchar(25) not null,ShopkeepersLName varchar(20) not null ,ShopkeepersCellNo varchar(20) unique, 
ShopkeepersCnic varchar(20) unique ,ShopkeepersEmail varchar(45) not null unique, ShopkeepersPassword Varchar(15) not null);
 
create table VisitingPlaces(PlaceId int primary key not null auto_increment,PlaceName mediumtext not null,location mediumtext not null unique , 
City varchar(20),AboutPlace mediumtext default'',PlaceType Varchar(15) not null);

create table Shops(ShopsId int primary key not null auto_increment,Shopsname varchar(45) not null, ShopsNum Varchar(5),ShopsStreet varchar(15), ShopsCity varchar(20),
ShopsArea mediumtext,ShopsType Varchar(15) not null, ShopsRating decimal default 0, ShopkeepersId int,PlaceId int,
constraint Shopkepers_shops_fk foreign key(ShopkeepersId) references Shopkeepers(ShopkeepersId),
constraint VisitingPlaces_shops_fk foreign key(PlaceId) references VisitingPlaces(PlaceId));


create table includes(UserId int ,ShopsId int ,constraint usersincludes_fk foreign key(UserID) references Users(UserId), 
constraint Shopsincludes_fk foreign key(ShopsID) references Shops(ShopsId), constraint primary key(UserId,ShopsId));

create table Viewing(ShopsId int,UserId int,comments varchar(100) default'', ViewDate Date not null default(CURRENT_DATE),constraint Userview_fk foreign key(UserId) references Users(UserId)
,constraint Shopsview_fk foreign key(ShopsId) references Shops(ShopsId),constraint primary key(UserId,ShopsId));

alter table users auto_increment=1;
insert into users(UserFname,UserLname,UserCellNo,UserEmail,UserPassword) values('Shaheer','Shah',03493403757,'shaheerali@gmail.com','shaheer1234'),
('Zain','Shah',03186735128,'zainshah@gmail.com','zain34'),('Umair','Shah',03316745631,'umairali@gmail.com','umair789'),('Quandeel','Shah',03247654381,'quandeel321@gmail.com','quandeel321'),
('Ali','Hassan',03483463741,'hassanali@gmail.com','hasssan'),('Danish','Ali',03325681254,'Danish4321@gmail.com','danish1234'),
('Zaviyar','Ahsan',03312398432,'zaviahsan123@gmail.com','zaviyarfc');
select * from users;

alter table Shopkeepers auto_increment=1;
insert into Shopkeepers(ShopkeepersFname,ShopkeepersLname,ShopkeepersCellNo,ShopkeepersCnic,ShopkeepersEmail,ShopkeepersPassword) values
('Zariyab','khan',03127644384,'35401-1800504-7','zaryabalikan213@gmail.com','zaryab456'),('Abu','Huraira',03485762344,'35201-4779832-3','harrykhan654@gmail.com','iamharry'),
('Junaid','Ahmed',033317589753,'274-88-293789','ahmedjunaid4126@gmail.com','jadii123'),('Azwar','hussain',032765431864,'35202-0432509-1','azwar0976@gmail.com','123456'),
('Saeed','Ahmed',03483463741,'35202-7313316-8','saeedali@gmail.com','password'),('Ibrahim','Alha',03325681254,'267-91-486112','ibrahim4321@gmail.com','qwerty123'),
('Sana','Javed',03312398432,'71103-2474952-3','Sanaahsan123@gmail.com','1q2w3e'),('JALIL','ASGHER',03312367532,'41133-34744942-1','jalilasghar223@gmail.com','1q2w3e');
 select * from shopkeepers;

alter table visitingplaces auto_increment=1;
insert into visitingplaces(PlaceName,location,City,AboutPlace,PlaceType) values 
('Anarkali','Near Mall road','Lahore','Anarkali was the nickname given to a legendary courtesan, Nadira Begum who was said to be the love interest of the 16th-century Mughal prince Salim, who later became the Emperor Jahangir','Bazar');
insert into visitingplaces(PlaceName,location,City,AboutPlace,PlaceType) values 
('Emporium mall','16M Abdul Haque Rd, Trade Centre Commercial Area Phase 2 Johar Town','Lahore','Emporium Mall (Urdu: امپوریم خریداری مرکز) is a shopping mall located in Johar Town, Lahore southwest of Lahore International Expo Centre.','Shopping Mall');
insert into visitingplaces(PlaceName,location,City,AboutPlace,PlaceType) values 
('Penorama','Shahrah-e-Quaid-e-Azam, G.O.R. - I','Lahore','Panorama Shopping Centre offers verity of clothes for men to choose from on two basement floor. Panorama Shopping Center is also having number of shops on ground floor having beautiful yellow gold jewellry for females.','Shopping Mall');
insert into visitingplaces(PlaceName,location,City,AboutPlace,PlaceType) values 
('Gol Bagh','Sarwar Rd shad bagh','Lahore','Gol Bagh, Saddar, Lahore Cantt. is one of the top rated place listed as Park in Lahore','Bazar');
insert into visitingplaces(PlaceName,location,City,AboutPlace,PlaceType) values 
('Food Street','Dyal Singh Krishan Bazar near meo hospital','Lahore','','Bazar');
insert into visitingplaces(PlaceName,location,City,AboutPlace,PlaceType) values 
('Food Street','Badshahi Mosque,Shahi Mohallah Walled City','Lahore','It is a street located in the area known as Old Lahore, right next to the world famous Badshahi Mosque. Especially after 20.00 in the evening, you can take postcard photos with authentic lighting. There are many restaurants on the street. You will not miss the magnificent view of the Badshahi Mosque','Bazar');
insert into visitingplaces(PlaceName,location,City,AboutPlace,PlaceType) values 
('Nelum Cenama','','Lahore','','Bazar');
insert into visitingplaces(PlaceName,location,City,AboutPlace,PlaceType) values 
('Jadoon plaza phase 1','opposite army burnhall college, mandian, Abbottabad','Abbottabad','','Shooping mall');
insert into visitingplaces(PlaceName,location,City,AboutPlace,PlaceType) values 
('Jadoon plaza phase 3','Jinnahabad Abbottabad','Abbottabad','Newly developed ,every thing can be found in one place ,branded clothing imported cosmetic ,fancy jewelry, mobile shops and quality food','Shooping mall');
insert into visitingplaces(PlaceName,location,City,AboutPlace,PlaceType) values 
('awan plaza phase 2','56VM+3V7, Akash Rabbani Shaheed Colony','Abbottabad','Best Place & Safe Area With Open Car Parking Area Available','Shooping mall');
select * from visitingplaces;

alter table shops auto_increment=1;
insert into shops(Shopsname,ShopsNum,ShopsStreet,ShopsArea,ShopsCity,ShopsType,ShopkeepersId,PlaceId) values
('Greed Land','21A','23','Mall Road','Lahore','Garments',2,1),('AIDEAONE Men','31c','11','Mall Road','Lahore','Garments',4,1),('Bata','43A','23','Johar Town','Lahore','Shoes',7,2),
('Service','67Z','43','Mall Road','Lahore','Shoes',8,3),('Amratsari Nehari','7S','32','Meo hospital','Lahore','Foods',1,5),('Khan Shawarma','64','32','Shad Bagh','Lahore','Fast Food',5,4),
('Jawa','2','31','Shad Bagh','Lahore','Sweets',3,4),('Riaz Icecream','21S','15','Shad bagh','Lahore','Icecream',6,4),('Bata','7Q','3','Nelum Cenama','Lahore','shoes',7,7),
('Amratsari Nehari','43','2','Waleed city','Lahore','Foods',1,6),('Riaz Icecream','2S','53','Mall road','Lahore','Icecream',6,3);
insert into shops(Shopsname,ShopsNum,ShopsStreet,ShopsArea,ShopsCity,ShopsType,ShopkeepersId,PlaceId) values
('Greed Land','2A','N-35','Jaddon Plaza phase 1','Abbotabad','Garments',2,8),('Bata','43A','N-35','Jadddon plaza phase 1','Abbotabad','Shoes',7,8),
('Baby & Childrens Clothing Store','7A','N-35','Jadddon plaza phase 3','Abbotabad','Garments',4,10),('Captain cook','71','N-35','Jadddon plaza phase 1','Abbotabad','Foods',1,8),
('Hot and Spicy','6L','N-35','Jadddon plaza phase 3','Abbotabad','Fast Food',5,10),('Chaman Icecream','2S','N-35','Awan plaza, phase 2','Abbotabad','Icecream',6,9),
('K&Ns','3','N-35','Jaddon Plaza phase 1','Abbotabad','Foods',1,8);
select * from shops;

insert into includes(userid,shopsid) values(1,1),(1,3),(1,5),(1,7),(1,11),(1,10),(2,5),(2,1),(2,7),(2,3),(2,6),(3,5),(3,4),(3,6),(3,1),(4,12),(4,15),(4,17),(4,18)
,(5,1),(5,8),(5,9),(5,6),(6,14),(6,13),(6,17);
select * from includes;


insert into viewing(ShopsId,UserId,comments) values(12,1,'perfect dress and such lovely service');
insert into viewing(ShopsId,UserId,comments) values(5,7,' healthy food'),(5,1,'price expensives'),(8,1,''),(7,1,'fresh sweets'),(15,6,''),(2,5,'sweater is pretty great'),
(15,3,'Food and beverages served at incorrect temperatures'),(16,4,'Tastes great!'),(14,1,'');
select * from viewing;

