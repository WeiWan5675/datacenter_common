package com.hopson.dc.udf.field;

import com.hopson.dc.common.utils.StringUtils;
import com.hopson.dc.utils.date.DateUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * @Author: xiaozhennan
 * @Date: 2020/2/28 16:29
 * @Package: com.hopson.dc.udf.field
 * @ClassName: MergeField
 * @Description:
 **/
public class MergeField extends UDF {
    static final Logger logger = LoggerFactory.getLogger(MergeField.class);
    private Text result = new Text();

    public Text evaluate(Text... args) {
        ArrayList<String> fields = new ArrayList<>();

        for (Text fieldText : args) {
            if(fieldText == null || fieldText.toString().equalsIgnoreCase("null")){
                continue;
            }
            fields.add(fieldText.toString());
        }

        if(fields.size() == 1){
            result.set(fields.get(0));
            return result;
        }
        if(fields.size() == 2){
            if(fields.get(0).equalsIgnoreCase(fields.get(1))){
                result.set(fields.get(0));
            }else {
                logger.error("两个字段内容不一致,请检查!");
//                System.out.println("两个字段内容不一致,请检查!");
                result.set(fields.get(0) +fields.get(1));
            }
            return result;
        }
        if(fields.size() >= 3){
            String[] strings = fields.toArray(new String[fields.size()]);
            if(StringUtils.isEquals(strings)){
                result.set(fields.get(0));
            }else{
//                System.out.println("合并字段出错,字段不一致,字段信息: ["+fields.toString()+"]");
                logger.error("合并字段出错,字段不一致,字段信息: ["+fields.toString()+"]");
                result.set(fields.toString());
            }
        }

        return result;
    }
}
