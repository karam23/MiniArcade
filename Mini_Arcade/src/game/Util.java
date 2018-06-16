package game;

public class Util {
	public static String toUnicode(String str){
	      try{
	         byte[] b=str.getBytes("UTF-8");
	         return new String(b);         
	      }catch(java.io.UnsupportedEncodingException uee){
	         uee.printStackTrace();
	         return null;
	      }
	   }
	   public static String toLatin(String str){
	      try{
	         byte[] b=str.getBytes();
	         return new String(b, "KSC5601");         
	      }catch(java.io.UnsupportedEncodingException uee){
	         uee.printStackTrace();
	         return null;
	      }
	   }
}
