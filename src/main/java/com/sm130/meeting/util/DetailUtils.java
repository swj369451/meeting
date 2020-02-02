package com.sm130.meeting.util;

import com.sm130.meeting.po.Detail;
import com.sm130.meeting.po.User;
import com.sm130.meeting.service.DetailService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

public class DetailUtils {

    public static void addDetail(DetailService detailService,String details){
        Detail detail = new Detail();
        detail.setTime(new Date());
        detail.setContent(details);
        User user = (User) getSession().getAttribute("user");
        detail.setNickname(user.getNickname());
        detail.setUserid(user.getId());
        detailService.save(detail);
    }
    public static HttpSession getSession(){
        return getRequest().getSession();
    }

    public static ServletRequestAttributes getRequestAttributes() {

        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }
}
