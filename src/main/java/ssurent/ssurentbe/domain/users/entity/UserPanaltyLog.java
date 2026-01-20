package ssurent.ssurentbe.domain.users.entity;

import jakarta.persistence.*;
import lombok.*;
import ssurent.ssurentbe.common.base.BaseEntity;
import ssurent.ssurentbe.domain.item.entity.Items;
import ssurent.ssurentbe.domain.rental.entity.RentalHistory;
import ssurent.ssurentbe.domain.users.enums.PanaltyTypes;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user_panalty_log")
public class UserPanaltyLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "panalty_id")
    private Panalty panaltyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "items_id")
    private Items itemsId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id")
    private RentalHistory rentalHistoryId;

    @Column(name = "panalty_type")
    private PanaltyTypes panaltyType;

}