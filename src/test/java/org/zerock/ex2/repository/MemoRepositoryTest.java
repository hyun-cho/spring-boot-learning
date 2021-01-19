package org.zerock.ex2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.ex2.entity.Memo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class MemoRepositoryTest {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testInsertDummies() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..." + i).build();
            memoRepository.save(memo);
        });
    }

    // findById는 미리 결과를 실행해놓고 Optional객체에서 한번 더 확인
    @Test
    public void testSelect() {
        Long mno = 100L;
        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("===");

        if (result.isPresent()){
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    /*
     * @getOne의 경우 리턴 값은 해당 객체지만, 실제 객체가 필요한 순간까지 SQL을 실행하지 않음
     * testSelect2의 경우에 테스트 결과에서 에러가 나옴. 무슨 에러인지 모르겠 읍읍
     * @Transactional은 트랜잭션 처리를 위해 사용하는 어노테이션
     * */
    @Transactional
    @Test
    public void testSelect2() {
        Long mno = 99L;
        Memo memo = memoRepository.getOne(mno);

        System.out.println("===");
        System.out.println(memo);
    }


    /*
     * update쿼리의 특이한 점은 해당 번호의 memo를 확인(select)하고, 이를 업데이트 한다.
     * 객체를 메모리 상에 보관하기 위해 저장해서 엔티티 객체가 존재하는지 먼저 확인하는것
     * */
    @Test
    public void testUpdate() {
        Memo memo = Memo.builder().mno(100L).memoText("Update Text").build();

        System.out.println(memoRepository.save(memo));
    }


    /*
     * delete의 경우에 return 타입은 void.
     * 만일 해당 데이터가 존재하지 않다면 EmptyResultDataAccessExcetption 예외 발생
     * 테스ㅡㅌ 코드의 실행결과는 select dlgndp ㅇ딛ㅅㄷrnansdl tlfgodgksms qkdtlrdmfh ehdwkr
     * */
    @Test
    public void testDelete() {
        Long mno = 100L;
        memoRepository.deleteById(mno);
    }

    @Test
    public void testPageDefault() {
        //1페이지 10개

        Pageable pageable = PageRequest.of(0,10);
        // return type이 Page 타입이다. 단순히 해당 목록만으로 가져오는데 그치지 않고,
        // 실제 페이지 처리에 필요한 전체 데이터의 개수를 가져오는 쿼리 역시 같이 처리하기 때문
        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println(result);

        System.out.println("--------------------------");


        // Page<EntityType>은 다양한 메서드를 제공

        System.out.println("Total Pages : " + result.getTotalPages());
        System.out.println("Total Count : " + result.getTotalElements());
        System.out.println("Page Number : " + result.getNumber());
        System.out.println("Page Size : " + result.getSize());
        System.out.println("has next page? : " + result.hasNext());
        System.out.println("first page? : " + result.isFirst());

        // 실제 데이터의 처리를 위해서는 getContent()를 이용해서 List<EntityType>이나 Stream<EntityType>을 반환하는 get()사용
        System.out.println("-----------");

        for (Memo memo: result.getContent()) {
            System.out.println(memo);
        }
    }

    /*
    * PageRequest 에는 정렬과 관련된 org.springframework.data.domain.Sort 타입을 파라미터로 전달 가능
    * Sort는 한 개 혹은 여러개의 필드 값을 이용해 순차적 정렬이나 역순 정렬을 지정
    * */
    @Test
    public void testSort() {
        //sort option 설정
        Sort sort1 = Sort.by("mno").descending();

        // 여러개의 조건은 and()를 통해서 여러개의 정렬 조건을 다르게 지정할 수 있다.
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2);

        Pageable pageable = PageRequest.of(0,10);

        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    /*
    * Interfacedp findBy.. 저장
    * Return값을 List 객체로 받아와서 사용
    * */
    @Test
    public void testQueryMethods() {
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);

        for (Memo memo: list) {
            System.out.println(memo);
        }
    }

    @Test
    public void testQueryMethodWithPageable() {
        Pageable pageable = PageRequest.of(0,10, Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);
        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }


    /*
    * @Commit 과 @Transactional을 같이 사용.
    * select문으로 해당 엔티티 객체들을 가져오는 작업과 각 엔티티를 삭제하는 작업이 같이 이루어지기 때문
    * Commit은 최종 결과를 커밋하기 위해 사용, 이를 저장하지 않으면 deleteBy는 기본적으로 Rollback이 기본
    * 현업에서는 잘 사용하지 않는데, 범위로 삭제가 아닌 하나씩 삭제하기 때문
    * */
    @Commit
    @Transactional
    @Test
    public void testDeleteQueryMethods() {
        memoRepository.deleteByMemoByMnoLessThan(10L);
    }
}