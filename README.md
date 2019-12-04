### Kakao OAuth입니다

<img src="https://postfiles.pstatic.net/MjAxOTEyMDRfMTY3/MDAxNTc1NDIyNTgxMjM2.D1orLQsTB_M4vYmabPLstu2aEOPW4DAvpRLRIym5yyEg.pdWakkq-5pGz3cg23N_CXBhmjy7H3YATXg2_AEI4tMog.PNG.whdals410/kl_1.png?type=w773" width="40%" height="30%" title="px(픽셀) 크기 설정" alt="IMG"></img>

<img src="https://postfiles.pstatic.net/MjAxOTEyMDRfMTYx/MDAxNTc1NDIyNTgxMjI1.R4i5awUl-cFcZcJaz2llTPt2iAeEisT7g-kuN2jaA1Mg.eKSnPQujX8yt4Bcc33WUdG0wfMKU-p5VM92mzccnWw4g.PNG.whdals410/kl_2.png?type=w773" width="40%" height="30%" title="px(픽셀) 크기 설정" alt="IMG"></img>

+ 참고
   + [KakaoDevelopers_](https://developers.kakao.com/docs/android)

+ 인터넷을 사용하기위해 아래코드를 'AndroidManifest.xml'에 추가
```xml
<uses-permission android:name="android.permission.INTERNET" />
```
+ build.gradle (Project) 추가
```
allprojects {
    repositories {
       ...
        mavenCentral()
        maven { url 'http://devrepo.kakao.com:8088/nexus/content/groups/public/' }
       ...
    }
}
```
+ build.gradle (Module) 추가
```
implementation group: 'com.kakao.sdk', name: 'usermgmt', version: project.KAKAO_SDK_VERSION
```

+ 기본 제공 버튼 xml
```xml
<com.kakao.usermgmt.LoginButton
        android:id="@+id/btn_kakao_login"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        />
```
+ 커스텀 로그인 버튼의 동작
``` java
Session session = Session.getCurrentSession();
session.addCallback(new SessionCallback());
session.open(AuthType.KAKAO_LOGIN_ALL, MainActivity.this);
```
+ 커스텀 로그아웃 버튼의 동작
``` java
UserManagement.requestLogout(new LogoutResponseCallback() {
```

+ 사용자정보 요청에 성공한 경우
``` java
@Override
public void onSuccess(UserProfile userProfile) {

    String nickname = userProfile.getNickname();
    String profileImagePath = userProfile.getProfileImagePath();
    String thumnailPath = userProfile.getThumbnailImagePath();
    long id = userProfile.getId();

    Log.d("     Profile      : ", nickname + "");
    Log.d("     Profile      : ", profileImagePath  + "");
    Log.d("     Profile      : ", thumnailPath + "");
    Log.d("     Profile      : ", id + "");
}
```
+ 결과
nickname :: 김종민
profileImage :: http://k.kakaocdn.net/dn/b73HCN/btqzJtUMDOb/6YfzZRX22OpTdI8ML8GfR0/img_640x640.jpg
thumnailPath :: http://k.kakaocdn.net/dn/b73HCN/btqzJtUMDOb/6YfzZRX22OpTdI8ML8GfR0/img_110x110.jpg
id :: 1202998596



