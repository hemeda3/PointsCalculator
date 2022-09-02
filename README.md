### Assignment to calculate points of rewards


##### To run the app:
- ``` ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev```
##### Swagger UI:
- http://localhost:8080/swagger-ui/index.html

#### Test Data
<pre>
|customerid  | transaction date |
 ------------  ----------------
| 9999       | 1999-10-10       |
| 9999       | 2999-10-10       |
| 8888       | 1999-10-10       |
| 8888       | 2999-10-10       |
</pre>

##### To run the unit+integration test testing: execute 
- ` ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev `

##### To run the performance (Gatling) testing: execute 
- `  ./mvnw  gatling:test -Dgatling.simulationClass=performance.com.mapledoum.pointscalc.pointsapp.PointsSimulation -Dmaven.test.skip=true `

##### To open Gatling nice UI results ( the result file complete path already shown after the simulation finish)
- ```target/gatling/pointssimulation-***```

#### curl calcPointsPerMonth
- ```curl --location --request GET 'http://localhost:8080/customers/9999/points/1999-10-10' ```

#### curl calcPointsMonthRange
- ```curl --location --request GET 'http://localhost:8080/customers/9999/points?from=1999-10-10&to=2999-10-10' ```


![Gatling Results](screenshots/gatlingResults.png?raw=true "Title")




