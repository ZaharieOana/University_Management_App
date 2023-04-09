import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;

public class CreareCurs extends JPanel {
    private final JLabel titlu = new JLabel("Creeaza un curs");
    private final JComboBox<String> materie = new JComboBox<>();
    private final JLabel materieL = new JLabel("Materie:");
    private final JLabel nrStudL = new JLabel("Numar maxim studenti:");
    private final JTextField nrStudT = new JTextField("");
    private final JCheckBox curs = new JCheckBox("Curs");
    private final JCheckBox seminar = new JCheckBox("Seminar");
    private final JCheckBox laborator = new JCheckBox("Laborator");
    private final JTextField cursT = new JTextField("0");
    private final JTextField seminarT = new JTextField("0");
    private final JTextField laboratorT = new JTextField("0");
    private final SpringLayout springLayout = new SpringLayout();
    private final JPanel gridPanel1 = new JPanel();
    private final JPanel gridPanel2 = new JPanel();
    private final JButton back = new JButton("Back");
    private final JButton addB = new JButton("Creeaza");

    CreareCurs() {
        setLayout(springLayout);
        setBackground(Color.WHITE);
        gridPanel1.setLayout(new GridLayout(2, 2, 5, 5));
        gridPanel1.setBackground(Color.WHITE);
        gridPanel1.add(materieL);
        gridPanel1.add(materie);
        gridPanel1.add(nrStudL);
        gridPanel1.add(nrStudT);
        gridPanel2.setLayout(new GridLayout(2, 3, 5, 5));
        gridPanel2.setBackground(Color.WHITE);
        gridPanel2.add(curs);
        gridPanel2.add(seminar);
        gridPanel2.add(laborator);
        gridPanel2.add(cursT);
        gridPanel2.add(seminarT);
        gridPanel2.add(laboratorT);

        gridPanel1.setPreferredSize(new Dimension(500, 70));
        gridPanel2.setPreferredSize(new Dimension(400, 70));

        materieL.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        materie.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        nrStudL.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        nrStudT.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        titlu.setFont(new Font("TimesRoman", Font.BOLD, 35));
        curs.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        seminar.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        laborator.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        cursT.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        seminarT.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        laboratorT.setFont(new Font("TimesRoman", Font.PLAIN, 20));

        back.setPreferredSize(new Dimension(100, 40));
        back.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        add(back);
        addB.setPreferredSize(new Dimension(100, 40));
        addB.setFont(new Font("TimesRoman", Font.PLAIN, 17));
        add(addB);

        add(titlu);
        add(gridPanel1);
        add(gridPanel2);

        springLayout.putConstraint(SpringLayout.NORTH, titlu, 10, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titlu, 0, SpringLayout.HORIZONTAL_CENTER, this);

        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, gridPanel1, 0, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.SOUTH, gridPanel1, -50, SpringLayout.VERTICAL_CENTER, this);

        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, gridPanel2, 0, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.NORTH, gridPanel2, 0, SpringLayout.VERTICAL_CENTER, this);

        springLayout.putConstraint(SpringLayout.SOUTH, back, -20, SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.EAST, back, -20, SpringLayout.EAST, this);

        springLayout.putConstraint(SpringLayout.SOUTH, addB, -20, SpringLayout.NORTH, back);
        springLayout.putConstraint(SpringLayout.EAST, addB, -20, SpringLayout.EAST, this);

        curs.addItemListener(new CursCheck());
        seminar.addItemListener(new SeminarCheck());
        laborator.addItemListener(new LabCheck());
        cursT.setVisible(false);
        seminarT.setVisible(false);
        laboratorT.setVisible(false);

    }

    public final JComboBox<String> getComboMaterie() {
        return materie;
    }

    public JButton getBack() {
        return back;
    }

    public JButton getAddCurs() {
        return addB;
    }

    public void setMaterii(ResultSet rs) {
        materie.removeAllItems();
        try {
            while (rs.next())
                materie.addItem(rs.getString(1));
            materie.setSelectedIndex(-1);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(10);
        }

    }

    public void deselect() {
        curs.setSelected(false);
        seminar.setSelected(false);
        laborator.setSelected(false);
        materie.setSelectedIndex(-1);
        nrStudT.setText("");
    }

    public void changeCurs() {
        curs.setSelected(false);
        seminar.setSelected(false);
        laborator.setSelected(false);
        nrStudT.setText("");
    }

    public String[] getValues() {
        String[] rez = new String[8];
        rez[0] = (String) materie.getSelectedItem();
        rez[1] = nrStudT.getText();
        rez[2] = String.valueOf(curs.isSelected());
        rez[3] = String.valueOf(seminar.isSelected());
        rez[4] = String.valueOf(laborator.isSelected());
        rez[5] = cursT.getText();
        rez[6] = seminarT.getText();
        rez[7] = laboratorT.getText();
        return rez;
    }

    private class CursCheck implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            cursT.setVisible(curs.isSelected());
            if (!curs.isSelected())
                cursT.setText("0");
        }
    }

    private class SeminarCheck implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            seminarT.setVisible(seminar.isSelected());
            if (!seminar.isSelected())
                seminarT.setText("0");
        }
    }

    private class LabCheck implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            laboratorT.setVisible(laborator.isSelected());
            if (!laborator.isSelected())
                laboratorT.setText("0");
        }
    }
}
