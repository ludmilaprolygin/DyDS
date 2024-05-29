package View.Popup;

public class DeleteItem extends PopupItem
{
    public DeleteItem()
    {
        super("Delete!");
    }

    public void setBehaviour()
    {
        addActionListener(actionEvent ->
        {
            System.out.println("Ya elimine");
        });
    }
}

//    JMenuItem deleteItem = new JMenuItem("Delete!");
//    deleteItem.addActionListener(actionEvent -> {
//        if(savedTVSeries.getSelectedIndex() > -1){
//          DataBase.deleteEntry(savedTVSeries.getSelectedItem().toString());
//          savedTVSeries.setModel(new DefaultComboBoxModel(DataBase.getTitles().stream().sorted().toArray()));
//          storedPageContent.setText("");
//        }
//    });