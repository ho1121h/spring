package goraebob.diary.entity.message;

import org.junit.jupiter.api.Test;

import static goraebob.diary.factory.entity.MessageFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MessageTest {
    @Test
    void deleteBySenderTest() {
        // given
        Message message = createMessage();

        // when
        message.deleteBySender();

        // then
        assertThat(message.isDeletedBySender()).isTrue();
    }

    @Test
    void deleteByReceiverTest() {
        // given
        Message message = createMessage();

        // when
        message.deleteByReceiver();

        // then
        assertThat(message.isDeletedByReceiver()).isTrue();
    }

    @Test
    void isNotDeletableTest() {
        // given
        Message message = createMessage();

        // when
        boolean deletable = message.isDeletable();

        // then
        assertThat(deletable).isFalse();
    }

    @Test
    void isDeletableTest() {
        // given
        Message message = createMessage();
        message.deleteBySender();
        message.deleteByReceiver();

        // when
        boolean deletable = message.isDeletable();

        // then
        assertThat(deletable).isTrue();
    }

}