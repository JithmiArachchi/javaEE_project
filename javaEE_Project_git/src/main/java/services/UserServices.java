package services;

import dto.UserDto;
import entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.stream.Collectors;

public class UserServices {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPU");
    public UserDto createUser(UserDto userDto) {
        EntityManager em = emf.createEntityManager();
       try {
           em.getTransaction().begin();
           User user = new User(userDto.getName(), userDto.getEmail());
           em.persist(user);
           em.getTransaction().commit();
           return new UserDto(user.getId(), user.getName() ,user.getEmail());
       }catch (Exception ex) {
           em.getTransaction().rollback();
           throw new RuntimeException(ex);
       }finally {
           em.close();
       }
    }

    // return , user DTO list
    public List<UserDto> findAllUsers() {

        EntityManager em = emf.createEntityManager();

        try {
          return   em.createQuery("SELECT u FROM User u", User.class).getResultList()
                    .stream()
                    .map(user ->  new UserDto(user.getId(), user.getName(), user.getEmail()))
                    .collect(Collectors.toList());

        }catch (Exception ex){


        }finally {
            em.close();
        }
        return null;
    }

    public UserDto findUser(Long id) {
        EntityManager em = emf.createEntityManager();
     try {
         User user=  em.find(User.class,id);
         return   new UserDto(user.getId(), user.getName() ,user.getEmail());
     }finally {
         em.close();
     }
    }

    public boolean deleteUser(Long id){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class,id);
            if (user!=null){
                em.remove(user);
                em.getTransaction().commit();
                return true;
            }
            return false;

        }catch (Exception x) {
        em.getTransaction().rollback();
        throw new RuntimeException(x);
        }finally {
            em.close();
        }

    }


    public UserDto updateUser(Long id, UserDto userDto) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class,id);
            if (user!=null){
               user.setName(userDto.getName());
               user.setEmail(user.getEmail());
               em.merge(user);
               em.getTransaction().commit();

               return    new UserDto(user.getId(), user.getName(), user.getEmail());
            }
          return null;
        }catch (Exception x) {
            em.getTransaction().rollback();
            throw new RuntimeException(x);
        }finally {
            em.close();
        }
    }
}
