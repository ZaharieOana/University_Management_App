use proiect_2max;

call createStudent(1,1,"st1","prenumest1","adresast1","0770000001","st1","ing401",1,"");#id = 1
call createStudent(1,2,"st2","prenumest2","adresast2","0770000002","st2","ing402",2,"");#id = 2
call createStudent(1,3,"st3","prenumest3","adresast3","0770000003","st3","ing403",3,"");#id = 3
call createStudent(1,4,"st4","prenumest4","adresast4","0770000004","st4","ing404",4,"");#id = 4
call createStudent(1,5,"st5","prenumest5","adresast5","0770000005","st5","ing405",5,"");#id = 5
call createStudent(1,6,"st6","prenumest6","adresast6","0770000006","st6","ing406",6,"");#id = 6
call createStudent(3,7,"st7","prenumest7","adresast7","0770000007","st7","ing407",7,"");#id = 7
call createStudent(4,8,"st8","prenumest8","adresast8","0770000008","st8","ing408",8,"");#id = 8

call createProfesor(0,0,"dep1",11,"pr1","prenumepr1","adresapr1","0770000011","pr1","bt4001",1,"");#id = 9
call createProfesor(0,0,"dep1",12,"pr2","prenumepr2","adresapr2","0770000012","pr2","bt4002",2,"");#id = 10
call createProfesor(0,0,"dep1",13,"pr3","prenumepr3","adresapr3","0770000013","pr3","bt4003",3,"");#id = 11
call createProfesor(0,0,"dep2",14,"pr4","prenumepr4","adresapr4","0770000014","pr4","bt4004",4,"");#id = 12

call createAdmin(21,"ad",null,null,null,"ad",null,1,"");#id = 13
call createSuperAdmin(22,"sad",null,null,null,"sad",null,2,"");#id = 14

call creareMaterie("mate","matematici speciale");#id = 1
call creareMaterie("info","informatica");#id = 2

call alocareProfesor(9,1,2,true,true,false, 50, 50, 0);#id = 1
call alocareProfesor(9,1,2,true,true,false, 50, 50, 0);#id = -
call alocareProfesor(10,1,2,true,false,false, 100, 0, 0);#id = 2
call alocareProfesor(11,1,2,false,false,true, 0, 0, 100);#id = 3
call alocareProfesor(12,2,2,true,true,true, 50, 10, 40);#id = 4

call alocareStudent(1,1);
call alocareStudent(1,1);
call alocareStudent(2,1);
call alocareStudent(3,1);
call alocareStudent(4,1);
call alocareStudent(5,1);
call alocareStudent(6,1);
call alocareStudent(7,2);
call alocareStudent(8,2);

