import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class DatePersoana extends JPanel {
    private final JLabel[] labelsL = new JLabel[12];
    private final JLabel[] labels = new JLabel[12];
    private final JPanel gridPanel = new JPanel();
    private final SpringLayout springLayout = new SpringLayout();
    private final JButton back = new JButton("Back");
    private final JLabel titlu = new JLabel("Date Personale:");

    DatePersoana() {
        setLayout(springLayout);
        setBackground(Color.WHITE);
        gridPanel.setLayout(new GridLayout(12, 2, 5, 5));
        gridPanel.setBackground(Color.WHITE);
        for (int i = 0; i < 12; i++) {
            labelsL[i] = new JLabel("");
            labels[i] = new JLabel("");
            labelsL[i].setFont(new Font("TimesRoman", Font.PLAIN, 15));
            labels[i].setFont(new Font("TimesRoman", Font.PLAIN, 15));
            gridPanel.add(labelsL[i]);
            gridPanel.add(labels[i]);
        }
        back.setPreferredSize(new Dimension(80, 40));
        back.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        titlu.setFont(new Font("TimesRoman", Font.BOLD, 50));
        add(gridPanel);
        add(back);
        add(titlu);

        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, gridPanel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, gridPanel, 0, SpringLayout.VERTICAL_CENTER, this);

        springLayout.putConstraint(SpringLayout.SOUTH, back, -20, SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.EAST, back, -20, SpringLayout.EAST, this);

        springLayout.putConstraint(SpringLayout.NORTH, titlu, 10, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titlu, 0, SpringLayout.HORIZONTAL_CENTER, this);
    }

    public void modificare(ResultSet rs) {
        for (int i = 0; i < 12; i++) {
            labels[i].setText("");
            labelsL[i].setText("");
        }
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int colCount = rsmd.getColumnCount();
            rs.next();
            for (int i = 0; i < colCount; i++) {
                labelsL[i].setText(rsmd.getColumnName(i + 1) + ":  ");
                Object obj = rs.getObject(i + 1);
                if (obj != null) labels[i].setText(obj.toString());
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(4);
        }
    }

    public void setButtonBackHome(Controller.ButtonBackHome buttonBackHome) {
        back.addActionListener(buttonBackHome);
    }
}
