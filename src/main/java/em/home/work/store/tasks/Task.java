package em.home.work.store.tasks;

import em.home.work.store.comments.Comment;
import em.home.work.store.base.AbstractBaseEntity;
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
    String creator; // id пользователя создавшего задачу
    @Column()
    String status; // todo enum сделать
    @Column()
    String description;
    @Column()
    String priority;// todo enum сделать
    @Column()
    String contractor; // todo как брать и как обрабатывать ?

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "taskId", referencedColumnName = "id")
    private List<Comment> comments;

    public Task(String description, String contractor, String creator, String priority) {
        this.description = description;
        this.contractor = contractor;
        this.creator = creator;
        this.priority = priority;
    }
}
