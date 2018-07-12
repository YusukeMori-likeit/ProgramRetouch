package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import base.DBManager;
import beans.BuyDataBeans;

/**
 *
 * @author d-yamaguchi
 *
 */
public class BuyDAO {


	/**
	 * 購入情報登録処理
	 * @param bdb 購入情報
	 * @throws SQLException 呼び出し元にスローさせるため
	 */
	public static int insertBuy(BuyDataBeans bdb) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		int autoIncKey = -1;
		try {
			con = DBManager.getConnection();
			st = con.prepareStatement(
					"INSERT INTO t_buy(user_id,total_price,delivery_method_id,create_date) VALUES(?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, bdb.getUserId());
			st.setInt(2, bdb.getTotalPrice());
			st.setInt(3, bdb.getDelivertMethodId());
			st.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			st.executeUpdate();

			ResultSet rs = st.getGeneratedKeys();
			if (rs.next()) {
				autoIncKey = rs.getInt(1);
			}
			System.out.println("inserting buy-datas has been completed");

			return autoIncKey;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	/**
	 * 購入IDによる購入情報検索
	 * @param buyId
	 * @return BuyDataBeans
	 * 				購入情報のデータを持つJavaBeansのリスト
	 * @throws SQLException
	 * 				呼び出し元にスローさせるため
	 */
	public static BuyDataBeans getBuyDataBeansByBuyId(int buyId) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();

			st = con.prepareStatement(
					"SELECT * FROM t_buy"
							+ " JOIN m_delivery_method"
							+ " ON t_buy.delivery_method_id = m_delivery_method.id"
							+ " WHERE t_buy.id = ?");
			st.setInt(1, buyId);

			ResultSet rs = st.executeQuery();

			BuyDataBeans bdb = new BuyDataBeans();
			if (rs.next()) {
				bdb.setId(rs.getInt("id"));
				bdb.setTotalPrice(rs.getInt("total_price"));
				bdb.setBuyDate(rs.getTimestamp("create_date"));
				bdb.setDelivertMethodId(rs.getInt("delivery_method_id"));
				bdb.setUserId(rs.getInt("user_id"));
				bdb.setDeliveryMethodPrice(rs.getInt("price"));
				bdb.setDeliveryMethodName(rs.getString("name"));
			}

			System.out.println("searching BuyDataBeans by buyID has been completed");

			return bdb;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}
    /**
     * 全てのユーザ情報を取得する
     * @return
     */
    public List<BuyDataBeans> findBuyDataAll(int ID) {
        Connection conn = null;
        PreparedStatement st = null;
        List<BuyDataBeans> buyDataBeansList = new ArrayList<BuyDataBeans>();

        try {
            // データベースへ接続
            conn = DBManager.getConnection();


            // SELECT文を準備
            // TODO: 未実装：管理者以外を取得するようSQLを変更する
            st = conn.prepareStatement(
            		" Select t_buy.id,t_buy.create_date,m_delivery_method.name,t_buy.total_price "
            		      + " FROM t_buy INNER JOIN m_delivery_method "
            		      + " ON t_buy.delivery_method_id = m_delivery_method.id "
            		      + " WHERE t_buy.user_id = ? ");

            		      st.setInt(1, ID);
             // SELECTを実行し、結果表を取得
            ResultSet rs = st.executeQuery();

            // 結果表に格納されたレコードの内容を
            // Userインスタンスに設定し、ArrayListインスタンスに追加
            while (rs.next()) {
                int id = rs.getInt("id");
            	Date date = rs.getTimestamp("create_date");
                String name = rs.getString("name");
                int totalPrice = rs.getInt("total_price");
                BuyDataBeans buyDataBeans = new BuyDataBeans(id, date , name, totalPrice);

                buyDataBeansList.add(buyDataBeans);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            // データベース切断
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return buyDataBeansList;
    }

    public List<BuyDataBeans> findBuyDataAll1(int buy_id) {
        Connection conn = null;
        PreparedStatement st = null;
        List<BuyDataBeans> buyDataBeansList1 = new ArrayList<BuyDataBeans>();

        try {
            // データベースへ接続
            conn = DBManager.getConnection();


            // SELECT文を準備
            // TODO: 未実装：管理者以外を取得するようSQLを変更する
            st = conn.prepareStatement(
            		" Select t_buy.id,t_buy.create_date,m_delivery_method.name,t_buy.total_price,m_delivery_method.price "
            		      + " FROM t_buy INNER JOIN m_delivery_method "
            		      + " ON t_buy.delivery_method_id = m_delivery_method.id "
            		      + " WHERE t_buy.id = ? ");

            		      st.setInt(1, buy_id);
             // SELECTを実行し、結果表を取得
            ResultSet rs = st.executeQuery();

            // 結果表に格納されたレコードの内容を
            // Userインスタンスに設定し、ArrayListインスタンスに追加
            while (rs.next()) {
                int id = rs.getInt("id");
            	Date date = rs.getTimestamp("create_date");
                String name = rs.getString("name");
                int totalPrice = rs.getInt("total_price");
                int deliveryMethodPrice = rs.getInt("price");
                BuyDataBeans buyDataBeans1 = new BuyDataBeans(id, date , name, totalPrice , deliveryMethodPrice);

                buyDataBeansList1.add(buyDataBeans1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            // データベース切断
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return buyDataBeansList1;
    }



}
