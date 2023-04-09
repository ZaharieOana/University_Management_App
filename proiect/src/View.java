import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class View extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel();
    private final LogIn panelLog = new LogIn();
    private final SignIn panelSign = new SignIn();
    private final DatePersoana datePersoana = new DatePersoana();
    private final HomePage homePage = new HomePage();
    private final CreareMaterie creareMaterie = new CreareMaterie();
    private final ModificareDate modificareDate = new ModificareDate();
    private final CreareCurs creareCurs = new CreareCurs();
    private final AdaugareNote adaugareNote = new AdaugareNote();
    private final Catalog catalog = new Catalog();
    private final Note note = new Note();
    private final InscriereCurs inscriereCurs = new InscriereCurs();
    private final DateAdmin dateAdmin = new DateAdmin();
    private final Grup grup = new Grup();
    private final Notificari notificari = new Notificari();
    private final ActivitatiCurs activitatiCurs = new ActivitatiCurs();
    private final Calendar calendar = new Calendar();

    View() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 500);
        setTitle("Proiect");
        ImageIcon icon = new ImageIcon("utcn.jpg");
        this.setIconImage(icon.getImage());
        setLocationRelativeTo(null);
        cardPanel.setLayout(cardLayout);
        cardPanel.add("logging", panelLog);
        cardPanel.add("signing", panelSign);
        cardPanel.add("date", datePersoana);
        cardPanel.add("home", homePage);
        cardPanel.add("creareMaterie", creareMaterie);
        cardPanel.add("modificare", modificareDate);
        cardPanel.add("creareCurs", creareCurs);
        cardPanel.add("adaugareNote", adaugareNote);
        cardPanel.add("catalog", catalog);
        cardPanel.add("note", note);
        cardPanel.add("inscriereCurs", inscriereCurs);
        cardPanel.add("dateAdmin", dateAdmin);
        cardPanel.add("grup", grup);
        cardPanel.add("notificari", notificari);
        cardPanel.add("activitatiCurs", activitatiCurs);
        cardPanel.add("calendar", calendar);
        //cardLayout.show(cardPanel, "dateAdmin");

        this.add(cardPanel);
        this.setVisible(true);
    }

    public CreareMaterie getCreareMaterie() {
        return creareMaterie;
    }

    public HomePage getHomePage() {
        return homePage;
    }

    public ModificareDate getModificareDate() {
        return modificareDate;
    }

    public CreareCurs getCreareCurs() {
        return creareCurs;
    }

    public AdaugareNote getAdaugareNote() {
        return adaugareNote;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public Note getNote() {
        return note;
    }

    public InscriereCurs getInscriereCurs() {
        return inscriereCurs;
    }

    public DateAdmin getDateAdmin() {
        return dateAdmin;
    }

    public Grup getGrup() {
        return grup;
    }

    public Notificari getNotificari() {
        return notificari;
    }

    public ActivitatiCurs getActivitatiCurs() {
        return activitatiCurs;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public String getEmail() {
        return panelLog.getEmail();
    }

    public String getPassword() {
        return panelLog.getPassword();
    }

    public void setButtonLogIn(Controller.ButtonLogIn buttonLogIn) {
        panelLog.setButtonLogIn(buttonLogIn);
    }

    //public void setLogIn(boolean valid, ResultSet rs, boolean ad){
    public void setLogIn(boolean valid, ResultSet rs, TipUser tip) {
        panelLog.setLogIn(valid);
        if (valid) {
            datePersoana.modificare(rs);
            cardLayout.show(cardPanel, "home");
            System.out.println("logat");
            homePage.setVizibilButoane(tip);
            activitatiCurs.setTipUser(tip);
        }
    }

    public void goToDate() {
        cardLayout.show(cardPanel, "date");
    }

    public void setButtonGoToDate(Controller.ButtonToDate buttonGoToDate) {
        homePage.setButtonGoToDate(buttonGoToDate);
    }

    public void goHome() {
        cardLayout.show(cardPanel, "home");
        panelSign.resetAdd();
    }

    public void setButtonHome(Controller.ButtonBackHome buttonBackHome) {
        datePersoana.setButtonBackHome(buttonBackHome);
        panelSign.setButtonBackHome(buttonBackHome);
        creareMaterie.setButtonBackHome(buttonBackHome);
    }

    public void doDelogare() {
        cardLayout.show(cardPanel, "logging");
    }

    public void setButtonDelogare(Controller.ButtonDelogare buttonDelogare) {
        homePage.setButtonDelogare(buttonDelogare);
    }

    public void addAdaugareUser(Controller.ButtonAdaugare buttonAdaugare) {
        homePage.setButtonAdaugare(buttonAdaugare);
    }

    public void addAdaugareMaterie(Controller.ButtonAdaugareMaterie buttonAddMaterie) {
        homePage.setButtonAddMaterie(buttonAddMaterie);
    }

    public void doAdaugareMaterie() {
        cardLayout.show(cardPanel, "creareMaterie");
    }

    public void doAdaugareUser() {
        cardLayout.show(cardPanel, "signing");
        panelSign.resetAdd();
    }

    public void doNotare() {
        cardLayout.show(cardPanel, "adaugareNote");
        adaugareNote.deselect();
    }

    public void doCatalog() {
        cardLayout.show(cardPanel, "catalog");
    }

    public void doNote() {
        cardLayout.show(cardPanel, "note");
    }

    public void doGrup() {
        cardLayout.show(cardPanel, "grup");
    }

    public void doNotificari() {
        cardLayout.show(cardPanel, "notificari");
    }

    public void doActivitatiCurs() {
        cardLayout.show(cardPanel, "activitatiCurs");
    }

    public void doCalendar() {
        cardLayout.show(cardPanel, "calendar");
    }

    public void doInscriere() {
        cardLayout.show(cardPanel, "inscriereCurs");
        inscriereCurs.deselect();
    }

    public void doDateUseri() {
        cardLayout.show(cardPanel, "dateAdmin");
    }

    public void resetAdd() {
        panelSign.resetAdd();
    }

    public void setButtonAddUser(Controller.ButtonAddUser buttonAddUser) {
        panelSign.setButtonAddUser(buttonAddUser);
    }

    public TipUser getSignInTipUser() {
        return panelSign.getTipUser();
    }

    public String[] getSignInValues() {
        return panelSign.getValues();
    }

    public void setButtonAddMaterie(Controller.ButtonAddMaterie buttonAddMaterie) {
        creareMaterie.setButtonAddMaterie(buttonAddMaterie);
    }

    public void goAddCurs() {
        cardLayout.show(cardPanel, "creareCurs");
        creareCurs.deselect();
    }

    public void goModify() {
        cardLayout.show(cardPanel, "modificare");
    }
}
