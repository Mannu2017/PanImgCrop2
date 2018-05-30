package com.mannu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataUtility {
	private Connection conn;

	public List<String> getAllFiles(String text)  {
		List<String> fiList=new ArrayList<String>();
		try {
			conn=DriverManager.getConnection("jdbc:sqlserver://192.168.84.98;user=sa;password=Karvy@123;database=pan");
			PreparedStatement ps=conn.prepareStatement("select u.ImgPath,u.SDocumentNo from ImageUploadDetails u inner join Inward i on u.InwardID=i.InwardID where i.Ackno='"+text+"' order by u.SDocumentNo");
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				fiList.add(rs.getString(1));
			}
		} catch (SQLException e) {
			return fiList;
		}
		
		return fiList;
	}

}
