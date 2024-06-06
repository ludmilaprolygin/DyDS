package View;

import utils.ViewFormatting;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class RatedView extends View
{
    private JPanel ratedPanel;
    private JTable ratedTVSeries;
    protected DefaultTableModel tableModel;


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
}
