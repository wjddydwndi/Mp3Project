
<h1>Mp3Project</h1>
<img src="https://postfiles.pstatic.net/MjAxOTA1MjBfNTIg/MDAxNTU4Mjg1NzcwNTE3.49w-pe0AOLvdbu-lMXPiH0KrI6jJsbmtLiSleDHGFicg.on4Bd0jyFlD0m7mTjhdlyns8Lty6NqgID3APOlALtnQg.PNG.wjddydwndi/MAIN.png?type=w773" width="100%">
프로젝트 주제	: JAVA를 이용한 응용프로그램 만들기.

프로젝트 구분 : TeamProject

프로젝트 설명 :  로그인 처리를 통하여, 유저마다 자신의 취향별로 곡을 담을 수 있는 프로그램.<br>
  기존 mp3플레이어와 차이점은 유저들끼리 소통을 할 수 있는 게시판을 추가하여, 곡에 대한 평가등 유저끼리 의사소통을 할 수 있음.

개발 툴	: Eclipse.

개발 기술 : JAVA, Oracle

프로젝트 기간	: 2019. 02 .02 ~ 2019.02.10
<br><br><br><br>
<h3>구조 - DataBase</h3>
<br><br>
<img src="https://postfiles.pstatic.net/MjAxOTA1MjBfMjM4/MDAxNTU4Mjg1NzgyNDMw.-TpJxvwOyu17NyEYJP1C2HsfRq_vi-JVy7c-OlqXFdkg.JiWpODywn94SaVgtO6-N2cyz1j9JDpicb8Rqy92Msngg.PNG.wjddydwndi/2.png?type=w773" width="100%">
<br><BR>
<h4> ERD </h4>
<br><br>
<img src="https://postfiles.pstatic.net/MjAxOTA1MjBfMjYz/MDAxNTU4Mjg1NzkwNDcw.D69J5WaVFWtNcYD9zZd-ZjA9VCrWKpZydH9flbV0muUg.JgVgTy4JfO5PBqxzX3Kom6VbqcZic9FdpP_ZgVoPZKIg.PNG.wjddydwndi/mp3ERD.png?type=w773" width="100%">
<br><br>

<h3>클래스 구조</h3>
<br><br>
<img src="https://postfiles.pstatic.net/MjAxOTA1MjBfMjU2/MDAxNTU4Mjg1ODEyMzc3.kczdLTIg1uFsTBiiTZa0Px2PH1d82qG9Mc_gIaTP-xAg.IqLHrisDvpeNZJ9CCF481sgZKMmidOIiCeBM4TmoPREg.PNG.wjddydwndi/4.png?type=w773" width="100%">
<br><BR>
<h4> UML</h4>
<br><BR>
  
  <h4> 로그인 관련 </h4>
  <br><BR>
<img src="https://postfiles.pstatic.net/MjAxOTA1MjBfMjgg/MDAxNTU4Mjg1ODE4MjA2.NK8Xry9OPSJrwewKpAajHHX-c6GOMtkrIsI4Oa2s6hUg.6ZTCO7ZBLNzqlJaHSdJR4M7_8MqRmN-9ZmQdihVTvUQg.PNG.wjddydwndi/%EB%A1%9C%EA%B7%B8%EC%9D%B8_%EA%B4%80%EB%A0%A8_UML.png?type=w773" width="100%">
  <br><BR>
   <h4> 플레이어 관련 </h4>
  <br><br>
<img src="https://postfiles.pstatic.net/MjAxOTA1MjBfNTIg/MDAxNTU4Mjg1ODI3NzI5.o7dI-Aca192_0NG-fGcqVV1CqNfHaxaSMQrWkSdKPkAg.ysOvf45_R3HpQuZEUSJUIE_WN4CLsi4pwTLchCWkkWYg.PNG.wjddydwndi/%ED%94%8C%EB%A0%88%EC%9D%B4%EC%96%B4_%EA%B4%80%EB%A0%A8_UML.png?type=w773" width="100%">
  <br><br>
    <h4> 게시판 관련 </h4>
    <br><br>
