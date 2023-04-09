import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class Note extends JPanel {
    private final SpringLayout springLayout = new SpringLayout();
    private final JPanel gridPanel = new JPanel();
    private final JPanel panel = new JPanel();
    private final JLabel materie = new JLabel("Materie");
    private final JLabel descriere = new JLabel("Descriere");
    private final JLabel profesor = new JLabel("Profesor");
    private final JLabel curs = new JLabel("Nota curs");
    private final JLabel seminar = new JLabel("Nota seminar");
    private final JLabel laborator = new JLabel("Nota laborator");
    private final JLabel medie = new JLabel("Media finala");
    private final JLabel titlu = new JLabel("Note");
    private final JButton back = new JButton("Back");
    private final JPanel scrollPanel = new JPanel();

    Note() {
        setLayout(springLayout);
        setBackground(Color.WHITE);
        gridPanel.setLayout(new GridLayout(0, 7, 5, 5));
        gridPanel.setBackground(Color.WHITE);
        gridPanel.add(materie);
        gridPanel.add(descriere);
        gridPanel.add(profesor);
        gridPanel.add(curs);
        gridPanel.add(seminar);
        gridPanel.add(laborator);
        gridPanel.add(medie);

        panel.setLayout(new GridLayout(0, 7, 5, 2));
        panel.setBackground(Color.WHITE);

        JScrollPane jScrollPane = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        CardLayout cl = new CardLayout();
        scrollPanel.setBackground(Color.WHITE);
        scrollPanel.setLayout(cl);
        scrollPanel.add(jScrollPane);

        titlu.setFont(new Font("TimesRoman", Font.BOLD, 35));
        back.setPreferredSize(new Dimension(100, 40));
        back.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        add(titlu);
        add(gridPanel);
        add(scrollPanel);
        add(back);

        springLayout.putConstraint(SpringLayout.NORTH, titlu, 10, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titlu, 0, SpringLayout.HORIZONTAL_CENTER, this);

        springLayout.putConstraint(SpringLayout.WEST, gridPanel, 0, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.NORTH, gridPanel, 20, SpringLayout.SOUTH, titlu);
        springLayout.putConstraint(SpringLayout.EAST, gridPanel, -20, SpringLayout.EAST, this);

        springLayout.putConstraint(SpringLayout.WEST, scrollPanel, 0, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.NORTH, scrollPanel, 0, SpringLayout.SOUTH, gridPanel);
        springLayout.putConstraint(SpringLayout.EAST, scrollPanel, 0, SpringLayout.EAST, this);
        springLayout.putConstraint(SpringLayout.SOUTH, scrollPanel, 0, SpringLayout.NORTH, back);

        springLayout.putConstraint(SpringLayout.SOUTH, back, -20, SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.EAST, back, -20, SpringLayout.EAST, this);
    }

    public JButton getBack() {
        return back;
    }

    public void modificare(ResultSet rs, int[][] proc) {
        panel.removeAll();
        try {
            int n = 0;
            while (rs.next()) {
                for (int i = 1; i < 3; i++) {
                    JLabel jLabel = new JLabel(rs.getString(i));
                    jLabel.setFont(new Font("TimesRoman", Font.PLAIN, 10));
                    panel.add(jLabel);
                }
                JLabel nume = new JLabel(rs.getString(3) + " " + rs.getString(4));
                nume.setFont(new Font("TimesRoman", Font.PLAIN, 10));
                panel.add(nume);
                int nr = 0;
                double[] note = new double[3];
                for (int i = 5; i < 8; i++) {
                    note[nr] = rs.getDouble(i);
                    JLabel jLabel = new JLabel(String.valueOf(note[nr++]));
                    jLabel.setFont(new Font("TimesRoman", Font.PLAIN, 10));
                    panel.add(jLabel);
                }
                double medie = 0;
                for (int i = 0; i < 3; i++) medie += (note[i] * (proc[n][i] / 100.0));
                JLabel medieLabel = new JLabel(String.valueOf(medie));
                medieLabel.setFont(new Font("TimesRoman", Font.PLAIN, 10));
                panel.add(medieLabel);
                n++;
            }
            for (; n < 19; n++) {
                for (int j = 0; j < 7; j++) {
                    JLabel jLabel = new JLabel("");
                    jLabel.setFont(new Font("TimesRoman", Font.PLAIN, 10));
                    panel.add(jLabel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
