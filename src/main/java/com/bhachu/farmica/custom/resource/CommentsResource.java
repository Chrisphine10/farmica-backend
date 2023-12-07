package com.bhachu.farmica.custom.resource;

import com.bhachu.farmica.custom.service.CommentsService;
import com.bhachu.farmica.domain.Comment;
import com.bhachu.farmica.domain.enumeration.CurrentZone;
import com.bhachu.farmica.repository.CommentRepository;
import com.bhachu.farmica.service.CommentService;
import com.bhachu.farmica.web.rest.CommentResource;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CommentsResource extends CommentResource {

    public CommentsResource(CommentService commentService, CommentRepository commentRepository) {
        super(commentService, commentRepository);
    }

    private final Logger log = LoggerFactory.getLogger(CommentsResource.class);

    @Autowired
    private CommentsService commentsService;

    @GetMapping("/comments/{zoneId}/{status}")
    public ResponseEntity<List<Comment>> findAllByZoneIdAndStatus(@PathVariable Integer zoneId, @PathVariable CurrentZone status) {
        log.debug("REST request to get all Comments by Zone: {}", zoneId);
        return new ResponseEntity<>(commentsService.findAllByZoneIdAndStatus(zoneId, status), HttpStatus.OK);
    }
}
