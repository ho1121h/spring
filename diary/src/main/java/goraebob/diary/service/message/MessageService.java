package goraebob.diary.service.message;

import goraebob.diary.dto.message.MessageCreateRequest;
import goraebob.diary.dto.message.MessageDto;
import goraebob.diary.dto.message.MessageListDto;
import goraebob.diary.dto.message.MessageReadCondition;
import goraebob.diary.entity.member.Member;
import goraebob.diary.entity.message.Message;
import goraebob.diary.exception.MessageNotFoundException;
import goraebob.diary.repository.member.MemberRepository;
import goraebob.diary.repository.message.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;

    public MessageListDto readAllBySender(MessageReadCondition cond) { // 1
        return MessageListDto.toDto(
                messageRepository.findAllBySenderIdOrderByMessageIdDesc(cond.getMemberId(), cond.getLastMessageId(), Pageable.ofSize(cond.getSize()))
        );
    }

    public MessageListDto readAllByReceiver(MessageReadCondition cond) { // 1
        return MessageListDto.toDto(
                messageRepository.findAllByReceiverIdOrderByMessageIdDesc(cond.getMemberId(), cond.getLastMessageId(), Pageable.ofSize(cond.getSize()))
        );
    }

    public MessageDto read(Long id) { // 2
        return MessageDto.toDto(
                messageRepository.findWithSenderAndReceiverById(id).orElseThrow(MessageNotFoundException::new)
        );
    }

    @Transactional
    public void create(MessageCreateRequest req) { // 3
        messageRepository.save(MessageCreateRequest.toEntity(req, memberRepository));
    }

    @Transactional
    @PreAuthorize("@messageSenderGuard.check(#id)")
    public void deleteBySender(Long id) { // 4
        delete(id, Message::deleteBySender);
    }

    @Transactional
    @PreAuthorize("@messageReceiverGuard.check(#id)")
    public void deleteByReceiver(Long id) { // 4
        delete(id, Message::deleteByReceiver);
    }

    private void delete(Long id, Consumer<Message> delete) { // 5
        Message message = messageRepository.findById(id).orElseThrow(MessageNotFoundException::new);
        delete.accept(message);
        if(message.isDeletable()) {
            messageRepository.delete(message);
        }
    }
}