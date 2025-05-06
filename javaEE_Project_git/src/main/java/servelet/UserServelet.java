package servelet;

import com.google.gson.Gson;
import dto.UserDto;
import entity.User;
import services.UserServices;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;


@WebServlet("/api/user/*")
public class UserServelet  extends HttpServlet {

    private UserServices userServices;

    // work as constractor
    @Override
    public void init() throws ServletException {
        userServices = new UserServices();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        BufferedReader json = req.getReader();
        Gson gson = new Gson();
        UserDto userDto = gson.fromJson(json, UserDto.class);
       UserDto response = userServices.createUser(userDto);
       resp.setContentType("application/json");
       resp.getWriter().println(gson.toJson(response));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null){
          List<UserDto> user = userServices.findAllUsers();
          StringBuilder json = new StringBuilder();
            Gson gson = new Gson();
          json.append("[");

          for(UserDto userDto : user) {
            json.append(gson.toJson(userDto));
            json.append(",");
          }
          json.append("]");
          resp.setContentType("application/json");
          resp.getWriter().println(json.toString());
          }else {
            Long id = Long.parseLong(pathInfo.substring(1));
            userServices.findUser(id);
        }
        }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();
        if (pathInfo ==null){
            Long id = Long.parseLong(pathInfo.substring(1));
            boolean delete= userServices.deleteUser(id);
            if (delete){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else{
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
}

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();
        if(pathInfo != null){
            Long id = Long.parseLong(pathInfo.substring(1));
            BufferedReader json = req.getReader();
            Gson gson = new Gson();
            UserDto userDto = gson.fromJson(json, UserDto.class);
            userDto.setId(id);

           UserDto updateUser =  userServices.updateUser(id , userDto);
           if (updateUser!= null){
               resp.setContentType("application/json");
               resp.getWriter().println(gson.toJson(updateUser));
           }else{
               resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
           }
        }
    }
}
