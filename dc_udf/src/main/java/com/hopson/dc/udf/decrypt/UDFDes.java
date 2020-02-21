package com.hopson.dc.udf.decrypt;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

import com.hopson.dc.utils.enDeCrypt.ConstantsEnDeCrypt;
import com.hopson.dc.utils.enDeCrypt.DecryptUtils;

/**
 * @author caihm
 * date: 201907
 */
@Description(name = "des"
        , value = "_FUNC_(string[,string]) - get des decode by given input string."
        , extended = "Example:\n > select _FUNC_(string[,string]) from src;")
public class UDFDes extends UDF {
    private Text result = new Text();

    public UDFDes() {
    }

    /**
     * des解密
     *
     * @param text 被加密的内容(密钥为默认值)
     * @return 解密后的内容
     */
    public Text evaluate(Text text) {
    	return evaluate(text,new Text(ConstantsEnDeCrypt.DES3_KEY));
    }
    
    /**
     * des解密
     * @param text 被加密的内容
     * @param key 密钥
     * @return 解密后的内容
     */
    public Text evaluate(Text text,Text key) {
    	if (text == null || key ==null) {
            return null;
        }
        if (key.toString().length() != 24){
        	return null;
        }

        result.set(DecryptUtils.des3( key.toString() , text.toString() ) );
        return result;
    }
    
}
