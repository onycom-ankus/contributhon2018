#### 크롤링

- PYCON KR 2017: 처음부터 알아보는 웹 크롤러  

  - [발표 동영상-유투브](https://www.youtube.com/watch?v=KwiNvOgtRdo&index=8&t=3s&list=PLZPhyNeJvHRmvCnWMBZJiFXu9kDUcn5FG)
  - [발표자료](https://speakerdeck.com/beomi/pycon-kr-2017-ceoeumbuteo-alaboneun-web-keurolreo)
  

- 인스타그램 소스 분석
  - ID 이름
    - "id":"숫자" (인스타그램에서 임의로 정한 고유 ID)
    - "username":"여기에 저장" (인스타그램 계정 만들때 정한 ID)
  - 게시글
    - "node":{"text":"해시태그 및 게시글" (유저가 사진올릴 때 정한 해시태그 또는 게시글)
  - 댓글 수
    - "edge_media_to_comment":{"count":###}, (### 댓글 수)
  - 저장일시
    - "taken_at_timestamp":##########, (UNIX 타임스탬프 형식으로 저장)
  - 좋아요 수
    - "edge_liked_by":{"count":###}, (### 좋아요 숫자)
  - 사진
    - "display_url":"https://-----.jpg" (저장된 이미지 링크)
    - 뒤에 150x150, 240x240, 320x320, 480x480, 640x640 으로 크기가 변경된 이미지 링크도 있음

