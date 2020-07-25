package cn.apelx.elasticsearch.converter;

import cn.hutool.core.convert.AbstractConverter;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;

/**
 * Hutool
 *
 * @author lx
 * @since 2020/7/22 20:00
 */
public class LocalDateTimeConverter extends AbstractConverter<LocalDateTime> {

    private final String LOCAL_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * 内部转换器，被 {@link AbstractConverter#convert(Object, Object)} 调用，实现基本转换逻辑<br>
     * 内部转换器转换后如果转换失败可以做如下操作，处理结果都为返回默认值：
     *
     * <pre>
     * 1、返回{@code null}
     * 2、抛出一个{@link RuntimeException}异常
     * </pre>
     *
     * @param value 值
     * @return 转换后的类型
     */
    @Override
    protected LocalDateTime convertInternal(Object value) {
        Long mills = null;
        if (value instanceof Calendar) {
            // Handle Calendar
            mills = ((Calendar) value).getTimeInMillis();
        } else if (value instanceof Number) {
            // Handle Number
            mills = ((Number) value).longValue();
        } else if (value instanceof TemporalAccessor) {
            DateTime date = DateUtil.date((TemporalAccessor) value);
//            Date.from(source.atZone(ZoneId.systemDefault()).toInstant());
            return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        } else {
            // 统一按照字符串处理
            final String valueStr = convertToStr(value);
            Date date = null;
            try {
                date = DateUtil.parse(valueStr, LOCAL_DATE_TIME_FORMAT);
            } catch (Exception e) {
                // Ignore Exception
            }
            if (null != date) {
                mills = date.getTime();
            }
        }
        if (null == mills) {
            return null;
        }
        // 返回指定类型
        return LocalDateTime.ofEpochSecond(mills / 1000, 0, ZoneOffset.ofHours(8));
    }
}
