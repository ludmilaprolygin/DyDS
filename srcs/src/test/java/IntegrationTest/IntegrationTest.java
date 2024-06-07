package IntegrationTest;

import Model.DataBaseModel;
import Model.PageModel;
import Presenter.Interfaces.RatedDataBasePresenter;
import Presenter.RatedSeries;
import Stubs.DataBaseModelStub;
import Stubs.PageModelStub;
import Stubs.RatedTVSeriesDataBaseStub;
import View.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.mock;

public class IntegrationTest
{
    SearchView searchView;
    StorageView storageView;
    RatedView ratedView;
    PageModel pageModelStub;
    DataBaseModel dataBaseModelStub;
    RatedDataBasePresenter ratedDataBasePresenter;

    @Before
    public void setUp()
    {
        MainWindow mainWindow = new MainWindow();
        searchView = mainWindow.getSearchView();
        storageView = mainWindow.getStorageView();
        ratedView = mainWindow.getRatedView();

        pageModelStub = new PageModelStub();
        dataBaseModelStub = new DataBaseModelStub();
    }

    @Test
    public void getSavedTitlesTest()
    {
        ArrayList<String> titles = dataBaseModelStub.getSavedTitles();

        ArrayList<String> expectedTitles = new ArrayList<>();
        expectedTitles.add("title1");
        expectedTitles.add("title2");
        expectedTitles.add("title3");

        assert(titles.equals(expectedTitles));
    }

    @Test
    public void getRatedTitlesTest()
    {
        ArrayList<String> titles = dataBaseModelStub.getRatedTitles();

        ArrayList<String> expectedTitles = new ArrayList<>();
        expectedTitles.add("title1");
        expectedTitles.add("title2");
        expectedTitles.add("title3");

        assert(titles.equals(expectedTitles));
    }

    @Test
    public void getEntryTest()
    {
        RatedTVSeriesDataBaseStub ratedTVSeriesDataBaseStub = new RatedTVSeriesDataBaseStub();
        Date date = mock(Date.class);

        RatedSeries expectedRatedResult = new RatedSeries("111", "title1",  1, date);
        RatedSeries ratedResult = ratedTVSeriesDataBaseStub.getEntry("title1");


        assert(expectedRatedResult.getPageID().equals(ratedResult.getPageID()));
    }
}