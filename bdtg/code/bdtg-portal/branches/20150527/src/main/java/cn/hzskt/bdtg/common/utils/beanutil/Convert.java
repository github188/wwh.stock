package cn.hzskt.bdtg.common.utils.beanutil;

/**
 * 类型转换接口
 *
 * @param <S> 源类型
 * @param <T> 目标类型
 */
public interface Convert<S, T> {

    T convert(S source);
}  