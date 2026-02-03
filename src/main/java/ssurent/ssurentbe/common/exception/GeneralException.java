package ssurent.ssurentbe.common.exception;

import lombok.Getter;
import ssurent.ssurentbe.common.status.ErrorStatus;

@Getter
public class GeneralException extends RuntimeException {

    private final ErrorStatus status;

    public GeneralException(ErrorStatus status) {
        super(status.getMessage());
        this.status = status;
    }
}
