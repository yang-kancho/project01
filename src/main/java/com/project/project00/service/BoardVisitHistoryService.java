package com.project.project00.service;

import com.project.project00.entity.BoardVisitHistory;
import com.project.project00.repository.BoardVisitHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardVisitHistoryService {

    @Autowired
    private BoardVisitHistoryRepository boardVisitHistoryRepository;


    public void userVisit(BoardVisitHistory history){
        boardVisitHistoryRepository.save(history);
    }

    public List<BoardVisitHistory> findByUserId(Long userId){
        return boardVisitHistoryRepository.findByUserId(userId);
    }

    public void deleteByBoardId(Long boardId){
        boardVisitHistoryRepository.deleteByBoardId(boardId);
    }

    public int secretBoardHistoryCheck(Long boardId, Long userId){
        return boardVisitHistoryRepository.secretHistoryCheck(boardId, userId);
    }
}
