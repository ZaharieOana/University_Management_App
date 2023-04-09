import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class ModificareDate extends JPanel {
    private final JLabel titlu = new JLabel("Modifica datele");
    private final JComboBox<String> email = new JComboBox<>();
    private final JLabel emailL = new JLabel("email:");
    private final JLabel[] labelsL = new JLabel[9];
    private final JTextField[] texts = new JTextField[9];
    private final JPanel gridPanel = new JPanel();
    private final JPanel gridPanel1 = new JPanel();
    private final SpringLayout springLayout = new SpringLayout();
    private final JButton back = new JButton("Back");
    private final JButton modify = new JButton("Modifica");
    private final JButton sterge = new JButton("Sterge");

    ModificareDate() {
        setLayout(springLayout);
        setBackground(Color.WHITE);
        gridPanel.setLayout(new GridLayout(11, 2, 5, 5));
        gridPanel.setBackground(Color.WHITE);
        gridPanel1.setLayout(new GridLayout(1, 2, 5, 5));
        gridPanel1.setBackground(Color.WHITE);

        gridPanel.setPreferredSize(new Dimension(300, 300));
        gridPanel1.setPreferredSize(new Dimension(300, 25));

        emailL.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        email.setFont(new Font("TimesRoman", Font.PLAIN, 15));

        gridPanel1.add(emailL, 0);
        gridPanel1.add(email, 1);
        for (int i = 0; i < 9; i++) {
            labelsL[i] = new JLabel("");
            texts[i] = new JTextField("");
            labelsL[i].setFont(new Font("TimesRoman", Font.PLAIN, 15));
            texts[i].setFont(new Font("TimesRoman", Font.PLAIN, 15));
            gridPanel.add(labelsL[i]);
            gridPanel.add(texts[i]);
            labelsL[i].setVisible(false);
            texts[i].setVisible(false);
        }

        back.setPreferredSize(new Dimension(110, 40));
        back.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        titlu.setFont(new Font("TimesRoman", Font.BOLD, 35));
        add(gridPanel);
        add(gridPanel1);
        add(back);
        add(titlu);
        modify.setPreferredSize(new Dimension(110, 40));
        modify.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        add(modify);
        sterge.setPreferredSize(new Dimension(110, 40));
        sterge.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        add(sterge);

        springLayout.putConstraint(SpringLayout.NORTH, titlu, 10, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titlu, 0, SpringLayout.HORIZONTAL_CENTER, this);

        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, gridPanel1, 0, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.NORTH, gridPanel1, 20, SpringLayout.SOUTH, titlu);

        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, gridPanel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.NORTH, gridPanel, 0, SpringLayout.SOUTH, gridPanel1);

        springLayout.putConstraint(SpringLayout.SOUTH, back, -20, SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.EAST, back, -20, SpringLayout.EAST, this);

        springLayout.putConstraint(SpringLayout.SOUTH, modify, -20, SpringLayout.NORTH, back);
        springLayout.putConstraint(SpringLayout.EAST, modify, -20, SpringLayout.EAST, this);

        springLayout.putConstraint(SpringLayout.SOUTH, sterge, -20, SpringLayout.NORTH, modify);
        springLayout.putConstraint(SpringLayout.EAST, sterge, -20, SpringLayout.EAST, this);

    }

    public final JComboBox<String> getComboEmail() {
        return email;
    }

    public final String getEmail() {
        return (String) email.getSelectedItem();
    }

    public JButton getBack() {
        return back;
    }

    public JButton getModify() {
        return modify;
    }

    public JButton getSterge() {
        return sterge;
    }

    public void modificare(ResultSet rs) {
        for (int i = 0; i < 9; i++) {
            texts[i].setText("");
            labelsL[i].setText("");
            labelsL[i].setVisible(false);
            texts[i].setVisible(false);
        }
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int colCount = rsmd.getColumnCount();
            rs.next();
            for (int i = 0; i < colCount - 3; i++) {
                if (i < 5) {
                    labelsL[i].setText(rsmd.getColumnName(i + 1) + ":  ");
                    Object obj = rs.getObject(i + 1);
                    if (obj != null) texts[i].setText(obj.toString());
                } else if (i < 6) {
                    labelsL[i].setText(rsmd.getColumnName(i + 2) + ":  ");
                    Object obj = rs.getObject(i + 2);
                    if (obj != null) texts[i].setText(obj.toString());
                } else if (colCount > 11) {
                    labelsL[i].setText(rsmd.getColumnName(i + 4) + ":  ");
                    Object obj = rs.getObject(i + 4);
                    if (obj != null) texts[i].setText(obj.toString());
                } else {
                    labelsL[i].setText(rsmd.getColumnName(i + 3) + ":  ");
                    Object obj = rs.getObject(i + 3);
                    if (obj != null) texts[i].setText(obj.toString());
                }
                labelsL[i].setVisible(true);
                texts[i].setVisible(true);
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(4);
        }

    }

    public void setEmails(ResultSet rs) {
        try {
            while (rs.next())
                email.addItem(rs.getString(1));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(10);
        }
    }

    public String[] getTexts(TipUser tip) {
        String[] rez = new String[switch (tip) {
            case Student -> 7;
            case Profesor -> 9;
            case Admin, SuperAdmin -> 6;
        }];
        int i = 0;
        switch (tip) {
            case Student -> rez[i++] = texts[6].getText();
            case Profesor -> {
                rez[i++] = texts[6].getText();
                rez[i++] = texts[7].getText();
                rez[i++] = texts[8].getText();
            }
        }
        rez[i++] = texts[0].getText();
        rez[i++] = texts[1].getText();
        rez[i++] = texts[2].getText();
        rez[i++] = texts[3].getText();
        rez[i++] = texts[4].getText();
        rez[i] = texts[5].getText();
        return rez;
    }

    public void reinitializare() {
        for (int i = 0; i < 9; i++) {
            texts[i].setText("");
            labelsL[i].setText("");
            labelsL[i].setVisible(false);
            texts[i].setVisible(false);
        }
        email.setSelectedIndex(-1);
    }
}
