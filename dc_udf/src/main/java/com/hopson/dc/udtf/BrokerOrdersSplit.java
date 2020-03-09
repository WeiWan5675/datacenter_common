package com.hopson.dc.udtf;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.json.JSONArray;


import java.util.ArrayList;
/**
 * @Author: xiaozhennan
 * @Date: 2020/3/5 10:44
 * @Package: com.hopson.dc.udtf
 * @ClassName: BrokerOrdersSplit
 * @Description:
 **/
public class BrokerOrdersSplit extends GenericUDTF {



    @Override
    public void process(Object[] objects) throws HiveException {
        String input = objects[0].toString();
        String[] result = new String[2];
        result[0] = input;
        result[1] = input+input;
        String[] result1 = new String[2];
        result1[0] = input+"a";
        result1[1] = input+"a"+input;
        forward(result);//一个forward 代表一行
        forward(result1);
    }



    @Override
    public StructObjectInspector initialize(ObjectInspector[] args)
            throws UDFArgumentException {
        if (args.length != 1) {
            throw new UDFArgumentLengthException("ExplodeMap takes only one argument");
        }
        if (args[0].getCategory() != ObjectInspector.Category.PRIMITIVE) {
            throw new UDFArgumentException("ExplodeMap takes string as a parameter");
        }


        ArrayList<String> fieldNames = new ArrayList<String>();
        ArrayList<ObjectInspector> fieldOIs = new ArrayList<ObjectInspector>();
        fieldNames.add("col1");
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldNames.add("col2");
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);

//定义了行的列数和类型
        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames,fieldOIs);
    }
    @Override
    public void close() throws HiveException {


    }
}
