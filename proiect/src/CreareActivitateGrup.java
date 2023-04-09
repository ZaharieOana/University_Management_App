import javax.swing.*;
import java.awt.*;
import java.sql.Time;
import java.sql.Timestamp;

import static java.awt.GridBagConstraints.*;

public class CreareActivitateGrup extends JPanel {
    private final JButton creaza = new JButton("Creaza");
    private final JLabel numeL = new JLabel("Nume:");
    private final JLabel dataActivitateL = new JLabel("Data:");
    private final JLabel dataExpirareL = new JLabel("Data expirare:");
    private final JLabel durataL = new JLabel("Durata:");
    private final JLabel nrMinStudL = new JLabel("Numar minim de studenti:");
    private final JTextField nume = new JTextField("");
    private final JHintTextField[] dataActivitate = new JHintTextField[5];//YYYY/MM/DD HH:MM
    private final JHintTextField[] dataExpirare = new JHintTextField[5];//YYYY/MM/DD HH:MM
    private final JHintTextField[] durata = new JHintTextField[2];//HH:MM
    private final JTextField nrMinStud = new JTextField("");
    private final JPanel gridPanel = new JPanel();
    private final JPanel dataActivitatePanel = new JPanel();
    private final JPanel dataExpirarePanel = new JPanel();
    private final JPanel durataPanel = new JPanel();

