import java.sql.*;

public class BD {
    private Connection connection;
    private int id;
    private boolean loggedIn;
    private TipUser tipUser;

    BD() {
        loggedIn = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://localhost/proiect_2max?user=root2&password=");
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(2);
        }
    }

    public void bdPrepare() {
        loggedIn = false;
    }

    public boolean LogIn(String email, String password) {
        if (loggedIn) return false;
        try {
            CallableStatement callableStatement = connection.prepareCall("{call getId(?,?)}");
            //Statement statement=connection.createStatement();
            callableStatement.setString(1, email);
            callableStatement.setString(2, password);
            ResultSet rs = callableStatement.executeQuery();
            if (!rs.next()) return false;
            Object obj = rs.getObject(1);
            loggedIn = true;
            id = Integer.parseInt(obj.toString());
            setTipUser(id);
            rs.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public TipUser getTipUser() {
        if (!loggedIn) return null;
        return tipUser;
    }

    private void setTipUser(int id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM student WHERE id = '" + id + "';");
        if (rs.next()) {
            tipUser = TipUser.Student;
            rs.close();
            return;
        }
        rs = statement.executeQuery("SELECT * FROM profesor WHERE id = '" + id + "';");
        if (rs.next()) {
            tipUser = TipUser.Profesor;
            rs.close();
            return;
        }
        rs = statement.executeQuery("SELECT * FROM administrator WHERE id = '" + id + "';");
        if (rs.next()) {
            if (rs.getBoolean(2)) {
                tipUser = TipUser.SuperAdmin;
            } else {
                tipUser = TipUser.Admin;
            }
            rs.close();
        }
    }

    public ResultSet getUserInfo() {
        try {
            if (!loggedIn) return null;
            return viewDataTip(id, tipUser);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean insertUser(TipUser tipUser, String[] values) {
        try {
            CallableStatement callableStatement = switch (tipUser) {
                case Student -> connection.prepareCall("{call createStudent(?,?,?,?,?,?,?,?,?,?)}");
                case Profesor -> connection.prepareCall("{call createProfesor(?,?,?,?,?,?,?,?,?,?,?,?)}");
                case Admin, SuperAdmin -> connection.prepareCall("{call createAdmin(?,?,?,?,?,?,?,?,?)}");
            };
            for (int i = 0; i < values.length; i++) {
                callableStatement.setString(i + 1, values[i]);
            }
            callableStatement.executeQuery();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean insertMaterie(String nume, String descriere) {
        try {
            CallableStatement callableStatement = connection.prepareCall("{call creareMaterie(?,?)}");
            callableStatement.setString(1, nume);
            callableStatement.setString(2, descriere);
            callableStatement.executeQuery();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean insertCurs(String[] rez) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id FROM fisamaterie WHERE nume = '" + rez[0] + "';");
            rs.next();
            String idMat = rs.getObject(1).toString();
            CallableStatement callableStatement = connection.prepareCall("{call alocareProfesor(?,?,?,?,?,?,?,?,?)}");
            callableStatement.setString(1, String.valueOf(id));
            callableStatement.setString(2, idMat);
            for (int i = 1; i < rez.length; i++) {
                if (i < 2 || i > 4)
                    callableStatement.setString(i + 2, rez[i]);
                else
                    callableStatement.setBoolean(i + 2, Boolean.parseBoolean(rez[i]));
            }
            callableStatement.executeQuery();
            rs.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet getEmails() {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery("SELECT email FROM utilizator WHERE email is not null;");
        } catch (SQLException e) {
            return null;
        }
    }

    public ResultSet getEmailsAdmin() {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery("SELECT u.email FROM utilizator u left join administrator a using(id) where a.id is null and u.email is not null;");
        } catch (SQLException e) {
            return null;
        }
    }

    public ResultSet getMaterii() {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery("SELECT nume FROM fisamaterie;");
        } catch (Exception e) {
            return null;
        }
    }

    public ResultSet getDescriere(String n) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery("SELECT descriere FROM fisamaterie WHERE nume ='" + n + "';");
        } catch (Exception e) {
            return null;
        }
    }

    public ResultSet getMateriiProf() {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery("SELECT nume FROM fisamaterie f, materie m WHERE f.id = m.id_fisa and m.id_profesor = " + id + ";");
        } catch (Exception e) {
            return null;
        }
    }

    public ResultSet getStudentiMaterie(String m) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT m.id FROM materie m, fisamaterie f WHERE m.id_profesor =" + id + " and m.id_fisa = f.id and f.nume = '" + m + "';");
            int idM = -1;
            if (rs.next())
                idM = rs.getInt(1);
            rs.close();
            return statement.executeQuery("SELECT email FROM utilizator u, student s, contract WHERE u.id = s.id and s.id = contract.id_student and contract.id_materie = " + idM + ";");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean[] getCSL(String m) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT m.id FROM materie m, fisamaterie f WHERE m.id_profesor =" + id + " and m.id_fisa = f.id and f.nume = '" + m + "';");
            int idM = -1;
            if (rs.next())
                idM = rs.getInt(1);
            rs.close();
            boolean[] values = {false, false, false};
            rs = statement.executeQuery("select id_materie from curs where id_materie=" + idM + ";");
            if (rs.next())
                values[0] = true;
            rs.close();
            rs = statement.executeQuery("select id_materie from seminar where id_materie=" + idM + ";");
            if (rs.next())
                values[1] = true;
            rs.close();
            rs = statement.executeQuery("select id_materie from laborator where id_materie=" + idM + ";");
            if (rs.next())
                values[2] = true;
            rs.close();
            return values;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int[] getCSLProcente(String m) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT m.id FROM materie m, fisamaterie f WHERE m.id_profesor =" + id + " and m.id_fisa = f.id and f.nume = '" + m + "';");
            int idM = -1;
            if (rs.next())
                idM = rs.getInt(1);
            rs.close();
            return getCSL(statement, idM);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private int[] getCSL(Statement statement, int idM) throws SQLException {
        ResultSet rs;
        int[] values = {0, 0, 0};
        rs = statement.executeQuery("select procent from curs where id_materie=" + idM + ";");
        if (rs.next())
            values[0] = rs.getInt(1);
        rs.close();
        rs = statement.executeQuery("select procent from seminar where id_materie=" + idM + ";");
        if (rs.next())
            values[1] = rs.getInt(1);
        rs.close();
        rs = statement.executeQuery("select procent from laborator where id_materie=" + idM + ";");
        if (rs.next())
            values[2] = rs.getInt(1);
        rs.close();
        return values;
    }

    public boolean setNote(String m, String e, double[] note, boolean[] val) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT m.id FROM materie m, fisamaterie f WHERE m.id_profesor =" + id + " and m.id_fisa = f.id and f.nume = '" + m + "';");
            int idM = -1;
            if (rs.next())
                idM = rs.getInt(1);
            rs.close();
            rs = statement.executeQuery("SELECT id FROM utilizator WHERE email ='" + e + "';");
            int idS = -1;
            if (rs.next())
                idS = rs.getInt(1);
            rs.close();
            //statement.executeUpdate("update contract set nota_curs = " +note[0]+", nota_seminar = " +note[1]+", nota_laborator = " +note[2]+" where id_student = "+idS+" and id_materie = "+idM+";");
            if (val[0])
                statement.executeUpdate("update contract set nota_curs = " + note[0] + " where id_student = " + idS + " and id_materie = " + idM + ";");
            if (val[1])
                statement.executeUpdate("update contract set nota_seminar = " + note[1] + " where id_student = " + idS + " and id_materie = " + idM + ";");
            if (val[2])
                statement.executeUpdate("update contract set nota_laborator = " + note[2] + " where id_student = " + idS + " and id_materie = " + idM + ";");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public ResultSet getDataFromEmail(String em) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id FROM utilizator WHERE email='" + em + "';");
            if (!rs.next()) return null;
            int id = rs.getInt(1);
            rs.close();
            TipUser tip;
            rs = statement.executeQuery("SELECT * FROM student WHERE id = '" + id + "';");
            if (rs.next()) tip = TipUser.Student;
            else {
                rs.close();
                rs = statement.executeQuery("SELECT * FROM profesor WHERE id = '" + id + "';");
                if (rs.next()) tip = TipUser.Profesor;
                else {
                    rs.close();
                    rs = statement.executeQuery("SELECT * FROM administrator WHERE id = '" + id + "';");
                    rs.next();
                    if (rs.getBoolean(2)) tip = TipUser.SuperAdmin;
                    else tip = TipUser.Admin;
                }
            }
            rs.close();
            return viewDataTip(id, tip);
        } catch (Exception e) {
            return null;
        }
    }

    private ResultSet viewDataTip(int id, TipUser tip) throws SQLException {
        CallableStatement callableStatement = switch (tip) {
            case Student -> connection.prepareCall("{call viewDataStudent(?)}");
            case Profesor -> connection.prepareCall("{call viewDataProfesor(?)}");
            case Admin, SuperAdmin -> connection.prepareCall("{call viewDataAdmin(?)}");
        };
        callableStatement.setInt(1, id);
        return callableStatement.executeQuery();
    }

    public int getIdEmail(String em) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select id from utilizator where email = '" + em + "';");
            rs.next();
            int rez = rs.getInt(1);
            rs.close();
            return rez;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public TipUser getTip(int idd) {
        try {
            Statement statement = connection.createStatement();
            TipUser tip = null;
            ResultSet rs = statement.executeQuery("SELECT * FROM student WHERE id = '" + idd + "';");
            if (rs.next()) {
                tip = TipUser.Student;
                rs.close();
            } else {
                rs = statement.executeQuery("SELECT * FROM profesor WHERE id = '" + idd + "';");
                if (rs.next()) {
                    tip = TipUser.Profesor;
                    rs.close();
                } else {
                    rs = statement.executeQuery("SELECT * FROM administrator WHERE id = '" + idd + "';");
                    if (rs.next()) {
                        tip = TipUser.Admin;
                        rs.close();
                    }
                }
            }
            return tip;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean modificareDate(int i, TipUser tip, String[] values) {
        try {
            CallableStatement callableStatement = switch (tip) {
                case Student -> connection.prepareCall("{call modifyStudent(?,?,?,?,?,?,?,?)}");
                case Profesor -> connection.prepareCall("{call modifyProfesor(?,?,?,?,?,?,?,?,?,?)}");
                case Admin, SuperAdmin -> connection.prepareCall("{call modifyAdmin(?,?,?,?,?,?,?)}");
            };
            callableStatement.setString(1, String.valueOf(i));
            for (int j = 0; j < values.length; j++) {
                callableStatement.setString(j + 2, values[j]);
            }
            callableStatement.executeQuery();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean stergeDate(int i) {
        try {
            Statement statement = connection.createStatement();
            int rs = statement.executeUpdate("UPDATE utilizator SET email = NULL WHERE id =" + i);
            return rs != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean alocareStudent(String nume) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id FROM fisamaterie WHERE nume = '" + nume + "';");
            int idM = -1;
            if (rs.next())
                idM = rs.getInt(1);
            rs.close();
            CallableStatement callableStatement = connection.prepareCall("{call alocareStudent(?,?)}");
            callableStatement.setInt(1, id);
            callableStatement.setInt(2, idM);
            callableStatement.executeQuery();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ResultSet getNote() {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery("""
                    SELECT fm.nume,fm.descriere,u.nume,u.prenume,c.nota_curs,c.nota_seminar,c.nota_laborator,m.id
                    FROM student s,contract c, materie m, fisamaterie fm,profesor p,utilizator u
                    WHERE s.id='""" + id + "' and c.id_student=s.id and c.id_materie=m.id and m.id_profesor=p.id and p.id=u.id and m.id_fisa=fm.id;");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int[] getCSLProcenteMaterie(int id) {
        try {
            Statement statement = connection.createStatement();
            return getCSL(statement, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getCatalog() {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery("""
                    SELECT fm.nume,fm.descriere,u.nume,u.prenume,c.nota_curs,c.nota_seminar,c.nota_laborator,m.id
                    FROM student s,contract c, materie m, fisamaterie fm,profesor p,utilizator u
                    WHERE p.id='""" + id + "' and c.id_student=s.id and c.id_materie=m.id and m.id_profesor=p.id and s.id=u.id and m.id_fisa=fm.id;");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getGrupuri() {
        try {
            CallableStatement callableStatement = connection.prepareCall("{call getGrupuri(?)}");
            callableStatement.setInt(1, id);
            return callableStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private int getMaterieId(int idGrup) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("""
                    SELECT m.id
                    FROM grup g,fisamaterie fm,materie m,contract c
                    WHERE g.id='""" + idGrup + "' and g.id_materie=fm.id and m.id_fisa=fm.id and c.id_materie=m.id and c.id_student='" + id + "';");
            if (!rs.next()) return -1;
            return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean inscriereGrup(int idGrup) {
        try {
            Statement statement = connection.createStatement();
            int idM = getMaterieId(idGrup);
            int rs = statement.executeUpdate("UPDATE contract SET inscris_grup=true WHERE id_student='" + id + "' and id_materie='" + idM + "';");
            if (rs != 0) {
                CallableStatement callableStatement = connection.prepareCall("{call trimiteMesaj(?,?,?,?)}");
                callableStatement.setInt(1, id);
                callableStatement.setInt(2, idGrup);
                callableStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                ResultSet resultSet = statement.executeQuery("SELECT u.nume,u.prenume FROM utilizator u WHERE u.id='" + id + "';");
                resultSet.next();
                callableStatement.setString(4, resultSet.getString(1) + " " + resultSet.getString(2) + " a intrat in grup");
                callableStatement.executeQuery();
            }
            return rs != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet getMembri(int idGrup) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery("""
                    SELECT u.nume,u.prenume
                    FROM utilizator u,student s,contract c,materie m,fisamaterie fm,grup g
                    WHERE g.id='""" + idGrup + """
                    ' and fm.id=g.id_materie and m.id_fisa=fm.id and c.id_materie=m.id and c.inscris_grup=true and s.id=c.id_student and u.id=s.id
                    ORDER BY nume,prenume;""");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getChat(int idGrup) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery("""
                    SELECT u.nume,u.prenume,m.mesaj,m.data_trimitere
                    FROM grup g,mesaj m,student s,utilizator u
                    WHERE g.id='""" + idGrup + "' and m.id_grup=g.id and m.id_student=s.id and u.id=s.id ORDER BY data_trimitere;");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean trimiteMesaj(int idGrup, String mesaj) {
        try {
            CallableStatement callableStatement = connection.prepareCall("{call trimiteMesaj(?,?,?,?)}");
            callableStatement.setInt(1, id);
            callableStatement.setInt(2, idGrup);
            callableStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT u.nume,u.prenume FROM utilizator u WHERE u.id='" + id + "';");
            resultSet.next();
            callableStatement.setString(4, mesaj);
            callableStatement.executeQuery();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ResultSet getActivitati(int idGrup) {
        try {
            CallableStatement callableStatement = connection.prepareCall("{call verificareActivitatiGrup()}");
            Timestamp now = new Timestamp(System.currentTimeMillis());
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Timestamp dataExp = rs.getTimestamp(5);
                Timestamp dataFin = new Timestamp(rs.getTimestamp(4).getTime() + rs.getTime(6).getTime());
                int conditie = 0;
                if (now.after(dataFin)) conditie = 1;
                if (now.after(dataExp) && rs.getInt(2) > rs.getInt(3)) conditie += 2;
                if (conditie > 0) {
                    ResultSet rsDelete;
                    callableStatement = connection.prepareCall("{call stergeActivitateGrup(?)}");
                    callableStatement.setInt(1, rs.getInt(1));
                    rsDelete = callableStatement.executeQuery();
                    String mesajExp = "Activitatea " + rs.getString(7) + " de pe data de " + rs.getTimestamp(4) + " a fost anulata din cauza numarului insuficient de participanti!";
                    String mesajFin = "Activitatea " + rs.getString(7) + " de pe data de " + rs.getTimestamp(4) + " a fost realizata!";
                    while (rsDelete.next()) {
                        callableStatement = connection.prepareCall("{call trimiteNotificare(?,?,?)}");
                        callableStatement.setInt(1, rsDelete.getInt(1));
                        callableStatement.setTimestamp(2, now);
                        if (conditie >= 2) {//exp
                            callableStatement.setString(3, mesajExp);
                        } else {//fin
                            callableStatement.setString(3, mesajFin);
                        }
                        callableStatement.executeQuery();
                    }
                }
            }
            callableStatement = connection.prepareCall("{call getActivitatiGrup(?,?)}");
            callableStatement.setInt(1, id);
            callableStatement.setInt(2, idGrup);
            return callableStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet inscriereActivitateGrup(int idActivitate) {
        try {
            Statement statement = connection.createStatement();
            int rs = statement.executeUpdate("insert into studentactivitategrup(id_student,id_activitate) values('" + id + "','" + idActivitate + "');");
            if (rs == 0) return null;
            CallableStatement callableStatement = connection.prepareCall("{call getActivitateGrup(?,?)}");
            callableStatement.setInt(1, id);
            callableStatement.setInt(2, idActivitate);
            return callableStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int creareActivitateGrup(int idGrup, String nume, Timestamp dataActivitate, Time durata, Timestamp dataExpirare, int nrMinStud) {
        try {
            CallableStatement callableStatement = connection.prepareCall("{call creareActivitateGrup(?,?,?,?,?,?)}");
            callableStatement.setInt(1, idGrup);
            callableStatement.setString(2, nume);
            callableStatement.setTimestamp(3, dataActivitate);
            callableStatement.setTimestamp(4, dataExpirare);
            callableStatement.setTime(5, durata);
            callableStatement.setInt(6, nrMinStud);
            callableStatement.executeQuery();
            callableStatement = connection.prepareCall("{call getActivitateId(?,?,?,?,?,?)}");
            callableStatement.setInt(1, idGrup);
            callableStatement.setString(2, nume);
            callableStatement.setTimestamp(3, dataActivitate);
            callableStatement.setTimestamp(4, dataExpirare);
            callableStatement.setTime(5, durata);
            callableStatement.setInt(6, nrMinStud);
            ResultSet rs = callableStatement.executeQuery();
            if (!rs.next()) return -1;
            return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ResultSet getNotificari() {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery("select mesaj,data_primire from notificare where id_utilizator='" + id + "';");
        } catch (Exception e) {
            return null;
        }
    }

    public boolean retragereGrup(int idGrup) {
        try {
            Statement statement = connection.createStatement();
            int idM = getMaterieId(idGrup);
            int rs = statement.executeUpdate("UPDATE contract SET inscris_grup=false WHERE id_student='" + id + "' and id_materie='" + idM + "';");
            if (rs != 0) {
                CallableStatement callableStatement = connection.prepareCall("{call trimiteMesaj(?,?,?,?)}");
                callableStatement.setInt(1, id);
                callableStatement.setInt(2, idGrup);
                callableStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                ResultSet resultSet = statement.executeQuery("SELECT u.nume,u.prenume FROM utilizator u WHERE u.id='" + id + "';");
                resultSet.next();
                callableStatement.setString(4, resultSet.getString(1) + " " + resultSet.getString(2) + " a iesit din grup");
                callableStatement.executeQuery();
            }
            return rs != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet getCursuri() {
        try {
            CallableStatement callableStatement;
            switch (tipUser) {
                case Student -> callableStatement = connection.prepareCall("{call getCursruriStudent(?)}");
                case Profesor -> callableStatement = connection.prepareCall("{call getCursruriProfesor(?)}");
                default -> {
                    return null;
                }
            }
            callableStatement.setInt(1, id);
            return callableStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getActivitatiCurs(int idCurs) {
        try {
            CallableStatement callableStatement = connection.prepareCall("{call getActivitatiCursuri(?,?)}");
            callableStatement.setInt(1, id);
            callableStatement.setInt(2, idCurs);
            return callableStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet inscriereActivitateCurs(int idActivitate) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select (select count(*) from studentactivitatematerie sam where sam.id_activitate=am.id),am.nr_max_stud from activitatematerie am where am.id='" + idActivitate + "';");
            if (rs.next()) {
                int nrMax = rs.getInt(2);
                int nr = rs.getInt(1);
                if (nr >= nrMax) return rs;
            } else return null;
            int result = statement.executeUpdate("insert into studentactivitatematerie(id_student,id_activitate) values('" + id + "','" + idActivitate + "');");
            if (result == 0) return null;
            CallableStatement callableStatement = connection.prepareCall("{call getActivitateCurs(?,?)}");
            callableStatement.setInt(1, id);
            callableStatement.setInt(2, idActivitate);
            return callableStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int creareActivitateCurs(int selectedCursId, String nume, Timestamp dataActivitate, Time durata, int nrMaxStud) {
        try {
            CallableStatement callableStatement = connection.prepareCall("{call creareActivitateCurs(?,?,?,?,?)}");
            callableStatement.setInt(1, selectedCursId);
            callableStatement.setString(2, nume);
            callableStatement.setTimestamp(3, dataActivitate);
            callableStatement.setTime(4, durata);
            callableStatement.setInt(5, nrMaxStud);
            callableStatement.executeQuery();
            callableStatement = connection.prepareCall("{call getActivitateCursId(?,?,?,?,?)}");
            callableStatement.setInt(1, selectedCursId);
            callableStatement.setString(2, nume);
            callableStatement.setTimestamp(3, dataActivitate);
            callableStatement.setTime(4, durata);
            callableStatement.setInt(5, nrMaxStud);
            ResultSet rs = callableStatement.executeQuery();
            if (!rs.next()) {
                return -1;
            }
            return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ResultSet getCalendarCurs() {
        try {
            Statement statement = connection.createStatement();
            if (tipUser == TipUser.Student) return statement.executeQuery("""
                    select am.data_activitate,am.durata,am.tip,fm.nume
                    from student s,studentactivitatematerie sam, activitatematerie am,materie m,fisamaterie fm
                    where s.id='""" + id + "' and sam.id_student=s.id and sam.id_activitate=am.id and m.id=am.id_materie and fm.id=m.id_fisa order by am.data_activitate;");
            return statement.executeQuery("""
                    select am.data_activitate,am.durata,am.tip,fm.nume
                    from activitatematerie am,materie m,fisamaterie fm
                    where m.id_profesor='""" + id + "' and m.id=am.id_materie and fm.id=m.id_fisa order by am.data_activitate;");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getSugestieGrup(int idGrup) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery("""
                    select u.nume,u.prenume,u.id
                    from utilizator u,student s,contract c,materie m,fisamaterie fm,grup g
                    where u.id=s.id and s.id=c.id_student and m.id=c.id_materie and fm.id=m.id_fisa and fm.id=g.id_materie and c.inscris_grup=false and g.id='""" + idGrup + "';");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean trimiteNotirifcare(int idUtilizator, String mesaj) {
        try {
            CallableStatement callableStatement = connection.prepareCall("{call trimiteNotificare(?,?,?)}");
            callableStatement.setInt(1, idUtilizator);
            callableStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            callableStatement.setString(3, mesaj);
            callableStatement.executeQuery();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getGrupName(int idGrup) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select fm.nume from fisamaterie fm,grup g where g.id='" + idGrup + "' and g.id_materie=fm.id;");
            if (rs.next()) return rs.getString(1);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
