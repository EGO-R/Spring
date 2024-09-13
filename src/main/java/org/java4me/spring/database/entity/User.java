package org.java4me.spring.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(name = "User.company",
        attributeNodes = @NamedAttributeNode("company"))
@Entity
@Table(name = "users")
@Data
@ToString(exclude = {"userChats"})
@EqualsAndHashCode(of = "username")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class User extends AuditingEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;
    private LocalDate birthDate;
    private String firstname;
    private String lastname;
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    private String image;

    private String password;

    @NotAudited
    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<UsersChat> userChats = new ArrayList<>();
}
