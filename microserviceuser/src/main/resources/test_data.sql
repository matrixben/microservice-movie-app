insert into user (username,realname,age,user_email,class_id,user_password) values ('user1','jason',99,jason@jason.luo,1,123456);
insert into user (username,realname,age,user_email,class_id,user_password) values ('user2','ben',9,jason@jason.luo,1,123456);
insert into user (username,realname,age,user_email,class_id,user_password) values ('user3','萝卜',29,354164737@qq.com,2,123456);
insert into user_class (class_name,class_discount,class_isactive) values ('入门',0.9,true);
insert into user_class (class_name,class_discount,class_isactive) values ('进阶',0.8,true);
insert into user_class (class_name,class_discount,class_isactive) values ('狂热',0.5,false);
insert into login_role (role_name,description) values ('用户','电影网站的使用者');
insert into login_role (role_name,description) values ('管理员','电影网站的管理者');

insert into user_role (user_id,role_id) values (1,2);
insert into user_role (user_id,role_id) values (2,1);
insert into user_role (user_id,role_id) values (3,1);