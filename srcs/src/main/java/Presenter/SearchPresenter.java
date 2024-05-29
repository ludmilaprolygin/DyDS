package Presenter;

import Model.*;
import Presenter.Listeners.ModelListener;
import View.Search.SearchView;
import View.Popup.WikiSearchesPopupMenu;
import View.Storage.StorageView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dyds.tvseriesinfo.fulllogic.DataBase;
import dyds.tvseriesinfo.fulllogic.SearchResult;
import retrofit2.Response;
import utils.JsonProcessing;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Set;

public class SearchPresenter
{
    protected SearchModel searchModel;
    protected SearchView searchView;
    protected PageModel pageModel;
    protected StorageView storageView;

    public SearchPresenter(SearchView searchView, SearchModel searchModel)
    {
        this.searchView = searchView;
        this.searchModel = searchModel;

        pageModel = PageModel.getInstance();

        initializeListeners();
    }

    protected void initializeListeners()
    //public void initializeListeners()
    {
        initializeSearchModelListeners();
        //initializeSaveLocallyButtonListeners();
        initializePageModelListeners();
    }
    protected void initializeSearchModelListeners()
    {
        searchModel.addListener(new ModelListener()
        {
            @Override
            public void taskFinished()
            {
                showSearchResults();
            }
        });
    }
    public void onEnterKeyPress() { onClickSearchButton(); }
    public void onClickSearchButton()
    {
        JTextField searchTextField = searchView.getSearchTextField();
        new Thread(() ->
        {
            setWorkingStatus();
            String termToSearch = searchTextField.getText() + " (Tv series) articletopic:\"television\"";
            searchModel.searchInWikipedia(termToSearch);
        }).start();
    }
    protected void initializeSearchResultsListeners()
    {
        Iterable<SearchResult> searchOptionsMenu = searchView.getPopUp().getSearchResults();

        for (SearchResult sr : searchOptionsMenu)
        {
            sr.addActionListener(actionEvent ->
            {
                String pageID = sr.getPageID();
                pageModel.getPageFromWikipedia(pageID);
                Response<String>  callForPageResponse = pageModel.getResponse();
                showPageResults(sr);
            });
        }
    }
    protected void initializeSaveLocallyButtonListeners()
    {
        JButton saveLocallyButton = searchView.getSaveLocallyButton();
        JComboBox<String> savedTVSeries = storageView.getSavedTVSeries();
        savedTVSeries.setModel(new DefaultComboBoxModel(DataBase.getTitles().stream().sorted().toArray()));

        String selectedResultTitle = null;
        String text = "";

        saveLocallyButton.addActionListener(actionEvent -> {
            if(text != ""){
                // save to DB  <o/
                DataBase.saveInfo(selectedResultTitle.replace("'", "`"), text);  //Dont forget the ' sql problem
                savedTVSeries.setModel(new DefaultComboBoxModel(DataBase.getTitles().stream().sorted().toArray()));
            }
        });

        initializeSavedTVSeriesListeners();
    }
    protected void initializeSavedTVSeriesListeners()
    {
        JComboBox<String> savedTVSeries = storageView.getSavedTVSeries();
        JTextPane storedPageContent = storageView.getStoredPageContent();

        savedTVSeries.addActionListener(actionEvent ->
                storedPageContent.setText(
                        textToHtml(
                                DataBase.getExtract(savedTVSeries.getSelectedItem().toString())
                        )));
        // This line is the one that makes the magic happen, it sets the text of the storedPageContent to the text of the selected item in the comboBox
        // The text is retrieved from the database using the getExtract method, which returns the text of the selected item
    }
    protected void initializePageModelListeners()
    {
        pageModel.addListener(new ModelListener()
        {
            @Override
            public void taskFinished()
            {
                // NO SE TT
            }
        });
    }

    public void showSearchResults()
    {
        JsonProcessing jsonProcessing = new JsonProcessing();
        Response<String> searchResponse = searchModel.getResponse();
        JsonArray jsonResults = jsonProcessing.getQueryResults(searchResponse, "search");

        WikiSearchesPopupMenu searchOptionsMenu = searchView.createPopUp();

        for (JsonElement je : jsonResults)
        {
            SearchResult sr = new SearchResult(je);
            searchOptionsMenu.add(sr);
        }

        initializeSearchResultsListeners();
        searchView.displayPopUp();

        setWaitingStatus();
    }

    public void showPageResults(SearchResult sr)
    {
        JsonProcessing jsonProcessing = new JsonProcessing();
        Response<String> callForPageResponse = pageModel.getResponse();
        JsonObject jobj2 = jsonProcessing.jsonObjectSetUp(callForPageResponse);
        JsonObject query2 = jsonProcessing.jsonQuery(jobj2);
        JsonObject pages = query2.get("pages").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
        Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
        JsonObject page = first.getValue().getAsJsonObject();
        JsonElement searchResultExtract2 = page.get("extract");
        String fullurl = page.get("fullurl").getAsString();
        JTextPane searchPageContent = searchView.getPaneContent();
        String text = "";
        String selectedResultTitle = null;

        if (searchResultExtract2 == null)
        {
            text = "No Results";
        }
        else
        {
            text = "<h1>" + sr.getTitle() + "</h1>";
            selectedResultTitle = sr.getTitle();
            text += searchResultExtract2.getAsString().replace("\\n", "\n");
            text += "<p><a href='" + fullurl + "'>" + fullurl + "</a></p>";
            text = textToHtml(text);
        }
        searchPageContent.setText(text);
        searchPageContent.setCaretPosition(0);
        //Back to edit time!
        setWaitingStatus();
    }

    protected void setWorkingStatus() //This method is used to disable the search panel while the search is being performed
    {
        JPanel searchPanel = searchView.getSearchPanel();
        JTextPane searchPageContent = searchView.getPaneContent();

        for(Component c: searchPanel.getComponents())
            c.setEnabled(false);
        searchPageContent.setEnabled(false);
    }
//
    protected void setWaitingStatus() //This method is used to enable the search panel after the search is done
    {
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