package com.mapledoum.pointscalc.utils;

import com.mapledoum.pointscalc.service.IPointsService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PointsUtilsTest {


    PointsUtils pointsUtils;

    @BeforeEach
    public void init(){
         pointsUtils = new PointsUtils();
    }

    @Test
    public void testUtil(){
        LocalDate localDate = pointsUtils.convertStToDate("1999-10-10");
        assertEquals(localDate, LocalDate.of(1999,10,10));
    }

}
