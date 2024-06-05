package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RatedView extends View
{
    private JPanel ratedPanel;
    private JList<RatedResult> ratedTVseriesList;


    public RatedView()
    {
        setUp();
    }
    protected void setUp()
    {
        searchPanelSetUp();
        loadRatedTVSeries();
    }

    public JPanel getRatedPanel() {
        return ratedPanel;
    }

    protected void searchPanelSetUp()
    {
        ratedPanel.setToolTipText("Rated TV series");
    }
    protected void loadRatedTVSeries()
    {
        DefaultListModel<RatedResult> listModel = new DefaultListModel<>();
        Date today = new Date();
        listModel.addElement(new RatedResult(333, "Breaking Bad", 10, today));


        ratedTVseriesList.setModel(listModel);
    }
    public JList<RatedResult> getRatedTVseriesList()
    {
        return ratedTVseriesList;
    }

    public JTextPane getPaneContent()
    {
        return null;
    }

    public void disableAll()
    {
        for(Component c: ratedPanel.getComponents())
            c.setEnabled(false);
    }

    public void enableAll()
    {
        for(Component c: ratedPanel.getComponents())
            c.setEnabled(true);
    }
}
