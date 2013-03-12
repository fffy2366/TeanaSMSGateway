package com.vastrek.nissan.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

public class GlobalUtil {
	/**
	 * 允许搜索的方式
	 */
	public static Map< String , String > sqlMethods = new HashMap< String , String >() {
		{
			this.put( "like" , " like ? " );
			this.put( "lt" , " < ? " );
			this.put( "gt" , " > ? " );
			this.put( "eq" , " = ? " );
			this.put( "lteq" , " <= ? " );
			this.put( "gteq" , " >= ? " );
		}
	};

	/**
	 * 时间格式转换
	 */
	public static String getFormatTime( Date logtime ) {
		if ( null == logtime ) {
			logtime = new Date();
		}
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String time = "";
		try {
			time = sdf.format( logtime );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 时间格式转换
	 * @param format
	 *        时间格式，默认为"yyyy-MM-dd HH:mm:ss"
	 */
	public static String getFormatTime( Date logtime , String format ) {
		if ( null == logtime ) {
			logtime = new Date();
		}
		if ( null == format ) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat( format );
		String time = "";
		try {
			time = sdf.format( logtime );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 格式化输出,多个字段时以\t分隔
	 * @param msgs
	 *        一个或者多个字段
	 * @return void
	 * @author yaofuyuan
	 * @since 2011-08-09 09:35:00
	 */
	public static void log( Object... msgs ) {
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		StringBuffer logmsg = new StringBuffer( sdf.format( new Date() ) );
		for ( int i = 0 , maxlen = msgs.length ; i < maxlen ; i++ ) {
			logmsg.append( "\t" ).append( msgs[ i ].toString() );
		}
		System.out.println( logmsg );
	}

	/**
	 * 把数组拼成字符串
	 * @param aArray
	 *        数组
	 * @param breakStr
	 *        分隔符
	 * @return 拼好的字符串,当aArray返回空
	 * @author yaofuyuan
	 */
	public static String joinArray( Object [] aArray , String breakStr ) {
		StringBuffer result = new StringBuffer( "" );
		if ( aArray == null ) return result.toString();
		for ( int i = 0 ; i < aArray.length ; i++ ) {
			result.append( "'" ).append( aArray[ i ].toString().replaceAll( "'" , "" ).replaceAll( "\"" , "" ) )
				.append( "'" ).append( breakStr );
		}
		if ( result.length() > 0 ) {
			result = result.delete( result.length() - breakStr.length() , result.length() );
		}
		return result.toString();
	}

	/**
	 * 把数组拼成字符串
	 * @param aArray
	 *        数组
	 * @param breakStr
	 *        分隔符
	 * @return 拼好的字符串,当aArray返回空
	 * @author yaofuyuan
	 */
	public static String joinArray2( Object [] aArray , String breakStr ) {
		StringBuffer result = new StringBuffer( "" );
		if ( aArray == null ) return result.toString();
		for ( int i = 0 ; i < aArray.length ; i++ ) {
			result.append( aArray[ i ].toString() ).append( breakStr );
		}
		if ( result.length() > 0 ) {
			result = result.delete( result.length() - breakStr.length() , result.length() );
		}
		return result.toString();
	}

	public static boolean saveImage( String printPicPath , String thumbPath , String audioPath , int len1 , int len2 ,
		HttpServletRequest request ) {
		ServletInputStream in = null;
		FileOutputStream out1 = null;
		FileOutputStream out2 = null;
		FileOutputStream out3 = null;
		try {
			in = request.getInputStream();
			System.out.println();
			byte [] streamByte = new byte [ 4096 ]; // 将一行的大小设置为NUM，
			int count = 0;
			int byteSize = 0;
			int len = len1+len2;
			out1 = new FileOutputStream( new File( thumbPath ) );
			out2 = new FileOutputStream( new File( printPicPath ) );
			out3 = new FileOutputStream( new File( audioPath ) );
			while ( ( count = in.read( streamByte ) ) != -1 ) {
				if ( byteSize > len ) {
					out3.write( streamByte , 0 , count );
				} else if ( byteSize + count > len ) {
					out2.write( streamByte , 0 , len - byteSize );
					out3.write( streamByte , len - byteSize , byteSize + count - len );
				} else if ( byteSize > len1 ) {
					out2.write( streamByte , 0 , count );
				} else if ( byteSize + count > len1 ) {
					out1.write( streamByte , 0 , len1 - byteSize );
					out2.write( streamByte , len1 - byteSize , byteSize + count - len1 );
				} else {
					out1.write( streamByte , 0 , count );
				}
//				if ( byteSize > len1 ) {
//					out2.write( streamByte , 0 , count );
//				} else if ( byteSize + count > len1 ) {
//					out1.write( streamByte , 0 , len1 - byteSize );
//					out2.write( streamByte , len1 - byteSize , byteSize + count - len1 );
//				} else {
//					out1.write( streamByte , 0 , count );
//				}
				byteSize += count;
			}
			return true;
		} catch ( Exception e ) {
			return false;
		} finally {
			if ( out1 != null ) {
				try {
					out1.close();
				} catch ( IOException e ) {
					e.printStackTrace();
				}
			}
			if ( out2 != null ) {
				try {
					out2.close();
				} catch ( IOException e ) {
					e.printStackTrace();
				}
			}
			if ( out3 != null ) {
				try {
					out3.close();
				} catch ( IOException e ) {
					e.printStackTrace();
				}
			}
		}
	}

	public static boolean saveOneImage( String thumbPath , int len , HttpServletRequest request ) {
		ServletInputStream in = null;
		FileOutputStream out = null;
		try {
			in = request.getInputStream();
			byte [] streamByte = new byte [ 4096 ]; // 将一行的大小设置为NUM，
			int count = 0;
			out = new FileOutputStream( new File( thumbPath ) );
			while ( ( count = in.read( streamByte ) ) != -1 ) {
				out.write( streamByte , 0 , count );
			}
			return true;
		} catch ( Exception e ) {
			return false;
		} finally {
			if ( out != null ) {
				try {
					out.close();
				} catch ( IOException e ) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 把数组拼成字符串
	 * @param aArray
	 *        数组
	 * @param breakStr
	 *        分隔符
	 * @return 拼好的字符串,当aArray返回空
	 * @author yaofuyuan
	 */
	public static String joinArray( ArrayList aArray , String breakStr ) {
		StringBuffer result = new StringBuffer( "" );
		if ( aArray == null ) return result.toString();
		for ( int i = 0 , len = aArray.size() ; i < len ; i++ ) {
			result.append( "'" ).append( aArray.get( i ).toString().replaceAll( "'" , "" ).replaceAll( "\"" , "" ) )
				.append( "'" ).append( breakStr );
		}
		if ( result.length() > 0 ) {
			result = result.delete( result.length() - breakStr.length() , result.length() );
		}
		return result.toString();
	}

	/**
	 * 把字符串拆成数组
	 * @param aArray
	 *        数组
	 * @param breakStr
	 *        分隔符
	 * @return 拼好的字符串,当aArray返回空
	 */
	public static ArrayList< Long > splitToLongArray( String aArrayStr , String breakStr ) {
		ArrayList< Long > result = new ArrayList< Long >();
		if ( aArrayStr == null ) { return result; }
		String [] strArray = aArrayStr.split( breakStr );
		for ( int i = 0 ; i < strArray.length ; i++ ) {
			try {
				if ( !"".equals( strArray[ i ] ) ) {
					result.add( Long.parseLong( strArray[ i ] ) );
				}
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 把字符串拆成数组
	 * @param aArray
	 *        数组
	 * @param breakStr
	 *        分隔符
	 * @return 拼好的字符串,当aArray返回空
	 */
	public static ArrayList< String > splitToStringArray( String aArrayStr , String breakStr ) {
		ArrayList< String > result = new ArrayList< String >();
		if ( aArrayStr == null ) { return result; }
		String [] strArray = aArrayStr.split( breakStr );
		for ( int i = 0 ; i < strArray.length ; i++ ) {
			try {
				if ( !"".equals( strArray[ i ] ) ) {
					result.add( strArray[ i ] );
				}
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String toUTF8( String parameter ) {
		try {
			return new String( parameter.getBytes( "ISO-8859-1" ) , "UTF-8" );
		} catch ( Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return parameter;
		}
	}

	/**
	 * 把c语言变量命名法转为骆驼法则 （camel）命名法
	 * search_site_log -> searchSiteLog
	 * SearchSiteLog -> searchSiteLog
	 * 骆驼法则 （camel）命名法
	 * 第一个单字以小写字母开始；第二个单字的首字母大写或每一个单字的首字母都采用大写字母
	 * 例如 pascalName
	 */
	public static StringBuffer toCamelName( StringBuffer name ) {
		StringBuffer res = new StringBuffer();
		for ( int i = 0 ; i < name.length() ; i++ ) {
			char c = name.charAt( i );
			if ( Character.isDigit( c ) ) {
				res.append( c );
			} else if ( Character.isLetter( c ) ) {
				// 若是前一个字符不是字母数字则，转为大写
				if ( i > 0 && !Character.isLetterOrDigit( name.charAt( i - 1 ) ) ) {
					res.append( Character.toUpperCase( c ) );
				} else {
					res.append( c );
				}
			} else if ( Character.isLetter( c ) ) {
				res.append( c );
			}
		}
		if ( res.length() > 0 ) {
			char c = res.charAt( 0 );
			if ( Character.isUpperCase( c ) ) {
				res.setCharAt( 0 , Character.toLowerCase( c ) );
			}
		}
		return res;
	}

	/**
	 * 把c语言变量命名法转为骆驼法则 （camel）命名法
	 * search_site_log -> searchSiteLog
	 * SearchSiteLog -> searchSiteLog
	 * 骆驼法则 （camel）命名法
	 * 第一个单字以小写字母开始；第二个单字的首字母大写或每一个单字的首字母都采用大写字母
	 * 例如 pascalName
	 */
	public static String toCamelName( String name ) {
		return toCamelName( new StringBuffer( name ) ).toString();
	}

	/**
	 * 把c语言变量命名法转为帕斯卡（pascal）命名法
	 * search_site_log -> SearchSiteLog
	 * searchSiteLog -> SearchSiteLog
	 * 帕斯卡（pascal）命名法
	 * 骆驼命名法是首字母小写，而帕斯卡命名法是首字母大写
	 * 例如 PascalName
	 */
	public static StringBuffer toPascalName( StringBuffer name ) {
		StringBuffer res = new StringBuffer();
		for ( int i = 0 ; i < name.length() ; i++ ) {
			char c = name.charAt( i );
			if ( Character.isDigit( c ) ) {
				res.append( c );
			} else if ( Character.isLetter( c ) ) {
				// 若是前一个字符不是字母数字则，转为大写
				if ( i > 0 && !Character.isLetterOrDigit( name.charAt( i - 1 ) ) ) {
					res.append( Character.toUpperCase( c ) );
				} else {
					res.append( c );
				}
			} else if ( Character.isLetter( c ) ) {
				res.append( c );
			}
		}
		if ( res.length() > 0 ) {
			char c = res.charAt( 0 );
			if ( Character.isLowerCase( c ) ) {
				res.setCharAt( 0 , Character.toUpperCase( c ) );
			}
		}
		return res;
	}

	/**
	 * 把c语言变量命名法转为帕斯卡（pascal）命名法
	 * search_site_log -> SearchSiteLog
	 * searchSiteLog -> SearchSiteLog
	 * 帕斯卡（pascal）命名法
	 * 骆驼命名法是首字母小写，而帕斯卡命名法是首字母大写
	 * 例如 PascalName
	 */
	public static String toPascalName( String str ) {
		return toPascalName( new StringBuffer( str ) ).toString();
	}

	/**
	 * 把帕斯卡（pascal）命名法或者骆驼法则 （camel）命名法 转为c语言变量命名法
	 * SearchSiteLog->search_site_log
	 * searchSiteLog->search_site_log
	 * 下划线 命名法 ,c语言变量命名
	 * 单词之间用"_"分隔,全小写
	 * 例如 search_site_log
	 */
	public static StringBuffer toCVarName( StringBuffer name ) {
		StringBuffer res = new StringBuffer();
		for ( int i = 0 ; i < name.length() ; i++ ) {
			char c = name.charAt( i );
			if ( Character.isUpperCase( c ) ) {
				// 若是前一个字符不是字母数字则，转为大写
				res.append( "_" );
				res.append( Character.toLowerCase( c ) );
			} else {
				res.append( c );
			}
		}
		return res;
	}

	/**
	 * 把帕斯卡（pascal）命名法或者骆驼法则 （camel）命名法 转为c语言变量命名法
	 * 下划线 命名法 ,c语言变量命名
	 * 单词之间用"_"分隔,全小写
	 * 例如 search_site_log
	 */
	public static String toCVarName( String name ) {
		return toCVarName( new StringBuffer( name ) ).toString();
	}

	/**
	 * @Description: TODO( 返回指定长度的随机数 )
	 * @param length
	 *        随机数长度
	 * @return String
	 */
	public static String randomNum( int length ) {
		Random random = new Random();
		StringBuffer sbRandom = new StringBuffer( length );
		for ( int i = 0 ; i < length ; i++ ) {
			sbRandom.append( random.nextInt( 10 ) );
		}
		return sbRandom.toString();
	}

	/**
	 * @Description: TODO( 获取随即 字母 大写 )
	 * @param length
	 *        随机数长度
	 * @return String
	 */
	public static String randomLetter( int length ) {
		String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		char [] c = s.toCharArray();
		Random random = new Random();
		String ranstr = "";
		for ( int i = 0 ; i < length ; i++ ) {
			ranstr += c[ random.nextInt( c.length ) ];
		}
		return ranstr;
	}

	/**
	 * 生成随即字母数字组合！
	 * @param length
	 *        随机数长度
	 * @return
	 */
	public static String randomNumAndLetter( int length ) {
		Random random = new Random();
		StringBuffer sbRandom = new StringBuffer( length );
		int num = random.nextInt( length + 1 ); // 数字出现的个数
		sbRandom.append( randomNum( num ) );
		sbRandom.append( randomLetter( length - num ) );
		return sbRandom.toString();
	}

	/**
	 * 返回两个日期相差的天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long getDistDates( Date startDate , Date endDate ) {
		long totalDate = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime( startDate );
		long timestart = calendar.getTimeInMillis();
		calendar.setTime( endDate );
		long timeend = calendar.getTimeInMillis();
		totalDate = Math.abs( ( timeend - timestart ) ) / ( 1000 * 60 * 60 * 24 );
		return totalDate;
	}

	/**
	 * @Description: TODO( 随即产生中奖用户 )
	 * @author leo(Li lele)
	 * @param proality
	 *        概率值 0.05 表示 5%。
	 * @return boolean
	 * @throws
	 */
	public static boolean winning( double probality ) {
		boolean isy = false;
		Random random = new Random();
		int result = random.nextInt( 100 );
		if ( result < 100 * ( probality ) ) {
			isy = true;
		} else {
			isy = false;
		}
		return isy;
	}

	/**
	 * @Description: TODO( 根据当天时间获取前几天或后几天的具体日期 )
	 * @author leo(Li lele)
	 * @param datePoor
	 *        时间差 例：当天时间为：2012-07-09 ;datePoor = 1 的返回结果为2012-07-08
	 *        datePoor = -1 返回结果为2012-07-10
	 * @return String
	 * @throws
	 */
	public static String byTime( int datePoor ) {
		Calendar cal = Calendar.getInstance();
		cal.add( Calendar.DATE , -datePoor );
		String yesterday = new SimpleDateFormat( "yyyy-MM-dd " ).format( cal.getTime() );
		System.out.println( yesterday );
		return yesterday;
	}

	public static void main( String [] args ) {
		long num = getDistDates( new Date() , new Date( "2012-07-09 11:19:32" ) );
		System.out.println( num );
	}

	public static String getRemoteIP( HttpServletRequest request ) {
		String ip = request.getRemoteAddr();
		ip = request.getHeader( "x-forwarded-for" );
		if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) ) {
			ip = request.getHeader( "Proxy-Client-IP" );
		}
		if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) ) {
			ip = request.getHeader( "WL-Proxy-Client-IP" );
		}
		if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) ) {
			ip = request.getRemoteAddr();
		}
		// if(ip.indexOf(",")!=-1){
		// systemLog("proxy="+ip+"\t"+request.getRequestURL().toString()+"?"+request.getQueryString());
		// String []s=ip.split(",");
		// ip=s[0].trim();
		// }
		// 防止xss注入
		return ip.replaceAll( "[^0-9\\.,]" , "" );
	}
}
