package View.Popup;

public class SaveChangesItem extends PopupItem
{
    public SaveChangesItem()
    {
        super("Save Changes!");
    }

    public void setBehaviour()
    {
        addActionListener(actionEvent ->
        {
            System.out.println("Ya guarde");
        });
    }
}

//    JMenuItem saveItem = new JMenuItem("Save Changes!");
//    saveItem.addActionListener(actionEvent -> {
//        // save to DB  <o/
//        DataBase.saveInfo(savedTVSeries.getSelectedItem().toString().replace("'", "`"), storedPageContent.getText());  //Dont forget the ' sql problem
//        //comboBox1.setModel(new DefaultComboBoxModel(DataBase.getTitles().stream().sorted().toArray()));
//    });