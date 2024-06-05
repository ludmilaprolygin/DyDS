package Model;

import Presenter.Listeners.ModelListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Model
{
    protected Map<String, ModelListener> modelListenersMap;
    protected ArrayList<ModelListener> modelListeners;
    public Model() { //modelListenersMap = new HashMap<String, ModelListener>();
        modelListeners = new ArrayList<ModelListener>();
    }
    public void addListener(ModelListener listener) //String modelName
        { //modelListenersMap.put(modelName, listener);
        modelListeners.add(listener);}
}