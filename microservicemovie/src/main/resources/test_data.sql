insert into movie (movie_name,director,main_actor,duration_min,description)
 values ('Venom','Ruben Fleischer','Todd McFarlane',107,'蜘蛛侠最强劲敌“毒液”强势来袭！');
insert into movie (movie_name,director,main_actor,duration_min,description)
 values ('无名之辈','饶晓志','陈建斌',108,'在一座山间小城中，一对低配劫匪、一个落魄的泼皮保安、一个身体残疾却性格彪悍的残毒舌女以及一系列生活在社会不同轨迹上的小人物。');
insert into movie_hall (seat_number,description)
 values (80,'hall of 3D');
insert into movie_hall (seat_number,description)
 values (42,'hall of 2D');
insert into movie_seat (hall_id,seat_row,seat_col,seat_isactive)
 values (1,5,6,true);
insert into movie_seat (hall_id,seat_row,seat_col,seat_isactive)
 values (1,5,7,true);
insert into movie_seat (hall_id,seat_row,seat_col,seat_isactive)
 values (2,6,1,false);
insert into movie_schedule (movie_id,hall_id,schedule_price,schedule_begindatetime)
 values (1,1,12.5,'2018-11-30 22:22:22');