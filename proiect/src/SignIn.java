import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SignIn extends JPanel {
    private final JLabel[] labels = new JLabel[14];
    private final JTextField[] texts = new JTextField[13];
    private final JComboBox<String> statutC = new JComboBox<>();
    private final JButton back = new JButton("Back");
    private final JButton addU = new JButton("Adauga");
    private final SpringLayout springLayout = new SpringLayout();
    private final JLabel titlu = new JLabel("Adauga User");
    private final JPanel gridPanel = new JPanel();
    private final JPanel studentPanel = new JPanel();
    private final JPanel profesorPanel = new JPanel();
    private final JPanel adminPanel = new JPanel();
    private TipUser tipUser;

    SignIn() {
        setLayout(springLayout);
        setBackground(Color.WHITE);
        gridPanel.setLayout(new GridLayout(10, 2, 5, 5));
        gridPanel.setBackground(Color.WHITE);
        studentPanel.setLayout(new GridLayout(1, 2, 5, 5));
        profesorPanel.setLayout(new GridLayout(3, 2, 5, 5));
        studentPanel.setBackground(Color.white);
        profesorPanel.setBackground(Color.white);
        adminPanel.setBackground(Color.white);
        this.setBackground(Color.white);

        gridPanel.setPreferredSize(new Dimension(300, 250));
        studentPanel.setPreferredSize(new Dimension(300, 20));
        profesorPanel.setPreferredSize(new Dimension(300, 75));

        statutC.addItem("Student");
        statutC.addItem("Profesor");
        statutC.addItem("Administrator");
        statutC.addItemListener(new ItemChangeListener());

        labels[0] = new JLabel("Nume: ");
        labels[1] = new JLabel("Prenume: ");
        labels[2] = new JLabel("Parola: ");
        labels[3] = new JLabel("CNP:");
        labels[4] = new JLabel("Adresa:");
        labels[5] = new JLabel("Numar telefon:");
        labels[6] = new JLabel("E-mail:");
        labels[7] = new JLabel("IBAN:");
        labels[8] = new JLabel("Numar contract:");
        labels[9] = new JLabel("An studiu:");
        labels[10] = new JLabel("Departament:");
        labels[11] = new JLabel("Numar minim ore predate:");
        labels[12] = new JLabel("Numar maxim ore predate:");
        labels[13] = new JLabel("Statut:");

        back.setPreferredSize(new Dimension(100, 40));
        back.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        add(back);
        addU.setPreferredSize(new Dimension(100, 40));
        addU.setFont(new Font("TimesRoman", Font.PLAIN, 19));
        add(addU);

        for (int i = 0; i < 13; i++) {
            texts[i] = new JTextField();
            labels[i].setFont(new Font("TimesRoman", Font.PLAIN, 12));
            texts[i].setFont(new Font("TimesRoman", Font.PLAIN, 12));
        }
        labels[13].setFont(new Font("TimesRoman", Font.PLAIN, 12));

        for (int i = 0; i < 9; i++) {
            gridPanel.add(labels[i]);
            gridPanel.add(texts[i]);
        }
        gridPanel.add(labels[13]);
        gridPanel.add(statutC);
        add(gridPanel);

        titlu.setFont(new Font("TimesRoman", Font.BOLD, 35));
        add(titlu);

        studentPanel.add(labels[9]);
        studentPanel.add(texts[9]);

        for (int i = 10; i < 13; i++) {
            profesorPanel.add(labels[i]);
            profesorPanel.add(texts[i]);
        }

        add(studentPanel);
        add(profesorPanel);

        springLayout.putConstraint(SpringLayout.NORTH, titlu, 0, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titlu, 0, SpringLayout.HORIZONTAL_CENTER, this);

        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, gridPanel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.NORTH, gridPanel, 20, SpringLayout.SOUTH, titlu);

        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, studentPanel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.NORTH, studentPanel, 0, SpringLayout.SOUTH, gridPanel);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, profesorPanel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.NORTH, profesorPanel, 0, SpringLayout.SOUTH, gridPanel);

        springLayout.putConstraint(SpringLayout.SOUTH, back, -20, SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.EAST, back, -20, SpringLayout.EAST, this);

        springLayout.putConstraint(SpringLayout.SOUTH, addU, -20, SpringLayout.NORTH, back);
        springLayout.putConstraint(SpringLayout.EAST, addU, -20, SpringLayout.EAST, this);

        afisare();
    }

    public void setButtonBackHome(Controller.ButtonBackHome buttonBackHome) {
        back.addActionListener(buttonBackHome);
    }

    public void setButtonAddUser(Controller.ButtonAddUser buttonAddUser) {
        addU.addActionListener(buttonAddUser);
    }

    public void afisare() {
        switch (statutC.getSelectedIndex()) {
            case 0 -> {
                tipUser = TipUser.Student;
                studentPanel.setVisible(true);
                profesorPanel.setVisible(false);
            }
            case 1 -> {
                tipUser = TipUser.Profesor;
                studentPanel.setVisible(false);
                profesorPanel.setVisible(true);
            }
            case 2 -> {
                tipUser = TipUser.Admin;
                studentPanel.setVisible(false);
                profesorPanel.setVisible(false);
            }
        }
    }

    public TipUser getTipUser() {
        return tipUser;
    }

    public String[] getValues() {
        String[] rez = new String[switch (tipUser) {
            case Student -> 10;
            case Profesor -> 12;
            case Admin, SuperAdmin -> 9;
        }];
        int i = 0;
        switch (tipUser) {
            case Student -> rez[i++] = texts[9].getText();
            case Profesor -> {
                rez[i++] = texts[11].getText();
                rez[i++] = texts[12].getText();
                rez[i++] = texts[10].getText();
            }
        }
        rez[i++] = texts[3].getText();
        rez[i++] = texts[0].getText();
        rez[i++] = texts[1].getText();
        rez[i++] = texts[4].getText();
        rez[i++] = texts[5].getText();
        rez[i++] = texts[6].getText();
        rez[i++] = texts[7].getText();
        rez[i++] = texts[8].getText();
        rez[i] = texts[2].getText();
        return rez;
    }

    public void resetAdd() {
        statutC.setSelectedIndex(0);
        for (int i = 0; i < 13; i++)
            texts[i].setText("");
    }

    class ItemChangeListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                afisare();
            }
        }
    }
}
