package UnitTest;

import Model.APIs.WikipediaSearchAPI;
import Model.SearchModel;
import org.junit.*;
import retrofit2.Call;
import retrofit2.Response;
import Presenter.Listeners.ModelListener;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class SearchModelTest
{
    protected SearchModel searchModelTest;
    protected WikipediaSearchAPI searchAPIMock;
    protected Call callMock;
    protected Response<String> response;

    @Before
    public void setUp()
    {
        searchModelTest = new SearchModel();
        searchAPIMock = mock(WikipediaSearchAPI.class);
        callMock = mock(Call.class);
        response = Response.success("Mock response");
    }

    @Test
    public void getPageFromWikipediaTest() throws IOException
    {
        when(searchAPIMock.searchForTerm(anyString())).thenReturn(callMock);
        when(callMock.execute()).thenReturn(response);

        searchModelTest.searchInWikipedia("testTerm");

        assertEquals(response, searchModelTest.getResponse());
    }

    @Test
    public void notifyListenersTest()
    {
        searchModelTest.addListener(new ModelListener()
        {
            @Override
            public void didSearchTermOnWiki() { assertTrue(true); }

            @Override
            public void didSearchPageOnWiki() { fail(); }

            @Override
            public void didGetExtract() { fail(); }

            @Override
            public void didDeletedSaved() { fail(); }

            @Override
            public void didSaveTVSeries() { fail(); }

            @Override
            public void didRateTVSeries() { fail(); }

            @Override
            public void didChangeTVSeries() { fail(); }
        });
        searchModelTest.searchInWikipedia("testTerm");
    }
}