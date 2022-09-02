package performance.com.mapledoum.pointscalc.pointsapp;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import lombok.extern.slf4j.Slf4j;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;
import static performance.com.mapledoum.pointscalc.pointsapp.PerfTestConfig.*;

@Slf4j
public class PointsSimulation extends Simulation {


    HttpProtocolBuilder httpProtocol = http.baseUrl(BASE_URL)
            .header("Content-Type", "application/json")
            .check(status().is(200));


    ScenarioBuilder calcPointsBySingleMonth = scenario("PointsCalcBySingleMonth")
            .exec(http("PointsPerMonth")
                    .get("/customers/9999/points/1999-10-10")
                    .check(status().is(200))
                    .check(jsonPath("$.total").is("110").saveAs("total"))

            )
            .exec(session -> session);
    ScenarioBuilder calcPointsByRange = scenario("PointsCalcByRange")
            .exec(http("PointsPerMonth")
                    .get("/customers/9999/points?from=1999-10-10&to=2999-10-10")
                    .check(status().is(200))
                    .check(jsonPath("$.total").is("220").saveAs("totalForRange"))

            )
            .exec(session -> session);


    {


        setUp(
                calcPointsBySingleMonth.injectOpen(atOnceUsers(10))
                        .andThen(calcPointsByRange.injectOpen(atOnceUsers(10)))
        ).protocols(httpProtocol)
                .assertions(
                        // Assert that the max response time of all requests is less than 100 ms
                        global().responseTime().percentile3().lt(P95_RESPONSE_TIME_MS),
                        // Assert that every request has no more than 5% of failing requests
                        forAll().failedRequests().percent().lte(5.0),
                        //target the number of successful requests
                        global().successfulRequests().percent().gte(95.0));

    }
}
