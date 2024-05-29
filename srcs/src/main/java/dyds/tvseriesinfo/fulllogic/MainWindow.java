package dyds.tvseriesinfo.fulllogic;

import Model.PageModel;
import Model.SearchModel;
import View.Search.Popup.SearchesPopupMenu;
import View.Storage.Popup.StoredInfoPopupMenu;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.awt.*;
import java.util.Map;
import java.util.Set;

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

  String selectedResultTitle = null; //For storage purposes, it may not coincide with the searched term (see below)
  String text = ""; //Last searched text! this variable is central for everything


  public MainWindow()
  {
    //WikipediaSearchAPI searchAPI = APIBuilder.createSearchAPI();
    //WikipediaPageAPI pageAPI = APIBuilder.createPageAPI();

    SearchModel searchModel = new SearchModel();
    PageModel pageModel = new PageModel();

    savedTVSeries.setModel(new DefaultComboBoxModel(DataBase.getTitles().stream().sorted().toArray()));

    searchPageContent.setContentType("text/html");
    storedPageContent.setContentType("text/html");
    // this is needed to open a link in the browser

    searchTextField.addActionListener(actionEvent ->
    {
      System.out.println("Esto escucha al ENTER cuando hay una busqueda");
      searchButton.doClick();
    });

    searchTextField.addPropertyChangeListener(propertyChangeEvent -> {
      if(!searchTextField.isEnabled())
        System.out.println("Esto se imprime cada vez que termina de ejecutar un setXstatus()");
    });

    searchButton.addActionListener(e -> new Thread(() -> {
              //This may take some time, dear user be patient in the meanwhile!
              setWorkingStatus();
              // get from service
              Response<String> callForSearchResponse;

                //ToAlberto: First, lets search for the term in Wikipedia
                //callForSearchResponse = searchAPI.searchForTerm(searchTextField.getText() + " (Tv series) articletopic:\"television\"").execute();
                searchModel.searchInWikipedia(searchTextField.getText() + " (Tv series) articletopic:\"television\"");
                callForSearchResponse = searchModel.getSearchResponse();

                //Show the result for testing reasons, if it works, dont forget to delete!
                System.out.println("(callForSearchResponse) JSON " + callForSearchResponse.body());

                //So we will use the Google library for JSONs (Gson) for its parsing and manipulation
                //Basically, we will turn the string into a JSON object,
                //With such object we can acceses to its fields using get(fieldname) method provided by Gson
                Gson gson = new Gson();
                JsonObject jobj = jsonObjectSetUp(callForSearchResponse, gson);
                JsonObject query = jsonQuery(jobj);
                JsonArray jsonResults = query.get("search").getAsJsonArray();

                //toAlberto: shows each result in the JSonArry in a Popupmenu
                SearchesPopupMenu searchOptionsMenu = new SearchesPopupMenu();//new JPopupMenu("Search Results");
                for (JsonElement je : jsonResults)
                {
                  SearchResult sr = new SearchResult(je);
                  searchOptionsMenu.add(sr);

                  //toAlberto: Adding an event to retrieve the wikipage when the user clicks an item in the Popupmenu
                  sr.addActionListener(actionEvent ->
                  {
                    try {
                      //This may take some time, dear user be patient in the meanwhile!
                      setWorkingStatus();

                      Response<String> callForPageResponse;

                      pageModel.getPageFromWikipedia(sr.pageID);
                      callForPageResponse = pageModel.getPageResponse();

                      System.out.println("(callForPageResponse) JSON " + callForPageResponse.body());

                      //toAlberto: This is similar to the code above, but here we parse the wikipage answer.
                      //For more details on Gson look for very important coment 1, or just google it :P
                      JsonObject jobj2 = jsonObjectSetUp(callForPageResponse, gson);
                      JsonObject query2 = jsonQuery(jobj2);
                      JsonObject pages = query2.get("pages").getAsJsonObject();
                      Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
                      Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
                      JsonObject page = first.getValue().getAsJsonObject();
                      JsonElement searchResultExtract2 = page.get("extract");
                      String fullurl = page.get("fullurl").getAsString();

                      if (searchResultExtract2 == null) {
                        text = "No Results";
                      } else {
                        text = "<h1>" + sr.title + "</h1>";
                        selectedResultTitle = sr.title;
                        text += searchResultExtract2.getAsString().replace("\\n", "\n");
                        text += "<p><a href='" + fullurl + "'>" + fullurl + "</a></p>";
                        text = textToHtml(text);
                      }
                      searchPageContent.setText(text);
                      searchPageContent.setCaretPosition(0);
                      //Back to edit time!
                      setWatingStatus();
                    } catch (Exception e12) {
                      System.out.println(e12.getMessage());
                    }
                  });
                }
                searchOptionsMenu.show(searchTextField, searchTextField.getX(), searchTextField.getY());

              //Now you can keep searching stuff!
              setWatingStatus();
    }).start());

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

  protected JsonObject jsonObjectSetUp(Response<String> response, Gson gson)
  {
    return gson.fromJson(response.body(), JsonObject.class);
  }
  protected JsonObject jsonQuery(JsonObject jsonObject) { return jsonObject.get("query").getAsJsonObject(); }
}