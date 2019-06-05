package com.sanshengshui.iot.auth.util;


import java.util.Arrays;

public class ConvertCode {
	 /** 
     * @Title:bytes2HexString 
     * @Description:字节数组转16进制字符串 
     * @param b 
     *            字节数组 
     * @return 16进制字符串 
     * @throws 
     */  
    public static String bytes2HexString(byte[] b) {  
        StringBuffer result = new StringBuffer();  
        String hex;  
        for (int i = 0; i < b.length; i++) {  
            hex = Integer.toHexString(b[i] & 0xFF);  
            if (hex.length() == 1) {  
                hex = '0' + hex;  
            }  
            result.append(hex.toUpperCase());  
        }  
        return result.toString();  
    }  
    /** 
     * @Title:hexString2Bytes 
     * @Description:16进制字符串转字节数组 
     * @param src  16进制字符串 
     * @return 字节数组 
     */  
    public static byte[] hexString2Bytes(String src) {  
        int l = src.length() / 2;  
        byte[] ret = new byte[l];  
        for (int i = 0; i < l; i++) {  
            ret[i] = (byte) Integer  
                    .valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();  
        }  
        return ret;  
    }
    /** 
     * @Title:string2HexString 
     * @Description:字符串转16进制字符串 
     * @param strPart  字符串 
     * @return 16进制字符串 
     */  
    public static String string2HexString(String strPart) {  
        StringBuffer hexString = new StringBuffer();  
        for (int i = 0; i < strPart.length(); i++) {  
            int ch = (int) strPart.charAt(i);  
            String strHex = Integer.toHexString(ch);  
            hexString.append(strHex);  
        }  
        return hexString.toString().toUpperCase();  
    }  
    /** 
     * @Title:hexString2String 
     * @Description:16进制字符串转字符串 
     * @param src 
     *            16进制字符串 
     * @return 字节数组 
     * @throws 
     */  
    public static String hexString2String(String src) {
        src = src.replaceAll(" ","");
        String temp = "";  
        for (int i = 0; i < src.length() / 2; i++) {
        	//System.out.println(Integer.valueOf(src.substring(i * 2, i * 2 + 2),16).byteValue());
            temp = temp+ (char)Integer.valueOf(src.substring(i * 2, i * 2 + 2),16).byteValue();  
        }  
        return temp;  
    }  
      
    /** 
     * @Title:char2Byte 
     * @Description:字符转成字节数据char-->integer-->byte 
     * @param src 
     * @return 
     * @throws 
     */  
    public static Byte char2Byte(Character src) {  
        return Integer.valueOf((int)src).byteValue();  
    }  
      
        /** 
     * @Title:intToHexString 
     * @Description:10进制数字转成16进制 
     * @param a 转化数据 
     * @param len 占用字节数 
     * @return 
     * @throws 
     */  
    public static String intToHexString(int a,int len){  
        len<<=1;  
        String hexString = Integer.toHexString(a);  
        int b = len -hexString.length();  
        if(b>0){  
            for(int i=0;i<b;i++)  {  
                hexString = "0" + hexString;  
            }  
        }else if(b<0){
            hexString = hexString.substring(hexString.length()-len,hexString.length());
        }
        return hexString;  
    }  
      
    
    /**
     * 将16进制的2个字符串进行异或运算
     * http://blog.csdn.net/acrambler/article/details/45743157	
     * @param strHex_X
     * @param strHex_Y
     * 注意：此方法是针对一个十六进制字符串一字节之间的异或运算，如对十五字节的十六进制字符串异或运算：1312f70f900168d900007df57b4884
		先进行拆分：13 12 f7 0f 90 01 68 d9 00 00 7d f5 7b 48 84
		13 xor 12-->1
		1 xor f7-->f6
		f6 xor 0f-->f9
		....
		62 xor 84-->e6
		即，得到的一字节校验码为：e6
     * @return
     */
    public static String xor(String strHex_X,String strHex_Y){   
        //将x、y转成二进制形式   
        String anotherBinary=Integer.toBinaryString(Integer.valueOf(strHex_X,16));   
        String thisBinary=Integer.toBinaryString(Integer.valueOf(strHex_Y,16));   
        String result = "";   
        //判断是否为8位二进制，否则左补零   
        if(anotherBinary.length() != 8){   
        	for (int i = anotherBinary.length(); i <8; i++) {   
                anotherBinary = "0"+anotherBinary;   
            }   
        }   
        if(thisBinary.length() != 8){   
        	for (int i = thisBinary.length(); i <8; i++) {   
                thisBinary = "0"+thisBinary;   
            }   
        }   
        //异或运算   
        for(int i=0;i<anotherBinary.length();i++){   
        	//如果相同位置数相同，则补0，否则补1   
            if(thisBinary.charAt(i)==anotherBinary.charAt(i))   
                result+="0";   
            else{   
                result+="1";
            }   
        }  
        return Integer.toHexString(Integer.parseInt(result, 2));   
    }  
    
    
    /**
     *  Convert byte[] to hex string.这里我们可以将byte转换成int
	 * @param src byte[] data   
	 * @return hex string   
	 */   
    public static String bytes2Str(byte[] src){   
        StringBuilder stringBuilder = new StringBuilder("");   
        if (src == null || src.length <= 0) {   
            return null;   
        }   
        for (int i = 0; i < src.length; i++) {   
            int v = src[i] & 0xFF;   
            String hv = Integer.toHexString(v);   
            if (hv.length() < 2) {   
                stringBuilder.append(0);   
            }   
            stringBuilder.append(hv);   
        }   
        return stringBuilder.toString();   
    }
    /**
 	 * @param by
 	 * @return 接收字节数据并转为16进制字符串
 	 */
 	public static String receiveHexToString(byte[] by) {
 		try {
 			/*io.netty.buffer.WrappedByteBuf buf = (WrappedByteBuf)msg;
 			ByteBufInputStream is = new ByteBufInputStream(buf);
 			byte[] by = input2byte(is);*/
 			String str = bytes2Str(by);
 			str = str.toLowerCase();
 			return str;
 		} catch (Exception ex) {
 			ex.printStackTrace();
 			System.out.println("接收字节数据并转为16进制字符串异常");
 		}
 		return null;
 	}
 	
