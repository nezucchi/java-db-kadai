package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Posts_Chapter07 {

	public static void main(String[] args) {
		Connection con = null;
        PreparedStatement statement = null;
        PreparedStatement selectStatement = null;

        
        try {
            // データベースに接続
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java",
                "root",
                "wata4noSQL+2024"
            );

            System.out.println("データベース接続成功"+ con);

            // INSERT用のSQLクエリを準備
            String insertSql =             		
            		"""
            		INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES
            		 ('1003', '2023-02-08', '昨日の夜は徹夜でした・・','13' ),
            		 ('1002', '2023-02-08', 'お疲れ様です！', '12'),
            		 ('1003', '2023-02-09', '今日も頑張ります！', '18'),
            		 ('1001', '2023-02-09', '油断は禁物ですよ！', '17'),
            		 ('1002', '2023-02-10', '明日から連休ですね！', '20');
            		 """;
            
            statement = con.prepareStatement(insertSql);
            

                // SQLクエリを実行（DBMSに送信）
                System.out.println("レコード追加を実行します" );
                int rowCnt = statement.executeUpdate();
                System.out.println( rowCnt + "件のレコードが追加されました");
                
                
                // SELECT用のSQLクエリを準備
                String selectSql = "SELECT user_id, posted_at, post_content, likes FROM posts WHERE user_id = 1002;";
                selectStatement = con.prepareStatement(selectSql);
                
                ResultSet result = selectStatement.executeQuery();
                
                
                // SQLクエリの実行結果を抽出
                while(result.next()) {
                	int user_id = result.getInt("user_id");
                    Date posted_at = result.getDate("posted_at");
                    String post_content = result.getString("post_content");
                    int likes = result.getInt("likes");
                    System.out.println("ユーザーIDが" + user_id + "のレコードを検索しました");
                    System.out.println(result.getRow() + "件目：投稿日時=" + posted_at + "／投稿内容=" + post_content + "／いいね数=" + likes );
                }
            
        } catch(SQLException e) {
            System.out.println("エラー発生：" + e.getMessage());
        } finally {
            // 使用したオブジェクトを解放
            if( statement != null ) {
                try { statement.close(); } catch(SQLException ignore) {}
            }
            if( con != null ) {
                try { con.close(); } catch(SQLException ignore) {}
            }
        }

	}

}
