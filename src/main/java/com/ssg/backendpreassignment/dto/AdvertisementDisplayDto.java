package com.ssg.backendpreassignment.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AdvertisementDisplayDto {
    private Long bidId;
    private Long companyId;
    private Long productId;
    private Long bidPrice;

    @Builder
    public AdvertisementDisplayDto(Long bidId, Long companyId, Long productId, Long bidPrice) {
        this.bidId = bidId;
        this.companyId = companyId;
        this.productId = productId;
        this.bidPrice = bidPrice;
    }
}