 	/**
 	 * "7dd",4,'0'==>"07dd"
 	 * @param input 需要补位的字符串
 	 * @param size 补位后的最终长度
 	 * @param symbol 按symol补充 如'0'
 	 * @return
 	 * N_TimeCheck中用到了
 	 */
 	public static String fill(String input, int size, char symbol) {
		while (input.length() < size) {
			input = symbol + input;
		}
		return input;
	}
    	
    /**
     * ascii码字符串转为16进制的字符串
     * @param str
     * @return
     */
    public static String convertASCIIStringToHex(String str){
   	 
    	  char[] chars = str.toCharArray();
     
    	  StringBuffer hex = new StringBuffer();
    	  for(int i = 0; i < chars.length; i++){
    	    hex.append(Integer.toHexString((int)chars[i]));
    	  }
     
    	  return hex.toString();
    	  }
     
    	  public String convertHexToString(String hex){
     
    	  StringBuilder sb = new StringBuilder();
    	  StringBuilder temp = new StringBuilder();
     
    	  //49204c6f7665204a617661 split into two characters 49, 20, 4c...
    	  for( int i=0; i<hex.length()-1; i+=2 ){
     
    	      //grab the hex in pairs
    	      String output = hex.substring(i, (i + 2));
    	      //convert hex to decimal
    	      int decimal = Integer.parseInt(output, 16);
    	      //convert the decimal to character
    	      sb.append((char)decimal);
     
    	      temp.append(decimal);
    	  }
     
    	  return sb.toString();
    	  }
    	  public static int byteToInt(byte b) {   
    		//Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值 
    		return b & 0xFF;   
    		}
    public static int hexStringToComplement(String hexStr){
        int bm=Integer.parseInt(hexStr,16);
        int ym=-Integer.parseInt(Integer.toBinaryString(~(bm-1)).substring(16),2);
        return ym;
    }
    public static int intToComplement(int val){
        int ym=-Integer.parseInt(Integer.toBinaryString(~(val-1)).substring(16),2);
        return ym;
    }
    public static Byte[] string2Bytes(String src) {
        src = string2HexString(src);
        int l = src.length() / 2;
        Byte[] ret = new Byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = (byte) Integer
                    .valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }
    public static Byte[] bytestoObjects(byte[] bytesPrim) {
        Byte[] bytes = new Byte[bytesPrim.length];
        Arrays.setAll(bytes, n -> bytesPrim[n]);
        return bytes;
    }
    public static byte[] ObjectstoPrim(Byte[] bytesPrim) {
        byte[] bytes = new byte[bytesPrim.length];
        int i = 0;
        for(Byte b :bytesPrim){
            bytes[i++] = b;
        }
        return bytes;
    }
    public static void main(String args[]){
        System.out.println(string2HexString("Hello, World!"));
        System.out.println("+"+hexString2String("0B 00 53 69 6D 70 6C 65 50 72 69 6E 74 00 06 FF 6A 77 59 06 48 65 6C 6C 6F 2C 20 57 6F 72 6C 64 21")+"=");

    }
}
