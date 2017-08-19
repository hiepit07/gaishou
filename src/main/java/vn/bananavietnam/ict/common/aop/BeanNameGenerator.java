package vn.bananavietnam.ict.common.aop;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

/**
 * “ÆŽ©’è‹`‚Ì {@link BeanNameGenerator}
 * Œ´‘¥‚Æ‚µ‚Ä {@link AnnotationBeanNameGenerator} ‚Æ“¯‚¶
 */
public class BeanNameGenerator extends AnnotationBeanNameGenerator {

    @Override
    protected String buildDefaultBeanName(BeanDefinition definition) {
        String defaultBeanName = super.buildDefaultBeanName(definition);
        return defaultBeanName;
    }
}