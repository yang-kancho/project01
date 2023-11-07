package com.project.project00.service;

import com.project.project00.entity.BoardComment;
import com.project.project00.repository.BoardCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardCommentService {

    @Autowired
    private BoardCommentRepository boardCommentRepository;

    public List<BoardComment> commentViewById(Long id){
        return boardCommentRepository.findByBoardContentIdOrderByIdDesc(id);
    }

    public void writeComment(BoardComment comment){
        boardCommentRepository.save(comment);
    }

    public void DeleteComment(Long id){
        boardCommentRepository.deleteById(id);
    }

    public void deleteByContentId(Long contentId){
        boardCommentRepository.deleteByContentId(contentId);
    }
}