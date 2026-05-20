package ssurent.ssurentbe.domain.item.entity;

import jakarta.persistence.*;
import lombok.*;
import ssurent.ssurentbe.common.base.BaseEntity;
import ssurent.ssurentbe.domain.item.enums.Condition;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "item_status_log")
public class ItemStatusLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", nullable = false)
    private String itemName;        // "우산(101)"

    @Column(name = "changed_by_info", length = 28, nullable = false)
    private String changedByInfo;   // "홍길동(20201234)"

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_status", nullable = false)
    private Condition prevStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false)
    private Condition newStatus;
}