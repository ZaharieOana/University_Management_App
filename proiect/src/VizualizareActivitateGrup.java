import javax.swing.*;
import java.awt.*;
import java.sql.Time;
import java.sql.Timestamp;

public class VizualizareActivitateGrup extends JPanel {
    private final JButton inscriere = new JButton("Inscrie-te");
    private final JLabel nume = new JLabel();
    private final JLabel dataActivitate = new JLabel();
    private final JLabel dataExpirare = new JLabel();
    private final JLabel durata = new JLabel();
    private final JLabel nrMinStud = new JLabel();
    private final JLabel nrStud = new JLabel();

    public VizualizareActivitateGrup() {
        setBackground(Color.WHITE);
        add(inscriere);
        SpringLayout sl = new SpringLayout();
        setLayout(sl);

        nume.setFont(new Font("TimesRoman", Font.BOLD, 20));
        dataActivitate.setFont(new Font("TimesRoman", Font.BOLD, 20));
        dataExpirare.setFont(new Font("TimesRoman", Font.BOLD, 20));
        durata.setFont(new Font("TimesRoman", Font.BOLD, 20));
        nrMinStud.setFont(new Font("TimesRoman", Font.BOLD, 20));
        nrStud.setFont(new Font("TimesRoman", Font.BOLD, 20));

        add(nume);
        add(dataActivitate);
        add(dataExpirare);
        add(durata);
        add(nrMinStud);
        add(nrStud);

        sl.putConstraint(SpringLayout.NORTH, nume, 0, SpringLayout.NORTH, this);
        sl.putConstraint(SpringLayout.WEST, nume, 0, SpringLayout.WEST, this);
        sl.putConstraint(SpringLayout.NORTH, dataActivitate, 0, SpringLayout.SOUTH, nume);
        sl.putConstraint(SpringLayout.WEST, dataActivitate, 0, SpringLayout.WEST, this);
        sl.putConstraint(SpringLayout.NORTH, dataExpirare, 0, SpringLayout.SOUTH, dataActivitate);
        sl.putConstraint(SpringLayout.WEST, dataExpirare, 0, SpringLayout.WEST, this);
        sl.putConstraint(SpringLayout.NORTH, durata, 0, SpringLayout.SOUTH, dataExpirare);
        sl.putConstraint(SpringLayout.WEST, durata, 0, SpringLayout.WEST, this);
        sl.putConstraint(SpringLayout.NORTH, nrMinStud, 0, SpringLayout.SOUTH, durata);
        sl.putConstraint(SpringLayout.WEST, nrMinStud, 0, SpringLayout.WEST, this);
        sl.putConstraint(SpringLayout.NORTH, nrStud, 0, SpringLayout.SOUTH, nrMinStud);
        sl.putConstraint(SpringLayout.WEST, nrStud, 0, SpringLayout.WEST, this);

        sl.putConstraint(SpringLayout.SOUTH, inscriere, 0, SpringLayout.SOUTH, this);
        sl.putConstraint(SpringLayout.EAST, inscriere, 0, SpringLayout.EAST, this);
    }

    public JButton getInscriere() {
        return inscriere;
    }

    public void setButton(boolean flag) {
        inscriere.setEnabled(!flag);
        inscriere.setText((!flag) ? "Inscrie-te" : "Inscris");
    }

    public void setNume(String data) {
        nume.setText(data);
    }

    public void setDataActivitate(Timestamp data) {
        dataActivitate.setText("Data: " + data.toString());
    }

    public void setDataExpirare(Timestamp data) {
        dataExpirare.setText("Data expirare: " + data.toString());
    }

    public void setDurata(Time data) {
        durata.setText("Durata: " + data.toString());
    }

    public void setNrMinStud(int data) {
        nrMinStud.setText("Numar minim de studenti: " + data);
    }

    public void setNrStud(int data) {
        nrStud.setText("Numar de studenti participanti: " + data);
    }
}
