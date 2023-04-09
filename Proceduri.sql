use proiect_2max;
drop procedure if exists createStudent;
delimiter //
create procedure createStudent(in_an_studiu int,in_cnp int,in_nume varchar(45),in_prenume varchar(45),in_adresa varchar(200),in_telefon varchar(10),in_email varchar(45),in_IBAN varchar(45),in_nr_contract int,in_parola varchar(20))
begin
insert into utilizator(cnp,nume,prenume,adresa,telefon,email,IBAN,nr_contract,parola) values (in_cnp,in_nume,in_prenume,in_adresa,in_telefon,in_email,in_IBAN,in_nr_contract,in_parola);
select @id:=id from utilizator p where p.email = in_email;
insert into student (id,an_studiu,nr_ore) values (@id,in_an_studiu,0); 
end //
delimiter ;

drop procedure if exists createProfesor;
delimiter //
create procedure createProfesor(in_nr_ore_min int,in_nr_ore_max int,in_departament varchar(45),in_cnp int,in_nume varchar(45),in_prenume varchar(45),in_adresa varchar(200),in_telefon varchar(10),in_email varchar(45),in_IBAN varchar(45),in_nr_contract int,in_parola varchar(20))
begin
insert into utilizator(cnp,nume,prenume,adresa,telefon,email,IBAN,nr_contract,parola) values (in_cnp,in_nume,in_prenume,in_adresa,in_telefon,in_email,in_IBAN,in_nr_contract,in_parola);
select @id:=id from utilizator p where p.email = in_email;
insert into profesor (id,nr_cursuri,min_ore,max_ore,departament) values (@id,0,in_nr_ore_min,in_nr_ore_max,in_departament); 
end //
delimiter ;

drop procedure if exists createAdmin;
delimiter //
create procedure createAdmin(in_cnp int,in_nume varchar(45),in_prenume varchar(45),in_adresa varchar(200),in_telefon varchar(10),in_email varchar(45),in_IBAN varchar(45),in_nr_contract int,in_parola varchar(20))
begin
insert into utilizator(cnp,nume,prenume,adresa,telefon,email,IBAN,nr_contract,parola) values (in_cnp,in_nume,in_prenume,in_adresa,in_telefon,in_email,in_IBAN,in_nr_contract,in_parola);
select @id:=id from utilizator p where p.email = in_email;
insert into administrator(id, superadmin) value(@id, 0);
end //
delimiter ;

drop procedure if exists createSuperAdmin;
delimiter //
create procedure createSuperAdmin(in_cnp int,in_nume varchar(45),in_prenume varchar(45),in_adresa varchar(200),in_telefon varchar(10),in_email varchar(45),in_IBAN varchar(45),in_nr_contract int,in_parola varchar(20))
begin
insert into utilizator(cnp,nume,prenume,adresa,telefon,email,IBAN,nr_contract,parola) values (in_cnp,in_nume,in_prenume,in_adresa,in_telefon,in_email,in_IBAN,in_nr_contract,in_parola);
select @id:=id from utilizator p where p.email = in_email;
insert into administrator(id, superadmin) value(@id, 1);
end //
delimiter ;

DROP PROCEDURE IF EXISTS viewDataStudent;
delimiter //
CREATE PROCEDURE  viewDataStudent(id int)
BEGIN
	select p.cnp, p.nume, p.prenume, p.adresa, p.telefon, p.email, p.IBAN, p.nr_contract, s.an_studiu, s.nr_ore
    from utilizator p, student s
    where p.id = s.id and p.id = id;
END//
delimiter ;

DROP PROCEDURE IF EXISTS viewDataProfesor;
delimiter //
CREATE PROCEDURE  viewDataProfesor(id int)
BEGIN
	select p.cnp, p.nume, p.prenume, p.adresa, p.telefon, p.email, p.IBAN, p.nr_contract, pr.nr_cursuri, pr.min_ore, pr.max_ore, pr.departament
    from utilizator p, profesor pr
    where p.id = pr.id and p.id = id;
END//
delimiter ;

