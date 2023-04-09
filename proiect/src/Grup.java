import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Grup extends JPanel {
    private final SpringLayout springLayout = new SpringLayout();
    private final JButton back = new JButton("Back");
    private final JLabel grupLabel = new JLabel("Grup:");
    private final JComboBox<String> grupuri = new JComboBox<>();
    private final JButton inscriere = new JButton("Inscrie-te");
    private final JPanel membri = new JPanel();
    private final JPanel chat = new JPanel();
    private final JTextField mesajText = new JTextField("");
    private final JButton trimite = new JButton(">");
    private final JPanel scrollPanelMembri = new JPanel();
    private final JPanel scrollPanelChat = new JPanel();
    private final JScrollPane jScrollPaneMembri = new JScrollPane(membri, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    private final JScrollPane jScrollPaneChat = new JScrollPane(chat, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    private final JButton switchButton = new JButton("Activitati");
    private final JPanel mesaje = new JPanel();
    private final JPanel activitati = new JPanel();
    private final JPanel listaActivitati = new JPanel();
    private final JPanel scrollPanelActivitati = new JPanel();
    private final JScrollPane jScrollPaneActivitati = new JScrollPane(listaActivitati, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    private final CreareActivitateGrup creareActivitateGrup = new CreareActivitateGrup();
    private final VizualizareActivitateGrup vizualizareActivitateGrup = new VizualizareActivitateGrup();
    private final JLabel sugestie = new JLabel();
    private ArrayList<Boolean> inscris = new ArrayList<>();
    private ArrayList<Integer> grupIds = new ArrayList<>();
    private boolean init = false;
    private int switchState = 1;
    private SelectActivitate lastActivitateSelected = null;
    private int idSugestie;

    public Grup() {
        setLayout(springLayout);
        setBackground(Color.WHITE);

        back.setPreferredSize(new Dimension(80, 40));
        back.setFont(new Font("TimesRoman", Font.PLAIN, 20));

        grupLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));
        setButtonRetragere();

        membri.setLayout(new GridLayout(0, 1));
        membri.setBackground(Color.WHITE);
        chat.setLayout(new GridLayout(0, 1));
        chat.setBackground(Color.WHITE);

        mesajText.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        mesajText.setBackground(Color.WHITE);

        jScrollPaneChat.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            int prevMax = 0;

            public void adjustmentValueChanged(AdjustmentEvent e) {
                if (e.getAdjustable().getMaximum() != prevMax) {
                    e.getAdjustable().setValue(e.getAdjustable().getMaximum());
                    prevMax = e.getAdjustable().getMaximum();
                }
            }
        });

        scrollPanelMembri.setBackground(Color.WHITE);
        scrollPanelMembri.setLayout(new CardLayout());
        scrollPanelMembri.add(jScrollPaneMembri);
        scrollPanelChat.setBackground(Color.WHITE);
        scrollPanelChat.setLayout(new CardLayout());
        scrollPanelChat.add(jScrollPaneChat);

        SpringLayout slMesaje = new SpringLayout();
        mesaje.setLayout(slMesaje);
        mesaje.setBackground(Color.WHITE);
        mesaje.add(scrollPanelMembri);
        mesaje.add(scrollPanelChat);
        mesaje.add(mesajText);
        mesaje.add(trimite);

        switchButton.setPreferredSize(new Dimension(140, 40));
        switchButton.setFont(new Font("TimesRoman", Font.PLAIN, 20));

        listaActivitati.setLayout(new GridLayout(0, 1));
        listaActivitati.setBackground(Color.WHITE);

        scrollPanelActivitati.setBackground(Color.WHITE);
        scrollPanelActivitati.setLayout(new CardLayout());
        scrollPanelActivitati.add(jScrollPaneActivitati);

        SpringLayout slActivitati = new SpringLayout();
        activitati.setLayout(slActivitati);
        activitati.setBackground(Color.WHITE);
        activitati.add(scrollPanelActivitati);
        activitati.add(vizualizareActivitateGrup);
        activitati.add(creareActivitateGrup);

        add(back);
        add(grupLabel);
        add(grupuri);
        add(inscriere);
        add(mesaje);
        add(activitati);
        add(switchButton);

        springLayout.putConstraint(SpringLayout.NORTH, grupLabel, 20, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, grupLabel, 20, SpringLayout.WEST, this);

        springLayout.putConstraint(SpringLayout.NORTH, inscriere, 20, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.EAST, inscriere, -20, SpringLayout.EAST, this);

        springLayout.putConstraint(SpringLayout.NORTH, grupuri, 20, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, grupuri, 10, SpringLayout.EAST, grupLabel);
        springLayout.putConstraint(SpringLayout.EAST, grupuri, -10, SpringLayout.WEST, inscriere);

        slMesaje.putConstraint(SpringLayout.NORTH, scrollPanelMembri, 0, SpringLayout.NORTH, mesaje);
        slMesaje.putConstraint(SpringLayout.WEST, scrollPanelMembri, 0, SpringLayout.WEST, mesaje);
        slMesaje.putConstraint(SpringLayout.EAST, scrollPanelMembri, 150, SpringLayout.WEST, scrollPanelMembri);
        slMesaje.putConstraint(SpringLayout.SOUTH, scrollPanelMembri, 0, SpringLayout.SOUTH, mesaje);

        slMesaje.putConstraint(SpringLayout.SOUTH, trimite, 0, SpringLayout.SOUTH, mesaje);
        slMesaje.putConstraint(SpringLayout.EAST, trimite, 0, SpringLayout.EAST, mesaje);

        slMesaje.putConstraint(SpringLayout.SOUTH, mesajText, 0, SpringLayout.SOUTH, mesaje);
        slMesaje.putConstraint(SpringLayout.WEST, mesajText, 10, SpringLayout.EAST, scrollPanelMembri);
        slMesaje.putConstraint(SpringLayout.EAST, mesajText, 0, SpringLayout.WEST, trimite);
        slMesaje.putConstraint(SpringLayout.NORTH, mesajText, 0, SpringLayout.NORTH, trimite);

        slMesaje.putConstraint(SpringLayout.NORTH, scrollPanelChat, 0, SpringLayout.NORTH, mesaje);
        slMesaje.putConstraint(SpringLayout.EAST, scrollPanelChat, 0, SpringLayout.EAST, mesaje);
        slMesaje.putConstraint(SpringLayout.WEST, scrollPanelChat, 0, SpringLayout.WEST, mesajText);
        slMesaje.putConstraint(SpringLayout.SOUTH, scrollPanelChat, -10, SpringLayout.NORTH, mesajText);

        springLayout.putConstraint(SpringLayout.NORTH, mesaje, 20, SpringLayout.SOUTH, grupLabel);
        springLayout.putConstraint(SpringLayout.SOUTH, mesaje, -10, SpringLayout.NORTH, back);
        springLayout.putConstraint(SpringLayout.WEST, mesaje, 20, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.EAST, mesaje, -20, SpringLayout.EAST, this);

        slActivitati.putConstraint(SpringLayout.NORTH, scrollPanelActivitati, 0, SpringLayout.NORTH, activitati);
        slActivitati.putConstraint(SpringLayout.WEST, scrollPanelActivitati, 0, SpringLayout.WEST, activitati);
        slActivitati.putConstraint(SpringLayout.EAST, scrollPanelActivitati, 150, SpringLayout.WEST, scrollPanelActivitati);
        slActivitati.putConstraint(SpringLayout.SOUTH, scrollPanelActivitati, 0, SpringLayout.SOUTH, activitati);

        slActivitati.putConstraint(SpringLayout.NORTH, vizualizareActivitateGrup, 0, SpringLayout.NORTH, activitati);
        slActivitati.putConstraint(SpringLayout.WEST, vizualizareActivitateGrup, 10, SpringLayout.EAST, scrollPanelActivitati);
        slActivitati.putConstraint(SpringLayout.EAST, vizualizareActivitateGrup, 0, SpringLayout.EAST, activitati);
        slActivitati.putConstraint(SpringLayout.SOUTH, vizualizareActivitateGrup, 0, SpringLayout.SOUTH, activitati);

        slActivitati.putConstraint(SpringLayout.NORTH, creareActivitateGrup, 0, SpringLayout.NORTH, activitati);
        slActivitati.putConstraint(SpringLayout.WEST, creareActivitateGrup, 10, SpringLayout.EAST, scrollPanelActivitati);
        slActivitati.putConstraint(SpringLayout.EAST, creareActivitateGrup, 0, SpringLayout.EAST, activitati);
        slActivitati.putConstraint(SpringLayout.SOUTH, creareActivitateGrup, 0, SpringLayout.SOUTH, activitati);

        springLayout.putConstraint(SpringLayout.NORTH, activitati, 20, SpringLayout.SOUTH, grupLabel);
        springLayout.putConstraint(SpringLayout.SOUTH, activitati, -10, SpringLayout.NORTH, back);
        springLayout.putConstraint(SpringLayout.WEST, activitati, 20, SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.EAST, activitati, -20, SpringLayout.EAST, this);

        springLayout.putConstraint(SpringLayout.SOUTH, back, -20, SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.EAST, back, -20, SpringLayout.EAST, this);

        springLayout.putConstraint(SpringLayout.SOUTH, switchButton, -20, SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.WEST, switchButton, 20, SpringLayout.WEST, this);
    }

    public JComboBox<String> getGrupuri() {
        return grupuri;
    }

    public void setGrupuri(ResultSet rs) {
        init = false;
        grupuri.removeAllItems();
        inscris = new ArrayList<>();
        grupIds = new ArrayList<>();
        chat.removeAll();
        membri.removeAll();
        toggleView(false);
        setButtonRetragere();
        try {
            while (rs.next()) {
                grupuri.addItem(rs.getString(1));
                inscris.add(rs.getBoolean(2));
                grupIds.add(rs.getInt(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        grupuri.setSelectedIndex(-1);
        init = true;
    }

    public JButton getSwitchButton() {
        return switchButton;
    }

    public JLabel getSugestie() {
        return sugestie;
    }

    public int getIdSugestie() {
        return idSugestie;
    }

    /**
     * @return 0 - mesaje->activitati,1 - activitati->mesaje,
     */
    public int triggerSwitch() {
        if (switchState == 1) {
            mesaje.setVisible(false);
            activitati.setVisible(true);
            switchButton.setText("Mesaje");
            switchState = 0;
        } else {
            mesaje.setVisible(true);
            activitati.setVisible(false);
            switchButton.setText("Activitati");
            switchState = 1;
        }
        return switchState;
    }

    public JButton getInscriere() {
        return inscriere;
    }

    public String getMesajText() {
        return mesajText.getText();
    }

    public JButton getTrimite() {
        return trimite;
    }

    private void setButtonRetragere() {
        if (!init) {
            inscriere.setEnabled(false);
            inscriere.setText(" ");
            return;
        }
        inscriere.setEnabled(true);
        inscriere.setText("Paraseste grupul");
        toggleView(true);
    }

    private void setButtonInscriere() {
        inscriere.setEnabled(true);
        inscriere.setText("Inscrie-te");
        toggleView(false);
    }

    private void toggleView(boolean flag) {
        scrollPanelMembri.setVisible(flag);
        scrollPanelChat.setVisible(flag);
        mesajText.setVisible(flag);
        trimite.setVisible(flag);
        switchButton.setVisible(flag);
        switchState = 0;
        triggerSwitch();
        mesajText.setText("");

    }

    public JButton getBack() {
        return back;
    }

    public void modificare() {
        if (!init) return;
        if (inscris.get(grupuri.getSelectedIndex())) setButtonRetragere();
        else setButtonInscriere();
    }

    public boolean getGrupSelectedInscris() {
        if (!init) return false;
        return inscris.get(grupuri.getSelectedIndex());
    }

    public int getGrupSelectedId() {
        if (!init) return -1;
        return grupIds.get(grupuri.getSelectedIndex());
    }

    public void showGrup(ResultSet rsMembri, ResultSet rsChat, ResultSet rsSugestii) {
        setButtonRetragere();
        chat.removeAll();
        membri.removeAll();
        try {
            int nrMembri = 0;
            while (rsMembri.next()) {
                membri.add(new JLabel(rsMembri.getString(1) + " " + rsMembri.getString(2)));
                nrMembri++;
            }
            for (; nrMembri < 18; nrMembri++) membri.add(new JLabel(""));
            if (rsSugestii.next()) {
                membri.add(new JLabel("Sugestie"));
                sugestie.setText(rsSugestii.getString(1) + " " + rsSugestii.getString(2));
                idSugestie = rsSugestii.getInt(3);
                membri.add(sugestie);
            } else {
                for (; nrMembri < 20; nrMembri++) membri.add(new JLabel(""));
            }


            int nrChat;
            for (nrChat = 0; nrChat < 8; nrChat++) chat.add(new JLabel(""));
            boolean par = false;
            while (rsChat.next()) {
                SpringLayout sl = new SpringLayout();
                JPanel jp = new JPanel();
                jp.setLayout(sl);

                if (par) jp.setBackground(new Color(243, 243, 243));
                else jp.setBackground(new Color(196, 196, 196));
                par = !par;

                JLabel sender = new JLabel(rsChat.getString(1) + " " + rsChat.getString(2) + ":");
                JLabel msg = new JLabel(rsChat.getString(3) + " ");
                JLabel time = new JLabel(rsChat.getTimestamp(4).toString());

                jp.add(sender);
                jp.add(msg);
                jp.add(time);
                sl.putConstraint(SpringLayout.NORTH, sender, 0, SpringLayout.NORTH, jp);
                sl.putConstraint(SpringLayout.WEST, sender, 0, SpringLayout.WEST, jp);

                sl.putConstraint(SpringLayout.NORTH, time, 0, SpringLayout.NORTH, jp);
                sl.putConstraint(SpringLayout.EAST, time, 0, SpringLayout.EAST, jp);
                sl.putConstraint(SpringLayout.SOUTH, time, 0, SpringLayout.SOUTH, sender);

                sl.putConstraint(SpringLayout.NORTH, msg, 3, SpringLayout.NORTH, sender);
                sl.putConstraint(SpringLayout.WEST, msg, 0, SpringLayout.WEST, jp);
                sl.putConstraint(SpringLayout.EAST, msg, 0, SpringLayout.EAST, jp);
                sl.putConstraint(SpringLayout.SOUTH, msg, 0, SpringLayout.SOUTH, jp);

                sl.putConstraint(SpringLayout.SOUTH, jp, sender.getPreferredSize().height + msg.getPreferredSize().height + 3, SpringLayout.NORTH, jp);

                if (nrChat > 0) {
                    chat.remove(0);
                    nrChat--;
                }
                chat.add(jp);
            }
            updateUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setActivitati(ResultSet rs) {
        listaActivitati.removeAll();
        vizualizareActivitateGrup.setVisible(false);
        creareActivitateGrup.setVisible(false);
        try {
            boolean par = false;
            int nrActivitati = 0;
            while (rs != null && rs.next()) {
                JPanel actPanel = new JPanel();
                if (par) actPanel.setBackground(new Color(243, 243, 243));
                else actPanel.setBackground(new Color(196, 196, 196));
                par = !par;
                actPanel.setLayout(new CardLayout());
                JLabel act = new JLabel(rs.getString(2));
                SelectActivitate selectActivitate = new SelectActivitate(vizualizareActivitateGrup, actPanel, rs.getString(2), rs.getInt(1), rs.getTimestamp(3), rs.getTimestamp(5), rs.getTime(4), rs.getInt(6), rs.getInt(8), rs.getBoolean(7));
                actPanel.addMouseListener(selectActivitate);
                actPanel.addMouseMotionListener(selectActivitate);
                actPanel.add(act);
                listaActivitati.add(actPanel);
                lastActivitateSelected = selectActivitate;
                nrActivitati++;
            }
            JPanel newActPanel = new JPanel();
            if (par) newActPanel.setBackground(new Color(243, 243, 243));
            else newActPanel.setBackground(new Color(196, 196, 196));
            newActPanel.setLayout(new CardLayout());
            JLabel newAct = new JLabel("Creaza Activitate");
            SelectActivitate selectActivitate = new SelectActivitate(vizualizareActivitateGrup, newActPanel, null, -1, null, null, null, 0, 0, false);
            newActPanel.addMouseListener(selectActivitate);
            newActPanel.addMouseMotionListener(selectActivitate);
            newActPanel.add(newAct);
            listaActivitati.add(newActPanel);
            nrActivitati++;
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
        vizualizareActivitateGrup.setVisible(false);
        creareActivitateGrup.setVisible(true);
    }

    public void setActivitate(SelectActivitate selectActivitate) {
        if (selectActivitate == null) {
            lastActivitateSelected.triggerClick();
            return;
        }
        if (lastActivitateSelected != null && !lastActivitateSelected.equals(selectActivitate))
            lastActivitateSelected.resetColor();
        vizualizareActivitateGrup.setVisible(true);
        creareActivitateGrup.setVisible(false);
        lastActivitateSelected = selectActivitate;
        selectActivitate.updateActivitate();
    }

    public int getActivitateId() {
        return lastActivitateSelected.idActivitate;
    }

    public void setActivitateData(ResultSet rs) {
        lastActivitateSelected.setData(rs);
    }

    public JButton getInscriereActivitate() {
        return vizualizareActivitateGrup.getInscriere();
    }

    public CreareActivitateGrup getCreareActivitateGrup() {
        return creareActivitateGrup;
    }

    public void retragereGrup() {
        inscris.set(grupuri.getSelectedIndex(), false);
        modificare();
    }

    public void inscriereGrup() {
        inscris.set(grupuri.getSelectedIndex(), true);
        modificare();
    }

    public class SelectActivitate extends MouseAdapter {
        private final int idActivitate;
        private final JPanel parent;
        private final Color originalColor;
        private final VizualizareActivitateGrup va;
        private boolean ok = false;
        private boolean inscrisActivitate;
        private String nume;
        private Timestamp dataActivitate;
        private Timestamp dataExpirare;
        private Time durata;
        private int nrMinStud;
        private int nrStud;

        public SelectActivitate(VizualizareActivitateGrup va, JPanel parent, String nume, int idActivitate, Timestamp dataActivitate, Timestamp dataExpirare, Time durata, int nrMinStud, int nrStud, boolean inscrisActivitate) {
            this.idActivitate = idActivitate;
            this.parent = parent;
            setData(nume, dataActivitate, dataExpirare, durata, nrMinStud, nrStud, inscrisActivitate);
            this.va = va;
            originalColor = parent.getBackground();
        }

        private void setData(String nume, Timestamp dataActivitate, Timestamp dataExpirare, Time durata, int nrMinStud, int nrStud, boolean inscrisActivitate) {
            this.dataActivitate = dataActivitate;
            this.dataExpirare = dataExpirare;
            this.nrMinStud = nrMinStud;
            this.durata = durata;
            this.nrStud = nrStud;
            this.inscrisActivitate = inscrisActivitate;
            this.nume = nume;
        }

        public void setData(ResultSet rs) {
            try {
                rs.next();
                nrStud = rs.getInt(8);
                inscrisActivitate = rs.getBoolean(7);
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
            va.setButton(inscrisActivitate);
            va.setNume(nume);
            va.setDataActivitate(dataActivitate);
            va.setDataExpirare(dataExpirare);
            va.setDurata(durata);
            va.setNrMinStud(nrMinStud);
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
