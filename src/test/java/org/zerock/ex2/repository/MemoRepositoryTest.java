package org.zerock.ex2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.ex2.entity.Memo;

import javax.transaction.Transactional;
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
}