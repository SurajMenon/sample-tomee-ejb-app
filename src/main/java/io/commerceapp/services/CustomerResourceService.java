package io.commerceapp.services;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@Path("/customers")
@Consumes("application/json")
@Produces("application/json")
@ApplicationScoped
public class CustomerResourceService {
	
	private Map<Integer, Customer> customerDB = new ConcurrentHashMap<>();
	private AtomicInteger idCounter = new AtomicInteger();
	
	@Inject
    private Faculty faculty;
	
	@POST
	public Response createCustomer(Customer c) {
		c.setId(idCounter.incrementAndGet());
		customerDB.put(c.getId(), c);
		System.out.println("Created customer " + c.getId());
		return Response.created(URI.create("/customers/" + c.getId())).build();
	}
	
	@GET
	@Path("{id}")
	public Customer getCustomer(@PathParam("id") int id) {
		System.out.println("Received id is " + id);
		Customer c = customerDB.get(id);
		System.out.println("c = " + c);
		if (c == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
		return c;
	}
	
	@GET
	@Path("/dummy")
	public Response getDummyCustomer() {
		//Customer c = new Customer();
		//c.setId(idCounter.incrementAndGet());
		System.out.println(faculty.getFacultyName());
		return Response.status(200).entity(faculty).build();
	}
	
	@PUT
	@Path("{id}")
	public void updateCustomer(@PathParam("id") int id, Customer c) {
		Customer custOrig = customerDB.get(id);
		
		if (custOrig == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
		custOrig.setCity(c.getCity());
		custOrig.setCountry(c.getCity());
		custOrig.setFirstName(c.getFirstName());
		custOrig.setLastName(c.getLastName());
		custOrig.setState(c.getState());
		custOrig.setStreet(c.getStreet());
		custOrig.setZip(c.getZip());
	}

}
