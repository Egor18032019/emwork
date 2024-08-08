package em.home.work.api;

import em.home.work.model.CommentIdResponse;
import em.home.work.model.CommentRequest;
import em.home.work.service.CommentServiceCommon;
import em.home.work.utils.EndPoint;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(EndPoint.COMMENT)
@Tag(name = "Управление комментариями")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentController {
    CommentServiceCommon commentService;

    @PostMapping(EndPoint.great)
    @Operation(summary = "создавать новый комментарий")
    public ResponseEntity<CommentIdResponse> greatComment(@RequestBody CommentRequest request) {
        CommentIdResponse response = commentService.greatComment(request);
        return ResponseEntity.ok(response);
    }
}
