package main.java.srv;

import jakarta.ws.rs.core.Application;
import main.java.srv.resources.AuctionResource;
import main.java.srv.resources.ControlResource;
import main.java.srv.resources.MediaResource;
import main.java.srv.resources.UserResource;

import java.util.HashSet;
import java.util.Set;

public class MainApplication extends Application {

    public static final boolean CACHE_FLAG = true;
    private Set<Object> singletons = new HashSet<Object>();
    private Set<Class<?>> resources = new HashSet<Class<?>>();

    public MainApplication() {
        resources.add(ControlResource.class);
        resources.add(MediaResource.class);
        singletons.add(new MediaResource());
        resources.add(AuctionResource.class);
        singletons.add(new AuctionResource());
        resources.add(UserResource.class);
        singletons.add(new UserResource());
    }

    @Override
    public Set<Class<?>> getClasses() {
        return resources;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