<img src="https://postfiles.pstatic.net/MjAxOTA1MjBfMzAg/MDAxNTU4Mjg1ODMwMjE0.yy-f699sWbloFYpPDSu8vNJ7l6qmI9pI4GnXnzfuzwMg.ddpuNAIBw2ko9E2ZvGmHAwBtjKHdbqCmxK4j1VRFYi8g.PNG.wjddydwndi/%EA%B2%8C%EC%8B%9C%ED%8C%90_%EA%B4%80%EB%A0%A8_UML.png?type=w773" width="100%">
    <br><br>
  <h3>상세 설명</h3>
  <br><br>
<h4>회원관리 - 로그인</h4>
  <br><br>
<img src="https://postfiles.pstatic.net/MjAxOTA1MjBfMTQw/MDAxNTU4Mjg1ODUwNjE4.3vcUnkJd0-N5gbdtUmlTLE-udzmTos9M2YwPo0j7VEIg.EsSsMArpdbPek81X2lxpdM_oIAhUldGnw7TAy6w0bPcg.PNG.wjddydwndi/5.png?type=w773" width="100%">
  <br>
  로그인 기능을 제공한다. 로그인 한 경우 플레이어 화면으로 전환된다.<br><br>
  1. ID / Password입력<br>
  2. 로그인 버튼 클릭<br>
     2-1. 로그인 성공시 Player화면으로 전환<br>
     2-2. 로그인 실패시 알림이 나오면서 화면 전환 없음<br>
<br><br>
  <h4>회원관리 - 회원가입</h4>
  <br><BR>
<img src="https://postfiles.pstatic.net/MjAxOTA1MjBfNjkg/MDAxNTU4Mjg1ODUwNTcx.a4YcXnWtvXZegHJEVjO_XZUhifOOi-4QZgbm2JpUgFAg.-zCQysc_ODxJGJ2OSprXc0OSoh60SKdp9xEZUvR4Vk4g.PNG.wjddydwndi/6.png?type=w773" width="100%">
  <br>
  회원가입 기능을 제공한다. 로그인 화면에서 Register 버튼을 클릭하면 회원가입으로 화면이 전환된다.  <br>
1. ID / Password / Name / Birth를 입력한다.<br>
    1-1. 각 항목을 입력하지 않은 경우, 알림이 뜨면서 return 된다.<br>
    1-2. check 버튼을 눌러 id 중복체크를 하고 중복되면 return된다.<br>
<br>
2. 정상적으로 입력을 맞추고 Register 버튼을 누르면 회원가입이 된다.<br>
     2-1. Cancel 버튼을 누를 경우 로그인 화면으로 돌아간다.<br>
<br><br>
   <h4>회원관리 - 아이디/비밀번호 찾기</h4>
  <br><BR>
<img src="" width="100%">
  <br>
회원이 자신의 개인정보를 분실한 경우, 개인정보를 찾을 수 있는 기능이다.<br>
로그인 화면에서 아이디 / 비밀번호 찾기 버튼을 클릭 시 화면이 전환된다.<br>
<img src="https://postfiles.pstatic.net/MjAxOTA1MjBfMjEz/MDAxNTU4Mjg1OTIyNDg5.yGVBjqRvwMRE9EcBdJSExxAeDwYWnVkQTITbUT_-0h4g.7XbM7-ywHBZ9ZHm5AzZeG4jJIGNb_MU7pIJrzeSGmOUg.PNG.wjddydwndi/7.png?type=w773" width="100%">
  <Br>
  - 아이디 찾기<br>
    1. 이름 / 생년월일을 입력한다.<br>
    2. 찾기 버튼을 누르면 해당 정보와 일치하는 id를 알려준다.<Br>
  <Br><br>
   - 비밀번호 찾기<br>
    1. ID / 이름 / 생년월일을 입력한다.<br>
    2. 찾기 버튼을 누르면 해당 정보와 일치하는 id를 알려준다.<br>
    <br><br>
    <h4>플레이어 - 재생기능(Play)</h4>
