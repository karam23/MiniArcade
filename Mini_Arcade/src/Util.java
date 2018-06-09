
public class Util {
	public static String toUnicode(String str){
		try{
			byte[] b=str.getBytes("MS949");
			return new String(b);			
		}catch(java.io.UnsupportedEncodingException uee){
			uee.printStackTrace();
			return null;
		}
	}
	public static String toLatin(String str){
		try{
			byte[] b=str.getBytes();
			return new String(b, "MS949");			
		}catch(java.io.UnsupportedEncodingException uee){
			uee.printStackTrace();
			return null;
		}
	}
}
