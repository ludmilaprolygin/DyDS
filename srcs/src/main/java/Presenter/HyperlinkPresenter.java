package Presenter;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.net.URI;

public class HyperlinkPresenter
{
    public static void onHyperlinkClick()
    {
        new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
                {
                    try
                    {
                        Desktop.getDesktop().browse(new URI(e.getURL().toString()));
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        };
    }
}