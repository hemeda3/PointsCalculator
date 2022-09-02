package performance.com.mapledoum.pointscalc.pointsapp;


import io.gatling.javaapi.core.Session;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

 import static performance.com.mapledoum.pointscalc.pointsapp.SystemPropertiesUtil.*;


@Slf4j
public final class PerfTestConfig {
    public static final String BASE_URL = getAsStringOrElse("baseUrl", "http://localhost:8080/");
     public static final int P95_RESPONSE_TIME_MS = getAsIntOrElse("p95ResponseTimeMs", 100000);


}
