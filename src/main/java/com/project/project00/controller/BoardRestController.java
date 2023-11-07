package com.project.project00.controller;

import com.project.project00.entity.BoardComment;
import com.project.project00.entity.BoardContent;
import com.project.project00.entity.BoardList;
import com.project.project00.entity.BoardVisitHistory;
import com.project.project00.service.BoardCommentService;
import com.project.project00.service.BoardContentService;
import com.project.project00.service.BoardListService;
import com.project.project00.service.BoardVisitHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BoardRestController {

    @Autowired
    private BoardContentService boardContentService;

    @Autowired
    private BoardCommentService boardCommentService;

    @Autowired
    private BoardListService boardListService;

    @Autowired
    private BoardVisitHistoryService boardVisitHistoryService;


    // 게시판 입장시 page를 보내주는 메서드
    @PostMapping("/boardTableView")
    public Page<BoardContent> boardTableView(@PageableDefault(page=0,size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,@RequestParam Long boardId, Integer pageNum){

        Page page = boardContentService.boardViewByBoardid(boardId,pageable);

        return page;
    }

    @GetMapping("/boardPageView")
    public Page<BoardContent> boardPageView(@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam Long boardId){
        Page page = boardContentService.boardViewByBoardid(boardId,pageable);

        return page;
    }


    @GetMapping("/readMore")
    public BoardContent boardContentDetail(@RequestParam("id") Long id){
        BoardContent board = boardContentService.boardById(id);
        boardContentService.boardContentCount(id);

        return board;
    }

    @PostMapping("/boardContentWrite")
    public void boardContentDetail(@RequestParam("boardContentTitle")String contentTitle,
                                   @RequestParam("boardContentContent")String content,
                                   @RequestParam("boardContentBoardId")Long boardId,
                                   @RequestParam("boardContentWriterId")Long writerId,
                                   @RequestParam("boardContentWriter")String writer
                                   ){
        BoardContent board = new BoardContent();
        board.setBoardId(boardId);
        board.setContent(content);
        board.setWriter(writer);
        board.setWriterid(writerId);
        board.setCount(0L);
        board.setTitle(contentTitle);
        boardContentService.boardContentsave(board);

    }
    @GetMapping("/boardComment")
    public List<BoardComment> boardCommentView(@RequestParam("id") Long contentId){
        List<BoardComment> list = boardCommentService.commentViewById(contentId);
        return list;
    }

    @PostMapping("/boardCommentWrite")
    public void boardCommentWrite(@RequestParam("id") Long contentId,
                                  @RequestParam("commentInput") String content,
                                  @RequestParam("writerId") Long userId,
                                  @RequestParam("writer") String userName){
        BoardComment comment = new BoardComment();
        comment.setWriter(userName);
        comment.setBoardContentId(contentId);
        comment.setWriterId(userId);
        comment.setCommentContent(content);


        boardCommentService.writeComment(comment);
    }

    @GetMapping("/boardCommentDelete")
    public void boardCommentDelete(@RequestParam("id") Long id){

        boardCommentService.DeleteComment(id);
    }

    @GetMapping("/boardContentLoad")
    public BoardContent boardContentLoad(@RequestParam("id") Long id){
        System.out.println(boardContentService.boardById(id));
        return boardContentService.boardById(id);

    }

    @PostMapping("/boardContentUpdate")
    public Long boardContentUpdate(@RequestParam("id") Long id,
                                   @RequestParam("boardContentTitle")String title,
                                   @RequestParam("boardContentContent")String content){
        BoardContent board = boardContentService.boardById(id);
        board.setContent(content);
        board.setTitle(title);
        boardContentService.boardContentsave(board);

        Long boardId = board.getBoardId();
        return boardId;
    }

    @GetMapping("/boardContentDelete")
    public void boardContentDelete(@RequestParam("id")Long id){
        boardContentService.DeleteContent(id);
    }

    @GetMapping("/findBoardListById")
    public BoardList findBoardListById(@RequestParam("id")Long id){
        BoardList board = boardListService.findById(id);
        return board;
    }

    @GetMapping("/board/join")
    public void boardListCountUp(@RequestParam("id")Long id,
                                 @RequestParam("userId")Long userId) {

        BoardList board = boardListService.findById(id);

        BoardVisitHistory history = new BoardVisitHistory();

        history.setBoardId(board.getId());
        history.setUserId(userId);
        history.setLat(board.getLat());
        history.setLng(board.getLng());
        history.setBoardTitle(board.getTitle());

        boardVisitHistoryService.userVisit(history);

        boardListService.boardCountUp(id);
    }

    @GetMapping("/myBoardVisitHistory")
    public List<BoardVisitHistory> visitHistoris(@RequestParam("userId") Long userId){

        List<BoardVisitHistory> history = boardVisitHistoryService.findByUserId(userId);

        return history;


    }

    @GetMapping("/boardDelete")
    public void boardDelete(@RequestParam("id")Long boardId){
        System.out.println(boardId);

        if(boardContentService.selectContentIdByBoardId(boardId) != null){
            List<Long> list = boardContentService.selectContentIdByBoardId(boardId);
            for(Long i : list){
                boardCommentService.deleteByContentId(i);
                boardContentService.DeleteContent(i);
            }
        }
        boardVisitHistoryService.deleteByBoardId(boardId);

        boardListService.deleteBoard(boardId);
    }
    @GetMapping("/boardPasswordCheck")
    public String boardPasswordCheck(@RequestParam("boardId")Long boardId,
                                     @RequestParam("userId")Long userId){
        if(boardVisitHistoryService.secretBoardHistoryCheck(boardId, userId) > 0)return "-1";
        else return boardListService.passwordCheck(boardId);
    }
}
