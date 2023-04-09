import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Calendar extends JPanel {
    private final SpringLayout springLayout = new SpringLayout();
    private final JLabel titlu = new JLabel("Calendar");
    private final JButton back = new JButton("Back");
    private final JButton descarca = new JButton("Descarca");
    private final JPanel days = new JPanel();
    private final JPanel activitati = new JPanel();
    private final JPanel[] zile = new JPanel[7];

    public Calendar() {
        setLayout(springLayout);
        setBackground(Color.WHITE);

        back.setPreferredSize(new Dimension(80, 40));
        back.setFont(new Font("TimesRoman", Font.PLAIN, 20));

        titlu.setFont(new Font("TimesRoman", Font.BOLD, 50));

        activitati.setBackground(Color.WHITE);
        activitati.setLayout(new GridLayout(0, 7, 2, 0));

        days.setBackground(new Color(234, 234, 234));
        days.setLayout(new GridLayout(0, 7, 2, 0));
        String[] zile = {"Luni", "Marti", "Miercuri", "Joi", "Vineri", "Sambata", "Duminica"};
        for (int i = 0; i < 7; i++) {
            days.add(new JLabel(zile[i]));
            this.zile[i] = new JPanel();
            this.zile[i].setBackground(Color.WHITE);
            this.zile[i].setLayout(new GridLayout(0, 1, 0, 2));
            activitati.add(this.zile[i]);
        }
        descarca.setPreferredSize(new Dimension(100, 40));
        descarca.setFont(new Font("TimesRoman", Font.PLAIN, 15));

        add(back);
        add(titlu);
        add(days);
        add(activitati);
        add(descarca);

        springLayout.putConstraint(SpringLayout.NORTH, titlu, 10, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titlu, 0, SpringLayout.HORIZONTAL_CENTER, this);

        springLayout.putConstraint(SpringLayout.NORTH, days, 10, SpringLayout.SOUTH, titlu);
        springLayout.putConstraint(SpringLayout.EAST, days, -20, SpringLayout.EAST, this);
        springLayout.putConstraint(SpringLayout.WEST, days, 20, SpringLayout.WEST, this);

        springLayout.putConstraint(SpringLayout.NORTH, activitati, 0, SpringLayout.SOUTH, days);
        springLayout.putConstraint(SpringLayout.EAST, activitati, -20, SpringLayout.EAST, this);
        springLayout.putConstraint(SpringLayout.WEST, activitati, 20, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.SOUTH, activitati, -20, SpringLayout.NORTH, back);

        springLayout.putConstraint(SpringLayout.SOUTH, back, -20, SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.EAST, back, -20, SpringLayout.EAST, this);

        springLayout.putConstraint(SpringLayout.SOUTH, descarca, -20, SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.EAST, descarca, -20, SpringLayout.WEST, back);
    }

    public JButton getBack() {
        return back;
    }

    public JButton getDescarca() {
        return descarca;
    }

    public void doDescarca(ResultSet rsCurs) {
        String[] zile = {"Luni", "Marti", "Miercuri", "Joi", "Vineri", "Sambata", "Duminica"};
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp nextWeek = new Timestamp(now.getTime() + (86400 * 7 * 1000));
        ArrayList[] dataPeZile = new ArrayList[7];
        for (int i = 0; i < 7; i++) dataPeZile[i] = new ArrayList<String>();
        try {
            while (rsCurs.next()) {
                Timestamp data = rsCurs.getTimestamp(1);
                Time durata = rsCurs.getTime(2);
                String numeAct = rsCurs.getString(3);
                String numeMat = rsCurs.getString(4);
                int zi = data.getDay();//0 luni 6 duminica
                if (zi == 0) zi = 7;
                zi--;
                if (data.after(now) && data.before(nextWeek)) {
                    String informatie = numeMat + " " + numeAct + " pe data de " + data + " va dura " + durata;
                    dataPeZile[zi].add(informatie);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File file = new File("calendar " + now.getTime() + ".txt");
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(file.getName());
                for (int i = 0; i < 7; i++) {
                    fileWriter.write(zile[i] + ":\n");
                    for (int j = 0; j < dataPeZile[i].size(); j++) {
                        fileWriter.write("\t" + dataPeZile[i].get(j) + "\n");
                    }
                }
                fileWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setActivitati(ResultSet rsCurs) {
        for (int i = 0; i < 7; i++) {
            zile[i].removeAll();
        }
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp nextWeek = new Timestamp(now.getTime() + (86400 * 7 * 1000));
        try {
            while (rsCurs.next()) {
                Timestamp data = rsCurs.getTimestamp(1);
                Time durata = rsCurs.getTime(2);
                String numeAct = rsCurs.getString(3);
                String numeMat = rsCurs.getString(4);
                int zi = data.getDay();//0 luni 6 duminica
                if (zi == 0) zi = 7;
                zi--;
                if (data.after(now) && data.before(nextWeek)) {
                    JPanel panel = new JPanel();
                    panel.setBackground(Color.WHITE);
                    SpringLayout sl = new SpringLayout();
                    panel.setLayout(sl);
                    JLabel dataL = new JLabel(data.toString().substring(0, 16));
                    JLabel durataL = new JLabel(durata.toString().substring(0, 5));
                    JLabel numeActL = new JLabel(numeAct);
                    JLabel numeMatL = new JLabel(numeMat);
                    panel.add(dataL);
                    panel.add(durataL);
                    panel.add(numeActL);
                    panel.add(numeMatL);

                    sl.putConstraint(SpringLayout.NORTH, dataL, 0, SpringLayout.NORTH, panel);
                    sl.putConstraint(SpringLayout.WEST, dataL, 0, SpringLayout.WEST, panel);

                    sl.putConstraint(SpringLayout.NORTH, durataL, 0, SpringLayout.SOUTH, dataL);
                    sl.putConstraint(SpringLayout.WEST, durataL, 0, SpringLayout.WEST, panel);

                    sl.putConstraint(SpringLayout.NORTH, numeMatL, 0, SpringLayout.SOUTH, durataL);
                    sl.putConstraint(SpringLayout.WEST, numeMatL, 0, SpringLayout.WEST, panel);

                    sl.putConstraint(SpringLayout.NORTH, numeActL, 0, SpringLayout.SOUTH, numeMatL);
                    sl.putConstraint(SpringLayout.WEST, numeActL, 0, SpringLayout.WEST, panel);

                    sl.putConstraint(SpringLayout.SOUTH, panel, 0, SpringLayout.SOUTH, numeActL);

                    zile[zi].add(panel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
