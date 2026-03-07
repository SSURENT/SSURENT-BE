package ssurent.ssurentbe.domain.users.entity;

import jakarta.persistence.*;
import lombok.*;
import ssurent.ssurentbe.common.base.BaseEntity;
import ssurent.ssurentbe.domain.users.enums.PenaltyTypes;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user_penalty_log")
public class UserPenaltyLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users userId;

    @Column(name = "item_name")
    private String itemName;

    @Enumerated(EnumType.STRING)
    @Column(name = "penalty_type")
    private PenaltyTypes penaltyType;
}