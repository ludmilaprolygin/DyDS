package Presenter;

import Model.PageModel;
import Presenter.Listeners.ModelListener;
import View.SearchView;
import dyds.tvseriesinfo.fulllogic.SearchResult;

import javax.swing.*;

public class ShowPagePresenter extends PagePresenter
{
    protected SearchView searchView;

    public ShowPagePresenter(SearchView searchView, PageModel pageModel)
    {
        this.searchView = searchView;
        this.pageModel = pageModel;

        initializePageModelListeners();
    }
    protected void initializePageModelListeners()
    {
        pageModel.addListener(new ModelListener()
        {
            @Override
            public void taskFinished() { showPageContent(); }
        });
    }

    public void onSelectedSearchResult(SearchResult selectedSearchResult)
    {
        searchView.disableAll();

        String pageID = selectedSearchResult.getPageID();
        pageModel.getPageFromWikipedia(pageID);
    }

    public void showPageContent()
    {
        String textToDisplay = getExtractFromLastSearchResponse();
        JTextPane searchPageContent = searchView.getPaneContent();

        searchPageContent.setText(textToDisplay);
        searchPageContent.setCaretPosition(0);

        searchView.enableAll();
    }
}