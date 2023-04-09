import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class JHintTextField extends JTextField {

    private final String hint;
    Font gainFont = new Font("TimesRoman", Font.BOLD, 13);
    Font lostFont = new Font("TimesRoman", Font.ITALIC, 13);

    public JHintTextField(final String hint) {
        setText(hint);
        setFont(lostFont);
        setForeground(Color.GRAY);
        this.hint = hint;

        this.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                setForeground(Color.BLACK);
                if (getText().equals(hint)) {
                    setText("");
                    setFont(gainFont);
                } else {
                    setText(getText());
                    setFont(gainFont);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().equals(hint) || getText().length() == 0) {
                    setText(hint);
                    setFont(lostFont);
                    setForeground(Color.GRAY);
                } else {
                    setText(getText());
                    setFont(gainFont);
                    setForeground(Color.BLACK);
                }
            }
        });
    }

    public void reset() {
        setText(hint);
        setFont(lostFont);
        setForeground(Color.GRAY);
    }

    @Override
    public String getText() {
        String rez = super.getText();
        if (rez.equals(hint)) return "";
        return rez;
    }
}
