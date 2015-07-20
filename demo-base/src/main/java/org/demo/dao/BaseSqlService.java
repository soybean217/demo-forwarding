package org.demo.dao;

import java.util.LinkedHashMap;

public class BaseSqlService {

	/**
	 * update sql
	 * @param tableName
	 * @param updateValue
	 * @param updateWhere
	 * @return
	 */
	public static String getUpdateSql(String tableName,LinkedHashMap<String,Object> updateValue,LinkedHashMap<String,Object> updateWhere){
		StringBuffer sb = new StringBuffer();
		sb.append("update `");
		sb.append(tableName);
		sb.append("` set ");
		int m=0;
		for(String s:updateValue.keySet()){
			if(m>0){
				sb.append(",");	
			}
			sb.append(s);
			sb.append("=");
			if("now()".equals(updateValue.get(s).toString())){
				sb.append("now()");
			}else{
				sb.append("'");
				sb.append(updateValue.get(s).toString());
				sb.append("'");	
			}
			m++;
		}
		sb.append(" where ");
		m=0;
		for(String s:updateWhere.keySet()){
			if(m>0){
				sb.append("and ");	
			}
			sb.append(s);
			sb.append("=");
			sb.append("'");
			sb.append(updateWhere.get(s).toString());
			sb.append("' ");			
			m++;
		}
		return sb.toString();
	}
	/**
	 * insert sql
	 * @param tableName
	 * @param insertValue
	 * @return
	 */
	public static String getInsertSql(String tableName,LinkedHashMap<String,Object> insertValue){
		StringBuffer sb = new StringBuffer();
		sb.append("insert into `");
		sb.append(tableName);
		sb.append("`(");
		int m=0;
		for(String s:insertValue.keySet()){
			if(m>0){
				sb.append(",");	
			}
			sb.append("`");
			sb.append(s);
			sb.append("`");
			m++;
		}
		sb.append(") values(");
		m=0;
		for(String s:insertValue.keySet()){
			if(m>0){
				sb.append(",");	
			}
			if("now()".equals(insertValue.get(s).toString())){
				sb.append("now()");
			}else{
				sb.append("'");
				sb.append(insertValue.get(s).toString());
				sb.append("'");	
			}
			m++;
		}
		sb.append(")");
		return sb.toString();
	}
	/**
	 * delete sql
	 * @param tableName
	 * @param deleteWhere
	 * @return
	 */
	public static String getDeleteSql(String tableName,LinkedHashMap<String,Object> deleteWhere){
		StringBuffer sb = new StringBuffer();
		sb.append("delete from `");
		sb.append(tableName);
		sb.append("` where ");
		int m=0;
		for(String s:deleteWhere.keySet()){
			if(m>0){
				sb.append(",");	
			}
			sb.append("`");
			sb.append(s);
			sb.append("`=");
			sb.append("'");
			sb.append(deleteWhere.get(s).toString());
			sb.append("'");
			m++;
		}		
		return sb.toString();
	}
	
}
