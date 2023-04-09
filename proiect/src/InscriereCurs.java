import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class InscriereCurs extends JPanel {
    private final JLabel titlu = new JLabel("Inscrie-te la un curs");
    private final JLabel cursL = new JLabel("Curs:");
    private final JComboBox<String> cursC = new JComboBox<>();
    private final JLabel descL = new JLabel("Descriere:");
    private final JLabel descL2 = new JLabel();
    private final SpringLayout springLayout = new SpringLayout();
    private final JPanel gridPanel = new JPanel();
    private final JButton back = new JButton("Back");
    private final JButton insB = new JButton("Inscrie-te");

    InscriereCurs() {
        setLayout(springLayout);
        setBackground(Color.WHITE);
        gridPanel.setLayout(new GridLayout(2, 2, 0, 20));
        gridPanel.setBackground(Color.WHITE);
        gridPanel.add(cursL);
        gridPanel.add(cursC);
        gridPanel.add(descL);
        gridPanel.add(descL2);

        cursL.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        cursC.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        descL.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        descL2.setFont(new Font("TimesRoman", Font.PLAIN, 22));
        cursL.setPreferredSize(new Dimension(100, 40));
        cursC.setPreferredSize(new Dimension(300, 40));
        descL.setPreferredSize(new Dimension(100, 40));
        descL2.setPreferredSize(new Dimension(300, 40));

        titlu.setFont(new Font("TimesRoman", Font.BOLD, 35));
        add(titlu);
        add(gridPanel);

        back.setPreferredSize(new Dimension(100, 40));
        back.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        add(back);
        insB.setPreferredSize(new Dimension(100, 40));
        insB.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        add(insB);

        springLayout.putConstraint(SpringLayout.NORTH, titlu, 10, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titlu, 0, SpringLayout.HORIZONTAL_CENTER, this);

        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, gridPanel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, gridPanel, 0, SpringLayout.VERTICAL_CENTER, this);

        springLayout.putConstraint(SpringLayout.SOUTH, back, -20, SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.EAST, back, -20, SpringLayout.EAST, this);

        springLayout.putConstraint(SpringLayout.SOUTH, insB, -20, SpringLayout.NORTH, back);
        springLayout.putConstraint(SpringLayout.EAST, insB, -20, SpringLayout.EAST, this);

        deselect();
    }

    public JButton getBack() {
        return back;
    }

    public JButton getInscriere() {
        return insB;
    }

    public JComboBox<String> getCurs() {
        return cursC;
    }

    public void setDesc(String d) {
        descL2.setText(d);
    }

    public void deselect() {
        cursC.setSelectedIndex(-1);
        descL2.setText("");
    }

    public void setMaterii(ResultSet rs) {
        cursC.removeAllItems();
        try {
            while (rs.next())
                cursC.addItem(rs.getString(1));
            cursC.setSelectedIndex(-1);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(10);
        }
    }
}
