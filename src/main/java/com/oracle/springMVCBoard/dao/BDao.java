package com.oracle.springMVCBoard.dao;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.oracle.springMVCBoard.dto.BDto;
import com.oracle.springMVCBoard.dto.BDto;

public class BDao {

	DataSource dataSource;

	public BDao() {
		// TODO Auto-generated constructor stub

		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/OracleDB");
		} catch (Exception e) {
			System.out.println("생성자 dataSource ->" + e.getMessage());
			e.printStackTrace();
		}
	}

	public ArrayList<BDto> list() {

		ArrayList<BDto> dtos = new ArrayList<BDto>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		
		try {
			connection = dataSource.getConnection();
			
			String query = "select bId, bName, bTitle, bContent, bDate, bHit,"
					+ " bGroup, bStep, bIndent "
					+ "from MVC_BOARD order by bGroup desc, bStep asc";
			
			preparedStatement = connection.prepareStatement(query);
			resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				
				int bId = resultSet.getInt("BID");
				String bName = resultSet.getString("BNAME");
				String bTitle = resultSet.getString("BTITLE");
				String  bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int	bHit = resultSet.getInt("bHit");
				int	bGroup= resultSet.getInt("bGroup");
				int	bStep= resultSet.getInt("bStep");
				int	bIndent= resultSet.getInt("bIndent");
				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate,
									bHit, bGroup, bStep, bIndent);
				//이전 방식 dto객체를 만들어서 setter로 집어 넣음
				//여기의 방식 일단 필드 값에 대한 객체를 만들어서 필드값을 요구하는 생성자를 이용하여 자료를 저장
				
				dtos.add(dto);
			}
			//mvc보드를 가지고 오는데, list로 가져와라 order by bGroup desc, bStep asc
			// connection -> preparedStatement -> executeQuery
			//생성자 이용하여 BDto dto를 생성하고
			//dots에 add시켜라
		} catch (Exception e) {
			System.out.println("BDao list Exception은 다음과 같다: " + e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
				
				if (preparedStatement != null)
					preparedStatement.close();
				
				if (resultSet != null)
					resultSet.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return dtos;
	}
	
	public BDto contentView(String bId) {
		upHit(bId);
		BDto dto = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query = "select *  "
					+ "from mvc_board where bid = ? "; //게시글 내용을 가져오는 쿼리
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bId);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				int bIdInt = resultSet.getInt("bId");
				String bName = resultSet.getString("BNAME");
				String bTitle = resultSet.getString("BTITLE");
				String  bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int	bHit = resultSet.getInt("bHit");
				int	bGroup= resultSet.getInt("bGroup");
				int	bStep= resultSet.getInt("bStep");
				int	bIndent= resultSet.getInt("bIndent");
				dto = new BDto(bIdInt, bName, bTitle, bContent, bDate,
									bHit, bGroup, bStep, bIndent);	
			}
			// dto 저장 
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
			        if(resultSet != null)		  resultSet.close();
					if(preparedStatement != null) preparedStatement.close();
					if(connection != null)        connection.close();
					
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}
		return dto;
	
	}

	private void upHit(String bId) {
		//조회수를 1 늘리는 메소드 값을 가져온 뒤에 1을 더하여 저장한다.
		
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
	
		
		try {
			connection = dataSource.getConnection();
			
			String query = "update mvc_board set BHIT=BHIT+1 where bId = ?"; 
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bId);
			
			int rn = preparedStatement.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
					if(preparedStatement != null) preparedStatement.close();
					if(connection != null)        connection.close();
					
			} catch (Exception e2) {
					// TODO Auto-generated catch block
					
				e2.printStackTrace();
			}
		
		
		}
	}
	
	
	public void modify(String bId,String bName,String bTitle,String bContent) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query = "update mvc_board set bName = ? ,bTitle = ?, "
					+ " bContent = ? where bId = ?"; //게시글 내용을 가져오는 쿼리
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			preparedStatement.setString(4, bId);
		
			int rn = preparedStatement.executeUpdate();
			
			// dto 저장 
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
			     
					if(preparedStatement != null) preparedStatement.close();
					if(connection != null)        connection.close();
					
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}
		
	}
	
	public void write(String bName,String bTitle,String bContent) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int bHit = 0;
		int bStep = 0;
		int bIndent = 0;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "insert into mvc_board(bId,bName,bTitle,bContent,"
					+ "bHit,bGroup,bStep,bIndent,bDate) "
					+ "values(mvc_board_seq.nextval,?,?,?,0,"
					+ "mvc_board_seq.currval,0,0,sysdate) "; 
			
			pstmt = conn.prepareStatement(sql);
	
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
	
			
			int rn = pstmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
					if(pstmt != null) pstmt.close();
					if(conn != null)  conn.close();
					
			} catch (Exception e2) {
					// TODO Auto-generated catch block
					
				e2.printStackTrace();
			}
		
		}	
	}
	
	
	
	public BDto reply_view(String str) {
	
		BDto dto = null;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query = "select * from mvc_board where bId=?"; //게시글 내용을 가져오는 쿼리
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, Integer.parseInt(str));
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				int bId = resultSet.getInt("bId");
				String bName = resultSet.getString("BNAME");
				String bTitle = resultSet.getString("BTITLE");
				String  bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int	bHit = resultSet.getInt("bHit");
				int	bGroup= resultSet.getInt("bGroup");
				int	bStep= resultSet.getInt("bStep");
				int	bIndent= resultSet.getInt("bIndent");
				dto = new BDto(bId, bName, bTitle, bContent, bDate,
									bHit, bGroup, bStep, bIndent);	
			}
			// dto 저장 
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
			        if(resultSet != null)		  resultSet.close();
					if(preparedStatement != null) preparedStatement.close();
					if(connection != null)        connection.close();
					
			} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
			}
		}
		return dto;
	}
	
	
	
	public void reply(String bId,String bName,
			String bTitle,String bContent,
			String bGroup,String bStep,String bIndent) {
		
		// bStep을 증가 시킨다. (입력하는 댓글이 사이에 들어가도록 함)
		replyShape(bGroup,bStep);
				
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "insert into mvc_board (bId, bName, bTitle,bContent,"
					+ "bGroup, bStep, bIndent) "
					+ "values (mvc_board_seq.nextval,?,?,?,?,?,?)"; 
			
			pstmt = conn.prepareStatement(sql);
	
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bGroup));
			pstmt.setInt(5, Integer.parseInt(bStep)+1);//N번째 글에 대한 덧글의 순서 N+1(N번 바로 다음)
			pstmt.setInt(6, Integer.parseInt(bIndent)+1);//원글에 대한 댓글의 들여쓰기 계층 = 0+1
		
			int rn = pstmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
					if(pstmt != null) pstmt.close();
					if(conn != null)  conn.close();
					
			} catch (Exception e2) {
					// TODO Auto-generated catch block
					
				e2.printStackTrace();
			}
		}	
	}
	
	
	
	//bStep은 댓글이 달린 순서임  12345순서로 5개의 글이 있는데, 3번글에 댓글을 다는 경우
	// bStep>3인 4,5번 글은 순서가 +1씩 됨
	//여기에서 말하는 bStep이 댓글을 다는 원글의 bStep이라는 것을 인지하면 이해가 빠름
	//원글이 아니라 원글의 아래있는 글의 위치를 한칸씩 밀어주는거임
	public void replyShape(String strGroup, String strStep) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query = "update mvc_board set bStep=bStep+1 "
					+ "where bGroup = ? and bStep > ?"; 
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, Integer.parseInt(strGroup));
			preparedStatement.setInt(2, Integer.parseInt(strStep));
			
			int rn = preparedStatement.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
					if(preparedStatement != null) preparedStatement.close();
					if(connection != null)        connection.close();
					
			} catch (Exception e2) {
					// TODO Auto-generated catch block
					
				e2.printStackTrace();
			}		
		}	
	}
	
	
	public void delete(String strbId) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query = "delete from mvc_board where bId = ?"; 
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, Integer.parseInt(strbId));
			
			int rn = preparedStatement.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
					if(preparedStatement != null) preparedStatement.close();
					if(connection != null)        connection.close();
					
			} catch (Exception e2) {
					// TODO Auto-generated catch block
					
				e2.printStackTrace();
			}		
		}		
	}
	
	
	
}