DROP PROCEDURE IF EXISTS viewDataAdmin;
delimiter //
CREATE PROCEDURE  viewDataAdmin(ida int)
BEGIN
	select p.cnp, p.nume, p.prenume, p.adresa, p.telefon, p.email, p.IBAN, p.nr_contract, a.superadmin
    from utilizator p, administrator a
    where p.id = ida and p.id=a.id;
END//
delimiter ;



DROP PROCEDURE IF EXISTS viewCursStudent;
delimiter //
CREATE PROCEDURE  viewCursStudent(id int)
BEGIN
	select f.nume, f.descriere, pe.nume, pe.prenume, c.nota_curs, c.nota_seminar, c.nota_laborator
    from student s
    join contract c on s.id = c.id_student and s.id = id
    join materie m on c.id_materie = m.id
    join fisamaterie f on m.id_fisa = f.id
    join profesor p on m.id_profesor = p.id
    join utilizator pe on pe.id = p.id;
END//
delimiter ;

DROP PROCEDURE IF EXISTS viewCursProfesor;
delimiter //
CREATE PROCEDURE  viewCursProfesor(id int)
BEGIN
	select f.nume, f.descriere, m.max_studenti, m.studenti
    from profesor p
    join materie m on p.id = m.id_profesor and p.id = id
    join fisamaterie f on m.id_fisa = f.id;
END//
delimiter ;


DROP PROCEDURE IF EXISTS alocareStudent;
delimiter //
CREATE PROCEDURE  alocareStudent(id_stud int,id_mat int)
BEGIN
	set @ex := exists(select * from (
		select fm.id
        from student s,contract c,materie m,fisamaterie fm
        where s.id=id_stud and c.id_student=s.id and c.id_materie=m.id and fm.id=m.id_fisa) as mat
        where mat.id=id_mat);
	if(@ex=0) then
    select @id := id from materie where id_fisa=id_mat && studenti=( select min(studenti) from materie  where id_fisa=id_mat and studenti<max_studenti) limit 1;
    insert into contract(id_student,id_materie,inscris_grup,nota_curs,nota_seminar,nota_laborator) values (id_stud,@id,false,0,0,0);
    end if;
END//
delimiter ;

DROP PROCEDURE IF EXISTS alocareProfesor;
delimiter //
CREATE PROCEDURE  alocareProfesor(id_prof int,id_mat int,nr_max int,c boolean,s boolean,l boolean, pc int, ps int, pl int)
BEGIN
	set @ex := exists(select * from materie where id_profesor=id_prof and id_fisa=id_mat);
    if(@ex=0) then
	insert into materie (id_profesor, id_fisa, max_studenti, studenti) values (id_prof, id_mat, nr_max, 0);
    select @id := id from materie where id_profesor = id_prof and id_fisa = id_mat;
    if(c) then insert into curs (id_materie, procent) value (@id, pc);
    end if;
    if(l) then insert into laborator (id_materie, procent) value (@id, pl);
    end if;
    if(s) then insert into seminar (id_materie, procent) value (@id, ps);
    end if;
    end if;
END//
delimiter ;

DROP PROCEDURE IF EXISTS creareMaterie;
delimiter //
CREATE PROCEDURE  creareMaterie(n varchar(45),d varchar(200))
BEGIN
	insert into fisamaterie(nume, descriere) values (n, d);
END//
delimiter ;

DROP PROCEDURE IF EXISTS getId;
delimiter //
CREATE PROCEDURE  getId(in_email varchar(45),in_parola varchar(20))
BEGIN
	select id from utilizator where email=in_email and parola=in_parola;
END//
delimiter ;

drop procedure if exists modifyStudent;
delimiter //
create procedure modifyStudent(in_id int, in_an_studiu int,in_cnp int,in_nume varchar(45),in_prenume varchar(45),in_adresa varchar(200),in_telefon varchar(10),in_IBAN varchar(45))
begin
update utilizator
set cnp = in_cnp,
	nume = in_nume,
	prenume = in_prenume,
    adresa = in_adresa,
    telefon = in_telefon,
    IBAN = in_IBAN
