package au.cogito.collab.controllers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pavankumarjoshi on 31/05/2017.
 */
public abstract class ApiController {
    private static final String API_PATH = "/api/v1";


    public static final String AUTHENTICATE_URL = "/login";
    public static final String STUFF_URL = API_PATH + "/stuff";

    // Spring Boot Actuator services
    public static final String AUTOCONFIG_ENDPOINT = "/autoconfig";
    public static final String BEANS_ENDPOINT = "/beans";
    public static final String CONFIGPROPS_ENDPOINT = "/configprops";
    public static final String ENV_ENDPOINT = "/env";
    public static final String MAPPINGS_ENDPOINT = "/mappings";
    public static final String METRICS_ENDPOINT = "/metrics";
    public static final String SHUTDOWN_ENDPOINT = "/shutdown";
}