package com.winterframework.modules.web.jsonresult;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
  
/** 
 *  java日期对象经过Jackson库转换成数字 
 * @author henry 
 */  
public class FirefrogDateSerializer extends JsonSerializer<Date> {  
  
        @Override  
        public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {  
                jgen.writeNumber(value.getTime());  
        }  
}  