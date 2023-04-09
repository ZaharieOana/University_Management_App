import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ActivitatiCurs extends JPanel {
    private final JButton back = new JButton("Back");
    private final JLabel cursLabel = new JLabel("Curs:");
    private final JComboBox<String> cursuri = new JComboBox<>();
    private final JPanel panel = new JPanel();
    private final JPanel listaActivitati = new JPanel();
    private final JPanel scrollPanelActivitati = new JPanel();
    private final JScrollPane jScrollPaneActivitati = new JScrollPane(listaActivitati, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    private final CreareActivitateCurs creareActivitateCurs = new CreareActivitateCurs();
    private final VizualizareActivitateCurs vizualizareActivitateCurs = new VizualizareActivitateCurs();
    SpringLayout springLayout = new SpringLayout();
    private boolean init = false;
    private ArrayList<Integer> cursuriIds = new ArrayList<>();
    private TipUser tipUser;
    private SelectActivitate lastActivitateSelected = null;

    public ActivitatiCurs() {
        setLayout(springLayout);
        setBackground(Color.WHITE);

        back.setPreferredSize(new Dimension(80, 40));
        back.setFont(new Font("TimesRoman", Font.PLAIN, 20));

        cursLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));

        listaActivitati.setLayout(new GridLayout(0, 1));
        listaActivitati.setBackground(Color.WHITE);

        scrollPanelActivitati.setBackground(Color.WHITE);
        scrollPanelActivitati.setLayout(new CardLayout());
        scrollPanelActivitati.add(jScrollPaneActivitati);

        SpringLayout sl = new SpringLayout();
        panel.setLayout(sl);
        panel.setBackground(Color.WHITE);
        panel.add(scrollPanelActivitati);
        panel.add(vizualizareActivitateCurs);
        panel.add(creareActivitateCurs);

        add(back);
        add(cursLabel);
        add(cursuri);
        add(panel);

        springLayout.putConstraint(SpringLayout.NORTH, cursLabel, 20, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, cursLabel, 20, SpringLayout.WEST, this);

        springLayout.putConstraint(SpringLayout.NORTH, cursuri, 20, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, cursuri, 10, SpringLayout.EAST, cursLabel);
        springLayout.putConstraint(SpringLayout.EAST, cursuri, -20, SpringLayout.EAST, this);

        sl.putConstraint(SpringLayout.NORTH, scrollPanelActivitati, 0, SpringLayout.NORTH, panel);
        sl.putConstraint(SpringLayout.WEST, scrollPanelActivitati, 0, SpringLayout.WEST, panel);
        sl.putConstraint(SpringLayout.EAST, scrollPanelActivitati, 150, SpringLayout.WEST, scrollPanelActivitati);
        sl.putConstraint(SpringLayout.SOUTH, scrollPanelActivitati, 0, SpringLayout.SOUTH, panel);

        sl.putConstraint(SpringLayout.NORTH, vizualizareActivitateCurs, 0, SpringLayout.NORTH, panel);
        sl.putConstraint(SpringLayout.WEST, vizualizareActivitateCurs, 10, SpringLayout.EAST, scrollPanelActivitati);
        sl.putConstraint(SpringLayout.EAST, vizualizareActivitateCurs, 0, SpringLayout.EAST, panel);
        sl.putConstraint(SpringLayout.SOUTH, vizualizareActivitateCurs, 0, SpringLayout.SOUTH, panel);

        sl.putConstraint(SpringLayout.NORTH, creareActivitateCurs, 0, SpringLayout.NORTH, panel);
        sl.putConstraint(SpringLayout.WEST, creareActivitateCurs, 10, SpringLayout.EAST, scrollPanelActivitati);
        sl.putConstraint(SpringLayout.EAST, creareActivitateCurs, 0, SpringLayout.EAST, panel);
        sl.putConstraint(SpringLayout.SOUTH, creareActivitateCurs, 0, SpringLayout.SOUTH, panel);

        springLayout.putConstraint(SpringLayout.NORTH, panel, 20, SpringLayout.SOUTH, cursLabel);
        springLayout.putConstraint(SpringLayout.SOUTH, panel, -10, SpringLayout.NORTH, back);
        springLayout.putConstraint(SpringLayout.WEST, panel, 20, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.EAST, panel, -20, SpringLayout.EAST, this);

        springLayout.putConstraint(SpringLayout.SOUTH, back, -20, SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.EAST, back, -20, SpringLayout.EAST, this);

        panel.setVisible(false);
    }

    public JButton getInscriereActivitate() {
        return vizualizareActivitateCurs.getInscriere();
    }

    public CreareActivitateCurs getCreareActivitateCurs() {
        return creareActivitateCurs;
    }

    public void setActivitati(ResultSet rs) {
        if (!init) return;
        vizualizareActivitateCurs.setVisible(false);
        creareActivitateCurs.setVisible(false);
        panel.setVisible(true);
        listaActivitati.removeAll();
        try {
            int nrActivitati = 0;
            while (rs != null && rs.next()) {
                JPanel actPanel = new JPanel();
                if (nrActivitati % 2 == 0) actPanel.setBackground(new Color(243, 243, 243));
                else actPanel.setBackground(new Color(196, 196, 196));
                actPanel.setLayout(new CardLayout());
                JLabel act = new JLabel(rs.getString(2));
                SelectActivitate selectActivitate = new SelectActivitate(vizualizareActivitateCurs, actPanel, rs.getString(2), rs.getInt(1), rs.getTimestamp(3), rs.getTime(4), rs.getInt(5), rs.getInt(7), rs.getBoolean(6));
                actPanel.addMouseListener(selectActivitate);
                actPanel.addMouseMotionListener(selectActivitate);
                actPanel.add(act);
                listaActivitati.add(actPanel);
                lastActivitateSelected = selectActivitate;
                nrActivitati++;
            }
            if (tipUser == TipUser.Profesor) {
                JPanel newActPanel = new JPanel();
                if (nrActivitati % 2 == 0) newActPanel.setBackground(new Color(243, 243, 243));
                else newActPanel.setBackground(new Color(196, 196, 196));
                newActPanel.setLayout(new CardLayout());
                JLabel act = new JLabel("Creaza Activitate");
                SelectActivitate selectActivitate = new SelectActivitate(vizualizareActivitateCurs, newActPanel, null, -1, null, null, 0, 0, false);
                newActPanel.addMouseListener(selectActivitate);
                newActPanel.addMouseMotionListener(selectActivitate);
                newActPanel.add(act);
                listaActivitati.add(newActPanel);
                nrActivitati++;
            }
            for (; nrActivitati < 20; nrActivitati++) listaActivitati.add(new JLabel(""));
            updateUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setActivitateNoua(SelectActivitate selectActivitate) {
        if (lastActivitateSelected != null && !lastActivitateSelected.equals(selectActivitate))
            lastActivitateSelected.resetColor();
        lastActivitateSelected = selectActivitate;
        vizualizareActivitateCurs.setVisible(false);
        creareActivitateCurs.setVisible(true);
    }

    public void setActivitate(SelectActivitate selectActivitate) {
        if (selectActivitate == null) {
            lastActivitateSelected.triggerClick();
            return;
        }
        if (lastActivitateSelected != null && !lastActivitateSelected.equals(selectActivitate))
            lastActivitateSelected.resetColor();
        vizualizareActivitateCurs.setVisible(true);
        creareActivitateCurs.setVisible(false);
        lastActivitateSelected = selectActivitate;
        selectActivitate.updateActivitate();
    }

    public int getActivitateId() {
        return lastActivitateSelected.idActivitate;
    }

    public void setActivitateData(ResultSet rs) {
        lastActivitateSelected.setData(rs);
    }

    public JComboBox<String> getCursuri() {
        return cursuri;
    }

    public void setCursuri(ResultSet rs) {
        init = false;
        panel.setVisible(false);
        cursuri.removeAllItems();
        cursuriIds = new ArrayList<>();
        listaActivitati.removeAll();
        try {
            while (rs.next()) {
                cursuri.addItem(rs.getString(1));
                cursuriIds.add(rs.getInt(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cursuri.setSelectedIndex(-1);
        init = true;
    }

    public void setTipUser(TipUser tipUser) {
        this.tipUser = tipUser;
    }

    public JButton getBack() {
        return back;
    }

    public int getSelectedCursId() {
        if (!init) return -1;
        return cursuriIds.get(cursuri.getSelectedIndex());
    }

    public class SelectActivitate extends MouseAdapter {
        private final int idActivitate;
        private final JPanel parent;
        private final Color originalColor;
        private final VizualizareActivitateCurs va;
        private boolean ok = false;
        private boolean inscrisActivitate;
        private String nume;
        private Timestamp dataActivitate;
        private Time durata;
        private int nrMaxStud;
        private int nrStud;

        public SelectActivitate(VizualizareActivitateCurs va, JPanel parent, String nume, int idActivitate, Timestamp dataActivitate, Time durata, int nrMaxStud, int nrStud, boolean inscrisActivitate) {
            this.idActivitate = idActivitate;
            this.parent = parent;
            setData(nume, dataActivitate, durata, nrMaxStud, nrStud, inscrisActivitate);
            this.va = va;
            originalColor = parent.getBackground();
        }

        private void setData(String nume, Timestamp dataActivitate, Time durata, int nrMinStud, int nrStud, boolean inscrisActivitate) {
            this.dataActivitate = dataActivitate;
            this.nrMaxStud = nrMinStud;
            this.durata = durata;
            this.nrStud = nrStud;
            this.inscrisActivitate = inscrisActivitate;
            this.nume = nume;
        }

        public void setData(ResultSet rs) {
            try {
                rs.next();
                nrStud = rs.getInt(7);
                inscrisActivitate = rs.getBoolean(6);
                updateActivitate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void resetColor() {
            parent.setBackground(originalColor);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            ok = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            if (ok) {
                triggerClick();
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            super.mouseExited(e);
            ok = false;
        }

        public void updateActivitate() {
            va.setButton(tipUser, inscrisActivitate);
            va.setNume(nume);
            va.setDataActivitate(dataActivitate);
            va.setDurata(durata);
            va.setNrMaxStud(nrMaxStud);
            va.setNrStud(nrStud);
        }

        public void triggerClick() {
            parent.setBackground(new Color(117, 174, 229));
            if (idActivitate == -1) {
                setActivitateNoua(this);
            } else {
                setActivitate(this);
            }
        }
    }
}
