## cnuJni.jar

@author skchae@cnuglobal.com
@version 0.1_20200907
@since 2020.09.07

* 해당 라이브러리는 JNI을 연동하기 위하여 제작되었다.
* javah_jni.bat 파일을 해당 폴더 안에서 실행하면 AMI_JNI.h 파일이 생성 => 생성된 파일로 작업하여 dll 또는 so 파일을 받아야함
* JniConfig는 프로퍼티 설정 관리를 위한 클래스 => 프로퍼티 설정이 필요로 하면 해당 파일 사용
* 윈도우(개발환경)에서는 dll 파일을 사용하고 리눅스(서버환경)에서는 so 파일로 구동한다.
* 리눅스 사용시 so 파일에 lib 주소가 하나더 추가된다. 파일명에 lib을 붙여서 사용할지 경로에 lib 붙여서 사용할지 결정한다.
