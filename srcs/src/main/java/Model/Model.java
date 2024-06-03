package Model;

import Presenter.Listeners.ModelListener;
import java.util.HashMap;
import java.util.Map;

public abstract class Model
{
    protected Map<String, ModelListener> modelListenersMap;
    public Model() { modelListenersMap = new HashMap<String, ModelListener>(); }
    public void addListener(String modelName, ModelListener listener)
        { modelListenersMap.put(modelName, listener); }

    protected void notifyListeners()
    {
        Iterable<ModelListener> modelListeners = this.modelListenersMap.values();
        for(ModelListener modelListener : modelListeners)
            modelListener.taskFinished();
    }
}