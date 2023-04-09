import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class JHintPasswordField extends JPasswordField {

    private final String hint;
    Font gainFont = new Font("TimesRoman", Font.BOLD, 13);
    Font lostFont = new Font("TimesRoman", Font.ITALIC, 13);
    private boolean hide = true;

    public JHintPasswordField(final String hint) {
        setText(hint);
        setFont(lostFont);
        setForeground(Color.GRAY);
        setEchoChar((char) 0);
        this.hint = hint;

        this.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                if (hide) setEchoChar('*');
                else setEchoChar((char) 0);
                setForeground(Color.BLACK);
                if (getPasswordText().equals(hint)) {
                    setText("");
                    setFont(gainFont);
                } else {
                    setText(getPasswordText());
                    setFont(gainFont);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getPasswordText().equals(hint) || getPasswordText().length() == 0) {
                    setText(hint);
                    setFont(lostFont);
                    setForeground(Color.GRAY);
                    setEchoChar((char) 0);
                } else {
                    setText(getPasswordText());
                    setFont(gainFont);
                    setForeground(Color.BLACK);
                }
            }
        });
    }

    public String getPasswordText() {
        String rez = String.valueOf(super.getPassword());
        if (rez.equals(hint)) return "";
        return rez;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
        if (!(getPasswordText().equals(hint) || getPasswordText().length() == 0)) {
            if (hide) setEchoChar('*');
            else setEchoChar((char) 0);
        }
    }

    public void reset() {
        setText(hint);
        setFont(lostFont);
        setForeground(Color.GRAY);
        setEchoChar((char) 0);
        hide = true;
    }
}
