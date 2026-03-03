package ssurent.ssurentbe.domain.rental.dto.response;

public record AdminPeriodRentalStatisticsResponse(
        int year,
        int month,
        long rentalCount) {
}