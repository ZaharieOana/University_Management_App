import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;

public class AdaugareNote extends JPanel {
    private final JLabel titlu = new JLabel("Adauga Note");
    private final JLabel materieL = new JLabel("Materie:");
    private final JComboBox<String> materieC = new JComboBox<>();
    private final JLabel studentL = new JLabel("Student:");
    private final JComboBox<String> studentC = new JComboBox<>();
    private final JCheckBox curs = new JCheckBox("Curs");
    private final JCheckBox seminar = new JCheckBox("Seminar");
    private final JCheckBox laborator = new JCheckBox("Laborator");
    private final JTextField cursT = new JTextField("0");
    private final JTextField seminarT = new JTextField("0");
    private final JTextField laboratorT = new JTextField("0");
    private final JLabel cursL = new JLabel("0");
    private final JLabel seminarL = new JLabel("0");
    private final JLabel laboratorL = new JLabel("0");
    private final SpringLayout springLayout = new SpringLayout();
    private final JPanel gridPanel1 = new JPanel();
    private final JPanel gridPanel2 = new JPanel();
    private final JButton back = new JButton("Back");
    private final JButton addB = new JButton("Noteaza");

    AdaugareNote() {
        setLayout(springLayout);
        setBackground(Color.WHITE);
        gridPanel1.setLayout(new GridLayout(2, 2, 5, 5));
        gridPanel1.setBackground(Color.WHITE);
        gridPanel1.add(materieL);
        gridPanel1.add(materieC);
        gridPanel1.add(studentL);
        gridPanel1.add(studentC);
        gridPanel2.setLayout(new GridLayout(3, 3, 5, 5));
        gridPanel2.setBackground(Color.WHITE);
        gridPanel2.add(curs);
        gridPanel2.add(seminar);
        gridPanel2.add(laborator);
        gridPanel2.add(cursT);
        gridPanel2.add(seminarT);
        gridPanel2.add(laboratorT);
        gridPanel2.add(cursL);
        gridPanel2.add(seminarL);
        gridPanel2.add(laboratorL);

        gridPanel1.setPreferredSize(new Dimension(300, 70));
        gridPanel2.setPreferredSize(new Dimension(400, 100));

        materieL.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        materieC.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        studentL.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        studentC.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        titlu.setFont(new Font("TimesRoman", Font.BOLD, 35));
        curs.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        seminar.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        laborator.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        cursT.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        seminarT.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        laboratorT.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        cursL.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        seminarL.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        laboratorL.setFont(new Font("TimesRoman", Font.PLAIN, 20));

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
        deselect();
    }

    public JButton getBack() {
        return back;
    }

    public JButton getAddNote() {
        return addB;
    }

    public JComboBox<String> getMaterie() {
        return materieC;
    }

    public JComboBox<String> getStudent() {
        return studentC;
    }

    public JCheckBox getCurs() {
        return curs;
    }

    public JCheckBox getSeminar() {
        return seminar;
    }

    public JCheckBox getLaborator() {
        return laborator;
    }

    public void setMaterii(ResultSet rs) {
        try {
            while (rs.next()) materieC.addItem(rs.getString(1));
            materieC.setSelectedIndex(-1);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(10);
        }

    }

    public void setStudenti(ResultSet rs) {
        try {
            while (rs.next()) studentC.addItem(rs.getString(1));
            studentC.setSelectedIndex(-1);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(11);
        }

    }

    public void afterNoatre() {
        curs.setSelected(false);
        seminar.setSelected(false);
        laborator.setSelected(false);
        cursT.setText("0");
        seminarT.setText("0");
        laboratorT.setText("0");
        studentC.setSelectedIndex(-1);
        deselect();
    }

    public void deselect() {
        curs.setVisible(false);
        seminar.setVisible(false);
        laborator.setVisible(false);
        cursT.setVisible(false);
        seminarT.setVisible(false);
        laboratorT.setVisible(false);
        cursL.setVisible(false);
        seminarL.setVisible(false);
        laboratorL.setVisible(false);
        materieC.removeAllItems();
        studentC.removeAllItems();
        curs.setSelected(false);
        seminar.setSelected(false);
        laborator.setSelected(false);
    }

    public void setProcente(int[] val) {
        cursL.setText(val[0] + "%");
        seminarL.setText(val[1] + "%");
        laboratorL.setText(val[2] + "%");
    }

    public double[] getNote() {
        return new double[]{Double.parseDouble(cursT.getText()), Double.parseDouble(seminarT.getText()), Double.parseDouble(laboratorT.getText())};
    }

    public boolean[] getActivitate() {
        return new boolean[]{curs.isSelected(), seminar.isSelected(), laborator.isSelected()};
    }

    private class CursCheck implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            cursT.setVisible(curs.isSelected());
            cursL.setVisible(curs.isSelected());
            if (!curs.isSelected()) cursT.setText("0");
        }
    }

    private class SeminarCheck implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            seminarT.setVisible(seminar.isSelected());
            seminarL.setVisible(seminar.isSelected());
            if (!seminar.isSelected()) seminarT.setText("0");
        }
    }

    private class LabCheck implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            laboratorT.setVisible(laborator.isSelected());
            laboratorL.setVisible(laborator.isSelected());
            if (!laborator.isSelected()) laboratorT.setText("0");
        }
    }
}
