import javax.swing.*;
import java.awt.*;

public class CreareMaterie extends JPanel {
    private final JLabel titlu = new JLabel("Adauga o materie");
    private final JLabel numeL = new JLabel("Nume: ");
    private final JLabel descriereL = new JLabel("Descriere: ");
    private final JTextField numeT = new JTextField();
    private final JTextField descriereT = new JTextField();
    private final JPanel gridPanel = new JPanel();
    private final SpringLayout springLayout = new SpringLayout();
    private final JButton back = new JButton("Back");
    private final JButton addU = new JButton("Adauga");

    CreareMaterie() {
        setLayout(springLayout);
        setBackground(Color.WHITE);
        gridPanel.setLayout(new GridLayout(2, 2, 0, 50));
        gridPanel.setBackground(Color.WHITE);

        titlu.setFont(new Font("TimesRoman", Font.BOLD, 35));
        add(titlu);

        numeL.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        numeT.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        descriereL.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        descriereT.setFont(new Font("TimesRoman", Font.PLAIN, 14));

        numeL.setPreferredSize(new Dimension(100, 20));
        numeT.setPreferredSize(new Dimension(200, 20));
        descriereL.setPreferredSize(new Dimension(100, 20));
        descriereT.setPreferredSize(new Dimension(200, 40));

        gridPanel.add(numeL);
        gridPanel.add(numeT);
        gridPanel.add(descriereL);
        gridPanel.add(descriereT);
        add(gridPanel);

        back.setPreferredSize(new Dimension(100, 40));
        back.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        add(back);
        addU.setPreferredSize(new Dimension(100, 40));
        addU.setFont(new Font("TimesRoman", Font.PLAIN, 19));
        add(addU);

        springLayout.putConstraint(SpringLayout.NORTH, titlu, 0, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titlu, 0, SpringLayout.HORIZONTAL_CENTER, this);

        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, gridPanel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, gridPanel, 0, SpringLayout.VERTICAL_CENTER, this);

        springLayout.putConstraint(SpringLayout.SOUTH, back, -20, SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.EAST, back, -20, SpringLayout.EAST, this);

        springLayout.putConstraint(SpringLayout.SOUTH, addU, -20, SpringLayout.NORTH, back);
        springLayout.putConstraint(SpringLayout.EAST, addU, -20, SpringLayout.EAST, this);
    }

    public String getNume() {
        return numeT.getText();
    }

    public String getDescriere() {
        return descriereT.getText();
    }

    public void resetTexts() {
        numeT.setText("");
        descriereT.setText("");
    }

    public void setButtonBackHome(Controller.ButtonBackHome buttonBackHome) {
        back.addActionListener(buttonBackHome);
    }

    public void setButtonAddMaterie(Controller.ButtonAddMaterie buttonAddMaterie) {
        addU.addActionListener(buttonAddMaterie);
    }

}
