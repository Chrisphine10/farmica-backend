package com.bhachu.farmica.custom.service;

import com.bhachu.farmica.custom.repository.CommentsRepo;
import com.bhachu.farmica.domain.Comment;
import com.bhachu.farmica.domain.enumeration.CurrentZone;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentsService {

    @Autowired
    private CommentsRepo commentsRepo;

    public CommentsService() {}

    public List<Comment> findAllByZoneIdAndStatus(Integer zoneId, CurrentZone status) {
        return commentsRepo.findAllByZoneIdAndStatus(zoneId, status);
    }
}
