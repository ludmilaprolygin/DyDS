package Presenter.Listeners;

public interface ModelListener
{
    void didSearchTermOnWiki();
    void didSearchPageOnWiki();
    void didGetExtract();
    void didDeletedSaved();
    void didSaveTVSeries();
    void didRateTVSeries();
    void didChangeTVSeries();
}