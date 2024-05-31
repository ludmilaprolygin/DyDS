package Presenter;

import Model.PageModel;
import Presenter.Listeners.ModelListener;
import View.SearchView;
import dyds.tvseriesinfo.fulllogic.SearchResult;

public class ShowSearchedPagePresenter extends PagePresenter
{
    public ShowSearchedPagePresenter(SearchView searchView, PageModel pageModel)
    {
        super(searchView, pageModel);

        this.view = searchView;
    }
    protected void initializePageModelListener()
    {
        pageModel.addListener(new ModelListener()
        {
            @Override
            public void taskFinished() { showPageContent(); }
        });
    }

    public void onSelectedSearchResult(SearchResult selectedSearchResult)
    {
        view.disableAll();

        String pageID = selectedSearchResult.getPageID();
        pageModel.getPageFromWikipedia(pageID);
    }

    protected void showPageContent()
    {
        super.showPageContent();

        view.enableAll();
    }
}