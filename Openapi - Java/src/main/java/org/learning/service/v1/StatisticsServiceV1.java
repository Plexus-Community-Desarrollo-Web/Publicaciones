package org.learning.service.v1;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Slf4j
@Service
public class StatisticsServiceV1 {

    public StatisticsData getStatistics(LocalDate from, LocalDate to) {
        log.info("Getting statistics from {} to {}", from, to);

        StatisticsData stats = new StatisticsData();
        stats.setTotalBooks(150);
        stats.setTotalMembers(75);
        stats.setActiveLoans(23);
        stats.setOverdueLoans(5);
        stats.setTotalLoansInPeriod(89);
        stats.setLoansByCategory(Map.of(
                "FICTION", 45,
                "SCIENCE", 20,
                "HISTORY", 15,
                "TECHNOLOGY", 9
        ));

        return stats;
    }

    @Data
    public static class StatisticsData {
        private Integer totalBooks;
        private Integer totalMembers;
        private Integer activeLoans;
        private Integer overdueLoans;
        private Integer totalLoansInPeriod;
        private Map<String, Integer> loansByCategory;
    }
}