<br><br>
<img src="https://postfiles.pstatic.net/MjAxOTA1MjBfNDkg/MDAxNTU4Mjg1OTIyNTQx.V-_5ZdyxkLunBHqqCK5uj0iuhn0cd-CsQS4WOxlwsIog.Tc0J9pP-Vjy8loeyKiJMEDc7cEV_rdxjlFLsx2rD-zAg.PNG.wjddydwndi/8.png?type=w773" width="100%">
    <br>
    ① Play 버튼을 누르면 pause_resume() method가 실행되고 조건에 따라서 pause, resume, play 함수가 실행된다. 처음 Play 버튼을 누르면 play 함수가 실행된다.<br>
    ② Play method가 실행되면 playlist에서 path를 받아와서 FileInputStream을 이용해서 path에 해당하는 파일을 읽어온다. 읽어 온 후 buffer로 다시 읽어와 player에 입력한다.<br>
입력된 buffer를 가지고 player는 play를 실행한다.
    <br><br>
    <h4>플레이어 - 재생기능(Play-> Pause)</h4> 
<Br><br>
<img src="https://postfiles.pstatic.net/MjAxOTA1MjBfMjE2/MDAxNTU4Mjg1OTI5NzEy.iTkiwirEAZaaO80uFnZGnrVixCnf2T1-j3zA1Ukm3eog.aFN-rACRYJkZ8wVZD-kQqb3DCnbw3E2kLAbeAhlSy_kg.PNG.wjddydwndi/9.png?type=w773" width="100%">
  <br>
  ③④ 재생을 누르면 버튼 아이콘이 정지버튼으로 전환, 정지버튼을 누르면 pause_resume() 함수가 다시 실행된다.<br>
  ⑤Pause_resume() 함수에서 pause 조건과 일치하므로 pause 함수가 실행된다.<br>
  <br><br>
   <h4>플레이어 - 재생기능 (Pause  → Resume)</h4> 
  <br><BR>
<img src="https://postfiles.pstatic.net/MjAxOTA1MjBfMTI5/MDAxNTU4Mjg1OTI5NzI1.7Eb-NRIrBVw4LyuNS1tdG6BIoWrtyKzM6PRYTMCGV40g.zZPmeUahBhCA2Djv4M-yIivhfTMyOn3ov9zgXSaaeDkg.PNG.wjddydwndi/10.png?type=w773" width="100%">
  <br>
  ⑥ 정지버튼 클릭 시, Play 버튼으로 변경되고 노래가 정지된다. 노래가 정지된 후, 다시 Play 버튼을 누르면 Pause_Resume() 함수에 의해 Resume 함수가 실행된다.<br>
  ⑦Resume() 함수가 실행될 때 play 함수가 다시 실행되는데, play 함수는 player를 다시 생성하므로 처음부터 다시 시작한다. 그래서 변수를 넘겨받아 skip method를 이용하여 정지시킨 부분까지 넘어가서 player가 생성되어 play가 된다.<br>
  <br><br>
   <h4>플레이어 - 재생기능 (nextSong / prevSong)</h4> 
<Br><br>
<img src="https://postfiles.pstatic.net/MjAxOTA1MjBfMTU1/MDAxNTU4Mjg1OTM2NTg3.FMYlNrj2GN7XDjQ1bRUQKJdl8fY8fYwl1l-Q0ulAY0Ug.cTCfczBzMAJ8R7HeHO76uqIU2rdaaXaXQCEt2LTIlC4g.PNG.wjddydwndi/11.png?type=w773" width="100%">
  <br>
  ⑧ 다음 버튼이나 이전 버튼을 누르면 노래가 playlist에 위치했던 row를 가져와 그 row의 다음 row나 이전 row의 노래를 재생시킨다.<br>
  ⑨-1 해당 playlist의 마지막 노래나 처음노래에서 다음버튼 또는 이전버튼을 누를 경우 리스트의 처음 or 마지막으로 이동된다. <br>
  ⑨-2 또한, nextSong은 랜덤 스위치가 켜진 경우 playlist내 랜덤으로 한 곡이 재생된다.
