package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Movie movie = new Movie();
            movie.setDirector("a");
            movie.setActor("b");
            movie.setName("c");
            movie.setPrice(10000);

            em.persist(movie);

            em.flush();
            em.clear();
            // 영속성 컨택스트에 있는 걸 DB로 날리기 때문에 1차캐시에 아무것도 안 남게됨

            Item item = em.find(Item.class, movie.getId());
            System.out.println("item = " + item);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

}