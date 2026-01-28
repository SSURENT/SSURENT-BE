package ssurent.ssurentbe.common.exception;

import lombok.Getter;
import ssurent.ssurentbe.common.base.BaseStatus;

@Getter
public class GeneralException extends RuntimeException {

    private final BaseStatus status;

    public GeneralException(BaseStatus status) {
        super(status.getMessage());
        this.status = status;
    }
}
