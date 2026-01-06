# FossID Workbench Notice Report

## 사전 필요 작업
- 식별된 각 `컴포넌트/버전`의 `Copyright, URL, License` 정보가 필요합니다. Components 페이지에서 `컴포넌트/버전`별 `Copyright, URL, License` 정보가 입력되지 않은 경우 각 정보 입력 후 실행하여 주시기 바랍니다.
- 도구 실행 도중 `Copyrght, URL, License` 정보가 입력되었는지 확인합니다. 만약, 각 정보가 입력되지 않은 `컴포넌트/버전`이 확인되면 어떠한 정보의 입력이 필요한지 확인할 수 있습니다. 하기와 같이 메시지가 확인되면 Components 페이지에서 `컴포넌트/버전` 검색 및 필요한 정보 입력 후 다시 도구를 실행하여 주시기 바랍니다.
```
e.g)
ID: react-server-dom-webpack / 19.0.0-rc-0bc30748-20241028 > Copyright is null
```

## 사용방법
1.'config.properties' 파일을 수정합니다.
```
- fossid.domain: Workbench 주소 
  + e.g) fossid.osbc.co.kr
- fossid.schema: Workbench 접속 프로토콜
  + e.g.) http or https
- fossid.username: Workbench 로그인 Username
- fossid.apikey: Workbench 유저 API KEY
  + User 메뉴에서 API Key를 먼저 생성하여 주시기 바랍니다.
- fossid.project: Project Name
 + Workbench Project Nmae
- fossid.scan: Scan Name
  + Workbench Scan Name
- fossid.header: 고지문 헤더
  + 고제문에 포함될 헤더 내용
```

2. jar 파일 실행방법
```
$ java -jar fossid_notice_report.jar --protocol https --address fossid.osbc.co.kr --username username --apikey 12345 --projectname projectName --scanname scanName

Parameters:
--protocol
--address
--username
--apikey
--projectname
--scanname

Note: 위 값은 `config.properties` 파일에 설정된 값보다 우선 설정됩니다. `fossid.header`는 `config.properties` 파일에서만 설정 가능합니다.
```