package dyds.tvseriesinfo.fulllogic;

import Model.PageModel;
import Model.SearchModel;
import Presenter.SearchPresenter;
import View.Storage.Popup.StoredInfoPopupMenu;

import java.awt.*;

import javax.swing.*;

import retrofit2.Response;

public class MainWindow {
  private JTextField searchTextField;
  private JButton searchButton;
  private JPanel windowContentPane;
  private JTextPane searchPageContent;
  private JButton saveLocallyButton;
  private JTabbedPane tabbedPane;
  private JPanel searchPanel;
  private JPanel storagePanel;
  private JComboBox<String> savedTVSeries;
  private JTextPane storedPageContent;
  private JPopupMenu searchOptionsMenu;

  String selectedResultTitle = null; //For storage purposes, it may not coincide with the searched term (see below)
  String text = ""; //Last searched text! this variable is central for everything


  public MainWindow()
  {
    SearchModel searchModel = SearchModel.getInstance();
    PageModel pageModel = PageModel.getInstance();
    SearchPresenter searchPresenter = SearchPresenter.getInstance();

    savedTVSeries.setModel(new DefaultComboBoxModel(DataBase.getTitles().stream().sorted().toArray()));

    saveLocallyButton.addActionListener(actionEvent -> {
      if(text != ""){
        // save to DB  <o/
        DataBase.saveInfo(selectedResultTitle.replace("'", "`"), text);  //Dont forget the ' sql problem
        savedTVSeries.setModel(new DefaultComboBoxModel(DataBase.getTitles().stream().sorted().toArray()));
      }
    });

    savedTVSeries.addActionListener(actionEvent -> storedPageContent.setText(textToHtml(DataBase.getExtract(savedTVSeries.getSelectedItem().toString()))));
    // This line is the one that makes the magic happen, it sets the text of the storedPageContent to the text of the selected item in the comboBox
    // The text is retrieved from the database using the getExtract method, which returns the text of the selected item

    JPopupMenu storedInfoPopup = new StoredInfoPopupMenu();

    storedPageContent.setComponentPopupMenu(storedInfoPopup);
  }

  private void setWorkingStatus()
  { //This method is used to disable the search panel while the search is being performed
    System.out.print("setWorkingStatus(): ");
    for(Component c: searchPanel.getComponents())
      c.setEnabled(false);
    searchPageContent.setEnabled(false);
  }

  private void setWatingStatus()
  { //This method is used to enable the search panel after the search is done
    System.out.print("setWatingStatus(): ");
    for(Component c: searchPanel.getComponents())
      c.setEnabled(true);
    searchPageContent.setEnabled(true);
  }

  public static void main(String[] args)
  {
    try {
      //Set System L&F
      //UIManager.put("nimbusSelection", new Color(247,248,250)); este es el que hace que no se vea nada en la seleccion del menu de borrar y bla
      //UIManager.put("nimbusBase", new Color(51,98,140)); //This is redundant!

      for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    }
    catch (Exception e)
    {
      System.out.println("Something went wrong with UI!");
    }


    JFrame frame = new JFrame("TV Series Info Repo");


    frame.setContentPane(new MainWindow().windowContentPane);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.pack();
    frame.setVisible(true);

    DataBase.loadDatabase();
    DataBase.saveInfo("test", "sarasa");


    System.out.println(DataBase.getExtract("test"));
    System.out.println(DataBase.getExtract("nada")); //This should return null
  }

  public static String textToHtml(String text)
  { //This method is used to format the text to be displayed in the JTextPane

    StringBuilder builder = new StringBuilder();

    builder.append("<font face=\"arial\">");

    String fixedText = text
        .replace("'", "`"); //Replace to avoid SQL errors, we will have to find a workaround..

    builder.append(fixedText);

    builder.append("</font>");

    return builder.toString();
  }
}