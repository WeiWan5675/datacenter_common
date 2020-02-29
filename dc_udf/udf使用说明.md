# UDF使用说明
---
##1. 添加jar
```
add jar /home/hopson/apps/var/hdfs/easylife_dw/lib/dc_udf-1.0-SNAPSHOT-jar-with-dependencies.jar;
```

##2. 创建方法
```
create temporary function md5 as 'com.hopson.dc.udf.encrypt.UDFMd5';

create temporary function md5 as 'com.hopson.dc.udf.encrypt.UDFMd5';

create temporary function sha256 as 'com.hopson.dc.udf.encrypt.UDFSha256';

create temporary function sha512 as 'com.hopson.dc.udf.encrypt.UDFSha512';

create temporary function enBase64 as 'com.hopson.dc.udf.encrypt.UDFBase64';

create temporary function enDes as 'com.hopson.dc.udf.encrypt.UDFDes';

create temporary function deBase64 as 'com.hopson.dc.udf.decrypt.UDFBase64';

create temporary function deDes as 'com.hopson.dc.udf.decrypt.UDFDes';

create temporary function DateDelta as 'com.hopson.dc.udf.date.DateDelta' 

create temporary function CurrentDateTimeFormatDelta as 'com.hopson.dc.udf.date.CurrentDateTimeFormatDelta'  ;

create temporary function CurrentDateDelta as 'com.hopson.dc.udf.date.CurrentDateDelta';

create temporary function CurrentDateDelta2 as 'com.hopson.dc.udf.date.CurrentDateDelta2';

create temporary function CurrentDateTimeDelta as 'com.hopson.dc.udf.date.CurrentDateTimeDelta';

create temporary function CurrentDateTimeDelta2 as 'com.hopson.dc.udf.date.CurrentDateTimeDelta2';

create temporary function mergeField as 'com.hopson.dc.udf.field.MergeField';

```


