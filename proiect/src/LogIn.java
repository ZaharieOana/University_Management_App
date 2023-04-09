import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class LogIn extends JPanel {
    private final SpringLayout springLayout = new SpringLayout();
    private final JLabel emailL;
    private final JHintTextField emailT = new JHintTextField("email");
    private final JLabel passwordL;
    private final JHintPasswordField passwordT = new JHintPasswordField("password");
    private final JToggleButton toggleButton = new JToggleButton();
    private final JButton log_in = new JButton("Log In");
    private final JLabel mesaj = new JLabel("Email sau parola incorecte!");
    private final JPanel emailPanel = new JPanel();
    private final JPanel passwordPanel = new JPanel();
    private final SpringLayout emailLayout = new SpringLayout();
    private final SpringLayout passwordLayout = new SpringLayout();

    LogIn() {
        this.setLayout(springLayout);
        this.setBackground(Color.white);

        emailPanel.setBackground(Color.white);
        passwordPanel.setBackground(Color.white);
        emailPanel.setLayout(emailLayout);
        passwordPanel.setLayout(passwordLayout);

        emailT.setPreferredSize(new Dimension(240, 30));
        passwordT.setPreferredSize(new Dimension(200, 30));

        emailL = new JLabel(new ImageIcon("user.png"));
        passwordL = new JLabel(new ImageIcon("pasw.jpg"));
        emailL.setPreferredSize(new Dimension(50, 50));
        passwordL.setPreferredSize(new Dimension(50, 50));

        toggleButton.addItemListener(e -> passwordT.setHide(e.getStateChange() != ItemEvent.SELECTED));
        toggleButton.setPreferredSize(new Dimension(30, 30));
        toggleButton.setBackground(Color.white);
        toggleButton.setIcon(new ImageIcon("show.png"));
        toggleButton.setSelectedIcon(new ImageIcon("hide.png"));
        toggleButton.setFocusable(false);
        toggleButton.setBorderPainted(false);
        toggleButton.setContentAreaFilled(false);

        JLabel utLogo = new JLabel(new ImageIcon("utcn.png"));
        log_in.setPreferredSize(new Dimension(100, 40));

        mesaj.setVisible(false);
        mesaj.setForeground(Color.red);
        this.add(utLogo);

        emailPanel.add(emailL);
        emailPanel.add(emailT);
        this.add(emailPanel);
        emailPanel.setPreferredSize(new Dimension(300, 50));

        passwordPanel.add(passwordL);
        passwordPanel.add(passwordT);
        passwordPanel.add(toggleButton);
        this.add(passwordPanel);
        passwordPanel.setPreferredSize(new Dimension(300, 50));

        this.add(log_in);
        this.add(mesaj);

        emailLayout.putConstraint(SpringLayout.VERTICAL_CENTER, emailT, 0, SpringLayout.VERTICAL_CENTER, emailPanel);
        emailLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, emailT, 30, SpringLayout.HORIZONTAL_CENTER, emailPanel);
        emailLayout.putConstraint(SpringLayout.VERTICAL_CENTER, emailL, 0, SpringLayout.VERTICAL_CENTER, emailPanel);
        emailLayout.putConstraint(SpringLayout.EAST, emailL, -10, SpringLayout.WEST, emailT);

        springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, emailPanel, -20, SpringLayout.VERTICAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, emailPanel, 0, SpringLayout.HORIZONTAL_CENTER, this);

        passwordLayout.putConstraint(SpringLayout.VERTICAL_CENTER, passwordT, 0, SpringLayout.VERTICAL_CENTER, passwordPanel);
        passwordLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, passwordT, 10, SpringLayout.HORIZONTAL_CENTER, passwordPanel);
        passwordLayout.putConstraint(SpringLayout.VERTICAL_CENTER, passwordL, 0, SpringLayout.VERTICAL_CENTER, passwordPanel);
        passwordLayout.putConstraint(SpringLayout.EAST, passwordL, -10, SpringLayout.WEST, passwordT);
        passwordLayout.putConstraint(SpringLayout.VERTICAL_CENTER, toggleButton, 0, SpringLayout.VERTICAL_CENTER, passwordPanel);
        passwordLayout.putConstraint(SpringLayout.WEST, toggleButton, 10, SpringLayout.EAST, passwordT);

        springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, passwordPanel, 40, SpringLayout.VERTICAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, passwordPanel, 0, SpringLayout.HORIZONTAL_CENTER, this);

        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, utLogo, 0, SpringLayout.HORIZONTAL_CENTER, this);

        springLayout.putConstraint(SpringLayout.SOUTH, log_in, -50, SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, log_in, 0, SpringLayout.HORIZONTAL_CENTER, this);

        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, mesaj, 0, SpringLayout.HORIZONTAL_CENTER, this);
        springLayout.putConstraint(SpringLayout.SOUTH, mesaj, -10, SpringLayout.NORTH, log_in);

        setFocusable(true);
        requestFocus();
    }

    public void setButtonLogIn(Controller.ButtonLogIn b) {
        log_in.addActionListener(b);
    }

    public String getEmail() {
        return emailT.getText();
    }

    public String getPassword() {
        return passwordT.getPasswordText();
    }

    public void setLogIn(boolean valid) {
        this.mesaj.setVisible(!valid);
        if (valid) {
            emailT.reset();
            passwordT.reset();
        }
    }
}
