package com.goodee.mvcboard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.goodee.mvcboard.vo.Board;

@Mapper
public interface BoardMapper {
	// 게시글입력
	int insertBoard(Board board);
	
	// 게시글 상세
	Board selectBoardOne(int boardNo);
	
	// 게시글 수정
	int modifyBoard(Board board);
	
	// 게시글삭제
	int deleteBoard(Board board);
	
	// local_name 컬럼과 count() 반환
	List<Map<String, Object>> selectLocalNameList();
	
	// mybatis 메서드는 매개값을 하나만 허용
	// param : Map<String, Object> map > int beginRow, int rowPerPage 
	List<Board> selectBoardListByPage(Map<String, Object> map); 
	
	// 전체 row 수
	int selectBoardCount();
}
