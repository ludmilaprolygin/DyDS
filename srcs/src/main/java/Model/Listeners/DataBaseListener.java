package Model.Listeners;

public interface DataBaseListener
{
    void didGetExtract();
    void didDeletedSaved();
    void didSaveTVSeries();
    void didRateTVSeries();
    void didChangeTVSeries();
}