<br><br>
   <h4>플레이어 -재생기능 (ProgressBar  - (Progressbar Thread)</h4> 
<br><br>
<img src="https://postfiles.pstatic.net/MjAxOTA1MjBfMTM0/MDAxNTU4Mjg1OTM2NTg4.Kv0xGw1qF3QcB2JrshgTpWJvzvQZZVk_hGtd4uSJUYUg.MbKaWA_OyagFmNCZLbTaRoArH_uaf8xF48ImzeNXWtgg.PNG.wjddydwndi/12.png?type=w773" width="100%">
  <br>
  ① - 1 노래가 재생되면 ProgressBar에 정의된 Thread에 의해 1초마다 bar의 내용이 차오르면서 시간이 표시된다.<br>
  ② - 2 노래의 시간은 JAudioTagger를 이용하여 시간 Tag값을 얻어왔다.<br>
  <br>
  ② 채워지는 양은 ProgressBar의 전체가 기본값이 100으로 정해져 있기 때문에 노래의 길이에서 1초만큼 지날때마다 늘어나는 값을 100을 기준으로 환산하여 매번 노래가 재생될 때 마다 초기화된다.<br>
  <br><Br>
  <h4>플레이어 -재생기능 (ProgressBar  - (SongThread))</h4> 
  <br><br>
<img src="https://postfiles.pstatic.net/MjAxOTA1MjBfMjk0/MDAxNTU4Mjg4NzY1NDA3.ZPhqfoFYrl0TwJh9q4OOr_rDxo_tUbQDF_8M4RQpb9sg.HpqW_zzeGW9JGIla5kPPG707kj7w-05cObe84Acr7-Mg.PNG.wjddydwndi/000.png?type=w773" width="100%">
 <br>
  ① 노래를 끝까지 들으면 다음 곡으로 넘어가기 위한 Thread이다. 노래가 시작하면 sec가 1초마다 1씩 증가하여 노래의 길이와 같아지는 경우 다음 곡으로 넘어가게 된다.<br>
  ② 노래를 끝까지 듣게 된다면 nextSongstart() method가 실행되고 실행된 method에는 player를 닫아주고 관련된 변수들을 초기값으로 초기화 시켜주는 playIntialize() method와 ProgressBar의 변수들도 초기값으로 초기화 시켜주는 barResetting() method가 실행된다. 다 실행되면 nextSong() method로 다음 곡으로 넘어간다.


<img src="https://postfiles.pstatic.net/MjAxOTA1MjBfMjAw/MDAxNTU4Mjg4NTQyNjUw.AOYa58xdSU03NGWUNApjouEJkTchQUehhjyeAp2zUE8g.oDTNtLj2sMIMJC0liyDyiniHWmE362raZLSaP8F0viYg.PNG.wjddydwndi/000.png?type=w773" width="100%">
 <Br>
  ① - 1 ProgressBar의 임의의 지점을 누르면 해당 노래가 그 지점부터 시작하는 기능이다.<br>
  ① - 2 해당 ProgressBar의 길이를 100을 기준으로 환산하여 X값을 구하였다.<br>
<br>
  ② , ③ MouseClick() method는 realX의 값을 해당 노래의 총 stream 양에 비례하여 곱하고 그 곱한 값을 총 stream 양에서 빼준다.<br>
       빼준 후 play가 다시 되므로 play method 내에 skip method로 인해 누른 지점부터 노래가 재생된다.<br>
  <br>
<img src="" width="100%">
<img src="" width="100%">
<img src="" width="100%">
<img src="" width="100%">
<img src="" width="100%">
<img src="" width="100%">
