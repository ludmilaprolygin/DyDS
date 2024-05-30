package Presenter;

import Model.*;
import Presenter.Listeners.ModelListener;
import View.Search.SearchView;
import View.Popup.WikiSearchesPopupMenu;
import View.Storage.StorageView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import dyds.tvseriesinfo.fulllogic.DataBase;
import dyds.tvseriesinfo.fulllogic.SearchResult;
import retrofit2.Response;
import utils.JsonProcessing;
import utils.StringFormatting;

import javax.swing.*;

public class SearchPresenterDeprecated
{
    protected SearchModel searchModel;
    protected SearchView searchView;
    protected PageModel pageModel;
    protected StorageView storageView;


    protected void initializeListeners()
    {
        //initializeSaveLocallyButtonListeners();
        //initializePageModelListeners();
    }


//    protected void initializeSearchResultsListeners()
//    {
//
//            sr.addActionListener(actionEvent ->
//            {
//                String pageID = sr.getPageID();
//                pageModel.getPageFromWikipedia(pageID);
//                Response<String>  callForPageResponse = pageModel.getResponse();
//                //showPageResults(sr);
//            });
//    }
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
                        StringFormatting.textToHtml(
                                DataBase.getExtract(savedTVSeries.getSelectedItem().toString())
                        )));
        // This line is the one that makes the magic happen, it sets the text of the storedPageContent to the text of the selected item in the comboBox
        // The text is retrieved from the database using the getExtract method, which returns the text of the selected item
    }

//    public void showPageResults(SearchResult sr)
//    {
//        Response<String> callForPageResponse = pageModel.getResponse();
//        JsonObject jobj2 = JsonProcessing.jsonObjectSetUp(callForPageResponse);
//        JsonObject query2 = JsonProcessing.jsonQuery(jobj2);
//        JsonObject pages = query2.get("pages").getAsJsonObject();
//        Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
//        Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
//        JsonObject page = first.getValue().getAsJsonObject();
//        JsonElement searchResultExtract2 = page.get("extract");
//        String fullurl = page.get("fullurl").getAsString();
//        JTextPane searchPageContent = searchView.getPaneContent();
//        String text = "";
//        String selectedResultTitle = null;
//
//        if (searchResultExtract2 == null)
//        {
//            text = "No Results";
//        }
//        else
//        {
//            text = "<h1>" + sr.getTitle() + "</h1>";
//            selectedResultTitle = sr.getTitle();
//            text += searchResultExtract2.getAsString().replace("\\n", "\n");
//            text += "<p><a href='" + fullurl + "'>" + fullurl + "</a></p>";
//            text = StringFormatting.textToHtml(text);
//        }
//        searchPageContent.setText(text);
//        searchPageContent.setCaretPosition(0);
//        //Back to edit time!
//        searchView.enableAll();
//    }
}