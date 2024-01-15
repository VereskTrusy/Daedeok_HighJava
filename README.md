# Daedeok_HighJava

24.01.12  
        자바 코드 깃허브에 업로드 했다. 그 동안 안했었는데 미리 할걸 그랬다

24.01.14  
        어젠 SQLDeveloper 안깔아서 테스트 안했었는데 오늘 테스트 했다.  
        깃허브 쓰기 어렵네..  
        알게된것 :  

        1. 깃허브에 프로젝트 올릴때 프로젝트명 바꾸지 말것.

        2. .ignore 파일에 주석처리된 설정파일 확장자들 주석 해제 할것.
           (이걸 안걸러서 애먹었다..)

        3. master랑 main이랑 branch 합친 버전으로 받을 것..
           방법은 일단 main branch로 clone 한다음에 rebase 했다..  
           깃허브 사용법 더 테스트 해보며 알아야할듯

        4. 깃헙받을 때 루트는 
           받을경로/프로젝트파일+IDE설정파일+.git파일이있을위치/프로젝트파일
           이런식이니까
           프로젝트파일 상위디렉터리를 만들고 그곳에 git init , git clone ... 할것


24.01.14  
        숙제 다했다 아 머리가 이제 못쓸지경인가? 생각이 하나도 안나네 ㄷㄷ..

24.01.15  
        properties 파일을 활용한 JDBC 세팅
        
        1. Collection의 Properties 객체를 활용하여
           res패키지를 만들고 하위 config패키지에서 .properties
           파일을 읽어 JDBC의 driver,url,user,pass를 세팅해주기
        2. ResourceBundle 객체를 활용하여 Properties 기능을
           작성하기(요즘엔 이렇게 쓴다)

