package goraebob.diary.factory.entity;


import goraebob.diary.entity.member.Member;
import goraebob.diary.entity.message.Message;

import static goraebob.diary.factory.entity.MemberFactory.createMember;

public class MessageFactory {
    public static Message createMessage() {
        return new Message("content", createMember(), createMember());
    }

    public static Message createMessage(Member sender, Member receiver) {
        return new Message("content", sender, receiver);
    }
}