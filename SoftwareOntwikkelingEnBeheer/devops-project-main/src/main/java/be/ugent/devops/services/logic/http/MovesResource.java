package be.ugent.devops.services.logic.http;

import be.ugent.devops.services.logic.api.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/moves")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MovesResource {

    @Inject
    FactionLogic factionLogic;

    @Path("base")
    @POST
    public BaseMove notifyBaseMove(BaseMoveInput body) {
        return factionLogic.nextBaseMove(body);
    }

    @Path("unit")
    @POST
    public UnitMove notifyUnitMove(UnitMoveInput body) {
        return factionLogic.nextUnitMove(body);
    }

}
