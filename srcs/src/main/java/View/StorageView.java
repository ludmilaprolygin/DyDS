package View;

import Presenter.Implemented.ModifySavedEntriesPresenterImpl;
import Presenter.Implemented.ShowSavedPagePresenterImpl;
import utils.Messages.UnsuccessfulTask;
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
    protected ShowSavedPagePresenterImpl showSavedPagePresenter;
    protected ModifySavedEntriesPresenterImpl modifySavedEntriesPresenter;

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

    public void setShowSavedPagePresenter(ShowSavedPagePresenterImpl showSavedPagePresenter)
        { this.showSavedPagePresenter = showSavedPagePresenter; }
    public void setModifyDataBasePresenter(ModifySavedEntriesPresenterImpl modifySavedEntriesPresenter)
        { this.modifySavedEntriesPresenter = modifySavedEntriesPresenter; }

    public void setSavedTVSeriesModel(Object[] savedTVSeriesTitles)
    {
        DefaultComboBoxModel comboBoxModel =
                new DefaultComboBoxModel(savedTVSeriesTitles);
        savedTVSeries.setModel(comboBoxModel);
    }

    public String getSelectedTitle() { return savedTVSeries.getSelectedItem().toString(); }
    public String getSelectedContent() { return storedPageContent.getText(); }

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

    protected void initializeURLdeterminationListener()
    {
        storedPageContent.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (oneLeftClick(e))
                {
                    int position = storedPageContent.viewToModel2D(e.getPoint());
                    if (position != -1)
                    {
                        HTMLDocument doc = (HTMLDocument) storedPageContent.getDocument();
                        Element element = doc.getCharacterElement(position);
                        int startOffset = element.getStartOffset();
                        int endOffset = element.getEndOffset();

                        try
                        {
                            String clickedText = doc.getText(startOffset, endOffset - startOffset);
                            if(StringFormatting.isURL(clickedText))
                                Desktop.getDesktop().browse(new URI(clickedText));
                        }
                        catch (Exception ex)
                            { UnsuccessfulTask.wikipediaError(); }
                    }
                }
            }
        });
    }
    protected boolean oneLeftClick(MouseEvent e)
        { return SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1; }
}