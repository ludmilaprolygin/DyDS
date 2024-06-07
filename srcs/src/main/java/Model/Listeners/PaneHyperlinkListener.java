package Model.Listeners;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class PaneHyperlinkListener implements HyperlinkListener
{
    public void hyperlinkUpdate(HyperlinkEvent e)
    {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
        {
            try
            {
                Desktop.getDesktop().browse(new URI(e.getURL().toString()));
            }
            catch (IOException | URISyntaxException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}