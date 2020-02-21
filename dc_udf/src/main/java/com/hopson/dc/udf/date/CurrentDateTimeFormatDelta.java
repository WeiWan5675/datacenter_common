/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hopson.dc.udf.date;


import java.util.Date;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;
import com.hopson.dc.utils.date.DateUtils;

/**
 * <code>CurrentDateDelta(offsets)</code>. 
 * 当前时间，进行日期时间计算,返回format格式日期
 */




public class CurrentDateTimeFormatDelta extends UDF {
	private Text result = new Text();
	
	/**
	 * @param args 参数1：offsets；参数2：format格式
	 * @return
	 */
	public Text evaluate(Text... args) {
		Date now = new Date();
		String offsets = args.length>=1 ? args[0].toString():null;
		Date reDate=DateUtils.relativedelta(now,offsets);
		String format = args.length>=2 ? args[1].toString():"yyyy-MM-dd HH:mm:ss";
		String re = DateUtils.getFormatDate(reDate,format);
		result.set(re);
		return result;
	}
}
