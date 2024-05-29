package Presenter;

import Model.*;
import Presenter.Listeners.SearchModelListener;
import View.Search.SearchView;
import View.Storage.Popup.WikiSearchesPopupMenu;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dyds.tvseriesinfo.fulllogic.SearchResult;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Set;

public class SearchPresenter extends Presenter
{
    protected static SearchPresenter searchPresenter;
    protected SearchModel searchModel;
    protected SearchView searchView;
    protected PageModel pageModel;

    private SearchPresenter()
    {
        super();
        searchModel = SearchModel.getInstance();
        searchView = SearchView.getInstance();
        pageModel = PageModel.getInstance();

        initializeListeners();
    }
    public static SearchPresenter getInstance()
    {
        if(searchPresenter == null)
            searchPresenter = new SearchPresenter();
        return searchPresenter;
    }

    protected void initializeListeners()
    {
        initializeSearchModelListeners();
        initializeTextFieldListeners();
        initializeSearchButtonListeners();
        //initializeOptionMenuItemsListeners();
    }

    protected void initializeSearchModelListeners()
    {
        searchModel.addListener(new SearchModelListener()
        {
            @Override
            public void taskFinished()
            {
                showSearchResults();
            }
        });
    }
    protected void initializeTextFieldListeners()
    {
        JTextField searchTextField = searchView.getSearchTextField();
        JButton searchButton = searchView.getSearchButton();

        searchTextField.addActionListener(actionEvent ->
        {
            System.out.println("Esto escucha al ENTER cuando hay una busqueda");
            searchButton.doClick();
        });

        searchTextField.addPropertyChangeListener(propertyChangeEvent -> {
            if(!searchTextField.isEnabled())
                System.out.println("Esto se imprime cada vez que termina de ejecutar un setXstatus()");
        });
    }
    protected void initializeSearchButtonListeners()
    {
        JButton searchButton = searchView.getSearchButton();
        JTextField searchTextField = searchView.getSearchTextField();

        searchButton.addActionListener(e -> new Thread(() ->
        {
            setWorkingStatus();

            String termToSearch = searchTextField.getText() + " (Tv series) articletopic:\"television\"";
            searchModel.searchInWikipedia(termToSearch);
            Response<String> callForSearchResponse = searchModel.getResponse();

            System.out.println("(callForSearchResponse) JSON " + callForSearchResponse.body());

            showSearchResults();
        }).start());
    }
    protected void initializeOptionMenuItemsListeners()
    {
        Iterable<SearchResult> searchOptionsMenu = searchView.getPopUp().getSearchResults();

        for (SearchResult sr : searchOptionsMenu)
        {
            sr.addActionListener(actionEvent ->
            {
                String pageID = sr.getPageID();
                pageModel.getPageFromWikipedia(pageID);
                Response<String>  callForPageResponse = pageModel.getResponse();

                System.out.println("(callForPageResponse) JSON " + callForPageResponse.body());

                showPageResults(sr);
            });
        }
    }

    public void showSearchResults()
    {
        Response<String> searchResponse = searchModel.getResponse();
        JsonArray jsonResults = getQueryResults(searchResponse, "search");

        WikiSearchesPopupMenu searchOptionsMenu = searchView.createPopUp();

        for (JsonElement je : jsonResults)
        {
            SearchResult sr = new SearchResult(je);
            searchOptionsMenu.add(sr);
        }

        initializeOptionMenuItemsListeners();
        searchView.displayPopUp();

        setWatingStatus();
    }

    public void showPageResults(SearchResult sr)
    {
        Response<String> callForPageResponse = pageModel.getResponse();
        JsonObject jobj2 = jsonObjectSetUp(callForPageResponse);
        JsonObject query2 = jsonQuery(jobj2);
        JsonObject pages = query2.get("pages").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
        Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
        JsonObject page = first.getValue().getAsJsonObject();
        JsonElement searchResultExtract2 = page.get("extract");
        String fullurl = page.get("fullurl").getAsString();
        JTextPane searchPageContent = searchView.getPaneContent();
        String text = "";
        String selectedResultTitle = null;

        if (searchResultExtract2 == null) {
                        text = "No Results";
                      } else {
                        text = "<h1>" + sr.getTitle() + "</h1>";
                        selectedResultTitle = sr.getTitle();
                        text += searchResultExtract2.getAsString().replace("\\n", "\n");
                        text += "<p><a href='" + fullurl + "'>" + fullurl + "</a></p>";
                        text = textToHtml(text);
                      }
                      searchPageContent.setText(text);
                      searchPageContent.setCaretPosition(0);
                      //Back to edit time!
                      setWatingStatus();

    }

    protected void setWorkingStatus() //This method is used to disable the search panel while the search is being performed
    {
        System.out.print("setWorkingStatus(): ");

        JPanel searchPanel = searchView.getSearchPanel();
        JTextPane searchPageContent = searchView.getPaneContent();

        for(Component c: searchPanel.getComponents())
            c.setEnabled(false);
        searchPageContent.setEnabled(false);
    }

    protected void setWatingStatus() //This method is used to enable the search panel after the search is done
    {
        System.out.print("setWatingStatus(): ");

        JPanel searchPanel = searchView.getSearchPanel();
        JTextPane searchPageContent = searchView.getPaneContent();

        for(Component c: searchPanel.getComponents())
            c.setEnabled(true);
        searchPageContent.setEnabled(true);
    }

    ////////////

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