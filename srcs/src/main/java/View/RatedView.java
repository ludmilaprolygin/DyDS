package View;

import Presenter.RatedDataBasePresenter;
import utils.ViewFormatting;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RatedView extends View
{
    private JPanel ratedPanel;
    private JTable ratedTVSeries;
    protected DefaultTableModel tableModel;
    protected String selectedTitle;
    protected RatedDataBasePresenter presenter;


    public RatedView()
    {
        setUp();
    }
    protected void setUp()
    {
        //ratedResults = new HashMap<Integer, RatedResult>();

        searchPanelSetUp();
        loadRatedTVSeries();
        addRowClickListener();
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
        String[] columnNames = {"Series title", "Score", "Last updated"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex)
            {
                switch (columnIndex) {
                    case 0:
                        return String.class;
                    case 1:
                        return Integer.class;
                    case 2:
                        return String.class;
                    default:
                        return Object.class;
                }
            }
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        ratedTVSeries.setModel(tableModel);
        ViewFormatting.setCellsAlignment(ratedTVSeries, SwingConstants.CENTER);

        sortRatedTVSeries();
    }
    public DefaultTableModel getTableModel() { return tableModel; }

    public void sortRatedTVSeries()
    {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(ratedTVSeries.getModel());
        ratedTVSeries.setRowSorter(sorter);

        int columnIndexToSort = 1;

        sorter.setSortKeys(List.of(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING)));
        sorter.sort();
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

    private void addRowClickListener()
    {
        ratedTVSeries.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ratedTVSeries.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent event)
            {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = ratedTVSeries.getSelectedRow();
                    if (selectedRow != -1)
                    {
                        int modelRow = ratedTVSeries.convertRowIndexToModel(selectedRow);
                        // Print the content of the first column
                        selectedTitle = tableModel.getValueAt(modelRow, 0).toString();
                        System.out.println(selectedTitle);
                        presenter.onClickRatedEntry();
                    }
                }
            }
        });
    }

    public String getSelectedTitle() { return selectedTitle; }

    public void setRatedDataBasePresenter(RatedDataBasePresenter p) { presenter = p; }
}
