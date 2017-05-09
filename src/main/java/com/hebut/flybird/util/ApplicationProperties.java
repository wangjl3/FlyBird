package com.hebut.flybird.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

/**
 * Created by WangJL on 2017/5/8.
 */
public class ApplicationProperties {

    public static final Logger logger = LoggerFactory
            .getLogger(ApplicationProperties.class);
    private Properties properties = new Properties();
    public ApplicationProperties(){
        Reader infile = null;
        String filePath = System.getProperty("sysProperties","/application.properties");
        try{
            if(filePath != null && !filePath.equals("/application.properties")){
                infile = new InputStreamReader(new FileInputStream(filePath),"UTF-8");
            }else {
                infile = new InputStreamReader(getClass().getResourceAsStream(filePath),"UTF-8");
                properties.load(infile);
            }
            properties.load(infile);
        }catch(Exception e){
            logger.info("",e);
        }finally {
            if(infile!=null){
                try {
                    infile.close();
                } catch (IOException e) {
                    logger.info("",e);
                }
                infile = null;
            }
        }
    }
    public String getProperty(String key){
        return this.properties.getProperty(key);
    }
    public String getProperty(String key,String defaultVaule){
        return this.properties.getProperty(key,defaultVaule);
    }

}
