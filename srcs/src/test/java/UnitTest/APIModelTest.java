package UnitTest;

import Impls.APIModelImpl;
import Model.APIModel;
import retrofit2.Response;
import org.junit.*;
import static org.junit.Assert.*;

public class APIModelTest
{
    protected APIModel APIModelTest;

    @Before
    public void setUp()
    {
        APIModelTest = new APIModelImpl();
    }

    @Test
    public void getResponseTest()
    {
        Response<String> response = APIModelTest.getResponse();
        assertNull("La response deberia ser nula", response);
    }
}