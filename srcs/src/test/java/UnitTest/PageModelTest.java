package UnitTest;

import Model.APIs.WikipediaPageAPI;
import Model.PageModel;
import org.junit.*;
import retrofit2.Call;
import retrofit2.Response;
import Model.Listeners.ModelListener;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PageModelTest
{
    protected PageModel pageModelTest;
    protected WikipediaPageAPI pageAPIMock;
    protected Call<String> callMock;
    protected Response<String> response;

    @Before
    public void setUp()
    {
        pageModelTest = new PageModel();
        pageAPIMock = mock(WikipediaPageAPI.class);
        callMock = mock(Call.class);
        response = Response.success("Mock response");
    }
    @Test
    public void getPageFromWikipediaTest() throws IOException
    {
        when(pageAPIMock.getExtractByPageID(anyString())).thenReturn(callMock);
        when(callMock.execute()).thenReturn(response);

        pageModelTest.getPageFromWikipedia("testPageID");

        assertEquals(response, pageModelTest.getResponse());
    }

    @Test
    public void notifyListenersTest()
    {
        pageModelTest.addListener(new ModelListener()
        {
            @Override
            public void didSearchTermOnWiki() { fail(); }

            @Override
            public void didSearchPageOnWiki() { assertTrue(true); }

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
        pageModelTest.getPageFromWikipedia("testPageID");
    }
}