    public CreareActivitateGrup() {
        setBackground(Color.WHITE);
        add(creaza);
        SpringLayout sl = new SpringLayout();
        setLayout(sl);

        gridPanel.setLayout(new GridLayout(0, 2, 0, 10));
        gridPanel.setBackground(Color.WHITE);
        dataActivitatePanel.setLayout(new GridBagLayout());
        dataActivitatePanel.setBackground(Color.WHITE);
        dataExpirarePanel.setLayout(new GridBagLayout());
        dataExpirarePanel.setBackground(Color.WHITE);
        durataPanel.setLayout(new GridBagLayout());
        durataPanel.setBackground(Color.WHITE);

        numeL.setFont(new Font("TimesRoman", Font.BOLD, 20));
        dataActivitateL.setFont(new Font("TimesRoman", Font.BOLD, 20));
        dataExpirareL.setFont(new Font("TimesRoman", Font.BOLD, 20));
        durataL.setFont(new Font("TimesRoman", Font.BOLD, 20));
        nrMinStudL.setFont(new Font("TimesRoman", Font.BOLD, 20));

        dataActivitate[0] = new JHintTextField("YYYY");
        dataActivitate[1] = new JHintTextField("MM");
        dataActivitate[2] = new JHintTextField("DD");
        dataActivitate[3] = new JHintTextField("HH");
        dataActivitate[4] = new JHintTextField("MM");

        dataExpirare[0] = new JHintTextField("YYYY");
        dataExpirare[1] = new JHintTextField("MM");
        dataExpirare[2] = new JHintTextField("DD");
        dataExpirare[3] = new JHintTextField("HH");
        dataExpirare[4] = new JHintTextField("MM");

        durata[0] = new JHintTextField("HH");
        durata[1] = new JHintTextField("MM");

        for (int i = 0; i < 5; i++) {
            dataActivitatePanel.add(dataActivitate[i], new GridBagConstraints(RELATIVE, 0, 1, 1, 0.0, 0.0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            if (i < 2)
                dataActivitatePanel.add(new JLabel(" / "), new GridBagConstraints(RELATIVE, 0, 1, 1, 0.0, 0.0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            else if (i == 3)
                dataActivitatePanel.add(new JLabel(" : "), new GridBagConstraints(RELATIVE, 0, 1, 1, 0.0, 0.0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            else if (i == 2)
                dataActivitatePanel.add(new JLabel("   "), new GridBagConstraints(RELATIVE, 0, 1, 1, 0.0, 0.0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

            dataExpirarePanel.add(dataExpirare[i], new GridBagConstraints(RELATIVE, 0, 1, 1, 0.0, 0.0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            if (i < 2)
                dataExpirarePanel.add(new JLabel(" / "), new GridBagConstraints(RELATIVE, 0, 1, 1, 0.0, 0.0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            else if (i == 3)
                dataExpirarePanel.add(new JLabel(" : "), new GridBagConstraints(RELATIVE, 0, 1, 1, 0.0, 0.0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            else if (i == 2)
                dataExpirarePanel.add(new JLabel("   "), new GridBagConstraints(RELATIVE, 0, 1, 1, 0.0, 0.0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        }
        durataPanel.add(durata[0], new GridBagConstraints(RELATIVE, 0, 1, 1, 0.0, 0.0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        durataPanel.add(new JLabel(" : "), new GridBagConstraints(RELATIVE, 0, 1, 1, 0.0, 0.0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        durataPanel.add(durata[1], new GridBagConstraints(RELATIVE, 0, 1, 1, 0.0, 0.0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

        JPanel[] panel = new JPanel[3];
        SpringLayout[] springLayout = new SpringLayout[3];
        JPanel[] panels = new JPanel[3];
        panels[0] = dataActivitatePanel;
        panels[1] = dataExpirarePanel;
        panels[2] = durataPanel;
        for (int i = 0; i < 3; i++) {
            panel[i] = new JPanel();
            springLayout[i] = new SpringLayout();
            panel[i].setBackground(Color.WHITE);
            panel[i].setLayout(springLayout[i]);
            panel[i].add(panels[i]);
            springLayout[i].putConstraint(SpringLayout.NORTH, panels[i], 0, SpringLayout.NORTH, panel[i]);
            springLayout[i].putConstraint(SpringLayout.WEST, panels[i], 0, SpringLayout.WEST, panel[i]);
            springLayout[i].putConstraint(SpringLayout.SOUTH, panels[i], 0, SpringLayout.SOUTH, panel[i]);
        }


        gridPanel.add(numeL);
        gridPanel.add(nume);
        gridPanel.add(dataActivitateL);
        //gridPanel.add(dataActivitatePanel);
        gridPanel.add(panel[0]);
        gridPanel.add(dataExpirareL);
        //gridPanel.add(dataExpirarePanel);
        gridPanel.add(panel[1]);
        gridPanel.add(durataL);
        //gridPanel.add(durataPanel);
        gridPanel.add(panel[2]);
        gridPanel.add(nrMinStudL);
        gridPanel.add(nrMinStud);

        add(gridPanel);

        sl.putConstraint(SpringLayout.NORTH, gridPanel, 0, SpringLayout.NORTH, this);
        sl.putConstraint(SpringLayout.HORIZONTAL_CENTER, gridPanel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        sl.putConstraint(SpringLayout.WEST, gridPanel, 10, SpringLayout.WEST, this);
        sl.putConstraint(SpringLayout.EAST, gridPanel, -10, SpringLayout.EAST, this);

        sl.putConstraint(SpringLayout.SOUTH, creaza, 0, SpringLayout.SOUTH, this);
        sl.putConstraint(SpringLayout.HORIZONTAL_CENTER, creaza, 0, SpringLayout.HORIZONTAL_CENTER, this);
    }

    public JButton getCreaza() {
        return creaza;
    }

    public String getNume() {
        return nume.getText();
    }

    public Timestamp getDataActivitate() {
        return getTimestamp(dataActivitate);
    }

    public Timestamp getDataExpirare() {
        return getTimestamp(dataExpirare);
    }

    public Time getDurata() {
        String hours = String.format("%02d", Integer.valueOf(durata[0].getText()));
        String minutes = String.format("%02d", Integer.valueOf(durata[1].getText()));
        return Time.valueOf(hours + ":" + minutes + ":00");
    }

    public int getNrMinStud() {
        return Integer.parseInt(nrMinStud.getText());
    }

    private Timestamp getTimestamp(JHintTextField[] data) {
        String hours = String.format("%02d", Integer.valueOf(data[3].getText()));
        String minutes = String.format("%02d", Integer.valueOf(data[4].getText()));
        return Timestamp.valueOf(data[0].getText() + "-" + data[1].getText() + "-" + data[2].getText() + " " + hours + ":" + minutes + ":00");
    }

    public void clearFields() {
        for (int i = 0; i < 5; i++) {
            dataActivitate[i].reset();
            dataExpirare[i].reset();
        }
        durata[0].reset();
        durata[1].reset();
        nume.setText("");
        nrMinStud.setText("");
    }
}
