package Model;

import Presenter.Listeners.ModelListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Model
{
    protected ArrayList<ModelListener> modelListeners;
    public Model() { //modelListenersMap = new HashMap<String, ModelListener>();
        modelListeners = new ArrayList<ModelListener>();
    }
    public void addListener(ModelListener listener)
        { modelListeners.add(listener);}
}