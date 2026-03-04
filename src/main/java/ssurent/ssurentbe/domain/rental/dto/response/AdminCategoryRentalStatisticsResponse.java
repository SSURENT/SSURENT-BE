package ssurent.ssurentbe.domain.rental.dto.response;

public record AdminCategoryRentalStatisticsResponse(
        Long categoryId,
        String categoryName,
        Long rentalCount) {
}
