package org.java4me.spring.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_chat")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersChat implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
//    @Column(unique = true, nullable = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
//    @Column(unique = true, nullable = false)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    public void setUser(User user) {
        this.user = user;
        user.getUserChats().add(this);
    }

    public void setChat(Chat chat) {
        this.chat = chat;
        chat.getUsersChats().add(this);
    }
}
