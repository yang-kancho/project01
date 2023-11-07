package com.project.project00.service;

import com.project.project00.entity.BoardContent;
import com.project.project00.repository.BoardContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardContentService {

    @Autowired
    private BoardContentRepository boardContentRepository;

    public Page<BoardContent> boardViewByBoardid(Long id, Pageable pageable ){
        return boardContentRepository.findByBoardId(id,pageable);
    }

    public BoardContent boardById(Long id){
        return boardContentRepository.findById(id).get();
    }
    public void boardContentsave(BoardContent board){
        boardContentRepository.save(board);
    }
    public void boardContentCount(Long id){
        BoardContent board = boardContentRepository.findById(id).get();
        board.setCount(board.getCount()+1);
        boardContentRepository.save(board);
    }


    public void DeleteContent(Long id){
        boardContentRepository.deleteById(id);
    }

    public List<Long> selectContentIdByBoardId(Long boardId){
        return boardContentRepository.selectContentIdByBoardId(boardId);
    }




//    public BoardContent boardTableView(Long id){
//        return boardContentRepository.findById(id);
//    }
}
