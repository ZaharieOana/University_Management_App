import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class DateAdmin extends JPanel {
    private final JLabel userL = new JLabel("Select User");
    private final JComboBox<String> user = new JComboBox<>();
    private final JLabel[] labelsL = new JLabel[12];
    private final JLabel[] labels = new JLabel[12];
    private final JPanel gridPanel = new JPanel();
    private final SpringLayout springLayout = new SpringLayout();
    private final JButton back = new JButton("Back");
    private final JLabel titlu = new JLabel("Date User");

    DateAdmin() {
        setLayout(springLayout);
        setBackground(Color.WHITE);
        gridPanel.setLayout(new GridLayout(13, 2, 5, 0));
        gridPanel.setBackground(Color.WHITE);
        gridPanel.add(userL);
        gridPanel.add(user);

        userL.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        user.setFont(new Font("TimesRoman", Font.PLAIN, 15));
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
        titlu.setFont(new Font("TimesRoman", Font.BOLD, 40));
        add(gridPanel);
        add(back);
        add(titlu);

        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, gridPanel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.NORTH, gridPanel, 5, SpringLayout.SOUTH, titlu);

        springLayout.putConstraint(SpringLayout.SOUTH, back, -20, SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.EAST, back, -20, SpringLayout.EAST, this);

        springLayout.putConstraint(SpringLayout.NORTH, titlu, 10, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titlu, 0, SpringLayout.HORIZONTAL_CENTER, this);
    }

    public JButton getBack() {
        return back;
    }

    public JComboBox<String> getUser() {
        return user;
    }

    public void setUser(ResultSet rs) {
        user.removeAllItems();
        user.setSelectedIndex(-1);
        try {
            while (rs.next()) user.addItem(rs.getString(1));
            user.setSelectedIndex(-1);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(10);
        }
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

    public void deselect() {
        for (int i = 0; i < 12; i++) {
            labelsL[i].setText("");
            labels[i].setText("");
        }
    }
}
