package em.home.work.store.tasks;

import em.home.work.store.Comments.Comment;
import em.home.work.store.entity.AbstractBaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task extends AbstractBaseEntity {
    @Column()
    String status; // todo enum сделать
    @Column()
    String description;
    @Column()
    String contractor; // todo как брать и как обрабатывать ?

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "taskId", referencedColumnName = "id")
    private List<Comment> comments;

    public Task(String status, String description, String contractor, List<Comment> comments) {
        this.status = status;
        this.description = description;
        this.contractor = contractor;
        this.comments = comments;
    }

    public Task(Long id, String status, String description, String contractor, List<Comment> comments) {
        super(id);
        this.status = status;
        this.description = description;
        this.contractor = contractor;
        this.comments = comments;
    }

    public Task(String status, String description, String contractor) {
        this.status = status;
        this.description = description;
        this.contractor = contractor;
    }
}
