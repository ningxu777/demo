package com.neil.demo.holiday;

import org.jooq.tools.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Neil on 2016/12/2.
 */
public class main {

    public static void main(String[] args){
        List<Holiday> holidayList = new ArrayList<Holiday>();
        //2016年
        String holidays_2016 = "2016-01-01,元旦;2016-01-02,;2016-01-03,;2016-02-07,除夕;2016-02-08,春节;2016-02-09,;2016-02-10,;2016-02-11,;2016-02-12,;2016-02-13,;" +
                "2016-04-02,;2016-04-03,;2016-04-04,清明节;2016-04-30,;2016-05-01,劳动节;2016-05-02,;2016-06-09,端午节;2016-06-10,;2016-06-11,;" +
                "2016-09-15,中秋节;2016-09-16,;2016-09-17,;2016-10-01,国庆节;2016-10-02,;2016-10-03,;2016-10-04,;2016-10-05,;2016-10-06,;2016-10-07,;";
        //2017年
        String holidays_2017 = "2017-01-01,元旦;2017-01-02,;2017-01-27,除夕;2017-01-28,春节;2017-01-29,;2017-01-30,;2017-01-31,;2017-02-01,;2017-02-02,;" +
                "2017-04-02,;2017-04-03,;2017-04-04,清明节;2017-04-29,;2017-04-30,;2017-05-01,劳动节;2017-05-28,;2017-05-29,;2017-05-30,端午节;" +
                "2017-10-01,国庆节;2017-10-02,;2017-10-03,;2017-10-04,中秋节;2017-10-05,;2017-10-06,;2017-10-07,;2017-10-08,;";

        String holidays = holidays_2016 + holidays_2017;

        String[] holidaysArr = holidays.split(";");

        for(String item:holidaysArr){
            if(!org.jooq.tools.StringUtils.isEmpty(item.trim())){
                String[] itemArr = item.split(",");
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = df.parse(itemArr[0].trim());
                    Holiday holiday = new Holiday();
                    holiday.setDateTime(date.getTime());
                    holiday.setYear(date.getYear()+1900);
                    holiday.setMonth(date.getMonth()+1);
                    holiday.setDate(date.getDate());
                    if(itemArr.length == 1){
                        holiday.setName("");
                    }else{
                        holiday.setName(itemArr[1].trim());
                    }
                    holidayList.add(holiday);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(holidayList.size());
    }
}
