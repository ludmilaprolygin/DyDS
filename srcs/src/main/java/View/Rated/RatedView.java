package View.Rated;

import javax.swing.*;

public class RatedView
{
    protected JComboBox<String> ratedTVSeries;
    protected JTextPane ratedPageContent;
    protected JPanel ratedPanel;

    public RatedView()
    {
        setUp();
    }

    protected void setUp()
    {
        ratedPageContentSetUp();
        ratedPanelSetUp();
    }
    protected void ratedPageContentSetUp()
    {
        ratedPageContent.setContentType("text/html");
        ratedPageContent.setEditable(true);
    }
    protected void ratedPanelSetUp() { ratedPanel.setToolTipText("Rated TV series"); }

    public JPanel getRatedPanel() { return ratedPanel; }
    public JComboBox<String> getRatedTVSeries() { return ratedTVSeries; }
    public JTextPane getRatedPageContent() { return ratedPageContent; }
}