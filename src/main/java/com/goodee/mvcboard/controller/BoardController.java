package com.goodee.mvcboard.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.goodee.mvcboard.service.BoardService;
import com.goodee.mvcboard.vo.Board;
import com.goodee.mvcboard.vo.Boardfile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	// 게시물 입력
	@GetMapping("/board/addBoard")
	public String addBoard() {
		return "/board/addBoard";
	}
	@PostMapping("/board/addBoard")	
	public String addBoard(HttpServletRequest request, Board board) { // 매개값으로 request 객체를 받는다 > request API를 직접 호출하기위해.
		String path = request.getServletContext().getRealPath("/upload/");
		int row = boardService.addBoard(board, path);
		log.debug("\u001B[31m"+ row + "입력 row" + "\u001B[0m");	
		return "redirect:/board/boardList";
	}
	
	// 게시물 상세
	@GetMapping("/board/boardOne")
	public String getBoardOne(Model model, 
							@RequestParam(name = "boardNo") int boardNo) {
		Map<String, Object> map = boardService.getBoardOne(boardNo);
		
		Board board = (Board)map.get("board");
		List<Boardfile> boardFiles = (List<Boardfile>) map.get("boardFiles");
		
		model.addAttribute("board", board);
		model.addAttribute("boardFile", boardFiles);
		
		return "/board/boardOne";
	}
	
	// 게시물 수정
	@GetMapping("/board/modifyBoard")
	public String modifyBoard(Model model, 
								@RequestParam(name = "boardNo") int boardNo) {
		Map<String, Object> map = boardService.getBoardOne(boardNo);
		
		Board board = (Board)map.get("board");
		List<Boardfile> boardFiles = (List<Boardfile>) map.get("boardFiles");
		
		model.addAttribute("board", board);
		model.addAttribute("boardFile", boardFiles);
		
		
		return "/board/modifyBoard";
	}
	@PostMapping("/board/modifyBoard")
	public String modifyBoard(@RequestParam(name="boardNo") int boardNo,
								@RequestParam(name="localName") String localName,
								@RequestParam(name="boardTitle") String boardTitle,
								@RequestParam(name="boardContent") String boardContent,
								@RequestParam(name="memberId") String memberId) {
		
		Board board = new Board();
		board.setBoardNo(boardNo);
		board.setLocalName(localName);
		board.setBoardTitle(boardTitle);
		board.setBoardContent(boardContent);
		board.setMemberId(memberId);
		
		int row = boardService.modifyBoard(board);
		log.debug("\u001B[31m"+ row + "수정 row" + "\u001B[0m");	
		return "redirect:/board/boardOne?boardNo="+boardNo;
	}
	
	// 게시물 삭제
	@GetMapping("/board/removeBoard")
	public String removeBoard(Model model,
								@RequestParam(name = "boardNo") int boardNo) {
		Map<String, Object> map = boardService.getBoardOne(boardNo);
		
		Board board = (Board)map.get("board");
		List<Boardfile> boardFiles = (List<Boardfile>) map.get("boardFiles");
		System.out.println(boardFiles.size());
		
		model.addAttribute("board", board);
		model.addAttribute("boardFile", boardFiles);
		
		return "/board/removeBoard";
	}
	
	@PostMapping("/board/removeBoard")
	public String removeBoard(HttpServletRequest request, 
								@RequestParam(name = "boardNo") int boardNo,
								@RequestParam(name = "memberId") String memberId) {
		
		String path = request.getServletContext().getRealPath("/upload/");
		Board board = new Board();
		board.setBoardNo(boardNo);
		board.setMemberId(memberId);
		
		int row = boardService.removeBoard(board, path);
		return "redirect:/board/boardList";
	}
	
	// 게시판 리스트
	@GetMapping("/board/boardList")
	public String boardList(Model model, 
							@RequestParam(name = "currentPage", defaultValue = "1") int currentPage, 
							@RequestParam(name = "rowPerPage", defaultValue = "10") int rowPerPage,
							@RequestParam(name = "localName", required = false) String localName) {
		
		log.debug("\u001B[31m"+ localName + " : localName" + "\u001B[0m");
		
		Map<String,Object> resultMap = boardService.getBoardList(currentPage, rowPerPage, localName);
		
		// view로 넘길때는 다시 분리해서
		model.addAttribute("localNameList", resultMap.get("localNameList"));
		model.addAttribute("boardList", resultMap.get("boardList"));
		
		model.addAttribute("currentPage", resultMap.get("currentPage"));
		model.addAttribute("lastPage", resultMap.get("lastPage"));
		
		return "/board/boardList";
	}
}
