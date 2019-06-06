package pchess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * This is a manual to help user to use this software. A manual window is
 * openned, explaining how to play prechess games and configure game appearance.
 */
public class ManualWindow extends JDialog {

    /**
     * Singleton to use this window.
     */
    private static ManualWindow manual = null;

    private static final long serialVersionUID = 1L;

    /**
     * Show this manual.
     *
     * @param frame parent frame of this dialog.
     */
    public static void showDialog(JFrame frame) {
        if (manual == null) {
            manual = new ManualWindow(frame);
            manual.setVisible(true);
        } else {
            manual.requestFocus();
        }
    }

    /**
     * Starts a new manual window.
     *
     * @param frame parent frame of this dialog.
     */
    private ManualWindow(JFrame frame) {
        super(frame, "Help Manual", false);
        
        setSize(605, frame.getHeight());
        setLocation(frame.getX() + frame.getWidth()/2, frame.getY());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                manual = null;
            }
        });

        JEditorPane jep;
        try {
            ClassLoader cl = getClass().getClassLoader();
            URL url = cl.getResource("manual/index.html");
            jep = new JEditorPane(url);
            jep.setEditable(false);
            jep.addHyperlinkListener(new HyperLink());
        } catch (IOException ioe) {
            jep = new JEditorPane();
            jep.setText("Couldn't open desired page!!!");
        }

        JScrollPane jsc = new JScrollPane(jep);
        jsc.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(Color.BLUE))
        );
        JPanel content = new JPanel(new BorderLayout());
        content.add(jsc, BorderLayout.CENTER);
        setContentPane(content);
        
    }
    
    /**
     * Inner class to implement hyperlink events.
     */
    private class HyperLink implements HyperlinkListener{
        
        @Override
        public void hyperlinkUpdate(HyperlinkEvent e){
            if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
                JEditorPane source = (JEditorPane)e.getSource();
                URL page = e.getURL();
                try{
                    source.setPage(page);
                } catch(IOException ioe){
                    source.setContentType("text/plain");
                    source.setText("A error ocurred while trying to load specified page!!!");
                }
            }
        }
    }

}
