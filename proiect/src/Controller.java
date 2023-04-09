import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller {
    private final View view = new View();
    private final BD bd = new BD();

    Controller() {
        view.setButtonLogIn(new ButtonLogIn());
        view.setButtonGoToDate(new ButtonToDate());
        view.setButtonHome(new ButtonBackHome());
        view.setButtonDelogare(new ButtonDelogare());
        view.addAdaugareUser(new ButtonAdaugare());
        view.setButtonAddUser(new ButtonAddUser());
        view.setButtonAddMaterie(new ButtonAddMaterie());
        view.addAdaugareMaterie(new ButtonAdaugareMaterie());

        view.getModificareDate().getBack().addActionListener(new ButtonBackHome());
        view.getHomePage().getModificare().addActionListener(new ButtonToModificare());
        view.getModificareDate().getModify().addActionListener(new ButtonModifica());
        view.getModificareDate().getSterge().addActionListener(new ButtonStergeUser());

        view.getCreareCurs().getBack().addActionListener(new ButtonBackHome());
        view.getHomePage().getAddCurs().addActionListener(new ButtonToAddCurs());
        view.getCreareCurs().getAddCurs().addActionListener(new ButtonAddCurs());

        view.getAdaugareNote().getBack().addActionListener(new ButtonBackHome());
        view.getHomePage().getNotare().addActionListener(new ButtonToNotare());
        view.getAdaugareNote().getAddNote().addActionListener(new ButtonAddNote());

        view.getCatalog().getBack().addActionListener(new ButtonBackHome());
        view.getHomePage().getCatalog().addActionListener(new ButtonToCatalog());
        view.getCatalog().getDescarca().addActionListener(new ButtonDescarcaCatalog());

        view.getNote().getBack().addActionListener(new ButtonBackHome());
        view.getHomePage().getNote().addActionListener(new ButtonToNote());

        view.getInscriereCurs().getBack().addActionListener(new ButtonBackHome());
        view.getHomePage().getInscriere().addActionListener(new ButtonToInscriere());
        view.getInscriereCurs().getInscriere().addActionListener(new ButtonInscriere());

        view.getDateAdmin().getBack().addActionListener(new ButtonBackHome());
        view.getHomePage().getDateUseri().addActionListener(new ButtonToDateUseri());

        view.getGrup().getBack().addActionListener(new ButtonBackHome());
        view.getHomePage().getGrup().addActionListener(new ButtonToGrup());
        view.getGrup().getGrupuri().addItemListener(new GrupChangeListener());
        view.getGrup().getInscriere().addActionListener(new ButtonInscriereGrup());
        view.getGrup().getTrimite().addActionListener(new ButtonTrimiteMesaj());
        view.getGrup().getSwitchButton().addActionListener(new ButtonSwitchGrup());
        view.getGrup().getInscriereActivitate().addActionListener(new ButtonInscriereActivitateGrup());
        view.getGrup().getCreareActivitateGrup().getCreaza().addActionListener(new ButtonCreareActivitateGrup());
        InvitaSugestie invitaSugestie = new InvitaSugestie();
        view.getGrup().getSugestie().addMouseListener(invitaSugestie);
        view.getGrup().getSugestie().addMouseMotionListener(invitaSugestie);

        view.getNotificari().getBack().addActionListener(new ButtonBackHome());
        view.getHomePage().getNotificari().addActionListener(new ButtonToNotificari());
        view.getHomePage().getNotificari2().addActionListener(new ButtonToNotificari());

        view.getActivitatiCurs().getBack().addActionListener(new ButtonBackHome());
        view.getHomePage().getActivitatiCurs().addActionListener(new ButtonToActivitatiCurs());
        view.getHomePage().getActivitatiCurs2().addActionListener(new ButtonToActivitatiCurs());
        view.getActivitatiCurs().getCursuri().addItemListener(new CursChangeListener());
        view.getActivitatiCurs().getInscriereActivitate().addActionListener(new ButtonInscriereActivitateCurs());
        view.getActivitatiCurs().getCreareActivitateCurs().getCreaza().addActionListener(new ButtonCreareActivitateCurs());

        view.getCalendar().getBack().addActionListener(new ButtonBackHome());
        view.getHomePage().getCalendar().addActionListener(new ButtonToCalendar());
        view.getHomePage().getCalendar2().addActionListener(new ButtonToCalendar());
        view.getCalendar().getDescarca().addActionListener(new ButtonDescarcaCalendar());
    }

    public void setEmails() {
        if (bd.getTipUser() == TipUser.SuperAdmin)
            view.getModificareDate().setEmails(bd.getEmails());
        else if (bd.getTipUser() == TipUser.Admin)
            view.getModificareDate().setEmails(bd.getEmailsAdmin());
        view.getModificareDate().getComboEmail().addItemListener(new EmailChangeListener());
    }

    public void setUsers() {
        if (bd.getTipUser() == TipUser.SuperAdmin)
            view.getDateAdmin().setUser(bd.getEmails());
        else if (bd.getTipUser() == TipUser.Admin)
            view.getDateAdmin().setUser(bd.getEmailsAdmin());
        view.getDateAdmin().getUser().addItemListener(new UserChangeListener());
    }

    private void getProc(int nr, int[][] proc, ResultSet rs) throws SQLException {
        while (rs.next()) {
            int[] tmp = bd.getCSLProcenteMaterie(rs.getInt(8));
            proc[nr][0] = tmp[0];
            proc[nr][1] = tmp[1];
            proc[nr][2] = tmp[2];
            nr++;
        }
    }

    public class ButtonLogIn implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(view.getEmail() + " " + view.getPassword());
            boolean ok = bd.LogIn(view.getEmail(), view.getPassword());
            TipUser tip = bd.getTipUser();
            view.setLogIn(ok, bd.getUserInfo(), tip);
        }
    }

    public class ButtonToDate implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.goToDate();
        }
    }

    public class ButtonBackHome implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.goHome();
        }
    }

    public class ButtonDelogare implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.doDelogare();
            bd.bdPrepare();
        }
    }

    public class ButtonAdaugare implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.doAdaugareUser();
        }
    }

    public class ButtonAddUser implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (bd.insertUser(view.getSignInTipUser(), view.getSignInValues())) view.resetAdd();
        }
    }

    public class ButtonAddMaterie implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            bd.insertMaterie(view.getCreareMaterie().getNume(), view.getCreareMaterie().getDescriere());
            view.getCreareMaterie().resetTexts();
        }
    }

    public class ButtonAdaugareMaterie implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.doAdaugareMaterie();
            view.getCreareMaterie().resetTexts();
        }
    }

    public class ButtonToModificare implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.goModify();
            view.getModificareDate().getComboEmail().removeAllItems();
            setEmails();
            view.getModificareDate().reinitializare();
        }
    }

    public class ButtonModifica implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int i = bd.getIdEmail(view.getModificareDate().getEmail());
            TipUser tip = bd.getTip(i);
            if (bd.modificareDate(i, tip, view.getModificareDate().getTexts(tip)))
                view.getModificareDate().reinitializare();
        }
    }

    public class ButtonStergeUser implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int i = bd.getIdEmail(view.getModificareDate().getEmail());
            if (bd.stergeDate(i)) {
                int index = view.getModificareDate().getComboEmail().getSelectedIndex();
                view.getModificareDate().reinitializare();
                view.getModificareDate().getComboEmail().removeItemAt(index);
            }
        }
    }

    public class ButtonToAddCurs implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.goAddCurs();
            view.getCreareCurs().setMaterii(bd.getMaterii());
            view.getCreareCurs().getComboMaterie().addItemListener(new MaterieChangeListener());
        }
    }

    public class ButtonAddCurs implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (bd.insertCurs(view.getCreareCurs().getValues()))
                view.getCreareCurs().deselect();
        }
    }

    public class ButtonToNotare implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.doNotare();
            view.getAdaugareNote().setMaterii(bd.getMateriiProf());
            view.getAdaugareNote().getMaterie().addItemListener(e1 -> {
                if (e1.getStateChange() == ItemEvent.SELECTED) {
                    view.getAdaugareNote().getStudent().removeAllItems();
                    view.getAdaugareNote().getStudent().addItem("");
                    String nume = (String) view.getAdaugareNote().getMaterie().getSelectedItem();
                    view.getAdaugareNote().setStudenti(bd.getStudentiMaterie(nume));
                    boolean[] values = bd.getCSL(nume);
                    view.getAdaugareNote().getCurs().setVisible(values[0]);
                    view.getAdaugareNote().getSeminar().setVisible(values[1]);
                    view.getAdaugareNote().getLaborator().setVisible(values[2]);
                    int[] procente = bd.getCSLProcente(nume);
                    view.getAdaugareNote().setProcente(procente);
                }
            });
        }
    }

    public class ButtonAddNote implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double[] note = view.getAdaugareNote().getNote();
            String nume = (String) view.getAdaugareNote().getMaterie().getSelectedItem();
            String student = (String) view.getAdaugareNote().getStudent().getSelectedItem();
            boolean[] val = view.getAdaugareNote().getActivitate();
            bd.setNote(nume, student, note, val);
            view.getAdaugareNote().afterNoatre();
        }
    }

    public class ButtonToCatalog implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int nr = 0;
            int[][] proc;
            try {
                ResultSet rs = bd.getCatalog();
                while (rs.next()) nr++;
                rs.close();
                proc = new int[nr][3];
                nr = 0;
                rs = bd.getCatalog();
                getProc(nr, proc, rs);
                rs.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            view.getCatalog().modificare(bd.getCatalog(), proc);
            view.doCatalog();
        }
    }

    public class ButtonToNote implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int nr = 0;
            int[][] proc;
            try {
                ResultSet rs = bd.getNote();
                while (rs.next()) nr++;
                rs.close();
                proc = new int[nr][3];
                nr = 0;
                rs = bd.getNote();
                getProc(nr, proc, rs);
                rs.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            view.getNote().modificare(bd.getNote(), proc);
            view.doNote();
        }
    }

    public class ButtonToInscriere implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.doInscriere();
            view.getInscriereCurs().setMaterii(bd.getMaterii());
            view.getInscriereCurs().getCurs().addItemListener(e1 -> {
                if (e1.getStateChange() == ItemEvent.SELECTED) {
                    try {
                        ResultSet rs = bd.getDescriere((String) view.getInscriereCurs().getCurs().getSelectedItem());
                        if (rs.next())
                            view.getInscriereCurs().setDesc(rs.getString(1));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            view.getInscriereCurs().setDesc("");
        }
    }

    public class ButtonInscriere implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nume = (String) view.getInscriereCurs().getCurs().getSelectedItem();
            if (bd.alocareStudent(nume))
                view.getInscriereCurs().deselect();
        }
    }

    public class ButtonToDateUseri implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.doDateUseri();
            setUsers();
            view.getDateAdmin().deselect();
        }
    }

    class EmailChangeListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String em = view.getModificareDate().getEmail();
                if (bd.getDataFromEmail(em) != null)
                    view.getModificareDate().modificare(bd.getDataFromEmail(em));
            }
        }
    }

    class MaterieChangeListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                view.getCreareCurs().changeCurs();
            }
        }
    }

    class UserChangeListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String em = (String) view.getDateAdmin().getUser().getSelectedItem();
                ResultSet r = bd.getDataFromEmail(em);
                if (r != null)
                    view.getDateAdmin().modificare(r);
            }
        }
    }

    public class ButtonToGrup implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.doGrup();
            view.getGrup().setGrupuri(bd.getGrupuri());
        }
    }

    class GrupChangeListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                view.getGrup().modificare();
                if (view.getGrup().getGrupSelectedInscris()) {
                    int idGrup = view.getGrup().getGrupSelectedId();
                    view.getGrup().showGrup(bd.getMembri(idGrup), bd.getChat(idGrup), bd.getSugestieGrup(idGrup));
                }
            }
        }
    }

    public class ButtonInscriereGrup implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int idGrup = view.getGrup().getGrupSelectedId();
            if (!view.getGrup().getGrupSelectedInscris()) {
                bd.inscriereGrup(idGrup);
                view.getGrup().inscriereGrup();
                view.getGrup().showGrup(bd.getMembri(idGrup), bd.getChat(idGrup), bd.getSugestieGrup(idGrup));
            } else {
                bd.retragereGrup(idGrup);
                view.getGrup().retragereGrup();
            }
        }
    }

    public class ButtonTrimiteMesaj implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String mesaj = view.getGrup().getMesajText();
            int idGrup = view.getGrup().getGrupSelectedId();
            boolean ok = bd.trimiteMesaj(idGrup, mesaj);
            if (ok) {
                view.getGrup().modificare();
                view.getGrup().showGrup(bd.getMembri(idGrup), bd.getChat(idGrup), bd.getSugestieGrup(idGrup));
            }
        }
    }

    public class ButtonSwitchGrup implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int idGrup = view.getGrup().getGrupSelectedId();
            if (view.getGrup().triggerSwitch() == 0) {
                view.getGrup().setActivitati(bd.getActivitati(idGrup));
            } else {
                view.getGrup().showGrup(bd.getMembri(idGrup), bd.getChat(idGrup), bd.getSugestieGrup(idGrup));
            }
        }
    }

    public class ButtonInscriereActivitateGrup implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int idActivitate = view.getGrup().getActivitateId();
            view.getGrup().setActivitateData(bd.inscriereActivitateGrup(idActivitate));
        }
    }

    public class ButtonCreareActivitateGrup implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CreareActivitateGrup ca = view.getGrup().getCreareActivitateGrup();
            int idActivitate = bd.creareActivitateGrup(view.getGrup().getGrupSelectedId(), ca.getNume(), ca.getDataActivitate(), ca.getDurata(), ca.getDataExpirare(), ca.getNrMinStud());
            if (idActivitate != -1) {
                view.getGrup().setActivitati(bd.getActivitati(view.getGrup().getGrupSelectedId()));
                view.getGrup().setActivitate(null);
                view.getGrup().setActivitateData(bd.inscriereActivitateGrup(idActivitate));
            }
            ca.clearFields();
        }
    }

    public class ButtonToNotificari implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.doNotificari();
            view.getNotificari().setNotificari(bd.getNotificari());
        }
    }

    public class ButtonToActivitatiCurs implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.doActivitatiCurs();
            view.getActivitatiCurs().setCursuri(bd.getCursuri());
        }
    }

    class CursChangeListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                int idCurs = view.getActivitatiCurs().getSelectedCursId();
                view.getActivitatiCurs().setActivitati(bd.getActivitatiCurs(idCurs));
            }
        }
    }

    public class ButtonInscriereActivitateCurs implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int idActivitate = view.getActivitatiCurs().getActivitateId();
            view.getActivitatiCurs().setActivitateData(bd.inscriereActivitateCurs(idActivitate));
        }
    }

    public class ButtonCreareActivitateCurs implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CreareActivitateCurs ca = view.getActivitatiCurs().getCreareActivitateCurs();
            int idActivitate = bd.creareActivitateCurs(view.getActivitatiCurs().getSelectedCursId(), ca.getNume(), ca.getDataActivitate(), ca.getDurata(), ca.getNrMaxStud());
            if (idActivitate != -1) {
                view.getActivitatiCurs().setActivitati(bd.getActivitatiCurs(view.getActivitatiCurs().getSelectedCursId()));
                view.getActivitatiCurs().setActivitate(null);
            }
            ca.clearFields();
        }
    }

    public class ButtonToCalendar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.doCalendar();
            view.getCalendar().setActivitati(bd.getCalendarCurs());
        }
    }

    public class ButtonDescarcaCalendar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.getCalendar().doDescarca(bd.getCalendarCurs());
        }
    }

    public class ButtonDescarcaCatalog implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int nr = 0;
            int[][] proc;
            try {
                ResultSet rs = bd.getCatalog();
                while (rs.next()) nr++;
                rs.close();
                proc = new int[nr][3];
                nr = 0;
                rs = bd.getCatalog();
                getProc(nr, proc, rs);
                rs.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            view.getCatalog().doDescarca(bd.getCatalog(), proc);
        }
    }

    public class InvitaSugestie extends MouseAdapter {
        boolean ok = false;

        @Override
        public void mousePressed(MouseEvent e) {
            ok = true;
            super.mousePressed(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (ok) {
                int idSugestie = view.getGrup().getIdSugestie();
                String mesaj = view.getGrup().getSugestie().getText() + " a fost invitat in grup.";
                int idGrup = view.getGrup().getGrupSelectedId();
                boolean ok = bd.trimiteMesaj(idGrup, mesaj);
                if (ok) {
                    view.getGrup().modificare();
                    view.getGrup().showGrup(bd.getMembri(idGrup), bd.getChat(idGrup), bd.getSugestieGrup(idGrup));
                }
                bd.trimiteNotirifcare(idSugestie, "Ai fost invitat in grupul " + bd.getGrupName(idGrup));
            }
            super.mouseReleased(e);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            ok = false;
            super.mouseExited(e);
        }
    }
}
