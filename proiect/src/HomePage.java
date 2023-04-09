import javax.swing.*;
import java.awt.*;

public class HomePage extends JPanel {
    private final JButton date = new JButton("Date");
    private final JButton delogare = new JButton("Delogare");
    private final JButton adaugare = new JButton("Adauga user");
    private final JButton adaugareM = new JButton("Adauga materie");
    private final JButton modificare = new JButton("Modifica date");
    private final JButton adaugareCurs = new JButton("Adauga curs");
    private final JButton notare = new JButton("Adauga note");
    private final JButton catalog = new JButton("Catalog");
    private final JButton note = new JButton("Note");
    private final JButton grup = new JButton("Grupuri");
    private final JButton notificari = new JButton("Notificari");
    private final JButton notificari2 = new JButton("Notificari");
    private final JButton activitatiCurs = new JButton("Activitati cursuri");
    private final JButton activitatiCurs2 = new JButton("Activitati cursuri");
    private final JButton calendar = new JButton("Calendar");
    private final JButton calendar2 = new JButton("Calendar");
    private final JButton inscriere = new JButton("Inscriere cursuri");
    private final JButton dateUseri = new JButton("Date Useri");
    private final JPanel panel = new JPanel();
    private final JPanel panelAdmin = new JPanel();
    private final JPanel panelProf = new JPanel();
    private final JPanel panelStudent = new JPanel();
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final SpringLayout layout = new SpringLayout();

    HomePage() {
        setBackground(Color.white);
        setLayout(layout);
        add(panel);
        //layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, panel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        //layout.putConstraint(SpringLayout.VERTICAL_CENTER, panel, 0, SpringLayout.VERTICAL_CENTER, this);
        panel.setBackground(Color.white);
        panel.setLayout(new GridLayout(0, 2, 5, 5));
        add(cardPanel);
        cardPanel.setBackground(Color.white);
        cardPanel.setLayout(cardLayout);

        panelAdmin.setBackground(Color.white);
        panelAdmin.setLayout(new GridLayout(0, 2, 5, 5));
        panelProf.setBackground(Color.white);
        panelProf.setLayout(new GridLayout(0, 2, 5, 5));
        panelStudent.setBackground(Color.white);
        panelStudent.setLayout(new GridLayout(0, 2, 5, 5));
        cardPanel.add("admin", panelAdmin);
        cardPanel.add("prof", panelProf);
        cardPanel.add("stud", panelStudent);

        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, cardPanel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, cardPanel, -5, SpringLayout.VERTICAL_CENTER, this);
        layout.putConstraint(SpringLayout.WEST, panel, 0, SpringLayout.WEST, cardPanel);
        layout.putConstraint(SpringLayout.EAST, panel, 0, SpringLayout.EAST, cardPanel);
        layout.putConstraint(SpringLayout.SOUTH, panel, -5, SpringLayout.NORTH, cardPanel);

        panel.add(date);
        panel.add(delogare);

        panelAdmin.add(adaugare);
        panelAdmin.add(adaugareM);
        panelAdmin.add(modificare);
        panelAdmin.add(dateUseri);

        panelProf.add(notificari);
        panelProf.add(calendar);
        panelProf.add(catalog);
        panelProf.add(adaugareCurs);
        panelProf.add(activitatiCurs);
        panelProf.add(notare);

        panelStudent.add(notificari2);
        panelStudent.add(calendar2);
        panelStudent.add(note);
        panelStudent.add(grup);
        panelStudent.add(activitatiCurs2);
        panelStudent.add(inscriere);
    }

    public JButton getModificare() {
        return modificare;
    }

    public JButton getAddCurs() {
        return adaugareCurs;
    }

    public JButton getNotare() {
        return notare;
    }

    public JButton getCatalog() {
        return catalog;
    }

    public JButton getNote() {
        return note;
    }

    public JButton getGrup() {
        return grup;
    }

    public JButton getInscriere() {
        return inscriere;
    }

    public JButton getDateUseri() {
        return dateUseri;
    }

    public JButton getNotificari() {
        return notificari;
    }

    public JButton getActivitatiCurs() {
        return activitatiCurs;
    }

    public JButton getCalendar() {
        return calendar;
    }

    public void setButtonGoToDate(Controller.ButtonToDate b) {
        date.addActionListener(b);
    }

    public void setButtonDelogare(Controller.ButtonDelogare b) {
        delogare.addActionListener(b);
    }

    public void setButtonAdaugare(Controller.ButtonAdaugare b) {
        adaugare.addActionListener(b);
    }

    public void setButtonAddMaterie(Controller.ButtonAdaugareMaterie b) {
        adaugareM.addActionListener(b);
    }

    public JButton getNotificari2() {
        return notificari2;
    }

    public JButton getActivitatiCurs2() {
        return activitatiCurs2;
    }

    public JButton getCalendar2() {
        return calendar2;
    }

    public void setVizibilButoane(TipUser tip) {
        if (tip == TipUser.Admin || tip == TipUser.SuperAdmin) {
            cardLayout.show(cardPanel, "admin");
        } else if (tip == TipUser.Profesor) {
            cardLayout.show(cardPanel, "prof");
        } else {
            cardLayout.show(cardPanel, "stud");
        }
    }
}