where id = in_id;
update student
set an_studiu = in_an_studiu
where id = in_id;
end //
delimiter ;

drop procedure if exists modifyProfesor;
delimiter //
create procedure modifyProfesor(in_id int, in_nr_ore_min int,in_nr_ore_max int,in_departament varchar(45),in_cnp int,in_nume varchar(45),in_prenume varchar(45),in_adresa varchar(200),in_telefon varchar(10),in_IBAN varchar(45))
begin
update utilizator
set cnp = in_cnp,
	nume = in_nume,
	prenume = in_prenume,
    adresa = in_adresa,
    telefon = in_telefon,
    IBAN = in_IBAN
where id = in_id;
update profesor
set departament = in_departament,
	min_ore = in_nr_ore_min,
    max_ore = in_nr_ore_max
where id = in_id;
end //
delimiter ;

drop procedure if exists modifyAdmin;
delimiter //
create procedure modifyAdmin(in_id int, in_cnp int,in_nume varchar(45),in_prenume varchar(45),in_adresa varchar(200),in_telefon varchar(10),in_IBAN varchar(45))
begin
update utilizator
set cnp = in_cnp,
	nume = in_nume,
	prenume = in_prenume,
    adresa = in_adresa,
    telefon = in_telefon,
    IBAN = in_IBAN
where id = in_id;
end //
delimiter ;

drop trigger if exists addStudent;
delimiter //
CREATE TRIGGER addStudent AFTER INSERT
ON contract FOR EACH ROW
BEGIN
	update materie set studenti=studenti+1  where id=new.id_materie;
end //
delimiter ;

drop trigger if exists addFisaMaterie;
delimiter //
CREATE TRIGGER addFisaMaterie AFTER INSERT
ON fisamaterie FOR EACH ROW
BEGIN
	insert grup(id_materie) values (new.id);
end //
delimiter ;

drop procedure if exists getGrupuri;
delimiter //
create procedure getGrupuri(id_st int)
begin
select fm.nume,c.inscris_grup,g.id
from student s,contract c,materie m,fisamaterie fm,grup g
where s.id=id_st and c.id_student=s.id and m.id=c.id_materie and fm.id=m.id_fisa and g.id_materie=fm.id;
end //
delimiter ;

drop procedure if exists trimiteMesaj;
delimiter //
create procedure trimiteMesaj(id_st int,id_gr int,data_tr datetime,msj varchar(280))
begin
insert into mesaj(id_student,id_grup,data_trimitere,mesaj) values(id_st,id_gr,data_tr,msj);
end //
delimiter ;

drop procedure if exists getActivitatiGrup;
delimiter //
create procedure getActivitatiGrup(id_st int,id_gr int)
begin
SELECT ag.id,ag.nume,ag.data_activitate,ag.durata,ag.data_expirare,ag.nr_min_stud,exists(select * from studentactivitategrup sag where sag.id_activitate=ag.id and sag.id_student=id_st) as inscris,(select count(*) from studentactivitategrup sag where sag.id_activitate=ag.id) as nr_studenti
FROM grup g,activitategrup ag
WHERE g.id=ag.id_grup and g.id=id_gr
ORDER BY ag.id;
end //
delimiter ;

drop procedure if exists getActivitateGrup;
delimiter //
create procedure getActivitateGrup(id_st int,id_act int)
begin
SELECT ag.id,ag.nume,ag.data_activitate,ag.durata,ag.data_expirare,ag.nr_min_stud,exists(select * from studentactivitategrup sag where sag.id_activitate=ag.id and sag.id_student=id_st) as inscris,(select count(*) from studentactivitategrup sag where sag.id_activitate=ag.id) as nr_studenti
FROM activitategrup ag
WHERE ag.id=id_act;
end //
delimiter ;

