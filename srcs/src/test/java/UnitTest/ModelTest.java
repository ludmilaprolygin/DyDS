package UnitTest;

import Model.Model;
import Implementations.ModelImpl;
import Implementations.ModelListenerImpl;
import Presenter.Listeners.ModelListener;
import org.junit.*;
import static org.junit.Assert.*;

public class ModelTest
{
    protected Model modelTest;
    protected ModelListener modelListenerImpl;

    @Before
    public void setUp()
    {
        modelTest = new ModelImpl();
        modelListenerImpl = new ModelListenerImpl();
    }

    @Test
    public void addListenerTest_oneListener()
    {
        modelTest.addListener(modelListenerImpl);
        assertEquals("La lista de listeners deberia tener un unico listener", 1, modelTest.getListeners().size());
        assertTrue("El listener deberia estar en la lista de listeners", modelTest.getListeners().contains(modelListenerImpl));
    }

    @Test
    public void addListenerTest_severalListeners()
    {
        int quantity = 10;

        for (int i = 0; i < quantity; i++)
            { modelTest.addListener(modelListenerImpl); }

        assertEquals("La lista de listeners deberia tener " + quantity + " listeners", modelTest.getListeners().size(), quantity);
    }
}