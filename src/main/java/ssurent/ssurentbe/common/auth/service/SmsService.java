package ssurent.ssurentbe.common.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ssurent.ssurentbe.common.exception.GeneralException;
import ssurent.ssurentbe.common.status.ErrorStatus;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmsService {

    private final DefaultMessageService messageService;

    @Value("${nurigo.sender-number}")
    private String senderNumber;

    public void sendVerificationCode(String phoneNum, String code) {
        Message message = new Message();
        message.setFrom(senderNumber);
        message.setTo(phoneNum.replace("-", ""));
        message.setText("[SSURENT] 인증번호 [" + code + "]를 입력해주세요. (5분 이내 입력)");

        try {
            messageService.sendOne(new SingleMessageSendingRequest(message));
        } catch (Exception e) {
            log.error("[SMS] 발송 실패 - to: {}, cause: {}", phoneNum.replace("-", ""), e.getMessage(), e);
            throw new GeneralException(ErrorStatus.SMS_SEND_FAILED);
        }
    }
}
