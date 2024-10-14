package com.liyz.boot3.common.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Desc: Date tool class
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/8 14:24
 */
@Slf4j
@UtilityClass
public class DateUtil {

    /**
     * 年月日格式匹配符（预置），详情请参阅{@link SimpleDateFormat}
     */
    public static final String PATTERN_YEAR_MONTH_DAY = "yyyyMMdd";
    public static final String PATTERN_YEAR_MONTH = "yyyyMM";
    public static final String TIME_BEGIN = "00:00:00";
    public static final String TIME_END = "23:59:59";
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DATE_1 = "yyyy/MM/dd";
    public static final String TIME_ZONE_GMT8 = "GMT+8";

    /**
     * 获取目标日期的时间戳
     *
     * @param date
     * @return
     */
    public static long getTimeMillis(Date date) {
        if (date == null) {
            throw new NullPointerException("date is not null");
        }
        return date.getTime();
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date currentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /**
     * 将目标时间设置为开始时间
     *
     * @param date
     * @return
     */
    public static Date formatBegin(Date date) {
        return formatTime(date, TIME_BEGIN);
    }

    /**
     * 将目标时间设置为结束时间
     *
     * @param date
     * @return
     */
    public static Date formatEnd(Date date) {
        return formatTime(date, TIME_END);
    }

    /**
     * 将日期设置为固定时间
     *
     * @param date 指定日期
     * @param timePatten 时间10:00:00
     * @return
     */
    public static Date formatTime(Date date, String timePatten) {
        if (date == null) {
            return null;
        }
        if (StringUtils.isBlank(timePatten)) {
            return date;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATE);
        String formatDate = sdf.format(date).concat(" ").concat(timePatten);
        sdf.applyPattern(PATTERN_DATE_TIME);
        try {
            return sdf.parse(formatDate);
        } catch (ParseException e) {
            log.error("formatTime error", e);
        }
        return null;
    }

    /**
     * 将字符串格式日期，转化为日期
     *
     * @param date
     * @param format
     * @return
     */
    public static Date parse(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            log.error("parse error", e);
        }
        return null;
    }

    /**
     * 目标时间增肌天数
     *
     * @param date
     * @param day
     * @return
     */
    public static Date addDay(Date date, int day) {
        return add(date, day, Calendar.DAY_OF_YEAR);
    }