drop procedure if exists creareActivitateGrup;
delimiter //
create procedure creareActivitateGrup(id_gr int,nu varchar(30),d_act datetime,d_exp datetime,dur time,n_min int)
begin
insert into activitategrup(id_grup,nume,data_activitate,durata,data_expirare,nr_min_stud) values(id_gr,nu,d_act,dur,d_exp,n_min);
end //
delimiter ;

drop procedure if exists getActivitateId;
delimiter //
create procedure getActivitateId(id_gr int,nu varchar(30),d_act datetime,d_exp datetime,dur time,n_min int)
begin
select id from activitategrup where id_grup=id_gr and nume=nu and data_activitate=d_act and data_expirare=d_exp and durata=dur and nr_min_stud=n_min;
end //
delimiter ;

drop procedure if exists verificareActivitatiGrup;
delimiter //
create procedure verificareActivitatiGrup()
begin
select ag.id,ag.nr_min_stud,(select count(*) from studentactivitategrup sag where sag.id_activitate=ag.id) as nr_studenti,ag.data_activitate,ag.data_expirare,ag.durata,ag.nume,ag.id_grup
from activitategrup ag;
end //
delimiter ;

drop procedure if exists stergeActivitateGrup;
delimiter //
create procedure stergeActivitateGrup(id_act int)
begin
select * from studentactivitategrup where id_activitate=id_act;
delete from studentactivitategrup where id_activitate=id_act;
delete from activitategrup where id=id_act;
end //
delimiter ;

drop procedure if exists trimiteNotificare;
delimiter //
create procedure trimiteNotificare(id_ut int,d_prim datetime,msj varchar(280))
begin
insert into notificare(id_utilizator,data_primire,mesaj) values(id_ut,d_prim,msj);
end //
delimiter ;

drop procedure if exists getCursruriStudent;
delimiter //
create procedure getCursruriStudent(id_st int)
begin
select fm.nume,m.id
from student s, contract c,materie m,fisamaterie fm
where s.id=id_st and c.id_student=s.id and c.id_materie=m.id and m.id_fisa=fm.id;
end //
delimiter ;

drop procedure if exists getCursruriProfesor;
delimiter //
create procedure getCursruriProfesor(id_pr int)
begin
select fm.nume,m.id
from profesor p,materie m,fisamaterie fm
where p.id=id_pr and m.id_profesor=p.id and m.id_fisa=fm.id;
end //
delimiter ;

drop procedure if exists getActivitatiCursuri;
delimiter //
create procedure getActivitatiCursuri(id_ut int,id_mat int)
begin
select am.id,am.tip,am.data_activitate,am.durata,am.nr_max_stud,exists(select * from studentactivitatematerie sam where sam.id_activitate=am.id and sam.id_student=id_ut) as inscris,(select count(*) from studentactivitatematerie sam where sam.id_activitate=am.id) as nr_studenti
from materie m,activitatematerie am
where m.id=id_mat and am.id_materie=m.id;
end //
delimiter ;

drop procedure if exists getActivitateCurs;
delimiter //
create procedure getActivitateCurs(id_ut int,id_act int)
begin
SELECT am.id,am.tip,am.data_activitate,am.durata,am.nr_max_stud,exists(select * from studentactivitatematerie sam where sam.id_activitate=am.id and sam.id_student=id_ut) as inscris,(select count(*) from studentactivitatematerie sam where sam.id_activitate=am.id) as nr_studenti
FROM activitatematerie am
WHERE am.id=id_act;
end //
delimiter ;

drop procedure if exists creareActivitateCurs;
delimiter //
create procedure creareActivitateCurs(id_mat int,nu varchar(30),d_act datetime,dur time,n_max int)
begin
insert into activitatematerie(id_materie,tip,data_activitate,durata,nr_max_stud) values(id_mat,nu,d_act,dur,n_max);
end //
delimiter ;

drop procedure if exists getActivitateCursId;
delimiter //
create procedure getActivitateCursId(id_mat int,nu varchar(30),d_act datetime,dur time,n_max int)
begin
select id from activitatematerie where id_materie=id_mat and tip=nu and data_activitate=d_act and durata=dur and nr_max_stud=n_max;
end //
delimiter ;











