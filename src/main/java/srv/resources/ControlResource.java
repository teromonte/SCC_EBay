package main.java.srv.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Class with control endpoints.
 */
@Path("/ctrl")
public class ControlResource
{

	/**
	 * These methods just prints a string. It may be useful to check if the current
 	 */
	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		return "v: Projeto SCC final";
	}

}
