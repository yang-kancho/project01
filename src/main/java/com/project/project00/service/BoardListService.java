package com.project.project00.service;


import com.project.project00.entity.BoardList;
import com.project.project00.repository.BoardListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardListService {

    @Autowired
    private BoardListRepository boardListRepository;




    public List<BoardList> boardListAll(){
        return boardListRepository.findAll();
    }

    public void createBoard(BoardList board){
        boardListRepository.save(board);
    }


    public int boardCountUp(Long id) {
        return boardListRepository.updateCount(id);
    }

    public BoardList findById(Long id){
        return boardListRepository.findById(id).get();
    }

    public String findTitleByid(Long id){
        return boardListRepository.findTitleByid(id);
    }


    public void deleteBoard(Long id){
        boardListRepository.deleteById(id);
    }


    public String passwordCheck(Long boardId){
        return boardListRepository.passwordCheck(boardId);
    }



}
