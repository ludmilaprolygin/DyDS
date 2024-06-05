package View;

import Presenter.ModifySavedEntriesPresenter;
import Presenter.ShowSavedPagePresenter;
import utils.StringFormatting;

import javax.swing.*;
import javax.swing.text.Element;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

public class StorageView extends View
{
    protected JPanel storagePanel;
    protected JComboBox<String> savedTVSeries;
    protected JTextPane storedPageContent;
    protected JPopupMenu optionsPopup;
    protected ShowSavedPagePresenter showSavedPagePresenter;
    protected ModifySavedEntriesPresenter modifySavedEntriesPresenter;

    public StorageView()
    {
        setUp();
    }

    protected void setUp()
    {
        optionsPopupSetUp();

        storedPageContentSetUp();
        storagePanelSetUp();

        initializeListeners();
    }
    protected void optionsPopupSetUp()
    {
        optionsPopup = new JPopupMenu();

        deleteItemSetUp();
        saveChangesItemSetUp();
    }
    protected void deleteItemSetUp()
    {
        JMenuItem deleteItem = new JMenuItem("Delete!");
        deleteItem.addActionListener(actionEvent -> { modifySavedEntriesPresenter.deleteSelected(); });
        optionsPopup.add(deleteItem);
    }
    protected void saveChangesItemSetUp()
    {
        JMenuItem saveChangesItem = new JMenuItem("Save changes!");
        saveChangesItem.addActionListener(actionEvent -> { modifySavedEntriesPresenter.saveChangesOnSelected(); });
        optionsPopup.add(saveChangesItem);
    }
    protected void storedPageContentSetUp()
    {
        storedPageContent.setContentType("text/html");
        storedPageContent.setEditable(true);
        storedPageContent.setComponentPopupMenu(optionsPopup);
    }
    protected void storagePanelSetUp() { storagePanel.setToolTipText("Stored info"); }
    protected void initializeListeners()
    {
        initializeSavedTVSeriesListener();
        initializeURLdeterminationListener();
    }
    protected void initializeSavedTVSeriesListener()
    {
        savedTVSeries.addActionListener(e ->
                showSavedPagePresenter.onSelectedSavedResult());
    }

    public JPanel getStoragePanel() { return storagePanel; }
    public JComboBox<String> getSavedTVSeries() { return savedTVSeries; }
    public JTextPane getPaneContent() { return storedPageContent; }

    public void setShowSavedPagePresenter(ShowSavedPagePresenter showSavedPagePresenter)
        { this.showSavedPagePresenter = showSavedPagePresenter; }
    public void setModifyDataBasePresenter(ModifySavedEntriesPresenter modifySavedEntriesPresenter)
        { this.modifySavedEntriesPresenter = modifySavedEntriesPresenter; }

    public void disableAll()
    {
        for(Component c: storagePanel.getComponents())
            c.setEnabled(false);
        storedPageContent.setEnabled(false);
    }
    public void enableAll()
    {
        for(Component c: storagePanel.getComponents())
            c.setEnabled(true);
        storedPageContent.setEnabled(true);
    }

    public void setSavedTVSeriesModel(Object[] savedTVSeriesTitles)
    {
        DefaultComboBoxModel comboBoxModel =
                new DefaultComboBoxModel(savedTVSeriesTitles);
        savedTVSeries.setModel(comboBoxModel);
    }

    public String getSelectedTitle() { return savedTVSeries.getSelectedItem().toString(); }
    public String getSelectedContent() { return storedPageContent.getText(); }
    public boolean selectedEntryExists() { return (savedTVSeries.getSelectedIndex() > -1); }

    //////////////////

    protected void initializeURLdeterminationListener()
    {
        storedPageContent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
                    int pos = storedPageContent.viewToModel2D(e.getPoint());
                    if (pos != -1) {
                        HTMLDocument doc = (HTMLDocument) storedPageContent.getDocument();
                        Element elem = doc.getCharacterElement(pos);
                        int start = elem.getStartOffset();
                        int end = elem.getEndOffset();
                        try
                        {
                            String clickedText = doc.getText(start, end - start);
                            if(StringFormatting.isURL(clickedText))
                                Desktop.getDesktop().browse(new URI(clickedText));
                            System.out.println("Clicked text: " + clickedText);
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}