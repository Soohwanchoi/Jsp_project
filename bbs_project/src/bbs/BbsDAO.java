package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BbsDAO {
	
	private Connection conn;
	private ResultSet rs;
	
	//db연결
	public BbsDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/BBS?characterEncoding=UTF-8&serverTimezone=UTC";
			String dbID = "root";
			String dbPassword = "a48094809";
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// end of public UserDAO
	
	public String getDate() {
		String SQL = "select now()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	//인덱스증가
	public int getNext() {
		String SQL = "select bbsID from bbs order by bbsID desc";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) + 1;
			}
			return 1;
		} catch (Exception e){
			e.printStackTrace();
		}
		return -1;
	}
	
	//글쓰기
	public int write(String bbsTitle, String userID , String bbsContent) {
		String SQL = "INSERT INTO BBS VALUES (?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, bbsTitle);
			pstmt.setString(3, userID);
			pstmt.setString(4, getDate());
			pstmt.setString(5, bbsContent);
			pstmt.setInt(6, 1);
			return pstmt.executeUpdate();
		} catch (Exception e){
			e.printStackTrace();
		}
		return -1;
	}
	
	//게시글 목록 처리
	public ArrayList<BbsVO> getList(int pageNumber){
		String SQL = "select * from bbs where bbsID < ? and bbsAvailable = 1 order by bbsID desc limit 10";
		ArrayList<BbsVO> list = new ArrayList<BbsVO>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,  getNext() - (pageNumber - 1 ) * 10);
			rs = pstmt.executeQuery();
			
			//date 포맷
			String pattern = "yy년 MM월 dd일";
			SimpleDateFormat SDF = new SimpleDateFormat(pattern);
			
			while (rs.next()) {
				BbsVO bbsVO = new BbsVO();
				bbsVO.setBbsID(rs.getInt(1));
				bbsVO.setBbsTitle(rs.getNString(2));
				bbsVO.setUserID(rs.getNString(3));
				bbsVO.setBbsDate(SDF.format(rs.getDate(4)));
				bbsVO.setBbsContent(rs.getNString(5));
				bbsVO.setBbsAvailable(rs.getInt(6));
				list.add(bbsVO);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	//페이징처리
	public boolean nextPage(int pageNumber) {
		String SQL = "select * from bbs where bbsID < ? and bbsAvailable = 1";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,  getNext() - (pageNumber - 1 ) * 10);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	//게시글 보기
	public BbsVO getBbs(int bbsID) {
		String SQL = "select * from bbs where bbsID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			rs = pstmt.executeQuery();
			
			String pattern = "yy년 MM월 dd일";
			SimpleDateFormat SDF = new SimpleDateFormat(pattern);
			
			if (rs.next()) {
				BbsVO bbsVO = new BbsVO();
				bbsVO.setBbsID(rs.getInt(1));
				bbsVO.setBbsTitle(rs.getNString(2));
				bbsVO.setUserID(rs.getNString(3));
				bbsVO.setBbsDate(SDF.format(rs.getDate(4)));
				bbsVO.setBbsContent(rs.getNString(5));
				bbsVO.setBbsAvailable(rs.getInt(6));
				return bbsVO;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	//게시글 수정
	public int update(int bbsID, String bbsTitle, String bbsContent) {
		String SQL = "update bbs set bbsTitle = ?, bbsContent = ? where bbsID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, bbsTitle);
			pstmt.setString(2, bbsContent);
			pstmt.setInt(3, bbsID);
			return pstmt.executeUpdate();
		} catch (Exception e){
			e.printStackTrace();
		}
		return -1;
	}
	
	//글삭제
	public int delete(int bbsID) {
		String SQL = "update bbs set bbsAvailable where bbsID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			return pstmt.executeUpdate();
		} catch (Exception e){
			e.printStackTrace();
		}
		return -1;
	}
}
