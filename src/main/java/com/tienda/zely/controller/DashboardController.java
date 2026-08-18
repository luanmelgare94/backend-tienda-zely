package com.tienda.zely.controller;

import static com.tienda.zely.util.Constants.APPLICATION_JSON_UTF8_VALUE;

import com.tienda.zely.dto.dashboard.DashboardDefaultDto;
import com.tienda.zely.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping(path = "/statistics", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DashboardDefaultDto> getStatisticsForDashboard() {
        log.info("Consultando estadisticas del dashboard");
        return ResponseEntity.ok(dashboardService.getStatistics());
    }
}
