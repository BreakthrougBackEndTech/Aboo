package com.aboo.movie.springcloud.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-12-12 15:14
 **/
@Slf4j
@Component
public class TrackingFilter extends ZuulFilter{
    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER=true;

    @Autowired
    FilterUtils filterUtils;

    @Override
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;
    }
    @Override
    public int filterOrder() {
        return FILTER_ORDER; // filter执行顺序，通过数字指定 ,优先级为0，数字越大，优先级越低
    }
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }
    private boolean isCorrelationIdPresent(){
        if (filterUtils.getCorrelationId() !=null){
            return true;
        }
        return false;
    }
    private String generateCorrelationId(){
        return java.util.UUID.randomUUID().toString();
    }
    public Object run() {
        if (isCorrelationIdPresent()) {
            log.debug("tmx-correlation-id found in tracking filter: {}.",
                    filterUtils.getCorrelationId());
        }
        else{
            filterUtils.setCorrelationId(generateCorrelationId());
            log.debug("tmx-correlation-id generated in tracking filter: {}.", filterUtils.getCorrelationId());
        }
        RequestContext ctx = RequestContext.getCurrentContext();
        log.debug("Processing incoming request for {}.", ctx.getRequest().getRequestURI());
        return null;
    }
}

