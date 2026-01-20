package ssurent.ssurentbe.domain.item.entity;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import ssurent.ssurentbe.common.base.BaseEntity;
import ssurent.ssurentbe.domain.item.enums.Status;
import ssurent.ssurentbe.domain.users.entity.Users;

public class itemStatusLog extends BaseEntity {
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Items itemsId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by")
    private Users changedBy;

    @Column(name = "privious_status")
    private Status prevStatus;

    @Column(name = "new_status")
    private Status newStatus;
}