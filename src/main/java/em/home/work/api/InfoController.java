package em.home.work.api;

import em.home.work.model.Info;
import em.home.work.utils.EndPoint;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndPoint.INFO)
@RequiredArgsConstructor
@Tag(name = "Общая информация")
public class InfoController {
    @GetMapping
    @Operation(summary = "Доступно всем!")
    public ResponseEntity<Info> example() {
        Info info = new Info("Hello world !!!!");
        return ResponseEntity.ok(info);
    }
}
