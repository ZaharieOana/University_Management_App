import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.sql.ResultSet;

public class Notificari extends JPanel {
    private final SpringLayout springLayout = new SpringLayout();
    private final JLabel titlu = new JLabel("Notificari");
    private final JButton back = new JButton("Back");
    private final JPanel feed = new JPanel();
    private final JPanel notificari = new JPanel();
    private final JPanel scrollPanelNotificari = new JPanel();
    private final JScrollPane jScrollPaneNotificari = new JScrollPane(notificari, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    public Notificari() {
        setLayout(springLayout);
        setBackground(Color.WHITE);

        back.setPreferredSize(new Dimension(80, 40));
        back.setFont(new Font("TimesRoman", Font.PLAIN, 20));

        titlu.setFont(new Font("TimesRoman", Font.BOLD, 50));

        notificari.setLayout(new GridLayout(0, 1));
        notificari.setBackground(Color.WHITE);

        jScrollPaneNotificari.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            int prevMax = 0;

            public void adjustmentValueChanged(AdjustmentEvent e) {
                if (e.getAdjustable().getMaximum() != prevMax) {
                    e.getAdjustable().setValue(e.getAdjustable().getMaximum());
                    prevMax = e.getAdjustable().getMaximum();
                }
            }
        });

        scrollPanelNotificari.setBackground(Color.WHITE);
        scrollPanelNotificari.setLayout(new CardLayout());
        scrollPanelNotificari.add(jScrollPaneNotificari);

        SpringLayout sl = new SpringLayout();
        feed.setLayout(sl);
        feed.setBackground(Color.WHITE);

        feed.add(scrollPanelNotificari);

        sl.putConstraint(SpringLayout.NORTH, scrollPanelNotificari, 0, SpringLayout.NORTH, feed);
        sl.putConstraint(SpringLayout.EAST, scrollPanelNotificari, 0, SpringLayout.EAST, feed);
        sl.putConstraint(SpringLayout.WEST, scrollPanelNotificari, 0, SpringLayout.WEST, feed);
        sl.putConstraint(SpringLayout.SOUTH, scrollPanelNotificari, 0, SpringLayout.SOUTH, feed);

        add(back);
        add(feed);
        add(titlu);

        springLayout.putConstraint(SpringLayout.NORTH, feed, 10, SpringLayout.SOUTH, titlu);
        springLayout.putConstraint(SpringLayout.EAST, feed, 10, SpringLayout.SOUTH, titlu);
        springLayout.putConstraint(SpringLayout.NORTH, feed, 10, SpringLayout.SOUTH, titlu);
        springLayout.putConstraint(SpringLayout.NORTH, feed, 10, SpringLayout.SOUTH, titlu);

        springLayout.putConstraint(SpringLayout.NORTH, feed, 20, SpringLayout.SOUTH, titlu);
        springLayout.putConstraint(SpringLayout.SOUTH, feed, -10, SpringLayout.NORTH, back);
        springLayout.putConstraint(SpringLayout.WEST, feed, 20, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.EAST, feed, -20, SpringLayout.EAST, this);

        springLayout.putConstraint(SpringLayout.NORTH, titlu, 10, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titlu, 0, SpringLayout.HORIZONTAL_CENTER, this);

        springLayout.putConstraint(SpringLayout.SOUTH, back, -20, SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.EAST, back, -20, SpringLayout.EAST, this);
    }

    public JButton getBack() {
        return back;
    }

    public void setNotificari(ResultSet rs) {
        notificari.removeAll();
        try {
            for (int i = 0; i < 8; i++) {
                notificari.add(new JLabel(" "));
            }
            int nrNotificari = 0;
            while (rs.next()) {
                JPanel panel = new JPanel();

                if (nrNotificari % 2 == 0) panel.setBackground(new Color(243, 243, 243));
                else panel.setBackground(new Color(196, 196, 196));
                if (nrNotificari < 8) notificari.remove(0);

                SpringLayout sl = new SpringLayout();
                panel.setLayout(sl);
                String message = rs.getString(1);
                JLabel label = new JLabel(message);
                JLabel data = new JLabel(rs.getTimestamp(2).toString());

                panel.add(label);
                panel.add(data);

                sl.putConstraint(SpringLayout.NORTH, data, 0, SpringLayout.NORTH, panel);
                sl.putConstraint(SpringLayout.WEST, data, 0, SpringLayout.WEST, panel);

                sl.putConstraint(SpringLayout.NORTH, label, 0, SpringLayout.SOUTH, data);
                sl.putConstraint(SpringLayout.WEST, label, 0, SpringLayout.WEST, panel);

                sl.putConstraint(SpringLayout.SOUTH, panel, data.getPreferredSize().height + label.getPreferredSize().height, SpringLayout.NORTH, panel);

                notificari.add(panel);

                nrNotificari++;
            }
            updateUI();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}