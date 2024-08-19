package em.home.work.store.tasks;

import em.home.work.store.comments.Comment;
import em.home.work.store.base.AbstractBaseEntity;
import em.home.work.utils.Priority;
import em.home.work.utils.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class Task extends AbstractBaseEntity {
    @Column()
    String creator;
    @Column()
    Status status;
    @Column()
    String description;
    @Column()
    Priority priority;
    @Column()
    String contractor; // todo как брать и как обрабатывать ?

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "taskId", referencedColumnName = "id")
    private List<Comment> comments;
}
