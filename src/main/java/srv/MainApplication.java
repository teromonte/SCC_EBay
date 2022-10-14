package main.java.srv;

import jakarta.ws.rs.core.Application;
import main.java.srv.MediaResource;

import java.util.HashSet;
import java.util.Set;

public class MainApplication extends Application
{
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> resources = new HashSet<Class<?>>();

	public MainApplication() {
		resources.add(srv.ControlResource.class);
		resources.add(MediaResource.class);
		singletons.add( new MediaResource());	
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
