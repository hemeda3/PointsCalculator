package com.mapledoum.pointscalc.controller;

import com.mapledoum.pointscalc.model.ApiResponseDTO;
import com.mapledoum.pointscalc.repository.CustomerRepository;
import com.mapledoum.pointscalc.service.IPointsService;
import com.mapledoum.pointscalc.utils.PointsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/customers")
public class CustomerController {


    IPointsService iPointsService;


    CustomerRepository customerRepository;



    PointsUtils pointsUtils;


    @Autowired
    public CustomerController(IPointsService iPointsService, CustomerRepository customerRepository, PointsUtils pointsUtils) {
        this.iPointsService = iPointsService;
        this.customerRepository = customerRepository;
        this.pointsUtils = pointsUtils;
    }

    @GetMapping(value = "/{id}/points", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/{id}/points")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = ApiResponseDTO.class),
            @ApiResponse(code = 404, message = " Customer not found "),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 401, message = "Unauthorized "),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<ApiResponseDTO> calcPointsRange(
            @PathVariable("id") Long customerId,
            @RequestParam("from") String from,
            @RequestParam("to") String to) {

        ApiResponseDTO responseDTO =
                iPointsService.calcPointsTotalFromTo(customerId,
                        pointsUtils.convertStToDate(from), pointsUtils.convertStToDate(to));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @GetMapping(value = "/{id}/points/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/{id}/points/{date}")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = ApiResponseDTO.class),
            @ApiResponse(code = 404, message = " Customer not found "),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 401, message = "Unauthorized "),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<ApiResponseDTO> calcPointsByMonth(
            @PathVariable("id") Long customerId,
            @PathVariable("date") String date) {

        ApiResponseDTO responseDTO =
                iPointsService.calcPointsForMonth(customerId, pointsUtils.convertStToDate(date));


        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


}
