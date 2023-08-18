package com.goodee.mvcboard.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.mvcboard.mapper.BoardMapper;
import com.goodee.mvcboard.mapper.BoardfileMapper;
import com.goodee.mvcboard.vo.Board;
import com.goodee.mvcboard.vo.Boardfile;

@Transactional
@Service
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;
	@Autowired
	private BoardfileMapper boardfileMapper;
	
	// REST API 차트호출
	public List<Map<String, Object>> getLocalNamelist(){
		
		return boardMapper.selectLocalNameList();
	}
	
	// 게시글 입력
	public int addBoard(Board board, String path) {
		int row = boardMapper.insertBoard(board);
		
		// addboard 성공 및 첨부된 파일이 1개 이상 있다면
		List<MultipartFile> fileList = board.getMultipartFile();
		if(row == 1 && fileList != null && fileList.size() > 0) {
			int boardNo = board.getBoardNo();
		
			for(MultipartFile mf : fileList) { // 첨부 파일 개수만큼 반복
				if(mf.getSize() > 0) {
					Boardfile bf = new Boardfile();
					bf.setBoardNo(boardNo); // 부모키값
					bf.setOriginFilename(mf.getOriginalFilename()); // 파일원본이름
					bf.setFilesize(mf.getSize()); // 파일크기
					bf.setFiletype(mf.getContentType()); // 파일타입(MIME : Multipurpose Internet Mail Extensions = 파일변환타입)
					
					// 저장될 파일 이름
					// 확장자
					int lastIdx = mf.getOriginalFilename().lastIndexOf(".");
					String ext = mf.getOriginalFilename().substring(lastIdx); // 마지막 .의 위치값 > 확장자 ex) A.jpg 에서 자른다
					// 새로운 이름 + 확장자
					bf.setSaveFilename(UUID.randomUUID().toString().replace("-", "") + ext); 
					
					// 테이블에 저장
					boardfileMapper.insertBoardfile(bf);
					// 파일저장(저장위치필요 > path변수 필요)
					// path 위치에 저장파일이름으로 빈파일을 생성
					File f = new File(path+bf.getSaveFilename());
					// 빈파일에 첨부된 파일의 스트림을 주입한다.
					try {
						mf.transferTo(f);
					} catch(IllegalStateException | IOException e) {
						// 트랜잭션 작동을 위해 예외 발생이 필요
						e.printStackTrace();
						// 트랜잭션 작동을 위해 예외(try catch 강요하지 않는 예외 ex) RuntimeException) 발생 필요
						throw new RuntimeException();
					}
				}
			}
		}
		return row;
	}
	
	// 게시글 상세
	public Map<String, Object> getBoardOne(int boardNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("board", boardMapper.selectBoardOne(boardNo));
		map.put("boardFiles", boardfileMapper.selectBoardFile(boardNo));
		return map;
	}
	
	// 게시글 수정
	public int modifyBoard(Board board) {
		return boardMapper.modifyBoard(board);
	}
	// 게시글 삭제
	public int removeBoard(Board board, String path) {
		List<Boardfile> boardFile = boardfileMapper.selectBoardFile(board.getBoardNo());
		for(Boardfile bf : boardFile) {
			File f = new File(path+bf.getSaveFilename());
			if(f.exists()) {
				f.delete();
			}
		}
		int row = boardfileMapper.deleteBoardFile(board.getBoardNo());
		row = boardMapper.deleteBoard(board);
		return row;
	}
	
	// 리스트
	public Map<String,Object> getBoardList(int currentPage, int rowPerPage, String localName){
		
		// service layer의 역할1 : controller가 넘겨준 매개값을 dao의 매개값에 맞게 가공
		int beginRow = (currentPage - 1) * rowPerPage;
		// 반환값 1
		List<Map<String,Object>> localNameList = boardMapper.selectLocalNameList();
		
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("beginRow",beginRow);
		paramMap.put("rowPerPage",rowPerPage);
		paramMap.put("localName", localName);
		
		// 반환값 2
		List<Board> boardList = boardMapper.selectBoardListByPage(paramMap);
		
		int boardCount = boardMapper.selectBoardCount(); // 전체행
		// service layer 역할1 : dao에서 반환받은 값을 가공하여 controller에 반환
		int lastPage = boardCount / rowPerPage;
		if(boardCount % rowPerPage != 0) {
			lastPage += 1; // lastPage =lastPage +1; 
		}
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("localNameList", localNameList);
		resultMap.put("boardList", boardList);
		resultMap.put("currentPage", currentPage);
		resultMap.put("lastPage", lastPage);
		
		return resultMap;
	}
}