    /**
     * 目标时间增加时间
     *
     * @param date
     * @param amount
     * @param field 详情请参阅{@link Calendar}
     * @return
     */
    public static Date add(Date date, int amount, int field) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * 格式化时间（yyyy-MM-dd HH:mm:ss）
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return formatDate(date, null);
    }

    /**
     * 获取当前时间格式化
     *
     * @param pattern 格式匹配符
     * @return 处理结果
     */
    public static String formatDate(String pattern) {
        return formatDate(currentDate(), pattern);
    }

    /**
     * 格式化时间
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        if (Objects.isNull(date)) {
            return null;
        }
        if (StringUtils.isBlank(pattern)) {
            pattern = PATTERN_DATE_TIME;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 格式化时间
     *
     * @param time
     * @return
     */
    public static String formatDate(LocalDateTime time) {
        return formatDate(time, null);
    }

    /**
     * 格式化时间
     *
     * @param time
     * @param pattern
     * @return
     */
    public static String formatDate(LocalDateTime time, String pattern) {
        if (time == null) {
            return null;
        }
        if (StringUtils.isBlank(pattern)) {
            pattern = PATTERN_DATE_TIME;
        }
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取两个时间的间隔天数
     *
     * @param sourceDate
     * @param targetDate
     * @return
     */
    public static long between(Date sourceDate, Date targetDate) {
        return between(sourceDate, targetDate, ChronoUnit.DAYS);
    }

    /**
     * 计算两个日期的时间差
     *
     * @param sourceDate
     * @param targetDate
     * @param field 详情请参阅{@link ChronoUnit}
     * @return
     */
    public static long between(Date sourceDate, Date targetDate, ChronoUnit field) {
        if (sourceDate == null || targetDate == null) {
            return 0L;
        }
        if (field == null) {
            field = ChronoUnit.DAYS;
        }
        return field.between(convertDateToLocalDateTime(sourceDate),
                convertDateToLocalDateTime(targetDate));
    }

    /**
     * 日期差值的绝对值
     *
     * @param sourceDate
     * @param targetDate
     * @return
     */
    public static long betweenAbs(Date sourceDate, Date targetDate) {
        return Math.abs(between(sourceDate, targetDate, ChronoUnit.DAYS));
    }

    /**
     * 日期差值的绝对值
     *
     * @param sourceDate
     * @param targetDate
     * @param field
     * @return
     */
    public static long betweenAbs(Date sourceDate, Date targetDate, ChronoUnit field) {
        return Math.abs(between(sourceDate, targetDate, field));
    }

    /**
     * 目标时间月份的第一天
     *
     * @param date
     * @return
     */
    public static Date firstDayOfMonth(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 目标时间月份的最后一天
     *
     * @param date
     * @return
     */
    public static Date lastDayOfMonth(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar.getTime();
    }

    /**
     * 目标时间前一个月份的第一天
     *
     * @param date
     * @return
     */
    public static Date firstDayOfPreMonth(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 目标时间前一个月份的最后一天
     *
     * @param date
     * @return
     */
    public static Date lastDayOfPreMonth(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar.getTime();
    }

    /**
     * 目标时间后一个月份的第一天
     *
     * @param date
     * @return
     */
    public static Date firstDayOfNextMonth(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 目标时间后一个月份的最后一天
     *
     * @param date
     * @return
     */
    public static Date lastDayOfNextMonth(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 2);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar.getTime();
    }

    /**
     * 目标时间 季度的第一天
     *
     * @param date
     * @return
     */
    public static Date firstDayOfQuarter(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        if (month <=2) {
            calendar.set(Calendar.MONTH, 0);
        } else if (month <= 5) {
            calendar.set(Calendar.MONTH, 3);
        } else if (month <= 8) {
            calendar.set(Calendar.MONTH, 6);
        } else {
            calendar.set(Calendar.MONTH, 9);
        }
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 目标时间 季度的最后一天
     *
     * @param date
     * @return
     */
    public static Date lastDayOfQuarter(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(firstDayOfQuarter(date));
        calendar.add(Calendar.MONTH, 3);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar.getTime();
    }

    /**
     * 目标时间 上一个季度的第一天
     *
     * @param date
     * @return
     */
    public static Date firstDayOfPreQuarter(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(firstDayOfQuarter(date));
        calendar.add(Calendar.MONTH, -3);
        return calendar.getTime();
    }

    /**
     * 目标时间 上个季度的最后一天
     *
     * @param date
     * @return
     */
    public static Date lastDayOfPreQuarter(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lastDayOfQuarter(date));
        calendar.add(Calendar.MONTH, -3);
        return calendar.getTime();
    }

    /**
     * 目标时间 下一个季度的第一天
     *
     * @param date
     * @return
     */
    public static Date firstDayOfNextQuarter(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(firstDayOfQuarter(date));
        calendar.add(Calendar.MONTH, 3);
        return calendar.getTime();
    }

    /**
     * 目标时间 下一个季度的最后一天
     *
     * @param date
     * @return
     */
    public static Date lastDayOfNextQuarter(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lastDayOfQuarter(date));
        calendar.add(Calendar.MONTH, 3);
        return calendar.getTime();
    }

    /**
     * 目标时间 年度的第一天
     *
     * @param date
     * @return
     */
    public static Date firstDayOfYear(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }

    /**
     * 目标时间 年度的最后一天
     *
     * @param date
     * @return
     */
    public static Date lastDayOfYear(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 1);
        calendar.set(Calendar.DAY_OF_YEAR, 0);
        return calendar.getTime();
    }

    /**
     * 目标时间 上一年度的第一天
     *
     * @param date
     * @return
     */
    public static Date firstDayOfPreYear(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -1);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }

    /**
     * 目标时间 上一年度的最后一天
     *
     * @param date
     * @return
     */
    public static Date lastDayOfPreYear(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, 0);
        return calendar.getTime();
    }

    /**
     * 目标时间 下一年度的第一天
     *
     * @param date
     * @return
     */
    public static Date firstDayOfNextYear(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 1);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }

    /**
     * 目标时间 下一年度的最后一天
     *
     * @param date
     * @return
     */
    public static Date lastDayOfNextYear(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 2);
        calendar.set(Calendar.DAY_OF_YEAR, 0);
        return calendar.getTime();
    }

    /**
     * date转换为LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转date
     *
     * @param time
     * @return
     */
    public static Date convertLocalDateTimeToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 加日期
     *
     * @param time
     * @param number
     * @param field
     * @return
     */
    public static LocalDateTime plusTime(LocalDateTime time, long number, TemporalUnit field) {
        return time.plus(number, field);
    }

    /**
     * 减日期
     *
     * @param time
     * @param number
     * @param field
     * @return
     */
    public static LocalDateTime minusTime(LocalDateTime time, long number, TemporalUnit field) {
        return time.minus(number, field);
    }

    /**
     * 获取两个时间差
     *
     * @param startTime
     * @param endTime
     * @param field
     * @return
     */
    public static long between(LocalDateTime startTime, LocalDateTime endTime,
                               ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) {
            return period.getYears();
        }
        if (field == ChronoUnit.MONTHS) {
            return period.getYears() * 12L + period.getMonths();
        }
        return field.between(startTime, endTime);
    }

    /**
     * 获取下周一
     *
     * @param date
     * @return
     */
    public static Date getNextWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, 7);
        return cal.getTime();
    }

    /**
     * 获取这周一
     *
     * @param date
     * @return
     */
    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }
}
