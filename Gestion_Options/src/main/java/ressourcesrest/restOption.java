package ressourcesrest;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("options")
public class restOption {

    public static OptionBusiness optionBusiness = new OptionBusiness();

    // A2 + A3 : toutes les options, ou filtrées par domaine
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOption(@QueryParam("domaine") String d) {
        List<Option> l = (d == null) ? optionBusiness.getListeOptions()
                : optionBusiness.getOptionsByDomaine(d);
        if (l.isEmpty()) {
            return Response.status(204).build();
        }
        return Response.status(200).entity(l).build();
    }

    // A6 : une option par code
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOptionById(@PathParam("id") int id) {
        Option o = optionBusiness.getOptionByCode(id);
        if (o == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(o).build();
    }

    // A1 : création
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ajoutOption(Option op) {
        if (optionBusiness.getOptionByCode(op.getCodeOption()) != null) {
            return Response.status(400).build();   // code déjà existant
        }
        optionBusiness.addOption(op);
        return Response.status(200).build();
    }

    // A4 : suppression
    @DELETE
    @Path("{id}")
    public Response deleteOption(@PathParam("id") int id) {
        if (optionBusiness.deleteOption(id)) {
            return Response.status(204).build();
        }
        return Response.status(404).build();
    }

    // A5 : modification
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOption(@PathParam("id") int id, Option op) {
        op.setCodeOption(id);   // le chemin fait foi
        if (optionBusiness.updateOption(id, op)) {
            return Response.status(200).build();
        }
        return Response.status(404).build();
    }
}