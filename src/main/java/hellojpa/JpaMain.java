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
            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity", "street", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new AddressEntity("old1", "street", "10000"));
            member.getAddressHistory().add(new AddressEntity("old2", "street", "10000"));

            em.persist(member);

            em.flush();
            em.clear();
            // DB에 넣은 뒤 DB에는 존재하는 상태로 깔끔하게 조회하기 위함 // 마치 application 다시 실행하는 것처럼

            System.out.println("================== start ==================");
            Member findMember = em.find(Member.class, member.getId());

            // homeCity -> newCity
//            Address oldAddress = findMember.getHomeAddress();
//            findMember.setHomeAddress(new Address("newCity", oldAddress.getStreet(), oldAddress.getZipcode()));
            // 값 타입을 수정할 때는 완전히 새로운 instance로 통으로 갈아끼워야 함 // 값 타입은 추적도 안되기에...
            // 객체 지향 프로그래밍(OOP)에서, 어떤 등급에 속하는 각 객체를 인스턴스라고 한다.
            // 예를 들면 ‘목록(list)’이라는 등급을 정의하고 그 다음에 ‘본인 목록(my list)’이라는 객체를 생성(기억 장치 할당)하면 그 등급의 인스턴스가 생성된다.

            // 치킨 -> 한식
//            findMember.getFavoriteFoods().remove("치킨");
//            findMember.getFavoriteFoods().add("한식");

            // old1 -> newCity1
//            findMember.getAddressHistory().remove(new AddressEntity("old1", "street", "10000"));
            // remove() equals로 동작하기 때문에 equals와 hashcode가 제대로 구현돼있어야 한다
//            findMember.getAddressHistory().add(new AddressEntity("newCity1", "street", "10000"));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}