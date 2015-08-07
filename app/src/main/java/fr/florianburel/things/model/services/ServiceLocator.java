package fr.florianburel.things.model.services;

import fr.florianburel.things.model.services.dummy.LocalBasketRepository;
import fr.florianburel.things.model.services.dummy.VolleyParseClient;

/**
 * Created by fl0 on 04/08/15.
 */
public class ServiceLocator {

    public static final int VERSION = 1;

    private static ServiceLocator instance;

    private ServiceLocator()
    {

    }

    public static ServiceLocator getInstance ()
    {
        if(instance == null)
        {
            instance = new ServiceLocator();
        }
        return instance;
    }

    private IThingsClient _thingClient;
    public IThingsClient getThingClient() {

        if(_thingClient == null)
        {
            _thingClient = new VolleyParseClient();
        }
        return _thingClient;
    }

    private IBasketRepository _basketRepository;
    public IBasketRepository getBasketRepository() {

        if(_basketRepository == null)
        {
            _basketRepository = new LocalBasketRepository();
        }
        return _basketRepository;
    }

}
