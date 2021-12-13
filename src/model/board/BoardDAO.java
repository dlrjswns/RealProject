package model.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;

public class BoardDAO {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	String sql_insert=""; // 로그인에 성공한 경우에만 C 진행
	String sql_update="update board set favcnt=favcnt+1 where bid=?"; // 좋아요+1
	String sql_delete=""; // 해당 게시글의 작성자만이 삭제가능
	String sql_selectAll="select * from board order by bid desc"; // "더보기": pagination(페이지네이션)
		// oracle에서 limit사용하는 sql문
	
	String sql_insertR="";
	String sql_deleteR="";
	
	public ArrayList<BoardSet> selectAll(int mcnt){ // 몇개의 글을 볼수있는지에 대한 정보를 받아옴
		ArrayList<BoardSet> datas=new ArrayList<BoardSet>();
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(sql_selectAll);
			//pstmt.setInt(1, mcnt);
			System.out.println("mcnt: "+mcnt);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				BoardSet bs=new BoardSet();
				BoardVO b=new BoardVO();
				ArrayList<ReplyVO> rdatas=new ArrayList<ReplyVO>();
				
				b.setBid(rs.getInt("bid"));
				b.setFavcnt(rs.getInt("favcnt"));
				b.setMid(rs.getString("mid"));
				b.setMsg(rs.getString("msg"));
				
				String sql="select * from reply where bid=? order by rid desc";
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, b.getBid()); // rs.getInt("bid")
				ResultSet rs2=pstmt.executeQuery();
				while(rs2.next()) {
					ReplyVO r=new ReplyVO();
					r.setBid(rs2.getInt("bid"));
					r.setMid(rs2.getString("mid"));
					r.setRid(rs2.getInt("rid"));
					r.setRmsg(rs2.getString("rmsg"));
					rdatas.add(r);
				}
				b.setRcnt(rdatas.size()); // 게시글에 대한 댓글의 개수
				
				bs.setBoard(b);
				bs.setRdatas(rdatas);
				datas.add(bs);
			}
		} catch (SQLException e) {
			System.out.println("BoardDAO selectAll()에서 문제발생!");
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}		
		return datas;
	}
	public boolean update(BoardVO vo) {
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(sql_update);
			pstmt.setInt(1, vo.getBid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("BoardDAO update()에서 문제발생!");
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true;
	}
}
