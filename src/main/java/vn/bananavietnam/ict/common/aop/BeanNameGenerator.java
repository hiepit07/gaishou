package vn.bananavietnam.ict.common.aop;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

/**
 * 独自定義の {@link BeanNameGenerator}
 * 原則として {@link AnnotationBeanNameGenerator} と同じ
 */
public class BeanNameGenerator extends AnnotationBeanNameGenerator {

    @Override
    protected String buildDefaultBeanName(BeanDefinition definition) {
        String defaultBeanName = super.buildDefaultBeanName(definition);
        return defaultBeanName;
    }
}