package em.home.work.service;

import em.home.work.model.CommentIdResponse;
import em.home.work.model.CommentRequest;
import em.home.work.store.comments.Comment;
import em.home.work.store.comments.CommentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Comment Service", description = "Управление комментариями.")
public class CommentService { //todo сделать интерфейс
    CommentRepository commentRepository;
    UserService userService;

    @Operation(summary = "Создание комментария")
    public CommentIdResponse greatComment(CommentRequest request) {
        Comment comment = new Comment(userService.getCurrentUser().getUsername(), request.getText(), request.getTaskId());
        Comment commentAfterSave = commentRepository.save(comment);
        return new CommentIdResponse(commentAfterSave.getId());
    }

}
