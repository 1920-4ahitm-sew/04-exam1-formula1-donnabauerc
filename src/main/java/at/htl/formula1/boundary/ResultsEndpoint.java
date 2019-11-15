package at.htl.formula1.boundary;

import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("results")
public class ResultsEndpoint {


    @PersistenceContext
    EntityManager em;

    /**
     * @param name als QueryParam einzulesen
     * @return JsonObject
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getPointsSumOfDriver(@QueryParam("name") String name) {
        return null;
    }

    /**
     * @param id des Rennens
     * @return
     */
    @GET
    public Response findWinnerOfRace(long id) {

        return null;
    }


    // Ergänzen Sie Ihre eigenen Methoden ...

}
