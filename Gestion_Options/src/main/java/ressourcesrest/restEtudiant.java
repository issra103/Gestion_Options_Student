package ressourcesrest;

import entities.Etudiant;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;
import entities.EtudiantList;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/etudiants")
public class restEtudiant {

    EtudiantBusiness eb = new EtudiantBusiness();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ajouterEtudiant(Etudiant etudiant) {

        if (eb.addEtudiant(etudiant)) {
            return Response.status(200).build();
        }

        return Response.status(404).build();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEtudiants() {

        return Response.status(200)
                .entity(eb.getAllEtudiants())
                .build();
    }
    @GET
    @Path("/{identifiant}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantByIdentifiant(
            @PathParam("identifiant") String identifiant) {

        Etudiant etudiant = eb.getEtudiantByIdentifiant(identifiant);

        if (etudiant != null) {
            return Response.status(200).entity(etudiant).build();
        }

        return Response.status(404).build();
    }
    @DELETE
    @Path("/{identifiant}")
    public Response supprimerEtudiant(
            @PathParam("identifiant") String identifiant) {

        if (eb.deleteEtudiant(identifiant)) {
            return Response.status(204).build();
        }

        return Response.status(404).build();
    }
    @PUT
    @Path("/{identifiant}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifierEtudiant(
            @PathParam("identifiant") String identifiant,
            Etudiant etudiant) {

        if (eb.updateEtudiant(identifiant, etudiant)) {
            return Response.status(200).build();
        }

        return Response.status(404).build();
    }
    @GET
    @Path("/option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(
            @QueryParam("codeOption") int codeOption) {

        OptionBusiness ob = new OptionBusiness();

        Option option = ob.getOptionByCode(codeOption);

        if (option != null) {

            EtudiantList liste = new EtudiantList(
                    eb.getEtudiantsByOption(option)
            );

            return Response.status(200)
                    .entity(liste)
                    .build();
        }
        return Response.status(404).build();
    }
}