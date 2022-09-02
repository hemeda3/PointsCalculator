### Assignment to calculate points of rewards



##### To run the performance testing: execute 
- `mvn gatling:test -Dgatling.simulationClass=performance.com.mapledoum.pointscalc.pointsapp.PointsSimulation`

##### Then Go to this path open Gatling results ( the result file complete path already shown after the simulation finish)
- ```target/gatling/pointssimulation-***```

#### curl calcPointsPerMonth
- ```curl --location --request GET 'http://localhost:8080/customers/9999/points/1999-10-10' ```

#### curl calcPointsMonthRange
- ```curl --location --request GET 'http://localhost:8080/customers/9999/points?from=1999-10-10&to=2999-10-10' ```

##### Swagger UI:
http://localhost:8080/swagger-ui/index.html


