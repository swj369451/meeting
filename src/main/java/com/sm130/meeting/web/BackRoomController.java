package com.sm130.meeting.web;

import com.sm130.meeting.po.Room;
import com.sm130.meeting.po.User;
import com.sm130.meeting.service.DetailService;
import com.sm130.meeting.service.RoomService;
import com.sm130.meeting.service.UserService;
import com.sm130.meeting.web.utils.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("back/room")
public class BackRoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private DetailService detailService;

    @Autowired
    private UserService userService;

    @ModelAttribute
    public Room getTbUser(Long roomId){
        if(roomId == null){
            Room room = new Room();
            room.setProjector(false);
            return room;
        }else {
            return roomService.getById(roomId);
        }

    }

    /**
     * 用户列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
        List<Room> rooms = roomService.getAll();
        model.addAttribute("rooms",rooms);
        return "b/roomList";
    }

    /**
     * 跳转到用户表单页面
     * @return
     */
    @RequestMapping(value = "/form",method = RequestMethod.GET)
    public String roomForm(HttpServletRequest request, Model model){
        String id = request.getParameter("id");

//        传递管理员
        List<User> manager = userService.findAll();
        model.addAttribute("manager",manager);

        if(id!=null){
            Room room = roomService.getById(Long.parseLong(id));
            model.addAttribute("room",room);
        }
        return "b/room_form";
    }

    /**
     * 保存与新增用户
     * @param room
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(Room room,
                       Model model,
                       String manager,
                       RedirectAttributes redirectAttributes){
        BaseResult baseResult;
        if(room.getId()==null){
//            新增
            baseResult = BaseResult.success("新增用户成功");
        }else{
//            修改
            baseResult = BaseResult.success("修改用户成功");
        }

//        存储管理员
        User user = userService.findUser(Long.parseLong(manager));
        room.setUserId(user.getId());
        room.setNickname(user.getNickname());
        Room save = roomService.save(room);
        if(save != null){
//            保存成功
            redirectAttributes.addFlashAttribute("result",baseResult);
            return "redirect:/back/room/list";
        }else{
//            保存失败
            baseResult = BaseResult.success("新增用户失败");
            model.addAttribute("result",baseResult);
            model.addAttribute("room",room);
            return "b/room_form";
        }
    }

    /**
     * 删除用户
     * @param roomId
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public BaseResult delete(String roomId){
        Room room = roomService.deleteById(Long.parseLong(roomId));
        BaseResult baseResult;
        if(room!=null){
            baseResult=BaseResult.success("删除成功");
        }else{
            baseResult=BaseResult.fail("删除失败");
        }
        return baseResult;
    }

//    @RequestMapping(value="/detail",method = RequestMethod.GET)
//    public String detail(String id,Model model){
//        List<Detail> details = detailService.listByRoomId(Long.parseLong(id));
//
//        List<Detail> collect = details.stream()
//                .sorted(Comparator.comparing(Detail::getTime).reversed())
//                .limit(10)
//                .collect(Collectors.toList());
//        model.addAttribute("details",collect);
//        return "b/detail";
//    }
}
