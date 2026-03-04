package ssurent.ssurentbe.domain.rental.dto.response;

public record AdminItemRentalStatisticsResponse(
        Long itemId,
        String itemName,
        String itemNum,
        Long rentalCount
) {
}
