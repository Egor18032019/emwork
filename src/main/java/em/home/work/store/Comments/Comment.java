package em.home.work.store.Comments;

import em.home.work.store.entity.AbstractBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment extends AbstractBaseEntity {
    @Column()
    String author;
    @Column()
    String text;

    @Column()
    private Long taskId;
}
