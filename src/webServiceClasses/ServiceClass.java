package webServiceClasses;

import DAO.Database;
import model.PersonTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Created by asus on 6/11/2020.
 */


@Path("/person")
public class ServiceClass {


    @Path("/helloWord")
    @Produces("text/plain")
    @GET
    public String myMethod() {
        return " Hello This is My First Service";
    }



    @GET
    @Produces("application/xml")
    @Path("/allPerson")
    public List<PersonTO> getAllPerson() {
        Database db = new Database();
        List<PersonTO> person = new ArrayList<>();
        try {
            person = db.select();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }



    @GET
    @Produces("application/json")
    @Path("/allPersonJson")
    public List<PersonTO> getAllPersonJson() {
        Database db = new Database();
        List<PersonTO> person = new ArrayList<>();
        try {
            person = db.select();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @GET
    @Produces("application/json")
    @Path("/personById")
    public PersonTO getPesonById(@QueryParam("id") int id) {
        Database db = new Database();
        PersonTO person = new PersonTO();
        try {
            person = db.select(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }


    @GET
    @Produces("application/json")
    @Path("/findById/{id}")
    public PersonTO getPesonById2(@PathParam("id") int personId) {
        Database db = new Database();
        PersonTO person = new PersonTO();

        try {
            person = db.select(personId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @GET
    @Produces("application/json")
    @Path("/personByNameFamily")
    public List<PersonTO> getPesonByNameFamily(@QueryParam("name") String name, @QueryParam("family") String family) {
        Database db = new Database();
        List<PersonTO> person = new ArrayList<>();
        try {
            person = db.select(name, family);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @GET
    @Produces("application/json")
    @Path("/personByNameFamily/{name}/{family}")
    public List<PersonTO> getPesonByNameFamily2(@PathParam("name") String name, @PathParam("family") String family) {
        Database db = new Database();
        List<PersonTO> person = new ArrayList<>();
        try {
            person = db.select(name, family);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @GET
    //@Produces("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/personByNameFamily/{name}")
    public List<PersonTO> getPesonByNameFamily3(@PathParam("name") String name, @QueryParam("family") String family) {
        Database db = new Database();
        List<PersonTO> person = new ArrayList<>();
        try {
            person = db.select(name, family);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @POST
    @Path("/insertPersonTo")
    @Consumes("application/json")
    public Response insertUser2(PersonTO personTO) {
        Database db = new Database();
        String success = "NOT";
        try {
            int personId = db.insert(personTO);

            if (personId == 1)
                success = "Successful";

            return Response.status(201).entity(success).build();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @POST
    @Path("/insertPerson")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertUser2(@FormParam("name") String name, @FormParam("family") String family) {
        Database db = new Database();
        try {
            db.insert(name, family);
            return Response.status(201).entity(name).build();
        } catch (Exception e) {
            return null;
        }

    }


    @POST
    @Path("/insertPersonForm")
    public Response insertUser(@FormParam("name") String name, @FormParam("family") String family) {
        Database db = new Database();
        try {
            db.insert(name, family);
            return Response.status(201).entity(name).build();
        } catch (Exception e) {
            return null;
        }

    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/UpdatePerson/{id}")
    public Response update(@PathParam("id") int id, PersonTO personTO) {
        Database db = new Database();
        personTO.setId(id);
        try {
            if (db.update(id, personTO)) {
                return Response.ok().build();
            } else {
                return Response.notModified().build();
            }


        } catch (Exception e) {
            return null;
        }
    }


    @DELETE
    @Path("/deletePerson/{id}")
    public Response delete(@PathParam("id") int id) {
        Database db = new Database();
        try {
        if (db.delete(id)) {
            return Response.ok().build();
        } else {
            return Response.notModified().build();
        }
        } catch (Exception e) {
            return null;
        }
    }


    }
