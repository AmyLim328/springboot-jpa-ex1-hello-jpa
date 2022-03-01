package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.changeTeam(team); // **
            em.persist(member);

//            team.getMembers().add(member); // ** // 연관관계 편의 메서드에 넣음

            em.flush();
            em.clear();
            //// 이렇게 해야 DB에서 값을 깔끔하게 가져올 수 있음 ////

            Team findTeam = em.find(Team.class, team.getId()); // 1차 캐시
            List<Member> members = findTeam.getMembers();

            System.out.println("===============================");
            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
            }
            System.out.println("===============================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}