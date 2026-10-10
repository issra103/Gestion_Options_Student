package ressourcesrest;

import entities.Etudiant;
import metiers.EtudiantBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("etudiants")
public class restEtudiant {

    public static EtudiantBusiness etudiantBusiness = new EtudiantBusiness();

    // B1 : création (404 si l'option n'existe pas)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ajoutEtudiant(Etudiant e) {
        if (e.getOption() == null
                || restOption.optionBusiness.getOptionByCode(e.getOption().getCodeOption()) == null) {
            return Response.status(404).build();   // option inexistante
        }
        if (etudiantBusiness.getEtudiantByIdentifiant(e.getIdentifiant()) != null) {
            return Response.status(400).build();   // identifiant déjà utilisé
        }
        etudiantBusiness.addEtudiant(e);
        return Response.status(200).build();
    }
}